package example2;
/*
这意味着只有第一段才是由 Object.clone()复制的,所以此时进行的是一种“浅层复制”。
若希望复制整条蛇——即进行“深层复制”——必须在被覆盖的 clone()里采取附加的操作。 
*/
public class Snake implements Cloneable { 
	private Snake next; 
	private char c; 
	// Value of i == number of segments 
	Snake(int i, char x) { 
		c = x; 
		if(--i > 0) 
			next = new Snake(i, (char)(x + 1)); 

	} 
	void increment() { 
		c++; 
		if(next != null) 
			next.increment(); 
	} 
	public String toString() { 
		String s = ":" + c; 
		if(next != null) 
			s += next.toString(); 
		return s; 
	} 
	public Object clone() { 
		Object o = null; 
		try { 	
			o = super.clone();
		} catch (CloneNotSupportedException e) {} 
		return o; 
	} 

	public static void main(String[] args) { 
		Snake s = new Snake(5, 'a'); 
		System.out.println("s = " + s); 
		Snake s2 = (Snake)s.clone(); 
		System.out.println("s2 = " + s2); 
		s.increment(); 
		System.out.println( 
				"after s.increment, s2 = " + s2); 
	} 
}
