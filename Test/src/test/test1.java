package test;

public class test1 {

	public static void main(String[] args) throws NumberFormatException{
		String s=new String("0123");
		int a=new Integer(10);
		int b=Integer.valueOf(s,8);//��string�е����԰˽���ת��Ϊ10���Ƹ��Ƹ�b
		System.out.println(a+":"+b+":"+s);
	}
}
