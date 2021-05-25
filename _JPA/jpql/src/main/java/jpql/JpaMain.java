package jpql;

import jpql.DTO.MemberDTO;
import jpql.domain.Address;
import jpql.domain.Member;
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

            em.persist(member);

            em.flush();
            em.clear();

//            String query = "select m from Member m inner join m.team t";
//            String query = "select m from Member m left outer join m.team t";
            String query = "select (select avg(a.age) from Member a) from Member m";
            List<Member> result = em.createQuery(query, Member.class).getResultList();

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

