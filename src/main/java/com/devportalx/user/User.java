package com.devportalx.user;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue()
    private Long id;
    private String email;
    private String password;
    private LocalDate dateJoined;


    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.dateJoined = LocalDate.now();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public LocalDate getDateJoined() {
        return dateJoined;
    }

    @Override
    public String toString() {
        return "User [" + email + "]";
    }
}
