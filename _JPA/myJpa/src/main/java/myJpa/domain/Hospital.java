package myJpa.domain;

import javax.persistence.*;

@Entity
public class Hospital extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HOSPITAL_ID")
    private Long id;

    @Column(name = "HOSPITAL_NAME")
    private String name;
    @Column(name = "INSTITUTION_CODE")
    private String code;
    private String chiefName;

    public Hospital() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getChiefName() {
        return chiefName;
    }

    public void setChiefName(String chiefName) {
        this.chiefName = chiefName;
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", chiefName='" + chiefName + '\'' +
                '}';
    }
}
