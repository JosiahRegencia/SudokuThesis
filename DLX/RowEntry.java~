class RowEntry
{
  final class Node
  {
    Node left, right, up, down;
    
    Node(Node l, Node r)
    {
      left = l;
      right = r;
    }
  }
  
  String desc;
  Node head;
  
  public RowEntry(String d)
  {
    desc = d;
    head = null;
  }
  
  public Cursor getCursor()
  {
    Cursor result = new Cursor()
    {
      Node ptr = head;
      
      public void moveLeft()
      {
        ptr = ptr.left;
      }
      public void moveRight()
      {
        ptr = ptr.right;
      }
      public boolean isFirst()
      {
        return ptr == head;
      }
      public boolean isLast()
      {
        return ptr == head.left;
      }
      
    };
  }
}
