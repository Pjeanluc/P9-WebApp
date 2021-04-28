package com.ocr.axa.jlp.mediscreen.controller;

import com.ocr.axa.jlp.mediscreen.dto.Note;
import com.ocr.axa.jlp.mediscreen.proxies.AssessmentProxy;
import com.ocr.axa.jlp.mediscreen.proxies.NoteProxy;
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
public class NoteController {
    private static final Logger logger = LogManager.getLogger("noteController");

    @Autowired
    NoteProxy noteProxy;

    @Autowired
    PatientProxy patientProxy;

    @Autowired
    AssessmentProxy assessmentProxy;

    /**
     * Endpoint to show the list of note
     * @param model
     * @return the note list
     */
    @RequestMapping("/patHistory/list")
    public String home(Model model) {

        model.addAttribute("notes", noteProxy.listOfNotes());

        logger.info("/patHistory/list : OK");
        return "patHistory/list";
    }

    /**
     * Endpoint to show the list of note for one patient
     * @param model
     * @return the note list of a patient
     */
    @GetMapping(value = "/patHistory/{id}/list")
    public String getNotesForAPatient(@PathVariable("id") Long id, Model model) {
        model.addAttribute("notes", noteProxy.getListNotesPatient(id));
        model.addAttribute("patient",patientProxy.findById(id));
        model.addAttribute("assessment",assessmentProxy.getAssessmentPatient(id,1));
        return "patHistory/list";
    }

    /**
     * Endpoint to display note adding form
     * @param model (note) to be added
     * @return
     */
    @GetMapping("/patHistory/{id}/add")
    public String addnote(@PathVariable("id") long id, Model model) {
        model.addAttribute("note", new Note());
        logger.info("GET /patHistory/add : OK");
        return "patHistory/add";
    }

    /**
     * Endpoint to validate the info of note
     * @param note, note to be added
     * @return
     */
    @PostMapping("/patHistory/{id}/validate")
    public String validate(@PathVariable("id") long id, @Valid Note note) {
        note.setId(null);
        note.setPatientId(id);
        noteProxy.addNote(note);
        logger.info("POST /patHistory/validate : OK");
        return "redirect:/patHistory/"+id+"/list";
    }

    /**
     * Endpoint to display note updating form
     * @param id the note id
     * @param model public interface model, model can be accessed and attributes can be added
     * @return note/update if OK
     */
    @GetMapping("/patHistory/{id}/update/{noteId}")
    public String showUpdateForm(@PathVariable("id") Long id,@PathVariable("noteId") String noteId, Model model) {
        Note note = noteProxy.findById(noteId);
        model.addAttribute("note", note);
        logger.info("GET /patHistory/update : OK");
        return "patHistory/update";
    }

    /**
     * Endpoint to validate the note updating form
     * @param id
     * @param note the note id
     * @param result technical result
     * @param model public interface model, model can be accessed and attributes can be added
     * @return note/list if ok or note/update if ko
     */
    @PostMapping("/patHistory/{id}/update/{noteId}")
    public String updateNote (@PathVariable("id") Long id, @PathVariable("noteId") String noteId, @Valid Note note, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.info("POST /patHistory/update : KO");
            return "patHistory/"+id+"/update";
        }
        note.setPatientId(id);
        noteProxy.updateNote(note);
        model.addAttribute("notes", noteProxy.listOfNotes());
        logger.info("POST /patHistory/update : OK");
        return "redirect:/patHistory/"+id+"/list";
    }

    /**
     * Endpoint to delete a note
     * @param id the note id to delete
     * @param model public interface model, model can be accessed and attributes can be added
     * @return note/list if ok
     */
    @GetMapping("/patHistory/{id}/delete/{noteId}")
    public String deleteNote(@PathVariable("id") Long id, @PathVariable("noteId") String noteId, Model model) {
        noteProxy.delete(noteId);
        model.addAttribute("notes", noteProxy.listOfNotes());
        logger.info("/patHistory/delete : OK");
        return "redirect:/patHistory/"+id+"/list";
    }

}
