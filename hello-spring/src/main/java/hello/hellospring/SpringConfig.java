package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    // 자바 코드로 직접 스프링 빈에 등록하기..
    // configuration 애노테이션을 만들고, Bean 애노테이션을 달아준다.
    // 스프링이 뜰 때,, Bean 애노테이션을 스프링 빈에 등록을 하고,
    // 그리고, 스프링 빈에 등록된 MemberRepository를 MemberService에 등록해준다.
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

}
