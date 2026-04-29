package aufgabe3.application.usecase;

import aufgabe3.domain.command.PlaceMoveCommand;
import aufgabe3.infrastructure.kafka.KafkaGameCommandProducer;
import aufgabe3.messaging.mapper.CommandMapper;

public class SubmitMoveUseCase {

    private final KafkaGameCommandProducer producer;
    private final CommandMapper mapper;

    public SubmitMoveUseCase(KafkaGameCommandProducer producer,
                             CommandMapper mapper) {
        this.producer = producer;
        this.mapper = mapper;
    }

    public void execute(PlaceMoveCommand command) {
        var dto = mapper.toPlaceMoveRequest(command);

        // KEY = gameId → ensures ordering!
        producer.send(dto, command.getGameId().toString());
    }
}