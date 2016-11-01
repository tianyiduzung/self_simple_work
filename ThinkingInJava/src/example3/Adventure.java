package example3;
/*
 一个类既继承了父类，也实现了接口，那么可以在父类中实现对接口中方法的重写。 
 */
import java.util.*; 

interface CanFight { 
	void fight(); 
} 

interface CanSwim { 
	void swim(); 
} 

interface CanFly { 
	void fly(); 
} 

class ActionCharacter { 
	public void fight() {
		System.out.println("AC fight()");
	} 
} 

class Hero extends ActionCharacter  
implements CanFight, CanSwim, CanFly {
	//注意：此处巧妙的利用了继承ActionCharacter类，来实现了fight()的重写。
	//所以还是实现了CanFight接口中fight()方法的重写
	public void swim() {
		System.out.println("Hero swim()");
	} 
	public void fly() {
		System.out.println("Hero fly()");
	}
	//即fight()方法在父类中重写了，子类可以不重写
}

public class Adventure { 
	static void t(CanFight x) { x.fight(); } 
	static void u(CanSwim x) { x.swim(); } 
	static void v(CanFly x) { x.fly(); } 
	static void w(ActionCharacter x) { x.fight(); } 
	public static void main(String[] args) { 
		Hero i = new Hero(); 
		t(i); // Treat it as a CanFight 
		u(i); // Treat it as a CanSwim 
		v(i); // Treat it as a CanFly 
		w(i); // Treat it as an ActionCharacter 
	} 
} 