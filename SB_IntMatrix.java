
  
public class SB_IntMatrix extends SudokuBoard
{
  int[][] matrix = new int[9][9];

  public SB_IntMatrix()
  {
  }
  
  public int get(int r, int c)
  {
    return matrix[r-1][c-1];
  }
  
  public void set(int r, int c, int v)
  {
    matrix[r-1][c-1] = v;
  }  
  
  boolean isRowCompatible(int r, int c)
  {
    int value = get(r, c);
    int counter = 0;

    for (int i = 1; i <= 9; i++) {
      if (value == get(r, i)) {
        counter = counter + 1;
      }
    }

    System.out.println("row counter: " + counter);

    if (counter > 1) {
      return false;
    }

    return true;
  }
  
  boolean isColCompatible(int r, int c) 
  {
    int value = get(r, c);
    int counter = 0;

    for (int i = 1; i <= 9; i++) {
      if (value == get(i, c)) {
        counter = counter + 1;
      }
    }

    System.out.println("column counter: " + counter);

    if (counter > 1) {
      return false;
    }

    return true;
  }

  boolean isBoxCompatible(int r, int c) 
  { 
    int value = get(r, c);
    int counter = 0;

    for (int i = r - 1; i < r + 2; i++) {
      for (int j = c - 1; j < c + 2; j++) {
        if (value == get(i, j)) {
          counter = counter + 1;
        }
      }
    }

    System.out.println("box counter: " + counter);

    if (counter > 1) {
      return false;
    }

    return true;
  }
}