package string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class string2 {
	public static void main(String[] args){
		string2 wb=new string2();
		String[] str={"ca","cd","ec"};
		wb.WordConnect(str);
	}
	
	public void WordConnect(String[] str){
		new string2();
		ArrayList str1=new ArrayList();
		ArrayList str2=new ArrayList();
		for(int i=0;i<str.length;i++){
			 str1.add(str[i].charAt(0));
			 str2.add(str[i].charAt(str[i].length()-1));
		}
		int l=getSame(str1,str2);
		if(l>=str.length-1){
			System.out.println("true");
			
		}else{
			System.out.println("false");
		}
	}
	
	public int getSame(ArrayList str1,ArrayList str2){
		int len=0;
		if(str1 == null || str2 == null) {  
            return 0;  
        }  
        for(int i=0;i<str1.size();i++){
        	for(int j=0;j<str2.size();j++){
        		if(str1.get(i).equals(str2.get(j))){
        			len++;
        			System.out.println(str1.get(i));
        			str2.remove(j);
        		}else{
        			continue;
        		}
        	}
        }
        return len; 
	}
}
