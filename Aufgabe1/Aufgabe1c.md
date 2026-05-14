## **Aufgabe 1c – Client-Programmierung**

Für die Client-Programmierung wurde ein eigener MQTT-Client in **Java** umgesetzt. Als MQTT-Bibliothek wurde der **HiveMQ MQTT Client** verwendet, da dieser MQTT Version 5 unterstützt. Zum Parsen der Wetterdaten wurde die **Jackson-Library** eingesetzt.

Der Client verbindet sich mit dem FIM-MQTT-Broker:

```text
Broker: 10.50.12.150
Port:   1883
Topic:  /AZ-Envy/JSON
```

Nach dem Verbindungsaufbau abonniert der Client das Wetterdaten-Topic `/AZ-Envy/JSON`. Die empfangenen Nachrichten liegen im JSON-Format vor und werden nicht direkt als String ausgegeben, sondern zuerst in ein Java-Objekt `WeatherData` umgewandelt.

Das Objekt enthält folgende Messwerte:

```text
Temp
Humidity
Lpg
Co
Smoke
```

Anschließend werden die Werte strukturiert auf der Konsole ausgegeben.

### **Test mit dem FIM-MQTT-Broker**

Der Client wurde erfolgreich mit dem FIM-MQTT-Broker getestet. Beim Starten des Programms erscheint folgende Ausgabe:

```text
Verbinde mit MQTT-Broker 10.50.12.150:1883...
Verbunden.
Abonniere Wetterdaten: /AZ-Envy/JSON
```

Danach werden periodisch neue Wetterdaten empfangen, geparst und ausgegeben:

```text
========== Wetterdaten ==========
Zeit:              2026-04-28T16:56:18.661613400
Temperatur:        29.73 °C
Luftfeuchtigkeit:  22.14 %
LPG:               0.0021
CO:                6.0E-4
Smoke:             0.0052
=================================

========== Wetterdaten ==========
Zeit:              2026-04-28T16:56:25.291516
Temperatur:        0.0 °C
Luftfeuchtigkeit:  0.0 %
LPG:               0.0022
CO:                9.0E-4
Smoke:             0.0055
=================================
```

Damit ist nachgewiesen, dass der Client erfolgreich Daten vom MQTT-Broker empfängt, diese korrekt verarbeitet und als Objektwerte ausgibt.
