package whoami.core.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import whoami.core.domain.members.Members;
import whoami.core.domain.members.MembersRepository;
import whoami.core.dto.members.MembersSaveRequestDto;
import whoami.core.security.JwtTokenProvider;
import whoami.core.service.MemberService;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
public class MembersController {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MembersRepository membersRepository;
    private final MemberService memberService;

    // 회원가입 완료
    @PostMapping("/users/signup") // 회원가입 api
    public String joinMember(@RequestBody MembersSaveRequestDto requestDto){
        if (memberService.joinMember(requestDto)!=null){
            return "회원가입 완료"; // return "redirect:/login"; -> 페이지 이동
        }
            return "중복되는 아이디가 있습니다."; // return "redirect:/users/signup"; -> 페이지 이동
    }

    // 로그인 완료 -> 동작 확인함.
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        Members member = membersRepository.findByUserId(user.get("userId"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 회원아이디 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(member.getUserId(), member.getRole());
    }
//    private final MemberService memberService;
//
//    @PostMapping("/users/signup") // 회원가입 api
//    public Long joinMember(@RequestBody MembersSaveRequestDto requestDto){
//        return memberService.joinMember(requestDto);
//    }

//    @GetMapping("/users/{id}") // 회원 조회
//    public MembersResponseDto findById(@PathVariable Long id){
//        return memberService.findById(id);
//    }

//    @PutMapping("/users/{id}")     // 회원 수정
//    public Long update(@PathVariable Long id, @RequestBody MembersUpdateRequestDto requestDto){
//        return memberService.update(id,requestDto);
//    }


}
