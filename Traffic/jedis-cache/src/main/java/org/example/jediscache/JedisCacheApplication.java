package org.example.jediscache;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing
public class JedisCacheApplication implements ApplicationRunner {

    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(JedisCacheApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userRepository.save(User.builder().name("chany").email("chany@email.com").build());
        userRepository.save(User.builder().name("enzo").email("enzo@email.com").build());
        userRepository.save(User.builder().name("sam").email("sam@email.com").build());
        userRepository.save(User.builder().name("oliver").email("oliver@email.com").build());
        userRepository.save(User.builder().name("cris").email("cris@email.com").build());
    }
}
