package agh.edu.pl.tai.lineup.infrastructure.cassandra;

import agh.edu.pl.tai.lineup.domain.user.UserRepository;
import agh.edu.pl.tai.lineup.domain.user.aggregate.User;
import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;
import agh.edu.pl.tai.lineup.infrastructure.DTODomainConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CassandraUserRepository implements UserRepository {

    @Autowired
    private CassandraCRUDUserRepository userRepository;


    @Override
    public Optional<User> load(UserId id) {
        return userRepository.find(id.getValue()).map(DTODomainConverter::fromUserDTO);
    }

    @Override
    public void save(User user) {
        userRepository.insert(DTODomainConverter.toUserDTO(user));
    }
}
