package example3;
/*
 * 演示static的使用：只初始化一次
 * 初始化的顺序是首先 static(如果它们尚未由前一次对象创建过程初始化),
 * 接着是非 static 对象。
 * 
 * */
class Bow{
	Bow(int marker){
		System.out.println("Bow("+marker+")");
	}
	void f(int marker){
		System.out.println("f("+marker+")");
	}
}

class Table{
	static Bow b1=new Bow(1);
	Table(){
		System.out.println("Table()");
		b2.f(1);
	}
	void f2(int marker){
		System.out.println("f2("+marker+")");
		
	}
	static Bow b2=new Bow(2);
}

class Cupboard{
	Bow b3=new Bow(3);
	static Bow b4=new Bow(4);
	Cupboard() {
		System.out.println("Cupboard()");
		b4.f(2);
	}
	void f3(int marker){
		System.out.println("f3("+marker+")");
	}
	static Bow b5=new Bow(5);
}

public class StaticInitialization{
	public static void main(String[] args) {
		System.out.println("Creating  new Cupboard() in main");
		new Cupboard();
		System.out.println("Creating new Cupboard() in main");
		new Cupboard();
		t2.f2(1);
		t3.f3(1);
	}
	static Table t2=new Table();
	static Cupboard t3=new Cupboard();
}