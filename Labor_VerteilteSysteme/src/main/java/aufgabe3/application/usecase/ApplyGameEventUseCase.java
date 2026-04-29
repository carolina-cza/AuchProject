package aufgabe3.application.usecase;

import aufgabe3.domain.event.GameEvent;
import aufgabe3.domain.event.GameStartedEvent;
import aufgabe3.domain.model.GameState;
import aufgabe3.domain.model.Player;
import aufgabe3.domain.service.GameStateProjector;
import aufgabe3.infrastructure.repository.InMemoryStateRepository;

public class ApplyGameEventUseCase {

    private final GameStateProjector gameStateProjector;
    private final InMemoryStateRepository inMemoryStateRepository;

    public ApplyGameEventUseCase(GameStateProjector gameStateProjector,
                                 InMemoryStateRepository inMemoryStateRepository) {
        this.gameStateProjector = gameStateProjector;
        this.inMemoryStateRepository = inMemoryStateRepository;
    }

    public void execute(GameEvent event) {
        GameState state = inMemoryStateRepository.findById(event.getGameId());

        if (state == null) {
            if (event instanceof GameStartedEvent) {
                GameStartedEvent e = (GameStartedEvent) event;
                state = new GameState(e.getGameId(), e.getRows(), e.getCols());
            } else {
                return;
            }
        }

        gameStateProjector.apply(state, event);
        inMemoryStateRepository.save(state);

        printState(state, event);
    }

    private void printState(GameState state, GameEvent event) {
        System.out.println("\n=== EVENT APPLIED: " + event.getClass().getSimpleName() + " ===");
        System.out.println("GameId: " + state.getGameId());
        System.out.println("Status: " + state.getStatus());

        if (state.getWinner() != null) {
            System.out.println("WINNER: " + state.getWinner());
        }

        printBoard(state);
    }

    private void printBoard(GameState state) {
        Player[][] grid = state.getBoard().getGrid();

        System.out.println("Board:");

        for (int row = 0; row < state.getRows(); row++) {
            for (int col = 0; col < state.getCols(); col++) {
                Player cell = grid[row][col];

                if (cell == null) {
                    System.out.print(". ");
                } else if (cell == Player.PLAYER1) {
                    System.out.print("X ");
                } else {
                    System.out.print("O ");
                }
            }
            System.out.println();
        }

        System.out.println();
    }
}