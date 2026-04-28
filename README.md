SS 2026
Übungen zu Verteilte Systeme
Übungsblatt Labor Verteilte Systeme
Die Abgabe kann in Gruppen von 3-4 Personen im Moodle-Abgabecontainer er
folgen. Sie können dabei die gesamte Abgabe in einer einzigen Zip-Datei ablegen
mit sinnvoller Unterverzeichnisstruktur (z.B. Aufgabe1 mit Unterordnern für Auf
gaben 1a-d, usw.).
Bitte legen Sie dort jeweils pro Aufgabe auch ein Protokoll im PDF-Format bei mit
den Antworten auf die Text-Fragen. Im Protokoll können Sie auch gerne aufgetre
tene Probleme und deren Lösung beschreiben. Sie können dort pro Gruppe eine
einzige Zip-Datei hochladen. Bitte schreiben Sie Ihre Matrikelnummern ins Proto
koll, damit ich diese zuordnen kann.
Legen Sie bitte auch die Quelldateien in der Verzeichnisstruktur ab, also beispiels
weise für Aufgabe 1c in einem Unterverzeichnis Aufgabe1/1c/sourcen oder
ggf. mit Projektnamen.
Folgende Programmiersprachen können Sie für die Projekte verwenden: Java, Go,
Rust, C#, C/C++, Python, Ruby, Javascript (node.js), Typescript, Kotlin
Bei anderen Programmiersprachen bitte vorher absprechen!
Wichtig: die Abgabe sollte “ordentlich” sein, also entweder Gradle oder Maven
nehmen (oder eben das Build-Management-System der gewählten Programmier
sprache, beispielsweise eine requirements.txt bei Python)!
Aufgabe 1: MQTT
Bitte loggen Sie sich für diese Aufgabe ins VPN der DHBW Mosbach mit dem Lehre
Profil ein oder benutzen Sie das Campus-WLAN. Der MQTT-Server des FIM-Labors
ist unter der IP 10.50.12.150, Port 1883 zu erreichen.
(a) Machen Sie sich mit MQTT vertraut.
Hierzu helfen Ihnen folgende Links:
• HiveMQ Getting Started with MQTT
• MQTTEssentials
• MQTTspecifications
Beantworten Sie sich folgende Fragen:
• Welches Pattern aus der Softwareentwicklung fällt Ihnen zu den Grundprinzi
pien von MQTT ein?
• Wie kann ich beispielsweise alle Topics unter /weather abonnieren?
• Wasist der “Last Will”?
• Wassind die wichtigsten Unterschiede zwischen MQTT v3 und v5?
Seite 1/13
SS 2026
Übungen zu Verteilte Systeme
(b) Erste Schritte mit einem MQTT-Client.
Installieren Sie sich zuerst einen MQTT-Client. Am besten können Sie hierzu Mos
quitto nehmen, das sowohleinenBroker als auchdieClient-Tools mosquitto_sub
und mosquitto_pub mitbringt. Den Broker können Sie später dann auch für lo
kale Tests verwenden.
Hinweis: mit-v gibt mosquitto_sub auch die Topics mit aus.
Alternativ können Sie auch MQTT-CLI von HiveMQ verwenden.
Das Tool MQTT-Spy stellt eine graphische Oberfläche zu Verfügung und läuft mit
Java 1.8. Hinweis: legen Sie zuerst eine Konfigurationsdatei an und speichern Sie
diese, bevor Sie eine Verbindung und Topics konfigurieren.
Der MQTT-Explorer stellt einen einfach benutzbaren GUI-Client dar, der sowohl
für Mac als auch für Windows und Linux erhältlich ist.
Selbstverständlich können Sie auch gerne andere MQTT-Clients verwenden.
Verbinden Sie sich mit dem FIM-MQTT-Server.
• Wie können Sie alle Topics abonnieren, auch ohne diese alle im Voraus zu
kennen?
• Welche Topics und Werte können Sie sehen?
• Wie können Sie ein bestimmtes Topic abonnieren, z.B. das Wetter für Mos
bach?
• In welchem Datenformat werden die Wetterdaten bereitgestellt?
(c) Client-Programmierung.
Wir bauen nun unseren eigenen Client für Wetterdaten. Es gibt viele MQTT-Biblio
theken, siehe z.B. die Liste in Wikipedia.
Da der Server Protokoll-Version 5 unterstützt, sollte am besten auch die Client
Bibliothek Version 5 unterstützen. Hierzu gibt es beispielsweise Eclipse Paho
oder den HiveMQ-Client. Empfohlen wird der HiveMQ-Client, da Eclipse Paho in
der Version 1.2.5 einen Bug in der subscribe-Implementierung enthält. Wenn
Sie den HiveMQ-Client verwenden, empfiehlt es sich, die asynchrone Implemen
tierung zu benutzen.
Programmieren Sie einen MQTT-Client, der die Wetterdaten periodisch liest und
ausgibt. Dabei sollen die Daten korrekt in ein Objekt übersetzt und erst dann aus
gegeben werden (also nicht nur einfach den JSON-String auf die Console schrei
ben!). Als Ausgabe können Sie die Daten auf die Console schreiben, graphisch
anzeigen oder als HTML-Seite ausgeben.
Hinweis: zum Parsen der Daten können Sie die Jackson-Library benutzen.
Sie können gerne auch Quarkus benutzen. Hiermit lassen sich nicht nur Weban
wendungen, sondern auch Kommandozeilenanwendungen schreiben.
Seite 2/13
SS 2026
Übungen zu Verteilte Systeme
• Testen Sie Ihren Client mit dem FIM-MQTT-Broker.
• Laden Sie Ihren Quellcode zusammen mit dem Protokoll im Abgabecontainer
hoch.
(d) Chat-Server.
Unter http://10.50.12.24:8080/ steht ein kleiner Chat-Server zu Verfügung, der die
Nachrichten via MQTT an den FIM-Broker sendet. Die Chat-Nachrichten wer
den als Unter-Topics von /aichat versendet– standardmäßig ist dies der Topic
/aichat/default.
• Analysieren Sie denAufbauderMQTT-Chatdaten(dieclientIdistfreiwähl
bar, sollte aber nicht dieselbe sein wie die des Chat-Servers).
• Programmieren Sie einen interaktiven Chat-Client, der sowohl empfangene
Daten anzeigen kann, als auch selbst Chatnachrichten verschicken kann (via
MQTT!).
• Testen Sie Ihren Chat-Client mit dem Vorlesungs-Chat-Server, indem Sie sich
Nachrichten schicken.
• Schicken Sie mir eine Nachricht, wenn Ihr Programm funktioniert.
• Ihr Programm sollte beim Hochfahren in /aichat/clientstate eine kurze
Status-Nachricht schreiben (normaler Text ist ok, wie z.B. “Chat Client XYZ
started/stopped”), sowie ebenfalls eine Nachricht, wenn Ihr Programm been
det wird (Last Will).
Den Chat-Client können Sie wieder als Kommandozeilenanwendung, Weban
wendung oder GUI-Anwendung programmieren. Diesmal müssen allerdings auch
Nachrichten verschickt werden! Ansonsten gelten die Hinweise von Aufgabe c zur
Programmierung.
Besonders schön wäre, wenn Ihr Programm die versendeten Daten als UTF-8
kennzeichnen und mit korrektem Mime-Type verschicken würde
(application/json).
Wer den Chat-Server zum Absturz bringt, sollte mir bitte unbedingt mitteilen, wie
dies reproduziert werden kann.
Laden Sie ihr Programm zusammen mit dem Protokoll ins Moodle hoch. Gerne
kann die gesamte Aufgabe mit sinnvoll benannten Unterverzeichnisstrukturen als
Zip-Datei hochgeladen werden.
Seite 3/13
SS 2026
Übungen zu Verteilte Systeme
Aufgabe 2: Kafka
Auch für diese Aufgabe benötigen Sie wieder einen VPN-Zugang.
Der Kafka-Server der DHBW ist erreichbar unter 10.50.15.52:9092. Kafka ist eine
weitere Möglichkeit, ereignisbasierte Datenströme zu speichern. Es bietet ebenfalls
ein Publisher/Subscriber-Schema, das hier Producer/Consumer heißt (und sich auch
tatsächlich etwas unterschiedlich verhalten kann, siehe später).
(a) Erste Schritte mit Kafka.
Bitte schauen Sie sich folgende Dokumente an:
• Einführung zu Kafka
• Quickstart. Hierfür können Sie gerne schon den DHBW-Server benutzen. Cli
ents werden weiter unten beschrieben. Bitte legen Sie zum Testen Topics mit
Präfix test_ an.
• Kafka-Doku mit API-Dokumentation.
• Bitte löschen Sie keine Topics auf dem DHBW-Server!
• Folgende Topics dürfen nur für das Lesen von Daten benutzt werden.
Schreiben Sie nichts in diese Topics:– azenvy– fim_order, fim_sap_no, fim_readings, alles mit Präfix fim– weather
Sie können als Client beispielsweise kafkacat/kcat verwenden, oder alternativ die
Kafka-Distribution herunterladen. Diese enthält auch Kommandozeilen-Clients,
die unter Windows und Linux lauffähig sind. Kcat kann auch im Docker-Container
gestartet werden (https://github.com/edenhill/kcat#running-in-docker).
Beantworten Sie folgende Fragen:
• Was sind Gemeinsamkeiten und Unterschiede zwischen Kafka und MQTT
Brokern?
• Für welche Szenarien ist Kafka besser geeignet?
• ManfindetbeiVergleichenöfters die Aussage, Kafka würdedasModell“Dumb
Broker / Smart Consumer” implementieren, während bei MQTT “Smart bro
ker / Dumb Consumer” gilt. Was ist damit gemeint? Was muss ein Kafka
Consumer beachten?
• Was sind Partitionen? Für was kann man sie neben Load-Balancing noch
verwenden?
(b) Die allseits beliebten Wetter-Daten mal wieder.
Unter demTopicweatherkönnenSiedieWetterdatenfürMosbach,Mergentheim
und Stuttgart abfragen. Schreiben Sie einen Kafka-Client, der die Wetterdaten via
Seite 4/13
SS 2026
Übungen zu Verteilte Systeme
Kafka auslesen und auf der Konsole ausgeben kann. Sie können hierzu gerne die
Java-basierte Consumer-API verwenden, oder aber jeweilige Client-Implementierungen
für andere Programmiersprachen / Frameworks. Beispielsweise existiert für Py
thon eine Implementierung, siehe hier: https://kafka-python.readthedocs.io/.
• Implementieren und testen Sie Ihren Consumer.
• Geben Sie die Quellen mit in Ihrem Archiv ab.
(c) Eigener Producer.
Schreiben Sie einen eigenen Producer. Hierfür wählen Sie am besten ein Topic
nach dem folgenden Schema:
vlvs_inf22(...)_eindeutiges_kuerzel_oder_gruppenname
Die Daten können Sie beliebig wählen, solange diese visualisierbar sind. Bei
spielsweise können Sie die CPU-Load oder gerne auch kreativere Werte nehmen.
(in Java via ManagementFactory.getOperatingSystemMXBean). Alternativ
können Sie dafür auch die Faker-Library nehmen.
• Implementieren und testen Sie Ihren Producer.
• Geben Sie die Quellen mit in Ihrem Archiv ab.
• Wozu dienen Key und Value beim Versenden von Kafka-Messages?
(d) Kafka-Pipeline mit Grafana.
Neben der Weiterverarbeitung von Daten in einer Pipeline können diese zusätz
lich auch einfach visualisiert werden. Hierfür wird oft Grafana verwendet.
• Schauen Sie sich das Tutorial zu Grafana an.
• Benutzen Sie das DHBW-Grafana unter http://10.50.15.52:3000. Sie können
dort einen Account anlegen, am besten nur einen pro Gruppe. Als eMail
Adresse können Sie gerne irgendeine Fake-Adresse angeben. Sie können
die Accountdaten selbstverständlich dann in der Gruppe teilen.
• Oder installieren Sie sich lokal eine Grafana-Instanz. Pro Gruppe dürfte nur
jeweils eine nötig sein. Die einfachste Möglichkeit ist die Installation via Do
cker.
• Ziel ist, die Wetter-Daten via Grafana zu visualisieren für die 3 Standorte.
Wie bekommen wir die Daten nun vom Broker ins Grafana?
Prinzipiell können Sie gerne auch einen anderen Weg gehen, als den in der Auf
gabevorgeschlagenen. Eine Alternative wäre beispielsweise ein eigener PostgreSQL
oder MySQL-Server, oder auch Prometheus oder InfluxDB.
Noch einfacher geht es allerdings mit Graphite, einer Datenbank für Zeitserienda
ten.
• Machen Sie sich mit Graphite vertraut.
Seite 5/13
SS 2026
Übungen zu Verteilte Systeme
• SchauenSiesich insbesondere an, wie Daten in Graphite abgespeichert wer
den.
• Wir müssen umdenken! Einfach mal JSON reinfüttern wird nicht gehen.
• Überlegen Sie sich eine sinnvolle Namenshierarchie.
FürdasLaborkönnenSiegernedieaufdemKafka-ServerinstallierteGraphite
Instanz nehmen.
Diese ist unter http://10.50.15.52/ zu erreichen. Falls Sie jedoch eine eigene Grafana
Instanz haben, empfiehlt sich auch hier eine eigene Installation, da Sie ansonsten
die Daten mehrfach von der DHBWherunter- und wieder hochladen müssen (Kaf
ka −→ eigener Client −
→Graphite −→ Grafana). Wenn Sie dies nicht stört, können
Sie den Server gerne benutzen.
Bitte benutzen Sie dann dafür ein geeignetes Namensschema, beispielsweise ei
ne Kombination aus Kursname und Gruppennummer als Präfix, um Kollissionen
zu vermeiden.
Aufgabe: Schreiben Sie einen Kafka-Consumer, der die Daten an Ihre Graphite
Instanz übergibt. Dabei sollten auch historische Daten von mindestens An
fang 2025 (oder gerne auch vollständig) visualisiert werden, nicht nur ein
paar aktuelle Datenpunkte.
Für Java können Sie beispielsweise das metrics-graphite-Paket von Dropwizard
zum Speichern von Daten in Graphite benutzen.
Um Bandbreite zu sparen können Sie Ihren Consumer auf dem XeonPhi-Server
laufen lassen. Dieser ist via SSH unter 10.50.15.130 zu erreichen. Derzeit sind
dort Python, Java und Monoinstalliert– bei Sonderwünschen bitte bei mir melden.
Benutzer: vl-vs, Passwort: iz5UDqI0uJ
Hinweis: um lang laufende Jobs zu starten können Sie am besten screen,byobu
oder tmux benutzen (siehe z.B. man screen).
Hinweis: ein Consumer muss nicht immer wieder am Anfang des Topics mit Le
sen beginnen. Sie können ein Offset abspeichern (commit), damit die Arbeit das
nächste Maldort weitergehen kann. Hierzu ist es sinnvoll, einer Consumer-Gruppe
beizutreten. Wählen Sie möglichst eine eindeutige Gruppennummer für Ihren Cli
ent.
Sie können sich überlegen, ob Sie die Daten alle direkt an Graphite weitergeben,
oder sie vorher sinnvoll aggregieren. Da die Wetterdaten eigentlich nur alle 10
Minuten aktualisiert werden, kann hier eine Filterung sinnvoll sein.
Sobald die Daten sinnvoll in Graphite stehen, kann in Grafana eine Data Source
für den Zugriff konfiguriert werden. Nun können Sie die Temperaturdaten einfach
visualisieren! Graphite bietet zwar selbst eine Visualisungsmöglichkeit– diese ist
aber eher sehr einfach gehalten.
Seite 6/13
SS 2026
Übungen zu Verteilte Systeme
• Geben Sie den Code Ihres Kafka-Consumers ab.
• Geben Sie ebenfalls einen Screenshot Ihres Grafana-Dashboards ab (mit
Gruppen-Id, bzw. Matrikelnummern im Titel des Graphen).
Seite 7/13
SS 2026
Übungen zu Verteilte Systeme
Aufgabe 3: Kafka und Eventgetriebene Kommunikation
Wir möchten das Spiel “4 gewinnt” in der Variante “Linetris” implementieren (Spielre
geln siehe Wikipedia). Die Spieler werfen abwechselnd jeweils einen Stein in eine der
Spalten. Wer zuerst 4 aufeinanderfolgende eigene Spielsteine in einer Reihe, Spalte
oder Diagonale legt, gewinnt das Spiel.
Im Unterschied zum normalen Spielverlauf wird bei “Linetris” ähnlich zu Tetris die un
terste Reihe geleert, wenn sie voll ist (solange das Spiel nicht gewonnen wurde). Dies
bedeutet konkret, dass dann natürlich die Spielsteine der Reihen weiter oben nach
unten fallen.
Die Spiellogik wird hierbei vom Game-Master kontrolliert- Sie müssen diese also
nicht selbst implementieren. Anstelle dessen wird hier eine Ereignis-gesteuerte Archi
tektur verwendet. Hierzu existieren auf dem Kafka-Server 10.50.15.52:9092 fol
gende Topics:
game-requests Hier können Sie Ihre Spielzüge übermitteln.
game-events In dieses Topic stellt der Game-Master akzeptierte Spielzüge (State
OK) oder Fehlermeldungen, wenn ein Zug illegal ist (State ERROR).
Ein Kommunikations-Ablauf ist im nachfolgenden Diagramm dargestellt.
Spielzug einreichen
Topic game-requests
Spielzug checken
Game-Client
Modell aktualisieren
Game-Master
Topic game-events
Antwort ERROR/OK
Jedes Spiel bekommt eine eindeutige gameId in Form einer UUID. Diese können Sie
selbst festlegen in einer Message an den Game-MasterviaTopic“game-requests” um
ein Spiel zu starten (siehe Listing 1). Die Client-Ids sowie Spielernamen sind dabei frei
wählbar.
Seite 8/13
SS 2026
Übungen zu Verteilte Systeme
Wichtig: Jeder Spielzug eines Spiels muss das Feld gameId mit der zugehörigen
UUID beinhalten, sonst kann der Zug nicht korrekt zugeordnet werden! Auch müs
sen Sie nachher die vom Game-Master akzeptierten Spielzüge wieder anhand der
gameId korrekt Ihrem Modell des Spielstands zuordnen und Ihr Modell aktualisieren!
Spielzüge sind erst legal, wenn sie vom Game-Master mit OK bestätigt sind. Jedes
Spiel muss dabei eine individuelle und eindeutige gameId bekommen. Sie dürfen die
gameIds also nach einem Spiel nicht wiederholen, sondern Sie müssen für ein neues
Spiel auch eine neue gameId erzeugen!
{ "type": "newGame",
"gameId": "0e8b568f-b78c-4671-bb6a-84233f3b72c9",
"client1": {"name":"My Client"},
"player1": {"name":"Player 1"},
"client2": {"name":"My Client"},
"player2": {"name":"Player 2"} }
Listing 1: Request Spielstart
Der Game-Master antwortet über das Topic “game-events” mit einer Game-Event
Message (siehe Listing 2). Diese beinhaltet den originalen Request im Feld actions
zusammen mit eventuell weiteren nötigen Aktionen wie beispielsweise dem Löschen
einer vollen Zeile (Listing 7) oder einer winAction falls das Spiel mit dem letzten Zug
gewonnen wurde (Listing 8). Hier sollte aber immer zuerst das Feld state ausge
wertet werden. Sollte dieses den Wert ERROR beinhalten, muss der Spielzug ignoriert
werden! Sie können die Fehlermeldung dann im Feld messages nachlesen (siehe
Listing 9).
Nach Anlegen eines neuen Spiels erfolgt eine Bestätigung durch den Game-Master.
Diese enthält die Anzahl der Zeilen und Spalten des Spielfelds.
Wichtig ist, dass Sie die gameId Ihres Spiels kennen. Diese muss bei jedem Request
gesetzt werden, und Sie müssen dann aus dem Topic “game-events” die entsprechen
den Bestätigungen oder Fehlermeldungen des Game-Master herauslesen und Ihr
Datenmodell dementsprechend aktualisieren.
Ihre Spielzüge können Sie dann abwechselnd (siehe Feld player) über das Topic
“game-requests” an den Game-Master schicken. Ein Beispiel für den ersten Spielzug
f
inden Sie in Listing 3. Hierbei ist zu beachten, dass das Feld player ein Enum ent
hält, das nur die Werte PLAYER1 und PLAYER2 annehmen kann– unabhängig von
den gewählten Spielernamen.
Auch hier können Sie Ihr Datenmodell erst aktualisieren wenn die Bestätigung des
Game-Master eintrifft (siehe Listing 4).
Seite 9/13
SS 2026
Übungen zu Verteilte Systeme
{ "timeStamp": 1746130001421,
"actions":[
{ "type":"newGame",
"client1":{"name":"My Client"},
"player1":{"name":"Player 1"},
"client2":{"name":"My Client"},
"player2":{"name":"Player 2"},
"rows":8,
"cols":8 }
],
"state": "OK",
"gameId": "0e8b568f-b78c-4671-bb6a-84233f3b72c9" }
Listing 2: Antwort-Event Spielstart
{ "type": "move",
"gameId": "0e8b568f-b78c-4671-bb6a-84233f3b72c9",
"player": "PLAYER1",
"column": 1 }
Listing 3: Request Spielzug Spieler 1
Danach ist Spieler 2 dran. Den Request sehen Sie in Listing 5, die Antwort des Game
Master in Listing 6.
Wenn das Spiel gewonnen ist, antwortet der Game-Master entsprechend mit einer
Gewinn-Nachricht, die direkt im actions-Feld nach dem move kommt (Listing 8).
Debugging: Wenn Sie nicht sicher sind, ob Sie Ihr Modell korrekt aktualisiert haben,
können Sie an einen Request das Feld requestDebugOut zufügen und auf “true”
setzen. Der Game-Master schickt dann seinen Spielstand als ASCII-Art im Feld de
bugOut zurück. Dies sollte aber nicht missbraucht werden, um damit selbst Output
auf der Konsole zu produzieren, sondern nur um Ihr Modell zu überprüfen!
Noch ein Hinweis: der Game-Master ignoriert alle Requests, die älter sind als ein
Tag. Das heißt, Sie sollten Ihr Spiel innerhalb von 24 Stunden abgeschlossen haben,
sonst wird der Spielstand verworfen
.
Seite 10/13
SS2026 ÜbungenzuVerteilteSysteme
{"timeStamp":1746130002467,
"actions":[
{"type":"move","player":"PLAYER1","column":1}
],
"state":"OK",
"gameId":"0e8b568f-b78c-4671-bb6a-84233f3b72c9"}
Listing4:Antwort-EventSpieler1
{"type":"move",
"gameId":"0e8b568f-b78c-4671-bb6a-84233f3b72c9",
"player":"PLAYER2",
"column":3}
Listing5:RequestSpielzugSpieler2
{"timeStamp":1746130004336,
"actions":[
{"type":"move","player":"PLAYER2","column":3}
],
"state":"OK",
"gameId":"0e8b568f-b78c-4671-bb6a-84233f3b72c9"}
Listing6:Antwort-EventSpieler2
{"timeStamp":1746130485596,
"actions":[
{"type":"move","player":"PLAYER1","column":8},
{"type":"deleteBottomRow","row":8}
],
"state":"OK",
"gameId":"0e8b568f-b78c-4671-bb6a-84233f3b72c9"}
Listing7:Antwort-EventmitLöscheneinerZeile
Seite11/13
SS 2026
Übungen zu Verteilte Systeme
{ "timeStamp": 1746130584078,
"actions":[
{"type":"move","player":"PLAYER2","column":1},
{"type":"winAction","player":"PLAYER2"}
],
"state": "OK",
"gameId": "0e8b568f-b78c-4671-bb6a-84233f3b72c9" }
Listing 8: Antwort-Event Gewinn-Aktion
{
"timeStamp": 1746130691239,
"actions":[
{"type":"move","player":"PLAYER1","column":11}
],
"state": "ERROR",
"message":"Illegal column number: 11, max=8",
"gameId":"7921f007-668d-4612-a660-7729104886fd" }
Listing 9: Antwort-Event mit Fehlermeldung
Seite 12/13
SS 2026
Übungen zu Verteilte Systeme
(a) Generelle Fragen zu Kafka und Partitionen.
• Machen Sie sich mit dem Datenformat vertraut, und schauen Sie sich die
Daten in den Topics “game-requests” und “game-events” an.
• Wie sind die Daten organisiert? Achten Sie dabei auf die Einteilung der Parti
tionen.
• Was muss man beachten, wenn man ein Producer/Consumer-Schema um
setzen möchtemit maximalca.10Consumern?WievielPartitionen sind (min
destens) nötig? Was ist noch nötig um zu vermeiden, dass Events mehrfach
empfangen werden?
• Für welche Zwecke eignen sich Standalone Consumer (ohne Gruppe)?
(b) “Linetris”-Client.
Schreiben Sie einen Client für das Spiel (Konsole, Web-basiert, GUI– egal).
Ihr Client muss dabei die Producer-Rolle übernehmen und ins Topic “game-requests”
Spielzüge einstellen, gleichzeitig aber auch als Consumer akzeptierte Züge aus
dem Topic “game-events” auslesen und seinen Spielstand entsprechend aktuali
sieren. Für die Consumer-Rolle empfiehlt sich die Verwendung einer Consumer
Group, sodaß Sie die Events nicht ständig von Beginn an verarbeiten müssen.
Bitte beachten Sie dabei, dass Sie nicht dieselbe Consumer Group verwenden
können, wenn Sie Ihren Client ein zweites Mal starten (warum ist das so? Sie
he Fragen zu Aufgabe a). Eine Alternative zu Consumer Groups wäre consu
mer.assign gefolgt von consumer.seek.
Sie müssen dabei die Spiellogik nicht komplett selbst implementieren wie z.B.
einen Gewinn zu entdecken oder das Löschen von Zeilen– all dies erledigt der
Game-MasterfürSie.Selbstverständlich müssen Sie aber die Aktionen des Game
Master auf Ihr Modell anwenden (z.B. deleteBottomRow, siehe Listing 7).
Empfohlen wird eine Lösung mit mehreren Threads, da Sie einerseits Spielzüge
annehmen müssen, andererseits aber die Antwort des Game-Master zeitverzö
gert eintrifft und Sie daraufhin dann die Anzeige aktualisieren müssen.
• Ihr Client muss in der Lage sein, mehrfach gestartet zu werden um die Rollen
von Spieler 1 und Spieler 2 übernehmen zu können. Denken Sie sich hier
zu einen Mechanismus aus, wie die beiden Instanzen ein neues Spiel star
ten können. Beispielsweise können Sie hierfür ein eigenes Topic verwenden
(nicht “game-requests”!) um die UUIDs zu synchronisieren. Mehr ist eigent
lich dafür nicht nötig! Der Client von Spieler 1 muss dann das Spiel initiieren.
• Dokumentieren Sie ein erfolgreiches Spiel in der Abgabe-PDF indem Sie die
zugehörigen “game-events” auflisten.
Seite 13/13
