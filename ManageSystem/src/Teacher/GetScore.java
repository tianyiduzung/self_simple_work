package Teacher;

import java.sql.*;
import java.util.*;

public class GetScore {

	private String host;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private Vector<Vector> v=new Vector<Vector>();
	private double  xuefen;
	
	public GetScore(String host){
		this.host=host;
	}
	
	//根据学号获得学生已修课程信息的方法
	public Vector getAllScore(String stu_id){
		v.removeAllElements();
		try{
			this.initialConnection();
			String sql="select course.cou_name,grade.score,course.xuefen from"+
					" course,grade where grade.stu_id='"+stu_id+"' and grade.isdual=1"+
					" and grade.cou_id=course.cou_id order by score desc";
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				Vector temp=new Vector();
				String cou_name=new String(rs.getString(1).getBytes("gb2312"));
				String score=rs.getDouble(2)+"";
				String xuefen=rs.getDouble(3)+"";
				temp.add(cou_name);temp.add(score);
				temp.add(xuefen);v.add(temp);
			}
					
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.closeConn();
		}
		return v;
	}
	
	//根据学号获得学生不及格课程的信息
	public Vector getFailScore(String stu_id){
		v.removeAllElements();
		try{
			this.initialConnection();
			String sql="select course.cou_name,grade.score,course.xuefen"+
					" from course,grade where grade.stu_id='"+stu_id+"'"+
					"and grade.cou_id=course.cou_id and score<60 and grade.isdual=1";
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				Vector temp=new Vector();
				String cou_name=new String(rs.getString(1).getBytes("gb2312"));
				String score=rs.getDouble(2)+"";
				String xuefen=rs.getDouble(3)+"";
				temp.add(cou_name);temp.add(score);
				temp.add(xuefen);v.add(temp);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.closeConn();
		}		
		return v;
	}
	
	//根据学号获得所修学分
	public double getXueFen(String stu_id){
		try{
			this.initialConnection();
			String sql="select sum(xuefen) from course,grade where stu_id='"+stu_id+"'"+
					" and grade.cou_id=course.cou_id and grade.score>=60 and isdual=1";
			rs=stmt.executeQuery(sql);
			if(rs.next()){
				xuefen=rs.getDouble(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return xuefen;
	}
	
	
	//自定义的初始化数据库连接的方法
		public void initialConnection(){
			try{
				Class.forName("com.mysql.jdbc.Driver");
				conn=DriverManager.getConnection("jdbc:mysql://"+host+"/managesystem","root","123456");
				stmt=conn.createStatement();
			}catch(SQLException e){
				e.printStackTrace();
			}catch(ClassNotFoundException e){
				e.printStackTrace();
			}
			
		}
		
		//关闭数据库连接的方法
		public void closeConn(){
			try{
				if(rs!=null){
					rs.close();
				}
				if(stmt!=null){
					stmt.close();
				}
				if(conn!=null){
					conn.close();
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
}
