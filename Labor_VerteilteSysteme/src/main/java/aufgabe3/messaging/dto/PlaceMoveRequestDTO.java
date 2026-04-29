package aufgabe3.messaging.dto;

import java.util.UUID;

public class PlaceMoveRequestDTO {

    private String type = "move";
    private UUID gameId;
    private String player;
    private int column;

    public PlaceMoveRequestDTO(UUID gameId, String player, int column) {
        this.gameId = gameId;
        this.player = player;
        this.column = column;
    }

    public String getType() {
        return type;
    }

    public UUID getGameId() {
        return gameId;
    }

    public String getPlayer() {
        return player;
    }

    public int getColumn() {
        return column;
    }
}
