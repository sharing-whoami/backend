package whoami.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import whoami.core.domain.Role;
import whoami.core.domain.members.Members;
import whoami.core.domain.members.MembersRepository;
import whoami.core.dto.MembersSaveRequestDto;
import whoami.core.dto.MembersUpdateRequestDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MembersControllerTest {
    @Autowired
    MockMvc mockMvc;

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
    @WithMockUser(roles="USER")
    public void 회원가입_테스트() throws Exception {
        //given
        String userId = "yeon1";
        String password = "1111";
        String name = "yeon";
        String registryNum = "111111-1111111";
        String phoneNum = "010-1111-1111";
        String email = "yeon1@naver.com";
        boolean isReceiveNotification = true;
        String role = Role.USER.getValue();
        String profile = "1.jpg";
        MembersSaveRequestDto requestDto = MembersSaveRequestDto.builder()
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

        String url = "http://localhost:" + port + "/users/signup";
        // when
        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());
        // then
        List<Members> all = membersRepository.findAll();
        assertThat(all.get(0).getUserId()).isEqualTo(userId);
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
        String role=Role.USER.getValue();
        String profile="1.jpg";
        Members savedMember= membersRepository.save(Members.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .registryNum(registryNum)
                .phoneNum(phoneNum)
                .email(email)
                .isReceiveNotification(isReceiveNotification)
                .role(role)
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

        // when
        mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // then
        List<Members> all= membersRepository.findAll();
        System.out.println((all.get(0).getPassword()));
        assertThat(all.get(0).getPhoneNum()).isEqualTo(expectedPhoneNum);
        assertThat(all.get(0).getEmail()).isEqualTo(expectedEmail);
    }

}