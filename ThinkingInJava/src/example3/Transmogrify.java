package example3;
/*
思路是首先选择“合成”——如果不能十分确定自己应使用哪一个。
合成不会强迫我们的程序设计进入 "继承" 的分级结构中。同时,合成显得更加灵活,
因为可以动态选择一种类型(以及行为),而继承要求在编译期间准确地知道一种类型。
 * */
interface Actor { 
	void act(); 
} 

class HappyActor implements Actor { 
	public void act() {  
		System.out.println("HappyActor");  
	} 
} 

class SadActor implements Actor { 
	public void act() {  
		System.out.println("SadActor"); 
	} 
} 

class Stage { 
	Actor a = new HappyActor(); 
	void change() { a = new SadActor(); } 
	void go() { a.act(); } 
} 

public class Transmogrify { 
	public static void main(String[] args) { 
		Stage s = new Stage(); 
		s.go(); // Prints "HappyActor" 
		s.change(); 
		s.go(); // Prints "SadActor" 
	} 
}