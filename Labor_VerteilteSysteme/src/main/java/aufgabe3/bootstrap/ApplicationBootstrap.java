package aufgabe3.bootstrap;

import aufgabe3.client.GameClient;

public class ApplicationBootstrap {

    public GameClient createGameClient() {
        return new GameClient();
    }
}