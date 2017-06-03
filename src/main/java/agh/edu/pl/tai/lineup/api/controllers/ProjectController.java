package agh.edu.pl.tai.lineup.api.controllers;

import agh.edu.pl.tai.lineup.api.ApiDomainConverter;
import agh.edu.pl.tai.lineup.api.requests.project.ProjectEntityRequest;
import agh.edu.pl.tai.lineup.api.responses.IdResponse;
import agh.edu.pl.tai.lineup.api.responses.project.ProjectResponse;
import agh.edu.pl.tai.lineup.api.responses.user.UserDetailsResponse;
import agh.edu.pl.tai.lineup.api.security.AuthenticatedUser;
import agh.edu.pl.tai.lineup.api.security.LoggedUser;
import agh.edu.pl.tai.lineup.domain.project.ProjectRepository;
import agh.edu.pl.tai.lineup.domain.project.aggregate.Project;
import agh.edu.pl.tai.lineup.domain.project.entity.ProjectParticipants;
import agh.edu.pl.tai.lineup.domain.project.valueobject.ProjectId;
import agh.edu.pl.tai.lineup.domain.user.UserRepository;
import agh.edu.pl.tai.lineup.domain.user.valueobject.Department;
import agh.edu.pl.tai.lineup.domain.user.valueobject.FieldOfStudy;
import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;
import agh.edu.pl.tai.lineup.infrastructure.RandomIdGenerator;
import agh.edu.pl.tai.lineup.infrastructure.utils.exceptions.ResourceForbiddenException;
import agh.edu.pl.tai.lineup.infrastructure.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static agh.edu.pl.tai.lineup.infrastructure.utils.Mapper.filterCollection;
import static agh.edu.pl.tai.lineup.infrastructure.utils.Mapper.mapCollection;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProjectController(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/projects", method = POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public CompletableFuture<IdResponse> createProject(@RequestBody ProjectEntityRequest request, @LoggedUser AuthenticatedUser user) {
        Project project = new Project(ProjectId.of(RandomIdGenerator.next()), request.getTitle(), request.getDescription(),
                request.getVersionControlUrl(), user.getUserId(), ApiDomainConverter.toProjectTechnologies(request.getTechnologies()));

        return projectRepository
                .save(project)
                .thenApplyAsync(ProjectId::getValue).thenApplyAsync(IdResponse::new);
    }

    @RequestMapping(value = "/projects", method = GET)
    public CompletableFuture<List<ProjectResponse>> getAllProjects() {
        return projectRepository
                .findAll()
                .thenApplyAsync(projects -> mapCollection(projects, ApiDomainConverter::toProjectResponse, Collectors.toList()));
    }

    @RequestMapping(value = "/fieldofstudies", method = GET)
    public CompletableFuture<List<String>> getAllFieldOfStudies() {
        return CompletableFuture
                .supplyAsync(() -> Arrays
                        .stream(FieldOfStudy.values())
                        .map(Enum::name)
                        .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/departments", method = GET)
    public CompletableFuture<List<String>> getAllDepartments() {
        return CompletableFuture
                .supplyAsync(() -> Arrays
                .stream(Department.values())
                .map(Enum::name)
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/projects/{projectId}", method = PUT)
    public CompletableFuture<IdResponse> editProject(@RequestBody ProjectEntityRequest request, @PathVariable("projectId") String projectId, @LoggedUser AuthenticatedUser user) {
        return loadMapAndSaveProject(
                ProjectId.of(projectId),
                user.getUserId(),
                project -> project.editProject(request.getTitle(), request.getDescription(), request.getVersionControlUrl(), ApiDomainConverter.toProjectTechnologies(request.getTechnologies())));
    }

    @RequestMapping(value = "/projects/{projectId}", method = GET)
    public CompletableFuture<ProjectResponse> getProject(@PathVariable("projectId") String projectId, @LoggedUser AuthenticatedUser performer) {
        return projectRepository
                .load(ProjectId.of(projectId))
                .thenApplyAsync(projectOpt -> projectOpt.orElseThrow(ResourceNotFoundException::new))
                .thenApplyAsync(ApiDomainConverter::toProjectResponse);
    }

    @RequestMapping(value = "/projects/{projectId}/activate", method = PUT)
    public CompletableFuture<IdResponse> activateProject(@PathVariable("projectId") String projectId, @LoggedUser AuthenticatedUser performer) {
        return loadMapAndSaveProject(
                ProjectId.of(projectId),
                performer.getUserId(),
                Project::activate
        );
    }

    @RequestMapping(value = "/projects/{projectId}/end", method = PUT)
    public CompletableFuture<IdResponse> endProject(@PathVariable("projectId") String projectId, @LoggedUser AuthenticatedUser performer) {
        return loadMapAndSaveProject(
                ProjectId.of(projectId),
                performer.getUserId(),
                Project::endProject
        );
    }

    @RequestMapping(value = "/projects/{projectId}/participants", method = GET)
    public CompletableFuture<List<UserDetailsResponse>> getParticipantsForProject(@PathVariable("projectId") String projectId) {
        return projectRepository
                .load(ProjectId.of(projectId))
                .thenApplyAsync(projectOpt -> projectOpt.orElseThrow(ResourceNotFoundException::new))
                .thenApplyAsync(Project::getParticipants)
                .thenApplyAsync(ProjectParticipants::getProjectParticipants)
                .thenApplyAsync(users ->
                                users
                                        .stream()
                                        .map(userId -> userRepository
                                                .load(UserId.of(userId.getValue()))
                                                .thenApplyAsync(user -> user.orElseThrow(ResourceNotFoundException::new))
                                                .join()
                                        )
                                        .collect(Collectors.toList())
                )
                .thenApplyAsync(users -> mapCollection(users, ApiDomainConverter::toUserResponse, Collectors.toList()));
    }

    @RequestMapping(value = "/projects/{projectId}/participants/{userId}", method = DELETE)
    public CompletableFuture<IdResponse> removeParticipantFromProject(@PathVariable("projectId") String projectId, @PathVariable("userId") String userId, @LoggedUser AuthenticatedUser performer) {
        return loadMapAndSaveProject(
                ProjectId.of(projectId),
                performer.getUserId(),
                project -> {
                    if (!project.getOwner().equals(performer.getUserId())) throw new ResourceForbiddenException();
                    else project.removeParticipant(UserId.of(userId));
                }
        );
    }

    @RequestMapping(value = "/projects/{projectId}/inactivate", method = PUT)
    public CompletableFuture<IdResponse> inactivateProject(@PathVariable("projectId") String projectId, @LoggedUser AuthenticatedUser performer) {
        return loadMapAndSaveProject(
                ProjectId.of(projectId),
                performer.getUserId(),
                Project::setAsInactive
        );
    }

    @RequestMapping(value = "/projects/collaborated", method = GET)
    public CompletableFuture<List<ProjectResponse>> getCollaboratedProjects(@LoggedUser AuthenticatedUser performer) {
        return projectRepository
                .findCollaboratedProjects(performer.getUserId())
                .thenApplyAsync(users -> filterCollection(users, p -> !p.getOwner().equals(performer.getUserId()), Collectors.toList()))
                .thenApplyAsync(projects -> mapCollection(projects, ApiDomainConverter::toProjectResponse, Collectors.toList()));
    }

    @RequestMapping(value = "/projects/me", method = GET)
    public CompletableFuture<List<ProjectResponse>> getProjectsCreatedByMe(@LoggedUser AuthenticatedUser performer) {
        return projectRepository
                .findByOwner(performer.getUserId())
                .thenApplyAsync(projects -> mapCollection(projects, ApiDomainConverter::toProjectResponse, Collectors.toList()));
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
