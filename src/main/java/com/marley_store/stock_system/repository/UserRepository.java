package com.marley_store.stock_system.repository;

import com.marley_store.stock_system.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
    List<User> findAll();
    <S extends User> S saveAndFlush(S entity);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    void deleteById(Long id);
    void deleteAllById(Iterable<? extends Long> ids);
}
