package jpql;

import jpql.DTO.MemberDTO;
import jpql.domain.Address;
import jpql.domain.Member;
import jpql.domain.MemberType;
import jpql.domain.Team;

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

            Team team = new Team();
            team.setName("team");
            em.persist(team);

            Member member = new Member();
            member.setUsername("chany");
            member.setAge(20);
            member.changeTeam(team);
            member.setMemberType(MemberType.ADMIN);

            em.persist(member);

            em.flush();
            em.clear();

//            String query = "select m.username, 'HE''LLO', TRUE FROM Member m " +
//                    "where m.memberType = jpql.domain.MemberType.ADMIN";

            String query = "select m.username, 'HE''LLO', TRUE FROM Member m " +
                    "where m.memberType = :type";

            em.createQuery(query)
                    .setParameter("type", MemberType.ADMIN)
                    .getResultList();

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

