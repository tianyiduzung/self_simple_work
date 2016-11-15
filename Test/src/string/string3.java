package string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class string3 {
	public static void main(String[] args){
		string3 wb=new string3();
		String[] str={"ab","bc","cd","fe"};
		wb.WordConnect(str);
	}
	
	public void WordConnect(String[] str){
		new string3();
		ArrayList str1=new ArrayList();
		ArrayList str2=new ArrayList();
		for(int i=0;i<str.length;i++){
			 str1.add(str[i].charAt(0));
			 str2.add(str[i].charAt(str[i].length()-1));
		}
		List<String> list=getSame(str1,str2);
		if(list.toString().length()>=str.length-1){
			System.out.println("true");
			
		}else{
			System.out.println("false");
		}
	}
	
	public List<String> getSame(ArrayList str1,ArrayList str2){
		if(str1 == null || str2 == null) {  
            return null;  
        }  
        str1.retainAll(str2);
        return str1; 
	}
}
