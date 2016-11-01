package example3;
/*
1,
  public表示它们可在包外使用;Static 强调它们只有一个;而 final 表明它是一个常数。
注意对于含有固定初始化值(即编译期常数)的 final static 基本数据类型,
它们的名字根据规则要全部采用大写。
也要注意 i5 在编译期间是未知的,所以它没有大写。
2,
  不能由于某样东西的属性是 final,就认定它的值能在编译时期知道。
  i4 和 i5 向大家证明了这一点。
3,
static final与final的区别：
  注意对于 fd1 和 fd2 来说,i4 的值是唯一的,但 i5 的值不会由于创建了
另一个 FinalData 对象而发生改变。那是因为它的属性是 static,
而且在载入时初始化,而非每创建一个对象时初始化。
4,
  从v1到v4的变量向我们揭示出final句柄的含义。正如大家在 main()中看到的那样,
并不能认为由于v2属于final,所以就不能再改变它的值。
然而,我们确实不能再将v2绑定到一个新对象,因为它的属性是final。
 */

class Value{
	int i=1;
}
public class FinalData {

	final int i1=9;
	static final int I2=99;
	public static final int I3=39;
	final int i4=(int)(Math.random()*20);
	static final int i5=(int)(Math.random()*20);
	
	Value v1=new Value();
	final Value v2=new Value();
	static final Value v3=new Value();
	//! final Value v4;//Error:no initializer
	
	final int[] a={1,2,3,4,5,6};
	
	public void print(String id){
		System.out.println(id+":"+
				"i4="+i4+",i5="+i5);
	}
	
	public static void main(String[] args){
		FinalData fd1=new FinalData();
		//! fd1.i1++;
		fd1.v2.i++;//v2中的值是可以改变的，不能改变的只是v2本身
		fd1.v1=new Value();
		for(int i=0;i<fd1.a.length;i++)
			fd1.a[i]++;
		//! fd1.a=new int[3];
		
		fd1.print("fd1");
		System.out.println("Creating new FinalData");
		
		FinalData fd2=new FinalData();
		fd1.print("fd1");
		fd2.print("fd2");
	}
}
