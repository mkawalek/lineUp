package agh.edu.pl.tai.lineup.infrastructure.mongo.join;

import agh.edu.pl.tai.lineup.infrastructure.dto.JoinDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface MongoCRUDJoinRepository extends MongoRepository<JoinDTO,String> {

    @Async
    CompletableFuture<JoinDTO> findById(String id);

    @Async
    CompletableFuture<List<JoinDTO>> findByInvitationAndProjectId(Boolean invitation, String projectId);

    @Async
    CompletableFuture<List<JoinDTO>> findByInvitationAndWho(Boolean invitation, String who);

}
