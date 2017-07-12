

public class SB_IntMatrix extends SudokuBoard
{
  int [][] m;
  public SB_IntMatrix()
  {
  }
  
  public int get(int r, int c)
  {
    return m[r-1][c-1]
  }
  
  public void set(int r, int c, int v)
  {
    m[r-1][c-1] = v;
  }  
  
  boolean isRowCompatible(int r, int c)
  {
  }
  
  boolean isColCompatible(int r, int c) 
  {
  }

  boolean isBoxCompatible(int r, int c) 
  { 
  }
}