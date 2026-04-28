## **Aufgabe 1a – MQTT Grundlagen**

### **1. Welches Pattern aus der Softwareentwicklung fällt Ihnen zu den Grundprinzipien von MQTT ein?**

MQTT basiert auf dem **Publish-Subscribe-Pattern (Pub/Sub)**.

Dabei senden sogenannte *Publisher* Nachrichten an einen zentralen Broker, ohne zu wissen, wer diese empfängt. *Subscriber* abonnieren bestimmte Topics und erhalten automatisch alle Nachrichten, die zu diesen Topics veröffentlicht werden.

Dieses Muster führt zu einer **losen Kopplung** zwischen Sender und Empfänger, da beide unabhängig voneinander arbeiten und lediglich über den Broker kommunizieren.

---

### **2. Wie kann ich beispielsweise alle Topics unter `/weather` abonnieren?**

In MQTT können sogenannte **Wildcard-Zeichen** verwendet werden, um mehrere Topics gleichzeitig zu abonnieren.

Um alle Topics unter `/weather` zu abonnieren, wird die Multi-Level-Wildcard `#` verwendet:

```bash
mosquitto_sub -h 10.50.12.150 -p 1883 -t "/weather/#"
```

Dabei bedeutet:

* `/weather/#` → alle Untertopics von `/weather`
* `#` → beliebig viele Ebenen

---

### **3. Was ist der „Last Will“?**

Der sogenannte **Last Will and Testament (LWT)** ist eine Nachricht, die ein Client beim Verbindungsaufbau beim MQTT-Broker hinterlegt.

Falls die Verbindung eines Clients **unerwartet abbricht** (z. B. durch Absturz oder Netzwerkfehler), veröffentlicht der Broker automatisch diese Nachricht in einem definierten Topic.

Der Last Will wird häufig verwendet, um den Zustand eines Clients zu signalisieren, z. B.:

```text
Client XYZ ist offline
```

Dadurch können andere Clients zuverlässig erkennen, wenn ein Teilnehmer nicht mehr erreichbar ist.

---

### **4. Was sind die wichtigsten Unterschiede zwischen MQTT v3 und v5?**

MQTT Version 5 stellt eine Weiterentwicklung von MQTT Version 3 dar und bietet zahlreiche Verbesserungen:

* **Erweiterte Fehlermeldungen:**
  MQTT v5 verwendet sogenannte *Reason Codes*, die genauere Informationen über Fehler liefern.

* **User Properties:**
  Es können zusätzliche Schlüssel-Wert-Paare mit Nachrichten übertragen werden.

* **Message Expiry Interval:**
  Nachrichten können ein Ablaufdatum erhalten und werden danach automatisch verworfen.

* **Session Expiry:**
  Sitzungen können zeitlich begrenzt gespeichert werden.

* **Topic Aliases:**
  Häufig verwendete Topics können effizienter übertragen werden.

* **Request/Response Pattern:**
  MQTT v5 unterstützt direkte Antwortmechanismen zwischen Clients.

* **Verbesserte Subscription-Optionen:**
  Mehr Kontrolle über das Verhalten beim Abonnieren von Topics.

Zusammenfassend bietet MQTT v5 mehr Flexibilität, bessere Diagnosemöglichkeiten und zusätzliche Funktionalitäten im Vergleich zu MQTT v3.
