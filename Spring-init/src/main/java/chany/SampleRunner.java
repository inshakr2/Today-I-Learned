package chany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleRunner implements ApplicationRunner {

    @Autowired
    ChanyProperties chanyProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        System.out.println("===================");
        System.out.println("Full Name = " + chanyProperties.getFullName());
        System.out.println("Name = " + chanyProperties.getName());
        System.out.println("age = " + chanyProperties.getAge());
        System.out.println("===================");
    }
}
