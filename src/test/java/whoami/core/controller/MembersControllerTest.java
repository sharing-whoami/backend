package whoami.core.controller;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import whoami.core.domain.members.Members;
import whoami.core.domain.members.MembersRepository;
import whoami.core.dto.MembersResponseDto;
import whoami.core.dto.MembersSaveRequestDto;
import whoami.core.dto.MembersUpdateRequestDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MembersControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MembersRepository membersRepository;

    @After
    public void tearDown() throws Exception{
        membersRepository.deleteAll();
    }

    @Test
    public void Port_번호() throws Exception{
        System.out.println(port);
    }

    @Test
    public void 회원가입_테스트() throws Exception{
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
        MembersSaveRequestDto requestDto= MembersSaveRequestDto.builder()
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

        String url="http://localhost:"+port+"/users/signup";
        // when
        ResponseEntity<Long> responseEntity = restTemplate.
                postForEntity(url,requestDto,Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Members> all=membersRepository.findAll();
        assertThat(all.get(0).getUserId()).isEqualTo(userId);
        assertThat(all.get(0).getPassword()).isEqualTo(password);
    }

    @Test
    public void 회원정보수정_테스트() throws Exception{
        // given
        String userId="yeon1";
        String password="1111";
        String name="yeon";
        String registryNum="111111-1111111";
        String phoneNum="010-1111-1111";
        String email="yeon1@naver.com";
        boolean isReceiveNotification=true;
        boolean isAdmin=false;
        String profile="1.jpg";
        Members savedMember= membersRepository.save(Members.builder()
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

        Long updatedId=savedMember.getId();
        String expectedPassword="2222";
        String expectedPhoneNum="2222";
        String expectedEmail="222@";
        boolean expectedIsReceivedNotification=true;
        MembersUpdateRequestDto requestDto=MembersUpdateRequestDto.builder()
                .password(expectedPassword)
                .phoneNum(expectedPhoneNum)
                .email(expectedEmail)
                .isReceiveNotification(expectedIsReceivedNotification)
                .build();
        String url="http://localhost:"+port+"/users/"+updatedId;
        HttpEntity<MembersUpdateRequestDto> requestEntity=new HttpEntity<>(requestDto);

        // when
        ResponseEntity<Long> responseEntity=restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                Long.class
        );

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Members> all= membersRepository.findAll();
        assertThat(all.get(0).getPassword()).isEqualTo(expectedPassword);
        assertThat(all.get(0).getPhoneNum()).isEqualTo(expectedPhoneNum);
        assertThat(all.get(0).getEmail()).isEqualTo(expectedEmail);
    }

}