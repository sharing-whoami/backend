package whoami.core.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import whoami.core.domain.members.Members;
import whoami.core.domain.members.MembersRepository;
import whoami.core.security.JwtTokenProvider;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
public class MembersController {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MembersRepository membersRepository;

    // 회원가입
    @PostMapping("/users/signup") // 회원가입 api
    public Long joinMember(@RequestBody Map<String, String> user){
        return membersRepository.save(Members.builder()
                .userId(user.get("userId"))
                .password(user.get("password"))
                .name(user.get("name"))
                .registryNum(user.get("registryNum"))
                .phoneNum(user.get("phoneNum"))
                .email(user.get("email"))
                .role(user.get("role"))
                .profile(user.get("profile"))
                .build()).getId();
    }

    // 로그인
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
