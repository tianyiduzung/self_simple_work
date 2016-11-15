/**
 * �����û�������ģ�ͣ�������ɶ��û��ĸ��ֲ���[������Ҫ����ҵ�����]
 */
package com.mhl.model;
import com.mhl.db.*;
import java.sql.*;
public class UserModel  {
	
	/**
	 * 
	 * @param uid  �û����
	 * @param p   �û�����	
	 * @return	���û���ְλ  ������û��������򷵻ؿ�
	 */
	
	public String  checkUser(String uid, String p)
	{
		String zhiwei="";
		SqlHelper sp=null;
		try {
			//��֯sql���Ͳ����б�
			//String sql="use RestaurantDB; select clerkInfo.cleZw  from  login, clerkInfo  where login.cleId=clerkInfo.cleId and login.cleId=?  and  login.passwd=?";
			String sql="select clezw from clerkinfo where cleId=? and passwd=?";
			String paras[]={uid, p};
			sp=new SqlHelper();
			ResultSet rs=sp.query(sql, paras);
			if(rs.next())
			{
				//ȡ��ְλ
				zhiwei=rs.getString(1);
			}
		} catch (Exception e) {
			
		}finally{
			sp.close();
		}
		return zhiwei;
	}
	
}
