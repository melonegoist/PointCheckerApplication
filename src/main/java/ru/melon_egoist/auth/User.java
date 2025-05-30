package ru.melon_egoist.auth;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.melon_egoist.graph.classes.Coords;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="labusers")
public class User {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @OneToMany(mappedBy = "UsersCoords", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Coords> coords;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

    // Getters and Setters

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
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePassword = encoder.encode(password);
        this.password = encodePassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Coords> getCoords() {
        return coords;
    }

    public void setCoords(List<Coords> coords) {
        this.coords = coords;
    }
}
