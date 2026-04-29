package aufgabe3.domain.event;

import java.util.UUID;

public abstract class GameEvent {

    private UUID gameId;

    public GameEvent(UUID gameId) {
        this.gameId = gameId;
    }

    public UUID getGameId() {
        return gameId;
    }
}
