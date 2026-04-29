package aufgabe3.domain.model;

import java.util.UUID;

public class GameState {

    private UUID gameId;
    private Board board;
    private Player currentPlayer;
    private GameStatus status;
    private int rows;
    private int cols;
    private Player winner;

    public GameState(UUID gameId, int rows, int cols) {
        this.gameId = gameId;
        this.rows = rows;
        this.cols = cols;
        this.board = new Board(rows, cols);
        this.status = GameStatus.RUNNING;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
