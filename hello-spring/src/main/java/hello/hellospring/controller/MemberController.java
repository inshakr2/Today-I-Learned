package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    // 이 상태로 실행하면 오류.
    // 스프링컨테이너에서 해당 MemberService를 가져오는데,
    // Controller는 스프링이 뜰 때 등록이 되지만,
    // MemberService는 스프링이 알 수 있는 방법이 없다.
}
