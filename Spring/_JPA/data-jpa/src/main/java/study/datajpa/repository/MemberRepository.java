package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom, JpaSpecificationExecutor<Member> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findTop3HelloBy();

    //    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

    @Query("SELECT m FROM Member m WHERE m.username = :username AND m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("SELECT m.username FROM Member m")
    List<String> findUsernameList();

    @Query("SELECT new study.datajpa.dto.MemberDto(m.id, m.username, t.name) FROM Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("SELECT m FROM Member m WHERE m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    List<Member> findListByUsername(String username); // 컬렉션

    Member findMemberByUsername(String username); // 단건

    Optional<Member> findOptionalByUsername(String username); // optional 단건

    @Query(value = "SELECT m FROM Member m LEFT JOIN m.team t",
            countQuery = "SELECT COUNT(m.username) FROM Member m"
    )
    Page<Member> findByAge(int age, Pageable pageable);

    Slice<Member> findSliceByAge(int age, Pageable pageable);

    // Bulk
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.age = m.age + 1 WHERE m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    // EntityGraph
    @Query("SELECT m FROM Member m JOIN FETCH m.team")
    List<Member> findMemberFetchJoin();


    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("SELECT m FROM Member m")
    List<Member> findMemberEntityGraph();

    //    @EntityGraph(attributePaths = ("team"))
    @EntityGraph("Member.all")
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    // hint
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    // select for update
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);

    //List<UsernameOnly> findProjectionsByUsername(@Param("username") String username);

//    동적 Projection
    <T> List<T> findProjectionsByUsername(@Param("username") String username, Class<T> type);

    // Native Query
    @Query(
            value = "SELECT * FROM member WHERE username = ?",
            nativeQuery = true
    )
    Member findByNativeQuery(String username);

    @Query(
            value = "SELECT m.member_id as id, m.username, t.name as teamName " +
                    "FROM member m LEFT JOIN team t",
            countQuery = "SELECT COUNT(*) FROM member",
            nativeQuery = true
    )
    Page<MemberProjection> findByNativeProjection(Pageable pageable);

}

