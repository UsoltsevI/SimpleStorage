package org.example.sstorage.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Entity to save sFile data.
 * Used in the save method in SFileRepository.
 *
 * @author UsoltsevI
 */
@Builder
@Data
public class FileSave {
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
     * Filename.
     */
    @NonNull
    private String filename;
    
    /**
     * File bucket.
     */
    @NonNull
    private String bucket;

    /**
     * File key.
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
}
