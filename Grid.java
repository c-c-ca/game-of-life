import java.util.Random;

public class Grid {

  private Cell[][] cells;

  public Grid(int numRows, int numCols) {
    this.cells = new Cell[numRows][];
    this.initGrid(numCols);
  }

  private Grid(Cell[][] cells) {
    this.cells = cells;
  }

  private void initGrid(int numCols) {
    Random rand = new Random();

    for (int row = 0; row < this.cells.length; row++) {
      this.cells[row] = new Cell[numCols];
      for (int col = 0; col < this.cells[row].length; col++) {
        this.cells[row][col] = new Cell(rand.nextBoolean());
      }
    }
  }

  public Grid nextGrid() {
    Cell[][] newCells = new Cell[this.cells.length][];

    for (int row = 0; row < newCells.length; row++) {
      newCells[row] = new Cell[this.cells[0].length];
      for (int col = 0; col < newCells[row].length; col++) {
        newCells[row][col] = this.nextCell(row, col);
      }
    }

    return new Grid(newCells);
  }

  private Cell nextCell(int row, int col) {
    int numAliveNeighbors = checkNeighbors(row, col);

    return (this.cells[row][col].isAlive())
      ? new Cell(2 <= numAliveNeighbors && numAliveNeighbors <= 3)
      : new Cell(numAliveNeighbors == 3);
  }

  private int checkNeighbors(int row, int col) {
    final int[][] deltaXandY = {
      { -1, -1 },
      { 0, -1 },
      { 1, -1 },
      { -1, 0 },
      { 1, 0 },
      { -1, 1 },
      { 0, 1 },
      { 1, 1 },
    };

    int numAliveNeighbors = 0;

    for (int i = 0; i < deltaXandY.length; i++) {
      int neighborCol = col + deltaXandY[i][0];
      int neighborRow = row + deltaXandY[i][1];

      if (
        inBounds(neighborRow, neighborCol) &&
        this.cells[neighborRow][neighborCol].isAlive()
      ) {
        numAliveNeighbors++;
      }
    }

    return numAliveNeighbors;
  }

  private boolean inBounds(int row, int col) {
    return (
      0 <= row &&
      row < this.cells.length &&
      0 <= col &&
      col < this.cells[0].length
    );
  }

  public boolean equals(Object other) {
    if (other instanceof Grid) {
      Grid otherGrid = (Grid) other;

      if (
        this.cells.length != otherGrid.cells.length ||
        this.cells[0].length != otherGrid.cells[0].length
      ) return false;

      for (int row = 0; row < this.cells.length; row++) {
        for (int col = 0; col < this.cells[row].length; col++) {
          if (
            this.cells[row][col].isAlive() !=
            otherGrid.cells[row][col].isAlive()
          ) {
            return false;
          }
        }
      }
      return true;
    }

    return false;
  }

  private String generateBorder() {
    String result = "+";

    for (int i = 0; i < this.cells[0].length; i++) {
      result += "-+";
    }

    result += "\n";

    return result;
  }

  public String toString() {
    String result = generateBorder();

    for (int row = 0; row < this.cells.length; row++) {
      result += "|";
      for (int col = 0; col < this.cells[row].length; col++) {
        result += (this.cells[row][col].isAlive() ? "*" : " ") + "|";
      }
      result += "\n";
      result += generateBorder();
    }

    return result;
  }
}
