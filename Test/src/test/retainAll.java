package test;

import java.util.*;

public class retainAll {

	public static void main(String[] args) {

	    List<String> list=new ArrayList<String>();

	    list.add("��һ��Ԫ��");  //���б����������

	    list.add("�ڶ���Ԫ��");  //���б����������

	    list.add("������Ԫ��");  //���б����������

	    List<String> list1=new ArrayList<String>();

	    list1.add("��һ��Ԫ��");  //���б����������

	    list1.add("������Ԫ��");  //���б����������

	    boolean ret=list.retainAll(list1);//����ָ��collection�а���������Ԫ��
	    
	    System.out.println(list1);
	}
}
