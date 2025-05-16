package org.example.sstorage.repositories;

import org.example.sstorage.entities.FileSave;
import org.example.sstorage.entities.SFile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * Repository for abstracting work with the sFile SQL storage.
 *
 * @author UsoltsevI
 */
public interface SFileRepository {

    /**
     * Save file record to the database.
     *
     * @param fileSave sFile data to save
     * @return resulting sFile
     */
    public SFile save(FileSave fileSave);

    /**
     * Find file by ID.
     *
     * @param id file ID
     * @return file if found
     */
    public Optional<SFile> findById(Long id);

    /**
     * Find all files belonging to the user by user ID.
     *
     * @param userId owner ID
     * @return list of user files
     */
    public Page<SFile> findAllByUserId(Long userId, Pageable pageable);

    /**
     * Find all files belonging to the user by owner username.
     *
     * @param username owner username
     * @return list of user files
     */
    public Page<SFile> findAllByUsername(String username, Pageable pageable, String sortOption);

    /**
     * Find all files belonging to the user by owner username.
     * Filter by filename.
     *
     * @param username owner username
     * @return list of user files
     */
    public Page<SFile> findAllByUsernameAndFilename(String username, String filename, Pageable pageable, String sortOption);

    /**
     * Find all files.
     *
     * @return list of all files
     */
    public Page<SFile> findAll(Pageable pageable, String sortOption);

    /**
     * Delete sFile by ID.
     *
     * @param id file ID
     * @return true if success
     */
    public boolean deleteById(Long id);

    /**
     * Delete sFile by user ID.
     *
     * @param userId owner ID
     * @return true if success
     */
    public boolean deleteAllByUserId(Long userId);

    /**
     * Delete sFile by owner username.
     *
     * @param username owner username
     * @return true if success
     */
    public boolean deleteAllByUsername(String username);
}
