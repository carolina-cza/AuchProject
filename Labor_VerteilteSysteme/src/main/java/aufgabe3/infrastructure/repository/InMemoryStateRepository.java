package aufgabe3.infrastructure.repository;

import aufgabe3.domain.model.GameState;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InMemoryStateRepository {

    private final ConcurrentMap<UUID, GameState> store;

    public InMemoryStateRepository() {
        this.store = new ConcurrentHashMap<>();
    }

    public GameState findById(UUID gameId) {
        return store.get(gameId);
    }

    public void save(GameState state) {
        store.put(state.getGameId(), state);
    }

    public boolean exists(UUID gameId) {
        return store.containsKey(gameId);
    }

    public void delete(UUID gameId) {
        store.remove(gameId);
    }
}