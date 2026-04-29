package aufgabe3.client;

import aufgabe3.domain.model.Player;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class GameContext {

    private final AtomicReference<UUID> currentGameId = new AtomicReference<>();
    private final AtomicReference<Player> localPlayer = new AtomicReference<>();
    private final CountDownLatch gameIdLatch = new CountDownLatch(1);

    public void setCurrentGameId(UUID gameId) {
        currentGameId.set(gameId);
        gameIdLatch.countDown();
    }

    public UUID getCurrentGameId() {
        return currentGameId.get();
    }

    public boolean hasGame() {
        return currentGameId.get() != null;
    }

    public void awaitGameId() {
        try {
            gameIdLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void setLocalPlayer(Player player) {
        localPlayer.set(player);
    }

    public Player getLocalPlayer() {
        return localPlayer.get();
    }
}