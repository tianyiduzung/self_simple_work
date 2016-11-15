/**
 * �������µ�����ģ���࣬��Ҫ����ɶ����±�ĸ��ֲ���
 */
package com.mhl.model;
import javax.swing.table.*;
import java.util.*;
import com.mhl.db.*;
import java.sql.*;

public class EmpModel extends AbstractTableModel {

	Vector<String>colums;
	Vector<Vector>rows;
	
	
	//�ṩһ��ͨ��Ա������ɾ��Ա��
	public boolean delEmpById(String empId)
	{
		boolean b=true;
		String sql="use RestaurantDB;delete from clerkInfo where cleId=?";
		String []paras={empId};
		SqlHelper sh=new SqlHelper();
		try {
			b=sh.exeUpdate(sql, paras);
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			sh.close();
		}
		return b;
	}
	
	//дһ�����������ڲ�ѯ��Ҫ��ʾ��������Ϣ����һ��ͨ���Ե�query
	public void query(String sql, String paras[])
	{
		//��ʼ����
		this.colums=new Vector<String >();
		
		
		this.rows=new Vector <Vector>();
		//����һ��SqlHelper����
		SqlHelper sh=new SqlHelper();
		ResultSet rs=sh.query(sql, paras);	
		
		try {
			//��rs �����п��Եõ�ResultSetMetadata
			//rsmt���Եõ�����ֶ����У����ҿ���֪��ÿһ�е�����
			ResultSetMetaData rsmt=rs.getMetaData();
			for(int i=0; i<rsmt.getColumnCount(); i++)    //֪��Ҫ��ѯҪ��ʾ������
			{
				this.colums.add(rsmt.getColumnName(i+1));  //����ÿһ�е�����
			}
			
			//��rs�Ľ�������뵽ros��
			while(rs.next())
			{
				Vector<String> temp=new Vector<String>();
				for(int i=0; i<rsmt.getColumnCount(); i++)
				{
					temp.add(rs.getString(i+1));   //���ز�ѯ����ֵ
				}
				rows.add(temp);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally{
			sh.close();
		}
	}
	
	public int getColumnCount() {
		
		return this.colums.size();
	}
   
	public int getRowCount() {
		
		return this.rows.size();
	}

	public Object getValueAt(int arg0, int arg1) {
		
		return ((Vector)rows.get(arg0)).get(arg1);
	}

	@Override
	public String getColumnName(int arg0) {
		
		return this.colums.get(arg0).toString();
	}

}
