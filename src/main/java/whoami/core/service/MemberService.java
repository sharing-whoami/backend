package whoami.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whoami.core.domain.Role;
import whoami.core.domain.members.Members;
import whoami.core.domain.members.MembersRepository;
import whoami.core.dto.members.MembersSaveRequestDto;
import whoami.core.dto.members.MembersUpdateRequestDto;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {
    private final MembersRepository membersRepository;

    // 스프링 시큐리티에서 유저를 찾는 메소드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return membersRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다.1"));
    }

    // 회원가입 처리
    @Transactional
    public Long joinMember(MembersSaveRequestDto requestDto) {
        // 중복 회원 검증
        if (!validateDuplicateMember(requestDto)) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
            requestDto.setRole(Role.USER.getValue());
            return membersRepository.save(requestDto.toEntity()).getMemberId();
        }
        return null;
    }

    // 회원 조회
    @Transactional
    public Members findById(Long id){
        Members entity=membersRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다. id="+id));
        return entity;
    }

    // 회원 정보 수정
    @Transactional
    public Long update(Long id, MembersUpdateRequestDto requestDto){
        Members members=membersRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        members.update(requestDto.getPassword(),requestDto.getPhoneNum(),requestDto.getEmail(),requestDto.isReceiveNotification());
        return id;
    }

    // 회원가입 아이디 중복체크
    @Transactional
    public boolean validateDuplicateMember(MembersSaveRequestDto membersDto){
       return membersRepository.existsByUserId(membersDto.getUserId());
    }
}
