package example3;
/*
构建器的调用遵照下面的顺序:
(1) 调用基础类构建器。这个步骤会不断重复下去,首先得到构建的是分级结构的根部,
然后是下一个衍生类,等等。直到抵达最深一层的衍生类。 
(2) 按声明顺序调用成员初始化模块。 
(3) 调用衍生构建器的主体。
*/
class Meal { 
	Meal() { System.out.println("Meal()"); } 
} 

class Bread { 
	Bread() { System.out.println("Bread()"); } 
} 

class Cheese { 
	Cheese() { System.out.println("Cheese()"); } 
} 

class Lettuce { 
	Lettuce() { System.out.println("Lettuce()"); } 
} 

class Lunch extends Meal { 
	Lunch() { System.out.println("Lunch()");} 
} 

class PortableLunch extends Lunch { 
	PortableLunch() { 
		System.out.println("PortableLunch()"); 
	} 
} 

public class Sandwich extends PortableLunch { 
	Bread b = new Bread(); 
	Cheese c = new Cheese(); 
	Lettuce l = new Lettuce(); 
	Sandwich() {  
		System.out.println("Sandwich()"); 
	} 
	public static void main(String[] args) { 
		new Sandwich(); 
	} 
}