package aufgabe3;

import aufgabe3.bootstrap.ApplicationBootstrap;
import aufgabe3.client.GameClient;

public class Main {

    public static void main(String[] args) {
        ApplicationBootstrap bootstrap = new ApplicationBootstrap();
        GameClient client = bootstrap.createGameClient();

        Runtime.getRuntime().addShutdownHook(new Thread(client::shutdown));

        client.start();
    }
}