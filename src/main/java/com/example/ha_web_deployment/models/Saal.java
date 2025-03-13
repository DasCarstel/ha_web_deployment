package com.example.ha_web_deployment.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Saal")
public class Saal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Saal_ID")
    private Integer saalId;

    @Column(name = "max_Parkettplaetze", nullable = false)
    private Integer maxParkettplaetze;

    @Column(name = "max_Logenplaetze", nullable = false)
    private Integer maxLogenplaetze;

    @Column(name = "Ausstattung")
    private String ausstattung;

    @OneToMany(mappedBy = "saal")
    private List<Vorstellung> vorstellungen;

    // Getter und Setter
    public Integer getSaalId() {
        return saalId;
    }

    public void setSaalId(Integer saalId) {
        this.saalId = saalId;
    }

    public Integer getMaxParkettplaetze() {
        return maxParkettplaetze;
    }

    public void setMaxParkettplaetze(Integer maxParkettplaetze) {
        this.maxParkettplaetze = maxParkettplaetze;
    }

    public Integer getMaxLogenplaetze() {
        return maxLogenplaetze;
    }

    public void setMaxLogenplaetze(Integer maxLogenplaetze) {
        this.maxLogenplaetze = maxLogenplaetze;
    }

    public String getAusstattung() {
        return ausstattung;
    }

    public void setAusstattung(String ausstattung) {
        this.ausstattung = ausstattung;
    }

    public List<Vorstellung> getVorstellungen() {
        return vorstellungen;
    }

    public void setVorstellungen(List<Vorstellung> vorstellungen) {
        this.vorstellungen = vorstellungen;
    }
}