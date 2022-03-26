package whoami.core.DTO;
import lombok.Getter;
import whoami.core.Domain.Answer;
import whoami.core.Domain.Tag;
import whoami.core.Domain.User;
import java.time.OffsetDateTime;
import java.util.Collection;

@Getter
public class AnswerResponseDTO {

    private Long answerId; // PK
    private String answerContents;
    private OffsetDateTime updatedAt;
    private Collection<Tag> tagId; // FK

    public AnswerResponseDTO(Answer entity){
        this.answerId = entity.getAnswerId();
        this.answerContents = entity.getAnswerContents();
        this.updatedAt = entity.getUpdatedAt();
        this.tagId = entity.getTagId();
    }

}
