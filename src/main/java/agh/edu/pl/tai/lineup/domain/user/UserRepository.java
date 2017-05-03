package agh.edu.pl.tai.lineup.domain.user;

import agh.edu.pl.tai.lineup.domain.user.aggregate.User;
import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;

import java.util.Optional;

public interface UserRepository {

    Optional<User> load(UserId id);
    void save(User user);

}
