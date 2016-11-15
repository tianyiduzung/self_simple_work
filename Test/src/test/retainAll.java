package test;

import java.util.*;

public class retainAll {

	public static void main(String[] args) {

	    List<String> list=new ArrayList<String>();

	    list.add("第一个元素");  //向列表中添加数据

	    list.add("第二个元素");  //向列表中添加数据

	    list.add("第三个元素");  //向列表中添加数据

	    List<String> list1=new ArrayList<String>();

	    list1.add("第一个元素");  //向列表中添加数据

	    list1.add("第三个元素");  //向列表中添加数据

	    boolean ret=list.retainAll(list1);//保留指定collection中包含的所有元素
	    
	    System.out.println(list1);
	}
}
