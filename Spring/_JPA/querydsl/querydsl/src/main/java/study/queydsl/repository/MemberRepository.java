package study.queydsl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import study.queydsl.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>,
                                        MemberRepositoryCustom,
                                        QuerydslPredicateExecutor<Member> {

    List<Member> findByUsername(String username);

}
