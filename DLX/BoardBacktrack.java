/*
 * Uses backtracking as described in Wikipedia 
 * The numbers are enumerated recursively, even though the board is partially populated
 */

class BoardBacktrack
{
  int N = 9; // for debugging and tracing
  int rec = 0, recur = 0, solns = 0, depth = 0, maxdepth=0;
  short b[][];
  
  /*
  void readInput()
  {
  READ FROM FILE
  
  
  read (" file")
  
  b = new short[9][9];
  b[0][0]..
  }
  */
  
  void readInput()
  {
  /*
     b =    new short[][] {
    { 16,   4,   8,  32,  64, 128, 256,   1,   2},
    { 32,  64,   2,   1, 256,  16,   4,   8, 128},
    {  1, 256, 128,   4,   8,   2,  16,  32,  64},
    {128,  16, 256,  64,  32,   1,   8,   2,   4},
    {  8,   2,  32, 128,  16,   4,  64, 256,   1},
    { 64,   1,   4, 256,   2,   8, 128,  16,  32},
    {256,  32,   1,  16,   4,  64,   2, 128,   8},
    {  2, 128,  64,   8,   1, 256,  32,   4,  16},
    {  4,   8,  16,   2, 128,  32,   1,  64, 256}
    };

    b =     {
    { 16,   0,   0,  32,  64, 128, 0,   1,   2},
    { 32,  0,   2,   1, 0,  16,   4,   8, 0},
    {  1, 256, 128,   4,   8,   2,  16,  32,  64},
    {128,  16, 256,  64,  32,   1,   8,   2,   4},
    {  8,   2,  32, 128,  16,   4,  64, 256,   1},
    { 64,   1,   4, 256,   2,   8, 128,  16,  32},
    {256,  0,   1,  16,   4,  64,   2, 128,   8},
    {  2, 0,  0,   8,   1, 256,  32,   4,  16},
    {  4,   8,  0,   2, 128,  32,   1,  64, 256}
    };
*/

  
  //from google
    b =   new short[][]  {
    { 16,   4,   0,   0,  64,   0,   0,   0,   0},
    { 32,   0,   0,   1, 256,  16,   0,   0,   0},
    {  0, 256, 128,   0,   0,   0,   0,  32,   0},
    {128,   0,   0,   0,  32,   0,   0,   0,   4},
    {  8,   0,   0, 128,   0,   4,   0,   0,   1},
    { 64,   0,   0,   0,   0,   0,   0,   0,  0},
    {  0,  32,   0,   0,   0,   0,   2, 128,   0},
    {  0,   0,   0,   8,   1, 256,   0,   0,  0},
    {  0,   0,   0,   0, 128,   0,   0,  64, 256}
    };
    
/*
    b =     {
    { 0,   4,   0,   0,  64,   0,   0,   0,   0},
    { 0,   0,   0,   1,  0,  16,   0,   0,   0},
    {  0, 0,  0,   0,   0,   0,   0,  32,   0},
    { 0,   0,   0,   0,  32,   0,   0,   0,   4},
    {  8,   0,   0,   0,   0,   4,   0,   0,   1},
    { 0,   0,   0,   0,   0,   0,   0,   0,  0},
    {  0,  32,   0,   0,   0,   0,   2, 128,   0},
    {  0,   0,   0,   8,   1, 256,   0,   0,  0},
    {  0,   0,   0,   0, 128,   0,   0,  64, 256}
    };
    
  */
  }
  
  boolean isOk(int i, int j)
  {
    return isOkRow(i,j) && isOkCol(i,j) && isOkBox(i,j);
  }
                 
  boolean isOk()
  {
    for (int i=0; i<9; i++)
      if (!(isOkRow(i,0) && isOkCol(0,i)&&isOkBox((i/3)*3,(i%3)*3)))
          return false;
    return true;  
  }
  
  boolean isOkRow(int i, int j)
  {
    short result = 0;
    for (int k=0; k<9;k++)
      if ((b[i][k]>0) && (result == (result |= b[i][k])))
        return false;
    return true;
  }
  boolean isOkCol(int i, int j)
  {
    short result = 0;
    for (int k=0; k<9;k++)
      if ((b[k][j]>0) && (result == (result |= b[k][j])))
        return false;
    return true;
  }
  boolean isOkBox(int i, int j)
  {
    short result = 0;
    for (int x=(i/3)*3; x<((i/3)+1)*3; x++)
      for (int y=(j/3)*3; y<((j/3)+1)*3; y++)
        if ((b[x][y]>0) && (result == (result |= b[x][y])))
          return false;
    
    return true;         
  }


  void backtrack(int i, int j)
  {
    rec++;
    
// is end of board
    if(i>=N)
    {
      if(isOk())
      {
        solns++;
      }
    }
// is empty cell      
    else if (b[i][j] == 0)
    {
      
      if(++depth>maxdepth)
         maxdepth = depth;
  
      for(short d = 1; d<257; d=(short)(d<<1))
      {
        recur++;
        b[i][j] = d;
        if (isOk(i,j))
            backtrack((j==8)?(i+1):i, (j+1)%9);
      }
      // restore (no solution found here)
      b[i][j] = 0;
      
      depth--;
    }
// failed
    else
      backtrack((j==8)?(i+1):i, (j+1)%9);
  }
  
  void display()
  {
    for (int i=0; i<9; i++)
    {
      System.out.println();
      for (int j=0; j<9; j++)
        System.out.print("   "+b[i][j]);
    }
    System.out.println();
  }
  
  public static void main(String[] arg)
  {
    BoardBacktrack b = new BoardBacktrack();
    b.readInput();
    long start = System.currentTimeMillis();
    b.backtrack(0,0);
    long end = System.currentTimeMillis();
    System.out.println(b.rec+" and total of "+b.recur+". Num solutions found:" +b.solns+". max depth: "+b.maxdepth+" time "+(end-start));;
//    b.display();
  }
}
