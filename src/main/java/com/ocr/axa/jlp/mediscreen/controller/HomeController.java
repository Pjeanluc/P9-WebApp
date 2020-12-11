package com.ocr.axa.jlp.mediscreen.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    private static final Logger logger = LogManager.getLogger("MediscreenHomeController");

    @RequestMapping("/")
    public String home(Model model) {
        return "home";
    }
    @RequestMapping(value = "/admin/home")
    public String getLog(Model model) {
        logger.info(" home : OK");
        return "redirect:/patient/list";
    }
}
