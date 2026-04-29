package aufgabe3.domain.model;

public class Board {

    private Player[][] grid;

    public Board(int rows, int cols) {
        this.grid = new Player[rows][cols];
    }

    public Player[][] getGrid() {
        return grid;
    }

    public void setCell(int row, int col, Player player) {
        grid[row][col] = player;
    }

    public Player getCell(int row, int col) {
        return grid[row][col];
    }
}
