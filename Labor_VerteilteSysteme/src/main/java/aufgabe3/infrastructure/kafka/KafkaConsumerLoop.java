package aufgabe3.infrastructure.kafka;

import aufgabe3.application.usecase.ApplyGameEventUseCase;
import aufgabe3.client.GameContext;
import aufgabe3.domain.event.GameEvent;
import aufgabe3.messaging.dto.GameEventEnvelopeDTO;
import aufgabe3.messaging.mapper.EventMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class KafkaConsumerLoop implements Runnable {

    private final KafkaGameEventConsumer consumer;
    private final EventMapper eventMapper;
    private final ApplyGameEventUseCase useCase;
    private final GameContext gameContext;
    private final CountDownLatch readyLatch = new CountDownLatch(1);
    private final Set<String> processedRecords = Collections.newSetFromMap(new ConcurrentHashMap<>());

    private volatile boolean running = true;

    public KafkaConsumerLoop(KafkaGameEventConsumer consumer,
                             EventMapper eventMapper,
                             ApplyGameEventUseCase useCase,
                             GameContext gameContext) {
        this.consumer = consumer;
        this.eventMapper = eventMapper;
        this.useCase = useCase;
        this.gameContext = gameContext;
    }

    @Override
    public void run() {
        consumer.waitUntilAssigned();
        readyLatch.countDown();

        gameContext.awaitGameId();

        while (running) {
            List<ConsumerRecord<String, GameEventEnvelopeDTO>> records = consumer.poll();

            if (records.isEmpty()) {
                continue;
            }

            UUID currentGameId = gameContext.getCurrentGameId();

            for (ConsumerRecord<String, GameEventEnvelopeDTO> record : records) {
                try {
                    GameEventEnvelopeDTO dto = record.value();

                    if (dto == null) {
                        continue;
                    }

                    if (currentGameId == null || !currentGameId.equals(dto.getGameId())) {
                        continue;
                    }

                    String recordId = record.topic() + "-" + record.partition() + "-" + record.offset();

                    if (!processedRecords.add(recordId)) {
                        continue;
                    }

                    if ("ERROR".equals(dto.getState())) {
                        eventMapper.printError(dto);
                        continue;
                    }

                    if (!"OK".equals(dto.getState())) {
                        System.out.println("[WARN] Ignored event with unknown state: " + dto.getState());
                        continue;
                    }

                    List<GameEvent> events = eventMapper.map(dto);

                    for (GameEvent event : events) {
                        useCase.execute(event);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            consumer.commit();
        }
    }

    public void awaitReady() {
        try {
            readyLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        running = false;
        consumer.close();
    }
}