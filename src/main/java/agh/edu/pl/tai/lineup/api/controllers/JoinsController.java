package agh.edu.pl.tai.lineup.api.controllers;

import agh.edu.pl.tai.lineup.api.ApiDomainConverter;
import agh.edu.pl.tai.lineup.api.requests.joins.JoinRequest;
import agh.edu.pl.tai.lineup.api.responses.IdResponse;
import agh.edu.pl.tai.lineup.api.responses.join.JoinResponse;
import agh.edu.pl.tai.lineup.api.security.AuthenticatedUser;
import agh.edu.pl.tai.lineup.api.security.LoggedUser;
import agh.edu.pl.tai.lineup.domain.joins.JoinRepository;
import agh.edu.pl.tai.lineup.domain.joins.aggregate.Join;
import agh.edu.pl.tai.lineup.domain.joins.valueobject.JoinId;
import agh.edu.pl.tai.lineup.domain.joins.valueobject.JoinStatus;
import agh.edu.pl.tai.lineup.domain.project.ProjectRepository;
import agh.edu.pl.tai.lineup.domain.project.valueobject.ProjectId;
import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;
import agh.edu.pl.tai.lineup.infrastructure.RandomIdGenerator;
import agh.edu.pl.tai.lineup.infrastructure.utils.exceptions.ResourceForbiddenException;
import agh.edu.pl.tai.lineup.infrastructure.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static agh.edu.pl.tai.lineup.infrastructure.utils.Mapper.filterCollection;
import static agh.edu.pl.tai.lineup.infrastructure.utils.Mapper.mapCollection;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class JoinsController {

    private final ProjectRepository projectRepository;
    private final JoinRepository joinRepository;

    @Autowired
    public JoinsController(ProjectRepository projectRepository, JoinRepository joinRepository) {
        this.projectRepository = projectRepository;
        this.joinRepository = joinRepository;
    }

    @RequestMapping(value = "/projects/{projectId}/invite", method = POST)
    public CompletableFuture<IdResponse> inviteUser(
            @PathVariable("projectId") String projectId, @RequestBody JoinRequest request, @LoggedUser AuthenticatedUser performer) {

        return joinRepository
                .save(new Join(JoinId.of(RandomIdGenerator.next()), UserId.of(request.getWho()), ProjectId.of(projectId), true, performer.getUserId()))
                .thenApplyAsync(JoinId::getValue)
                .thenApplyAsync(IdResponse::new);
    }

    @RequestMapping(value = "/projects/{projectId}/join", method = POST)
    public CompletableFuture<IdResponse> joinProject(
            @PathVariable("projectId") String projectId, @RequestBody JoinRequest request, @LoggedUser AuthenticatedUser performer) {

        return joinRepository
                .save(new Join(JoinId.of(RandomIdGenerator.next()), UserId.of(request.getWho()), ProjectId.of(projectId), false, performer.getUserId()))
                .thenApplyAsync(JoinId::getValue)
                .thenApplyAsync(IdResponse::new);
    }

    @RequestMapping(value = "/joins/{joinId}/accept", method = PUT)
    public CompletableFuture<IdResponse> acceptJoinRequest(@PathVariable("joinId") String joinId, @LoggedUser AuthenticatedUser performer) {
        return joinRepository
                .load(JoinId.of(joinId))
                .thenApplyAsync(join -> join.orElseThrow(ResourceNotFoundException::new))
                .thenComposeAsync(join -> projectRepository
                        .load(join.getProjectId())
                        .thenApplyAsync(p -> p.orElseThrow(ResourceNotFoundException::new))
                        .thenApplyAsync(project -> {
                            if (project.getOwner().equals(performer.getUserId()) && !join.getInvitation()) join.markAsAccepted();
                            else if (!project.getOwner().equals(performer.getUserId()) && join.getInvitation()) join.markAsAccepted();
                            else throw new ResourceForbiddenException();
                            return project;
                        })
                        .thenApplyAsync(project -> {
                            project.addParticipant(join.getWho());
                            return project;
                        })
                        .thenApplyAsync(projectRepository::save)
                        .thenApplyAsync(p -> join))
                .thenComposeAsync(joinRepository::save)
                .thenApplyAsync(JoinId::getValue)
                .thenApplyAsync(IdResponse::new); // todo move all below code to some service
    }

    @RequestMapping(value = "/joins/{joinId}/decline", method = PUT)
    public CompletableFuture<IdResponse> declineJoinRequest(@PathVariable("joinId") String joinId, @LoggedUser AuthenticatedUser performer) {
        return joinRepository
                .load(JoinId.of(joinId))
                .thenApplyAsync(join -> join.orElseThrow(ResourceNotFoundException::new))
                .thenComposeAsync(join -> projectRepository
                        .load(join.getProjectId())
                        .thenApplyAsync(p -> p.orElseThrow(ResourceNotFoundException::new))
                        .thenApplyAsync(project -> {
                            if (project.getOwner().equals(performer.getUserId()) && !join.getInvitation()) join.markAsDeclined();
                            else if (!project.getOwner().equals(performer.getUserId()) && join.getInvitation()) join.markAsDeclined();
                            else throw new ResourceForbiddenException();
                            return project;
                        })
                        .thenApplyAsync(p -> join))
                .thenComposeAsync(joinRepository::save)
                .thenApplyAsync(JoinId::getValue)
                .thenApplyAsync(IdResponse::new); // todo move all below code to some service
    }

    @RequestMapping(value = "/projects/{projectId}/joins", method = GET)
    public CompletableFuture<List<JoinResponse>> getAllJoinRequests(@PathVariable("projectId") String projectId, @LoggedUser AuthenticatedUser performer) {
        return projectRepository
                .load(ProjectId.of(projectId))
                .thenApplyAsync(p -> p.orElseThrow(ResourceNotFoundException::new))
                .thenApplyAsync(project -> {
                    if (!project.getOwner().equals(performer.getUserId())) throw new ResourceForbiddenException();
                    else return project;
                })
                .thenComposeAsync(p -> joinRepository.getJoinsForProject(p.getProjectId().getValue()))
                .thenApplyAsync(users -> filterCollection(users, join -> join.getStatus().equals(JoinStatus.PENDING), Collectors.toList()))
                .thenApplyAsync(joins -> mapCollection(joins, ApiDomainConverter::toJoinResponse, Collectors.toList()));
    }

    @RequestMapping(value = "/joins", method = GET)
    public CompletableFuture<List<JoinResponse>> getAllUsersInvitations(@LoggedUser AuthenticatedUser performer) {
        return joinRepository
                .getInvitationsByUser(performer.getUserId().getValue())
                .thenApplyAsync(users -> filterCollection(users, join -> join.getStatus().equals(JoinStatus.PENDING), Collectors.toList()))
                .thenApplyAsync(joins -> mapCollection(joins, ApiDomainConverter::toJoinResponse, Collectors.toList()));
    }

}
