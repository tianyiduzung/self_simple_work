package example3;

public class test4 {

	public static void main(String[] args) {
		C c=new C();
	}
}

class A extends C{
	A(String s){
		System.out.println("A1"+s);
	}
}
class B{
	B(String s1){
		System.out.println("B1"+s1);
	}
}

class C{
	C(){
		
		//！ A a=new A("2"); //Error：构成了循环初始化，造成内存溢出
		B b=new B("2");
	}
	
}