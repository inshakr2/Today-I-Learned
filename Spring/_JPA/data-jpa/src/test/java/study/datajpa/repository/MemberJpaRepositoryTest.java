package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() {
        String name = "chany";
        Member member = new Member(name);
        Member saveMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.findById(saveMember.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        Member findMember1 = memberJpaRepository.findById(member1.getId());
        Member findMember2 = memberJpaRepository.findById(member2.getId());
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        List<Member> all = memberJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        Long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);

        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);

        Long count2 = memberJpaRepository.count();
        assertThat(count2).isEqualTo(0);

    }

    @Test
    public void findByUsernameAndAgeGreaterThen() {
        Member member1 = new Member("A", 10);
        Member member2 = new Member("B", 20);
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        List<Member> result = memberJpaRepository.findByUsernameAndAgeGreaterThen("A", 15);

        assertThat(result.get(0).getUsername()).isEqualTo("A");
        assertThat(result.get(0).getAge()).isEqualTo(20);
    }

    @Test
    public void findByUsername() {
        Member member1 = new Member("A", 10);
        Member member2 = new Member("B", 20);
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        List<Member> result = memberJpaRepository.findByUsername("A");
        Member findMember = result.get(0);
        assertThat(findMember).isEqualTo(member1);
    }

    @Test
    public void testPaging() {
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 10));
        memberJpaRepository.save(new Member("member3", 10));
        memberJpaRepository.save(new Member("member4", 10));
        memberJpaRepository.save(new Member("member5", 10));
        memberJpaRepository.save(new Member("member6", 10));
        memberJpaRepository.save(new Member("member7", 10));

        int age = 10;
        int offset = 1;
        int limit = 3;

        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
        long totalCount = memberJpaRepository.totalCount(age);

        // 페이지 계산 공식 적용... totalPage = totalCount / size
        // 1 page : offset = 0 , limit = 10 / 2 page : offset = 10 , limit = 10 ...
        assertThat(members.size()).isEqualTo(3);
        assertThat(totalCount).isEqualTo(7);
    }

    @Test
    public void bulkUpdate() {
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 19));
        memberJpaRepository.save(new Member("member3", 11));
        memberJpaRepository.save(new Member("member4", 20));
        memberJpaRepository.save(new Member("member5", 21));
        memberJpaRepository.save(new Member("member6", 40));
        memberJpaRepository.save(new Member("member7", 33));

        int resultCount = memberJpaRepository.bulkAgePlus(20);

        assertThat(resultCount).isEqualTo(4);
    }

}