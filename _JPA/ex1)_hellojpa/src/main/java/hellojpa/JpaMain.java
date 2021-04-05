package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory em = Persistence.createEntityManagerFactory("hello");

        EntityManager entityManager = em.createEntityManager();
        entityManager.close();
        em.close();
    }
}
