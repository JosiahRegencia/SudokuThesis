class Cursor
{
  Column column;
  Node ptr;
  
  Cursor(Column c)
  {
    column = c;
    ptr = c.down;
  }
  
  void goLeft()   { ptr = ptr.d}
  void goRight() {}
  
  void goUp()     { ptr = ptr.up;}
  void goDown()   { ptr = ptr.down;}
  void reLink();
  void unLink();
}