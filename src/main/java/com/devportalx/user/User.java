package com.devportalx.user;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private UUID guid;
    private LocalDate dateJoined;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.guid = UUID.randomUUID();
        this.dateJoined = LocalDate.now();
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
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
        return "User [" + email + "]";
    }

}
