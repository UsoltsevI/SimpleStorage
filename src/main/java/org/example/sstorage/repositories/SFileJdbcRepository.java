package org.example.sstorage.repositories;

import org.example.sstorage.entities.FileSave;
import org.example.sstorage.entities.SFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * SFileRepository implementation using Spring JdbcTemplate.
 *
 * @author UsoltsevI
 */
@Repository
public class SFileJdbcRepository implements SFileRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public SFile save(FileSave fileSave) {
        Instant now = Instant.now();

        String sql = "INSERT INTO files (user_id, username, filename, bucket, file_key, file_size, content_type, created_at) " +
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
                        .contentType(fileSave.getContentType())
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
                , fileSave.getContentType()
                , Timestamp.from(now));
    }

    @Override
    public Optional<SFile> findById(Long id) {
        String sql = "SELECT * FROM files WHERE id = ?";

        return jdbcTemplate.query(sql, (rs) -> {
            if (rs.next()) {
                return Optional.of(extractSFile(rs));
            }
            return Optional.empty();
        }, id);
    }

    @Override
    public List<SFile> findAllByUserId(Long userId) {
        String sql = "SELECT * FROM files WHERE user_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> extractSFile(rs), userId);
    }

    @Override
    public List<SFile> findAllByUsername(String username) {
        String sql = "SELECT * FROM files WHERE username = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> extractSFile(rs), username);
    }

    @Override
    public List<SFile> findAll() {
        String sql = "SELECT * FROM files";

        return jdbcTemplate.query(sql, (rs, rowNum) -> extractSFile(rs));
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM files WHERE id = ?";

        return jdbcTemplate.update(sql, id) >= 0;
    }

    @Override
    public boolean deleteAllByUserId(Long userId) {
        String sql = "DELETE FROM files WHERE user_id = ?";

        jdbcTemplate.update(sql, userId);

        return true;
    }

    @Override
    public boolean deleteAllByUsername(String username) {
        String sql = "DELETE FROM files WHERE username = ?";

        jdbcTemplate.update(sql, username);

        return true;
    }

    /**
     * Extract SFile from ResultSet.
     *
     * @param rs ReultSet with all SFile fields
     * @return resulting SFile
     * @throws SQLException
     */
    private static SFile extractSFile(ResultSet rs) throws SQLException {
        return SFile.builder()
                .id(rs.getLong("id"))
                .userId(rs.getLong("user_id"))
                .username(rs.getString("username"))
                .filename(rs.getString("filename"))
                .bucket(rs.getString("bucket"))
                .key(rs.getString("file_key"))
                .fileSize(rs.getLong("file_size"))
                .contentType(rs.getString("content_type"))
                .createdAt(rs.getTimestamp("created_at").toInstant())
                .build();
    }
}
