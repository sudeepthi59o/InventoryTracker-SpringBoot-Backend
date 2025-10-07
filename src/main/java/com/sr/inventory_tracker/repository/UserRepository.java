package com.sr.inventory_tracker.repository;

import com.sr.inventory_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String username);

    boolean existsByUserName(String userName);

    @Query("select u.role from User u where u.userName = ?1")
    String findRoleByUserName(String userName);
}
