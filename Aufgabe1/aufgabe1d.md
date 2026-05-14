## **Aufgabe 1d – Chat-Server**

Für Aufgabe 1d wurde ein eigener interaktiver MQTT-Chat-Client in **Java** umgesetzt. Als MQTT-Bibliothek wurde erneut der **HiveMQ MQTT Client** verwendet, da dieser MQTT Version 5 unterstützt.

Der Chat-Server ist erreichbar unter:

```text
http://10.50.12.24:8080/
```

Die Chatnachrichten werden über den FIM-MQTT-Broker verschickt:

```text
Broker: 10.50.12.150
Port:   1883
```

Die Chatnachrichten befinden sich unterhalb des Topics:

```text
/aichat
```

Standardmäßig wird das Topic verwendet:

```text
/aichat/default
```

---

### **Analyse der MQTT-Chatdaten**

Zur Analyse wurden die Chat-Topics mit Mosquitto abonniert:

```bash
mosquitto_sub -h 10.50.12.150 -p 1883 -t "/aichat/#" -v
```

Dabei werden alle Untertopics von `/aichat` angezeigt. Der Chat verwendet hauptsächlich folgende Topics:

```text
/aichat/default
/aichat/clientstate
```

`/aichat/default` enthält die eigentlichen Chatnachrichten.
`/aichat/clientstate` wird für Statusmeldungen der Clients verwendet.

Die Chatdaten werden als strukturierte Nachrichten im JSON-Format übertragen. Eine typische Nachricht enthält z. B. folgende Informationen:

```json
{
  "clientId": "AuchChat-3d3e73ad-555c-49e9-bc8c-af97cd09fbb2",
  "name": "RCK",
  "message": "Hallo, mein Chat-Client funktioniert!",
  "timestamp": 1777380000000
}
```

Die `clientId` ist frei wählbar, darf aber nicht identisch mit der Client-ID des Chat-Servers sein. Deshalb wurde für den eigenen Client eine eindeutige ID mit UUID verwendet.

---

### **Umsetzung des Chat-Clients**

Der eigene Chat-Client wurde als Kommandozeilenanwendung umgesetzt. Beim Start verbindet sich das Programm mit dem MQTT-Broker und abonniert alle Chatnachrichten unter:

```text
/aichat/#
```

Dadurch kann der Client sowohl Nachrichten vom Vorlesungs-Chat-Server als auch von anderen Clients empfangen.

Zusätzlich kann der Benutzer über die Konsole eigene Nachrichten eingeben. Diese Nachrichten werden als JSON-Objekt erzeugt und per MQTT an folgendes Topic gesendet:

```text
/aichat/default
```

Die gesendeten Nachrichten werden als UTF-8 übertragen und mit dem Content-Type gekennzeichnet:

```text
application/json
```

---

### **Statusnachrichten und Last Will**

Beim Start des Programms sendet der Client eine Statusnachricht an:

```text
/aichat/clientstate
```

Beispiel:

```text
Chat Client AuchChat-123 started
```

Beim normalen Beenden wird ebenfalls eine Statusnachricht gesendet:

```text
Chat Client AuchChat-123 stopped normally
```

Zusätzlich wurde ein **Last Will** eingerichtet. Falls der Client unerwartet beendet wird, veröffentlicht der Broker automatisch eine Nachricht auf `/aichat/clientstate`.

Beispiel:

```text
Chat Client AuchChat-123 stopped
```

Damit ist erkennbar, ob ein Client ordnungsgemäß beendet wurde oder unerwartet die Verbindung verloren hat.

---

### **Test mit dem Vorlesungs-Chat-Server**

Der Chat-Client wurde mit dem Vorlesungs-Chat-Server getestet. Dabei konnten Nachrichten empfangen und eigene Nachrichten über MQTT verschickt werden.

Beispielhafte Konsolenausgabe:

```text
Chat-Client gestartet.
Abonniere Topic: /aichat/#
Schreibe Nachrichten. Mit /exit beenden.

[/aichat/clientstate] Chat Client AuchChat-123 started

> Hallo, mein Chat-Client funktioniert!

[/aichat/default] {"clientId":"AuchChat-123","name":"RCK","message":"Hallo, mein Chat-Client funktioniert!","timestamp":1777380000000}

