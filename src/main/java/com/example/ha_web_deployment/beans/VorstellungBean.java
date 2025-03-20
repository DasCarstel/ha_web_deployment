package com.example.ha_web_deployment.beans;

import com.example.ha_web_deployment.models.Film;
import com.example.ha_web_deployment.models.Vorstellung;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

@Named("vorstellungBean")
@SessionScoped
public class VorstellungBean extends Bean {
    private Film selectedFilm;

    @Inject
    private TicketBean ticketBean;

    public VorstellungBean() {
        // Default-Konstruktor
    }

    /**
     * Ruft alle Vorstellungen für einen bestimmten Film ab
     * @return Liste von Vorstellung-Objekten
     */
    public List<Vorstellung> getVorstellungList() {
        if (selectedFilm == null) {
            return new ArrayList<>();
        }
        return loadVorstellungData(selectedFilm.getFilmId());
    }

    /**
     * Lädt Vorstellungsdaten für einen bestimmten Film aus der Datenbank
     * @param filmId Die ID des Films
     * @return Liste von Vorstellung-Objekten
     */
    private List<Vorstellung> loadVorstellungData(Integer filmId) {
        List<Vorstellung> vorstellungList = new ArrayList<>();

        try (Session session = openSession()) {
            session.beginTransaction();

            // HQL verwenden, um Vorstellungen für den ausgewählten Film abzurufen
            String hql = "FROM Vorstellung v WHERE v.film.filmId = :filmId ORDER BY v.vorfuehrungszeit";
            Query<Vorstellung> query = session.createQuery(hql, Vorstellung.class);
            query.setParameter("filmId", filmId);
            vorstellungList = query.getResultList();

            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Datenbankfehler: " + e.getMessage());
            e.printStackTrace();
        }

        return vorstellungList;
    }

    /**
     * Setzt den ausgewählten Film und gibt die Zielseite zurück
     * @param film Das ausgewählte Film-Objekt
     * @return Name der Zielseite
     */
    public String selectFilm(Film film) {
        this.selectedFilm = film;
        // TicketBean zurücksetzen, wenn ein neuer Film ausgewählt wird
        ticketBean.resetAllWerte();
        return "Vorstellungen?faces-redirect=true";
    }

    // Getter und Setter
    public Film getSelectedFilm() {
        return selectedFilm;
    }

    public void setSelectedFilm(Film selectedFilm) {
        this.selectedFilm = selectedFilm;
    }
}