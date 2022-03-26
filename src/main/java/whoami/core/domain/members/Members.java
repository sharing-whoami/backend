package whoami.core.domain.members;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;


@Getter
@Entity // ==table
// jpa의 entity 및 column은 자동으로 camel case -> DB의 snake_case에 매칭시켜줌
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 불완전한 객체 생성을 막아주는 역할
@EqualsAndHashCode(of = "userId")
@Table(name="user")
public class Members{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column
    private String name;

    @Column(name="registry_num")
    private String registryNum;

    @Column(name="phone_num")
    private String phoneNum;

    @Column(nullable = false)
    private String email;

    @Column(name="is_receive_notification")
    private boolean isReceiveNotification;

    @Column(name="is_admin")
    private String role;

    @Column
    private String profile;

    @Builder
    public Members(Long id, String userId, String password, String name, String registryNum, String phoneNum, String email, boolean isReceiveNotification, String role, String profile) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.registryNum = registryNum;
        this.phoneNum = phoneNum;
        this.email = email;
        this.isReceiveNotification = isReceiveNotification;
        this.role = role;
        this.profile = profile;
    }

    public void update(String password,String phoneNum,String email,boolean isReceiveNotification){
        this.password=bCryptPasswordEncoder(password);
        this.phoneNum=phoneNum;
        this.email=email;
        this.isReceiveNotification=isReceiveNotification;
    }

    private String bCryptPasswordEncoder(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }

    public void profileUpdate(String profile){
        this.profile=profile;
    }


}
