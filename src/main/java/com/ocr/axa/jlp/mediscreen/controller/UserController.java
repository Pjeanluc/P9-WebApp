package com.ocr.axa.jlp.mediscreen.controller;

import com.ocr.axa.jlp.mediscreen.controller.exceptions.ProductBadRequestException;
import com.ocr.axa.jlp.mediscreen.dto.User;
import com.ocr.axa.jlp.mediscreen.proxies.UserProxy;
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
public class UserController {
    private static final Logger logger = LogManager.getLogger("UserController");

    @Autowired
    private UserProxy userProxy;

    /**
     * Endpoint to show the list of user
     * @param model
     * @return the user list
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userProxy.listOfUser());
        logger.info("/user/list : OK");
        return "user/list";
    }

    /**
     * Endpoint to display user adding form
     * @param user the user to be added
     * @return
     */
    @GetMapping("/user/add")
    public String addUser(User user) {
        logger.info("GET /user/add : OK");
        return "user/add";
    }

    /**
     * Endpoint to validate the info of user
     * @param user, user to be added
     * @param result technical result
     * @param model public interface model, model can be accessed and attributes can be added
     * @return
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {

     try {
            userProxy.addUser(user);
            model.addAttribute("users", userProxy.listOfUser());
            logger.info("POST /user/validate : OK");
            return "redirect:/user/list";
             } catch (ProductBadRequestException e) {
                 logger.info("/user/validate : add User KO");
                 return "user/add";
             }

        }
        logger.info("/user/validate : KO");
        return "user/add";
    }

    /**
     * Endpoint to display user updating form
     * @param id the user id
     * @param model public interface model, model can be accessed and attributes can be added
     * @return user/update if OK
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        User user = userProxy.getUserById(id);
        user.setPassword("");
        model.addAttribute("user", user);
        logger.info("GET /user/update : OK");
        return "user/update";
    }

    /**
     * Endpoint to validate the user updating form
     * @param id
     * @param user the user id
     * @param result technical result
     * @param model public interface model, model can be accessed and attributes can be added
     * @return user/list if ok or user/update if ko
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.info("POST /user/update : KO");
            return "user/update";
        }

        userProxy.updateUser(user);
        model.addAttribute("users", userProxy.listOfUser());
        logger.info("POST /user/update : OK");
        return "redirect:/user/list";
    }

    /**
     * Endpoint to delete a user
     * @param id the user id to delete
     * @param model public interface model, model can be accessed and attributes can be added
     * @return user/list if ok
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        userProxy.delete(id);
        model.addAttribute("users", userProxy.listOfUser());
        logger.info("/user/delete : OK");
        return "redirect:/user/list";
    }
}
