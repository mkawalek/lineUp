package agh.edu.pl.tai.lineup.infrastructure.mongo.project;

import agh.edu.pl.tai.lineup.domain.project.ProjectRepository;
import agh.edu.pl.tai.lineup.domain.project.aggregate.Project;
import agh.edu.pl.tai.lineup.domain.project.valueobject.ProjectId;
import agh.edu.pl.tai.lineup.infrastructure.DTODomainConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Repository
public class MongoProjectRepository implements ProjectRepository {

    @Autowired
    private MongoCRUDProjectRepository projectRepository;


    @Override
    public CompletableFuture<Optional<Project>> load(ProjectId id) {
        return projectRepository
                .findById(id.getValue())
                .thenApplyAsync(Optional::ofNullable)
                .thenApplyAsync(projectOpt -> projectOpt.map(DTODomainConverter::fromProjectDTO));
    }

    @Override
    public CompletableFuture<ProjectId> save(Project project) {
        return CompletableFuture.supplyAsync(() -> projectRepository.save(DTODomainConverter.toProjectDTO(project))).thenApplyAsync(p -> ProjectId.of(p.getProjectId()));
    }

    @Override
    public CompletableFuture<List<Project>> findAll() {
        return CompletableFuture.supplyAsync(() -> projectRepository.findAll().stream().map(DTODomainConverter::fromProjectDTO).collect(Collectors.toList()));
    }
}
