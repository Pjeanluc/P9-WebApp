package com.ocr.axa.jlp.mediscreen.proxies;

import com.ocr.axa.jlp.mediscreen.config.FeignClientConfiguration;
import com.ocr.axa.jlp.mediscreen.dto.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "mediscreen-note", url = "http://note:8082",configuration = FeignClientConfiguration.class)
public interface NoteProxy {

    @GetMapping(value = "/patHistory/all")
    Object listOfNotes();

    @GetMapping(value = "/patHistory/id/")
    Note findById(@RequestParam("id") String id);

    @PostMapping(value = "/patHistory/delete/id", consumes = "application/json")
    Boolean delete(@RequestParam("noteId") String id);

    @PostMapping(value = "/patHistory/add", consumes = "application/json")
    void addNote(@RequestBody Note note);

    @PutMapping(value = "/patHistory/update", consumes = "application/json")
    void updateNote(@RequestBody Note note);

    @GetMapping(value = "/patHistory/")
    Object getListNotesPatient(@RequestParam("patientId") Long id);
}