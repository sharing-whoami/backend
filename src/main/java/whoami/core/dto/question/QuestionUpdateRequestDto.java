package whoami.core.dto.question;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class QuestionUpdateRequestDto {
    private Long questionId;
    private String contents;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate questionDate;

    @Builder
    public QuestionUpdateRequestDto(Long questionId, String contents, LocalDate questionDate) {
        this.questionId = questionId;
        this.contents = contents;
        this.questionDate = questionDate;
    }

    public void printAll() {
        System.out.println(this.questionId);
        System.out.println(this.contents);
        System.out.println(this.questionDate);
    }
}
