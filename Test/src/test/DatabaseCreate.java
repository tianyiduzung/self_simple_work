package test;

import java.sql.*;

public class DatabaseCreate {

	public static void main(String[] args) {
		Connection con=null;//ÉùÃ÷ConnectionÒýÓÃ
		PreparedStatement ps = null;
		ResultSet rs;
		try {
			Class.forName("com.mysql.jdbc.Driver");	
			con=DriverManager.getConnection("jdbc:mysql://localhost/contacts","root","123456");
		 	ps=con.prepareStatement("create database vvv");
		 	boolean b=ps.execute();
		 	if(b){
		 		System.out.println("ok");
		 	}else{
		 		System.out.println("no");
		 	}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				if(ps!=null){
					ps=con.prepareStatement("drop database vvv");
					ps.execute();
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}

	}
}
