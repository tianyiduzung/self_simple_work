package Teacher;

import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class ChangePwdTeacher extends JPanel implements ActionListener{

	private String host;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private JLabel[] jlArray={new JLabel("用户名"),new JLabel("原始密码"),
			new JLabel("新密码"),new JLabel("确认新密码")};
	private JTextField jtf=new JTextField();
	private JPasswordField[] jpfArray={new JPasswordField(),new JPasswordField(),
			new JPasswordField()};
	private JButton[] jbArray={new JButton("确认"),new JButton("重置")};
	
	public ChangePwdTeacher(String host){
		this.host=host;
		this.initialFrame();
		this.addListener();
	}
	
	//添加监听器
	public void addListener(){
		jtf.addActionListener(this);
		jpfArray[0].addActionListener(this);
		jpfArray[1].addActionListener(this);
		jpfArray[2].addActionListener(this);
		jbArray[0].addActionListener(this);
		jbArray[1].addActionListener(this);
	}
	
	//初始化页面的方法
	public void initialFrame(){
		this.setLayout(null);
		for(int i=0;i<jlArray.length;i++){
			jlArray[i].setBounds(30, 20+50*i, 150, 30);
			this.add(jlArray[i]);
			if(i==0){
				jtf.setBounds(130, 20+50*i, 150, 30);
				this.add(jtf);
			}else{
				jpfArray[i-1].setBounds(130, 20+50*i, 150, 30);
				this.add(jpfArray[i-1]);
			}
		}
		jbArray[0].setBounds(40, 230, 100, 30);this.add(jbArray[0]);
		jbArray[1].setBounds(170, 230, 100, 30);this.add(jbArray[1]);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jtf)
		{
			jpfArray[0].requestFocus(true);
		}else if(e.getSource()==jpfArray[0])
		{
			jpfArray[1].requestFocus(true);
		}else if(e.getSource()==jpfArray[1])
		{
			jpfArray[2].requestFocus(true);
		}else if(e.getSource()==jpfArray[2])
		{
			jbArray[0].requestFocus(true);
		}else if(e.getSource()==jbArray[1])
		{
			for(int i=0;i<jpfArray.length;i++){
				jpfArray[i].setText("");
				jtf.setText("");
			}
		}else if(e.getSource()==jbArray[0])
		{
			String patternStr="[0-9a-zA-Z]{6,12}";
			String user_name=jtf.getText().trim();
			if(user_name.equals("")){
				JOptionPane.showMessageDialog(this, "请输入用户名",
						"错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			String oldPwd=jpfArray[0].getText().trim();
			if(oldPwd.equals("")){
				JOptionPane.showMessageDialog(this, "请输入原始密码",
						"错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			String newPwd=jpfArray[1].getText().trim();
			if(newPwd.equals("")){
				JOptionPane.showMessageDialog(this, "请输入新密码",
						"错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!newPwd.matches(patternStr)){
				JOptionPane.showMessageDialog(this, "密码只能是6到12位的字母和数字",
						"错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			String newPwd1=jpfArray[2].getText().trim();
			if(!newPwd.equals(newPwd1)){
				JOptionPane.showMessageDialog(this, "两次密码不一致",
						"错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//初始化数据库连接并更改密码
			try{
				this.initialConnection();//得到数据库连接
				String sql="update user_teacher set pwd='"+newPwd+"'"+
						"where uid='"+user_name+"'"+"and pwd='"+oldPwd+"'";
				int i=stmt.executeUpdate(sql);
				if(i==0){
					JOptionPane.showMessageDialog(this, "修改失败，请检查您的用户名或密码是否正确",
							"错误",JOptionPane.ERROR_MESSAGE);
					return;
				}else if(i==1){
					JOptionPane.showMessageDialog(this, "密码修改成功",
							"错误",JOptionPane.ERROR_MESSAGE);
					return;
				}
				this.closeConn();
			}catch(Exception ea){
				ea.printStackTrace();
			}
		}
	}
	
	public void setFocus()
	{
		jtf.requestFocus(true);
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
