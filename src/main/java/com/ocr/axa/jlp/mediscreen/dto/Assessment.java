package com.ocr.axa.jlp.mediscreen.dto;

public class Assessment {

    private Long assessmentPatientId;
    private int assessmentId;
    private String assessmentLevel;

    public Long getAssessmentPatientId() {
        return assessmentPatientId;
    }

    public void setAssessmentPatientId(Long assessmentPatientId) {
        this.assessmentPatientId = assessmentPatientId;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getAssessmentLevel() {
        return assessmentLevel;
    }

    public void setAssessmentLevel(String assessmentLevel) {
        this.assessmentLevel = assessmentLevel;
    }
}
