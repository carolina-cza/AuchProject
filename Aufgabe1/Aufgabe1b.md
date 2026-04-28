---

## **Aufgabe 1b – Erste Schritte mit einem MQTT-Client**

Zur Durchführung der Aufgabe wurde der MQTT-Client **Mosquitto** verwendet. Die Verbindung zum MQTT-Broker des FIM-Labors erfolgte über die IP-Adresse `10.50.12.150` und Port `1883`.

---

### **1. Wie können Sie alle Topics abonnieren, auch ohne diese alle im Voraus zu kennen?**

Alle Topics können mithilfe der Multi-Level-Wildcard `#` abonniert werden.

```bash
mosquitto_sub -h 10.50.12.150 -p 1883 -t "#" -v
```

Die Option `-v` sorgt dafür, dass neben der Nachricht auch der zugehörige Topic-Name angezeigt wird.
Das Zeichen `#` steht dabei für beliebig viele Topic-Ebenen und ermöglicht somit das Abonnieren sämtlicher vorhandener Topics.

---

### **2. Welche Topics und Werte können Sie sehen?**

Beim Abonnieren aller Topics konnten verschiedene Sensordaten und Systeminformationen beobachtet werden. Beispiele hierfür sind:

```text
/AZ-Envy/Temp        29.31
/AZ-Envy/Hum         23.57
/AZ-Envy/Lpg         0.0089
/AZ-Envy/Co          0.0063
/AZ-Envy/Smoke       0.0238

/AZ-Envy-3/Temp      29.78
/AZ-Envy-3/Hum       22.39
/AZ-Envy-3/Lpg       0.0073
/AZ-Envy-3/Co        0.0046
/AZ-Envy-3/Smoke     0.0193

/siemens/1200CPU/Time
/siemens/1200CPU/Poti
/siemens/1200CPU/IO
/siemens/1200CPU/OPC/iRcv1
/siemens/1200CPU/OPC/sRcv
```

Die Topics sind hierarchisch aufgebaut und enthalten sowohl Wetterdaten als auch industrielle Messwerte.

---

### **3. Wie können Sie ein bestimmtes Topic abonnieren, z. B. das Wetter für Mosbach?**

Ein bestimmtes Topic kann durch Angabe des exakten Topic-Namens oder mithilfe von Wildcards abonniert werden.

Beispiel für ein konkretes Topic:

```bash
mosquitto_sub -h 10.50.12.150 -p 1883 -t "/AZ-Envy/JSON" -v
```

Beispiel für alle Untertopics eines Bereichs:

```bash
mosquitto_sub -h 10.50.12.150 -p 1883 -t "/AZ-Envy/#" -v
```

Durch die Verwendung von Wildcards können gezielt Teilbereiche der Topic-Hierarchie abonniert werden.

---

### **4. In welchem Datenformat werden die Wetterdaten bereitgestellt?**

Die Wetterdaten werden in zwei unterschiedlichen Formaten bereitgestellt:

* **Einzelwerte (Plain Text / numerisch):**
  Einzelne Messwerte wie Temperatur oder Luftfeuchtigkeit werden direkt als Zahlen übertragen.

  Beispiel:

  ```text
  /AZ-Envy/Temp 29.31
  ```

* **JSON-Format:**
  Mehrere Messwerte werden zusammengefasst als JSON-Objekt übertragen.

  Beispiel:

  ```json
  {"Temp":29.31,"Humidity":23.57,"Lpg":0.0089,"Co":0.0063,"Smoke":0.0238}
  ```

Das JSON-Format eignet sich besonders für die strukturierte Weiterverarbeitung der Daten in Anwendungen.
