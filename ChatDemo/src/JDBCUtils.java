import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

class JDBCUtils 
{
	public static void close(ResultSet rs,Statement stmt,Connection conn)
	{
		close(rs);
		close(stmt);
		close(conn);
	}
	//关闭语句块与连接
	public static void close(Statement stmt,Connection conn)
	{
		close(stmt);
		close(conn);
	}
	//关闭连接
	public static void close(Connection conn)
	{
		try
		{
			if(conn!=null)
			{
				conn.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	//关闭语句块
	public static void close(Statement stmt)
	{
		try
		{
			if(stmt!=null)
			{
				stmt.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	//关闭结果集
	public static void close(ResultSet rs)
	{
		try
		{
			if(rs!=null)
			{
				rs.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
