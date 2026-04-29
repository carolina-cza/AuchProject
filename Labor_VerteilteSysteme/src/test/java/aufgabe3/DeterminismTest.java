package aufgabe3;

import aufgabe3.application.usecase.ApplyGameEventUseCase;
import aufgabe3.domain.event.*;
import aufgabe3.domain.model.GameState;
import aufgabe3.domain.service.GameStateProjector;
import aufgabe3.infrastructure.repository.InMemoryStateRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DeterminismTest {

    private ApplyGameEventUseCase createUseCase(InMemoryStateRepository repo) {
        return new ApplyGameEventUseCase(new GameStateProjector(), repo);
    }

    private List<GameEvent> createEventStream(UUID gameId) {
        List<GameEvent> events = new ArrayList<>();

        events.add(new GameStartedEvent(gameId, 6, 6));

        for (int i = 0; i < 20; i++) {
            events.add(new MovePlacedEvent(gameId, i % 2 == 0 ? aufgabe3.domain.model.Player.PLAYER1 : aufgabe3.domain.model.Player.PLAYER2, (i % 6) + 1));
        }

        events.add(new BottomRowDeletedEvent(gameId, 5));

        return events;
    }

    @Test
    void sameEventStreamProducesSameState() {
        UUID gameId = UUID.randomUUID();

        List<GameEvent> stream = createEventStream(gameId);

        InMemoryStateRepository repo1 = new InMemoryStateRepository();
        InMemoryStateRepository repo2 = new InMemoryStateRepository();

        ApplyGameEventUseCase uc1 = createUseCase(repo1);
        ApplyGameEventUseCase uc2 = createUseCase(repo2);

        for (GameEvent e : stream) {
            uc1.execute(e);
            uc2.execute(e);
        }

        GameState s1 = repo1.findById(gameId);
        GameState s2 = repo2.findById(gameId);

        assertNotNull(s1);
        assertNotNull(s2);

        assertEquals(s1.getRows(), s2.getRows());
        assertEquals(s1.getCols(), s2.getCols());

        for (int r = 0; r < s1.getRows(); r++) {
            for (int c = 0; c < s1.getCols(); c++) {
                assertEquals(
                        s1.getBoard().getCell(r, c),
                        s2.getBoard().getCell(r, c)
                );
            }
        }
    }
}