Hier ist die aktualisierte Anleitung mit den gewünschten Ergänzungen:

---

# Anleitung zum Import eines Jakarta Maven Projekts von IntelliJ nach NetBeans

Diese Anleitung beschreibt, wie du dein Jakarta Maven Projekt von IntelliJ nach NetBeans migrieren kannst. Es handelt sich um eine Jakarta EE Webanwendung mit Maven als Build-Management-Tool.

---

## Voraussetzungen
- **NetBeans IDE** (aktuelle Version empfohlen)
- **Java Development Kit (JDK) 23** (wie in deinem Projekt angegeben)
- **Maven** (sollte mit NetBeans mitgeliefert werden)
- **GlassFish Server** (für Jakarta EE-Anwendungen)
- **Docker**

---

## Schritt 1: Vorbereitung
- Stelle sicher, dass du deine Projektdateien gesichert hast, entweder über Git oder durch manuelle Sicherung.

---

## Schritt 2: NetBeans installieren und konfigurieren
1. Falls noch nicht geschehen, lade die aktuelle NetBeans-Version von der offiziellen Website herunter und installiere sie.
2. **Prüfe, ob die Maven-Integration aktiviert ist:**
   - Gehe zu **Tools > Plugins**.
   - Wechsle zum Tab **Installed**.
   - Suche nach "Maven" in der Liste der installierten Plugins.
   - Überprüfe, ob "Maven" oder "Apache Maven" aktiviert ist (es sollte ein Häkchen davor sein).
   - Falls nicht aktiviert, installiere das Plugin über den Tab **Available Plugins**.
3. **Konfiguriere das JDK 23 in NetBeans:**
   - Gehe zu **Tools > Java Platforms**.
   - Klicke auf **Add Platform...**.
   - Wähle **Java Standard Edition** und navigiere zum Installationsverzeichnis deines JDK 23.
   - Folge den Anweisungen zum Abschluss der Konfiguration.

---

## Schritt 3: GlassFish Server in NetBeans einrichten
1. Gehe zu **Tools > Servers**.
2. Klicke auf **Add Server...**.
3. Wähle **GlassFish Server** und klicke auf **Next**.
4. Gib den Pfad zum GlassFish-Installationsverzeichnis an.
5. Setze den Domain-Namen auf `domain1` (oder den, den du verwendest).
6. Setze Benutzernamen auf `admin` (falls erforderlich) und konfiguriere das Passwort.
7. Klicke auf **Finish**.

---

## Schritt 4: Projekt in NetBeans importieren
### Variante 1: Direktes Öffnen als Maven-Projekt
1. Öffne NetBeans.
2. Wähle **File > Open Project...**.
3. Navigiere zum Stammverzeichnis deines Projekts (dort, wo die `pom.xml` liegt).
4. Wähle das Verzeichnis aus und klicke auf **Open Project**.
5. NetBeans erkennt das Projekt automatisch als Maven-Projekt.

### Variante 2: Projekt aus Git importieren
1. Öffne NetBeans.
2. Wähle **Team > Git > Clone...**.
3. Gib die URL https://github.com/DasCarstel/ha_web_deployment.
4. Folge den Anweisungen zur Authentifizierung.
5. Wähle den Branch aus, den du klonen möchtest.
6. Wähle ein lokales Verzeichnis für das Projekt.
7. Klicke auf **Finish**.

---

## Schritt 5: MySQL-Connector konfigurieren
1. **Lade den MySQL Connector/J herunter:**
   - Besuche [https://dev.mysql.com/downloads/connector/j/](https://dev.mysql.com/downloads/connector/j/).
   - Wähle "Platform Independent" aus.
   - Lade den **Connector/J 9.2.0** herunter.
2. **Füge den Connector zu NetBeans hinzu:**
   - Gehe zu **Tools > Libraries**.
   - Klicke auf **New Library...** und nenne sie "MySQL JDBC Driver".
   - Klicke auf **Add JAR/Folder** und navigiere zur MySQL Connector JAR-Datei (z. B. `mysql-connector-j-9.2.0.jar`).
   - Klicke auf **OK**, um die Bibliothek zu erstellen.
3. **Konfiguriere den Datenbanktreiber:**
   - Gehe zu **Services > Databases**.
   - Rechtsklick auf **Drivers** und wähle **New Driver...**.
   - Klicke auf **Add...** und wähle die MySQL Connector JAR-Datei.
   - Setze die Treiberklasse auf `com.mysql.cj.jdbc.Driver`.
   - Benenne den Treiber als "MySQL (Connector/J driver)" und klicke auf **OK**.

---

## Schritt 6: Datenbank konfigurieren
1. **Docker-Container starten:**
   - Stelle sicher, dass deine Datenbank in einem Docker-Container läuft.
   - Starte den Container mit folgendem Befehl:
     ```bash
     docker-compose up -d
     ```
2. **Verbinde dich mit der Datenbank:**
   - Gehe zu **Services > Databases**.
   - Rechtsklick auf **MySQL Server** (oder den gerade konfigurierten Treiber) und wähle **Connect...**.
   - Gib die Verbindungsinformationen ein:
     - Host: `localhost`
     - Port: `3306`
     - Datenbank: `102203984`
     - Benutzer: `root`
     - Passwort: `1234` (wie in deiner `hibernate.cfg.xml` angegeben).
   - Klicke auf **Test Connection**, um sicherzustellen, dass die Verbindung funktioniert.
   - Klicke auf **Finish**, um die Verbindung zu speichern.

---

## Schritt 7: Projekt konfigurieren
1. **Überprüfe die Maven-Konfiguration:**
   - Rechtsklick auf das Projekt.
   - Wähle **Properties**.
   - Wähle **Build > Actions**.
   - Stelle sicher, dass **Run Project** auf **Run with GlassFish** konfiguriert ist.
2. **Überprüfe die Projekteinstellungen:**
   - Rechtsklick auf das Projekt.
   - Wähle **Properties**.
   - Überprüfe die Java-Plattform und stelle sicher, dass JDK 23 verwendet wird.
   - Unter **Run** sollte der richtige Server (GlassFish) ausgewählt sein.
3. **Falls notwendig, konfiguriere den Deployment-Descriptor:**
   - Öffne den Ordner **Web Pages > WEB-INF**.
   - Überprüfe, ob `web.xml` korrekt konfiguriert ist.

---

## Schritt 8: Projektabhängigkeiten überprüfen
1. Rechtsklick auf das Projekt.
2. Wähle **Dependencies > Clean and Build**.
3. Überprüfe im Output-Fenster, ob alle Abhängigkeiten korrekt aufgelöst werden.

---

## Schritt 9: Projekt ausführen
1. Rechtsklick auf das Projekt.
2. Wähle **Run**.
3. NetBeans kompiliert das Projekt, erstellt ein WAR-Archiv und deployt es auf dem GlassFish-Server.
4. Nach erfolgreichem Deployment wird die Anwendung im Standard-Browser geöffnet.

---

## Tipps zur Fehlerbehebung
- **Pfadprobleme:** Falls Ressourcen nicht gefunden werden, überprüfe die Pfade in deinen XHTML-Dateien. NetBeans kann anders mit Ressourcenpfaden umgehen als IntelliJ.
- **Deployment-Probleme:** Falls das Deployment fehlschlägt:
  - Überprüfe die Server-Logs (im NetBeans-Output-Fenster oder im GlassFish-Logverzeichnis).
  - Stelle sicher, dass dein Server läuft und korrekt konfiguriert ist.
  - Versuche ein **Clean & Build** vor dem erneuten Deployment.
- **Maven-Abhängigkeiten:** Falls Maven-Abhängigkeiten nicht aufgelöst werden:
  - Öffne ein Terminal und führe `mvn clean install` im Projektverzeichnis aus.
  - Aktualisiere die Maven-Repositories (**File > Project > Update Dependencies**).
- **Hibernate-Konfiguration:** Überprüfe, ob die `hibernate.cfg.xml` korrekt erkannt wird. Falls nicht, stelle sicher, dass sie im richtigen Verzeichnis liegt (`src/main/resources`).
- **JSF-Konfiguration:** Stelle sicher, dass die JSF-Konfiguration korrekt ist und dass NetBeans die Faces-Definitionen richtig erkennt.

---

Mit dieser Anleitung solltest du dein Jakarta Maven Projekt erfolgreich von IntelliJ nach NetBeans migrieren können. Die Kernkomponente ist die `pom.xml`-Datei, die von beiden IDEs unterstützt wird und die Projektstruktur sowie Abhängigkeiten definiert.