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

            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.changeTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.changeTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("member3");
            member3.changeTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            String query1 =
                    "SELECT m " +
                    "FROM Member m " +
                    "WHERE m = :member";
            String query2 =
                    "SELECT m " +
                    "FROM Member m " +
                    "WHERE m.id = :memberId";
            String query3 =
                    "SELECT m " +
                    "FROM Member m " +
                    "WHERE m.team = :team";


            em.createQuery(query1)
                    .setParameter("member", member1)
                    .getResultList();

            em.createQuery(query2)
                    .setParameter("memberId", member1.getId())
                    .getResultList();

            em.createQuery(query3)
                    .setParameter("team", teamA)
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

