package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

// 관례적으로 Test 하고자 하는 Class 이름 + Test
public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    // Test는 작성 순서에 의존하지 않는다,
    // Test Case에서 동일한 이름으로 객체를 생성했을 경우, Test 이후 객체가 삭제되도록 해야한다.
    // Method가 끝난 후 어떤 동작을 하도록 하는 Callback Method
    // Test는 서로 의존관계가 없이 설계가 되어야 한다 !
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    // Test Annotator 생성하면, 해당 Method Test 가능
    @Test
    public void save() {
        Member member = new Member();
        member.setName("Chany");
        repository.save(member);
                                // optional.get() 은 좋지 않지만, 우선 Test 용이니까..
        Member res = repository.findById(member.getId()).get();

        // case 1
        Assertions.assertEquals(res, member);
                                // (Expected, Actual)

        // case 2 .. 가독성은 assertThat이 더 좋음.
        assertThat(member).isEqualTo(res);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("Chany");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("Chang yeol");
        repository.save(member2);

        Member res = repository.findByName("Chany").get();

        assertThat(res).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("Chany");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("chang yeol");
        repository.save(member2);

        List<Member> res = repository.findAll();
        assertThat(res.size()).isEqualTo(2);
    }
}
