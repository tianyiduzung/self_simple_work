package example3;

class Homer{
	char doh(char c){
		System.out.println("doh(char):"+c);
		return 'd';
	}
	float doh(float f){
		System.out.println("doh(float):"+f);
		return 1.0f;
	}
}
class Mlhouse{}
class Bart extends Homer{
	//doh()方法重载
	void doh(Mlhouse m){
		System.out.println("doh(Mlhouse)");
	}
}
public class test3{
	public static void main(String[] args){
		Bart b=new Bart();
		b.doh(1);
		b.doh('x');
		b.doh(1.0f);
		b.doh(new Mlhouse());
	}
}

/*public class test3 {

	public static void main(String[] args)
	{
		int i=-1;
		System.out.println(i>>>10);
		long l=-1;
		System.out.println(l>>>10);
		short s=-1;
		System.out.println(s>>>10);
		byte b=-1;
		System.out.println(b>>>10);
	}
}*/
