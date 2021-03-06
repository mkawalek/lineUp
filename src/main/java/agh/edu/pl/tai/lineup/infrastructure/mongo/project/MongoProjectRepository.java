package agh.edu.pl.tai.lineup.infrastructure.mongo.project;

import agh.edu.pl.tai.lineup.domain.project.ProjectRepository;
import agh.edu.pl.tai.lineup.domain.project.aggregate.Project;
import agh.edu.pl.tai.lineup.domain.project.valueobject.ProjectId;
import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;
import agh.edu.pl.tai.lineup.infrastructure.DTODomainConverter;
import agh.edu.pl.tai.lineup.infrastructure.dto.ProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static agh.edu.pl.tai.lineup.infrastructure.utils.Mapper.mapCollection;

@Repository
public class MongoProjectRepository implements ProjectRepository {

    private final MongoCRUDProjectRepository projectRepository;

    @Autowired
    public MongoProjectRepository(MongoCRUDProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    @Override
    public CompletableFuture<Optional<Project>> load(ProjectId id) {
        return projectRepository
                .findById(id.getValue())
                .thenApplyAsync(Optional::ofNullable)
                .thenApplyAsync(projectOpt -> projectOpt.map(DTODomainConverter::fromProjectDTO));
    }

    @Override
    public CompletableFuture<ProjectId> save(Project project) {
        return CompletableFuture
                .supplyAsync(() -> projectRepository.save(DTODomainConverter.toProjectDTO(project)))
                .thenApplyAsync(ProjectDTO::getId)
                .thenApplyAsync(ProjectId::of);
    }

    @Override
    public CompletableFuture<List<Project>> findAll() {
        return CompletableFuture
                .supplyAsync(projectRepository::findAll)
                .thenApplyAsync(this::convert);
    }

    @Override
    public CompletableFuture<List<Project>> findByOwner(UserId owner) {
        return projectRepository
                .findByOwner(owner.getValue())
                .thenApplyAsync(this::convert);
    }

    @Override
    public CompletableFuture<List<Project>> findCollaboratedProjects(UserId userId) {
        return projectRepository
                .findCollaboratingProjects(userId.getValue())
                .thenApplyAsync(this::convert);
    }

    private List<Project> convert(List<ProjectDTO> projectDTOS) {
        return mapCollection(projectDTOS, DTODomainConverter::fromProjectDTO, Collectors.toList());
    }
}
