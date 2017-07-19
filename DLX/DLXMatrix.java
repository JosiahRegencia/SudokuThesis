class DLXMatrix extends XMatrix
{
  Column HEADER;
  java.util.Stack<Node> soln;
  Column[] COLUMNS;
  
  boolean isEmpty()
  {
    return HEADER.R == HEADER;
  } 
  Column chooseMinCol(boolean chooseMin)
  {
    if (chooseMin)
    {
      Column min = (Column)HEADER.R;
      for(Column p=min; p!=HEADER; p=(Column)p.R)
        if (p.size<min.size)
          min = p;
      return min;
    }
    else
      return (Column)HEADER.R;
  }
  
  void cover(Column c)
  {
    c.unlink(null, false);
    for (Node i = ((Node)c).D; i!= c; i=i.D)
      for (Node j = i.R; j!=i; j=j.R)
        j.unlink(c,true);
  }
  void uncover(Column c)
  {
    for (Node i = ((Node)c).U; i!=c; i=i.U)
      for (Node j = i.L; j!=i; j=j.L)
        j.relink(c,true);
    c.relink(null,false);
  }
  
  void search(int depth, boolean chooseMin)
  {
    if (isEmpty())
      onSolution();
    else
    {
      Column c = chooseMinCol(chooseMin);
      cover(c);
      for (Node r=((Node)c).D; r!=c; r=r.D)
      {
        soln.push(r);
        for (Node j=r.R; j!= r; j=j.R)
          cover(COLUMNS[j.getColNum()]);
        
        search(depth + 1, chooseMin);
        
        for (Node j=r.L; j!=r; j=j.L)  
          uncover(COLUMNS[j.getColNum()]);
        soln.pop();
      }
      uncover(c);
    }
  }
  
  //==========================================
  
    ///================= SUDOKU
  
  public static DLXMatrix makeSudoku(int[] except)
  {
    DLXMatrix M = new DLXMatrix(324);
    
    boolean isExceptRow = false;
    int next = 0;
    int xc_row, su_rc, su_rd, su_cd, su_bd;
    
    for (int i=0; i<9; i++)
    {
      for (int j=0; j<9; j++)
      {        
        isExceptRow = (next<except.length) && (except[next]/10 == (i*10+j));
        
        for (int d=0; d<9; d++)
        {
          xc_row = i*81 + j*9 + d;
          
            su_rc = 81*0 + i*9 + j;
            su_rd = 81*1 + i*9 + d;
            su_cd = 81*2 + j*9 + d;
            su_bd = 81*3 + XMatrix.boxNum(i,j)*9 + d;
            
          if(isExceptRow && (except[next]%10 != (d+1)))
          {
             // 3 columns to increase num of constraints
            M.createAppendAndAttachTo(M.createAppendAndAttachTo(M.COLUMNS[su_rd].append(new Node(xc_row, su_rd)), xc_row, su_cd), xc_row, su_bd);
          }
          else
          {
            M.createAppendAndAttachTo(M.createAppendAndAttachTo(M.createAppendAndAttachTo(M.COLUMNS[su_rc].append(new Node(xc_row, su_rc)), xc_row, su_rd), xc_row, su_cd), xc_row, su_bd);   
          }
        }
        if (isExceptRow) next++;
      }
    }
    return M;
  }
  
  
  DLXMatrix(int numCols)
  {
    HEADER = new Column("HEADER");
    COLUMNS = new Column[numCols];
    soln = new java.util.Stack<Node>();
    
    Column c = HEADER;
    for (int i=0; i<numCols; i++)
    {
      COLUMNS[i] = new Column(Integer.toString(i));
      c.R = COLUMNS[i];
      c.R.L = c;
      c=(Column)c.R;
    }
    //complete the loop
    c.R = HEADER;
    c.R.L=c;
  }
  
  DLXMatrix(String[] bitStrings)
  {
    this(bitStrings[0].length());
    
    for (int i=0; i<bitStrings.length; i++)
    {
      int j = bitStrings[i].indexOf('1',0);
    
      //create a row
      Node n  = new Node(i,j);
      COLUMNS[j].append(n);
 
      for (j=bitStrings[i].indexOf('1',j+1); j>=0; j=bitStrings[i].indexOf('1',j+1))
        n = createAppendAndAttachTo(n, i, j);
    }
  }
  
  Node createAppendAndAttachTo(Node prev, int r, int c)
  {
    Node n = new Node(r,c);
    COLUMNS[c].append(n);
    n.attachTo(prev);
    return n;
  }
  
  
  //====== DEBUGGER ==============
  void show(int depth, String msg)
  {
    System.out.print("d"+depth);
    for (int i=0; i<depth; i++)
      System.out.print(" ");
    System.out.println("> " +msg);
  }
}

class Column extends Node
{
  int size;
  String name;
  
  Column(String name) { this.name = name; size=0;}
  
  Node append(Node p)
  {
    p.D = this;
    p.U = U;
    p.U.D = p;
    p.D.U = p;
    size++;
    return p;
  }
  public String toString()
  {
    StringBuffer s = new StringBuffer(name+": ");
      
    for (Node p=D; p!=this; p=p.D)
    {
      s.append(p.RC +", ");
    }
    return s.toString();
  }
}

class Node
{
  Node L, R, U, D;
  int RC;
  Node()
  {
    U=D=L=R=this;
  }
  Node(int r, int c)
  {
    this();
    RC = r*1000+c;
  }
  
  void unlink(Column c, boolean rowWise)
  {
    if (rowWise)
    {
      U.D = D; D.U = U;
      c.size--;
    }
    else
    {
      R.L = L; L.R = R; 
    }
  }
  
  void relink(Column c, boolean rowWise)
  {
    if (rowWise)
    {
      U.D = this; D.U = this;
      c.size++;
    }
    else
    {
      R.L = this; L.R = this;
    }
  }
  
  //============================
  
  int getRowNum()
  {
    return RC/1000;
  }
  int getColNum()
  {
    return RC%1000;
  }
  
  void attachTo(Node last)
  {
    L = last;
    R = last.R;
    L.R = this;
    R.L = this;
  }
  public String toString()
  {
    return (RC/1000)+":"+(RC%1000);
  }
  
}
