package student;
import manager.*;
import javax.swing.*;
import java.sql.*;
public class DataBase
{
	Connection con=null;//����Connection����
	Statement stat;
	ResultSet rs;
	int count;
	public static String message;//����һ����̬��Ա����
	public static Login log;
	public DataBase(){
		try{//����MySQL�������࣬���������ݿ�����
			Class.forName("com.mysql.jdbc.Driver");	
			con=DriverManager.getConnection("jdbc:mysql://"+message+"/bookmanage","root","123456");
		 	stat=con.createStatement();//����Statement����
		}
		catch(Exception e){//�����Login�ഫ�Ĳ������ԣ�����ʾ����
    	   	JOptionPane.showMessageDialog(log,"�û�IP��˿ںŴ��󣡣���",
    	   	              "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
		}	
	}	
	public void selectDb(String sql){//����select����
		try{
			rs=stat.executeQuery(sql);
		}
		catch(Exception ie){ie.printStackTrace();}
	}	
	public int updateDb(String sql){//����update����
		try{
//			sql = new String(sql.getBytes(),"ISO-8859-1");//ת��
			count=stat.executeUpdate(sql);
		}
		catch(Exception ie){ie.printStackTrace();}
		return count;		
	}	
	public void dbClose(){//����close����		
		try{con.close();}
		catch(Exception e){e.printStackTrace();}	
	}
}