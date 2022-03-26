package whoami.core.Domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.OffsetDateTime;

@Getter
@NoArgsConstructor
//@Builder
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OneToMany  // 한 명의 유저는 다수의 answer를 가질 수 있다
    @JoinColumn(name="answer_id")
    Long id;

    @Column(name = "user_name")
    String userName;

}
