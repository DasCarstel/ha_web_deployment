package com.example.ha_web_deployment.beans;

import com.example.ha_web_deployment.models.Vorstellung;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.util.HashMap;
import java.util.Map;

@Named("saalBean")
@SessionScoped
public class SaalBean extends Bean {
    private Vorstellung selectedVorstellung;
    private String freieParkett;
    private String freieLoge;

    public SaalBean() {
        // Default-Konstruktor
    }

    /**
     * Setzt die ausgewählte Vorstellung und lädt die verfügbaren Sitzplätze
     * @param vorstellung Das ausgewählte Vorstellung-Objekt
     * @return Name der Zielseite
     */
    public String selectVorstellung(Vorstellung vorstellung) {
        this.selectedVorstellung = vorstellung;
        loadFreeSeatData(vorstellung.getVorstellungsId());
        return "Saele?faces-redirect=true";
    }

    /**
     * Lädt die Daten zu den freien Sitzplätzen mithilfe der FreeSeats-Funktion aus der Datenbank
     * @param vorstellungsId Die ID der Vorstellung
     */
    private void loadFreeSeatData(Integer vorstellungsId) {
        try (Session session = openSession()) {
            session.beginTransaction();

            // SQL-Funktion FreeSeats aufrufen
            String sql = "SELECT FreeSeats(:vorstellungsId)";
            NativeQuery<String> query = session.createNativeQuery(sql, String.class);
            query.setParameter("vorstellungsId", vorstellungsId);

            String result = query.getSingleResult();

            // Ergebnis parsen (Format: "Free Parkett: X, Free Loge: Y")
            String[] parts = result.split(",");
            freieParkett = parts[0].trim().replace("Free Parkett: ", "");
            freieLoge = parts[1].trim().replace("Free Loge: ", "");

            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Datenbankfehler: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Getter und Setter
    public Vorstellung getSelectedVorstellung() {
        return selectedVorstellung;
    }

    public void setSelectedVorstellung(Vorstellung selectedVorstellung) {
        this.selectedVorstellung = selectedVorstellung;
    }

    public String getFreieParkett() {
        return freieParkett;
    }

    public void setFreieParkett(String freieParkett) {
        this.freieParkett = freieParkett;
    }

    public String getFreieLoge() {
        return freieLoge;
    }

    public void setFreieLoge(String freieLoge) {
        this.freieLoge = freieLoge;
    }
}