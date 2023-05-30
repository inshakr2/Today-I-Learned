package com.chany.security1.config.auth;

import com.chany.security1.model.User;
import com.chany.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * Security 설정에서 loginProcessingUrl에 설정한 경로로 요청이 오면 (로그인 요청)
 * 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행
 *
 */
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username 파라미터는 실제 프론트의 Form에 명시된 name 값과 맵핑되기 때문에 주의해야 함.
        // 만약 변경이 필요하다면 SecurityConfig 에서 usernameParameter 체인을 추가하여 설정 변경해주어야 함.

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("username not found")
        );

        // 이 리턴되는 UserDetails 타입의 객체는 Authentication 객체에 들어가고,
        // 추후 Authentication 객체는 Security Session에 들어간다.
        return new PrincipalDetails(user);
    }
}
