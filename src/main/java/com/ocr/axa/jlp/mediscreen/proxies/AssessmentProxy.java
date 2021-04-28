package com.ocr.axa.jlp.mediscreen.proxies;

import com.ocr.axa.jlp.mediscreen.config.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mediscreen-assessment", url = "http://assessment:8083",configuration = FeignClientConfiguration.class)
public interface AssessmentProxy {

    @GetMapping(value = "/assessment/id")
    Object getAssessmentPatient(@RequestParam("patientId") Long id, @RequestParam("assessmentId") int idAssessment);
}
