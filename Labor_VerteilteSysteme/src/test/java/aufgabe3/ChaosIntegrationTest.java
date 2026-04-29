package aufgabe3;

import aufgabe3.application.usecase.ApplyGameEventUseCase;
import aufgabe3.domain.event.*;
import aufgabe3.domain.model.GameState;
import aufgabe3.domain.model.Player;
import aufgabe3.domain.service.GameStateProjector;
import aufgabe3.infrastructure.repository.InMemoryStateRepository;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ChaosIntegrationTest {

    @Test
    void chaos_event_stream_produces_deterministic_correct_state() {

        // setup
        UUID gameId = UUID.randomUUID();

        InMemoryStateRepository repo = new InMemoryStateRepository();
        GameStateProjector projector = new GameStateProjector();
        ApplyGameEventUseCase useCase = new ApplyGameEventUseCase(projector, repo);

        // --- EVENT STREAM (CHAOS SEQUENCE) ---

        // [1] start game
        useCase.execute(new GameStartedEvent(gameId, 6, 8));

        // [2] P1 move
        useCase.execute(new MovePlacedEvent(gameId, Player.PLAYER1, 1));

        // [3] P2 move
        useCase.execute(new MovePlacedEvent(gameId, Player.PLAYER2, 2));

        // [3 DUPLICATE] -> SHOULD NOT HAPPEN IN REAL SYSTEM, but we simulate chaos
        useCase.execute(new MovePlacedEvent(gameId, Player.PLAYER2, 2));

        // [4 ERROR] -> should be ignored -> we simulate by NOT calling useCase
        // intentionally skipped

        // [5]
        useCase.execute(new MovePlacedEvent(gameId, Player.PLAYER1, 3));

        // [6]
        useCase.execute(new MovePlacedEvent(gameId, Player.PLAYER2, 4));

        // [7]
        useCase.execute(new MovePlacedEvent(gameId, Player.PLAYER1, 5));

        // [8]
        useCase.execute(new MovePlacedEvent(gameId, Player.PLAYER2, 6));

        // [9]
        useCase.execute(new MovePlacedEvent(gameId, Player.PLAYER1, 7));

        // [10]
        useCase.execute(new MovePlacedEvent(gameId, Player.PLAYER2, 8));

        // [11] row deletion (simulate Game-Master action)
        useCase.execute(new BottomRowDeletedEvent(gameId, 6));

        // stack vertical column
        useCase.execute(new MovePlacedEvent(gameId, Player.PLAYER1, 1));
        useCase.execute(new MovePlacedEvent(gameId, Player.PLAYER2, 1));
        useCase.execute(new MovePlacedEvent(gameId, Player.PLAYER1, 1));
        useCase.execute(new MovePlacedEvent(gameId, Player.PLAYER2, 1));
        useCase.execute(new MovePlacedEvent(gameId, Player.PLAYER1, 1));

        // [WIN]
        useCase.execute(new GameWonEvent(gameId, Player.PLAYER1));

        // --- ASSERTIONS ---

        GameState state = repo.findById(gameId);

        assertNotNull(state);

        // 1. Game must be won
        assertEquals(Player.PLAYER1, state.getWinner());
        assertEquals("WON", state.getStatus().name());

        // 2. Board consistency check (no floating pieces)
        var grid = state.getBoard().getGrid();

        for (int col = 0; col < state.getCols(); col++) {
            boolean emptyFound = false;

            for (int row = state.getRows() - 1; row >= 0; row--) {
                if (grid[row][col] == null) {
                    emptyFound = true;
                } else {
                    // once empty below, no piece allowed above
                    assertFalse(emptyFound, "Floating piece detected in column " + col);
                }
            }
        }

        // 3. Determinism check -> replay same sequence again

        InMemoryStateRepository repo2 = new InMemoryStateRepository();
        ApplyGameEventUseCase useCase2 = new ApplyGameEventUseCase(
                new GameStateProjector(),
                repo2
        );

        // replay exact same sequence
        useCase2.execute(new GameStartedEvent(gameId, 6, 8));
        useCase2.execute(new MovePlacedEvent(gameId, Player.PLAYER1, 1));
        useCase2.execute(new MovePlacedEvent(gameId, Player.PLAYER2, 2));
        useCase2.execute(new MovePlacedEvent(gameId, Player.PLAYER2, 2)); // duplicate again
        useCase2.execute(new MovePlacedEvent(gameId, Player.PLAYER1, 3));
        useCase2.execute(new MovePlacedEvent(gameId, Player.PLAYER2, 4));
        useCase2.execute(new MovePlacedEvent(gameId, Player.PLAYER1, 5));
        useCase2.execute(new MovePlacedEvent(gameId, Player.PLAYER2, 6));
        useCase2.execute(new MovePlacedEvent(gameId, Player.PLAYER1, 7));
        useCase2.execute(new MovePlacedEvent(gameId, Player.PLAYER2, 8));
        useCase2.execute(new BottomRowDeletedEvent(gameId, 6));
        useCase2.execute(new MovePlacedEvent(gameId, Player.PLAYER1, 1));
        useCase2.execute(new MovePlacedEvent(gameId, Player.PLAYER2, 1));
        useCase2.execute(new MovePlacedEvent(gameId, Player.PLAYER1, 1));
        useCase2.execute(new MovePlacedEvent(gameId, Player.PLAYER2, 1));
        useCase2.execute(new MovePlacedEvent(gameId, Player.PLAYER1, 1));
        useCase2.execute(new GameWonEvent(gameId, Player.PLAYER1));

        GameState state2 = repo2.findById(gameId);

        assertNotNull(state2);

        // 4. FINAL PROOF: deterministic equality
        assertEquals(
                serializeBoard(state),
                serializeBoard(state2),
                "State is not deterministic!"
        );
    }

    private String serializeBoard(GameState state) {
        StringBuilder sb = new StringBuilder();
        var grid = state.getBoard().getGrid();

        for (int r = 0; r < state.getRows(); r++) {
            for (int c = 0; c < state.getCols(); c++) {
                var cell = grid[r][c];
                sb.append(cell == null ? "." : cell.name());
            }
        }
        return sb.toString();
    }
}