package org.example.sstorage.repositories;

import org.example.sstorage.entities.FileSave;
import org.example.sstorage.entities.SFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<SFile> findAllByUserId(Long userId, Pageable pageable) {
        String sql = "SELECT * FROM files WHERE user_id = ? ORDER BY id LIMIT ? OFFSET ?";
        String countSql = "SELECT COUNT(*) FROM files WHERE user_id = ?";

        List<SFile> list = jdbcTemplate.query(sql, (rs, rowNum) -> extractSFile(rs)
                , userId
                , pageable.getPageSize()
                , pageable.getOffset());

        Long total = jdbcTemplate.queryForObject(countSql, Long.class, userId);

        if (total == null) {
            total = 0L;
        }

        return new PageImpl<>(list, pageable, total);
    }

    @Override
    public Page<SFile> findAllByUsername(String username, Pageable pageable, String sortOption) {
        String sql = switch (sortOption) {
            case "file_size" -> "SELECT * FROM files WHERE username = ? ORDER BY file_size LIMIT ? OFFSET ?";
            case "created_at" -> "SELECT * FROM files WHERE username = ? ORDER BY created_at LIMIT ? OFFSET ?";
            default -> "SELECT * FROM files WHERE username = ? ORDER BY id LIMIT ? OFFSET ?";
        };

        String countSql = "SELECT COUNT(*) FROM files WHERE username = ?";

        List<SFile> list = jdbcTemplate.query(sql, (rs, rowNum) -> extractSFile(rs)
                , username
                , pageable.getPageSize()
                , pageable.getOffset());

        Long total = jdbcTemplate.queryForObject(countSql, Long.class, username);

        return new PageImpl<>(list, pageable, total == null ? 0L : total);
    }

    @Override
    public Page<SFile> findAllByUsernameAndFilename(String username, String filename, Pageable pageable, String sortOption) {
        String sql = switch (sortOption) {
            case "file_size" -> "SELECT * FROM files WHERE username = ? AND filename LIKE ? ORDER BY file_size LIMIT ? OFFSET ?";
            case "created_at" -> "SELECT * FROM files WHERE username = ? AND filename LIKE ? ORDER BY created_at LIMIT ? OFFSET ?";
            default -> "SELECT * FROM files WHERE username = ? AND filename LIKE ? ORDER BY id LIMIT ? OFFSET ?";
        };

        String countSql = "SELECT COUNT(*) FROM files WHERE username = ? AND filename LIKE ?";
        String filenamePrefix = filename + "%";

        List<SFile> list = jdbcTemplate.query(sql, (rs, rowNum) -> extractSFile(rs)
            , username
            , filenamePrefix
            , pageable.getPageSize()
            , pageable.getOffset());

        Long total = jdbcTemplate.queryForObject(countSql, Long.class, username, filenamePrefix);

        return new PageImpl<>(list, pageable, total == null ? 0L : total);
    }

    @Override
    public Page<SFile> findAll(Pageable pageable, String sortOption) {
        String sql = switch(sortOption) {
            case "file_size" -> "SELECT * FROM files ORDER BY file_size LIMIT ? OFFSET ?";
            case "owner" ->  "SELECT * FROM files ORDER BY user_id LIMIT ? OFFSET ?";
            case "created_at" -> "SELECT * FROM files ORDER BY created_at LIMIT ? OFFSET ?";
            default -> "SELECT * FROM files ORDER BY id LIMIT ? OFFSET ?";
        };

        String countSql = "SELECT COUNT(*) FROM files";

        List<SFile> list = jdbcTemplate.query(sql, (rs, rowNum) -> extractSFile(rs)
                , pageable.getPageSize()
                , pageable.getOffset());

        Long total = jdbcTemplate.queryForObject(countSql, Long.class);

        return new PageImpl<>(list, pageable, total == null ? 0L : total);
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
