#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "$DIR"

clear

echo "Avvio di CSV Cleaner..."

if [ -f "target/CsvCleaner-1.0-SNAPSHOT.jar" ]; then
    java -jar target/CsvCleaner-1.0-SNAPSHOT.jar

elif [ -f "CsvCleaner.jar" ]; then
    java -jar CsvCleaner.jar

else
    echo "❌ Errore: File .jar non trovato!"
    echo "Assicurati di aver compilato il progetto con 'mvn package'"
    echo "oppure che il file CsvCleaner.jar sia in questa cartella."
fi