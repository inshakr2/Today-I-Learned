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



            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();

            System.out.println("member1.getAge() = " + member1.getAge()); // 0
            System.out.println("member2.getAge() = " + member2.getAge()); // 0
            System.out.println("member3.getAge() = " + member3.getAge()); // 0


            Member ex_member = em.find(Member.class, member1.getId());
            System.out.println("ex_member.getAge() = " + ex_member.getAge()); // 0

            em.clear();

            Member aft_member = em.find(Member.class, member1.getId());
            System.out.println("aft_member.getAge() = " + aft_member.getAge()); // 20


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

