package com.devportalx.user;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String email;
    @JsonIgnore
    private byte[] password;
    private UUID guid;
    private LocalDate dateJoined;

    public User() {
    }

    public User(String email, byte[] password) {
        this.email = email;
        this.password = password;
        this.guid = UUID.randomUUID();
        this.dateJoined = LocalDate.now();

    }

    public User(String email, String password) throws NoSuchAlgorithmException {
        this.email = email;
        this.password = getHashedDigest(password);
        this.guid = UUID.randomUUID();
        this.dateJoined = LocalDate.now();
    }

    @PrePersist
    protected void prePersist() {
        if (guid == null)
            guid = UUID.randomUUID();
        if (dateJoined == null)
            dateJoined = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    /**
     * Sets user password.
     * 
     * PRE: length > 0
     * 
     * @param password
     * @throws NoSuchAlgorithmException
     */
    public void setPassword(String password) throws NoSuchAlgorithmException {
        if (password.equals("")) {
            return;
        }
        this.password = getHashedDigest(password);
    }

    public UUID getGuid() {
        return guid;
    }

    public LocalDate getDateJoined() {
        return dateJoined;
    }

    /**
     * Used for hashing passwords so that raw values aren't saved to db
     * 
     * @param text
     * @return hash of the passed in text
     * @throws NoSuchAlgorithmException
     */
    public static byte[] getHashedDigest(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(UserMessage.HASHING_ALGORITHM);
        return digest.digest(text.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String toString() {
        return "User {" + email + "}";
    }

}
