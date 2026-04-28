package aufgabe2b;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class WeatherConsumer {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.50.15.52:9092");
        props.put("group.id", "gruppe-b-weather");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "earliest");

        ObjectMapper mapper = new ObjectMapper();

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(List.of("weather"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(3));
            for (ConsumerRecord<String, String> record : records) {
                try {
                    WeatherData2b w = mapper.readValue(record.value(), WeatherData2b.class);
                    if (w.getCity().equals("Mosbach") || w.getCity().equals("Stuttgart") || w.getCity().equals("Bad Mergentheim")) {
                        System.out.println(w);
                    }
                } catch (Exception e) {
                    // ignore malformed messages
                }
            }
        }
    }
}