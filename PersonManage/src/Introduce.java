
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class Introduce extends JPanel implements ActionListener
{
	private JTextArea[] jta=
	{//声明文本区
		new JTextArea(),
		new JTextArea(),
		new JTextArea()	
	};
	private JScrollPane jsp1=new JScrollPane(jta[0]);//将jta[0]添进滚动窗体
    private JScrollPane jsp2=new JScrollPane(jta[1]);
	private JScrollPane jsp3=new JScrollPane(jta[2]);
	DataBase db;
	String sql;
	private JButton[] jb=
	{//声明按钮设置并为其指定文本
	      new JButton("添加简历"),	      
	      new JButton("查    询"),
	      new JButton("清    空")	
	};
	private JLabel jl=new JLabel("请输入职工ID");
	private JLabel[] jl1=
	{//设置标签并为其指定文本
		new JLabel("个人介绍"),
		new JLabel("工作经历"),
		new JLabel("其    他")
	};
	private JTextField jtf=new JTextField();//声明文本框
	public Introduce()
	{
		this.setLayout(null);//设置JPanel为空布局
		for(int i=0;i<3;i++)
		{//初始化文本区
			jta[i].setLineWrap(true);//设置其自动换行              
			this.add(jl1[i]);
			jta[i].append("");
		}
		jsp1.setBounds(20,30,500,155);//设置第一个滚动窗体的大小及位置
		jsp2.setBounds(20,225,500,155);
		jsp3.setBounds(20,420,500,155);
		this.add(jsp1);//将第一个滚动窗体添加进界面
		this.add(jsp2);
		this.add(jsp3);
		for(int i=0;i<3;i++)
		{//初始化按钮并为其添加监听器
			this.add(jb[i]);
			jb[i].addActionListener(this);
			jb[i].setBounds(230+i*100,600,90,25);			
		}
		for(int i=0;i<3;i++)
		{//为标签设置大小及其位置
			jl1[i].setBounds(5,5+i*190,80,20);
		}
		this.add(jl);	
		jl.setBounds(20,600,80,25);
		this.add(jtf);
		jtf.setBounds(100,600,100,25);//设置文本框的大小及位置
		//设置窗体的大小位置及可见性		
		this.setBounds(10,10,550,700);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jb[0])
		{//实现个人简历的添加
			this.insert();
		}
		if(e.getSource()==jb[1])
		{//实现个人简历的查询及显示
			this.search();
		}
		if(e.getSource()==jb[2])
		{
			for(int i=0;i<3;i++)
			{//清空文本区
			    jta[i].setText("");				
			}
		}
	}
	public void insert()
	{
		try
		{
			String[] s1=new String[3];
			for(int i=0;i<3;i++)
			{//得到文本区内容
				s1[i]=jta[i].getText().trim();
			}
			String s=s1[0]+"|"+s1[1]+"|"+s1[2];
			if(jtf.getText().length()!=0)
			{//文本区不为空
				if(! (jtf.getText().trim().matches("^\\d+$")))
	 			{//判断管理员名的格式
					JOptionPane.showMessageDialog(this,	"管理员名只能为数字！！！",
					        "消息",JOptionPane.INFORMATION_MESSAGE);
					return;							
				}
				int in=Integer.parseInt(jtf.getText().trim());
				sql="select EmployeeID from Person where EmployeeID="+in;//确认该职工ID的职工是否存在
			    db=new DataBase();
				db.selectDb(sql);
				if(db.rs.next())
				{
					if(s1[0].equals("")||s1[1].equals("")||s1[2].equals(""))
					{//文本区为空
						JOptionPane.showMessageDialog(this,"请在三个文本区均输入必要信息！！！",
						          "信息",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if(!s.trim().equals("||"))
					{//文本区不为空执行插入操作
						sql="update person set Resume='"+s+"' where EmployeeID="+in;
						db=new DataBase();
						db.updateDb(sql);
						JOptionPane.showMessageDialog(this,"添加成功！！",
						       "消息!!",JOptionPane.INFORMATION_MESSAGE);//添加成功提示
						return;
					}
				}
				else
				{//搜索该职工不存在提示
					JOptionPane.showMessageDialog(this,"本公司没有这名职工！！",
					                 "消息!!",JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
			else
			{//职工ID文本框输入为空进行提示
				JOptionPane.showMessageDialog(this,"请输入职工ID！！",
						  "消息!!",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}					
		catch(Exception e0)
		{//捕获异常
			e0.printStackTrace();
		}		
	}
	public void search()
	{
		try
		{
			if(jtf.getText().length()!=0)
			{//如果职工ID文本框不为空
				if(! (jtf.getText().trim().matches("^\\d+$")))
	 			{//判断管理员名的格式
					JOptionPane.showMessageDialog(this,	"管理员名只能为数字！！！",
					        "消息",JOptionPane.INFORMATION_MESSAGE);
					return;							
				}
				int in=Integer.parseInt(jtf.getText().trim());
				sql="select EmployeeID from Person where EmployeeID="+in;//确认职工身份的合法性
			    db=new DataBase();
				db.selectDb(sql);
				if(db.rs.next())
				{//该职工存在
						sql="select Resume from person where EmployeeID="+in;//查询其个人简历
						db=new DataBase();
						db.selectDb(sql);
						db.rs.next();
						String ss=db.rs.getString(1);
				//注意：此处不能用db.rs.next()来作为判断条件，因为那样不管简历是否填写，都为真
						if(!(ss.equals("")))
						{//便利结果集
							
							String[] s2=ss.split("\\|");//结果集以"|"分隔
							for(int i=0;i<3;i++)
							{//将个人简介不同的内容添加进各自的区域
//								s2[i]=new String(s2[i].getBytes("ISO-8859-1"),"gb2312");
								jta[i].setText(s2[i]);
							}
							JOptionPane.showMessageDialog(this,"查询成功！！",
					               "消息!!",JOptionPane.INFORMATION_MESSAGE);
			                return;
						}
						else
						{//该职工个人简历不详提示
							JOptionPane.showMessageDialog(this,"该职工个人简历不详！！",
						          "消息!!",JOptionPane.INFORMATION_MESSAGE);
						      return;
					    }	
				}
				else
				{//职工身份不合法提示
					JOptionPane.showMessageDialog(this,"本公司没有这名职工！！",
					                 "消息!!",JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
			else
			{//职工ID文本框输入为空
				JOptionPane.showMessageDialog(this,"请输入职工ID！！",
						  "消息!!",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}					
		catch(Exception e0)
		{//捕获异常
			e0.printStackTrace();
		}		
	}
	public static void main(String[]args)
	{
		new Introduce();
	}
	
}
