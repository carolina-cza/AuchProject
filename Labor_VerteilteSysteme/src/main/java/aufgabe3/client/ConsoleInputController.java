package aufgabe3.client;

import aufgabe3.application.usecase.StartGameUseCase;
import aufgabe3.application.usecase.SubmitMoveUseCase;
import aufgabe3.domain.command.PlaceMoveCommand;
import aufgabe3.domain.command.StartGameCommand;
import aufgabe3.domain.model.GameState;
import aufgabe3.domain.model.Player;
import aufgabe3.infrastructure.repository.InMemoryStateRepository;

import java.util.Scanner;
import java.util.UUID;

public class ConsoleInputController implements Runnable {

    private final StartGameUseCase startGameUseCase;
    private final SubmitMoveUseCase submitMoveUseCase;
    private final GameContext gameContext;
    private final InMemoryStateRepository repository;

    private volatile boolean running = true;

    public ConsoleInputController(StartGameUseCase startGameUseCase,
                                  SubmitMoveUseCase submitMoveUseCase,
                                  GameContext gameContext,
                                  InMemoryStateRepository repository) {
        this.startGameUseCase = startGameUseCase;
        this.submitMoveUseCase = submitMoveUseCase;
        this.gameContext = gameContext;
        this.repository = repository;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (!gameContext.hasGame() && running) {
            System.out.println("\n=== LINETRIS CLIENT ===");
            System.out.println("1 = Start new game (Player 1)");
            System.out.println("2 = Join existing game (Player 2)");
            System.out.print("> ");

            String choice = scanner.nextLine().trim();

            if ("1".equals(choice)) {
                UUID gameId = UUID.randomUUID();
                gameContext.setCurrentGameId(gameId);
                gameContext.setLocalPlayer(Player.PLAYER1);

                StartGameCommand startCmd = new StartGameCommand(
                        gameId,
                        "Client",
                        "Client",
                        "Player1",
                        "Player2"
                );

                startGameUseCase.execute(startCmd);
                System.out.println("New game started. gameId: " + gameId);
                break;

            } else if ("2".equals(choice)) {

                while (running && !gameContext.hasGame()) {
                    System.out.println("Enter gameId to join:");
                    System.out.print("> ");

                    String raw = scanner.nextLine().trim();

                    if (raw.isEmpty()) {
                        System.out.println("GameId cannot be empty.");
                        continue;
                    }

                    try {
                        UUID gameId = UUID.fromString(raw);
                        gameContext.setCurrentGameId(gameId);
                        gameContext.setLocalPlayer(Player.PLAYER2);
                        System.out.println("Joined game: " + gameId);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid UUID. Try again.");
                    }
                }

            } else {
                System.out.println("Unknown option. Try again.");
            }
        }

        while (running) {
            System.out.println("\n--- Commands ---");
            System.out.println("m = Place move");
            System.out.println("exit = Quit");
            System.out.print("> ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "m":
                    if (!gameContext.hasGame()) {
                        System.out.println("No active game.");
                        break;
                    }

                    GameState state = repository.findById(gameContext.getCurrentGameId());

                    if (state == null) {
                        System.out.println("Game state not yet initialized.");
                        break;
                    }

                    Player player = gameContext.getLocalPlayer();

                    if (player == null) {
                        System.out.println("Player role not set.");
                        break;
                    }

                    System.out.print("Column: ");
                    int column;
                    try {
                        column = Integer.parseInt(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid column.");
                        break;
                    }

                    PlaceMoveCommand moveCmd =
                            new PlaceMoveCommand(gameContext.getCurrentGameId(), player, column);

                    submitMoveUseCase.execute(moveCmd);
                    break;

                case "exit":
                    shutdown();
                    break;

                default:
                    System.out.println("Unknown command.");
                    break;
            }
        }
    }

    public void shutdown() {
        running = false;
    }
}