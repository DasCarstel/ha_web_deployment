package com.example.ha_web_deployment.beans;

import com.example.ha_web_deployment.models.Gast;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("gastBean")
@SessionScoped
public class GastBean extends Bean implements Serializable {
    private Gast gast = new Gast();

    @Inject
    private TicketBean ticketBean;

    public GastBean() {
        // Default-Konstruktor
    }

    /**
     * Speichert die Gastdaten und leitet die Ticketbuchung ein
     */
    public String buchungInitiieren() {
        try (org.hibernate.Session session = openSession()) {
            session.beginTransaction();

            // Gast speichern
            session.persist(gast);
            session.getTransaction().commit();

            // Gast-ID an TicketBean übermitteln und Buchung starten
            return ticketBean.buchungDurchfuehren(gast);
        } catch (Exception e) {
            System.err.println("Fehler beim Speichern des Gastes: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Setzt den Gast zurück für neue Buchungen
     */
    public void resetGast() {
        this.gast = new Gast();
    }

    // Getter und Setter
    public Gast getGast() {
        return gast;
    }

    public void setGast(Gast gast) {
        this.gast = gast;
    }
}