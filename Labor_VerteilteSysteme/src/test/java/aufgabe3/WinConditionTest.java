package aufgabe3;

import aufgabe3.application.usecase.ApplyGameEventUseCase;
import aufgabe3.domain.event.GameStartedEvent;
import aufgabe3.domain.event.GameWonEvent;
import aufgabe3.domain.model.GameState;
import aufgabe3.domain.model.GameStatus;
import aufgabe3.domain.model.Player;
import aufgabe3.domain.service.GameStateProjector;
import aufgabe3.infrastructure.repository.InMemoryStateRepository;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class WinConditionTest {

    @Test
    void gameWonEventSetsWinner() {
        UUID gameId = UUID.randomUUID();

        InMemoryStateRepository repo = new InMemoryStateRepository();
        ApplyGameEventUseCase uc = new ApplyGameEventUseCase(new GameStateProjector(), repo);

        uc.execute(new GameStartedEvent(gameId, 6, 6));
        uc.execute(new GameWonEvent(gameId, Player.PLAYER1));

        GameState state = repo.findById(gameId);

        assertEquals(GameStatus.WON, state.getStatus());
        assertEquals(Player.PLAYER1, state.getWinner());
    }
}