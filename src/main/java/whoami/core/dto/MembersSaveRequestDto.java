package whoami.core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import whoami.core.domain.members.Members;

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
    private boolean isAdmin;
    private String profile;

    @Builder
    public MembersSaveRequestDto(String userId, String password, String name, String registryNum, String phoneNum, String email, boolean isReceiveNotification, boolean isAdmin, String profile) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.registryNum = registryNum;
        this.phoneNum = phoneNum;
        this.email = email;
        this.isReceiveNotification = isReceiveNotification;
        this.isAdmin = isAdmin;
        this.profile = profile;
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
                .isAdmin(isAdmin)
                .profile(profile)
                .build();
    }
}
