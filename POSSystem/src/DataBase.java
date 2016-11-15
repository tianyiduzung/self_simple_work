import java.sql.*;


public class DataBase {

	Connection con=null;
	Statement stat;
	ResultSet rs;
	int count;
	public DataBase(){
		try{
			Class.forName("com.mysql.jdbc.Driver");	
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/possystem","root","123456");
			stat=con.createStatement();//创建Statement对象
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void selectDb(String sql){
		try{
			sql=new String(sql.getBytes(),"gb2312");
			rs=stat.executeQuery(sql);
		}catch(Exception ie){
			ie.printStackTrace();
		}
	}

	public int updateDb(String sql){
		try{
			sql=new String(sql.getBytes(),"gb2312");
			count=stat.executeUpdate(sql);
			con.close();
		}catch(Exception ie){
			ie.printStackTrace();
		}
		return count;
	}

	public void dbClose(){
		try{
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public int[] batchSql(String[] sql){
		int[] im=new int[sql.length];
		try{
			con.setAutoCommit(false);
			for(int i=0;i<sql.length;i++){
				sql[i]=new String(sql[i].getBytes(),"gb2312");
				stat.addBatch(sql[i]);
			}
			im=stat.executeBatch();
			con.commit();
			con.setAutoCommit(true);
		}catch(Exception e){
			e.printStackTrace();
		}
		return im;
	}
}





