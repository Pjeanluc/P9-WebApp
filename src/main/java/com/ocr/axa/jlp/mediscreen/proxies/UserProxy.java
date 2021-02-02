package com.ocr.axa.jlp.mediscreen.proxies;

import com.ocr.axa.jlp.mediscreen.config.FeignClientConfiguration;
import com.ocr.axa.jlp.mediscreen.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "mediscreen-user", url = "http://localhost:8080",configuration = FeignClientConfiguration.class)
public interface UserProxy {

    @GetMapping(value = "/user/all")
    public Object listOfUser();

    @GetMapping(value = "/user/id")
    User getUserById(@RequestParam("id") Long id);

    @PostMapping(value = "/user/add", consumes = "application/json")
    void addUser(@RequestBody User user);

    @PostMapping(value = "/user/update", consumes = "application/json")
    void updateUser(@RequestBody User user);

    @PostMapping(value = "/user/delete/id", consumes = "application/json")
    void delete(@RequestParam("id") Long id);

    @GetMapping(value = "/user/username")
    User findByUsername(@RequestParam("username") String username);
}