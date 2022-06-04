package whoami.core.dto.guestbook;

import lombok.Getter;
import lombok.Setter;
import whoami.core.domain.member.Member;

import java.time.LocalDateTime;

@Getter
@Setter
public class GuestbookResponseDto {
    private Long guestbookId;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Member ownerId;
    private Member writerId;
    private Boolean isUpdated;

}
