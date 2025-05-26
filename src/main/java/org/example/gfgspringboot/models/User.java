package org.example.gfgspringboot.models;

import jakarta.persistence.*;
import lombok.NonNull;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    @NonNull
    private String username;

    @Column(length = 10)
    @NonNull
    private String password;

    @NonNull
    private String email;

    @NonNull
    private UserRole role;

    @NonNull
    private UserStatus status;

    @OneToMany(mappedBy = "contributor", cascade = CascadeType.ALL)
    private List<Asset> contributions;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public @NonNull String getUsername() {
        return username;
    }

    public List<Asset> getContributions() {
        return contributions;
    }

    public void setContributions(List<Asset> contributions) {
        this.contributions = contributions;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public @NonNull String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public @NonNull String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public @NonNull UserRole getRole() {
        return role;
    }

    public void setRole(@NonNull UserRole role) {
        this.role = role;
    }

    public @NonNull UserStatus getStatus() {
        return status;
    }

    public void setStatus(@NonNull UserStatus status) {
        this.status = status;
    }
}
