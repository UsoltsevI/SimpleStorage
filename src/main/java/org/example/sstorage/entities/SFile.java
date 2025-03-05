package org.example.sstorage.entities;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class SFile {
    /**
     * File ID in SQL storage.
     */
    private Long id;

    /**
     * Owner ID.
     */
    private Long userId;

    /**
     * Owner username.
     */
    private String username;

    /**
     * Filename
     */
    private String filename;
    
    /**
     * File bucket in S3.
     */
    private String bucket;

    /**
     * File key in S3.
     */
    private String key;

    /**
     * File type == file content type.
     */
    private String fileType;

    /**
     * File size.
     */
    private Long fileSize;

    /**
     * Creation timestamp.
     */
    private Instant createdAt;
}
