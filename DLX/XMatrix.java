abstract class XMatrix
{
  int solutions = 0;
  abstract void search(int depth, boolean chooseMin);
  void search(boolean chooseMin)
  {
    search(0, chooseMin);
  }
  void search()
  {
    search(0,false);
  }
  void onSolution()
  {
    solutions = solutions + 1;
    System.out.println("solutions found: " + solutions);
  };
  
  static int boxNum(int r, int c)
  {
    return (r/3)*3 + (c/3);
  }
  
  static void runAndDisplayStat(String name, XMatrix M)
  {
    double start = System.currentTimeMillis();
    M.search(0,true);
    double end = System.currentTimeMillis();
    System.out.println("computing time (millis) for "+name+": " +(end-start)/1000);
  }
  
  
  public static void main(String[] args)
  {
    int[] input = Sudoku.sbHARD_01;

    // for (int i = 0; i < input.length; i++) {
    //   System.out.print(input[i] + " ");
    // }
    
    // runAndDisplayStat("simple",new BitXCMatrix(input));
    runAndDisplayStat("DLX", DLXMatrix.makeSudoku(input));

    for (int i = 0; i < input.length; i++) {
      System.out.print(input[i] + " ");
    }
  }
}