package test;
/*
 * С��ȥ�������̵���ƻ������թ���̷�ʹ���������ף�ֻ�ṩ6��ÿ����8��ÿ��
 * �İ�װ����װ���ɲ�֣�������С������ֻ�빺��ǡ��n��ƻ����С���빺�����ٵĴ���
 * ����Я����������ܹ���ǡ��n��ƻ����С�׽����Ṻ��
 */
import java.util.Scanner;
public class Apple {
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		while(in.hasNextInt()){
			int n=in.nextInt();
			if(n<0||n>100){
				System.out.println("������0-100����");
				continue;
			}
			int temp1=0,temp2=0;
			int a=n/8;
			int b=n/6;
			for(int i=0;i<=a;i++){
				for(int j=0;j<=b;j++){
					if((i*8+j*6)==n){
						temp1=i;
						temp2=j;
					}
				}
			}
			System.out.println("8��һ����"+temp1+"  6��һ����"+temp2);
		}
	}
}
