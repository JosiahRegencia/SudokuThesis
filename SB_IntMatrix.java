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

    for (int i = 1; i <= 9; i++) {
      if ((i != c) && (value == get(r, i))) { 
        return false;
      }
    }
    return true;
  }
  
  boolean isColCompatible(int r, int c) 
  {
    int value = get(r, c);

    for (int i = 1; i <= 9; i++) {
      if ((i != r) && (value == get(i, c))) {
        return false;
      }
    }
    return true;
  }

  boolean isBoxCompatible(int r, int c) 
  { 
   int value = get(r, c);
   int startRow = getBase(r);
   int startCol = getBase(c);
   int lastRow = getBase(r) + 2;
   int lastCol = getBase(c) + 2;

   for (int i = startRow; i <= lastRow; i++) {
    for (int j = startCol; j <= lastCol; j++) {
      if (!((i == r) && (j == c)) && value == get(i, j)) {
        return false;
      }
    }
   }
   return true;
  }

  int getBase(int i)
  {
    if ((i >= 1) && (i <= 3)) {
      return 1;
    }
    else if ((i >= 4) && (i <= 6)) {
      return 4;
    }
    else if ((i >= 7) && (i <= 9)) {
      return 7;
    }

    return 0;
  }
}