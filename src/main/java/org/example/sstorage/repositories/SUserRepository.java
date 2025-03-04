package org.example.sstorage.repositories;

import org.example.sstorage.entities.SUser;

import java.util.List;
import java.util.Optional;

public interface SUserRepository {
    public SUser save(SUser user);

    public Optional<SUser> findById(Long id);

    public Optional<SUser> findByUsername(String username);

    public List<SUser> findAll();

    public boolean deleteById(Long id);

    public boolean deleteByUsername(String username);
}
