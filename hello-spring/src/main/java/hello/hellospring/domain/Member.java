package hello.hellospring.domain;

import javax.persistence.*;

@Entity
public class Member {

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
                            // DB에서 생성되는 PK id 값 사용
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name="username") 만약 DB 컬럼명이 username인경우 @Column을 통해 맵핑
    private String name;

}
