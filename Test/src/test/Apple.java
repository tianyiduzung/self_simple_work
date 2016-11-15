package test;
/*
 * 小易去附近的商店买苹果，奸诈的商贩使用了捆绑交易，只提供6个每袋和8个每袋
 * 的包装（包装不可拆分）。可是小易现在只想购买恰好n个苹果，小易想购买尽量少的袋数
 * 方便携带。如果不能购买恰好n个苹果，小易将不会购买。
 */
import java.util.Scanner;
public class Apple {
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		while(in.hasNextInt()){
			int n=in.nextInt();
			if(n<0||n>100){
				System.out.println("请输入0-100的数");
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
			System.out.println("8个一袋："+temp1+"  6个一袋："+temp2);
		}
	}
}
