/**
 * 这是人事的数据模型类，主要是完成对人事表的各种操作
 */
package com.mhl.model;
import javax.swing.table.*;
import java.util.*;
import com.mhl.db.*;
import java.sql.*;

public class EmpModel extends AbstractTableModel {

	Vector<String>colums;
	Vector<Vector>rows;
	
	
	//提供一个通过员工号来删除员工
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
	
	//写一个方法，用于查询需要显示的人事信息，做一个通用性的query
	public void query(String sql, String paras[])
	{
		//初始化列
		this.colums=new Vector<String >();
		
		
		this.rows=new Vector <Vector>();
		//创建一个SqlHelper对象
		SqlHelper sh=new SqlHelper();
		ResultSet rs=sh.query(sql, paras);	
		
		try {
			//从rs 对象中可以得到ResultSetMetadata
			//rsmt可以得到结果又多少列，而且可以知道每一列的名字
			ResultSetMetaData rsmt=rs.getMetaData();
			for(int i=0; i<rsmt.getColumnCount(); i++)    //知道要查询要显示多少列
			{
				this.colums.add(rsmt.getColumnName(i+1));  //返回每一列的名字
			}
			
			//把rs的结果集放入到ros中
			while(rs.next())
			{
				Vector<String> temp=new Vector<String>();
				for(int i=0; i<rsmt.getColumnCount(); i++)
				{
					temp.add(rs.getString(i+1));   //返回查询到的值
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
