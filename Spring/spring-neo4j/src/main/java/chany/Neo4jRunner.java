package chany;

import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.internal.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletionStage;

@Component
public class Neo4jRunner implements ApplicationRunner {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        SessionFactory session = this.sessionFactory;
        session.newInstance(SessionConfig.builder().build());

        session.close();
    }
}
