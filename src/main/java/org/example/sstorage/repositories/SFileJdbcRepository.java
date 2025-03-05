package org.example.sstorage.repositories;

import org.example.sstorage.entities.FileSave;
import org.example.sstorage.entities.SFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public class SFileJdbcRepository implements SFileRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public SFile save(FileSave fileSave) {
        Instant now = Instant.now();

        String sql = "INSERT INTO files (user_id, username, filename, bucket, file_key, file_size, file_type, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                "RETURNING id";

        return jdbcTemplate.query(sql, (rs) -> {
            if (rs.next()) {
                return SFile.builder()
                        .id(rs.getLong("id"))
                        .userId(fileSave.getUserId())
                        .username(fileSave.getUsername())
                        .filename(fileSave.getFilename())
                        .bucket(fileSave.getBucket())
                        .key(fileSave.getKey())
                        .fileSize(fileSave.getFileSize())
                        .fileType(fileSave.getFileType())
                        .createdAt(now)
                        .build();
            }
            return null;
        }, fileSave.getUserId()
                , fileSave.getUsername()
                , fileSave.getFilename()
                , fileSave.getBucket()
                , fileSave.getKey()
                , fileSave.getFileSize()
                , fileSave.getFileType()
                , Timestamp.from(now));
    }

    @Override
    public Optional<SFile> findById(Long id) {
        String sql = "SELECT id, user_id, username, filename, bucket, file_key, file_size, file_type, created_at " +
                "FROM files " +
                "WHERE id = ?";

        return jdbcTemplate.query(sql, (rs) -> {
            if (rs.next()) {
                return Optional.of(SFile.builder()
                        .id(rs.getLong("id"))
                        .username(rs.getString("username"))
                        .filename(rs.getString("filename"))
                        .userId(rs.getLong("user_id"))
                        .bucket(rs.getString("bucket"))
                        .key(rs.getString("file_key"))
                        .fileSize(rs.getLong("file_size"))
                        .fileType(rs.getString("file_type"))
                        .createdAt(rs.getTimestamp("created_at").toInstant())
                        .build());
            }
            return Optional.empty();
        }, id);
    }

    @Override
    public List<SFile> findAllByUserId(Long userId) {
        String sql = "SELECT id, user_id, username, filename, bucket, file_key, file_size, file_type, created_at " +
                "FROM files " +
                "WHERE user_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> SFile.builder()
                        .id(rs.getLong("id"))
                        .userId(rs.getLong("user_id"))
                        .username(rs.getString("username"))
                        .filename(rs.getString("filename"))
                        .bucket(rs.getString("bucket"))
                        .key(rs.getString("file_key"))
                        .fileSize(rs.getLong("file_size"))
                        .fileType(rs.getString("file_type"))
                        .createdAt(rs.getTimestamp("created_at").toInstant())
                        .build()
                , userId);
    }

    @Override
    public List<SFile> findAllByUsername(String username) {
        String sql = "SELECT id, user_id, username, filename, bucket, file_key, file_size, file_type, created_at " +
                "FROM files " +
                "WHERE username = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> SFile.builder()
                        .id(rs.getLong("id"))
                        .userId(rs.getLong("user_id"))
                        .username(rs.getString("username"))
                        .filename(rs.getString("filename"))
                        .bucket(rs.getString("bucket"))
                        .key(rs.getString("file_key"))
                        .fileSize(rs.getLong("file_size"))
                        .fileType(rs.getString("file_type"))
                        .createdAt(rs.getTimestamp("created_at").toInstant())
                        .build()
                , username);
    }



    @Override
    public List<SFile> findAll() {
        String sql = "SELECT id, user_id, username, filename, bucket, file_key, file_type, file_size, created_at " +
                "FROM files";

        return jdbcTemplate.query(sql, (rs, rowNum) -> SFile.builder()
                        .id(rs.getLong("id"))
                        .userId(rs.getLong("user_id"))
                        .username(rs.getString("username"))
                        .filename(rs.getString("filename"))
                        .bucket(rs.getString("bucket"))
                        .key(rs.getString("file_key"))
                        .fileSize(rs.getLong("file_size"))
                        .fileType(rs.getString("file_type"))
                        .createdAt(rs.getTimestamp("created_at").toInstant())
                        .build());
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM files WHERE id = ?";

        return jdbcTemplate.update(sql, id) >= 0;
    }

    @Override
    public boolean deleteByUserId(Long userId) {
        String sql = "DELETE FROM files WHERE user_id = ?";

        jdbcTemplate.update(sql, userId);

        return true;
    }

    @Override
    public boolean deleteByUsername(String username) {
        String sql = "DELETE FROM files WHERE username = ?";

        jdbcTemplate.update(sql, username);

        return true;
    }
}
