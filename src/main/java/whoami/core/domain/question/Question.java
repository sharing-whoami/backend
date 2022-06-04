package whoami.core.domain.question;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@EnableJpaAuditing
@Entity(name="question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false, name = "question_date")
    private LocalDate questionDate;

    @CreationTimestamp
    @Column(nullable = false, name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Question() {
        this.updatedAt = LocalDateTime.now();
    }

    public void printAll() {
        System.out.println(this.questionId);
        System.out.println(this.contents);
        System.out.println(this.questionDate);
        System.out.println(this.updatedAt);
    }
}
