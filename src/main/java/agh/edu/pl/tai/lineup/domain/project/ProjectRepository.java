package agh.edu.pl.tai.lineup.domain.project;

import agh.edu.pl.tai.lineup.domain.project.aggregate.Project;
import agh.edu.pl.tai.lineup.domain.project.valueobject.ProjectId;
import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ProjectRepository {

    CompletableFuture<Optional<Project>> load(ProjectId id);
    CompletableFuture<ProjectId> save(Project project);
    CompletableFuture<List<Project>> findAll();
    CompletableFuture<List<Project>> findByOwner(UserId owner);
    CompletableFuture<List<Project>> findCollaboratedProjects(UserId userId);

}