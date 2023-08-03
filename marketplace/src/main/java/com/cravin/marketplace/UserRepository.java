package com.cravin.marketplace;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import jakarta.transaction.Transactional;
 
/**
 * JPA Repository to Store all registered users.
 * See User.java for entity class & table name
 */

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Looks up user based on username
     * @param username
     * @return User associated with username
     */
    @Query("SELECT u FROM User u WHERE u.username = :username" )
    User findByUsername(@Param("username") String username);

    /**
     * Updates the password of given user
     * @param user
     * @param pass
     */
    @Transactional
    @Modifying
    @Query("update User u SET u.password = :pass WHERE u.username = :user")
    void updatePassword(String user, String pass);
}
