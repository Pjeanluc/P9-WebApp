package com.ocr.axa.jlp.mediscreen.controller;

import com.ocr.axa.jlp.mediscreen.dto.User;
import com.ocr.axa.jlp.mediscreen.proxies.UserProxy;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WithMockUser(roles = "USER")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserProxy userProxy;

    /**
     * Test controller to show user list
     */

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
    }

    /**
     * Test to get the form to add a user
     */

    public void getUserAddTest() throws Exception {

        // GIVEN
        // WHEN
        // THEN
        this.mockMvc
                .perform(get("/user/add"))
                .andExpect(status().isOk());
    }


    /**
     * Test to delete a user, with an id user not existing
     */

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
