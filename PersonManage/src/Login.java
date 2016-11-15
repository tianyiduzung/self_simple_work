
import javax.swing.*;import java.awt.*;
import java.awt.event.*;import javax.swing.table.*;
import javax.swing.event.*;import java.sql.*;
import java.util.*;import java.util.Date;
public class Login extends JFrame implements ActionListener{
	private JPanel jp=new JPanel();//创建JPanel对象
    private JLabel []jlArray={new JLabel("用户名"),new JLabel("密  码")}; //创建标签数组
    private JButton[] jbArray={new JButton("登录"),new JButton("清空")};//创建按钮数组
    private JTextField jtxt=new JTextField("wyf");//创建文本框 
    private JPasswordField jpassword=new JPasswordField("hxl");//创建密码框
    String sql;
    DataBase db;
    public Login(){
    	jp.setLayout(null);	
    	for(int i=0;i<2;i++){//设置标签的大小和位置
        	jlArray[i].setBounds(70,20+i*50,80,26);
        	jp.add(jlArray[i]);
        }
        for(int i=0;i<2;i++){
        	jbArray[i].setBounds(80+i*120,130,100,26);//设置按钮的大小位置
        	jp.add(jbArray[i]);	
        	jbArray[i].addActionListener(this);//为按钮注册动作事件监听器       
        }  
        jtxt.setBounds(130,20,180,30);//设置文本框的大小位置
        jp.add(jtxt);//将文本框添加进JPanel容器 
        jtxt.addActionListener(this);//为文本框注册事件监听器 
        jpassword.setBounds(130,70,180,30);//设置密码框的大小位置
        jp.add(jpassword);//将密码框添加进JPanel容器 
        jpassword.setEchoChar('*');//设置密码框的 回显字符 
        jpassword.addActionListener(this);//为密码框注册监听器
        this.add(jp);
 		Image image=new ImageIcon("ico.gif").getImage(); //对logo图片进行初始化	  
 		this.setIconImage(image);
        this.setTitle("登录");
        this.setResizable(false);
        this.setBounds(100,100,400,300);//设置窗体的大小位置
        this.setVisible(true);//设置窗体的可见性
    }
	public void actionPerformed(ActionEvent e){
		String mgno=jtxt.getText().trim();
	    if(e.getSource()==jtxt){//切换输入焦点到密码框
			jpassword.requestFocus();
		}
		else if(e.getSource()==jbArray[1]){//单击"清空"按钮，将清空界面上的所有信息
			jtxt.setText("");
			jpassword.setText("");
			jtxt.requestFocus();//将输入焦点设置到文本框
		}
		else if(e.getSource()==jbArray[0]){//事件源为管理员登录按钮
	    	String no=jtxt.getText().trim();
		    if(jtxt.getText().trim().equals("")){//用户名提示为空
				JOptionPane.showMessageDialog(this,"用户名不能为空！","信息",JOptionPane.INFORMATION_MESSAGE);
		    	return;
			}
			if(jpassword.getText().trim().equals("")){//用户密码为空提示
				JOptionPane.showMessageDialog(this,"用户密码不能为空！","信息",JOptionPane.INFORMATION_MESSAGE);
		    	return;
			}
	        sql="select mgNo,password from man where mgNo='"+no+"'";//验证登录者的身份和权限
	        db=new DataBase();
			db.selectDb(sql);
			try{
				String mgNo="wyf";
				String password="hxl";
				while(db.rs.next()){//获得结果集信息
				     mgNo=db.rs.getString(1).trim();
			         password=db.rs.getString(2).trim();					
				}
	        	if(jtxt.getText().trim().equals(mgNo)&&
	    		String.valueOf(jpassword.getPassword()).equals(password)){//登录成功提示
	    			JOptionPane.showMessageDialog(this,"恭喜您，登录成功！！！","信息",
	    			     JOptionPane.INFORMATION_MESSAGE);
	    			new Root();
	    			this.dispose();   			
	    		}
	    		else{//登录失败提示
	    			JOptionPane.showMessageDialog(this,"登录失败！","信息",
	    			     JOptionPane.INFORMATION_MESSAGE);
	    	        return;  
	    		}
			}
		    catch(Exception e1){e1.printStackTrace();}  
		    db.dbClose();//关闭数据库链接   
		}
	}
    public static void main(String[]args)
    {
    	new Login();
    }
}