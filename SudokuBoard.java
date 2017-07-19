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
  String[] data = new String[8];
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
  } 

  void saveData (String[] data) throws java.io.IOException
  {

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
  }

  static int countTotalRows () {
    int count = 0;
      try 
      {
        java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader("16-clue_results.csv"));
        String input;

        while((input = bufferedReader.readLine()) != null)
          {
            count = count + 1;
          }

      } catch (java.io.IOException e) {
          e.printStackTrace();
      }

      return count;
  }
  
  public static void main(String []arg)
  {
    int numClues;

     try {
        java.io.BufferedReader csvFile = new java.io.BufferedReader(new java.io.FileReader("clue_set"));
        String dataRow;
        while ((dataRow = csvFile.readLine()) !=  null) {
          SudokuBoard board = new SB_IntMatrix();
          String[] stringSet = new String[16];
          int[] PUZZLE1 = new int[16];
          board.puzzleNum = board.puzzleNum + 1;
          stringSet = dataRow.split(" ");

          for (int i = 0; i < stringSet.length; i++) {
            PUZZLE1[i] = Integer.parseInt(stringSet[i]);
          }

          board.incorporateClues(PUZZLE1);
          
          for (int i = 0; i < 5; i++) {
            board.solutionsCounter = 0;
            board.solve();
            board.data[0] = Integer.toString(board.puzzleNum);
            board.data[1] = dataRow;
            board.data[2] = Integer.toString(board.solutionsCounter);
            board.data[3   + i] = Double.toString(board.endTime - board.startTime);
          }

          try 
          {
            board.saveData(board.data);
          } catch (java.io.IOException e) {
            e.printStackTrace();
          }
        }   
        csvFile.close();

      } catch (java.io.IOException e) {
          e.printStackTrace();
      }
  }
  
}