package string;

import java.util.*;

public class string {

	Vector<Map> v;
	boolean flag;
	public static void main(String[] args) {
		new string();
	}
	
	public string(){
		flag=true;
		String[] ss={"ab","bc","cde","eda"};
		v=new Vector<Map>();

		for(String s:ss){
			v.add(getMap(s));
		}
		
		for(int i=0;i<v.size()-1;i++){
			if(v.get(i).get(v.get(i).size()-1)!=v.get(i+1).get(0)){
				flag=false;
			}
		}
		
		if(flag){
			System.out.println("可以");
		}else{
			System.out.println("不行");
		}
		
		
	}

	public Map getMap(String str){
		int value=0;
		TreeMap<Integer,Character> map=new TreeMap<Integer,Character>();
		char[] chs=str.toCharArray();

		for(Character ch:chs){			
			map.put(value, ch);
			value++;		
		}
		return map;
	}

}
