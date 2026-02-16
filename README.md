# 🧹 MySQL CSV Cleaner Tool

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Version](https://img.shields.io/badge/Version-25.0-blue?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

Un tool da riga di comando (CLI) scritto in Java per risolvere i comuni errori di importazione CSV in MySQL, in particolare su macOS.

## ⬇️ Download
[Scarica l'ultima versione per Mac/Windows qui](https://github.com/FeDevv/mysql-csv-cleaner/releases/download/v1.0.0/MySQL_CsvCleaner_v1.0.zip)

## ⚠️ Il Problema
Quando si tenta di importare file CSV (spesso generati da Excel) in MySQL 8.0+, si verificano spesso questi errori:
- **Error 2068:** `LOAD DATA LOCAL INFILE file request rejected`
- **Encoding Error:** `ascii codec can't decode byte 0xa0` (Non-breaking space)

## ✅ La Soluzione
Questo tool:
1. Legge il file CSV originale forzando la codifica **Windows-1252**.
2. Sostituisce i caratteri problematici (come `0xA0` NBSP) con spazi standard.
3. Genera un nuovo file `_CLEANED.csv` codificato in **UTF-8** puro.
4. Fornisce un'interfaccia interattiva con barra di avanzamento.

---

## 🚀 Come usare

### Requisiti
- Java JDK 25 o superiore installato.

### Avvio Rapido

#### 🍎 Su Mac/Linux:
Fai doppio click sul file `run_mac.command` oppure esegui da terminale:
```bash
./run_mac.command
```

> **⚠️ Nota Importante per macOS:**
> Al primo avvio, il Mac potrebbe bloccare lo script per sicurezza ("Sviluppatore non identificato"). Per risolvere:
> 1. Fai **Tasto Destro** sul file `run_mac.command`.
> 2. Seleziona **Apri**.
> 3. Clicca su **Apri** nel pop-up di conferma.
>
> Se invece ricevi un errore di permessi ("Permission denied"), apri il terminale nella cartella e scrivi:
> `chmod +x run_mac.command`

#### 🪟 Su Windows:
Fai doppio click sul file `run_win.bat`.

---

## 🛠 Compilazione (Per sviluppatori)
Se vuoi modificare il codice e ricompilare il progetto:

1. Assicurati di avere **Maven** installato.
2. Esegui il comando:
```bash
mvn clean package
```
3. Troverai il nuovo eseguibile in `target/CsvCleaner-1.0-SNAPSHOT.jar`.

---

## 👨‍💻 Author
Developed with ❤️ by **FeDevv**.

---

## 📄 License
Questo progetto è distribuito sotto licenza **MIT**.

```text
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
