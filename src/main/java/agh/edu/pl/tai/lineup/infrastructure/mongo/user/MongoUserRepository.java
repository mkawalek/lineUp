package agh.edu.pl.tai.lineup.infrastructure.mongo.user;

import agh.edu.pl.tai.lineup.domain.user.UserRepository;
import agh.edu.pl.tai.lineup.domain.user.aggregate.User;
import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;
import agh.edu.pl.tai.lineup.infrastructure.DTODomainConverter;
import agh.edu.pl.tai.lineup.infrastructure.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static agh.edu.pl.tai.lineup.infrastructure.utils.Mapper.mapCol;

@Repository
public class MongoUserRepository implements UserRepository {

    @Autowired
    private MongoCRUDUserRepository userRepository;


    @Override
    public CompletableFuture<Optional<User>> load(UserId id) {
        return
                userRepository
                        .findById(id.getValue())
                        .thenApplyAsync(Optional::ofNullable)
                        .thenApplyAsync(user -> user.map((DTODomainConverter::fromUserDTO)));
    }

    @Override
    public CompletableFuture<UserId> save(User user) {
        return CompletableFuture.supplyAsync(() -> userRepository.insert(DTODomainConverter.toUserDTO(user))).thenApplyAsync(u -> UserId.of(u.getId()));
    }

    @Override
    public CompletableFuture<Optional<User>> findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .thenApplyAsync(Optional::ofNullable)
                .thenApplyAsync(user -> user.map(DTODomainConverter::fromUserDTO));
    }

    @Override
    public CompletableFuture<List<User>> findAll() {
        return CompletableFuture.supplyAsync(() -> userRepository.findAll()).thenApplyAsync(this::convert);
    }

    private List<User> convert(List<UserDTO> userDTOS) {
        return mapCol(userDTOS, DTODomainConverter::fromUserDTO, Collectors.toList());
    }
}
