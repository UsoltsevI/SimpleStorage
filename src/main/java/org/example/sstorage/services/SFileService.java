package org.example.sstorage.services;

import io.minio.*;
import lombok.RequiredArgsConstructor;
import org.example.sstorage.entities.FileSave;
import org.example.sstorage.entities.SFile;
import org.example.sstorage.entities.SUser;
import org.example.sstorage.repositories.SFileRepository;
import org.example.sstorage.repositories.SUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for working with sFile repository.
 *
 * @author UsoltsevI
 */
@RequiredArgsConstructor
@Service
public class SFileService {

    /**
     * SFileRepository to manage the files in the SQL storage.
     * Initialized using Spring (RequiredArgsConstructor)
     */
    private final SFileRepository sFileRepository;

    /**
     * SUserRepository to get owners info from the user storage.
     * Initialized using Spring (RequiredArgsConstructor)
     */
    private final SUserRepository sUserRepository;

    /**
     * MinioClient to manage the files in the MinIo Storage.
     * Initialized using Spring (RequiredArgsConstructor)
     */
    private final MinioClient minioClient;

    /**
     * The name of the storage bucket in the MinIO storage.
     */
    @Value("${minio.bucket-name}")
    private String bucketName;

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
     * Find all files belonging to the user by owner username.
     *
     * @param username owner username
     * @return list of user files
     */
    public List<SFile> findAllByUsername(String username) {
        return sFileRepository.findAllByUsername(username);
    }

    /**
     * Get all sFiles.
     *
     * @return list of all files
     */
    public List<SFile> getAllFiles() {
        return sFileRepository.findAll();
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
     * Save file to the database.
     *
     * @param file file to save
     * @param userId owner ID
     * @return resulting sFile
     */
    public SFile uploadFile(MultipartFile file, Long userId) {
        Optional<SUser> userOptional = sUserRepository.findById(userId);

        return userOptional.map(sUser -> uploadFile(file, sUser)).orElse(null);
    }

    /**
     * Save file to the database.
     *
     * @param file file to save
     * @param username owner username
     * @return resulting sFile
     */
    public SFile uploadFile(MultipartFile file, String username) {
        Optional<SUser> userOptional = sUserRepository.findByUsername(username);

        return userOptional.map(sUser -> uploadFile(file, sUser)).orElse(null);
    }

    /**
     * Save file to the database.
     *
     * @param file file to save
     * @param sUser owner
     * @return resulting sFile
     */
    public SFile uploadFile(MultipartFile file, SUser sUser) {
        try {
            InputStream inputStream = file.getInputStream();

            String key = generateFileKey(file);

//            Save file to the object storage
            minioClient.putObject(PutObjectArgs.builder()
                    .contentType(file.getContentType())
                    .bucket(bucketName)
                    .object(key)
                    .stream(inputStream, file.getSize(), -1)
                    .build());

//            Save file metadata to the SQL storage
            return sFileRepository.save(FileSave.builder()
                    .userId(sUser.getId())
                    .username(sUser.getUsername())
                    .filename(file.getOriginalFilename())
                    .fileSize(file.getSize())
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get file content.
     *
     * @param sFile sFile with content metadata
     * @return file content as bytes
     */
    public byte[] getFile(SFile sFile) {
        try {
            GetObjectResponse response = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(sFile.getKey())
                    .build());
            return response.readAllBytes();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete sFile by ID.
     *
     * @param id file ID
     * @return true if success
     */
    public boolean deleteFileById(Long id) {
        Optional<SFile> sFile = sFileRepository.findById(id);

        return sFile.filter(this::deleteFile).isPresent();

    }

    /**
     * Delete sFile.
     *
     * @param sFile sFile to delete
     * @return true if success
     */
    public boolean deleteFile(SFile sFile) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(sFile.getKey())
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        sFileRepository.deleteById(sFile.getId());

        return true;
    }

    /**
     * Delete all files by owner ID.
     *
     * @param userId owner ID.
     * @return true if success
     */
    public boolean deleteAllByUserId(Long userId) {
        List<SFile> files = sFileRepository.findAllByUserId(userId);

        for (SFile file : files) {
            deleteFile(file);
        }

        return true;
    }

    /**
     * Delete all files by owner username.
     *
     * @param username owner username
     * @return true if success
     */
    private boolean deleteAllByUsername(String username) {
        List<SFile> files = sFileRepository.findAllByUsername(username);

        for (SFile file : files) {
            deleteFile(file);
        }

        return true;
    }

    /**
     * Generate key to store file in object storage.
     *
     * @param file file to store
     * @return generated key
     */
    private String generateFileKey(MultipartFile file) {
        return UUID.randomUUID().toString() + "_" + file.getName().replace(" ", "");
    }
}
