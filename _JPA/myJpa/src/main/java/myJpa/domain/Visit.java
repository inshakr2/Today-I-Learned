package myJpa.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Visit extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VISIT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PATIENT_ID")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "HOSPITAL_ID")
    private Hospital hospital;

    private LocalDateTime visitDate;
    @Enumerated(value = EnumType.STRING)
    private VisitCode visitCode;

    public Visit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public LocalDateTime getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDateTime visitDate) {
        this.visitDate = visitDate;
    }

    public VisitCode getVisitCode() {
        return visitCode;
    }

    public void setVisitCode(VisitCode visitCode) {
        this.visitCode = visitCode;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", visitDate=" + visitDate +
                ", visitCode=" + visitCode +
                '}';
    }
}
