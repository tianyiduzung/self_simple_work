package example3;
/*
 从一个字符串中删除恰好俩个字符，能形成多少种不同的字符串？
 * */
import java.util.*;
public class test1{
	public List list;
	public static void main(String[] args)
	{
		String ss="data";
		test1 t=new test1();
		List li=t.count(ss);
		System.out.println(li);
	}
	public List count(String s){
		String s1;
		list=new ArrayList();
		for(int i=0;i<s.length()-1;i++){
			for(int j=i+1;j<s.length();j++){
				s1=s.substring(0, i)+
						s.substring(i+1,j)+s.substring(j+1,s.length());
				if(!(list.contains(s1))){
					list.add(s1);
				}
			}
		}
		return list;
	}
}