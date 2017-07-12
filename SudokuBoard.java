/*************
  * This is the parent class of Sudoku
  * 
  * notes:
  *   Indices are base-1
  *   Empty cells are filled with 0 values
  */
public abstract class SudokuBoard
{
  protected int ROWS = 9;
  protected int COLS = 9;
  
  // data accessors
  public abstract int get(int r, int c);
  public abstract void set(int r, int c, int v);
  
  // specific constraints checker, returns true even if the values are not complete
  abstract boolean isRowCompatible(int r, int c);
  abstract boolean isColCompatible(int r, int c);
  abstract boolean isBoxCompatible(int r, int c);
  
  // returns true if element S[r,c] is compatible, even if some values arount it are not filled
  public boolean isCompatible(int r, int c)
  {
    for (int i=0; i<ROWS; i++)
      for (int j=0; j<COLS; j++)
        if(! (isRowCompatible(r, c) && isColCompatible(r, c) && isBoxCompatible(r, c)))
          return false;
    return true;
  }

  // this is the one called to solve the sudoku
  public void solve()
  {
    solve(1,1);
  }
  
  // function to incorporate clues
  public void incorporateClues(int[] clues)
  {
    for (int i=0; i<clues.length; i++)
      set(clues[i]/100, (clues[i]%100)/10, clues[i]%10); 
  }
  
  
  // the recursive backtracking function that does the hardwork
  void solve(int r, int c)
  {
    if (r<=9 && c<=9)
    {
      if (get(r,c) == 0)
      {
        for (int v=1; v<=COLS; v++)
        {
          set(r,c,v);
          if (isCompatible(r,c))
            solve((c==9)?(r+1):r, (c==9)?1:(c+1));
        }
        set(r, c, 0);
      }
      else
        solve((c==9)?(r+1):r, (c==9)?1:(c+1));
    }
    else print();
  }
  
  // sample display function
  void print()
  {
    for(int i=1; i<=ROWS; i++)
    {
      for (int j=1; j<=COLS; j++)
        System.out.print(get(i,j));
       System.out.println();
    }               
  }
 
  
  public static void main(String []arg)
  {
    SudokuBoard board = new SB_IntMatrix();
    board.incorporateClues(PUZZLE1);
    board.set(5, 5, 4);
    System.out.println("Row Compatibility: " + board.isRowCompatible(5, 5));
    // System.out.println("Column Compatibility: " + board.isColCompatible(5, 5));
    // System.out.println("Box Compatibility: " + board.isBoxCompatible(5, 5));
    // System.out.println("Compatibility: " + board.isCompatible(5, 5));
    // board.solve();
  }
  
  public static int[] PUZZLE1 = {115, 123,157, 216, 241, 259, 265, 329, 338, 386, 418, 456, 493, 514, 548, 563, 591, 617, 652, 696, 726, 772, 788, 844, 851, 869, 895, 958, 987, 999};
  
}