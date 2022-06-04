package whoami.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import whoami.core.domain.member.Member;
import whoami.core.dto.guestbook.GuestbookResponseDto;
import whoami.core.dto.guestbook.GuestbookSaveRequestDto;
import whoami.core.dto.guestbook.GuestbookUpdateRequestDto;
import whoami.core.service.GuestbookService;

@RestController
@RequestMapping("/guestbook")
public class GuestbookController {
    private GuestbookService guestbookService;

    @Autowired
    public GuestbookController(GuestbookService guestbookService) {
        this.guestbookService = guestbookService;
    }

    @GetMapping(value = "")
    public ResponseEntity getGuestbookList(
            @RequestParam(value="size", defaultValue="5", required = false) int size,
            @RequestParam(value="page", defaultValue="1", required = false) int page,
            @RequestParam(value="member_id", required = false) Long memberId
    ){
        if (memberId == null) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Member userDetails = (Member)principal;
            memberId = userDetails.getMemberId();
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("created_at"));
        Page<GuestbookResponseDto> guestbookResponseDtos = guestbookService.findAll(pageable, memberId);
        return ResponseEntity.ok().body(guestbookResponseDtos);
    }

    @PatchMapping(value = "")
    public ResponseEntity updateGuestbook(@RequestBody GuestbookUpdateRequestDto guestbookUpdateRequestDto) {
        //guestbookUpdateRequestDto.printAll();
        GuestbookResponseDto guestbookResponseDto = guestbookService.updateGuestbook(guestbookUpdateRequestDto);
        return ResponseEntity.ok().body(guestbookResponseDto);
    }

    @PostMapping(value = "")
    public ResponseEntity createGuestbook(@RequestBody GuestbookSaveRequestDto guestbookSaveRequestDto) {
        GuestbookResponseDto guestbookResponseDto = guestbookService.createGuestbook(guestbookSaveRequestDto);
        return ResponseEntity.ok().body(guestbookResponseDto);
    }

    @DeleteMapping(value = "/{guestbookId}")
    public ResponseEntity<?> deleteGuestbook(@PathVariable Long guestbookId) {
        guestbookService.deleteByGuestbookId(guestbookId);
        return ResponseEntity.ok().body("Success");
    }
}
