package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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

            // 비영속
            Member member = new Member();
            member.setId(3L);
            member.setName("Hello JPA world");

            // 영속
            System.out.println("=== BEFORE ===");
            em.persist(member);
            System.out.println("=== AFTER ===");


            tx.commit();
        }catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
