package com.eyes.authentication.database.repositories;


import com.eyes.authentication.database.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    @Query("select user from UserEntity user " +
            " where user.email=?1")
    @Transactional
    UserEntity findByEmail(String email);
}