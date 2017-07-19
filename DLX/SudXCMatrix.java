class SudXCMatrix extends XCMatrix
{
  java.util.BitSet excepts;
  SudXCMatrix(int[] except)
  {
    super(729, 324);
    excepts = new java.util.BitSet(729);
    for(int i:except)
      excepts.set((i/100)*81 + ((i/10)%10)*9 + (i%10)-1);
  }
  
  boolean isSet(int i, int j)
  {
    if (getCols(i,j/81) == j)
    {
      if (excepts.get(i) && j<81)
        return false;
      return true;
    }
    else
      return false;
  }
  
  int nextColAfterOnRow(int start, int row)
  {
    if (excepts.get(row) && start<81)
      return nextColAfterOnRow(81, row);
    
    
    int col = getCols(row, start/81);

    if (col < 0)
      return -1;
    if ((col < start) || (!isSet(row, col)))
      return nextColAfterOnRow(start+81,row); 
    return col;
  }
  
  int nextRowAfterOnCol(int start, int col)
  {
    for (int i=start; i<ROWS; i++)
      if (isSet(i,col))
         return i;
    return -1;
  }
  
  /// ==================================
/*  int[] getCols(int row)
  {
    int r = i/81;
    int c = (i-r*81)/9;
    int d = i-r*81-c*9;
    return new int[] {r*9+c, 81+r*9+d,81*2+c*9+d,81*3+XMatrix.Box(r,c)*9+d};
  }
  */
  int getCols(int row, int index)
  {
    int r = row/81;
    int c = (row-r*81)/9;
    int d = row-r*81-c*9;
    switch(index)
    {
      case 0: return r*9+c;
      case 1: return 81+r*9+d;
      case 2: return 81*2+c*9+d;
      case 3: return 81*3+XMatrix.boxNum(r,c)*9+d;
    }
    return -1;
  }
}