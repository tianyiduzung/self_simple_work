package test;

public class test1 {

	public static void main(String[] args) throws NumberFormatException{
		String s=new String("0123");
		int a=new Integer(10);
		int b=Integer.valueOf(s,8);//将string中的数以八进制转换为10进制复制给b
		System.out.println(a+":"+b+":"+s);
	}
}
