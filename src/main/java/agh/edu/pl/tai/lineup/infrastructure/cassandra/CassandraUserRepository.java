package agh.edu.pl.tai.lineup.infrastructure.cassandra;

import agh.edu.pl.tai.lineup.domain.user.UserRepository;
import agh.edu.pl.tai.lineup.domain.user.aggregate.User;
import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;
import agh.edu.pl.tai.lineup.infrastructure.DTODomainConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Repository
public class CassandraUserRepository implements UserRepository {

    @Autowired
    private CassandraCRUDUserRepository userRepository;


    @Override
    public CompletableFuture<Optional<User>> load(UserId id) {
        return
                userRepository
                        .findByUserId(id.getValue())
                        .thenApplyAsync(Optional::ofNullable)
                        .thenApplyAsync(user -> user.map((DTODomainConverter::fromUserDTO)));
    }

    @Override
    public void save(User user) {
        userRepository.insert(DTODomainConverter.toUserDTO(user));
    }

    @Override
    public CompletableFuture<List<User>> findByEmail(String email) {
        return userRepository.findByEmail(email).thenApplyAsync(users -> users.stream().map(DTODomainConverter::fromUserDTO).collect(Collectors.toList()));
    }
}
