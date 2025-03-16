package com.example.ha_web_deployment.beans;

import com.example.ha_web_deployment.models.Gast;
import com.example.ha_web_deployment.models.Ticket;
import com.example.ha_web_deployment.models.Vorstellung;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import java.io.Serializable;

@Named("ticketBean")
@SessionScoped
public class TicketBean extends Bean implements Serializable {
    private Integer parkettAnzahl = 0;
    private Integer logenAnzahl = 0;

    @Inject
    private SaalBean saalBean;

    public TicketBean() {
        // Default-Konstruktor
    }

    /**
     * Navigation zur Datenaufnahme-Seite
     * Prüft zuvor, ob die Anzahl der Tickets gültig ist
     */
    public String weiterZurDatenaufnahme() {
        // Prüfen ob mindestens ein Ticket ausgewählt wurde
        if (parkettAnzahl + logenAnzahl <= 0) {
            return null; // Bleibt auf der aktuellen Seite
        }

        // Prüfen ob nicht mehr als 20 Tickets ausgewählt wurden (redundant zur JavaScript-Prüfung)
        if (parkettAnzahl + logenAnzahl > 20) {
            return null;
        }

        return "Gaeste?faces-redirect=true";
    }

    /**
     * Führt die Buchung der Tickets durch
     * Wird von der GastBean aufgerufen, nachdem der Gast gespeichert wurde
     */
    public String buchungDurchfuehren(Gast gast) {
        try (Session session = openSession()) {
            session.beginTransaction();

            // Ruft die Stored Procedure auf, um die Tickets zu erstellen
            String sql = "CALL CreateTickets(:vorstellungsId, :gastId, :parkettCount, :logeCount)";
            NativeQuery<?> query = session.createNativeQuery(sql);
            query.setParameter("vorstellungsId", saalBean.getSelectedVorstellung().getVorstellungsId());
            query.setParameter("gastId", gast.getGastId());
            query.setParameter("parkettCount", parkettAnzahl);
            query.setParameter("logeCount", logenAnzahl);

            query.executeUpdate();

            session.getTransaction().commit();

            // Nach erfolgreicher Buchung die Werte zurücksetzen
            resetWerte();

            return "BuchungsBestaetigung?faces-redirect=true";
        } catch (Exception e) {
            System.err.println("Fehler bei der Buchung: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Setzt alle Werte zurück nach erfolgreicher Buchung
     */
    private void resetWerte() {
        parkettAnzahl = 0;
        logenAnzahl = 0;
    }

    // Getter und Setter
    public Integer getParkettAnzahl() {
        return parkettAnzahl;
    }

    public void setParkettAnzahl(Integer parkettAnzahl) {
        this.parkettAnzahl = parkettAnzahl;
    }

    public Integer getLogenAnzahl() {
        return logenAnzahl;
    }

    public void setLogenAnzahl(Integer logenAnzahl) {
        this.logenAnzahl = logenAnzahl;
    }
}