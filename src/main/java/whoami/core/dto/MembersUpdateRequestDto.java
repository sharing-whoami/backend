package whoami.core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MembersUpdateRequestDto {
    private String userId;
    private String password;
    private String phoneNum;
    private String email;
    private boolean isReceiveNotification;

    @Builder
    public MembersUpdateRequestDto(String password, String phoneNum, String email, boolean isReceiveNotification) {
        this.password = password;
        this.phoneNum = phoneNum;
        this.email = email;
        this.isReceiveNotification = isReceiveNotification;
    }
}
