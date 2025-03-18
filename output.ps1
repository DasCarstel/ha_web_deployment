# Pfad zum Ordner, der durchsucht werden soll
$sourceFolder = "C:\Users\carst\IdeaProjects\ha_web_deployment"

# Pfad zur Ausgabedatei
$outputFile = "C:\Users\carst\IdeaProjects\ha_web_deployment\output.txt"

# Liste der zu verarbeitenden Pfade (als Array)
# Diese können relative Pfade zum $sourceFolder sein oder Dateiendungen oder Muster
$includeList = @(
    "src\\",          # Nur Dateien im src-Verzeichnis
    "\\main\\",       # Nur Dateien, die "main" im Pfad haben
    "\.java$",        # Nur Java-Dateien
    "\.js$",          # Nur JavaScript-Dateien
    "\.html$",        # Nur HTML-Dateien
    "\.css$",         # Nur CSS-Dateien
    "\.xhtml$",       # Nur xhtml-Dateien
    "\.xml$",         # Nur xml-Dateien
    "README\.md$"     # README.md Dateien
)

# Existierende Ausgabedatei löschen, falls vorhanden
if (Test-Path $outputFile) {
    Remove-Item $outputFile
}

# Statistik-Variablen initialisieren
$totalFiles = 0
$includedFiles = 0
$errorFiles = 0

# Alle Dateien im Ordner und Unterordnern durchsuchen
Get-ChildItem -Path $sourceFolder -Recurse -File | ForEach-Object {
    $filePath = $_.FullName
    $relativePath = $filePath.Substring($sourceFolder.Length)
    $totalFiles++

    # Überprüfen, ob der Pfad verarbeitet werden soll
    $shouldInclude = $false
    $matchedPattern = ""
    foreach ($pattern in $includeList) {
        if ($relativePath -match $pattern) {
            $shouldInclude = $true
            $matchedPattern = $pattern
            break
        }
    }

    # Wenn die Datei in der Einschlussliste ist, verarbeite sie
    if ($shouldInclude) {
        $includedFiles++
        Write-Host "Verarbeite Datei: $filePath (Entspricht Muster: $matchedPattern)" -ForegroundColor Green

        # Dateiinhalt lesen und in die Ausgabedatei schreiben
        Add-Content -Path $outputFile -Value "### Datei: $filePath ###"

        # Überprüfen, ob die Datei gelesen werden kann
        if (Test-Path -LiteralPath $_.FullName) {
            try {
                $content = Get-Content -LiteralPath $_.FullName -ErrorAction Stop -Raw
                Add-Content -Path $outputFile -Value $content
                Add-Content -Path $outputFile -Value "`n`n" # Leerzeilen für bessere Lesbarkeit
            }
            catch {
                Add-Content -Path $outputFile -Value "!!! FEHLER BEIM LESEN DER DATEI: $($_.Exception.Message) !!!"
                Add-Content -Path $outputFile -Value "`n`n"
                Write-Host "Fehler beim Lesen der Datei: $filePath - $($_.Exception.Message)" -ForegroundColor Red
                $errorFiles++
            }
        } else {
            Add-Content -Path $outputFile -Value "!!! DATEI NICHT GEFUNDEN !!!"
            Add-Content -Path $outputFile -Value "`n`n"
            Write-Host "Datei nicht gefunden: $filePath" -ForegroundColor Red
            $errorFiles++
        }
    }
}

# Zusammenfassung ausgeben
Write-Host "`nZusammenfassung:" -ForegroundColor Cyan
Write-Host "Insgesamt gefunden: $totalFiles Dateien" -ForegroundColor Cyan
Write-Host "Verarbeitet: $includedFiles Dateien" -ForegroundColor Green
Write-Host "Mit Fehlern: $errorFiles Dateien" -ForegroundColor $(if ($errorFiles -gt 0) { "Red" } else { "Green" })
Write-Host "Übersprungen: $($totalFiles - $includedFiles) Dateien" -ForegroundColor Yellow
Write-Host "`nExport abgeschlossen! Alle gewünschten Inhalte wurden in $outputFile gespeichert." -ForegroundColor Cyan