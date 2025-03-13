package com.example.ha_web_deployment.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Film")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Film_ID")
    private Integer filmId;

    @Column(name = "Titel", nullable = false)
    private String titel;

    @Column(name = "Genre")
    private String genre;

    @Column(name = "Dauer", nullable = false)
    private Double dauer;

    @OneToMany(mappedBy = "film")
    private List<Vorstellung> vorstellungen;

    // Getter und Setter
    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Double getDauer() {
        return dauer;
    }

    public void setDauer(Double dauer) {
        this.dauer = dauer;
    }

    public List<Vorstellung> getVorstellungen() {
        return vorstellungen;
    }

    public void setVorstellungen(List<Vorstellung> vorstellungen) {
        this.vorstellungen = vorstellungen;
    }
}