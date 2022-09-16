package whoami.core.dto.guestbookComment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuestbookCommentUpdateRequestDto {
    private Long guestbookCommentId;
    private String contents;
}

