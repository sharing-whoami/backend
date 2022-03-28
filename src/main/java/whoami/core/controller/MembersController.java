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
import whoami.core.service.ExpiredRefreshTokenService;
import whoami.core.service.MemberService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
public class MembersController {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MembersRepository membersRepository;
    private final MemberService memberService;
    private final ExpiredRefreshTokenService expiredRefreshTokenService;

    // 회원가입 완료
    @PostMapping("/users/signup") // 회원가입 api
    public String joinMember(@RequestBody MembersSaveRequestDto requestDto) {
        if (memberService.joinMember(requestDto) != null) {
            return "회원가입 완료"; // return "redirect:/login"; -> 페이지 이동
        }
        return "중복되는 아이디가 있습니다."; // return "redirect:/users/signup"; -> 페이지 이동
    }

    // 로그인 완료 -> 동작 확인함.
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> user, HttpServletRequest request,
                        HttpServletResponse response) {
        Members member = membersRepository.findByUserId(user.get("userId"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 회원아이디 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        String expiredToken = jwtTokenProvider.resolveRefreshToken(request);
        if (expiredToken != null && !expiredToken.isBlank()) {
            System.out.println("만료된 토큰 : "+ expiredToken);
            expiredRefreshTokenService.addExpiredToken(expiredToken);
        }

        String accessToken = jwtTokenProvider.createToken(member.getUsername(), member.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getUsername(), member.getRole());

        Cookie refreshTokenCookie = new Cookie("refresh-token", refreshToken);
        response.setHeader("access-token", accessToken);
        response.addCookie(refreshTokenCookie);
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", accessToken);
        map.put("refreshToken",refreshToken);
        return map;

    }
    @PostMapping("/users/test")
    public Map userResponseTest() {
        Map<String, String> result = new HashMap<>();
        result.put("result","user ok");
        return result;
    }

    @PostMapping("/admin/test")
    public Map adminResponseTest() {
        Map<String, String> result = new HashMap<>();
        result.put("result","admin ok");
        return result;
    }

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



