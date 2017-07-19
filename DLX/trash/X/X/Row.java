class Row
{
  long data = 0;
  
  Row(short r, short c, short b, short v)
  {    data = (long)r | ((long) c)<<9 | ((long) b)<<18  | ((long) v)<<27;}
  
  short xRow()
  {    return (short)(data & 511);}
  
  short xCol()
  {    return (short)(data>>9 & 511);}
  
  short xBox()
  {    return (short)(data>>18 & 511L);}
  
  short xVal()
  {    return (short)(data>>27 & 511L);}
  
  boolean isMember(short x)
  {
    return (x == (short)(data & 511))
      ||   (x == (short)(data>>9 & 511))
      ||   (x == (short)(data>>18 & 511))
      ||   (x == (short)(data>>27 & 511));
  }
  
  // row deletion/restoratioin
  
  void delete()
  {
    data |= (1<<36);
  }
  void unDelete()
  {
    
  }
}