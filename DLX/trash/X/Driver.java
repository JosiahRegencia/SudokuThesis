class Driver
{
  public static void createColumns()
  {
    Column.expand("A");
    Column.expand("B");
    Column.expand("C");
    Column.expand("D");
    Column.expand("E");
    Column.expand("F");
    Column.expand("G");
  }
  
  public static boolean isSet(char c)
  {
    return c=='1';
  }
  
  public static Column.Node createRow(int rowNum, String row)
  {
    Column.Node head = null;
    
    Column c = Column.MASTER.R;
    for(int i=0; i<row.length(); i++, c=c.R)
      if (isSet(row.charAt(i)))
      {
         Column.Node node = c.createNode(rowNum);
         if (head == null)
           head = node;
         else
           head.attachToLeft(node);
      }
    return head;
  }
  public static void displayCols()
  {
    for(Column c = Column.MASTER.R; c!=Column.MASTER; c=c.R)
    {
      int i=c.count;
      System.out.print(c.name+": ");
      for (Column.Node p = c.top.down; i>0; p=p.down, i--)
        System.out.print(p.rowNum);
      System.out.println(" total "+c.count+" nodes");
    }
  }
  
  public static void displayRow(Column.Node r)
  {
    int i=0;
    for (Column.Node p=r; i==0 || p!=r; p=p.right,i++)
      System.out.print(p.colName());
    System.out.println();
  }
  public static void displayRows(Column.Node[] r)
  {
    for (int i=0; i<r.length; i++)
      displayRow(r[i]);
  }
  
  public static void main(String []arg)
  {
    createColumns();
    Column.Node [] row = new Column.Node[6];
    row[0] = createRow(0,"0010110");
    row[1] = createRow(1,"1001001");
    row[2] = createRow(2,"0110010");
    row[3] = createRow(3,"1001000");
    row[4] = createRow(4,"0100001");
    row[5] = createRow(5,"0001101");
    
    displayRows(row);
    displayCols();
System.out.println("covering A");
    
    Column a = Column.MASTER.R;
    a.cover();
    displayCols();
    
    a.top.down.coverColumnsss();
    displayCols();
    a.top.down.uncoverColumnsss();
   // displayCols();

    System.out.println("--------------");   
    a.top.down.down.coverColumnsss();
    displayCols();
    a.top.down.down.uncoverColumnsss();
//    displayCols();
    a.uncover();
    displayCols();
  }
}