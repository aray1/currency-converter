package com.aray1.commonproblems.currencyconverter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aray1.commonproblems.currencyconverter.entity.UserEntity;

/**
 * Repository to handle CRUD operations for {@link UserEntity}.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);
}
