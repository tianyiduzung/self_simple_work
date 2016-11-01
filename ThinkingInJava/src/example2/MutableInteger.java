package example2;

import java.util.*; 
/*
若确实需要一个容纳了基本数据类型的对象,并想对基本数据类型进行修改,就必须亲自创建它们。
幸运的是,操作非常简单:
 */
class IntValue {  
  int n; 
  IntValue(int x) { n = x; } 
  public String toString() {  
    return Integer.toString(n); 
  } 
} 
 
public class MutableInteger { 
  public static void main(String[] args) { 
    Vector v = new Vector(); 
    for(int i = 0; i < 10; i++)  
      v.addElement(new IntValue(i)); 
    System.out.println(v); 
    for(int i = 0; i < v.size(); i++) 
      ((IntValue)v.elementAt(i)).n++; 
    System.out.println(v); 
  } 
} 
