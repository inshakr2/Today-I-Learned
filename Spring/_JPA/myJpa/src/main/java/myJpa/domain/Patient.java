package myJpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Patient extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PATIENT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "HOSPITAL_ID")
    private Hospital hospital;

    @OneToMany(mappedBy = "patient")
    private List<Visit> visits = new ArrayList<>();

    @Column(name = "PATIENT_NAME")
    private String name;
    @Column(name = "PATIENT_CODE")
    private String code;
    private Gender gender;
    @Column(nullable = true)
    private String birth;
    @Column(nullable = true)
    private String phoneNumber;

    public void register(Visit visit) {
        visits.add(visit);
        visit.setPatient(this);
    }

    public Patient() {
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", hospital=" + hospital +
                ", visits=" + visits +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", gender=" + gender +
                ", birth='" + birth + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
