class Column 
{
  String name;
  int count = 0;
  Column L,R;
  Node top = createNode(-1);
  
  void cover()
  {
  //  unlinkColumn();
    L.R = R;
    R.L = L;
    
    int c = count;
    for (Node i = top.down; c > 0 ; i = i.down, --c)
      for (Node j = i.right; j != i; j = j.right)
        j.unlink();
  }
  
  void uncover()
  {
    int c = count;
    for (Node i = top.up; c > 0; i = i.up, --c)
      for (Node j = i.left; i != j; j = j.left)
         j.relink();
    
    L.R = this;
    R.L = this;
//    relinkColumn();
  }
   
  static Column pickMinColumn()
  {
    Column min = MASTER.R;
    for (Column c = min; c != MASTER; c=c.R)
      min = (c.count<min.count)?c:min;
    return min;
  }
  
  ///// delete or move to driver
  void coverRows()
  {
    System.out.println(".... covering cols..."+name); 
    for (Node i = top.down; i!=top; i=i.down)
    {
      System.out.println("iterating vertically at " + i.rowNum+i.colName());
      i.coverColumnsss();
      //save i;
      //recurse
      //restore i
      //uncover i;
    }
  }
  void uncoverRows()
  {
    for (Node i = top.up; i!=top; i=i.up)
      i.uncoverColumnsss();
  }
  
  public final class Node
  {
    Node up, down, right, left;
    int rowNum =-1;

    Node unlink()
    {
      up.down = down;
      down.up = up;
      count--;
      return this;
    }

    void relink()
    {
      up.down = this;
      down.up = this;
      count++;
    }
 
    void coverColumn()
    {
      cover();
    }
    
    void uncoverColumn()
    {
      uncover();
    }
    
    void coverColumnsss()
    {
      for (Node j = right; j != this; j = j.right)
        j.coverColumn();
    }
    
    void uncoverColumnsss()
    {
      for (Node j = left; j != this; j = j.left)
        j.uncoverColumn();
    }
    ////////////////
    /////////////////////////////
    
    Node(int r)
    {
      left = right = this;
      rowNum = r;
      // for heuristics (D. Knuth)
      if (top == null)
      {
        top = down = up = this;
      }
      else
     {
        count++;
        down = top;
        up = top.up;
        top.up = this;
        up.down = this;
      }
    }

    
    public String toString()
    {
      return "("+rowNum+":"+name+")";
    }
    
    //to be used for populating input
    // newNode will be attached to the left of this node
    void attachToLeft(Node newNode)
    {
      newNode.left = left;
      newNode.right = this;
      newNode.right.left = newNode;
      newNode.left.right = newNode;
    }
    public String colName() { return name; }
  } // class Node

  
  Column(String name)
  {
    this.name = name;
    L = this;
    R = this;
  }
    
  static Column MASTER = new Column("Master");
  static Column expand(String name)
  {
    Column c = new Column(name);
    c.R = MASTER;
    c.L = MASTER.L;
    c.L.R = c;
    c.R.L = c;
    return MASTER;
  }
  
  Column.Node createNode(int r)
  {
    return new Column.Node(r);
  }
}