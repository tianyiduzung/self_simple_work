import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PersonChat {
	
	/**
	 * ��ѯ��������
	 * 
	 * @throws Exception
	 */
	//���ڼ���Ƿ��������ݿ�
	public void readDate() throws Exception{
		ConnectionFactory factory=new ConnectionFactory();
		//�õ���������
		Connection conn=factory.getConnection();
		//�õ�����
		Statement stmt=conn.createStatement();
		//ƴдSQL��䣬��ѯ���е�Person
		String sqlstr="select * from zhuce";
		//�õ������
		ResultSet rs=stmt.executeQuery(sqlstr);
		//�������������ʾ���
		//while(rs.next()){
			//System.out.println(rs.getString(1)+"\t");
			//System.out.println(rs.getString(2)+"\t");
		//}
		//�رս����������������
		JDBCUtils.close(rs,stmt,conn);
	}
	
	//�ж��û����Ƿ����
	public boolean readDate(String name) throws Exception{
		ConnectionFactory factory=new ConnectionFactory();
		//�õ���������
		Connection conn=factory.getConnection();
		//�õ�����
		Statement stmt=conn.createStatement();
		//ƴдSQL��䣬��ѯ���е�Person
		String sqlstr="select name from zhuce where name='"+name+"'";
		//�õ������
		ResultSet rs=stmt.executeQuery(sqlstr);
		//�������������ʾ���
		while(rs.next()){
			return true;
		}
		//�رս����������������
		JDBCUtils.close(rs,stmt,conn);
		return false;
	}
	
	//�ж������Ƿ���ȷ
	public boolean readDate(String name,String password) throws Exception{
		ConnectionFactory factory=new ConnectionFactory();
		//�õ���������
		Connection conn=factory.getConnection();
		//�õ�����
		Statement stmt=conn.createStatement();
		//ƴдSQL��䣬��ѯ���е�Person
		String sqlstr="select name,password from zhuce where name='"+name+"' and password='"+password+"'";
		//�õ������
		ResultSet rs=stmt.executeQuery(sqlstr);
		//�������������ʾ���
		while(rs.next()){
			return true;
		}
		//�رս����������������
		JDBCUtils.close(rs,stmt,conn);
		return false;
	}
	
	/**
	 * ����һ������
	 * @name String ����
	 * @password String ����
	 * @throws Exception
	 */
	//ע����룬��ע����Ϣ�ϴ������ݿ�
	public void insertDate(String name,String password)
	  throws Exception{
		ConnectionFactory factory=new ConnectionFactory();
		//�õ���������
		Connection conn=factory.getConnection();
		//ƴдһ������SQL���
		String sqlstr="insert into zhuce values(?,?)";
		//�õ�Ԥ�������
		PreparedStatement psmt=conn.prepareStatement(sqlstr);
		//������صĲ���ֵ
		psmt.setString(1,name);
		psmt.setString(2, password);
		//ִ�в���SQL�����
		psmt.executeUpdate();
		//�رս����������������
		JDBCUtils.close(psmt,conn);
		//this.readDate();
	}

}
