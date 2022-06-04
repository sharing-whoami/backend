package whoami.core.dto.question;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class QuestionSaveRequestDto {
    private String contents;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate questionDate;

    @Builder
    public QuestionSaveRequestDto(String contents, LocalDate questionDate) {
        this.contents = contents;
        this.questionDate = questionDate;
    }

    public void printAll() {
        System.out.println(this.contents);
        System.out.println(this.questionDate);
    }
}
