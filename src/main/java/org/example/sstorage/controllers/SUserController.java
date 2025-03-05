package org.example.sstorage.controllers;

import org.example.sstorage.entities.SFile;
import org.example.sstorage.services.SFileService;
import org.example.sstorage.services.SUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
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
    @GetMapping("/{userId}/files")
    public String getFiles(@PathVariable Long userId, Model model) {
        List<SFile> userFiles = sFileService.findAllByUserId(userId);

        model.addAttribute("files", userFiles);

        return "files";
    }

    /**
     * Upload a new user file.
     *
     * @param model
     * @return
     */
    @PostMapping("/{userId}/files/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long userId, Model model) {
        try {
            SFile sFile = sFileService.uploadFile(file, userId);
        } catch (Exception e) {
            LOGGER.error("Failed to upload file", e);
        }
        model.addAttribute("files", sFileService.findAllByUserId(userId));
        return "files";
    }

    /**
     * Delete user file.
     *
     * @param model
     * @return
     */
    @DeleteMapping("/files/{fileId}/delete")
    public String deleteFile(@PathVariable Long fileId, Model model) {
        Optional<SFile> sFile = sFileService.findById(fileId);

        if (sFile.isEmpty()) {
            model.addAttribute("error", "No such file");
            return "files";
        }

        try {
            sFileService.deleteFile(sFile.get());
        } catch (Exception e) {
            LOGGER.error("Delete file failure", e);
        }

        return "files";
    }

    /**
     * Download file.
     *
     * @param fileId sFile ID
     */
    @GetMapping("/files/{fileId}/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long fileId, Model model) {
        Optional<SFile> sFile = sFileService.findById(fileId);

        if (sFile.isEmpty()) {
            model.addAttribute("error", "No such file");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
