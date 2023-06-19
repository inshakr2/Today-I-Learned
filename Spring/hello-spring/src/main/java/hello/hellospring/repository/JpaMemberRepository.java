package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }
    // EntityManager --- spring boot가 em을 만들어주는데,, 요거를 injection받으면 된다.


    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name= :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();

    }

    @Override
    public List<Member> findAll() {     // 등록한 Entity에서 조회
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

}
