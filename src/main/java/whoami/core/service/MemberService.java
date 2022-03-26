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
import whoami.core.dto.members.MembersResponseDto;
import whoami.core.dto.members.MembersSaveRequestDto;
import whoami.core.dto.members.MembersUpdateRequestDto;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {
    private final MembersRepository membersRepository;

    // 스프링시큐리티에서 유저를 찾는 메소드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return membersRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    // 회원가입
    @Transactional
    public Long joinMember(MembersSaveRequestDto requestDto) {
        validateDuplicateMember(requestDto); // 중복 회원 검증
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        requestDto.setRole(Role.USER.getValue());
        return membersRepository.save(requestDto.toEntity()).getId();
    }

    // 회원 조회
    @Transactional
    public MembersResponseDto findById(Long id){
        Members entity=membersRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다. id="+id));
        return new MembersResponseDto(entity);
    }

    // 회원 정보 수정
    @Transactional
    public Long update(Long id, MembersUpdateRequestDto requestDto){
        Members members=membersRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        members.update(requestDto.getPassword(),requestDto.getPhoneNum(),requestDto.getEmail(),requestDto.isReceiveNotification());
        return id;
    }

//     회원가입 아이디 중복체크
    @Transactional
    public void validateDuplicateMember(MembersSaveRequestDto membersDto){
        Optional<Members> findMember=membersRepository.findByUserId(membersDto.getUserId());
        if (findMember.isPresent()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

}
