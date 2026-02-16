@echo off
cls
echo Avvio di CSV Cleaner...

if exist target\CsvCleaner-1.0-SNAPSHOT.jar (
    java -jar target\CsvCleaner-1.0-SNAPSHOT.jar
) else if exist CsvCleaner.jar (
    java -jar CsvCleaner.jar
) else (
    echo ❌ Errore: File .jar non trovato!
    pause
)