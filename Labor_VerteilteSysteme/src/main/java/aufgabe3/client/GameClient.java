package aufgabe3.client;

import aufgabe3.application.usecase.ApplyGameEventUseCase;
import aufgabe3.application.usecase.StartGameUseCase;
import aufgabe3.application.usecase.SubmitMoveUseCase;
import aufgabe3.domain.service.GameStateProjector;
import aufgabe3.infrastructure.kafka.KafkaConsumerLoop;
import aufgabe3.infrastructure.kafka.KafkaGameCommandProducer;
import aufgabe3.infrastructure.kafka.KafkaGameEventConsumer;
import aufgabe3.infrastructure.repository.InMemoryStateRepository;
import aufgabe3.infrastructure.serialization.JsonSerializer;
import aufgabe3.messaging.mapper.CommandMapper;
import aufgabe3.messaging.mapper.EventMapper;

public class GameClient {

    private final Thread consumerThread;
    private final Thread inputThread;

    private final KafkaConsumerLoop consumerLoop;
    private final ConsoleInputController inputController;

    public GameClient() {
        JsonSerializer serializer = new JsonSerializer();
        InMemoryStateRepository repository = new InMemoryStateRepository();

        KafkaGameEventConsumer consumer = new KafkaGameEventConsumer(serializer);
        KafkaGameCommandProducer producer = new KafkaGameCommandProducer(serializer);

        GameStateProjector projector = new GameStateProjector();

        EventMapper eventMapper = new EventMapper();
        CommandMapper commandMapper = new CommandMapper();

        ApplyGameEventUseCase applyUseCase =
                new ApplyGameEventUseCase(projector, repository);

        StartGameUseCase startGameUseCase =
                new StartGameUseCase(producer, commandMapper);

        SubmitMoveUseCase submitMoveUseCase =
                new SubmitMoveUseCase(producer, commandMapper);

        GameContext gameContext = new GameContext();

        this.consumerLoop = new KafkaConsumerLoop(
                consumer,
                eventMapper,
                applyUseCase,
                gameContext
        );

        this.consumerThread = new Thread(consumerLoop, "game-event-consumer");

        this.inputController = new ConsoleInputController(
                startGameUseCase,
                submitMoveUseCase,
                gameContext,
                repository
        );

        this.inputThread = new Thread(inputController, "console-input");
    }

    public void start() {
        consumerThread.start();

        inputThread.start();

        consumerLoop.awaitReady();
    }

    public void shutdown() {
        consumerLoop.shutdown();
        inputController.shutdown();
    }
}