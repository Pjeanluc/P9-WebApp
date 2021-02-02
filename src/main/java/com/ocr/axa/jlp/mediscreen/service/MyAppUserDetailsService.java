package com.ocr.axa.jlp.mediscreen.service;

import com.ocr.axa.jlp.mediscreen.dto.User;
import com.ocr.axa.jlp.mediscreen.proxies.UserProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class MyAppUserDetailsService implements UserDetailsService {
    @Autowired
    private UserProxy userProxy;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //User user = userProxy.getUserById(44L);
        User user = userProxy.findByUsername(username);

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        UserDetails userDetails = (UserDetails)new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), Arrays.asList(authority));
        return userDetails;
    }
}
