package agh.edu.pl.tai.lineup.infrastructure.cassandra.user;

import agh.edu.pl.tai.lineup.infrastructure.dto.UserDTO;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CassandraCRUDUserRepository extends CassandraRepository<UserDTO> {

    @Async
    CompletableFuture<UserDTO> findByUserId(String userId);

    @Async
    @Query("SELECT * FROM userdto WHERE email = ?0 ALLOW FILTERING")
    CompletableFuture<List<UserDTO>> findByEmail(String email);

}
