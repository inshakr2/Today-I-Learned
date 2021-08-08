package study.queydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@Getter
@NoArgsConstructor
@Data
public class MemberDto {

    private String username;
    private int age;

    @QueryProjection
    public MemberDto(String username, int age) {
        this.username = username;
        this.age = 10000*age;
    }

    public MemberDto(int age, String username) {
        this.username = username;
        this.age = age;
    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public void setAge(int age) {
//        this.age = age * 100;
//    }
//
    @Override
    public String toString() {
        return "MemberDto{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
