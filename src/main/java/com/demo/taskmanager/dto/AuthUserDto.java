package com.demo.taskmanager.dto;

public class AuthUserDto {
    private String email;
    private String role;
    private boolean valid;

    public String getEmail() { return email; }
    public String getRole() { return role; }
    public boolean isValid() { return valid; }

    public void setEmail(String email) { this.email = email; }
    public void setRole(String role) { this.role = role; }
    public void setValid(boolean valid) { this.valid = valid; }
}