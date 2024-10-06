package com.devportalx.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

/**
 * UserRepository is the Data Access layer for the user package, which is
 * responsible for interacting with the database. It is injected into the
 * Service layer.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u where u.email = ?1")
    Optional<User> findUserByEmail(String email);

    @Query("SELECT u FROM User u where u.guid = ?1")
    Optional<User> findUserByGuid(UUID guid);

}
