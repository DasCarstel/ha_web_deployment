package com.example.ha_web_deployment.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import jakarta.annotation.PostConstruct;
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
    private static SessionFactory sessionFactory;
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
     * Lädt die Filmdaten aus der Datenbank bei jedem Aufruf
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

            // Native SQL-Query verwenden (anstelle von HQL)
            String sql = "SELECT f.Film_ID, f.Titel, f.Genre, f.Dauer, " +
                    "v.Vorstellungs_ID, v.Vorfuehrungszeit, v.Saal_ID, " +
                    "FreeSeats(v.Vorstellungs_ID) as VerfuegbarePlaetze " +
                    "FROM Film f " +
                    "JOIN Vorstellung v ON f.Film_ID = v.Film_ID " +
                    "WHERE v.Vorfuehrungszeit > NOW() " +
                    "ORDER BY v.Vorfuehrungszeit";

            var query = session.createNativeQuery(sql, Object[].class);
            List<Object[]> results = query.getResultList();

            // Ergebnisse in eine Liste von Maps umwandeln
            for (Object[] row : results) {
                Map<String, Object> filmData = new HashMap<>();
                filmData.put("filmId", row[0]);
                filmData.put("titel", row[1]);
                filmData.put("genre", row[2]);
                filmData.put("dauer", row[3]);
                filmData.put("vorstellungsId", row[4]);
                filmData.put("vorfuehrungszeit", row[5]);
                filmData.put("saalId", row[6]);
                filmData.put("verfuegbarePlaetze", row[7]);
                filmList.add(filmData);
            }

            session.getTransaction().commit();

        } catch (Exception e) {
            System.err.println("Datenbankfehler: " + e.getMessage());
            e.printStackTrace();
        }

        return filmList;
    }

    // Hilfsmethode zum Extrahieren von Daten für die JSF-Seite
    public String getFreeSeatsSummary(String verfuegbarePlaetze) {
        return verfuegbarePlaetze;
    }

    @PreDestroy
    public void destroy() {
        // Nichts zu tun, da die SessionFactory statisch ist
    }

    // Diese Methode würde beim Herunterfahren der Anwendung aufgerufen werden
    public static void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}