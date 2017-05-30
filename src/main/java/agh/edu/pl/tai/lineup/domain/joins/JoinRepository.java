package agh.edu.pl.tai.lineup.domain.joins;

import agh.edu.pl.tai.lineup.domain.joins.aggregate.Join;
import agh.edu.pl.tai.lineup.domain.joins.valueobject.JoinId;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface JoinRepository {

    @Async
    CompletableFuture<JoinId> save(Join join);

    @Async
    CompletableFuture<Optional<Join>> load(JoinId joinId);

    @Async
    CompletableFuture<List<Join>> getJoinsForProject(String projectId); // todo isInvitation should be false !

    @Async
    CompletableFuture<List<Join>> getInvitationsByUser(String userId); // todo isInvitation should be true !
}
