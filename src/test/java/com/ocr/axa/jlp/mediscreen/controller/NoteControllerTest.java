package com.ocr.axa.jlp.mediscreen.controller;

import com.ocr.axa.jlp.mediscreen.dto.Note;
import com.ocr.axa.jlp.mediscreen.dto.Patient;
import com.ocr.axa.jlp.mediscreen.proxies.NoteProxy;
import com.ocr.axa.jlp.mediscreen.proxies.PatientProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientController.class)
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteProxy noteProxy;

    @MockBean
    private PatientProxy patientProxy;

    @Configuration
    static class ContextConfiguration {
        @Bean
        public NoteController getBNoteController() {
            return new NoteController();
        }
    }




    /**
     * Test controller to add one Note
     */
    @Test
    void ValidateOneNoteReturnOK() throws Exception {

        Note note = new Note();
        note.setId("1");
        note.setNote("note test");
        note.setPatientId(1L);

        doNothing().when(noteProxy).addNote(note);

        this.mockMvc.perform(post("/note/1/validate")
                .param("note", note.getNote())
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(view().name("redirect:/note/1/list"))
                .andExpect(status().is3xxRedirection());
    }




    /**
     * Test to get the form to add a Note
     */
    @Test
    public void getNoteAddTest() throws Exception {

        // GIVEN
        // WHEN
        // THEN
        this.mockMvc
                .perform(get("/note/1/add"))
                .andExpect(status().isOk());
    }



    /**
     * Test to delete a Note
     */
    @Test
    public void getDeleteNoteTest() throws Exception {

        // GIVEN
        Note note = new Note();
        note.setId("1");
        note.setNote("note test");
        note.setPatientId(1L);

        Mockito.when(noteProxy.getListNotesPatient(any(Long.class))).thenReturn(note);

        // WHEN
        // THEN
        this.mockMvc
                .perform(get("/note/delete/1"))
                .andExpect(view().name("redirect:/note/1/list")).andExpect(status().is3xxRedirection());
    }

    /**
     * Test to delete a Note, with an id Note not existing
     */
    @Test
    public void getDeleteNoteNoExistingTest() throws Exception {

        // GIVEN
        Note note = null;

        Mockito.when(noteProxy.getListNotesPatient(any(Long.class))).thenReturn(note);

        // WHEN
        // THEN
        try {
            this.mockMvc.perform(
                    get("/note/delete/1"));
        } catch (Exception e) {
            assertEquals(e.getMessage(),"Invalid note Id:1");
        }
    }

}
