package aufgabe3.application.usecase;

import aufgabe3.domain.command.StartGameCommand;
import aufgabe3.infrastructure.kafka.KafkaGameCommandProducer;
import aufgabe3.messaging.mapper.CommandMapper;

public class StartGameUseCase {

    private final KafkaGameCommandProducer producer;
    private final CommandMapper mapper;

    public StartGameUseCase(KafkaGameCommandProducer producer,
                            CommandMapper mapper) {
        this.producer = producer;
        this.mapper = mapper;
    }

    public void execute(StartGameCommand command) {
        var dto = mapper.toStartGameRequest(command);

        // KEY = gameId → CRITICAL for partitioning
        producer.send(dto, command.getGameId().toString());
    }
}