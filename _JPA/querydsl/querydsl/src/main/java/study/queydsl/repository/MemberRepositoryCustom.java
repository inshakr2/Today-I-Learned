package study.queydsl.repository;

import study.queydsl.dto.MemberSearchCondition;
import study.queydsl.dto.MemberTeamDto;

import java.util.List;

public interface MemberRepositoryCustom {
    List<MemberTeamDto> search(MemberSearchCondition condition);
}
