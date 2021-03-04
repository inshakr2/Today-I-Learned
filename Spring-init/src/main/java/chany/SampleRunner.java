package chany;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleRunner implements ApplicationRunner {

    @Value("${chany.name}")
    String name;

    Logger logger = LoggerFactory.getLogger(SampleRunner.class);

    @Autowired
    private String hello;

    @Autowired
    private ChanyProperties chanyProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        logger.debug("===================================");
        logger.debug(hello);
        logger.debug(chanyProperties.getFullName());
        logger.debug(chanyProperties.getName());
        logger.debug(String.valueOf(chanyProperties.getAge()));
        logger.debug("===================================");

    }
}

