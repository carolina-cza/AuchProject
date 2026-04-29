package aufgabe3.domain.command;

import java.util.UUID;

public class StartGameCommand {

    private UUID gameId;
    private String client1Name;
    private String client2Name;
    private String player1Name;
    private String player2Name;

    public StartGameCommand(UUID gameId, String client1Name, String client2Name,
                            String player1Name, String player2Name) {
        this.gameId = gameId;
        this.client1Name = client1Name;
        this.client2Name = client2Name;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public UUID getGameId() {
        return gameId;
    }

    public String getClient1Name() {
        return client1Name;
    }

    public String getClient2Name() {
        return client2Name;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }
}
