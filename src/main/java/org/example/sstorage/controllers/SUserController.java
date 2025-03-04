package org.example.sstorage.controllers;

import org.example.sstorage.services.SUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller for private user pages.
 *
 * @author UsoltsevI
 */
@Controller
public class SUserController {
    @Autowired
    private SUserService sUserService;

    /**
     * Get all user files.
     *
     * @param model model for adding a list of files
     * @return user files view name
     */
    @GetMapping("/{userId}/files")
    public String getFiles(Model model) {
        return "files";
    }

    /**
     * Upload a new user file.
     *
     * @param model
     * @return
     */
    @PostMapping("/{userId}/files/upload")
    public String uploadFile(Model model) {
        return "files";
    }

    /**
     * Delete user file.
     *
     * @param model
     * @return
     */
    @DeleteMapping("/files/delete")
    public String deleteFile(Model model) {
        return "files";
    }
}
