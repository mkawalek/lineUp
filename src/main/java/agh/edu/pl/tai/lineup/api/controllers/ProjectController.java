package agh.edu.pl.tai.lineup.api.controllers;

import agh.edu.pl.tai.lineup.api.ApiDomainConverter;
import agh.edu.pl.tai.lineup.api.requests.project.ParticipantEntityRequest;
import agh.edu.pl.tai.lineup.api.requests.project.ProjectEntityRequest;
import agh.edu.pl.tai.lineup.api.responses.IdResponse;
import agh.edu.pl.tai.lineup.api.responses.project.ProjectResponse;
import agh.edu.pl.tai.lineup.api.security.AuthenticatedUser;
import agh.edu.pl.tai.lineup.api.security.LoggedUser;
import agh.edu.pl.tai.lineup.domain.project.ProjectRepository;
import agh.edu.pl.tai.lineup.domain.project.aggregate.Project;
import agh.edu.pl.tai.lineup.domain.project.valueobject.ProjectId;
import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;
import agh.edu.pl.tai.lineup.infrastructure.RandomIdGenerator;
import agh.edu.pl.tai.lineup.infrastructure.utils.exceptions.ResourceForbiddenException;
import agh.edu.pl.tai.lineup.infrastructure.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class ProjectController {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @RequestMapping(value = "/projects", method = POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public CompletableFuture<IdResponse> createProject(@RequestBody ProjectEntityRequest request, @LoggedUser AuthenticatedUser user) {
        Project project = new Project(ProjectId.of(RandomIdGenerator.next()), request.getName(), request.getDescription(),
                request.getVersionControlUrl(), user.getUserId(), ApiDomainConverter.toProjectTechnologies(request.getProjectTechnologies()));

        return projectRepository
                .save(project)
                .thenApplyAsync(ProjectId::getValue).thenApplyAsync(IdResponse::new);
    }

    @RequestMapping(value = "/projects", method = GET)
    public CompletableFuture<List<ProjectResponse>> getAllProjects() {
        return projectRepository
                .findAll()
                .thenApplyAsync(projects -> projects
                        .stream()
                        .map(ApiDomainConverter::toProjectResponse)
                        .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/projects/{projectId}", method = PUT)
    public CompletableFuture<IdResponse> editProject(@RequestBody ProjectEntityRequest request, @RequestParam("projectId") String projectId, @LoggedUser AuthenticatedUser user) {
        return loadMapAndSaveProject(
                ProjectId.of(projectId),
                user.getUserId(),
                project -> project.editProject(request.getName(), request.getDescription(), request.getVersionControlUrl(), ApiDomainConverter.toProjectTechnologies(request.getProjectTechnologies())));
    }

    @RequestMapping(value = "/projects/{projectId}/activate", method = PUT)
    public CompletableFuture<IdResponse> activateProject(@RequestParam("projectId") String projectId, @LoggedUser AuthenticatedUser performer) {
        return loadMapAndSaveProject(
                ProjectId.of(projectId),
                performer.getUserId(),
                Project::activate
        );
    }

    @RequestMapping(value = "/projects/{projectId}/end", method = PUT)
    public CompletableFuture<IdResponse> endProject(@RequestParam("projectId") String projectId, @LoggedUser AuthenticatedUser performer) {
        return loadMapAndSaveProject(
                ProjectId.of(projectId),
                performer.getUserId(),
                Project::endProject
        );
    }

    @RequestMapping(value = "/projects/{projectId}/participants", method = POST)
    public CompletableFuture<IdResponse> addParticipantToProject(@RequestBody ParticipantEntityRequest request, @RequestParam("projectId") String projectId, @LoggedUser AuthenticatedUser performer) {
        return loadMapAndSaveProject(
                ProjectId.of(projectId),
                performer.getUserId(),
                project -> project.addParticipant(UserId.of(request.getUserId()))
        );
    }

    @RequestMapping(value = "/projects/{projectId}/participants", method = DELETE)
    public CompletableFuture<IdResponse> removeParticipantFromProject(@RequestBody ParticipantEntityRequest request, @RequestParam("projectId") String projectId, @LoggedUser AuthenticatedUser performer) {
        return loadMapAndSaveProject(
                ProjectId.of(projectId),
                performer.getUserId(),
                project -> project.removeParticipant(UserId.of(request.getUserId()))
        );
    }

    @RequestMapping(value = "/projects/{projectId}/inactivate", method = PUT)
    public CompletableFuture<IdResponse> inactivateProject(@RequestParam("projectId") String projectId, @LoggedUser AuthenticatedUser performer) {
        return loadMapAndSaveProject(
                ProjectId.of(projectId),
                performer.getUserId(),
                Project::setAsInactive
        );
    }


    private Project projectExistAndPerformerIsAllowed(Optional<Project> projectOpt, UserId performer) {
        return projectOpt.map(p -> requestPerformerIsProjectOwner(p, performer)).orElseThrow(ResourceNotFoundException::new);
    }

    private Project requestPerformerIsProjectOwner(Project project, UserId performer) {
        if (project.getOwner().equals(performer)) return project;
        else throw new ResourceForbiddenException();
    }

    private CompletableFuture<IdResponse> loadMapAndSaveProject(ProjectId projectId, UserId performer, Consumer<Project> projectFunction) {
        return projectRepository
                .load(projectId)
                .thenApplyAsync(project -> projectExistAndPerformerIsAllowed(project, performer))
                .thenApplyAsync(project -> {
                    projectFunction.accept(project);
                    return project;
                })
                .thenCompose(projectRepository::save)
                .thenApplyAsync(ProjectId::getValue)
                .thenApplyAsync(IdResponse::new);
    }

}
