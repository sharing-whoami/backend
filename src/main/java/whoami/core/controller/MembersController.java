package whoami.core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whoami.core.dto.MembersResponseDto;
import whoami.core.dto.MembersSaveRequestDto;
import whoami.core.dto.MembersUpdateRequestDto;
import whoami.core.service.MemberService;

@RequiredArgsConstructor
@RestController
public class MembersController {

    private final MemberService memberService;

    @PostMapping("/users/signup") // 회원가입 api
    public Long joinMember(@RequestBody MembersSaveRequestDto requestDto){
        return memberService.joinMember(requestDto);
    }

//    @GetMapping("/users/{id}") // 회원 조회
//    public MembersResponseDto findById(@PathVariable Long id){
//        return memberService.findById(id);
//    }

    @PutMapping("/users/{id}")     // 회원 수정
    public Long update(@PathVariable Long id, @RequestBody MembersUpdateRequestDto requestDto){
        return memberService.update(id,requestDto);
    }

//    public String joinMember(MembersSaveRequestDto membersDto){
//        memberService.joinMember(membersDto);
//        return "redirect:/login";
//    }

}
