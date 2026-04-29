package aufgabe3;

import aufgabe3.application.usecase.ApplyGameEventUseCase;
import aufgabe3.domain.event.BottomRowDeletedEvent;
import aufgabe3.domain.event.GameStartedEvent;
import aufgabe3.domain.event.MovePlacedEvent;
import aufgabe3.domain.model.GameState;
import aufgabe3.domain.service.GameStateProjector;
import aufgabe3.infrastructure.repository.InMemoryStateRepository;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RowDeletionTest {

    @Test
    void bottomRowDeletionShiftsBoard() {
        UUID gameId = UUID.randomUUID();

        InMemoryStateRepository repo = new InMemoryStateRepository();
        ApplyGameEventUseCase uc = new ApplyGameEventUseCase(new GameStateProjector(), repo);

        uc.execute(new GameStartedEvent(gameId, 4, 4));

        // fill bottom row
        for (int c = 1; c <= 4; c++) {
            uc.execute(new MovePlacedEvent(gameId, aufgabe3.domain.model.Player.PLAYER1, c));
        }

        uc.execute(new BottomRowDeletedEvent(gameId, 3));

        GameState state = repo.findById(gameId);

        for (int c = 0; c < 4; c++) {
            assertNull(state.getBoard().getCell(3, c)); // bottom row cleared
        }
    }
}