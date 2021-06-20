package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        // 엔티티는 수정 될 수있다.. 이렇게 엔티티 자체를 반환하면, 엔티티가 수정될 경우 api 스펙이 틀려진다.
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result membersV2() {
        // 위 V1과는 다르게, 출력해주고자 하는 필드를 가지고있는 DTO에 값을 담아 출력한다.
        // 그럼, 엔티티가 수정되어도 api스펙은 바뀌지 않는다. 그리고, 필드명이 바뀌면 컴파일 시점에 오류가 나기 때문에 유지보수에도 good
        List<Member> findMembers = memberService.findMembers();
        List<MembersDto> collect = findMembers.stream()
                                        .map(m -> new MembersDto(m.getName()))
                                        .collect(Collectors.toList());
        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        // response body에 data라는 필드로 한번 감싸줌.
        // 수정 및 관리할 때 json 스펙이 깨지지 않도록해줌.
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MembersDto {
        private String name;
    }


    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        // 조회 api와 마찬가지로, 엔티티가 수정될 경우 이 api 스펙 자체가 바뀌므로 이런식으로 엔티티에 직접적으로 값을 받아선 안된다.
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        // V1과는 다르게 DTO로부터 값을 받아서 새로운 Member 엔티티를 생성하여 값을 세팅하여 준다.
        // 지금은 예시로 하나의 값을 set하였지만, 실제로 사용할 때는 등록용 메서드를 하나 생성하여 값을 넣어주도록 하자.
        // (엔티티의 setter는 왠만해선 열어두지 않는 것이 좋다.)
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request) {
        // 수정하고자 하는 값을 DTO로 받아서 값을 수정해주는 간단한 로직이지만,
        // 컨트롤러 레벨에서는 식별자만 넘기고, 서비스 레벨에서 비지니스 로직을 수행하도록 한다.
        // 서비스 레벨에서는 트랜잭션 안에서 움직이기 때문에, 외부의 다른 객체가 넘어오면 이 트랜잭션과 관계가 없어 애매해진다.
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);

        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class CreateMemberRequest {
        @NotEmpty
        String name;
    }


    @Data
    @AllArgsConstructor
    static class CreateMemberResponse {
        private Long id;
    }
}
