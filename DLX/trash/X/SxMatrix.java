import java.util.BitSet;
import java.util.Stack;
import java.util.Iterator;

class SxMatrix
{
  // 729 rows!
  
  SxRow[] rows;
  BitSet deletedRows = new BitSet(729);
  BitSet deletedCols = new BitSet(324);
  Stack soln = new Stack();
  
  boolean SxIsEmpty()
  {
    return (deletedCols.cardinality() == 324) || (deletedRows.cardinality() == 729);
  }
  
  short SxChooseCol()
  {
    return (short)deletedCols.nextClearBit(0);
  }
  
  short SxChooseRow(short c)
  {
    int i = deletedRows.nextClearBit(0);
    while (rows[i].isClear(c))
      i = deletedRows.nextClearBit(i+1);
    return (short)i;
  }
  
  void SxDeleteAllRowsInCol(int col)
  {
    short c = (short) col;
    for (int i = deletedRows.nextClearBit(0); i< 729; i = deletedRows.nextClearBit(i+1))
    {
      if (rows[i].isSet(c))
        deletedRows.set(i);
    }
  }
  void SxRestoreAllRowsInCol(int col)
  {
    short c = (short)col;
    for (int i = deletedRows.nextSetBit(0); i< 729; i = deletedRows.nextSetBit(i+1))
    {
      if (rows[i].isSet(c))
        deletedRows.clear(i);
    }
  }
  
  void coverColumn(int row)
  {   
      int j;
      Iterator<Short> colIter = rows[row].getIterator();
      while (colIter.hasNext())
      {
        j = colIter.next();
        deletedCols.set(j);   
        SxDeleteAllRowsInCol(j);
      }
  }
  
  void uncoverColumn(int row)
  {
    int j;
    Iterator<Short> colIter = rows[row].getIterator();
    while (colIter.hasNext())
    {
      j = colIter.next();
      deletedCols.clear(j);      
      SxDeleteAllRowsInCol(j);
    }    
  }
  
  void backtrack(int depth)
  {
    if (SxIsEmpty())
      //terminate
    {}
    else
    {
      short col = SxChooseCol();
      short row = SxChooseRow(col);
      
      soln.push(row);
      
      coverColumn(row);
      
      backtrack(depth + 1);
      
      uncoverColumn(row);
    
    //row = soln.pop();   
    }
  }
}