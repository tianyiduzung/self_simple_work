package example2;
/*
Integer 类(以及基本的“封装器”类)用简单的形式实现了“不变性”:它们没有提供可以修改对象的方法。
 */
//: ImmutableInteger.java 
//The Integer class cannot be changed 
import java.util.*; 

public class ImmutableInteger { 
	public static void main(String[] args) { 
		Vector v = new Vector(); 
		for(int i = 0; i < 10; i++)  
			v.addElement(new Integer(i));
		System.out.println(v);
		// But how do you change the int inside the Integer? 
	} 
} ///: ̄ 
