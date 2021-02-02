package com.ocr.axa.jlp.mediscreen.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import com.ocr.axa.jlp.mediscreen.dto.User;
import com.ocr.axa.jlp.mediscreen.proxies.UserProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserProxy userProxy;

    @Configuration
    static class ContextConfiguration {
        @Bean
        public UserController getBUserController() {
            return new UserController();
        }
    }


    /**
     * Test controller to show user list
     */
    @Test
    void GetListOfUserReturnOK() throws Exception {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(1L);
        user.setPseudo("pseudo");
        user.setUsername("username");
        user.setPassword("password");

        users.add(user);
        when(userProxy.listOfUser()).thenReturn(users);


        this.mockMvc.perform(get("/user/list")
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(view().name("user/list"))
                .andExpect(status().isOk());
        verify(userProxy, Mockito.times(1)).listOfUser();
    }

    /**
     * Test controller to add one user
     */
    @Test
    void ValidateOneUserReturnOK() throws Exception {

        User user = new User();
        user.setId(1L);
        user.setPseudo("pseudo");
        user.setUsername("username");
        user.setPassword("Password1!");
        user.setRole("ADMIN");

        doNothing().when(userProxy).addUser(user);

        this.mockMvc.perform(post("/user/validate")
                .param("password", user.getPassword()).param("pseudo", user.getPseudo())
                .param("role", user.getRole()).param("username", user.getUsername())
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(status().is3xxRedirection());
    }

    /**
     * Test to validate a user update
     */
    @Test
    public void postUpdateUserTest() throws Exception {

        // GIVEN
        User user = new User();
        user.setId(1L);
        user.setPseudo("pseudo");
        user.setUsername("username");
        user.setPassword("Password1!");
        user.setRole("ADMIN");

        Mockito.when(userProxy.getUserById(any(Long.class))).thenReturn(user);

        // WHEN
        // THEN
        this.mockMvc.perform(
                post("/user/update/1")
                        .param("password", user.getPassword()).param("pseudo", user.getPseudo())
                        .param("role", user.getRole()).param("username", user.getUsername())

        ).andDo(print()).andExpect(view().name("redirect:/user/list"))
                .andExpect(status().is3xxRedirection());
    }

    /**
     * Test to validate a user update with an error (password)
     */
    @Test
    public void postUpdateUserWithWrongParametersTest() throws Exception {

        // GIVEN
        User user = new User();
        user.setId(1L);
        user.setPseudo("pseudo");
        user.setUsername("username");
        user.setPassword("Password1!");
        user.setRole("ADMIN");

        Mockito.when(userProxy.getUserById(any(Long.class))).thenReturn(user);

        // WHEN
        // THEN
        this.mockMvc
                .perform(post("/user/update/1").param("password", user.getPassword())
                        .param("pseudo", user.getPseudo()).param("role", user.getRole())
                        .param("username", user.getUsername()))
                .andExpect(view().name("redirect:/user/list"));
    }

    /**
     * Test to get the form to add a user
     */
    @Test
    public void getUserAddTest() throws Exception {

        // GIVEN
        // WHEN
        // THEN
        this.mockMvc
                .perform(get("/user/add"))
                .andExpect(status().isOk());
    }

    /**
     * Test to get the form to update a user
     */
    @Test
    public void getUpdateUserTest() throws Exception {

        // GIVEN
        User user = new User();
        user.setId(1L);
        user.setPseudo("pseudo");
        user.setUsername("username");
        user.setPassword("Password1!");
        user.setRole("ADMIN");

        Mockito.when(userProxy.getUserById(any(Long.class))).thenReturn(user);

        // WHEN
        // THEN
        this.mockMvc.perform(
                get("/user/update/1"))
                .andExpect(status().isOk());
    }

    /**
     * Test to delete a user
     */
    @Test
    public void getDeleteUserTest() throws Exception {

        // GIVEN
        User user = new User();
        user.setId(1L);
        user.setPseudo("pseudo");
        user.setUsername("username");
        user.setPassword("Password1!");
        user.setRole("ADMIN");

        Mockito.when(userProxy.getUserById(any(Long.class))).thenReturn(user);

        // WHEN
        // THEN
        this.mockMvc
                .perform(get("/user/delete/1"))
                .andExpect(view().name("redirect:/user/list")).andExpect(status().is3xxRedirection());
    }

    /**
     * Test to delete a user, with an id user not existing
     */
    @Test
    public void getDeleteUserNoExistingTest() throws Exception {

        // GIVEN
        User user = null;

        Mockito.when(userProxy.getUserById(any(Long.class))).thenReturn(user);

        // WHEN
        // THEN
        try {
            this.mockMvc.perform(
                    get("/user/delete/1"));
        } catch (Exception e) {
            assertEquals(e.getMessage(),"Invalid user Id:1");
        }
    }

}
