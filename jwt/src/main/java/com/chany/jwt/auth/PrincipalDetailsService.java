package com.chany.jwt.auth;

import com.chany.jwt.model.User;
import com.chany.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 원래는 Security 설정에서 loginProcessingUrl (기본 /login) 요청 시 아래 메서드가 자동으로 실행되나
    // JWT에서는 formLogin 을 disable 해두었기 때문에 별도의 설정이 필요함.
    //   => JwtAuthenticationFilter

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userEntity = userRepository.findByUsername(username);
        return new PrincipalDetails(userEntity);
    }
}
