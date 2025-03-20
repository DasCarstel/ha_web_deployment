# GlassFish Web-Anwendung Setup Guide

## Einrichtungsschritte für GlassFish Webprojekt

Diese Anleitung beschreibt die notwendigen Schritte, um eine Web-Anwendung mit GlassFish Server von Grund auf einzurichten.

### Voraussetzungen

- IntelliJ IDEA
- Docker
- Java Development Kit (JDK)
- GlassFish Server

### Einrichtungsschritte

#### 1. Docker-Umgebung starten

```bash
# Docker-Container starten
docker-compose up -d
```

#### 2. GlassFish Plugin installieren

1. Öffne IntelliJ IDEA
2. Gehe zu **File > Settings > Plugins**
3. Suche nach "GlassFish"
4. Installiere das GlassFish-Addon
5. **IDE neu starten**, um die Installation abzuschließen

#### 3. Server-Konfiguration einrichten

1. Klicke auf **Edit Configurations** in der oberen Leiste
2. Klicke auf das **"+"** Symbol, um eine neue Konfiguration hinzuzufügen
3. Gib **"GlassFish"** in die Suche ein
4. Wähle **Local** als Server-Typ aus

#### 4. GlassFish Server konfigurieren

1. Klicke im Konfigurationsdialog auf **Configure** neben "Application Server"
2. Navigiere zum **GlassFish Home-Verzeichnis** und wähle es aus
    - Stelle sicher, dass der Pfad zum korrekten GlassFish-Installationsordner führt
3. Wähle unter **Server Domain** eine verfügbare Domain aus (üblicherweise "domain1")
4. Gehe im Konfigurationsdialog zum Tab **Deployment**
5. Klicke auf das **"+"** Symbol
6. Wähle **Artifact** aus
7. Wähle das **"war exploded"** Artifact deines Projekts aus
    - Dies ermöglicht schnellere Entwicklungszyklen und sofortiges Feedback bei Codeänderungen

#### 6. Anwendung starten

Nach erfolgreicher Konfiguration kannst du den Server über den Run-Button starten.
Deine Anwendung sollte unter `http://localhost:8080/ha_web_deployment/` verfügbar sein.


---

*Dieses Setup-Guide wurde erstellt, um die Einrichtung von GlassFish-Projekten zu standardisieren und die Entwicklungsumgebung schnell wiederherzustellen.*