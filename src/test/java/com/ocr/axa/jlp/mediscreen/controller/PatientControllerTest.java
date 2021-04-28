package com.ocr.axa.jlp.mediscreen.controller;

import com.ocr.axa.jlp.mediscreen.dto.Patient;
import com.ocr.axa.jlp.mediscreen.proxies.PatientProxy;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WithMockUser(roles = "USER")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientProxy patientProxy;


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
       // when(patientProxy.listOfPatient()).thenReturn(patients);


        this.mockMvc.perform(get("/patient/list")
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(view().name("patient/list"))
                .andExpect(status().isOk());

    }


    /**
     * Test to get the form to add a patient
     */

    public void getPatientAddTest() throws Exception {

        // GIVEN
        // WHEN
        // THEN
        this.mockMvc.perform(get("/patient/add")                )
                .andExpect(status().isOk());
    }
}
