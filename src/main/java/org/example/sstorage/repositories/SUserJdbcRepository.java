package org.example.sstorage.repositories;

import org.example.sstorage.entities.SRole;
import org.example.sstorage.entities.SUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public class SUserJdbcRepository implements SUserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public SUser save(SUser user){
        Instant now = Instant.now();

        String sql = "INSERT INTO users (username, password, role, created_at) " +
                "VALUES (?, ?, ?, ?) " +
                "RETURNING id";

        return jdbcTemplate.query(sql, (rs) -> {
                    if (rs.next()) {
                        return SUser.builder()
                                .id(rs.getLong("id"))
                                .username(user.getUsername())
                                .password(user.getPassword())
                                .role(user.getRole())
                                .createdAt(now)
                                .build();
                    }
                    return null;
                }
                , user.getUsername()
                , user.getPassword()
                , user.getRole().name()
                , Timestamp.from(now));
    }

    @Override
    public Optional<SUser> findById(Long id) {
        String sql = "SELECT username, password, role, created_at FROM users" +
                "WHERE id = ?";

        return jdbcTemplate.query(sql, (rs) -> {
                    if (rs.next()) {
                        return Optional.of(SUser.builder()
                                .id(id)
                                .username(rs.getString("username"))
                                .password(rs.getString("password"))
                                .role(SRole.valueOf(rs.getString("role")))
                                .createdAt(rs.getTimestamp("created_at").toInstant())
                                .build());
                    }
                    return Optional.empty();
                }
                , id);
    }

    @Override
    public Optional<SUser> findByUsername(String username) {
        String sql = "SELECT id, password, role, created_at FROM users " +
                "WHERE username = ?";

        return jdbcTemplate.query(sql, (rs) -> {
                    if (rs.next()) {
                        return Optional.of(SUser.builder()
                                .id(rs.getLong("id"))
                                .username(username)
                                .password(rs.getString("password"))
                                .role(SRole.valueOf(rs.getString("role")))
                                .createdAt(rs.getTimestamp("created_at").toInstant())
                                .build());
                    }
                    return Optional.empty();
                }
                , username);
    }

    @Override
    public List<SUser> findAll() {
        String sql = "SELECT id, username, role, created_at FROM users";

        return jdbcTemplate.query(sql, (rs, rowNum) -> SUser.builder()
                .id(rs.getLong("id"))
                .username(rs.getString("username"))
                .role(SRole.valueOf(rs.getString("role")))
                .createdAt(rs.getTimestamp("created_at").toInstant())
                .build());
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        return jdbcTemplate.update(sql, id) >= 0;
    }

    @Override
    public boolean deleteByUsername(String username) {
        String sql = "DELETE FROM users WHERE username = ?";

        return jdbcTemplate.update(sql, username) >= 0;
    }
}
