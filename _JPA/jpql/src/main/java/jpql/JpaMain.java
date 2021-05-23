package jpql;

import jpql.DTO.MemberDTO;
import jpql.domain.Address;
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

            em.flush();
            em.clear();

//            List<Member> result = em.createQuery("select m from Member m", Member.class)
//                    .getResultList();

//            Member findMember = result.get(0);
//            findMember.setAge(20);

//            em.createQuery("select o.address from Order o", Address.class)
//                    .getResultList();


//            em.createQuery("select a from Address a", Address.class)
//                    .getResultList();     임베디드 타입 직접 조회 불가능!

            List<Object[]> resultList = em.createQuery("select m.id, m.username, m.age from Member m")
                    .getResultList();

            Object[] res = resultList.get(0);

            List<MemberDTO> result = em.createQuery("select new jpql.DTO.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();

            MemberDTO memberDTO = result.get(0);
            System.out.println("memberDTO.getAge() = " + memberDTO.getAge());
            System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());


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

