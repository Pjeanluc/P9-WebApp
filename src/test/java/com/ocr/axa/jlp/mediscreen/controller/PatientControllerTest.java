package com.ocr.axa.jlp.mediscreen.controller;

import com.ocr.axa.jlp.mediscreen.dto.Note;
import com.ocr.axa.jlp.mediscreen.dto.Patient;
import com.ocr.axa.jlp.mediscreen.proxies.PatientProxy;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientProxy patientProxy;

    @Configuration
    static class ContextConfiguration {
        @Bean
        public PatientController getBPatientController() {
            return new PatientController();
        }
    }

    @Test
    void GetListOfPatientReturnOK() throws Exception {
        List<Patient> patients = new ArrayList<>();
        Patient patient = new Patient();
        LocalDate birth = LocalDate.of(2000,1,15);
        patient.setId(1L);
        patient.setAddress("12 rue des oliviers");
        patient.setFirstname("TestFamille");
        patient.setLastname("Test");
        patient.setGenre('M');
        patient.setBirthDate(birth);

        patients.add(patient);
        when(patientProxy.listOfPatient()).thenReturn(patients);


        this.mockMvc.perform(get("/patient/list")
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(view().name("patient/list"))
                .andExpect(status().isOk());
        verify(patientProxy, Mockito.times(1)).listOfPatient();
    }
    /**
     * Test controller to add one patient
     */
    @Test
    void ValidateOnePatientReturnOK() throws Exception {
        Patient patient = new Patient();
        LocalDate birth = LocalDate.of(2000,1,15);
        patient.setId(1L);
        patient.setAddress("12 rue des oliviers");
        patient.setFirstname("TestFamille");
        patient.setLastname("Test");
        patient.setGenre('M');
        patient.setBirthDate(birth);

        doNothing().when(patientProxy).addPatient(patient);

        this.mockMvc.perform(post("/patient/validate")
                .param("firstname", patient.getFirstname())
                .param("lastname", patient.getLastname())
                .param("address", patient.getAddress())
                .param("birthDate",patient.getBirthDate().toString())
                .param("genre", String.valueOf(patient.getGenre()))
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(view().name("redirect:/patient/list"))
                .andExpect(status().is3xxRedirection());
    }

    /**
     * Test controller to update one Note
     */
    @Test
    void getUpdateOnePatientReturnOK() throws Exception {

        Patient patient = new Patient();
        LocalDate birth = LocalDate.of(2000,1,15);
        patient.setId(1L);
        patient.setAddress("12 rue des oliviers");
        patient.setFirstname("TestFamille");
        patient.setLastname("Test");
        patient.setGenre('M');
        patient.setBirthDate(birth);

        Mockito.when(patientProxy.findById(any(Long.class))).thenReturn(patient);

        this.mockMvc.perform(get("/patient/update/1")
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(view().name("patient/update"))
                .andExpect(status().isOk());
    }
    /**
     * Test to validate a patient update
     */
    @Test
    public void postUpdatePatientTest() throws Exception {

        // GIVEN
        Patient patient = new Patient();
        LocalDate birth = LocalDate.of(2000,1,15);
        patient.setId(1L);
        patient.setAddress("12 rue des oliviers");
        patient.setFirstname("TestFamille");
        patient.setLastname("Test");
        patient.setGenre('M');
        patient.setBirthDate(birth);

        when(patientProxy.getPatientById(any(Long.class))).thenReturn(patient);

        // WHEN
        // THEN
        this.mockMvc.perform(
                post("/patient/update/1")
                .param("firstname", patient.getFirstname())
                .param("lastname", patient.getLastname())
                .param("address", patient.getAddress())
                .param("birthDate",patient.getBirthDate().toString())
                .param("genre", String.valueOf(patient.getGenre()))

        ).andDo(print()).andExpect(view().name("redirect:/patient/list"))
                .andExpect(status().is3xxRedirection());
    }

    /**
     * Test to validate a patient update with an error
     */
    @Test
    public void postUpdatePatientWithWrongParametersTest() throws Exception {

        // GIVEN
        Patient patient = new Patient();
        LocalDate birth = LocalDate.of(2000,1,15);
        patient.setId(1L);
        patient.setAddress("12 rue des oliviers");
        patient.setFirstname("TestFamille");
        patient.setLastname("Test");
        patient.setGenre('M');
        patient.setBirthDate(birth);

        when(patientProxy.getPatientById(any(Long.class))).thenReturn(patient);

        // WHEN
        // THEN
        this.mockMvc
                .perform(post("/patient/update/1")
                        .param("firstname", patient.getFirstname())
                        .param("lastname", patient.getLastname())
                        .param("address", patient.getAddress())
                        .param("birthDate",patient.getBirthDate().toString())
                        .param("genre", String.valueOf(patient.getGenre())))
                .andExpect(view().name("redirect:/patient/list"));
    }

    /**
     * Test to get the form to add a patient
     */
    @Test
    public void getPatientAddTest() throws Exception {

        // GIVEN
        // WHEN
        // THEN
        this.mockMvc.perform(get("/patient/add")                )
                .andExpect(status().isOk());
    }



    /**
     * Test to delete a patient
     */
    @Test
    public void getDeletePatientTest() throws Exception {

        // GIVEN
        Patient patient = new Patient();
        LocalDate birth = LocalDate.of(2000,1,15);
        patient.setId(1L);
        patient.setAddress("12 rue des oliviers");
        patient.setFirstname("TestFamille");
        patient.setLastname("Test");
        patient.setGenre('M');
        patient.setBirthDate(birth);

        when(patientProxy.getPatientById(any(Long.class))).thenReturn(patient);

        // WHEN
        // THEN
        this.mockMvc
                .perform(get("/patient/delete/1"))
                .andExpect(view().name("redirect:/patient/list")).andExpect(status().is3xxRedirection());
    }

    /**
     * Test to delete a user, with an id user not existing
     */
    @Test
    public void getDeletePatientNoExistingTest() throws Exception {

        // GIVEN

        when(patientProxy.getPatientById(any(Long.class))).thenReturn(null);

        // WHEN
        // THEN
        try {
            this.mockMvc.perform(
                    get("/patient/delete/1"));
        } catch (Exception e) {
            assertEquals(e.getMessage(),"Invalid patient Id:1");
        }
    }

}
