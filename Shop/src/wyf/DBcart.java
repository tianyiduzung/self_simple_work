package wyf;

import java.sql.*;
import java.util.*;
import javax.sql.*;
import javax.naming.*;

public class DBcart
{
	//定义每页显示商品的数量
	private static int span=2;
	
	public static int getSpan()
	{
		return span;
	}
	
	public static void setSpan(int i)
	{
		span = i;
	}
	
	public static Vector<String> getInfo(String sql)
	{
		Vector<String> vclass = new Vector<String>();
		try
		{
			//初始化上下文
			Context initial = new InitialContext();    
            //其中mysql为数据源jndi名称      	
            DataSource ds = (DataSource)initial.lookup("java:comp/env/jdbc/shops");
            //得到连接
            Connection con=ds.getConnection();
            //声明语句
            Statement st = con.createStatement();
            //执行语句得到结果集
            ResultSet rs = st.executeQuery(sql);
            while(rs.next())
            {
            	String str = rs.getString(1);
            	str = new String(str.getBytes("ISO-8859-1"),"gb2312");
            	vclass.add(str);
            }
            //关闭结果集,语句
            rs.close();
            st.close();
            //归还连接
            con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return vclass;
	}
	
	public static boolean isLegal(String sql)
	{
		boolean flag = false;
		try
		{
			//初始化上下文
			Context initial = new InitialContext();    
            //其中mysql为数据源jndi名称      	
            DataSource ds = (DataSource)initial.lookup("java:comp/env/jdbc/shops");
            //得到连接
            Connection con=ds.getConnection();
            //声明语句
            Statement st = con.createStatement();
            //执行语句得到结果集
            ResultSet rs = st.executeQuery(sql);
            if(rs.next())
            {
            	flag = true;
            }			
            rs.close();
            st.close();
            con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	
	public static int getID(String tname,String colname)
	{
		int id = 0;
		try
		{
			//初始化上下文
			Context initial = new InitialContext();    
            //其中mysql为数据源jndi名称      	
            DataSource ds = (DataSource)initial.lookup("java:comp/env/jdbc/shops");
            //得到连接
            Connection con=ds.getConnection();
            //声明语句
            Statement st = con.createStatement();
            String sql = "select Max("+colname+") from "+tname;
            //执行语句得到结果集
            ResultSet rs = st.executeQuery(sql);
            if(rs.next())
            {
            	id = rs.getInt(1);
            }
            id++;
            rs.close();
            st.close();
            con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return id;
	}
	
	public static int updateTable(String sql)
	{
		int i = 0;
		try
		{
			//初始化上下文
			Context initial = new InitialContext();    
            //其中mysql为数据源jndi名称      	
            DataSource ds = (DataSource)initial.lookup("java:comp/env/jdbc/shops");
            //得到连接
            Connection con=ds.getConnection();
            //声明语句
            Statement st = con.createStatement();
            //更新表
            i = st.executeUpdate(sql);
            //关闭语句
            st.close();
            //归还连接
            con.close();		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return i;
	}
	
	
	public static Vector<String[]> getInfoArr(String sql)
	{
		Vector<String[]> vtemp = new Vector<String[]>();
		try
		{
			//初始化上下文
			Context initial = new InitialContext();    
            //其中mysql为数据源jndi名称      	
            DataSource ds = (DataSource)initial.lookup("java:comp/env/jdbc/shops");
            //得到连接
            Connection con=ds.getConnection();
            //声明语句
            Statement st = con.createStatement();
            //执行语句得到结果集
            ResultSet rs = st.executeQuery(sql);
            //获取结果集的元数据
            ResultSetMetaData rsmt = rs.getMetaData();
            //得到结果集中的总列数
            int count = rsmt.getColumnCount();
            while(rs.next())
            {
            	String[] str = new String[count];
            	for(int i=0;i<count;i++)
            	{
            		str[i] = rs.getString(i+1);
            		str[i] = new String(str[i].getBytes("ISO-8859-1"),"gb2312");
            	}
            	vtemp.add(str);
            }
            rs.close();
            st.close();
            con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return vtemp;
	}

	public static int getTotalPage(String sql)
	{
		int totalpage = 1;
		try
		{
			//初始化上下文
			Context initial = new InitialContext();    
            //其中mysql为数据源jndi名称      	
            DataSource ds = (DataSource)initial.lookup("java:comp/env/jdbc/shops");
            //得到连接
            Connection con=ds.getConnection();
            //声明语句
            Statement st = con.createStatement();
            //执行语句得到结果集
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            //得到总记录条数
            int rows = rs.getInt(1);
            totalpage = rows/span;
            if(rows%span!=0)
            {
            	totalpage++;
            }
            //关闭结果集,语句
            rs.close();
            st.close();
            //归还连接
            con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return totalpage;
	}
	
	public static Vector<String[]> getPageContent(int page,String sql)
	{
		Vector<String[]> vcon = new Vector<String[]>();
		try
		{
			//初始化上下文
			Context initial = new InitialContext();    
            //其中mysql为数据源jndi名称      	
            DataSource ds = (DataSource)initial.lookup("java:comp/env/jdbc/shops");
            //得到连接
            Connection con=ds.getConnection();
            //声明语句
            Statement st = con.createStatement();
			//执行语句得到结果集
            ResultSet rs = st.executeQuery(sql);
            //获取结果集的元数据
            ResultSetMetaData rsmt = rs.getMetaData();
            //得到结果集中的总列数
            int count = rsmt.getColumnCount();
            int start = (page-1)*span;
            if(start!=0)
            {
            	rs.absolute(start);
            }
			int temp=0;
            while(rs.next()&&temp<span)
            {
            	temp++;
            	String[] str = new String[count];
            	for(int i=0;i<str.length;i++)
            	{
            		str[i] = rs.getString(i+1);
            		//转码
            		str[i] = new String(str[i].getBytes("ISO-8859-1"),"gb2312");
            	}
            	vcon.add(str);
            }
            //关闭结果集,语句
            rs.close();
            st.close();
            con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return vcon;
	}
	
	public static int getSelectId(String sql)
	{
		int id = 0;
		try
		{
			//初始化上下文
			Context initial = new InitialContext();    
            //其中mysql为数据源jndi名称      	
            DataSource ds = (DataSource)initial.lookup("java:comp/env/jdbc/shops");
            //得到连接
            Connection con=ds.getConnection();
            //声明语句
            Statement st = con.createStatement();
			sql = new String(sql.getBytes(),"ISO-8859-1");
			//执行语句得到结果集
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            id = rs.getInt(1);
            rs.close();
            st.close();
            con.close();			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return id;
	}
	
	public static boolean batchSql(String[] sql)
	{
		boolean flag = true;
		Connection con = null;
		try
		{
			//初始化上下文
			Context initial = new InitialContext();    
            //其中mysql为数据源jndi名称      	
            DataSource ds = (DataSource)initial.lookup("java:comp/env/jdbc/shops");
            //得到连接
            con=ds.getConnection();
            //禁用自动提交模式,并开始一个事务
            con.setAutoCommit(false);
            //声明语句
            Statement st = con.createStatement();
            for(String str:sql)
            {
            	str = new String(str.getBytes(),"ISO-8859-1");
            	//添加批处理中的sql
            	st.addBatch(str);
            }
            //执行批处理
            st.executeBatch();
            //将事物提交
            con.commit();
            //恢复自动提交模式
            con.setAutoCommit(true);
            st.close();
		}
		catch(Exception e)
		{
			flag = false;
			try
			{
				//事务回滚
				con.rollback();
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
			}
		}
		finally
		{
			try
			{
				con.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	public static String getLevel(String sql)
	{
		String level="";
		try
		{
			//初始化上下文
			Context initial = new InitialContext();    
            //其中mysql为数据源jndi名称      	
            DataSource ds = (DataSource)initial.lookup("java:comp/env/jdbc/shops");
            //得到连接
            Connection con=ds.getConnection();
            //声明语句
            Statement st = con.createStatement();
			//执行语句得到结果集
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            level=rs.getString(2);
            rs.close();
            st.close();
            con.close();			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return level;
	}
}