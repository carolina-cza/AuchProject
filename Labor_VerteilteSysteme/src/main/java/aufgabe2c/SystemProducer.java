package aufgabe2c;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SystemProducer {

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.50.15.52:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        ObjectMapper mapper = new ObjectMapper();
        OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        System.out.println("Producer starten");

        while (true) {
            Map<String, Object> data = new HashMap<>();
            data.put("cpu", os.getSystemLoadAverage());
            data.put("cores", os.getAvailableProcessors());
            data.put("time", System.currentTimeMillis());

            String json = mapper.writeValueAsString(data);

            producer.send(new ProducerRecord<>("vlvs_inf23_gruppeRCK", "system", json));
            System.out.println("Gesendet: " + json);

            Thread.sleep(5000);
        }
    }
}