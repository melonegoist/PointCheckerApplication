package ru.melon_egoist.graph.classes;


import jakarta.persistence.*;
import ru.melon_egoist.auth.User;

@Entity
@Table(name = "coords")
public class Coords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String x;
    private String y;
    private String r;

    private String inArea;

    private String owner;

    @ManyToOne
    @JoinColumn(name="UsersCoords_id")
    private User UsersCoords;

    public Coords(String x, String y, String r, String owner, String inArea) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.owner = owner;
        this.inArea = inArea;
    }

    public void setX(String x) {
        this.x = x;
    }

    public void setY(String y) {
        this.y = y;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getR() {
        return r;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInArea() {
        return inArea;
    }

    public void setInArea(String inArea) {
        this.inArea = inArea;
    }

    public User getUsersCoords() {
        return UsersCoords;
    }

    public void setUsersCoords(User usersCoords) {
        UsersCoords = usersCoords;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Coords() {

    }
}
