package hello.hellospring;

import hello.hellospring.aop.timeTraceAop;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    // 자바 코드로 직접 스프링 빈에 등록하기..
    // configuration 애노테이션을 만들고, Bean 애노테이션을 달아준다.
    // 스프링이 뜰 때,, Bean 애노테이션을 스프링 빈에 등록을 하고,
    // 그리고, 스프링 빈에 등록된 MemberRepository를 MemberService에 등록해준다.
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

    //  AOP는 Spring Bean에 등록하는 것을 더 선호..
//    @Bean
//    public timeTraceAop tImeTraceAop() {
//        return new timeTraceAop();
//    }

//    @Bean
//    public MemberRepository memberRepository() {

        //return new MemoryMemberRepository();
        //return new JdbcMemberRepository(dataSource);
        //return new JdbcTemplateMemberRepository(dataSource);
        //return new JpaMemberRepository(em);
//    }



}
