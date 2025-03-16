package com.example.ha_web_deployment.beans;

import jakarta.annotation.PreDestroy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.Serializable;

public abstract class Bean implements Serializable {
    protected static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public Bean() {
        // Default-Konstruktor
    }

    /**
     * Öffnet eine neue Hibernate-Session
     * @return Eine neue Session
     */
    protected Session openSession() {
        return sessionFactory.openSession();
    }

    @PreDestroy
    public void destroy() {
        // Nichts zu tun, da die SessionFactory statisch ist
    }
}