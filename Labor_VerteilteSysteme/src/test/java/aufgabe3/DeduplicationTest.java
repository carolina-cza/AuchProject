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

public class DeduplicationTest {

    @Test
    void duplicateMoveIsAppliedTwice_byDesign() {
        // This test is intentionally NOT testing idempotency.
        // The client applies all events deterministically as received.
        // Deduplication is considered responsibility of Kafka / Game-Master.

        UUID gameId = UUID.randomUUID();

        InMemoryStateRepository repo = new InMemoryStateRepository();
        ApplyGameEventUseCase uc = new ApplyGameEventUseCase(new GameStateProjector(), repo);

        uc.execute(new GameStartedEvent(gameId, 6, 6));

        MovePlacedEvent move = new MovePlacedEvent(
                gameId,
                aufgabe3.domain.model.Player.PLAYER1,
                1
        );

        uc.execute(move);
        uc.execute(move); // duplicate event

        GameState state = repo.findById(gameId);

        int count = 0;
        for (int r = 0; r < state.getRows(); r++) {
            if (state.getBoard().getCell(r, 0) != null) {
                count++;
            }
        }

        // Expectation: BOTH events are applied → 2 stones
        assertEquals(2, count);
    }
}