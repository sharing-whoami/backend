package whoami.core.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import whoami.core.dto.members.LoginRequestDto;
import whoami.core.dto.members.LoginResponseDto;
import whoami.core.dto.members.MembersSaveRequestDto;
import whoami.core.security.JwtTokenProvider;
import whoami.core.service.MemberService;
import whoami.core.service.RedisService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MembersController {
    private final MemberService memberService;
    private final RedisService redisService;
    private final JwtTokenProvider jwtTokenProvider;

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
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto, HttpServletRequest request,
                                   HttpServletResponse response){
        LoginResponseDto loginResponseDto = memberService.loginUser(requestDto);
        if (loginResponseDto!=null){
            response.setHeader("accessToken", loginResponseDto.getAccessToken());
            response.setHeader("refreshToken",loginResponseDto.getRefreshToken());
            return ResponseEntity.ok(loginResponseDto);
        }else{
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }
    // 로그아웃
    @GetMapping("/api/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        System.out.println("logout : " + request.getHeader("refreshToken"));
        redisService.delValues(request.getHeader("refreshToken"));
        return ResponseEntity.ok().body("로그아웃 성공!");
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

/*
* package whoami.core.controller;

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
import java.io.IOException;
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
            System.out.println("현재 토큰 : "+ expiredToken);
             expiredRefreshTokenService.addExpiredToken(expiredToken); // 재발급이 필요함.
        }

        String accessToken = jwtTokenProvider.createToken(member.getUsername(), member.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getUsername(), member.getRole());

        Cookie refreshTokenCookie = new Cookie("refresh-token", refreshToken);
        response.setHeader("access-token", accessToken);
        response.addCookie(refreshTokenCookie);
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", accessToken);
        map.put("refreshToken",refreshToken);
        System.out.println("accessToken3 : "+ accessToken);
        System.out.println("refreshToken3 : " + refreshToken);
        return map;
    }

    @PostMapping("/refresh")
    public String refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader=jwtTokenProvider.resolveToken((HttpServletRequest) request);

        return "ok";
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




* */



