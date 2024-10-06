package com.devportalx.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u where u.email = ?1")
    Optional<User> findUserByEmail(String email);
    
    @Query("SELECT u FROM User u where u.guid = ?1")
    Optional<User> findUserByGuid(UUID guid);

}
