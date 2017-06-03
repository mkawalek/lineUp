package agh.edu.pl.tai.lineup.infrastructure.mongo.join;

import agh.edu.pl.tai.lineup.domain.joins.JoinRepository;
import agh.edu.pl.tai.lineup.domain.joins.aggregate.Join;
import agh.edu.pl.tai.lineup.domain.joins.valueobject.JoinId;
import agh.edu.pl.tai.lineup.infrastructure.DTODomainConverter;
import agh.edu.pl.tai.lineup.infrastructure.dto.JoinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static agh.edu.pl.tai.lineup.infrastructure.utils.Mapper.mapCol;

@Repository
public class MongoJoinRepository implements JoinRepository {

    private final MongoCRUDJoinRepository repository;

    @Autowired
    public MongoJoinRepository(MongoCRUDJoinRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<JoinId> save(Join join) {
        return CompletableFuture
                .supplyAsync(() -> repository.save(DTODomainConverter.toJoinDTO(join)))
                .thenApplyAsync(JoinDTO::getId)
                .thenApplyAsync(JoinId::of);
    }

    @Override
    public CompletableFuture<Optional<Join>> load(JoinId joinId) {
        return repository
                .findById(joinId.getValue())
                .thenApplyAsync(Optional::ofNullable)
                .thenApplyAsync(join -> join.map(DTODomainConverter::fromJoinDTO));
    }

    @Override
    public CompletableFuture<List<Join>> getJoinsForProject(String projectId) {
        return repository
                .findByInvitationAndProjectId(false, projectId)
                .thenApplyAsync(joinDTOS -> mapCol(joinDTOS, DTODomainConverter::fromJoinDTO, Collectors.toList()));
    }

    @Override
    public CompletableFuture<List<Join>> getInvitationsByUser(String userId) {
        return repository
                .findByInvitationAndWho(true, userId)
                .thenApplyAsync(joinDTOS -> mapCol(joinDTOS, DTODomainConverter::fromJoinDTO, Collectors.toList()));
    }
}
