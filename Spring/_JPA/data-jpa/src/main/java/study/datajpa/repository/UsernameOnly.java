package study.datajpa.repository;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {

    // CloseProjection
    String getUsername();

    // OpenProjection
    @Value("#{target.username + ' ' + target.age}")
    String getUsernameWithAge();
}
