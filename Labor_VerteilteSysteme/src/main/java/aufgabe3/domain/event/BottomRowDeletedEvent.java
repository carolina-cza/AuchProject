package aufgabe3.domain.event;

import java.util.UUID;

public class BottomRowDeletedEvent extends GameEvent {

    private int row;

    public BottomRowDeletedEvent(UUID gameId, int row) {
        super(gameId);
        this.row = row;
    }

    public int getRow() {
        return row;
    }
}
