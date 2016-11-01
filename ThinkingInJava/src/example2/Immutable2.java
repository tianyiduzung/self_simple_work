package example2;

//: Immutable2.java 
//A companion class for making changes 
//to immutable objects. 

/*
 从表面看,不变类的建立似乎是一个好方案。但是,一旦真的需要那种新类型的一个修改的对象,就必须辛
苦地进行新对象的创建工作,同时还有可能涉及更频繁的垃圾收集。对有些类来说,这个问题并不是很大。
但对其他类来说(比如 String 类),这一方案的代价显得太高了。 
为解决这个问题,我们可以创建一个“同志”类,并使其能够修改。以后只要涉及大量的修改工作,就可换
为使用能修改的同志类。完事以后,再切换回不可变的类。 
因此,上例可改成下面这个样子: 
 */

/*
 两个静态方法 modify1()和 modify2()揭示出获得同样结果的两种不同方法。在 modify1()中,所有工作都是
在 Immutable2 类中完成的,我们可看到在进程中创建了四个新的 Immutable2 对象(而且每次重新分配了
val,前一个对象就成为垃圾)。 
在方法 modify2()中,可看到它的第一个行动是获取 Immutable2 y,然后从中生成一个 Mutable(类似于前
面对 clone()的调用,但这一次创建了一个不同类型的对象)。随后,用 Mutable 对象进行大量修改操作,
同时用不着新建许多对象。最后,它切换回 Immutable2。在这里,我们只创建了两个新对象(Mutable 和
Immutable2 的结果),而不是四个。 
这一方法特别适合在下述场合应用: 
(1) 需要不可变的对象,而且 
(2) 经常需要进行大量修改,或者 
(3) 创建新的不变对象代价太高 
 */
class Mutable { 
	private int data; 
	public Mutable(int initVal) { 
		data = initVal;  
	} 
	public Mutable add(int x) {  
		data += x; 
		return this; 
	} 
	public Mutable multiply(int x) { 
		data *= x; 
		return this; 
	} 
	public Immutable2 makeImmutable2() { 
		return new Immutable2(data); 
	} 
} 

public class Immutable2 { 
	private int data; 
	public Immutable2(int initVal) { 
		data = initVal; 
	} 
	public int read() { return data; } 
	public boolean nonzero() { return data != 0; } 
	public Immutable2 add(int x) {  
		return new Immutable2(data + x); 
	} 
	public Immutable2 multiply(int x) { 
		return new Immutable2(data * x); 
	} 
	public Mutable makeMutable() { 
		return new Mutable(data); 
	} 
	public static Immutable2 modify1(Immutable2 y){ 
		Immutable2 val = y.add(12); 
		val = val.multiply(3); 
		val = val.add(11); 
		val = val.multiply(2); 
		return val; 
	} 
	// This produces the same result: 
	public static Immutable2 modify2(Immutable2 y){ 
		Mutable m = y.makeMutable(); 
		m.add(12).multiply(3).add(11).multiply(2); 
		return m.makeImmutable2(); 
	} 
	public static void main(String[] args) {  
		Immutable2 i2 = new Immutable2(47); 
		Immutable2 r1 = modify1(i2); 
		Immutable2 r2 = modify2(i2); 
		System.out.println("i2 = " + i2.read()); 
		System.out.println("r1 = " + r1.read()); 
		System.out.println("r2 = " + r2.read()); 
	} 
} 