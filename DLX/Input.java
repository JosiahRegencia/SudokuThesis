public class Input
{
  Node masterHead;
 
  void makeRow(String in)
  {
    Node p, head = null, col = masterHead.right;
    
    for (int i=0; i<in.length; i++)
    {
      if (in.charAt(i) = '1')
      {
        p = col.insert();
        if (head == null) // first element in row
        {
          head.left = head.right = p;
        }
        else
          head.insertLeft(p);
      }
      col = col.right;
    }
  }
}