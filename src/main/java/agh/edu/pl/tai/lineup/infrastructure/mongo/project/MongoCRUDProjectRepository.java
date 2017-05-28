package agh.edu.pl.tai.lineup.infrastructure.mongo.project;

import agh.edu.pl.tai.lineup.infrastructure.dto.ProjectDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface MongoCRUDProjectRepository extends MongoRepository<ProjectDTO, String> {

    @Async
    CompletableFuture<ProjectDTO> findById(String id);

    @Async
    CompletableFuture<List<ProjectDTO>> findByOnwer(String owner);

    @Async
    @Query("{ projectParticipants: ?0 }")
    CompletableFuture<List<ProjectDTO>> findCollaboratingProjects(String userId);

}
