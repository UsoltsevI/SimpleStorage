package org.example.sstorage.init;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Class to initialize MinIO storage.
 *
 * @author UsoltsevI
 */
@Component
public class MinIOInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MinIOInitializer.class);

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucket;

    /**
     * Create bucket if not exists.
     */
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            if (minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
                LOGGER.info("Bucket '" + bucket + "' has been created");
            } else {
                LOGGER.info("Bucket '" + bucket + "' already exists");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
