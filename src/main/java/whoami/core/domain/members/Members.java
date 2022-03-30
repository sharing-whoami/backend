package whoami.core.domain.members;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;


@Getter
@Entity // ==table
// jpa의 entity 및 column은 자동으로 camel case -> DB의 snake_case에 매칭시켜줌
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 불완전한 객체 생성을 막아주는 역할
@EqualsAndHashCode(of = "userId")
@Table(name="members")
public class Members implements UserDetails { //SpringSecurity는 UserDetails 객체를 통해 권한 정보를 관리
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long memberId;

    @Column(nullable = false, unique = true,name="user_id")
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
    public Members(String userId, String password, String name, String registryNum, String phoneNum, String email, boolean isReceiveNotification, String role, String profile) {
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
        this.password=password;
        this.phoneNum=phoneNum;
        this.email=email;
        this.isReceiveNotification=isReceiveNotification;
    }

    public void profileUpdate(String profile){
        this.profile=profile;
    }

    // 유저의 권한 목록
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(() -> "ROLE_"+getRole());
        return collectors;
    }

    @Override
    public String getUsername() {
        return getUserId();
    }

    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠김 여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 사용자 활성화 여부
    @Override
    public boolean isEnabled() {
        return true;
    }
}
