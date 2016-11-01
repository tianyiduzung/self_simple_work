package example2;
/*
 完全可以创建自己的只读类,下面是个简单的例子:
所有数据都设为private,可以看到没有任何public方法对数据作出修改。事实上,确实需要修改一个对象
的方法是 quadruple(),但它的作用是新建一个 Immutable1 对象,初始对象则是原封未动的。 
方法f()需要取得一个Immutable1对象,并对其采取不同的操作,而main()的输出显示出没有对x作任何修
改。因此,x对象可别名处理许多次,不会造成任何伤害,因为根据Immutable1类的设计,它能保证对象不
被改动。
 */
public class Immutable1 { 
	private int data; 
	public Immutable1(int initVal) { 
		data = initVal; 
	} 
	public int read() { return data; } 

	public boolean nonzero() { return data != 0; } 
	public Immutable1 quadruple() {  
		return new Immutable1(data * 4); 
	} 
	static void f(Immutable1 i1) { 
		Immutable1 quad = i1.quadruple(); 
		System.out.println("i1 = " + i1.read()); 
		System.out.println("quad = " + quad.read()); 
	} 
	public static void main(String[] args) { 
		Immutable1 x = new Immutable1(47); 
		System.out.println("x = " + x.read()); 
		f(x); 
		System.out.println("x = " + x.read()); 
	} 
}