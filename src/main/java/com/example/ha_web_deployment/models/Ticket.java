package com.example.ha_web_deployment.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Ticket_ID")
    private Integer ticketId;

    @ManyToOne
    @JoinColumn(name = "Vorstellungs_ID", nullable = false)
    private Vorstellung vorstellung;

    @ManyToOne
    @JoinColumn(name = "Gast_ID", nullable = false)
    private Gast gast;

    @Column(name = "Platz_Typ", nullable = false)
    private Boolean platzTyp; // FALSE = Parkett, TRUE = Loge

    // Getter und Setter
    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Vorstellung getVorstellung() {
        return vorstellung;
    }

    public void setVorstellung(Vorstellung vorstellung) {
        this.vorstellung = vorstellung;
    }

    public Gast getGast() {
        return gast;
    }

    public void setGast(Gast gast) {
        this.gast = gast;
    }

    public Boolean getPlatzTyp() {
        return platzTyp;
    }

    public void setPlatzTyp(Boolean platzTyp) {
        this.platzTyp = platzTyp;
    }

    // Hilfsmethode für bessere Lesbarkeit
    public boolean isLoge() {
        return platzTyp != null && platzTyp;
    }

    public boolean isParkett() {
        return platzTyp != null && !platzTyp;
    }
}