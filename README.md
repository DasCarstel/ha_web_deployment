# Cineplex - Kinobuchungssystem

## Überblick
Dieses Repository enthält ein Uni-Projekt für die IU Internationale Hochschule, das im Rahmen eines Kurses für Webentwicklung erstellt wurde. Es handelt sich um ein Java-Web-Projekt für ein Kinobuchungssystem, das mit Hibernate als ORM, JSF für die Views und Bootstrap für das UI entwickelt wurde.

## Projektbeschreibung
Das Projekt implementiert ein vollständiges Kinobuchungssystem mit folgenden Funktionalitäten:
- Anzeige verfügbarer Filme
- Auswahl von Vorstellungen
- Sitzplatzreservierung
- Buchungsprozess

## Technische Details
- **Backend**: Java mit Hibernate ORM
- **Frontend**: JSF (JavaServer Faces) mit Bootstrap
- **Datenbankanbindung**: MySQL über Hibernate
- **Build-System**: Maven
- **Server**: GlassFish

## Ausführungsanleitung

Dieses Projekt muss in NetBeans importiert und ausgeführt werden. Für die erfolgreiche Ausführung beachten Sie bitte die folgenden Schritte:

### Voraussetzungen
- NetBeans IDE (aktuelle Version empfohlen)
- JDK 23
- GlassFish Server
- MySQL-Datenbank (über Docker bereitgestellt)
- Maven

### Importieren und Starten des Projekts

1. **Projekt in NetBeans importieren**:
   - Öffnen Sie NetBeans
   - Wählen Sie `File > Open Project...`
   - Navigieren Sie zum Projektverzeichnis und öffnen Sie es

2. **Datenbank starten**:
   - Stellen Sie sicher, dass Docker installiert ist
   - Führen Sie im Projektverzeichnis den Befehl `docker-compose up -d` aus, um den MySQL-Container zu starten

3. **Hibernate-Konfiguration**:
   - Die Datenbankverbindung ist bereits in der `hibernate.cfg.xml` konfiguriert
   - Die Standardzugangsdaten sind:
     - Host: localhost
     - Port: 3306
     - Datenbank: MovieTheater
     - Benutzer: root
     - Passwort: 1234

4. **Projekt ausführen**:
   - Klicken Sie mit der rechten Maustaste auf das Projekt
   - Wählen Sie `Run`
   - Das Projekt wird kompiliert und auf dem GlassFish-Server deployed

> **Wichtiger Hinweis**: Nach dem Deployment wird die Webseite möglicherweise nicht automatisch im Browser geöffnet. Falls dies der Fall ist, navigieren Sie manuell zur Adresse: http://localhost:8080/ha_web_deployment/

### Detaillierte Anleitungen

Für eine ausführlichere Anleitung zur Einrichtung und zum Starten des Projekts stehen folgende Dokumente zur Verfügung:

- [NetBeans Guide](guide/Netbeans%20Guide.md) - Ausführliche Anleitung für die Einrichtung in NetBeans
- [IntelliJ Guide](guide/IntelliJ%20Guide.md) - Alternative Anleitung für IntelliJ (nur zur Information)

## Projektstruktur

Das Projekt folgt der Standard-Maven-Projektstruktur:

```
cineplex/
├── src/
│   ├── main/
│   │   ├── java/                         # Java-Quellcode
│   │   │   └── com/example/ha_web_deployment/
│   │   │       ├── beans/                # MVC-Controller
│   │   │       │   ├── Bean.java         # Basisklasse für alle Beans
│   │   │       │   ├── FilmBean.java     # Controller für Filme
│   │   │       │   ├── GastBean.java     # Controller für Gäste
│   │   │       │   ├── SaalBean.java     # Controller für Säle
│   │   │       │   ├── TicketBean.java   # Controller für Tickets
│   │   │       │   └── VorstellungBean.java # Controller für Vorstellungen
│   │   │       └── models/               # Hibernate-Entities
│   │   │           ├── Film.java         # Film-Entity
│   │   │           ├── Gast.java         # Gast-Entity
│   │   │           ├── Saal.java         # Saal-Entity
│   │   │           ├── Ticket.java       # Ticket-Entity
│   │   │           └── Vorstellung.java  # Vorstellung-Entity
│   │   ├── resources/                    # Ressourcendateien
│   │   │   ├── db_init/                  # Datenbank-Initialisierung
│   │   │   │   └── init.sql              # SQL-Skript
│   │   │   ├── META-INF/
│   │   │   │   └── persistence.xml       # JPA-Konfiguration
│   │   │   └── hibernate.cfg.xml         # Hibernate-Konfiguration
│   │   └── webapp/                       # Web-Ressourcen
│   │       ├── resources/
│   │       │   └── css/
│   │       │       └── KinoStyle.css     # CSS-Stylesheets
│   │       ├── WEB-INF/
│   │       │   └── web.xml               # Web-Konfiguration
│   │       ├── Abschluss.xhtml           # Bestätigungsseite
│   │       ├── Filme.xhtml               # Film-Übersichtsseite
│   │       ├── Gaeste.xhtml              # Gast-Datenerfassungsseite
│   │       ├── Saele.xhtml               # Saal-/Platzauswahl
│   │       └── Vorstellungen.xhtml       # Vorstellungs-Übersichtsseite
│   └── test/                             # Test-Quellcode
├── Guides/                               # Anleitungen
│   ├── Netbeans Guide.md                 # NetBeans-Anleitung
│   └── IntelliJ Guide.md                 # IntelliJ-Anleitung
├── pom.xml                               # Maven-Konfiguration
├── docker-compose.yml                    # Docker-Konfiguration für MySQL
└── README.md                             # Diese Datei
```

## Wichtige Hinweise für die Bewertung

- Die Datenbankverbindung wird **ausschließlich** über `hibernate.cfg.xml` hergestellt, wie in der Aufgabenstellung gefordert.
- Für jedes Modell wurde ein entsprechender Controller und eine View erstellt.
- Die Benutzeroberfläche verwendet ein modernes, responsive Design mit CSS.
- Benutzereingaben werden auf der Client-Seite durch JavaScript und serverseitig validiert.
- Das Projekt wurde als Maven-Projekt entwickelt, das in NetBeans importiert und ausgeführt werden kann.
- Der Buchungsprozess ist vollständig implementiert und funktionsfähig.

## Bekannte Einschränkungen
- Der erste Start kann länger dauern, da Hibernate die Datenbank automatisch erstellt
- Bei Problemen mit der Datenbankverbindung bitte überprüfen, ob der Docker-Container korrekt läuft
- **Wichtig**: Nach dem Deployment wird die Webseite möglicherweise nicht automatisch im Browser geöffnet. Falls dies der Fall ist, navigieren Sie manuell zur Adresse: http://localhost:8080/ha_web_deployment/

---

Bei Fragen oder Problemen bei der Ausführung des Projekts stehen die detaillierten Guides im Verzeichnis `guide/` zur Verfügung.