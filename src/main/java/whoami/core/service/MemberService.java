package whoami.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whoami.core.domain.members.Members;
import whoami.core.domain.members.MembersRepository;
import whoami.core.dto.MembersSaveRequestDto;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService { //implements UserDetailsService {
    private final MembersRepository membersRepository;

    @Transactional
    // 회원가입
    public Long joinMember(MembersSaveRequestDto requestDto){
        validateDuplicateMember(requestDto); // 중복 회원 검증
//        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
//        membersDto.setPassword(encoder.encode(membersDto.getPassword()));
        return membersRepository.save(requestDto.toEntity()).getId();
    }

    // 회원가입 아이디 중복체크
    @Transactional
    public void validateDuplicateMember(MembersSaveRequestDto membersDto){
        Optional<Members> findMember=membersRepository.findByUserId(membersDto.getUserId());
        if (findMember.isPresent()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
