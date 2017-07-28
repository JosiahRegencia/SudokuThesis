class Column
{
  String name;
  int count = 0;
  
  Node head = null;
  
  Column()
  {
    head = new Node();
  }  
  
  Node insert()
  {
    return new Node();
  }
  
  public final class Node
  {
    Node up, down, right, left;

    
    Node()
    {
      if (head == null)
      {
        down = this;
        up = this;
      }
      else
      {
        down = head;
        up = head.up;
        head.up = this;
        up.down = this;
        count++;
      }
    }
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
    
    //to be used for populating input
    void insertLeft(Node newNode)
    {
      newNode.left = left;
      newNode.right = this;
      newNode.right.left = this;
      newNode.left.right = this;
    }
  } // class Node
  
}