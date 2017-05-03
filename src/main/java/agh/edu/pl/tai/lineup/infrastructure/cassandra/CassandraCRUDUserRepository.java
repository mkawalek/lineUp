package agh.edu.pl.tai.lineup.infrastructure.cassandra;

import agh.edu.pl.tai.lineup.infrastructure.dto.UserDTO;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@Service
public class CassandraCRUDUserRepository {

    private Cluster cluster;
    private Session session;
    private CassandraOperations cassandraOps;

    public CassandraCRUDUserRepository() throws UnknownHostException {
        cluster = Cluster.builder().addContactPoints(InetAddress.getLocalHost()).build();
        session = cluster.connect("users");
        cassandraOps = new CassandraTemplate(session);
    }

    public void insert(UserDTO entity) {
        cassandraOps.insert(entity);
    }

    public Optional<UserDTO> find(String id) {
        Select s = QueryBuilder.select().from("users");
        s.where(QueryBuilder.eq("id", id));
        UserDTO u = cassandraOps.queryForObject(s, UserDTO.class);
        cassandraOps.truncate("users"); // todo maybe not needed ?
        return Optional.ofNullable(u);
    }

}
