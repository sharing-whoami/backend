package whoami.core.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import whoami.core.domain.GuestbookDomain;
import whoami.core.dto.GuestbookDto;
import whoami.core.exception.CustomMessage;
import whoami.core.exception.RecordNotFoundException;
import whoami.core.repository.GuestbookRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuestbookService {
    @Autowired
    private GuestbookRepository guestbookRepository;

    public List<GuestbookDto> findByCreateDateBetween(LocalDate startDate, LocalDate endDate){
        List<GuestbookDomain> guestbookDomainList =
                guestbookRepository.findByCreatedAtBetween(startDate.atStartOfDay(), endDate.atStartOfDay());
        return guestbookDomainList.stream()
                .map(this::copyGuestbookDomainToDto)
                .collect(Collectors.toList());
    }

    public GuestbookDto findByGuestbookId(Long guestbookId){
        if (guestbookRepository.existsById(guestbookId)) {
            GuestbookDomain guestbookDomain = guestbookRepository.findByGuestbookId(guestbookId);
            return copyGuestbookDomainToDto(guestbookDomain);
        } else {
            // TODO: Throw some exception
            throw new RecordNotFoundException(CustomMessage.DOESNOT_EXIT + guestbookId);
        }
    }

    public GuestbookDto createGuestbook(GuestbookDto guestbookDto) {
        try {
            GuestbookDomain guestbookDomain = copyGuestbookDtoToDomain(guestbookDto);
            guestbookDomain = guestbookRepository.save(guestbookDomain);
            return copyGuestbookDomainToDto(guestbookDomain);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(ex.getCause().getCause().getMessage());
        }
    }

    private GuestbookDto copyGuestbookDomainToDto(GuestbookDomain guestbookDomain) {
        GuestbookDto guestbookDto = new GuestbookDto();
        BeanUtils.copyProperties(guestbookDomain, guestbookDto);
        return guestbookDto;
    }
    private GuestbookDomain copyGuestbookDtoToDomain(GuestbookDto guestbookDto) {
        GuestbookDomain guestbookDomain = new GuestbookDomain();
        BeanUtils.copyProperties(guestbookDto, guestbookDomain);
        return guestbookDomain;
    }
}
