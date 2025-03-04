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
     * File bucket in S3.
     */
    private String bucket;

    /**
     * File key in S3.
     */
    private String key;

    /**
     * File type == file suffix.
     */
    private String fileType;

    /**
     * Creation timestamp.
     */
    private Instant createdAt;
}
