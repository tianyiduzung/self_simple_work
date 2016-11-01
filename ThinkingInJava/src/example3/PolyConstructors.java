package example3;
/*
初始化的实际过程:
(1) 在采取其他任何操作之前,为对象分配的存储空间初始化成二进制零。 
(2) 就象前面叙述的那样,调用基础类构建器。此时,被覆盖的 draw()方法会得到调用(的确是在
RoundGlyph 构建器调用之前),此时会发现 radius 的值为 0,这是由于步骤(1)造成的。 
(3) 按照原先声明的顺序调用成员初始化代码。 
(4) 调用衍生类构建器的主体。 

因此,设计构建器时一个特别有效的规则是:用尽可能简单的方法使对象进入就绪状态;
如果可能,避免调用任何方法。在构建器内唯一能够安全调用的是在基础类中
具有final属性的那些方法(也适用于 private方法,它们自动具有 final 属性)。
这些方法不能被覆盖,所以不会出现上述潜在的问题。
 * */
abstract class Glyph { 

	abstract void draw(); 
	Glyph() { 
		System.out.println("Glyph() before draw()"); 
		draw();  
		System.out.println("Glyph() after draw()"); 
	} 
} 

class RoundGlyph extends Glyph { 
	int radius = 1; 
	RoundGlyph(int r) { 
		radius = r; 
		System.out.println( 
				"RoundGlyph.RoundGlyph(), radius = " 
						+ radius); 
	} 
	void draw() {  
		System.out.println( 
				"RoundGlyph.draw(), radius = " + radius); 
	} 
} 

public class PolyConstructors { 
	public static void main(String[] args) { 
		new RoundGlyph(5); 
	} 
}