package whoami.core.dto;

import lombok.Getter;
import whoami.core.domain.members.Members;

@Getter
public class MembersResponseDto { // Entity 객체를 DTO로 변환해서 응답하는 클래스
    private String userId;
    private String password;
    private String name;
    private String registryNum;
    private String phoneNum;
    private String email;
    private boolean isReceiveNotification;
    private boolean isAdmin;
    private String profile;

    public MembersResponseDto(Members entity){
        this.userId = entity.getUserId();
        this.password = entity.getPassword();
        this.name = entity.getName();
        this.registryNum = entity.getRegistryNum();
        this.phoneNum = entity.getPhoneNum();
        this.email = entity.getEmail();
        this.isReceiveNotification = entity.isReceiveNotification();
        this.isAdmin = entity.isAdmin();
        this.profile = entity.getProfile();
    }
}
