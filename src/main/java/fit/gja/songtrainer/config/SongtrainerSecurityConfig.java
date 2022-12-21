package fit.gja.songtrainer.config;

import fit.gja.songtrainer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SongtrainerSecurityConfig {

    @Autowired
    private UserService userService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    public SongtrainerSecurityConfig(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    //beans
    //bcrypt bean definition
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        return manager;
//    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder a = http.getSharedObject(AuthenticationManagerBuilder.class);
        a.parentAuthenticationManager(null);
        return a.userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeRequests()
                .antMatchers("/").hasRole("USER")
                .antMatchers("/songs").hasRole("USER")
                .antMatchers("/playlist").hasRole("USER")
                .antMatchers("/profile").hasRole("USER")
                .antMatchers("/settings").hasRole("USER")
                .antMatchers("/lectors/**").hasRole("LECTOR")
                .and()
                .authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/js/**", "/css/**").permitAll()
                .and()
                .formLogin().loginPage("/showLoginPage")
                            .loginProcessingUrl("/authenticateTheUser")
                            .successHandler(customAuthenticationSuccessHandler)
                            .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied")
                .and().csrf().disable()//Fixme: only for testing
                .build();
    }
}
