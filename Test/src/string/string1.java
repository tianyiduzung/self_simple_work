package string;

public class string1 {

	String[] ss;
	char[] chs;
	boolean flag;
	public static void main(String[] args) {
		new string1();
	}
	
	public string1(){
		flag=true;
		String[] ss={"ab","bc","cde","eda"};
		for(int i=0;i<ss.length-1;i++){
			if(ss[i].charAt(ss[i].length()-1)!=ss[i+1].charAt(0)){
				flag=false;
			}
		}
		if(flag){
			System.out.println("yes");
		}else{
			System.out.println("no");
		}
	}
}
