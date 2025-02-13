package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.service.UserDetails;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetails UserDetails;

    @Autowired
    public WebSecurityConfig(UserDetails UserDetails) {
        this.UserDetails = UserDetails;
    }

    @Autowired
    private SuccessUserHandler successUserHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/", "/login").permitAll()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/user/**").hasAnyRole("USER","ADMIN")
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .successHandler(new SuccessUserHandler())
                .and()
                .logout()
                    .logoutUrl("/logout") // Logout URL
                    .logoutSuccessUrl("/login?logout") // Redirect after logout
                    .permitAll();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider (PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(UserDetails);
        return daoAuthenticationProvider;
    }
}