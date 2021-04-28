package hellojpa;

import hellojpa.embeded.Address;
import hellojpa.embeded.Period;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
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

            Address address = new Address("city", "street", "zip");

            Member member1 = new Member();
            member1.setUsername("m1");
            member1.setHomeAddres(address);
            em.persist(member1);

            Address newAddress = new Address("Another City", address.getStreet(), address.getZipcode());

            Member member2 = new Member();
            member2.setUsername("m1");
            member2.setHomeAddres(address);
            em.persist(member2);

//            member1.getHomeAddres().setCity("New City");

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
