package whoami.core.Domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Collection;

@Getter
@NoArgsConstructor
@Entity
public class Answer{

    @Id
    @Column(name = "answer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @Column(name = "contents", length = 1000, nullable = false)
    private String answerContents;

    @Column(name = "updated_at", columnDefinition = "DATETIME(6)", nullable = false) // DATETIME(6) : ms자르기
    private OffsetDateTime updatedAt;

    @OneToMany  // 하나의 answer는 다수의 tag를 가질 수 있다.
    @JoinColumn(name="answer_id")
    private Collection<Tag> tagId;

    @Builder // 생성자의 매개변수가 많을 때 좀 더 편리하게 객체를 만들 수 있게 도와주는 것
    public Answer(Long answerId, String answerContents, OffsetDateTime updatedAt, Collection<Tag> tagId) {
        this.answerId = answerId;
        this.answerContents = answerContents;
        this.updatedAt = updatedAt;
        this.tagId = tagId;
    }

    public void update(Long answerId, String answerContents, OffsetDateTime updatedAt){
        this.answerId = answerId;
        this.answerContents = answerContents;
        this.updatedAt = updatedAt;
    }

}
