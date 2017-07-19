import java.util.BitSet;
import java.util.Stack;


public abstract class XCMatrix extends XMatrix
{
  int ROWS, COLS;
  BitSet presentRows, presentCols;
  Stack soln = new Stack();
  
  XCMatrix(int numRows, int numCols)
  {
    ROWS = numRows;
    COLS = numCols;
    
    presentRows = new BitSet(ROWS);
    presentCols = new BitSet(COLS);
    
    presentRows.set(0, ROWS);
    presentCols.set(0, COLS);
  }
  
  boolean isEmpty()
  {
    return presentCols.cardinality() == 0;
  }
  
  int chooseMinCol(boolean chooseMin)
  {
    if (!chooseMin)
      return presentCols.nextSetBit(0);
    
    int count;
    int min = 800000;
    for (int c=presentCols.nextSetBit(0); c<COLS && c>=0; c=presentCols.nextSetBit(c+1))
    {
      count = 0;
      for (int i=presentRows.nextSetBit(0); i<ROWS && i>=0; i=presentRows.nextSetBit(i+1))
        if (isSet(i,c))
           count++;
      if (count < (min/1000))
        min = count*1000+c;
    }
    return min%1000;
  }
   
  abstract boolean isSet(int r, int c);
  abstract int nextColAfterOnRow(int start, int row);
  abstract int nextRowAfterOnCol(int start, int col);
    
  void search(int depth, boolean chooseMin)
  {
    if(isEmpty())
      onSolution();
    else
    {
      Stack rr;
      int c = chooseMinCol(chooseMin);

      for (int r = presentRows.nextSetBit(0); r<ROWS && r>=0; r = presentRows.nextSetBit(r+1))
      {
        if (isSet(r, c))
        {
          soln.push(r);
          rr = cover(r);
          search(depth+1, chooseMin);
          uncover(r, rr);
          soln.pop();
        }
      }
    }
  }

  Stack cover(int r)
  {
    Stack deleted = new Stack();
    for (int j = nextColAfterOnRow(0, r); j<COLS && j>=0; j=nextColAfterOnRow(j+1, r))
    {
      if (presentCols.get(j))
      {
        presentCols.clear(j);
        deleted.push(-1*(j+1));
        for(int i = nextRowAfterOnCol(0, j); i<ROWS && i>=0; i=nextRowAfterOnCol(i+1, j))
          if (presentRows.get(i))
        {
             presentRows.clear(i);
             deleted.push(i+1);
        }
      }
    }
    return deleted;
  }
  
  void uncover(int r, Stack deleted)
  {
    int x;
    
    while (!deleted.isEmpty())
    {
      x = Integer.valueOf(deleted.pop().toString());
      if (x>0)
        presentRows.set(x-1);
      else
        presentCols.set(-1*(x+1));
    }
  }
}
  