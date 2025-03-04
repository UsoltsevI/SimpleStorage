package org.example.sstorage.controllers;

import org.example.sstorage.services.SUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for creating new users and deleting them.
 *
 * @author UsoltsevI
 */
@Controller
public class RegisterController {
    @Autowired
    private SUserService sUserService;

    /**
     * Get register page.
     *
     * @return view name for the registration page
     */
    @GetMapping("/register")
    public String getRegisterPage() {
        return "register";
    }

    /**
     * Register a new user.
     *
     * @param username username
     * @param password password
     * @param model model for recording errors if they occur
     * @return redirect page
     */
    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username
            , @RequestParam("password") String password
            , Model model) {
        if (sUserService.registerNewUser(username, password)) {
            return "redirect:/login";
        }
        model.addAttribute("error", "Username already used");
        return "register";
    }
}
