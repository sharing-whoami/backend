package whoami.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whoami.core.dto.GuestbookCommentDto;
import whoami.core.service.GuestbookCommentService;

@RestController
@RequestMapping("/guestbook/comment")
public class GuestbookCommentController {
    private GuestbookCommentService guestbookCommentService;

    @Autowired
    public GuestbookCommentController(GuestbookCommentService guestbookCommentService) {
        this.guestbookCommentService = guestbookCommentService;
    }

    @GetMapping(value = "/{guestbookCommentId}")
    public ResponseEntity<GuestbookCommentDto> getGuestbook(@PathVariable Long guestbookCommentId) {
        GuestbookCommentDto guestbookComment = guestbookCommentService.findByGuestbookCommentId(guestbookCommentId);
        return new ResponseEntity<GuestbookCommentDto>(guestbookComment, HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<GuestbookCommentDto> createGuestbook(@RequestBody GuestbookCommentDto guestbookCommentDto) {
        GuestbookCommentDto guestbookComment = guestbookCommentService.createGuestbookComment(guestbookCommentDto);
        return new ResponseEntity<GuestbookCommentDto>(guestbookComment, HttpStatus.OK);
    }
}
