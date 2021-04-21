package myJpa;

import myJpa.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myjpa");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            // Hospital Data
            Hospital hospital = new Hospital();
            hospital.setName("GIL");
            hospital.setChiefName("Lee");

            save(em, hospital);

            // new Patient register
            Patient patient = new Patient();
            patient.setName("CY");
            patient.setGender(Gender.M);
            patient.setHospital(hospital);

            Visit visit = new Visit();
            visit.setVisitDate(LocalDateTime.now());
            visit.setHospital(hospital);
            visit.setVisitCode(VisitCode.VISITING);

            save(em, visit);
            Visit findVisit = em.find(Visit.class, visit.getId());

            patient.register(findVisit);

            System.out.println("======================");
            save(em, patient);
            System.out.println("======================");

            Patient findPatient = em.find(Patient.class, patient.getId());
            System.out.println(findPatient.toString());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void save(EntityManager em, Object entity) {

        em.persist(entity);
        em.flush();
        em.clear();

    }
}
