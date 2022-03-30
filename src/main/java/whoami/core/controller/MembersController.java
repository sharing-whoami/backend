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


