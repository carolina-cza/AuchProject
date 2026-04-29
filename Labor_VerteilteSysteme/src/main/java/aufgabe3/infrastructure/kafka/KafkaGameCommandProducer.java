package aufgabe3.infrastructure.kafka;

import aufgabe3.infrastructure.serialization.JsonSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaGameCommandProducer {

    private static final String TOPIC = "game-requests";

    private final KafkaProducer<String, String> producer;
    private final JsonSerializer serializer;

    public KafkaGameCommandProducer(JsonSerializer serializer) {
        Properties props = KafkaConfig.producerProps();
        this.producer = new KafkaProducer<>(props);
        this.serializer = serializer;
    }

    public void send(Object dto, String key) {
        String json = serializer.serialize(dto);

        ProducerRecord<String, String> record =
                new ProducerRecord<>(TOPIC, key, json);

        producer.send(record);
    }

    public void close() {
        producer.close();
    }
}