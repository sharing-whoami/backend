/*package whoami.core.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
import whoami.core.domain.members.MembersRepository;
import whoami.core.dto.members.TokenDto;
import whoami.core.service.MemberService;
//import whoami.core.service.RedisUtil;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
// refresh token의 검증에는 ExpiredRefreshtokenService에서 만료된 토큰인지 확인하고
// 만료되지 않은 경우에만 validateToken메소드에 토큰 검증을 위임한다.
public class JwtTokenProvider { // JWT를 생성하고 검증하는 컴포넌트
    private String secretKey = "secret";
    private final MembersRepository membersRepository;
    private final MemberService memberService;
    private static final Logger logger= LoggerFactory.getLogger(JwtAuthenticationFilter.class);
//    private final RedisUtil redisUtil;

    // 토큰 유효시간 30분 -> 나중에 10분으로 바꿔야함.
    private final long access_tokenValidTime = 1000L * 30;  // 1000L * 60 * 30; // 30분
    private final long refresh_tokenValidTime = 1000L * 60 * 60 ;// 1000 * 60 * 60 * 14; // 2주

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    //JWT Access 토큰 생성
    public TokenDto createToken(String userPk, String roles) { // List<String> -> string
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위
        claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();
        String accessToken= Jwts.builder()
                .setHeader(headers) // header
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + access_tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();

        Claims rclaims = Jwts.claims();
        claims.put("role", roles);
        Date expiration = new Date(now.getTime() + refresh_tokenValidTime);
        String refreshToken= Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return new TokenDto(accessToken,refreshToken);
    }



    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        System.out.println("사용자 아이디 :"+ this.getUserPk(token) + "token : " + token);
        UserDetails userDetails = memberService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 claim 추출하기
    public Claims getClaims(String token){
        try{
            return Jwts
                    .parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 Access token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("access-token");
        System.out.println("Access token : "+ token);
        return token;
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        String token = null;
        Cookie cookie = WebUtils.getCookie(request, "refresh-token");
        if (cookie != null)
            token = cookie.getValue();
        return token;
    }

    // 토큰의 유효성 + 만료일자 확인
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
//            logger.info("validate 들어옴;");
//            if (redisUtil.hasKeyBlackList(token)){
//                System.out.println("이미 탈퇴한 회원입니다");
//            }
//            return true;
//        }
//        catch (Exception e) {
//            System.out.println("Error1 : " + e);
//        }
//        return false;
//    }

    // 어세스 토큰 헤더 설정
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("authorization", "bearer "+ accessToken);
    }

    // 리프레시 토큰 헤더 설정
    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader("refreshToken", "bearer "+ refreshToken);
    }

    // RefreshToken 존재유무 확인
//    public boolean existsRefreshToken(String refreshToken) {
//        return expiredRefreshTokenRepository.existsByToken(refreshToken);
//    }

    // Email로 권한 정보 가져오기
    public String getRoles(String userId) {
        return membersRepository.findByUserId(userId).get().getRole();
    }
//        if(expiredRefreshTokenService.isExpiredToken(jwtToken)) {
//            return false;
//        }
//        return validateToken(jwtToken);

}
*/

package whoami.core.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import whoami.core.domain.members.Members;
import whoami.core.domain.members.MembersRepository;
import whoami.core.service.RedisService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RequiredArgsConstructor
@Component
// refresh token의 검증에는 ExpiredRefreshtokenService에서 만료된 토큰인지 확인하고
// 만료되지 않은 경우에만 validateToken메소드에 토큰 검증을 위임한다.
public class JwtTokenProvider { // JWT를 생성하고 검증하는 컴포넌트
    private String secretKey = "secret";
    private final MembersRepository membersRepository;
    private final RedisService redisService;

    // 토큰 유효시간 30분 -> 나중에 10분으로 바꿔야함.
    private final long access_tokenValidTime = 1000L * 30;  // 1000L * 60 * 30; // 30분
    private final long refresh_tokenValidTime = 1000L * 60 * 60 ;// 1000 * 60 * 60 * 14; // 2주

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    //JWT Access 토큰 생성
    public String createToken(String userPk, String roles) { // List<String> -> string
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위
        claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();
        return Jwts.builder()
                .setHeader(headers) // header
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + access_tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }

    //JWT Refresh 토큰 생성
    public String createRefreshToken(String userPk, String roles) { // List<String> -> string
        Claims claims = Jwts.claims();
        claims.put("role", roles);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + refresh_tokenValidTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // 검증된 refresh token에서 subject를 추출하여, 새로운 액세스 토큰 발급


    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        System.out.println("사용자 아이디 :"+ this.getUserPk(token) + "token : " + token);
        Optional<Members> member = membersRepository.findByUserId(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(member,"",member.get().getAuthorities());
    }


    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveAccessToken(HttpServletRequest request) {
        System.out.println("resolveAccessToken : " + request.getHeader("accessToken"));
        String token = request.getHeader("accessToken");
        return token;

    }
    // Request의 Header에서 RefreshToken 값을 가져옵니다. "authorization" : "token'
    public String resolveRefreshToken(HttpServletRequest request) {
        System.out.println("resolveRefreshToken : " + request.getHeader("refreshToken"));
        String token=request.getHeader("refreshToken");
        return token;
//        if(request.getHeader("refreshToken") != null )
//            return request.getHeader("refreshToken").substring(7);
//        return null;
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
    public boolean validateRefreshToken(String jwtToken) {
        return validateToken(jwtToken);
    }

    // RefreshToken 존재유무 확인
    public boolean existsRefreshToken(String refreshToken) {
        return redisService.getValues(refreshToken) != null;
    }

    // 어세스 토큰 헤더 설정
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("accessToken", accessToken);
    }
//
    // 리프레시 토큰 헤더 설정
    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader("refreshToken", refreshToken);
    }

    // Email로 권한 정보 가져오기
    public String getRoles(String userId) {
        return membersRepository.findByUserId(userId).get().getRole();
    }
}

