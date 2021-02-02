package com.ocr.axa.jlp.mediscreen.controller;

import com.ocr.axa.jlp.mediscreen.dto.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientController.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Configuration
    static class ContextConfiguration {
        @Bean
        public HomeController getBHomeController() {
            return new HomeController();
        }
    }

    /**
     * Test controller home
     */
    @Test
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
    @Test
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
    @Test
    void HomeUserReturnOK() throws Exception {

        this.mockMvc.perform(get("/user/home")
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(view().name("redirect:/patient/list"))
                .andExpect(status().is3xxRedirection());

    }
}
