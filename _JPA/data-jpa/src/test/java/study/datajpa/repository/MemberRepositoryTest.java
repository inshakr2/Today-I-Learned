package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    public void testMember() {
        String name = "chany";
        Member member = new Member(name);
        Member saveMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(saveMember.getId()).get();
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        Long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        memberRepository.delete(member1);
        memberRepository.delete(member2);

        Long count2 = memberRepository.count();
        assertThat(count2).isEqualTo(0);


    }

    @Test
    public void findByUsernameAndAgeGreaterThen() {
        Member member1 = new Member("A", 10);
        Member member2 = new Member("B", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("A", 15);

        assertThat(result.get(0).getUsername()).isEqualTo("A");
        assertThat(result.get(0).getAge()).isEqualTo(20);
    }

    @Test
    public void findHello() {
        List<Member> top3HelloBy = memberRepository.findTop3HelloBy();
    }

    @Test
    public void findByUsername() {
        Member member1 = new Member("A", 10);
        Member member2 = new Member("B", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByUsername("A");
        Member findMember = result.get(0);
        assertThat(findMember).isEqualTo(member1);
    }

    @Test
    public void testQuery() {
        Member member1 = new Member("A", 10);
        Member member2 = new Member("B", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findUser("A", 10);
        assertThat(result.get(0)).isEqualTo(member1);
    }

    @Test
    public void testFindUsernameList() {
        Member member1 = new Member("A", 10);
        Member member2 = new Member("B", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<String> usernameList = memberRepository.findUsernameList();
        for (String s : usernameList) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void testFindMemberDto() {
        Team team = new Team("teamA");
        teamRepository.save(team);

        Member member1 = new Member("A", 10);
        member1.changeTeam(team);
        memberRepository.save(member1);

        List<MemberDto> memberDto = memberRepository.findMemberDto();
        for (MemberDto dto : memberDto) {
            System.out.println("dto = " + dto);
        }
    }

    @Test
    public void testFindByNames() {
        Member member1 = new Member("A", 10);
        Member member2 = new Member("B", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByNames(Arrays.asList("A", "B"));
        for (Member member : result) {
            System.out.println("member = " + member);
        }

    }

    @Test
    public void testReturnType() {
        Member member1 = new Member("A", 10);
        Member member2 = new Member("B", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> m1 = memberRepository.findListByUsername("A");
        Member m2 = memberRepository.findMemberByUsername("A");
        Optional<Member> m3 = memberRepository.findOptionalByUsername("A");

    }

    @Test
    public void testPaging() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));
        memberRepository.save(new Member("member6", 10));
        memberRepository.save(new Member("member7", 10));

        int age = 10;

        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
        // 파라미터를 넘길 때, Pageable 인터페이스 구현체를 넘기면 되는데 PageRequest를 많이 씀.
        Page<Member> page = memberRepository.findByAge(age, pageRequest);
        Page<MemberDto> memberDtoPage = page.map(m -> new MemberDto(m.getId(), m.getUsername(), m.getTeam().getName()));

        List<Member> content = page.getContent();
        long totalElements = page.getTotalElements();

        for (Member member : content) {
            System.out.println("member = " + member);
        }
        System.out.println("totalElements = " + totalElements);

        assertThat(content.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(7);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(3);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
    }

    @Test
    public void testSlicing() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));
        memberRepository.save(new Member("member6", 10));
        memberRepository.save(new Member("member7", 10));

        int age = 10;

        // Slice는 토탈 카운트 쿼리를 날리지 않고 입력한 size+1 으로 limit 쿼리를 날림.
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
        Slice<Member> page = memberRepository.findSliceByAge(age, pageRequest);

        List<Member> content = page.getContent();

        for (Member member : content) {
            System.out.println("member = " + member);
        }

        assertThat(content.size()).isEqualTo(3);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
    }

    @Test
    public void bulkUpdate() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 11));
        memberRepository.save(new Member("member4", 20));
        memberRepository.save(new Member("member5", 21));
        memberRepository.save(new Member("member6", 40));
        memberRepository.save(new Member("member7", 33));

        int resultCount = memberRepository.bulkAgePlus(20);
        em.flush();
        em.clear();

        assertThat(resultCount).isEqualTo(4);
    }
}