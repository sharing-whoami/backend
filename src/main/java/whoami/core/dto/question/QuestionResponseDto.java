package whoami.core.dto.question;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class QuestionResponseDto {
    private Long questionId;
    private String contents;
    private LocalDate questionDate;
    private LocalDateTime updatedAt;

    @Builder
    public QuestionResponseDto(Long questionId, String contents, LocalDate questionDate, LocalDateTime updatedAt) {
        this.questionId = questionId;
        this.contents = contents;
        this.questionDate = questionDate;
        this.updatedAt = updatedAt;
    }

    @Builder
    public QuestionResponseDto(){}

    public void printAll() {
        System.out.println(this.questionId);
        System.out.println(this.contents);
        System.out.println(this.questionDate);
        System.out.println(this.updatedAt);
    }
}
