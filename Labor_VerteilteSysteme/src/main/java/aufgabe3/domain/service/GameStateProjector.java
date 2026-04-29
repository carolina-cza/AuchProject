package aufgabe3.domain.service;

import aufgabe3.domain.event.BottomRowDeletedEvent;
import aufgabe3.domain.event.GameEvent;
import aufgabe3.domain.event.GameStartedEvent;
import aufgabe3.domain.event.GameWonEvent;
import aufgabe3.domain.event.MovePlacedEvent;
import aufgabe3.domain.model.Board;
import aufgabe3.domain.model.GameState;
import aufgabe3.domain.model.GameStatus;
import aufgabe3.domain.model.Player;

public class GameStateProjector {

    public void apply(GameState state, GameEvent event) {

        if (event instanceof GameStartedEvent) {
            GameStartedEvent e = (GameStartedEvent) event;

            state.setGameId(e.getGameId());
            state.setRows(e.getRows());
            state.setCols(e.getCols());
            state.setBoard(new Board(e.getRows(), e.getCols()));
            state.setStatus(GameStatus.RUNNING);

            // optional initial value (not critical)
            state.setCurrentPlayer(null);

        } else if (event instanceof MovePlacedEvent) {
            MovePlacedEvent e = (MovePlacedEvent) event;

            Board board = state.getBoard();

            // convert 1-based column index (server) to 0-based
            int col = e.getColumn() - 1;

            if (col < 0 || col >= state.getCols()) {
                return;
            }

            // deterministic placement
            for (int row = state.getRows() - 1; row >= 0; row--) {
                if (board.getCell(row, col) == null) {
                    board.setCell(row, col, e.getPlayer());
                    break;
                }
            }

            // FIX: do NOT switch player → event is source of truth
            state.setCurrentPlayer(e.getPlayer());

        } else if (event instanceof BottomRowDeletedEvent) {
            BottomRowDeletedEvent e = (BottomRowDeletedEvent) event;

            Board board = state.getBoard();
            int rows = state.getRows();
            int cols = state.getCols();

            // shift rows down
            for (int row = rows - 1; row > 0; row--) {
                for (int col = 0; col < cols; col++) {
                    board.setCell(row, col, board.getCell(row - 1, col));
                }
            }

            // clear top row
            for (int col = 0; col < cols; col++) {
                board.setCell(0, col, null);
            }

        } else if (event instanceof GameWonEvent) {
            GameWonEvent e = (GameWonEvent) event;

            state.setStatus(GameStatus.WON);
            state.setWinner(e.getPlayer());
        }
    }
}