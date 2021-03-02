package com.ocr.axa.jlp.mediscreen.proxies;

import com.ocr.axa.jlp.mediscreen.config.FeignClientConfiguration;
import com.ocr.axa.jlp.mediscreen.dto.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "mediscreen-patient", url = "http://localhost:8084",configuration = FeignClientConfiguration.class)
public interface PatientProxy {

    @GetMapping(value = "/patient/all")
    List<Patient> listOfPatient();

    @GetMapping(value = "/patient")
    Patient getPatientById(@RequestBody Long id);

    @GetMapping(value = "/patient/id")
    Patient findById(@RequestParam("id") Long id);

    @PostMapping(value = "/patient/add", consumes = "application/json")
    void addPatient(@RequestBody Patient patient);

    @PostMapping(value = "/patient/update", consumes = "application/json")
    void updatePatient(@RequestBody Patient patient);

    @PostMapping(value = "/patient/delete/id", consumes = "application/json")
    void delete(@RequestParam("id") Long id);

}