package com.ocr.axa.jlp.mediscreen.config;

import com.ocr.axa.jlp.mediscreen.service.MyAppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAppUserDetailsService myAppUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/patient/**")
                .hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/user/**").hasAnyAuthority("ADMIN").and().formLogin()
                // .antMatchers("/user/**").permitAll().and().formLogin() // login
                // configuration
                .defaultSuccessUrl("/patient/list").and().logout() // logout configuration
                .logoutUrl("/app-logout").logoutSuccessUrl("/").and().exceptionHandling() // exception handling
                // configuration
                .accessDeniedPage("/app/error");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.userDetailsService(myAppUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public HttpTraceRepository htttpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }
}
