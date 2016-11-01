package example3;

public class test5 { 
	public Wrapping wrap(int x) { 
		// Base constructor call: 
		return new Wrapping(x) {  
			public int value() { 
				return super.value() * 47; 
			} 
		}; // Semicolon required 
	} 
	public static void main(String[] args) { 
		test5 p = new test5(); 
		Wrapping w = p.wrap(10); 
		System.out.println(w.value());

	} 
}