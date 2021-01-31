package chany;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:/test.properties")
class SpringInitApplicationTest {

    @Autowired
    Environment environment;

    @Test
    public void contextLoad() {
        assertThat(environment.getProperty("chany.name"))
                .isEqualTo("Chang");
    }

    @Test
    public void contestLoad2() {
        assertThat(environment.getProperty("chany.age"))
                .isEqualTo("29");
    }

}