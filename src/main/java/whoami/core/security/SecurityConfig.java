//package whoami.core.security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import whoami.core.service.MemberService;
//
//@RequiredArgsConstructor
//@Configuration
//@EnableWebSecurity // Spring Security 설정들 활성화 시켜줌
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    private final MemberService memberService;
//    // https://imgzon.tistory.com/101
//
//    @Override
//    // 인증을 무시할 경로 설정
//    public void configure(WebSecurity web) throws Exception{
//        web.ignoring().antMatchers("/css/**","/js/**","/img/**","/lib/**","/h2-console/**");
//    }
//
//    @Override
//    // http 관련 인증 설정 가능
//    public void configure(HttpSecurity http) throws Exception{
//        http
//                .csrf().disable().headers().frameOptions().disable()
//                .and()
//                    .authorizeRequests()
//                    .antMatchers("/login","/signup","/user").permitAll()
//                    .antMatchers("/").hasRole("ROLE_MEMBER")
//                    .antMatchers("/admin").hasRole("ROLE_ADMIN")
//                    .anyRequest().authenticated()
//                .and()
//                    .formLogin()
//                    .loginPage("/login")
//                    .defaultSuccessUrl("/")
//                .and()
//                    .logout()
//                    .logoutSuccessUrl("/login")
//                    .invalidateHttpSession(true);
//    }
//
//    @Override
//    // 로그인 시 필요한 정보를 가져옴
//    public void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.userDetailsService(memberService) // 유저 서비스를 어느 서비스에서 가져올 지 결정
//                .passwordEncoder(new BCryptPasswordEncoder());
//    }
//}
//
