package agh.edu.pl.tai.lineup.infrastructure.mongo.user;

import agh.edu.pl.tai.lineup.infrastructure.dto.UserDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface MongoCRUDUserRepository extends MongoRepository<UserDTO, String> {

    @Async
    CompletableFuture<UserDTO> findById(String userId);

    @Async
    CompletableFuture<UserDTO> findByEmail(String email);

}
