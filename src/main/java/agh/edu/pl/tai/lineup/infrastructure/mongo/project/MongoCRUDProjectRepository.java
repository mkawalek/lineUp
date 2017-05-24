package agh.edu.pl.tai.lineup.infrastructure.mongo.project;

import agh.edu.pl.tai.lineup.infrastructure.dto.ProjectDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface MongoCRUDProjectRepository extends MongoRepository<ProjectDTO, String> {

    @Async
    CompletableFuture<ProjectDTO> findById(String id);

}
