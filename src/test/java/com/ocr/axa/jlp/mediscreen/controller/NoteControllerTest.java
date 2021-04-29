package com.ocr.axa.jlp.mediscreen.controller;

import com.ocr.axa.jlp.mediscreen.dto.Note;
import com.ocr.axa.jlp.mediscreen.dto.Patient;
import com.ocr.axa.jlp.mediscreen.proxies.AssessmentProxy;
import com.ocr.axa.jlp.mediscreen.proxies.NoteProxy;
import com.ocr.axa.jlp.mediscreen.proxies.PatientProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientController.class)
*/


@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean(name = "com.ocr.axa.jlp.mediscreen.proxies.NoteProxy")
    private NoteProxy noteProxy;
    @MockBean(name = "com.ocr.axa.jlp.mediscreen.proxies.PatientProxy")
    private PatientProxy patientProxy;
    @MockBean(name = "com.ocr.axa.jlp.mediscreen.proxies.AssessmentProxy")
    private AssessmentProxy assessmentProxy;


    Note note = new Note();
    Patient patient = new Patient();

    @BeforeEach
    void setup() {
        note.setPatientId(1L);
        note.setNote("a note");
        patient.setId(1L);
        patient.setBirthDate(LocalDate.of(2000, 02, 02));
        patient.setAddress("an address");
        patient.setFirstname("test");
        patient.setLastname("test");
        patient.setGenre('M');
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

        this.mockMvc.perform(post("/patHistory/1/validate")
                .param("note", note.getNote())
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(view().name("redirect:/patHistory/1/list"))
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
                .perform(get("/patHistory/1/add"))
                .andExpect(status().isOk());
    }

    /**
     * Test controller to update one Note
     */
    @Test
    void updateOneNoteReturnOK() throws Exception {

        Note note = new Note();
        note.setId("1");
        note.setNote("note test");
        note.setPatientId(1L);

        when(noteProxy.findById(any(String.class))).thenReturn(note);

        this.mockMvc.perform(get("/patHistory/1/update/noteId")
                .param("noteID", note.getId())
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(view().name("patHistory/update"))
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

        when(noteProxy.getListNotesPatient(any(Long.class))).thenReturn(note);

        // WHEN
        // THEN
        this.mockMvc
                .perform(get("/patHistory/1/delete/1"))
                .andExpect(view().name("redirect:/patHistory/1/list")).andExpect(status().is3xxRedirection());
    }

    /**
     * Test to delete a Note, with an id Note not existing
     */
    @Test
    public void getDeleteNoteNoExistingTest() throws Exception {

        // GIVEN
        Note note = null;

        when(noteProxy.getListNotesPatient(any(Long.class))).thenReturn(note);

        // WHEN
        // THEN
        try {
            this.mockMvc.perform(
                    get("/patHistory/delete/1"));
        } catch (Exception e) {
            assertEquals(e.getMessage(),"Invalid note Id:1");
        }
    }

}
