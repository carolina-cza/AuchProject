package aufgabe3.domain.event;

import java.util.UUID;

public class GameStartedEvent extends GameEvent {

    private int rows;
    private int cols;

    public GameStartedEvent(UUID gameId, int rows, int cols) {
        super(gameId);
        this.rows = rows;
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
