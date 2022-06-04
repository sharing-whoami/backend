package whoami.core.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import whoami.core.domain.guestbookComment.GuestbookComment;
import whoami.core.domain.guestbookComment.GuestbookCommentRepository;
import whoami.core.domain.member.Member;
import whoami.core.dto.guestbook.GuestbookResponseDto;
import whoami.core.dto.guestbookComment.GuestbookCommentResponseDto;
import whoami.core.dto.guestbookComment.GuestbookCommentSaveRequestDto;
import whoami.core.dto.guestbookComment.GuestbookCommentUpdateRequestDto;
import whoami.core.exception.CustomMessage;
import whoami.core.exception.RecordNotFoundException;

import java.util.Optional;

@Service
public class GuestbookCommentService {
    @Autowired
    private GuestbookCommentRepository guestbookCommentRepository;

    @Autowired
    ModelMapper modelMapper;

    public GuestbookCommentResponseDto findByGuestbookCommentId(Long guestbookCommentId){
        if (guestbookCommentRepository.existsById(guestbookCommentId)) {
            GuestbookComment guestbookComment =
                    guestbookCommentRepository.findByGuestbookCommentId(guestbookCommentId);
            return modelMapper.map(guestbookComment,GuestbookCommentResponseDto.class);
        } else {
            throw new RecordNotFoundException(CustomMessage.DOESNOT_EXIT + guestbookCommentId);
        }
    }

    public GuestbookCommentResponseDto createGuestbookComment(GuestbookCommentSaveRequestDto guestbookCommentSaveRequestDto) {
        try {
            // Get writer
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<Member> writer = (Optional<Member>)principal;

            GuestbookComment guestbookComment
                    = new GuestbookComment (guestbookCommentSaveRequestDto.getGuestbookId(),
                    guestbookCommentSaveRequestDto.getContents(), writer.get());
            guestbookComment = guestbookCommentRepository.save(guestbookComment);
            return modelMapper.map(guestbookComment, GuestbookCommentResponseDto.class);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(ex.getCause().getCause().getMessage());
        }
    }

    public void deleteByGuestbookCommentId(Long guestbookCommentId) {
        if (guestbookCommentRepository.existsById(guestbookCommentId)) {
            guestbookCommentRepository.deleteById(guestbookCommentId);
            return;
        } else {
            throw new RecordNotFoundException(CustomMessage.DOESNOT_EXIT + guestbookCommentId);
        }
    }

    public GuestbookResponseDto updateGuestbookComment(GuestbookCommentUpdateRequestDto guestbookCommentUpdateRequestDto) {
        if (guestbookCommentRepository.existsById(guestbookCommentUpdateRequestDto.getGuestbookCommentId())) {
            GuestbookComment guestbookComment = guestbookCommentRepository.findByGuestbookCommentId(guestbookCommentUpdateRequestDto.getGuestbookCommentId());
            guestbookComment.update(guestbookCommentUpdateRequestDto.getContents());
            guestbookComment = guestbookCommentRepository.save(guestbookComment);
            return modelMapper.map(guestbookComment, GuestbookResponseDto.class);
        } else {
            throw new RecordNotFoundException(CustomMessage.DOESNOT_EXIT + guestbookCommentUpdateRequestDto.getGuestbookCommentId());
        }
    }
}
