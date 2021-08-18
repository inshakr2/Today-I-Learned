package study.queydsl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.queydsl.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsername(String username);
}
