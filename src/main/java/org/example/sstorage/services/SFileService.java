package org.example.sstorage.services;

import lombok.RequiredArgsConstructor;
import org.example.sstorage.entities.FileSave;
import org.example.sstorage.entities.SFile;
import org.example.sstorage.repositories.SFileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for working with sFile repository.
 *
 * @author UsoltsevI
 */
@RequiredArgsConstructor
@Service
public class SFileService {
    private final SFileRepository sFileRepository;

    /**
     * Find all files.
     *
     * @return list of all files
     */
    public List<SFile> findAll() {
        return sFileRepository.findAll();
    }

    /**
     * Find all files belonging to the user by user ID.
     *
     * @param userId owner ID
     * @return list of user files
     */
    public List<SFile> findAllByUserId(Long userId) {
        return sFileRepository.findAllByUserId(userId);
    }

    /**
     * Find file by ID.
     *
     * @param id file ID
     * @return file if found
     */
    public Optional<SFile> findById(Long id) {
        return sFileRepository.findById(id);
    }

    /**
     * Save file record to the database.
     *
     * @param fileSave sFile data to save
     * @return resulting sFile
     */
    public SFile saveFile(FileSave fileSave) {
        return sFileRepository.save(fileSave);
    }

    /**
     * Delete sFile by ID.
     *
     * @param id file ID
     * @return true if success
     */
    public boolean deleteFileById(Long id) {
        return sFileRepository.deleteById(id);
    }
}
