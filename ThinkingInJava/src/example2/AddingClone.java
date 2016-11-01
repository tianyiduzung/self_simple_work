package example2;
import java.util.*; 
/*
 用 V e c t o r 进行深层复制--
 总结出对 Vector 进行深层复制的先决条件:在克隆了 Vector 后,必须在其中遍历,并克隆
由 Vector 指向的每个对象。为了对 Hashtable(散列表)进行深层复制,也必须采取类似的处理。 
 */
class Int2 implements Cloneable { 
	private int i; 
	public Int2(int ii) { i = ii; } 
	public void increment() { i++; } 
	public String toString() { 
		return Integer.toString(i); 
	}
	public Object clone() { 
		Object o = null; 
		try { 
			o = super.clone(); 
		} catch (CloneNotSupportedException e) { 
			System.out.println("Int2 can't clone"); 
		} 
		return o; 
	} 
} 


// Once it's cloneable, inheritance 
// doesn't remove cloneability: 
class Int3 extends Int2 { 
	private int j; // Automatically duplicated 
	public Int3(int i) { super(i); } 
} 

public class AddingClone { 
	public static void main(String[] args) { 
		Int2 x = new Int2(10); 
		Int2 x2 = (Int2)x.clone(); 
//		Int2 x2=x;
		x2.increment(); 
		System.out.println( 
				"x = " + x + ", x2 = " + x2); 
		// Anything inherited is also cloneable: 
		Int3 x3 = new Int3(7); 
		x3 = (Int3)x3.clone(); 

		Vector v = new Vector(); 
		for(int i = 0; i < 10; i++ ) 
			v.addElement(new Int2(i)); 
		System.out.println("v: " + v); 
		Vector v2 = (Vector)v.clone(); 
		// Now clone each element: 
		for(int i = 0; i < v.size(); i++) 
			v2.setElementAt(
					((Int2)v2.elementAt(i)).clone(), i); //v和v2都可以
		// Increment all v2's elements: 
		for(Enumeration e = v2.elements();
				e.hasMoreElements(); ) 
			((Int2)e.nextElement()).increment(); 
		/*Enumeration e=v2.elements();
		while(e.hasMoreElements()){
			((Int2)e.nextElement()).increment();
		}*/
		// See if it changed v's elements: 
		System.out.println("v: " + v); 
		System.out.println("v2: " + v2); 
	} 
}
