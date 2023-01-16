package app.dev.pack.security;

import app.dev.pack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig{

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService service;

    @Autowired
    public WebSecurityConfig(BCryptPasswordEncoder passwordEncoder, UserService service) {
        this.passwordEncoder = passwordEncoder;
        this.service = service;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/register/**").permitAll();
                    auth.anyRequest().authenticated();
                }).formLogin(Customizer.withDefaults())
                .build();
    }

    @Bean
    protected AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) {
        return auth.authenticationProvider(daoAuthenticationProvider()).getOrBuild();
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(service);

        return provider;
    }
}