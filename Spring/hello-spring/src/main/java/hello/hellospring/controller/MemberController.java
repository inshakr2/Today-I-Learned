package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    // Dependency Injection : 생성자 주입
    private final MemberService memberService;

//  Dependency Injection : 필드 주입 (비추천)
//   : 바꿀 수 있는 방법이 없다. (?)
//  @Autowired private MemberService memberService;

//  Dependency Injection : Setter 주입
//  누군가가 호출했을 때 public으로 열려있어야한다. -> setting이 끝난후 바꿀일은 없다.
//    @Autowired
//    public void setMemberService(MemberService memberService) {
//       this.memberService = memberService;
//    }
//  의존관계가 실행중에 동적으로 변하는 경우는 없기 때문에 생성자 주입을 권장한다.

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
