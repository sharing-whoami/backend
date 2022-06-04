package whoami.core.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import whoami.core.domain.guestbook.Guestbook;
import whoami.core.domain.guestbook.GuestbookRepository;
import whoami.core.domain.member.Member;
import whoami.core.domain.member.MemberRepository;
import whoami.core.dto.guestbook.GuestbookResponseDto;
import whoami.core.dto.guestbook.GuestbookSaveRequestDto;
import whoami.core.dto.guestbook.GuestbookUpdateRequestDto;
import whoami.core.exception.CustomMessage;
import whoami.core.exception.RecordNotFoundException;

import java.util.Optional;

@Service
public class GuestbookService {
    @Autowired
    private GuestbookRepository guestbookRepository;
    private MemberRepository memberRepository;

    @Autowired
    ModelMapper modelMapper;

    public Page<GuestbookResponseDto> findAll(Pageable pageable, Long memberId){
        Page<Guestbook> guestbooks = guestbookRepository.findAllByOwnerId(memberId, pageable);
        return guestbooks.map(this::mapGuestbookDomainToResponseDto);
    }

    public GuestbookResponseDto findByGuestbookId(Long guestbookId){
        if (guestbookRepository.existsById(guestbookId)) {
            Guestbook guestbook = guestbookRepository.findByGuestbookId(guestbookId);
            return modelMapper.map(guestbook,GuestbookResponseDto.class);
        } else {
            throw new RecordNotFoundException(CustomMessage.DOESNOT_EXIT + guestbookId);
        }
    }

    public GuestbookResponseDto updateGuestbook(GuestbookUpdateRequestDto guestbookUpdateRequestDto) {
        if (guestbookRepository.existsById(guestbookUpdateRequestDto.getGuestbookId())) {
            Guestbook guestbook = guestbookRepository.findByGuestbookId(guestbookUpdateRequestDto.getGuestbookId());
            guestbook.update(guestbookUpdateRequestDto.getContents());
            guestbook = guestbookRepository.save(guestbook);
            return modelMapper.map(guestbook, GuestbookResponseDto.class);
        } else {
            throw new RecordNotFoundException(CustomMessage.DOESNOT_EXIT + guestbookUpdateRequestDto.getGuestbookId());
        }
    }

    public void deleteByGuestbookId(Long guestbookId) {
        if (guestbookRepository.existsById(guestbookId)) {
            guestbookRepository.deleteById(guestbookId);
            return;
        } else {
            throw new RecordNotFoundException(CustomMessage.DOESNOT_EXIT + guestbookId);
        }
    }

    public GuestbookResponseDto createGuestbook(GuestbookSaveRequestDto guestbookSaveRequestDto) {
        try {
            // Get Writer
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<Member> writer = (Optional<Member>)principal;

            // Get Owner
            Optional<Member> owner= memberRepository.findByMemberId(guestbookSaveRequestDto.getOwnerId());

            Guestbook guestbook = new Guestbook(guestbookSaveRequestDto.getContents(), owner.get(), writer.get());
            guestbook = guestbookRepository.save(guestbook);
            return modelMapper.map(guestbook,GuestbookResponseDto.class);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(ex.getCause().getCause().getMessage());
        }
    }

    private GuestbookResponseDto mapGuestbookDomainToResponseDto(Guestbook guestbook) {
        return modelMapper.map(guestbook,GuestbookResponseDto.class);
    }
}
