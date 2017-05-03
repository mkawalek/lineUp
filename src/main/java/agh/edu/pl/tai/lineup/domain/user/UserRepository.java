package agh.edu.pl.tai.lineup.domain.user;

import agh.edu.pl.tai.lineup.domain.user.aggregate.User;
import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface UserRepository {

    CompletableFuture<Optional<User>> load(UserId id);
    void save(User user);
    CompletableFuture<List<User>> findByEmail(String email);

}
