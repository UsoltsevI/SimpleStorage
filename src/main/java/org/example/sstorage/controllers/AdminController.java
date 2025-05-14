package org.example.sstorage.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.sstorage.entities.SFile;
import org.example.sstorage.entities.SUser;
import org.example.sstorage.services.SFileService;
import org.example.sstorage.services.SUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String getAllUsers(Model model
            , HttpSession session
            , @RequestParam(name = "page", defaultValue = "0") int pageNumber) {
        int pageSize = 10;

        Page<SUser> users = sUserService.getAllUsers(pageNumber, pageSize);

        model.addAttribute("allUsers", users.toList());
        model.addAttribute("pageNumber", users.getNumber());
        model.addAttribute("totalPages", users.getTotalPages());


        return "allusers";
    }

    /**
     * Delete user by user ID.
     *
     * @param userId user ID
     * @return redirect page
     */
    @PostMapping("/allusers/delete")
    public String deleteUser(@RequestParam("userId") Long userId) {
//        The call of the function Deletes user's files too
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
    public String getAllFiles(Model model
            , HttpSession session
            , @RequestParam(name = "page", defaultValue = "0") int pageNumber
            , @RequestParam(name = "sort", defaultValue = "id") String sortOption
            , @RequestParam(name = "searchUsername", defaultValue = "") String searchUsername
            , @RequestParam(name = "searchFilename", defaultValue = "") String searchFilename) {
        int pageSize = 10;

        Page<SFile> files = "".equals(searchUsername) || "".equals(searchFilename)
            ? sFileService.getAllFiles(pageNumber, pageSize, sortOption)
            : sFileService.findAllByUsernameAndFilename(searchUsername, searchFilename, pageNumber, pageSize, sortOption);

        model.addAttribute("allFiles", files);
        model.addAttribute("pageNumber", files.getNumber());
        model.addAttribute("totalPages", files.getTotalPages());
        model.addAttribute("sortOption", sortOption);
        model.addAttribute("searchFilename", searchFilename);
        model.addAttribute("searchUsername", searchUsername);

        return "allfiles";
    }

    /**
     * Delete file by File ID.
     *
     * @param fileId sFile ID
     * @return redirect page
     */
    @PostMapping("/allfiles/delete")
    public String deleteFile(@RequestParam("fileId") Long fileId) {
        sFileService.deleteFileById(fileId);

        return "redirect:/admin/allfiles";
    }
}
