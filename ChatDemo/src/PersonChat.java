import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PersonChat {
	
	/**
	 * 查询所有数据
	 * 
	 * @throws Exception
	 */
	//用于检查是否开启了数据库
	public void readDate() throws Exception{
		ConnectionFactory factory=new ConnectionFactory();
		//得到数据连接
		Connection conn=factory.getConnection();
		//得到语句块
		Statement stmt=conn.createStatement();
		//拼写SQL语句，查询所有的Person
		String sqlstr="select * from zhuce";
		//得到结果集
		ResultSet rs=stmt.executeQuery(sqlstr);
		//遍历结果集，显示结果
		//while(rs.next()){
			//System.out.println(rs.getString(1)+"\t");
			//System.out.println(rs.getString(2)+"\t");
		//}
		//关闭结果集、语句块与连接
		JDBCUtils.close(rs,stmt,conn);
	}
	
	//判断用户名是否存在
	public boolean readDate(String name) throws Exception{
		ConnectionFactory factory=new ConnectionFactory();
		//得到数据连接
		Connection conn=factory.getConnection();
		//得到语句块
		Statement stmt=conn.createStatement();
		//拼写SQL语句，查询所有的Person
		String sqlstr="select name from zhuce where name='"+name+"'";
		//得到结果集
		ResultSet rs=stmt.executeQuery(sqlstr);
		//遍历结果集，显示结果
		while(rs.next()){
			return true;
		}
		//关闭结果集、语句块与连接
		JDBCUtils.close(rs,stmt,conn);
		return false;
	}
	
	//判断密码是否正确
	public boolean readDate(String name,String password) throws Exception{
		ConnectionFactory factory=new ConnectionFactory();
		//得到数据连接
		Connection conn=factory.getConnection();
		//得到语句块
		Statement stmt=conn.createStatement();
		//拼写SQL语句，查询所有的Person
		String sqlstr="select name,password from zhuce where name='"+name+"' and password='"+password+"'";
		//得到结果集
		ResultSet rs=stmt.executeQuery(sqlstr);
		//遍历结果集，显示结果
		while(rs.next()){
			return true;
		}
		//关闭结果集、语句块与连接
		JDBCUtils.close(rs,stmt,conn);
		return false;
	}
	
	/**
	 * 插入一条数据
	 * @name String 名字
	 * @password String 密码
	 * @throws Exception
	 */
	//注册代码，将注册信息上传到数据库
	public void insertDate(String name,String password)
	  throws Exception{
		ConnectionFactory factory=new ConnectionFactory();
		//得到数据连接
		Connection conn=factory.getConnection();
		//拼写一个插入SQL语句
		String sqlstr="insert into zhuce values(?,?)";
		//得到预编译对象
		PreparedStatement psmt=conn.prepareStatement(sqlstr);
		//设置相关的参数值
		psmt.setString(1,name);
		psmt.setString(2, password);
		//执行操作SQL的语句
		psmt.executeUpdate();
		//关闭结果集、语句块与连接
		JDBCUtils.close(psmt,conn);
		//this.readDate();
	}

}
