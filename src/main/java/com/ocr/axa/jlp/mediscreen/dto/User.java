package com.ocr.axa.jlp.mediscreen.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class User {

    private Long id;
    @NotEmpty(message = "Username is mandatory")
    @Size(min = 1, max = 100)
    private String username;
    @NotEmpty(message = "Password is mandatory")
    private String password;
    @NotEmpty(message = "pseudo is mandatory")
    @Size(min = 1, max = 100)
    private String pseudo;
    @NotEmpty(message = "role is mandatory")
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }
}
