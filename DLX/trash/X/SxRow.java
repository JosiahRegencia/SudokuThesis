class SxRow
{
  /*
   * 00-08 r
   * 09-17 c
   * 18-26 b
   * 27-35 v
   * 36-39 delete (when on) bits
   */
  long data = 0;
  
  SxRow(short r, short c, short b, short v)
  {    data = (long)r | ((long) c)<<9 | ((long) b)<<18  | ((long) v)<<27;}
    
  boolean isSet(short x)
  {
    return !isClear(x);
  }
  
  boolean isClear(short x)
  {
    return _getIndex(x) < 0;
  }

  int _getIndex(short x)
  {
    if (x == (short)(data & 511))
      return 0;
    else if (x == (short)(data>>9 & 511))
      return 1;
    else if (x == (short)(data>>18 & 511))
      return 2;
    else if (x == (short)(data>>27 & 511))
      return 3;
    else return -1;
  }

  void SxDelete(short x)
  {
    data |= 1L << (36 + _getIndex(x));
  }
  
  void SxRestore(short x)
  {
    data &= ~0L ^ (1L << (36 + _getIndex(x)));
  }

  boolean SxIsDeleted(short x)
  {
    return (data & (1L <<36 + _getIndex(x))) != 0;
  }
  
  java.util.Iterator<Short> getIterator()
  {
    return new java.util.Iterator<Short>()
    {
      int i = 0;
      public boolean hasNext()
      {
        return i<4;
      }
      public Short next()
      {
        return (short)((data >> (9*i++)) & 511);
      }
      public void remove()
      {}
    };
  }
}