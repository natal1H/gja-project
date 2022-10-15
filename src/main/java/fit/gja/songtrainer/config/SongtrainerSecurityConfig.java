package fit.gja.songtrainer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SongtrainerSecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        UserDetails john = User.builder().username("john").password("{noop}test123").roles("USER").build();
        UserDetails mary = User.builder().username("mary").password("{noop}test123").roles("USER","LECTOR").build();

        return new InMemoryUserDetailsManager(john, mary);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeRequests(configurer -> configurer
                        .antMatchers("/").hasRole("USER")
                        .antMatchers("/lectors/**").hasRole("LECTOR"))
                .formLogin(configurer -> configurer.loginPage("/showMyLoginPage")
                                                   .loginProcessingUrl("/authenticateTheUser")
                                                   .permitAll())
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling(configurer -> configurer.accessDeniedPage("/access-denied"))
                .build();
    }
}
