package com.ocr.axa.jlp.mediscreen.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WithMockUser(roles = "USER")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test controller home
     */

    void HomeReturnOK() throws Exception {

        this.mockMvc.perform(get("/home")
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(view().name("home"))
                .andExpect(status().isOk());

    }

    /**
     * Test controller home
     */

    void HomeAdminReturnOK() throws Exception {

        this.mockMvc.perform(get("/admin/home")
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(status().is3xxRedirection());

    }

    /**
     * Test controller home
     */

    void HomeUserReturnOK() throws Exception {

        this.mockMvc.perform(get("/user/home")
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(view().name("redirect:/patient/list"))
                .andExpect(status().is3xxRedirection());

    }
}
