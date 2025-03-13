package com.example.ha_web_deployment.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Gast")
public class Gast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Gast_ID")
    private Integer gastId;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Email")
    private String email;

    @OneToMany(mappedBy = "gast")
    private List<Ticket> tickets;

    // Getter und Setter
    public Integer getGastId() {
        return gastId;
    }

    public void setGastId(Integer gastId) {
        this.gastId = gastId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}