package aufgabe2d;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.PrintWriter;
import java.net.Socket;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class GrafanaPipeline {

    // DHBW Graphite Server
    private static final String GRAPHITE_HOST = "10.50.15.52";
    private static final int GRAPHITE_PORT = 2003;

    // Präfix mit Gruppen-ID damit keine Kollissionen mit anderen Gruppen
    private static final String PREFIX = "vlvs_inf23_RKC";

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Properties props = new Properties();
        props.put("bootstrap.servers", "10.50.15.52:9092");
        props.put("group.id", "gruppe-RKC-graphite");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // Wichtig: von Anfang an lesen für historische Daten
        props.put("auto.offset.reset", "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(List.of("weather"));

        System.out.println("Starte Graphite Forwarder...");

        // Verbindung zu Graphite
        Socket socket = new Socket(GRAPHITE_HOST, GRAPHITE_PORT);
        PrintWriter graphite = new PrintWriter(socket.getOutputStream(), true);

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));

            for (ConsumerRecord<String, String> record : records) {
                try {
                    WeatherData2d w = mapper.readValue(record.value(), WeatherData2d.class);

                    // Nur die 3 Städte
                    if (!w.getCity().equals("Mosbach") &&
                            !w.getCity().equals("Stuttgart") &&
                            !w.getCity().equals("Bad Mergentheim")) {
                        continue;
                    }

                    // Stadtname normalisieren (kein Leerzeichen erlaubt in Graphite)
                    String city = w.getCity()
                            .toLowerCase()
                            .replace(" ", "_");

                    // Timestamp: Graphite braucht Sekunden, Java gibt Millisekunden
                    // timeStamp ist ein String wie "2023-04-08T00:02:06.041+00:00"
                    long timestamp = java.time.OffsetDateTime
                            .parse(w.getTimeStamp())
                            .toEpochSecond();

                    // Format: prefix.stadt.messwert wert timestamp
                    graphite.println(PREFIX + "." + city + ".tempCurrent " + w.getTempCurrent() + " " + timestamp);
                    graphite.println(PREFIX + "." + city + ".tempMax " + w.getTempMax() + " " + timestamp);
                    graphite.println(PREFIX + "." + city + ".tempMin " + w.getTempMin() + " " + timestamp);

                    System.out.println("→ Graphite: " + city + " | " + w.getTempCurrent() + "°C | " + w.getTimeStamp());

                } catch (Exception e) {
                    System.err.println("Fehler: " + e.getMessage());
                }
            }
            // Offset speichern damit wir nicht immer von vorne anfangen
            consumer.commitSync();
        }
    }
}
