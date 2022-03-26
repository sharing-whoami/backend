package whoami.core.Domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Tag {

    @Id // PK
    @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id 값을 null로 하면 DB가 알아서 AUTO_INCREMENT
    private Long tagId;

    @Column(name = "contents", length = 1000, nullable = false)
    private String contents;

    @Builder
    public Tag(Long tagId, String contents){
        this.tagId = tagId;
        this.contents = contents;
    }

}
