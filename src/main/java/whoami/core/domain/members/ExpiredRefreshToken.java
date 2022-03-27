package whoami.core.domain.members;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity // ==table
// jpa의 entity 및 column은 자동으로 camel case -> DB의 snake_case에 매칭시켜줌
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 불완전한 객체 생성을 막아주는 역할
@Table(name="token")
public class ExpiredRefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @Builder
    public ExpiredRefreshToken(String token) {
        this.token = token;
    }
}
