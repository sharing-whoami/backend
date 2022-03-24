package whoami.core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import whoami.core.dto.MembersSaveRequestDto;
import whoami.core.service.MemberService;

@RequiredArgsConstructor
@RestController
public class MembersController {

    private final MemberService memberService;

    @PostMapping("/users/signup") // 회원가입 api
    public Long joinMember(@RequestBody MembersSaveRequestDto requestDto){
        return memberService.joinMember(requestDto);
    }

//    public ResponseEntity<MembersResponseDto> joinMember(@RequestBody MembersSaveRequestDto requestDto){
//        memberService.joinMember(requestDto);
//        return new ResponseEntity<MembersResponseDto>(HttpStatus.CREATED);
//        // 201 : Created
//    }
//    public String joinMember(MembersSaveRequestDto membersDto){
//        memberService.joinMember(membersDto);
//        return "redirect:/login";
//    }

}
