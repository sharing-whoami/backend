package whoami.core.service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import whoami.core.domain.Role;
import whoami.core.domain.member.Member;
import whoami.core.domain.member.MemberRepository;
import whoami.core.dto.member.*;
import whoami.core.error.Response;
import whoami.core.security.JwtTokenProvider;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;
    private final Response response;

    // NOTE : 스프링 시큐리티에서 유저를 찾는 메소드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    // NOTE : 회원가입 처리
    @Transactional
    public ResponseEntity<? extends Object> joinMember(MemberSaveRequestDto requestDto) {
        if (!validateDuplicateMember(requestDto)) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));

            if (Objects.equals(requestDto.getRole(), "ADMIN")){
                requestDto.setRole(Role.ADMIN.getValue());
            } else{
                requestDto.setRole(Role.USER.getValue());
            }

            memberRepository.save(requestDto.toEntity());
            return response.success(new MemberResponseDto(requestDto.toEntity()),"회원가입이 완료되었습니다.",HttpStatus.CREATED);
        }
        return response.fail("이미 회원가입된 이메일입니다.", HttpStatus.CONFLICT);

    }

    // NOTE : 로그인
    @Transactional
    public ResponseEntity<?> loginUser(LoginRequestDto requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        Optional<Member> members = memberRepository.findByUserId(requestDto.getUserId());
        if (memberRepository.findByUserId(requestDto.getUserId()).isEmpty()){
            return response.fail("존재하지 않는 아이디입니다.",HttpStatus.UNAUTHORIZED);
        }
        if (!passwordEncoder.matches(requestDto.getPassword(), members.get().getPassword())) {
            return response.fail("비밀번호가 틀립니다.", HttpStatus.UNAUTHORIZED);
        }

        String accessToken = jwtTokenProvider.createToken(requestDto.getUserId(),members.get().getRole());
        String refreshToken=jwtTokenProvider.createRefreshToken(requestDto.getUserId(),members.get().getRole());

        redisService.setValues(requestDto.getUserId(),refreshToken,jwtTokenProvider.getExpiration(refreshToken));
        return response.success(new LoginResponseDto(accessToken,refreshToken), "로그인에 성공했습니다.", HttpStatus.CREATED);

    }

    // NOTE : 토큰 재발급
    @Transactional
    public ResponseEntity<? extends Object> reissue(ReissueTokenRequestDto requestDto){
        if (!jwtTokenProvider.validateToken(requestDto.getRefreshToken())) {
            return response.fail("Refresh Token 정보가 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(requestDto.getAccessToken());
        String userId = jwtTokenProvider.getUserPk(requestDto.getAccessToken());
        boolean validateRefreshToken = jwtTokenProvider.validateToken(requestDto.getRefreshToken());
        String isRefreshToken = redisService.getValues(userId);

        if (!validateRefreshToken && isRefreshToken==null){
            return response.fail("잘못된 요청입니다.",HttpStatus.BAD_REQUEST);
        }

        if(ObjectUtils.isEmpty(isRefreshToken)) {
            return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }
        if(!redisService.getValues(userId).equals(requestDto.getRefreshToken())){
            return response.fail("Refresh Token 정보가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        String roles = jwtTokenProvider.getRoles(userId);
        String newAccessToken = jwtTokenProvider.createToken(userId, roles);
        String newRefreshToken=jwtTokenProvider.createRefreshToken(userId,roles);
        redisService.setValues(userId, newRefreshToken, jwtTokenProvider.getExpiration(newRefreshToken));
        return response.success(new ReissueTokenResponseDto(newAccessToken,newRefreshToken), "Token 정보가 갱신되었습니다.", HttpStatus.CREATED);
    }

    // NOTE : 로그아웃
    @Transactional
    public ResponseEntity<? extends Object> logout(LogoutRequestDto requestDto) {
        String accessToken=requestDto.getAccessToken();
        String refreshToken=requestDto.getRefreshToken();
        String userId=jwtTokenProvider.getUserPk(accessToken);

        if (accessToken==null && refreshToken==null){
            return response.fail("입력값이 올바르지 않습니다.",HttpStatus.BAD_REQUEST);
        }

        if (!jwtTokenProvider.validateToken(accessToken)) {
            return response.fail("만료된 액세스 토큰입니다.", HttpStatus.UNAUTHORIZED);
        }

        if (redisService.getValues(userId)!=null){
            redisService.delValues(userId);
        }

        SecurityContextHolder.clearContext();
        redisService.setValues(accessToken,"logout",jwtTokenProvider.getExpiration(accessToken));
        return response.success(Collections.EMPTY_LIST,"로그아웃 되었습니다.",HttpStatus.CREATED);
    }

    // NOTE : 회원 조회
    @Transactional
    public Member findById(Long id){
        Member entity= memberRepository.findById(id).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다. id="+id));
        return entity;
    }

    // NOTE : 회원 리스트 조회
    @Transactional
    public List<Member> listMembers(){
        List<Member> members= memberRepository.findAll();
        return members;
    }

    // NOTE : 회원 정보 수정
    @Transactional
    public ResponseEntity<? extends Object>  updateMember(MemberUpdateRequestDto requestDto){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Optional<Member> nowMember = Optional.empty();
            if (authentication.getPrincipal() instanceof Optional) {
                nowMember = (Optional<Member>) authentication.getPrincipal();
            }
            Member member = memberRepository.findByUserId(nowMember.get().getUserId()).get();
            String nowMemberId=member.getUserId();

            if (memberRepository.findByUserId(nowMemberId).isEmpty()) {
                return response.fail("존재하지 않는 회원입니다.", HttpStatus.BAD_REQUEST);
            }
            else{
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
                member.update(requestDto.getPassword(),requestDto.getPhoneNum(),requestDto.getEmail(),requestDto.isReceiveNotification());
                return response.success(Collections.EMPTY_LIST,"회원 정보 수정이 완료되었습니다",HttpStatus.CREATED);
            }
        }catch (Exception e){
            return response.fail(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    // NOTE : 회원가입 아이디 중복체크
    @Transactional
    public boolean validateDuplicateMember(MemberSaveRequestDto membersDto){
        return memberRepository.existsByUserId(membersDto.getUserId());
    }

    // NOTE : 회원 탈퇴
    @Transactional
    public ResponseEntity<? extends Object> deleteMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Member> principal = Optional.empty();
        if (authentication.getPrincipal() instanceof Optional) {
            principal = (Optional<Member>) authentication.getPrincipal();
        }
        Member nowMember = memberRepository.findByUserId(principal.get().getUserId()).get();
        Optional<Member> member= memberRepository.findByUserId(principal.get().getUserId());

        try {
            if (!member.isPresent()){
                return response.fail("없는 회원입니다.", HttpStatus.BAD_REQUEST);
            }
            else{
                member.ifPresent(selectUser -> {
                    memberRepository.delete(selectUser);
                });
                return response.success(Collections.EMPTY_LIST,"회원탈퇴 완료되었습니다.",HttpStatus.OK);
            }

        }catch (Exception e){
            return response.fail(e.toString(),HttpStatus.CONFLICT);
        }
    }
}
