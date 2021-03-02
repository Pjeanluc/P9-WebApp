package com.ocr.axa.jlp.mediscreen.proxies;

import com.ocr.axa.jlp.mediscreen.config.FeignClientConfiguration;
import com.ocr.axa.jlp.mediscreen.dto.Note;
import com.ocr.axa.jlp.mediscreen.dto.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "mediscreen-note", url = "http://localhost:8082",configuration = FeignClientConfiguration.class)
public interface NoteProxy {

    @GetMapping(value = "/note/all")
    Object listOfNotes();

    @GetMapping(value = "/note/id/")
    Note findById(@RequestParam("id") String id);

    @PostMapping(value = "/note/delete/id", consumes = "application/json")
    Boolean delete(@RequestParam("noteId") String id);

    @PostMapping(value = "/note/add", consumes = "application/json")
    void addNote(@RequestBody Note note);

    @PutMapping(value = "/note/update", consumes = "application/json")
    void updateNote(@RequestBody Note note);

    @GetMapping(value = "/note/")
    Object getListNotesPatient(@RequestParam("patientId") Long id);
}