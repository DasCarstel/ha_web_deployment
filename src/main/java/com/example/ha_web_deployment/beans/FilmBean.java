package com.example.ha_web_deployment.beans;

import com.example.ha_web_deployment.models.Film;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

@Named("filmBean")
@RequestScoped
public class FilmBean extends Bean {

    public FilmBean() {
        // Default-Konstruktor
    }

    /**
     * Lädt die Filmdaten aus der Datenbank bei jedem Aufruf
     * @return Liste von Film-Objekten
     */
    public List<Film> getFilmList() {
        return loadFilmData();
    }

    private List<Film> loadFilmData() {
        List<Film> filmList = new ArrayList<>();

        try (Session session = openSession()) {
            session.beginTransaction();

            // HQL verwenden, um Film-Objekte direkt abzurufen
            String hql = "FROM Film f ORDER BY f.titel";
            filmList = session.createQuery(hql, Film.class).getResultList();

            session.getTransaction().commit();

        } catch (Exception e) {
            System.err.println("Datenbankfehler: " + e.getMessage());
            e.printStackTrace();
        }

        return filmList;
    }
}