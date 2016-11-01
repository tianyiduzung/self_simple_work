
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionFactory 
{
	public Connection getConnection() throws InstantiationException, IllegalAccessException
	{
		String user="root";
		String pwd="123456";
		Connection con=null;
		String url="jdbc:mysql://192.168.1.131:3306/chatdemo";
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}
		catch (ClassNotFoundException ce)
		{
			System.out.println(ce);
		}
		try
		{
			con=DriverManager.getConnection(url,user,pwd);
			//注意捕获异常信息
		}
		catch (SQLException ce)
		{
			System.out.println(ce);
		}
		return con;

	}
}
