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

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity", "street", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHisory().add(new AddressEntity("old1", "street", "100"));
            member.getAddressHisory().add(new AddressEntity("old2", "street", "100"));
            member.getAddressHisory().add(new AddressEntity("old3", "street", "100"));
            member.getAddressHisory().add(new AddressEntity("old4", "street", "100"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("=============== START ===============");
            Member findMember = em.find(Member.class, member.getId());

//            findMember.setHomeAddress().setCity("bla"); 이런식은 안됨..
//            Address orgAddress = findMember.getHomeAddress();
//            findMember.setHomeAddress(
//                    new Address("New City", orgAddress.getStreet(), orgAddress.getZipcode())
//            );

            // Food, 치킨 -> 한식
//            findMember.getFavoriteFoods().remove("치킨");
//            findMember.getFavoriteFoods().add("한식");

            // Address history
//            findMember.getAddressHisory().remove(
//                    new Address("old1", "street", "100")
//            );
//            findMember.getAddressHisory().add(
//                    new Address("New City", "street", "100")
//            );

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
