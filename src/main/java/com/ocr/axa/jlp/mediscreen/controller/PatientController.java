package com.ocr.axa.jlp.mediscreen.controller;

import com.ocr.axa.jlp.mediscreen.dto.Patient;
import com.ocr.axa.jlp.mediscreen.proxies.PatientProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
public class PatientController {
    private static final Logger logger = LogManager.getLogger("PatientController");

    @Autowired
    PatientProxy patientProxy;

    /**
     * Endpoint to show the list of patient
     * @param model
     * @return the patient list
     */
    @RequestMapping("/patient/list")
    public String home(Model model) {

        model.addAttribute("patients", patientProxy.listOfPatient());

        logger.info("/patient/list : OK");
        return "patient/list";
    }

    /**
     * Endpoint to display patient adding form
     * @param patient to be added
     * @return
     */
    @GetMapping("/patient/add")
    public String addPatient(Patient patient) {
        logger.info("GET /patient/add : OK");
        return "patient/add";
    }

    /**
     * Endpoint to validate the info of patient
     * @param patient, patient to be added
     * @param result technical result
     * @param model public interface model, model can be accessed and attributes can be added
     * @return
     */
    @PostMapping("/patient/validate")
    public String validate(@Valid Patient patient, BindingResult result, Model model) {
        if (!result.hasErrors()) {

            patientProxy.addPatient(patient);
            model.addAttribute("patient", patientProxy.listOfPatient());
            logger.info("POST /patient/validate : OK");
            return "redirect:/patient/list";
        }
        logger.info("/patient/validate : KO");
        return "patient/add";
    }

    /**
     * Endpoint to display patient updating form
     * @param id the patient id
     * @param model public interface model, model can be accessed and attributes can be added
     * @return patient/update if OK
     */
    @GetMapping("/patient/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Patient patient = patientProxy.findById(id);
        model.addAttribute("patient", patient);
        logger.info("GET /patient/update : OK");
        return "patient/update";
    }

    /**
     * Endpoint to validate the patient updating form
     * @param id
     * @param patient the patient id
     * @param result technical result
     * @param model public interface model, model can be accessed and attributes can be added
     * @return patient/list if ok or patient/update if ko
     */
    @PostMapping("/patient/update/{id}")
    public String updatePatient(@PathVariable("id") Long id, @Valid Patient patient, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.info("POST /patient/update : KO");
            return "patient/update";
        }

        patientProxy.updatePatient(patient);
        model.addAttribute("patients", patientProxy.listOfPatient());
        logger.info("POST /patient/update : OK");
        return "redirect:/patient/list";
    }

    /**
     * Endpoint to delete a patient
     * @param id the patient id to delete
     * @param model public interface model, model can be accessed and attributes can be added
     * @return patient/list if ok
     */
    @GetMapping("/patient/delete/{id}")
    public String deletePatient(@PathVariable("id") Long id, Model model) {
        Patient patient = patientProxy.findById(id);
        patientProxy.delete(id);
        model.addAttribute("patients", patientProxy.listOfPatient());
        logger.info("/patient/delete : OK");
        return "redirect:/patient/list";
    }
}
