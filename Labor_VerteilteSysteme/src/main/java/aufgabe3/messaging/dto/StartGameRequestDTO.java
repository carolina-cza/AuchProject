package aufgabe3.messaging.dto;

import java.util.UUID;

public class StartGameRequestDTO {

    private String type = "newGame";
    private UUID gameId;
    private NameDTO client1;
    private NameDTO player1;
    private NameDTO client2;
    private NameDTO player2;

    public StartGameRequestDTO(UUID gameId, NameDTO client1, NameDTO player1,
                               NameDTO client2, NameDTO player2) {
        this.gameId = gameId;
        this.client1 = client1;
        this.player1 = player1;
        this.client2 = client2;
        this.player2 = player2;
    }

    public String getType() {
        return type;
    }

    public UUID getGameId() {
        return gameId;
    }

    public NameDTO getClient1() {
        return client1;
    }

    public NameDTO getPlayer1() {
        return player1;
    }

    public NameDTO getClient2() {
        return client2;
    }

    public NameDTO getPlayer2() {
        return player2;
    }
}
