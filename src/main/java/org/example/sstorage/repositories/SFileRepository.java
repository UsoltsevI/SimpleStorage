package org.example.sstorage.repositories;

import org.example.sstorage.entities.FileSave;
import org.example.sstorage.entities.SFile;

import java.util.List;
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
    public List<SFile> findAllByUserId(Long userId);

    /**
     * Find all files belonging to the user by owner username.
     *
     * @param username owner username
     * @return list of user files
     */
    public List<SFile> findAllByUsername(String username);

    /**
     * Find all files.
     *
     * @return list of all files
     */
    public List<SFile> findAll();

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
    public boolean deleteByUserId(Long userId);

    /**
     * Delete sFile by owner username.
     *
     * @param username owner username
     * @return true if success
     */
    public boolean deleteByUsername(String username);
}
