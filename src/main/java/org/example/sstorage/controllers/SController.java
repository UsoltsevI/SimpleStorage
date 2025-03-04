package org.example.sstorage.controllers;

import org.example.sstorage.services.SUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SController {
    @Autowired
    private SUserService sUserService;

    @GetMapping("/files")
    public String getFiles(Model model) {
        return "files";
    }

    @PostMapping("/files/upload")
    public String uploadFile(Model model) {
        return "files";
    }

    @DeleteMapping("/files/delete")
    public String deleteFile(Model model) {
        return "files";
    }
}
