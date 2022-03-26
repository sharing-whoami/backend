package whoami.core.DTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import whoami.core.Domain.Answer;
import whoami.core.Domain.Tag;
import whoami.core.Domain.User;
import java.time.OffsetDateTime;
import java.util.Collection;

@Getter
@NoArgsConstructor
public class AnswerSaveRequestDTO {

    private Long answerId;                  // PK
    private String answerContents;
    private OffsetDateTime updatedAt;
    private Collection<Tag> tagId;          // FK

    @Builder
    public AnswerSaveRequestDTO(Long answerId, String answerContents, OffsetDateTime updatedAt, User id, Collection<Tag> tagId, Tag tagContents) {
        this.answerId = answerId;
        this.answerContents = answerContents;
        this.updatedAt = updatedAt;
        this.tagId = tagId;
    }

    public Answer toEntiy(){
        return Answer.builder()
                .answerId(answerId)
                .answerContents(answerContents)
                .updatedAt(updatedAt)
                .tagId(tagId)
                .build();
    }

}
