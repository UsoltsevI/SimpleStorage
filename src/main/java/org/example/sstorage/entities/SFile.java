package org.example.sstorage.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.Instant;

/**
 * The file's entity for accessing and saving data in the database.
 *
 * @author UsoltsevI
 */
@Builder
@Data
public class SFile {
    /**
     * File ID in SQL storage.
     */
    @NonNull
    private Long id;

    /**
     * Owner ID.
     */
    @NonNull
    private Long userId;

    /**
     * Owner username.
     */
    @NonNull
    private String username;

    /**
     * Filename
     */
    @NonNull
    private String filename;
    
    /**
     * File bucket in object storage.
     */
    @NonNull
    private String bucket;

    /**
     * File key in object storage.
     */
    @NonNull
    private String key;

    /**
     * File content type.
     */
    @NonNull
    private String contentType;

    /**
     * File size.
     */
    @NonNull
    private Long fileSize;

    /**
     * Creation timestamp.
     */
    @NonNull
    private Instant createdAt;
}
