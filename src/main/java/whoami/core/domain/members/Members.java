package whoami.core.domain.members;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import javax.persistence.*;

@Getter
@Entity // ==table
// jpa의 entity 및 column은 자동으로 camel case -> DB의 snake_case에 매칭시켜줌
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 불완전한 객체 생성을 막아주는 역할
@Table(name="user")
public class Members { // implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;

    @Column
    private String password;

    @Column
    private String name;

    @Column(name="registry_num")
    private String registryNum;

    @Column(name="phone_num")
    private String phoneNum;

    @Column
    private String email;

    @Column(name="is_receive_notification")
    private boolean isReceiveNotification;

    @Column(name="is_admin")
    private boolean isAdmin;

    @Column
    private String profile;

    @Builder // ctrl + enter
    public Members(String userId, String password, String name, String registryNum, String phoneNum, String email, boolean isReceiveNotification, boolean isAdmin, String profile) {
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
    public void update(String password,String phoneNum,String email,boolean isReceiveNotification){
        this.password=password;
        this.phoneNum=phoneNum;
        this.email=email;
        this.isReceiveNotification=isReceiveNotification;
    }

}
