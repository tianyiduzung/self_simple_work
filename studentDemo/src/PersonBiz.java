import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PersonBiz {
	
	/**
	 * 查询所有数据
	 * 
	 * @throws Exception
	 */
	public void readDate() throws Exception{
		ConnectionFactory factory=new ConnectionFactory();
		//得到数据连接
		Connection conn=factory.getConnection();
		//得到语句块
		Statement stmt=conn.createStatement();
		//拼写SQL语句，查询所有的Person
		String sqlstr="select * from person";
		//得到结果集
		ResultSet rs=stmt.executeQuery(sqlstr);
		//遍历结果集，显示结果
		while(rs.next()){
			System.out.println(rs.getInt(1)+"\t");
			System.out.println(rs.getString(2)+"\t");
			System.out.println(rs.getInt(3)+"\t");
			System.out.println(rs.getString(4)+"\t");
			System.out.println(rs.getString(5)+"\t");
		}
		//关闭结果集、语句块与连接
		JDBCUtils.close(rs,stmt,conn);
	}
	
	/**
	 * 插入一条数据
	 * @name String 名字
	 * @id int 唯一标识
	 * @address String 地址
	 * @throws Exception
	 */
	public void insertDate(String name,int id,String address)
	  throws Exception{
		ConnectionFactory factory=new ConnectionFactory();
		//得到数据连接
		Connection conn=factory.getConnection();
		//拼写一个插入SQL语句
		String sqlstr="insert into person values(?,?,?)";
		//得到预编译对象
		PreparedStatement psmt=conn.prepareStatement(sqlstr);
		//设置相关的参数值
		psmt.setString(1,name);
		psmt.setInt(2, id);
		psmt.setString(3, address);
		//执行操作SQL的语句
		psmt.executeUpdate();
		//关闭结果集、语句块与连接
		JDBCUtils.close(psmt,conn);
		//this.readDate();
	}
	
	/**
	 * 删除一条数据
	 * @name String名字
	 * @throws Exception
	 */
	public void deleteDate(String name) throws Exception{
		ConnectionFactory factory=new ConnectionFactory();
		//得到数据连接
		Connection conn=factory.getConnection();
		//得到语句块
		Statement stmt=conn.createStatement();
		//拼写通过name删除一条记录
		String sqlstr="delete from person where name='"+name+"'";
		//执行所操作的SQL语句
		stmt.executeUpdate(sqlstr);
		//关闭结果集、语句块与连接
		JDBCUtils.close(stmt,conn);
		//this.readDate();
	}
	
	/**
	 * 通过姓名更新数据，并将需更新的id、address以参数形式传入
	 * @name String 名字
	 * @id int 唯一标识
	 * @add String 地址
	 * @throws Exception
	 */
	public void updateDate(String name,int id,String add) throws Exception{
		ConnectionFactory factory=new ConnectionFactory();
		//得到数据连接
		Connection conn=factory.getConnection();
		//得到语句块
		//拼写更新Person表的SQL语句
		String sqlstr="update person set id=?,address=? where name='"+name+"'";
		//得到预编译对象
		PreparedStatement psmt=conn.prepareStatement(sqlstr);
		//设置相关的参数值
		psmt.setInt(1, id);
		psmt.setString(2, add);
		//执行所操作的SQL语句
		psmt.executeUpdate();
		//关闭结果集、语句块与连接
		JDBCUtils.close(psmt,conn);
		//this.readDate();
	}
	
	/**
	 * 通过ID查询一条person记录
	 * @id int 唯一标识
	 * @throws Exception
	 */
	public void selectDate(int id)throws Exception{
		ConnectionFactory factory=new ConnectionFactory();
		//得到数据连接
		Connection conn=factory.getConnection();
		//得到语句块
		Statement stmt=conn.createStatement();
		//拼写根据id查询person的SQL语句
		String sqlstr="select * from person where id="+id+"";
		//得到结果集
		ResultSet rs=stmt.executeQuery(sqlstr);
		//遍历结果集，显示结果
		while(rs.next()){
			System.out.println(rs.getString(1)+"\t");
			System.out.println(rs.getInt(2)+"\t");
			System.out.println(rs.getString(3)+"\t");
		}
		//关闭结果集、语句块与连接
		JDBCUtils.close(rs,stmt,conn);
	}
	
	/**
	 * 通过姓名查找，将结果保存在数组中
	 * @name String 姓名
	 * @throws Exception
	 */
	public String[] selectDate(String name) throws Exception{
		ConnectionFactory factory=new ConnectionFactory();
		//得到数据连接
		Connection conn=factory.getConnection();
		//得到语句块
		Statement stmt=conn.createStatement();
		String sqlstr="select * from person where name='"+name+"'";
		//得到结果集
		ResultSet rs=stmt.executeQuery(sqlstr);
		String[] data=new String[3];
		//遍历结果集，将结果保存在数组中
		while(rs.next()){		
			data[0]=rs.getString(1);
			data[1]=rs.getString(2);
			data[2]=rs.getString(3);
		}
		//关闭结果集、语句块与连接
		JDBCUtils.close(rs,stmt,conn);
		return data;
	}

}








