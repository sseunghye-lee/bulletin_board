package com.seung.pilot.business.user.repo;

import com.seung.pilot.business.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
    Optional<Users> findByUserEmail(String userEmail);
}
