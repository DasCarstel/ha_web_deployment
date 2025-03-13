package com.example.ha_web_deployment.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Vorstellung")
public class Vorstellung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Vorstellungs_ID")
    private Integer vorstellungsId;

    @ManyToOne
    @JoinColumn(name = "Film_ID", nullable = false)
    private Film film;

    @Column(name = "Vorfuehrungszeit", nullable = false)
    private LocalDateTime vorfuehrungszeit;

    @ManyToOne
    @JoinColumn(name = "Saal_ID", nullable = false)
    private Saal saal;

    @OneToMany(mappedBy = "vorstellung")
    private List<Ticket> tickets;

    // Getter und Setter
    public Integer getVorstellungsId() {
        return vorstellungsId;
    }

    public void setVorstellungsId(Integer vorstellungsId) {
        this.vorstellungsId = vorstellungsId;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public LocalDateTime getVorfuehrungszeit() {
        return vorfuehrungszeit;
    }

    public void setVorfuehrungszeit(LocalDateTime vorfuehrungszeit) {
        this.vorfuehrungszeit = vorfuehrungszeit;
    }

    public Saal getSaal() {
        return saal;
    }

    public void setSaal(Saal saal) {
        this.saal = saal;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}