package com.devportalx.user;

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
    
    @PrePersist
    protected void prePersist() {
        if (guid == null) guid = UUID.randomUUID();
        if (dateJoined == null) dateJoined = LocalDate.now();
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
    public UUID getGuid() {
        return guid;
    }
    public LocalDate getDateJoined() {
        return dateJoined;
    }

    @Override
    public String toString() {
        return "User{" + email + "}";
    }

}
