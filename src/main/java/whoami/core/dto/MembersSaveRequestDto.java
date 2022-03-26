package whoami.core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import whoami.core.domain.members.Members;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Getter
@Setter
@NoArgsConstructor
public class MembersSaveRequestDto { // RequestDto 객체를 Entity 객체로 변환
    private String userId;
    private String password;
    private String name;
    private String registryNum;
    private String phoneNum;
    private String email;
    private boolean isReceiveNotification;
    private String role;
    private String profile;

    @Builder
    public MembersSaveRequestDto(String userId, String password, String name, String registryNum, String phoneNum, String email, boolean isReceiveNotification, String role, String profile) {
        this.userId = userId;
        this.password = bCryptPasswordEncoder(password);
        this.name = name;
        this.registryNum = registryNum;
        this.phoneNum = phoneNum;
        this.email = email;
        this.isReceiveNotification = isReceiveNotification;
        this.role = role;
        this.profile = profile;
    }

    private String bCryptPasswordEncoder(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }

    // dto인 MembersSaveRequestDto의 객체를 Members의 Entity 객체로 변환하기 위한 메서드
    public Members toEntity(){
        return Members.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .registryNum(registryNum)
                .phoneNum(phoneNum)
                .email(email)
                .isReceiveNotification(isReceiveNotification)
                .role(role)
                .profile(profile)
                .build();
    }
}
