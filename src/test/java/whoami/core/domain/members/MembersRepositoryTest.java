package whoami.core.domain.members;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MembersRepositoryTest {
    @Autowired
    MembersRepository membersRepository;

    @After
    public void cleanup(){
        membersRepository.deleteAll();
    }

    @Test
    public void 회원_생성하기(){
        //given
        String userId="yeon1";
        String password="1111";
        String name="yeon";
        String registryNum="111111-1111111";
        String phoneNum="010-1111-1111";
        String email="yeon1@naver.com";
        boolean isReceiveNotification=true;
        boolean isAdmin=false;
        String profile="1.jpg";
        membersRepository.save(Members.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .registryNum(registryNum)
                .phoneNum(phoneNum)
                .email(email)
                .isReceiveNotification(isReceiveNotification)
                .isAdmin(isAdmin)
                .profile(profile)
                .build());

        // when
        List<Members> membersList=membersRepository.findAll();

        // then
        Members members=membersList.get(0);
        assertThat(members.getUserId()).isEqualTo(userId);
        assertThat(members.getPassword()).isEqualTo(password);
    }
}