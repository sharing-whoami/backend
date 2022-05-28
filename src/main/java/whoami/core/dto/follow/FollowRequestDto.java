package whoami.core.dto.follow;
import lombok.*;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
public class FollowRequestDto {
    private String followerId;
    private String followingId;

    @Builder
    public FollowRequestDto(String followerId,String followingId) {
        this.followerId=followerId;
        this.followingId=followingId;
    }


}
