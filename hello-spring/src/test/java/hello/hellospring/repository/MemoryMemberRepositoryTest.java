package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

// 관례적으로 Test 하고자 하는 Class 이름 + Test
public class MemoryMemberRepositoryTest {

    MemberRepository repository = new MemoryMemberRepository();

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
}
