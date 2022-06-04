package whoami.core.dto.guestbookComment;

import lombok.Getter;
import lombok.Setter;
import whoami.core.domain.member.Member;

import java.time.LocalDateTime;

@Getter
@Setter
public class GuestbookCommentResponseDto {
    private Long guestbookCommentId;
    private Long guestbookId;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Member memberId;
    private Boolean isUpdated;

}
