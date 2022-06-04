package whoami.core.dto.guestbookComment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuestbookCommentSaveRequestDto {
    private Long guestbookId;
    private String contents;
}

