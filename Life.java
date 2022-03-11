import java.util.Scanner;

public class Life {

  public static void playGame(int numRows, int numCols) {
    Scanner in = new Scanner(System.in);

    Grid prev = null;
    Grid curr = new Grid(numRows, numCols);

    while (!curr.equals(prev)) {
      System.out.println(curr);

      in.nextLine();

      prev = curr;
      curr = prev.nextGrid();
    }
  }

  public static void main(String[] args) {
    playGame(5, 5);
  }
}
