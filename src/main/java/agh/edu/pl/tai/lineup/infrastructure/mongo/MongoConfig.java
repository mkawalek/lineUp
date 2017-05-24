package agh.edu.pl.tai.lineup.infrastructure.mongo;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
class MongoConfig extends AbstractMongoConfiguration {

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient("ds151141.mlab.com:51141/lineupdb");
    }

    @Override
    protected String getDatabaseName() {
        return "lineupdb";
    }
}