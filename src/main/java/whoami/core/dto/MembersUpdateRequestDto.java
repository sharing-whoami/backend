package whoami.core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
public class MembersUpdateRequestDto {
    private String userId;
    private String password;
    private String phoneNum;
    private String email;
    private boolean isReceiveNotification;

    @Builder
    public MembersUpdateRequestDto(String password, String phoneNum, String email, boolean isReceiveNotification) {
        this.password =  bCryptPasswordEncoder(password);
        this.phoneNum = phoneNum;
        this.email = email;
        this.isReceiveNotification = isReceiveNotification;
    }
    private String bCryptPasswordEncoder(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }
}
