package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {


    @Override
    Optional<Member> findByName(String name);
    // interface만 있으면 Spring Data Jpa가 자동으로 구현체를 만들어서 Spring Bean에 등록.
    // 이걸 가져다 쓰면된다!
    // Method 이름만으로 조회 기능 제공
}
