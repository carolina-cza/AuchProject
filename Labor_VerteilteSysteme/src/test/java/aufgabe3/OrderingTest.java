package aufgabe3;

import aufgabe3.application.usecase.ApplyGameEventUseCase;
import aufgabe3.domain.event.GameStartedEvent;
import aufgabe3.domain.event.MovePlacedEvent;
import aufgabe3.domain.model.GameState;
import aufgabe3.domain.service.GameStateProjector;
import aufgabe3.infrastructure.repository.InMemoryStateRepository;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OrderingTest {

    @Test
    void outOfOrderEventsBreakState() {
        UUID gameId = UUID.randomUUID();

        InMemoryStateRepository repo = new InMemoryStateRepository();
        ApplyGameEventUseCase uc = new ApplyGameEventUseCase(new GameStateProjector(), repo);

        MovePlacedEvent move = new MovePlacedEvent(gameId, aufgabe3.domain.model.Player.PLAYER1, 1);

        // move BEFORE game start should be ignored
        uc.execute(move);

        uc.execute(new GameStartedEvent(gameId, 6, 6));

        GameState state = repo.findById(gameId);

        int count = 0;
        for (int r = 0; r < state.getRows(); r++) {
            if (state.getBoard().getCell(r, 0) != null) {
                count++;
            }
        }

        assertEquals(0, count); // move ignored
    }
}