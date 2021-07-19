package study.datajpa.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.datajpa.dto.MemberDto;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {
    private final EntityManager em;

    List<MemberDto> findMemberDto() {
        return em.createQuery(
                "SELECT new study.datajpa.dto.MemberDto(m.id, m.name, t.name) " +
                        "FROM Member m JOIN m.team t").getResultList();
    }
}
