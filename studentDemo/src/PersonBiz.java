import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PersonBiz {
	
	/**
	 * ��ѯ��������
	 * 
	 * @throws Exception
	 */
	public void readDate() throws Exception{
		ConnectionFactory factory=new ConnectionFactory();
		//�õ���������
		Connection conn=factory.getConnection();
		//�õ�����
		Statement stmt=conn.createStatement();
		//ƴдSQL��䣬��ѯ���е�Person
		String sqlstr="select * from person";
		//�õ������
		ResultSet rs=stmt.executeQuery(sqlstr);
		//�������������ʾ���
		while(rs.next()){
			System.out.println(rs.getInt(1)+"\t");
			System.out.println(rs.getString(2)+"\t");
			System.out.println(rs.getInt(3)+"\t");
			System.out.println(rs.getString(4)+"\t");
			System.out.println(rs.getString(5)+"\t");
		}
		//�رս����������������
		JDBCUtils.close(rs,stmt,conn);
	}
	
	/**
	 * ����һ������
	 * @name String ����
	 * @id int Ψһ��ʶ
	 * @address String ��ַ
	 * @throws Exception
	 */
	public void insertDate(String name,int id,String address)
	  throws Exception{
		ConnectionFactory factory=new ConnectionFactory();
		//�õ���������
		Connection conn=factory.getConnection();
		//ƴдһ������SQL���
		String sqlstr="insert into person values(?,?,?)";
		//�õ�Ԥ�������
		PreparedStatement psmt=conn.prepareStatement(sqlstr);
		//������صĲ���ֵ
		psmt.setString(1,name);
		psmt.setInt(2, id);
		psmt.setString(3, address);
		//ִ�в���SQL�����
		psmt.executeUpdate();
		//�رս����������������
		JDBCUtils.close(psmt,conn);
		//this.readDate();
	}
	
	/**
	 * ɾ��һ������
	 * @name String����
	 * @throws Exception
	 */
	public void deleteDate(String name) throws Exception{
		ConnectionFactory factory=new ConnectionFactory();
		//�õ���������
		Connection conn=factory.getConnection();
		//�õ�����
		Statement stmt=conn.createStatement();
		//ƴдͨ��nameɾ��һ����¼
		String sqlstr="delete from person where name='"+name+"'";
		//ִ����������SQL���
		stmt.executeUpdate(sqlstr);
		//�رս����������������
		JDBCUtils.close(stmt,conn);
		//this.readDate();
	}
	
	/**
	 * ͨ�������������ݣ���������µ�id��address�Բ�����ʽ����
	 * @name String ����
	 * @id int Ψһ��ʶ
	 * @add String ��ַ
	 * @throws Exception
	 */
	public void updateDate(String name,int id,String add) throws Exception{
		ConnectionFactory factory=new ConnectionFactory();
		//�õ���������
		Connection conn=factory.getConnection();
		//�õ�����
		//ƴд����Person���SQL���
		String sqlstr="update person set id=?,address=? where name='"+name+"'";
		//�õ�Ԥ�������
		PreparedStatement psmt=conn.prepareStatement(sqlstr);
		//������صĲ���ֵ
		psmt.setInt(1, id);
		psmt.setString(2, add);
		//ִ����������SQL���
		psmt.executeUpdate();
		//�رս����������������
		JDBCUtils.close(psmt,conn);
		//this.readDate();
	}
	
	/**
	 * ͨ��ID��ѯһ��person��¼
	 * @id int Ψһ��ʶ
	 * @throws Exception
	 */
	public void selectDate(int id)throws Exception{
		ConnectionFactory factory=new ConnectionFactory();
		//�õ���������
		Connection conn=factory.getConnection();
		//�õ�����
		Statement stmt=conn.createStatement();
		//ƴд����id��ѯperson��SQL���
		String sqlstr="select * from person where id="+id+"";
		//�õ������
		ResultSet rs=stmt.executeQuery(sqlstr);
		//�������������ʾ���
		while(rs.next()){
			System.out.println(rs.getString(1)+"\t");
			System.out.println(rs.getInt(2)+"\t");
			System.out.println(rs.getString(3)+"\t");
		}
		//�رս����������������
		JDBCUtils.close(rs,stmt,conn);
	}
	
	/**
	 * ͨ���������ң������������������
	 * @name String ����
	 * @throws Exception
	 */
	public String[] selectDate(String name) throws Exception{
		ConnectionFactory factory=new ConnectionFactory();
		//�õ���������
		Connection conn=factory.getConnection();
		//�õ�����
		Statement stmt=conn.createStatement();
		String sqlstr="select * from person where name='"+name+"'";
		//�õ������
		ResultSet rs=stmt.executeQuery(sqlstr);
		String[] data=new String[3];
		//����������������������������
		while(rs.next()){		
			data[0]=rs.getString(1);
			data[1]=rs.getString(2);
			data[2]=rs.getString(3);
		}
		//�رս����������������
		JDBCUtils.close(rs,stmt,conn);
		return data;
	}

}








