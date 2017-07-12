
  
public class SB_IntMatrix extends SudokuBoard
{
  int [][] matrix = new int[9][9];
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

    for (int i = 1; i < c - 1; i++) {
      // System.out.println(get(r, i));
      for (int j = c; j <= 9; j++) {
        if (value == get(r, i)) {
          return false;
        }

      }
    }
    return true;
  }
  
  boolean isColCompatible(int r, int c) 
  {
    int value = get(r, c);

    for (int i = 1; i <= 9; i++) {
      System.out.println(get(i, c));
      if (value == get(i, c)) {
        return false;
      }
    }
    return true;
  }

  boolean isBoxCompatible(int r, int c) 
  { 
    int value = get(r, c);


    for (int i = r - 1; i < r + 2; i++) {
      for (int j = c - 1; j < c + 2; j++) {
        if (value == get(i, j)) {
          return false;
        }
      }
    }
    return true;
  }
}