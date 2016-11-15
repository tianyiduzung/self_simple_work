package test;
/*
 * 大的奇约数
 * 定义函数f(x)为x最大的奇数约数，x为正整数。例如：f(44)=11.
 * 现在给出一个N=7<br>
 * f(1)+f(2)+f(3)+f(4)+f(5)+f(6)+f(7)=1+1+3+1+5+3+7=21
  */
import java.util.Scanner;
public class BigYue {
	public static void main(String[] args) {
		BigYue t=new BigYue();
		Scanner in=new Scanner(System.in);
		while(in.hasNextInt()){
			int sum=0;
			int a=in.nextInt();
			for(int i=1;i<=a;i++){
				sum+=t.f(i);
			}
			System.out.println(sum);
		}
	}
	public int f(int x){
		int r=0;
		int temp=x%2;
		switch(temp){
		case 0: r=f((x/2));
		break;
		case 1:r=x;
		break;
		}
		return r;
	}
}
