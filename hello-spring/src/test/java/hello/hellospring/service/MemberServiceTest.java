package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    // MemberService 에서 사용하는 MemoryMemberRepository, 현재 여기서 사용하는 MemoryMemberRepository가
    // 현재 다른 instance에서 생성된다. 물론 여기서는 MemberService에서 static으로 사용중이라 문제는 안되지만,
    // 조심해야한다. ( 다른 Repository를 이용중 )
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    // MemberService입장에서 외부에서 MemberRepositry를 넣어주는 형태, 직접 new 하지 않고.
    // Dependency Injection (DI)
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }


    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    // Test 코드 작성은 한글로 바꿔도 무관하다..
    // Build시에 실제로 포함되지 않는다.
    void 회원가입() {
        // Test 할 때 아래 given , when, then 순으로 하면 Test 코드가 길어져도 보기 편하다.
        // given
        Member member = new Member();
        member.setName("hello");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    // 의도한 예외가 잘 터지는지 보는것도 중요하다.
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("except");

        Member member2 = new Member();
        member2.setName("except");

//        //when
//        memberService.join(member1);
//        try {
//            memberService.join(member2);
//            // Build 실패 유도
//            fail();
//        }
//        // then
//        // 그러나 try - catch 문법쓰기면 오류를 보기 힘들다.
//         catch (IllegalStateException e) {
//           assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");


    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}