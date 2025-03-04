package org.example.sstorage.controllers;

import org.example.sstorage.services.SUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private SUserService sUserService;

    @GetMapping("/allusers")
    public String getAllUsers(Model model) {
        model.addAttribute("allUsers", sUserService.getAllUsers());
        return "allusers";
    }
}
