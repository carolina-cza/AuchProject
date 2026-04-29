package aufgabe3.domain.event;

import aufgabe3.domain.model.Player;

import java.util.UUID;

public class GameWonEvent extends GameEvent {

    private Player player;

    public GameWonEvent(UUID gameId, Player player) {
        super(gameId);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
