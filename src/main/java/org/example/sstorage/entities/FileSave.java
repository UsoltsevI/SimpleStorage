package org.example.sstorage.entities;

import lombok.Builder;
import lombok.Data;

/**
 * Entity to save sFile data.
 * Used in the save method in SFileRepository.
 */
@Builder
@Data
public class FileSave {
    /**
     * Owner ID.
     */
    private Long userId;

    /**
     * Filename.
     */
    private String filename;
    
    /**
     * File bucket.
     */
    private String bucket;

    /**
     * File key.
     */
    private String key;

    /**
     * File type == file content type.
     */
    private String fileType;
}
