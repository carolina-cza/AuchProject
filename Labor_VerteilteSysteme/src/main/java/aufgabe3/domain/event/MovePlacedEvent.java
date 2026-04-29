package aufgabe3.domain.event;

import aufgabe3.domain.model.Player;

import java.util.UUID;

public class MovePlacedEvent extends GameEvent {

    private Player player;
    private int column;

    public MovePlacedEvent(UUID gameId, Player player, int column) {
        super(gameId);
        this.player = player;
        this.column = column;
    }

    public Player getPlayer() {
        return player;
    }

    public int getColumn() {
        return column;
    }
}
