package whoami.core.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import whoami.core.domain.GuestbookCommentDomain;
import whoami.core.dto.GuestbookCommentDto;
import whoami.core.exception.CustomMessage;
import whoami.core.exception.RecordNotFoundException;
import whoami.core.repository.GuestbookCommentRepository;

@Service
public class GuestbookCommentService {
    @Autowired
    private GuestbookCommentRepository guestbookCommentRepository;

    public GuestbookCommentDto findByGuestbookCommentId(Long guestbookCommentId){
        if (guestbookCommentRepository.existsById(guestbookCommentId)) {
            GuestbookCommentDomain guestbookCommentDomain =
                    guestbookCommentRepository.findByGuestbookCommentId(guestbookCommentId);
            return copyGuestbookCommentDomainToDto(guestbookCommentDomain);
        } else {
            // TODO: Throw some exception
            throw new RecordNotFoundException(CustomMessage.DOESNOT_EXIT + guestbookCommentId);
        }
    }

    public GuestbookCommentDto createGuestbookComment(GuestbookCommentDto guestbookCommentDto) {
        try {
            GuestbookCommentDomain guestbookCommentDomain = copyGuestbookCommentDtoToDomain(guestbookCommentDto);
            guestbookCommentDomain = guestbookCommentRepository.save(guestbookCommentDomain);
            return copyGuestbookCommentDomainToDto(guestbookCommentDomain);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(ex.getCause().getCause().getMessage());
        }
    }

    private GuestbookCommentDto copyGuestbookCommentDomainToDto(GuestbookCommentDomain guestbookCommentDomain) {
        GuestbookCommentDto guestbookCommentDto = new GuestbookCommentDto();
        BeanUtils.copyProperties(guestbookCommentDomain, guestbookCommentDto);
        return guestbookCommentDto;
    }
    private GuestbookCommentDomain copyGuestbookCommentDtoToDomain(GuestbookCommentDto guestbookCommentDto) {
        GuestbookCommentDomain guestbookCommentDomain = new GuestbookCommentDomain();
        BeanUtils.copyProperties(guestbookCommentDto, guestbookCommentDomain);
        return guestbookCommentDomain;
    }
}
