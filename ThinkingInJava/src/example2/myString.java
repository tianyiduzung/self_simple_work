package example2;
/*
(1) 创建一个 myString 类,在其中包含了一个 String 对象,以便用在构建器中用构建器的自变量对其进行
初始化。添加一个 toString()方法以及一个 concatenate()方法,令其将一个 String 对象追加到我们的内部
字串。在 myString 中实现 clone()。创建两个 static 方法,每个都取得一个 myString x 句柄作为自己的自
变量,并调用 x.concatenate("test")。但在第二个方法中,请首先调用 clone()。测试这两个方法,观察它
们不同的结果。 
 */
public class myString implements Cloneable{
	private String s;
	public myString(String s){
		this.s=s;
	}
	public String toString(){
		return s;
	}
	public String concatenate(String s1){
		s=s+s1;
		return s;
	}
	public Object clone(){
		Object o=null;
		try { 
			o = (myString)super.clone(); 
		} catch (CloneNotSupportedException e) { 
			e.printStackTrace(); 
		} 
		return o;
	}
	static void get1(myString x){
		x.concatenate("test");
		
	}
	static void get2(myString x){
		myString x1=(myString) x.clone();
		x1.concatenate("test");
	}
	
	public static void main(String[] args) {
		myString ms1=new myString("yi");
		myString ms2=new myString("jia");
		get1(ms1);
		System.out.println(ms1);
		get2(ms2);
		System.out.println(ms2);
	}
}
