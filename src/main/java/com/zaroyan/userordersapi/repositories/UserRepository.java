package com.zaroyan.userordersapi.repositories;

import com.zaroyan.userordersapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Zaroyan
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {}

