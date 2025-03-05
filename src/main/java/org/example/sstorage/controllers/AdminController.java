package org.example.sstorage.controllers;

import org.example.sstorage.services.SFileService;
import org.example.sstorage.services.SUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The controller of pages accessible only to the administrator.
 *
 * @author UsoltsevI
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private SFileService sFileService;
    @Autowired
    private SUserService sUserService;

    /**
     * Get list of all users.
     *
     * @param model model for adding a list of users
     * @return view name
     */
    @GetMapping("/allusers")
    public String getAllUsers(Model model) {
        model.addAttribute("allUsers", sUserService.getAllUsers());

        return "allusers";
    }

    /**
     * Delete user by user ID.
     *
     * @param userId user ID
     * @return redirect page
     */
    @DeleteMapping("/allusers/{userId}")
    public String deleteUser(@PathVariable Long userId) {
//        Deletes user's files too
        sUserService.deleteUserById(userId);

        return "redirect:/admin/allusers";
    }

    /**
     * Get list of all files stored on the platform.
     *
     * @param model model to add list of all files
     * @return all files page
     */
    @GetMapping("/allfiles")
    public String getAllFiles(Model model) {
        model.addAttribute("allFiles", sFileService.getAllFiles());

        return "allfiles";
    }

    /**
     * Delete file by File ID.
     *
     * @param fileId sFile ID
     * @return redirect page
     */
    @DeleteMapping("/allfiles/{fileId}")
    public String deleteFile(@PathVariable Long fileId) {
        sFileService.deleteFileById(fileId);

        return "redirect:/admin/allfiles";
    }
}
