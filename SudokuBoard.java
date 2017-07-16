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
  int solutionsCounter;
  double startTime;
  double endTime;
  double[] data = new double[3];
  int puzzleNum = countTotalRows();
  
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
    //convert to seconds
    startTime = System.nanoTime() / 1000000000.0;
    // System.out.println("Star Time: " + startTime);
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
    else 
    {
      solutionsCounter = solutionsCounter + 1;

      //convert to seconds
      endTime = System.nanoTime() / 1000000000.0;
      // print();
      // System.out.println("End Time: " + endTime);
      // System.out.println("Elapsed Time: " + (endTime - startTime));
      // System.out.println();
    }
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

    System.out.println("count: " + solutionsCounter);
    // System.out.println();    
  } 

  void saveData (double[] data) throws java.io.IOException
  {
    // java.io.File dataCSV = new java.io.File("16-clue_results.csv");

    // java.io.PrintWriter outfile = new java.io.PrintWriter(dataCSV);

    try 
    {
      java.io.BufferedWriter outfile = new java.io.BufferedWriter(new java.io.FileWriter("16-clue_results.csv", true));

      for (int i = 0; i < data.length; i++) {
      outfile.write(String.valueOf(data[i]));
      outfile.append(',');
      }

      outfile.append('\n');
      outfile.close();

    } catch (java.io.IOException e) {
      e.printStackTrace();
    }

    // outfile.append('\n');
  }

  static int countTotalRows () {
    int count = 1;
      try 
      {
        java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader("16-clue_results.csv"));
        String input;

        while((input = bufferedReader.readLine()) != null)
          {
            count++;
          }

      } catch (java.io.IOException e) {
          e.printStackTrace();
      }

      return count;
  }
  
  public static void main(String []arg)
  {
    SudokuBoard board = new SB_IntMatrix();
    board.incorporateClues(PUZZLE1);
    board.solutionsCounter = 0;
    board.solve();
    board.data[0] = board.puzzleNum;
    board.data[1] = board.solutionsCounter;
    board.data[2] = board.endTime - board.startTime;
    System.out.println("Number of Solution: " + board.data[1]);
    System.out.println("Elapsed Time: " + (board.data[2]));

    try 
    {
      board.saveData(board.data);
    } catch (java.io.IOException e) {
      e.printStackTrace();
    }
  }
  
  // public static int[] PUZZLE1 = {181, 214, 322, 455, 497, 538, 573, 631, 659, 713, 744, 772, 825, 841, 948, 966};
  public static int[] PUZZLE1 = {115, 123,157, 216, 241, 259, 265, 329, 338, 386, 418, 456, 493, 514, 548, 563, 591, 617, 652, 696, 726, 772, 788, 844, 851, 869, 895, 958, 987, 999};
  // public static int[] PUZZLE1 = {119, 136, 157, 174, 193, 244, 272, 327, 352, 363, 381, 415, 471, 524, 542, 568, 586, 633, 695, 723, 747, 785, 837, 865, 914, 935, 951, 977, 998};

  
}