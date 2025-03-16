package com.example.ha_web_deployment.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.annotation.PreDestroy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named("filmBean")
@RequestScoped
public class FilmBean implements Serializable {
    private static final SessionFactory sessionFactory;
    // Keine gespeicherte Liste mehr

    static {
        try {
            // SessionFactory in einem static block initialisieren
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public FilmBean() {
        // Default-Konstruktor
    }

    /**
     * LÃ¤dt die Filmdaten aus der Datenbank bei jedem Aufruf
     * @return Liste von Film-Daten als Maps
     */
    public List<Map<String, Object>> getFilmList() {
        // Daten werden bei jedem Aufruf neu geladen
        return loadFilmData();
    }

    private List<Map<String, Object>> loadFilmData() {
        List<Map<String, Object>> filmList = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            // Angepasste SQL-Abfrage, die nur die benötigten Daten abruft
            String sql = "SELECT f.Titel, f.Genre, f.Dauer " +
                    "FROM Film f " +
                    "GROUP BY f.Film_ID " +
                    "ORDER BY f.Titel";

            var query = session.createNativeQuery(sql, Object[].class);
            List<Object[]> results = query.getResultList();

            // Ergebnisse in eine Liste von Maps umwandeln, nur mit den benötigten Feldern
            for (Object[] row : results) {
                Map<String, Object> filmData = new HashMap<>();
                filmData.put("titel", row[0]);
                filmData.put("genre", row[1]);
                filmData.put("dauer", row[2]);
                filmList.add(filmData);
            }

            session.getTransaction().commit();

        } catch (Exception e) {
            System.err.println("Datenbankfehler: " + e.getMessage());
            e.printStackTrace();
        }

        return filmList;
    }

    @PreDestroy
    public void destroy() {
        // Nichts zu tun, da die SessionFactory statisch ist
    }
}