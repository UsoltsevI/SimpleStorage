package org.example.sstorage.controllers;

import org.example.sstorage.entities.SFile;
import org.example.sstorage.services.SFileService;
import org.example.sstorage.services.SUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * Controller for private user pages.
 *
 * @author UsoltsevI
 */
@Controller
@RequestMapping("/user")
public class SUserController {
    private static final Logger LOGGER  = LoggerFactory.getLogger(SUserController.class);

    @Autowired
    private SUserService sUserService;

    @Autowired
    private SFileService sFileService;

    /**
     * Get all user files.
     *
     * @param model model for adding a list of files
     * @return user files view name
     */
    @GetMapping("/{username}/files")
    @PreAuthorize("#username == authentication.name")
    public String getFiles(@PathVariable String username, Model model) {
        List<SFile> userFiles = sFileService.findAllByUsername(username);

        model.addAttribute("allFiles", userFiles);

        return "files";
    }

    /**
     * Upload a new user file.
     *
     * @return redirect page
     */
    @PostMapping("/{username}/files/upload")
    @PreAuthorize("#username == authentication.name")
    public String uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String username, Model model) {
        try {
            SFile sFile = sFileService.uploadFile(file, username);
        } catch (Exception e) {
            model.addAttribute("error", "Upload file failure");
            LOGGER.error("Failed to upload file", e);
        }
        return "redirect:/user/" + username + "/files";
    }

    /**
     * Delete user file.
     *
     * @param model
     * @return redirect page
     */
    @PostMapping("/{username}/files/delete")
    @PreAuthorize("#username == authentication.name")
    public String deleteFile(@PathVariable String username, @RequestParam("fileId") Long fileId, Model model) {
        Optional<SFile> sFile = sFileService.findById(fileId);

        if (sFile.isEmpty()) {
            model.addAttribute("error", "No such file");
            return "files";
        }

        if (!sFile.get().getUsername().equals(username)) {
            model.addAttribute("error", "Permission denied");
            return "files";
        }

        try {
            sFileService.deleteFile(sFile.get());
        } catch (Exception e) {
            model.addAttribute("error", "Delete file failure");
            LOGGER.error("Delete file failure", e);
        }

        return "redirect:/user/" + username + "/files";
    }

    /**
     * Download file.
     *
     * @param fileId sFile ID
     */
    @GetMapping("/{username}/files/{fileId}/download")
    @PreAuthorize("#username == authentication.name")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String username
            , @PathVariable Long fileId, Model model) {
        Optional<SFile> sFile = sFileService.findById(fileId);

        if (sFile.isEmpty()) {
            model.addAttribute("error", "No such file");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (!sFile.get().getUsername().equals(username)) {
            model.addAttribute("error", "Permission denied");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        try {
            byte[] bytes = sFileService.getFile(sFile.get());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION
                    , "attachment; filename='" + sFile.get().getFilename() + "'");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(bytes.length)
                    .body(new ByteArrayResource(bytes));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
