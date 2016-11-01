package example3;

import java.util.*;

/*
 给定一个只包含0和1的字符串S,找到S的一个子串S[i,j]满足：
 1,存在下标k，满足i<=k<j;
 2,子串S[i,k]中0的个数比1的个数多
 3,子串S[k+1,j]中1的个数比0的个数多
 4,子串S[i,j]尽可能长
 */
public class test2 {
	List list;
	int len=0;
	String si;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ss="0001011011101";
		test2 t=new test2(ss);
	}
	
	public test2(String ss){
		list=new ArrayList<String>();
		list=this.search(ss);
		String s1;
		for(int i=0;i<list.size();i++){
			s1=(String)list.get(i);
			if(s1.length()>len){
				len=s1.length();
				si=s1;
			}
		}
		System.out.println(si);
	}
	//对字符串的每个子串进行判断，是否满足4个要求，满足则加入list集合
	public List search(String s){
		String s1;
		for(int i=0;i<s.length();i++)
		{
			for(int j=i;j<s.length();j++){
				s1=s.substring(i, j+1);
				if(count(s1)){
					list.add(s1);
				}
			}
		}
		return list;
	}
	//判断字符串s是否满足4个要求
	public boolean count(String s){
		boolean b = false;
		for(int i=0;i<s.length();i++){
			String s1=s.substring(0,i);
			String s2=s.substring(i,s.length());
			int count1,count2,count3,count4;
			count1=sum(s1,"0");
			count2=sum(s1,"1");
			count3=sum(s2,"0");
			count4=sum(s2,"1");
			if(count1>count2){
				if(count3<count4){
					b=true;
				}
			}
		}
		return b;
	}
	//计算字符串s中包含s1的个数
	public int sum(String s,String s1){
		int count = 0;
		int index=0;
		while(true){
			index=s.indexOf(s1,index+1);
			if(index>0){
				count++;
			}else{
				break;
			}
		}
		return count;
	}
}
