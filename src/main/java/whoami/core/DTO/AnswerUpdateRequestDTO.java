package whoami.core.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import whoami.core.Domain.Tag;
import whoami.core.Domain.User;

import java.time.OffsetDateTime;

@Getter
@NoArgsConstructor
public class AnswerUpdateRequestDTO {

    private Long answerId;                  // PK
    private String answerContents;
    private OffsetDateTime updatedAt;       // 자동으로 update 되어야 함
    private Tag tagContents;                // FK

    @Builder
    public AnswerUpdateRequestDTO(Long answerId, String answerContents, OffsetDateTime updatedAt, Tag tagContents){
        this.answerId = answerId;
        this.answerContents = answerContents;
        this.updatedAt = updatedAt;
        this.tagContents = tagContents;
    }
}
