package aufgabe3.infrastructure.kafka;

import aufgabe3.infrastructure.serialization.JsonSerializer;
import aufgabe3.messaging.dto.GameEventEnvelopeDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class KafkaGameEventConsumer {

    private static final String TOPIC = "game-events";

    private final KafkaConsumer<String, String> consumer;
    private final JsonSerializer serializer;

    public KafkaGameEventConsumer(JsonSerializer serializer) {
        Properties props = KafkaConfig.consumerProps();
        this.consumer = new KafkaConsumer<>(props);
        this.serializer = serializer;

        consumer.subscribe(Collections.singletonList(TOPIC));
    }

    public void waitUntilAssigned() {
        while (consumer.assignment().isEmpty()) {
            consumer.poll(Duration.ofMillis(100));
        }
    }

    public List<ConsumerRecord<String, GameEventEnvelopeDTO>> poll() {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

        List<ConsumerRecord<String, GameEventEnvelopeDTO>> result = new ArrayList<>();

        for (ConsumerRecord<String, String> record : records) {
            GameEventEnvelopeDTO dto =
                    serializer.deserialize(record.value(), GameEventEnvelopeDTO.class);

            result.add(new ConsumerRecord<>(
                    record.topic(),
                    record.partition(),
                    record.offset(),
                    record.key(),
                    dto
            ));
        }

        return result;
    }

    public void commit() {
        consumer.commitSync();
    }

    public void close() {
        consumer.close();
    }
}