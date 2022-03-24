package whoami.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import whoami.core.domain.members.Members;
import whoami.core.domain.members.MembersRepository;
import whoami.core.dto.MembersResponseDto;
import whoami.core.dto.MembersSaveRequestDto;
import whoami.core.dto.MembersUpdateRequestDto;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService { //implements UserDetailsService {
    private final MembersRepository membersRepository;

    @Transactional
    // 회원가입
    public Long joinMember(MembersSaveRequestDto requestDto){
        validateDuplicateMember(requestDto); // 중복 회원 검증

        return membersRepository.save(requestDto.toEntity()).getId();
//
//        public Long joinMember(MembersSaveRequestDto requestDto){
//            validateDuplicateMember(requestDto); // 중복 회원 검증
//            return membersRepository.save(requestDto.toEntity()).getId();
//        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
//        membersDto.setPassword(encoder.encode(membersDto.getPassword()));

    }

    // 회원가입 아이디 중복체크
    @Transactional
    public void validateDuplicateMember(MembersSaveRequestDto membersDto){
        Optional<Members> findMember=membersRepository.findByUserId(membersDto.getUserId());
        if (findMember.isPresent()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
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

}
