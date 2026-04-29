package aufgabe3.domain.command;

import aufgabe3.domain.model.Player;

import java.util.UUID;

public class PlaceMoveCommand {

    private UUID gameId;
    private Player player;
    private int column;

    public PlaceMoveCommand(UUID gameId, Player player, int column) {
        this.gameId = gameId;
        this.player = player;
        this.column = column;
    }

    public UUID getGameId() {
        return gameId;
    }

    public Player getPlayer() {
        return player;
    }

    public int getColumn() {
        return column;
    }
}
