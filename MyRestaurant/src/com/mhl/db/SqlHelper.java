/**
 * �����ݿ��������
 * �����ݿ�Ĳ��� crud
 * ���ô洢����
 */
package com.mhl.db;
import java.util.*;
import java.sql.*;
public class SqlHelper {
	//������Ҫ�Ķ���
	PreparedStatement ps=null;
	ResultSet rs=null;
	Connection ct=null;
	String dirverName="com.mysql.jdbc.Driver";
	String url="jdbc:mysql://127.0.0.1:3306/restaurantdb";
	String user="root";
	String passwd="19940308";
	//���캯������ʼ��ct
	public SqlHelper()
	{
		try {
			//��������
			Class.forName(dirverName);
			ct=DriverManager.getConnection(url, user, passwd);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	//�����ݿ������ɾ���� дһ������
	
	public boolean exeUpdate(String sql, String []paras)
	{
		boolean b=true;
		try {
			ps=ct.prepareStatement(sql);
			//��sql����е�?�Ÿ�ֵ
			for(int i=0; i<paras.length; i++)
			{
				ps.setString(i+1,paras[i]);
			}
			ps.executeUpdate();   //��������
		} catch (Exception e) {
			
		}
		return b;
	}
	
	//��¼���������
	public ResultSet query(String sql, String []paras)
	{
		try {
			ps=ct.prepareStatement(sql);
			//��sql����е�?�Ÿ�ֵ
			for(int i=0; i<paras.length; i++)
			{
				ps.setString(i+1,paras[i]);
			}
			rs=ps.executeQuery();
		} catch (Exception e) {
		
		}
		return rs;
	}
	
	//�ر���Դ�� ����
	public void close()
	{
		try {
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
			if(ct!=null) ct.close();
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}
}
