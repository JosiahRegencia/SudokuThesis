import java.util.BitSet;
import java.util.Stack;
import java.util.Iterator;


class BitXCMatrix extends XCMatrix
{
  BitSet[] data;
  
  BitXCMatrix(int r, int c)
  {
    super(r,c);
    data = new BitSet[r];
    for (int i=0; i<r; i++)
      data[i] = new BitSet(c);
  }
  
  boolean isSet(int r, int c)
  {
    return data[r].get(c);
  }
  
  int nextColAfterOnRow(int start, int row)
  {
    /*
    for(int c=start; c<COLS; c++)
      if(data[row].get(c))
        return c;
    return -1;
    */
    return data[row].nextSetBit(start);
  }
  
  int nextRowAfterOnCol(int start, int col)
  {
    for(int r=start; r<ROWS; r++)
      if(data[r].get(col))
        return r;
    return -1;
  }
  
  void setOnRowBitStrings(int r, String bits)
  {
    data[r] = new BitSet(bits.length());
    for(int i=0; i<bits.length(); i++)
      if (bits.charAt(i)=='1')
         data[r].set(i);
  }
  
//======delete this==========================================
  
  boolean confirm(Stack rows)
  {
    BitSet ith;
    BitSet ANDs = new BitSet(COLS);
    BitSet ORs = new BitSet(COLS);
    ANDs.set(0,COLS, true);
    
    for(Iterator iter = rows.iterator(); iter.hasNext();)
    {
      ith = data[Integer.parseInt(iter.next().toString())];
      ANDs.and(ith);
      ORs.or(ith);
    }
    return ANDs.cardinality() == 0 && ORs.cardinality() == COLS;
  }
  
  BitXCMatrix(String[] bs)
  {
    this(bs.length, bs[0].length());
    
    data= new BitSet[ROWS];
    for (int i=0; i<ROWS; i++)
      setOnRowBitStrings(i,bs[i]);  
  }
  
  
  BitXCMatrix(int[] partialVals)
  {
    this(729,324);
    
    int row;
    for (int r=0; r<9; r++)
    {
      for (int c=0; c<9; c++)
      {
        for (int d=0; d<9; d++)
        {
          row = r*81 + c*9 + d;
          data[row].set(81*0 + r*9 + c);
          data[row].set(81*1 + r*9 + d);
          data[row].set(81*2 + c*9 + d);
          data[row].set(81*3 + XMatrix.boxNum(r,c)*9 + d);
        }
      }
    }
    int r, c, v;
    for (int rcv : partialVals)
    {
      r = rcv/100;
      c = (rcv/10)%10;
      v = (rcv%10)-1;
      
      row = r*81+c*9;
      for(int vv=0; vv<9; vv++)
        if (vv!=v)
           data[row+vv].clear(r*9+c);
    }
  }
  /*
  
  public static void main(String[] args)
  {
    SimpleXCMatrix M = makeSudokuMat(Sudoku.sbHARD_01);

    long start = System.currentTimeMillis();
    M.search(0);
    long end = System.currentTimeMillis();
    System.out.println("computing time (millis): " +(end-start));
  
  }

*/
}