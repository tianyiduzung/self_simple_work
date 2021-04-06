
import java.sql.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
public class DataBase
{
	Connection con=null;//声明Connection引用
	Statement stat;
	ResultSet rs;
	int count;
	public DataBase()
	{
		try
		{//加载MySQL的驱动类，并创建数据库连接
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/personmanage?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC","root","123456");
			stat=con.createStatement();//创建Statement对象			
		}
		catch(Exception e)
		{//捕获异常，并打印出来
			e.printStackTrace();
		}
	}
	public void selectDb(String sql)
	{//声明select方法
		try
		{
//			佛曰：有必要转码么，都什么时代了！
			//对SQL语句进行转码
//			sql=new String(sql.getBytes(),"ISO-8859-1");
			rs=stat.executeQuery(sql);
		}
		catch(Exception ei)
		{//捕获异常，并打印出来
			ei.printStackTrace();
		}
	}
	public int updateDb(String sql)
	{//声明update方法
		try
		{
//			同上
			//对SQL语句进行转码
//			sql=new String(sql.getBytes(),"ISO-8859-1");
			count=stat.executeUpdate(sql);			
		}
	    catch(Exception ei)
		{//捕获异常，并打印出来
			ei.printStackTrace();
		}
		return count;//返回参数
	}
	public void dbClose()
	{//声明close方法		
		try
		{
			con.close();//执行数据库关闭动作
		}
		catch(Exception e)
		{//捕获异常，并打印出来
			e.printStackTrace();
	    }	
	}
	public int updatePic(String path,int eid)
	{		
		try
		{//创建预编译语句实现将图片存进数据库的功能
			PreparedStatement ps=con.prepareStatement("update Person set Photo=? where EmployeeID="+eid);
			File f=new File(path);		
			int length=(int)f.length();//获取图片的长度
			byte[] b=new byte[length];//创建byte数组其程度为图片文件的长度
			FileInputStream fin=new FileInputStream(f);//创建文件类型字节流对象并为其指定源文件
			fin.read(b);//读取文件存于byte数组中
			fin.close();
			ps.setBytes(1,b);//设置预编译语句中的字段的值
			ps.execute();
			ps.close();
		}
	    catch(Exception ei)
		{//捕获异常，并打印出来
			ei.printStackTrace();
		}
		return count;
	}
	public Vector<String[]> getPerson()
	{//对员工信息进行搜索
		Vector<String[]> vtemp = new Vector<String[]>();
		String sql = "select EmployeeID,Name,Sex,Date,City,Nation,Polity,"+
				"Culture,Marriage,Graduate,Spec,Speci,Wtype,Duty,Depart,"+
				"IDcard,Address,Postcode,HomePhone,Mobile,Email from Person";//执行查询的SQL语句
		try
		{
			rs=stat.executeQuery(sql);
			while(rs.next())
			{//当结果集不为空
				String[] str = new String[21];
				for(int i=0;i<str.length;i++)
				{//得到结果集，并对其进行转码
					str[i] = rs.getString(i+1);
//					一样的，c/s程序转码……太扯淡了
//					str[i] = new String(str[i].getBytes("ISO-8859-1"),"gb2312");
				}
				vtemp.add(str);
			}
		}
		catch(Exception e)
		{//捕获异常，并打印出来
			e.printStackTrace();
		}
		return vtemp;
	}
	public Vector<Image> getRecord ()
{
	Vector<Image> image=new Vector<Image>();//定义照片向量
	try
	{		
		String sql="select Photo from Person";//查询某员工的照片
		rs=stat.executeQuery(sql);
		while(rs.next())
		{//当结果集不为空
			byte[] buff=rs.getBytes(1);
			if(buff!=null)
			{//当结果集不为空就将输入的照片显示
			   image.add((new ImageIcon(buff)).getImage());
			}
			else
			{//否则显示默认图片
				image.add(new ImageIcon("ico.gif").getImage()); 
			} 				
		} 		
	}
	catch(Exception e)
	{//捕获异常，并打印出来
		e.printStackTrace();
	}
	return image; 		
}
	public Vector<Image> getPic()
	{
		Vector<Image> image=new Vector<Image>();//定义照片向量
		Image i=new ImageIcon("ico.gif").getImage();
		try
		{		
			String sql="select Photo from Person";//查询某员工的照片
			rs=stat.executeQuery(sql);
			while(rs.next())
			{//当结果集不为空
				byte[] buff=rs.getBytes(1);
				if(buff!=null)
				{//当输入流不为空就将输入的照片显示
				   image.add((new ImageIcon(buff)).getImage());
				}
				else
				{//否则显示默认图片
					image.add(i); 
				} 				
			} 		
		}
		catch(Exception e)
		{//捕获异常，并打印出来
			e.printStackTrace();
		}
		return image; 		
	}
	public Vector<String[]> getMessage()
	{
		Vector<String[]> v = new Vector<String[]>();
		String sql = "select * from Attend";//执行对Attend表的信息的查询
		try
		{
			rs=stat.executeQuery(sql);
			while(rs.next())
			{//当结果集不为空
				String[] str = new String[8];
				for(int i=0;i<str.length;i++)
				{//得到结果集，并对其进行转码
					str[i] = rs.getString(i+1);
//					不解释
//					str[i] = new String(str[i].getBytes("ISO-8859-1"),"gb2312");
				}
				v.add(str);//将获得的结果集数据，存到一个向量中
			}
		}
		catch(Exception eo)
		{//捕获异常，并打印出来
			eo.printStackTrace();
		}
		return v;
	}
	public Vector<String[]> getWage()
	{
		Vector<String[]> vv= new Vector<String[]>();//定义一个字符串向量
		String sql = "select * from Wage";//执行对Wage表的信息的查询
		try
		{//需要对结果集进行异常的捕获
			rs=stat.executeQuery(sql);
			while(rs.next())
			{//结果集不为空
				String[] str = new String[10];
				for(int i=0;i<str.length;i++)
				{//得到结果集，并对其进行转码
					str[i] = rs.getString(i+1);
//					再也不解释
//					str[i] = new String(str[i].getBytes("ISO-8859-1"),"gb2312");
				}
				vv.add(str);//将获得的结果集数据，存到一个向量中
			}
		}
		catch(Exception eov)
		{//捕获异常，并打印出来
			eov.printStackTrace();
		}
		return vv;
	}
	public static void main(String[] args)
	{
		DataBase db = new DataBase();
		Vector<String[]> vtemp = db.getPerson();
		System.out.println(db.getPic().size());
		
		for(String[] str:vtemp)
		{
			System.out.println(str[0]);
		}
	}
}