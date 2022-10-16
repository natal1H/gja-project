package fit.gja.songtrainer.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SongtrainerSecurityConfig {

    // add a reference to our security data source

    private DataSource securityDataSource;

    @Autowired
    public SongtrainerSecurityConfig(DataSource theSecurityDataSource) {
        securityDataSource = theSecurityDataSource;
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        return new JdbcUserDetailsManager(securityDataSource);
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
