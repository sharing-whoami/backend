package whoami.core.dto.members;

import lombok.Getter;
import whoami.core.domain.members.Members;

@Getter
public class MembersResponseDto { // Entity 객체를 DTO로 변환해서 응답하는 클래스

    private Long id;
    private String userId;
    private String name;
    private String email;

    public MembersResponseDto(Members entity){
        this.id=entity.getId();
        this.userId = entity.getUserId();
        this.name = entity.getName();
        this.email = entity.getEmail();
    }
}
