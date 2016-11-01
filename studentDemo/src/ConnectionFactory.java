
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionFactory 
{
	public Connection getConnection()
	{
		String user="root";
		String pwd="123456";
		Connection con=null;
		String url="jdbc:mysql://localhost:3306/studb";
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException ce)
		{
			System.out.println(ce);
		}
		try
		{
			con=DriverManager.getConnection(url,user,pwd);
			//ע�Ⲷ���쳣��Ϣ
		}
		catch (SQLException ce)
		{
			System.out.println(ce);
		}
		return con;

	}
}
