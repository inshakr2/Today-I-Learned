package jpql;

import jpql.domain.Member;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        // Factory 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        // Manager 생성
        EntityManager em = emf.createEntityManager();

        // Transaction
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            Member member = new Member();
            member.setUsername("chany");
            member.setAge(10);
            em.persist(member);

            TypedQuery<Member> typeQuery = em.createQuery("select m from Member m", Member.class);
            List<Member> resultList = typeQuery.getResultList();
            Member singleResult = typeQuery.getSingleResult();

            // 이름 기준 바인딩
            em.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", "chany")
                    .getSingleResult();

            // 위치 기준 바인딩
            em.createQuery("select m from Member m where m.username = ?1")
                    .setParameter(1, "chany")
                    .getSingleResult();

            Query query = em.createQuery("select m.username, m.age from Member m");

            tx.commit();
        }catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }


}

