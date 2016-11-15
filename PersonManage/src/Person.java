
import javax.swing.*;import java.awt.*;
import java.awt.event.*;import javax.swing.table.*;
import java.sql.*;import java.util.*;
import java.util.Date;import java.io.*;
public class Person extends JPanel implements ActionListener{	
	JFileChooser jfc=new JFileChooser();//创建文件选择器	
	Vector listData=new Vector();//创建用来存放列表数据的向量
	String[] str=new String[21];
	DataBase db;
	String sql;
	Vector<String[]> vtemp;//创建字符串向量
	Vector<Image> image;//创建image向量
	int count=0;
	private JLabel[] jlArray={//声明标签并为其指定文本
			new JLabel("职工ID"),new JLabel("姓    名"),new JLabel("性    别"),
			new JLabel("出生年月"),new JLabel("籍    贯"),new JLabel("民    族"),
			new JLabel("政治面貌"),new JLabel("文化程度"),new JLabel("婚姻状况"),
			new JLabel("毕业院校"),new JLabel("所学专业"),new JLabel("个人特长"),
			new JLabel("工    种"),new JLabel("职    务"),new JLabel("部门名称"),
			new JLabel("身份证号"),new JLabel("家庭住址"),new JLabel("邮政编码"),
			new JLabel("联系电话"),new JLabel("Email"),new JLabel("个人资料档案"),
			new JLabel("个人照片"),new JLabel("(住宅电话)"),new JLabel("(手    机)"),
			new JLabel("浏览按钮"),new JLabel("功能按钮"),new JLabel("照片路径")
	};
	private JButton[] jbArray=new JButton[]{//声明按钮并为其指定文本
			new JButton("最前"),new JButton("上一个"),new JButton("下一个"),
			new JButton("最后"),new JButton("添加"),new JButton("删除"),
			new JButton("修改"),new JButton("查询"),new JButton("浏览照片")
	};
	private JTextField[] jtxt={//声明文本框
			new JTextField(),new JTextField(),new JTextField(),new JTextField(),
			new JTextField(),new JTextField(),new JTextField(),new JTextField(),
			new JTextField(),new JTextField(),new JTextField(),new JTextField(),
			new JTextField(),new JTextField(),new JTextField(),new JTextField(),
			new JTextField(),new JTextField(),new JTextField(),new JTextField(),
			new JTextField(),new JTextField()
	};
	private JLabel jl=new JLabel();
	public Person(){
		this.setLayout(null);//设置本窗体为空布局
		for(int i=0;i<23;i++){//初始化标签和文本框
			this.add(jlArray[i]);
			if(i<3){//设置第一行标签和文本框的大小和位置
				jlArray[i].setBounds(50+i*200,60,60,25);
				jtxt[i].setBounds(110+i*200,60,130,25);
			}
			if(i>=3&&i<6){//设置第二行标签和文本框的大小和位置
				jlArray[i].setBounds(50+(i-3)*200,100,60,25);
				jtxt[i].setBounds(110+(i-3)*200,100,130,25);
			}
			if(i>=6&&i<9){//设置第三行标签和文本框的大小位置
				jlArray[i].setBounds(50+(i-6)*200,140,60,25);
				jtxt[i].setBounds(110+(i-6)*200,140,130,25);
			}
			if(i>=9&&i<11){//设置4~6行第一列标签和文本框的大小位置
				jlArray[i].setBounds(50,180+(i-9)*40,60,25);
				jtxt[i].setBounds(110,180+(i-9)*40,530,25);
			}
			jlArray[11].setBounds(50,260,100,25);
			jtxt[11].setBounds(110,260,530,65);
			if(i>=12&&i<16){//设置第七行标签和文本框的大小位置
				jlArray[i].setBounds(50+(i-12)*200,340,60,25);
				jtxt[i].setBounds(110+(i-12)*200,340,130,25);
			}	
		}
		for(int i=0;i<21;i++){jtxt[i].addActionListener(this);}
		this.add(jl);//添加照片所在的标签
		jl.setBounds(645,60,194,250);//设置照片所在的标签的大小位置
		jl.setBorder(BorderFactory.createLineBorder(Color.BLACK));//将JLbel的边框线显现出来
		//以下是对一些分布比较零散的按钮及文本框所进行的初始化
		jlArray[16].setBounds(50,380,60,25);
		jtxt[16].setBounds(110,380,530,25);
		jlArray[17].setBounds(650,380,60,25);
		jtxt[17].setBounds(710,380,130,25);
		jlArray[18].setBounds(50,435,60,25);
		jtxt[18].setBounds(110,420,130,25);
		jtxt[19].setBounds(110,448,130,25);
		jlArray[21].setBounds(725,310,60,25);
		jlArray[22].setBounds(250,420,80,25);
		jlArray[23].setBounds(250,448,80,25);
		jlArray[19].setBounds(350,420,60,25);
		jtxt[20].setBounds(410,420,433,25);
		jlArray[20].setBounds(5,5,100,25);
		this.add(jlArray[23]);
		this.add(jlArray[24]);
		this.add(jlArray[25]);
		this.add(jlArray[26]);
		this.add(jbArray[8]);
		jbArray[8].addActionListener(this);
		jbArray[8].setBounds(750,448,93,25);
		jlArray[26].setBounds(350,448,60,25);
		jtxt[21].setBounds(410,448,330,25);
		jlArray[24].setBounds(10,505,100,20);
		jlArray[25].setBounds(10,555,100,20);
		for(int i=0;i<22;i++){this.add(jtxt[i]);}
		for(int i=0;i<8;i++){//初始化按钮
			this.add(jbArray[i]);
			jbArray[i].addActionListener(this);//为按钮设置事件监听器
			if(i<4){//设置第一行按钮的大小位置
				jbArray[i].setBounds(100+i*200,530,100,25);
			}
			else{//设置第二行按钮的大小位置
				jbArray[i].setBounds(100+(i-4)*200,580,100,25);
			}
		}
		db=new DataBase();
		vtemp=db.getPerson();//调用getPerson方法以获得职工信息	
		image=db.getPic();//获得照片信息
		db.dbClose();
		this.setBounds(5,5,650,500);//设置窗体的大小位置
		this.setVisible(true);		//设置窗体的可见性
	}
	//获取选中图片文件的方法
	public File getPic()
	{//弹出保存对话框
		int i=jfc.showSaveDialog(this);
		if(i==JFileChooser.APPROVE_OPTION)
		{//按下保存按扭
			return jfc.getSelectedFile();	
		}
		return null;
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jbArray[8])
		{ //实现添加照片的功能  
			jfc.showOpenDialog(this);
			if(jfc.getSelectedFile()!=null)
			{//将照片放在一标签中
				jtxt[21].setText(""+jfc.getSelectedFile());
			}
		}
		//以下20行代码实现了键盘易用性	
		if(e.getSource()==jtxt[0]){jtxt[1].requestFocus();}
		if(e.getSource()==jtxt[1]){jtxt[2].requestFocus();}
		if(e.getSource()==jtxt[2]){jtxt[3].requestFocus();}
		if(e.getSource()==jtxt[3]){jtxt[4].requestFocus();}
		if(e.getSource()==jtxt[4]){jtxt[5].requestFocus();}   
		if(e.getSource()==jtxt[5]){jtxt[6].requestFocus();}
		if(e.getSource()==jtxt[6]){jtxt[7].requestFocus();} 
		if(e.getSource()==jtxt[7]){jtxt[8].requestFocus();}
		if(e.getSource()==jtxt[8]){jtxt[9].requestFocus();}
		if(e.getSource()==jtxt[9]){jtxt[10].requestFocus();}
		if(e.getSource()==jtxt[10]){jtxt[11].requestFocus();}
		if(e.getSource()==jtxt[11]){jtxt[12].requestFocus();}
		if(e.getSource()==jtxt[12]){jtxt[13].requestFocus();}
		if(e.getSource()==jtxt[13]){jtxt[14].requestFocus();}
		if(e.getSource()==jtxt[14]){jtxt[15].requestFocus();}   
		if(e.getSource()==jtxt[15]){jtxt[16].requestFocus();}
		if(e.getSource()==jtxt[16]){jtxt[17].requestFocus();} 
		if(e.getSource()==jtxt[17]){jtxt[18].requestFocus();}
		if(e.getSource()==jtxt[18]){jtxt[19].requestFocus();}  
		if(e.getSource()==jtxt[19]){jtxt[20].requestFocus();}   
		if(e.getSource()==jbArray[4])
		{//实现添加职工信息的功能
			this.insertPerson();
		}
		if(e.getSource()==jbArray[5])
		{//实现删除职工信息的功能
			this.deletePerson();
		}
		if(e.getSource()==jbArray[6])
		{//实现修改职工信息的功能
			this.updatePerson();
		}
		if(e.getSource()==jbArray[7])
		{//实现"查询"按钮的提示
			JOptionPane.showMessageDialog(this,"请使用左边树的子节点 '查询' ！！",
					"消息!!",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if(e.getSource()==jbArray[0])
		{//显示数据库中第一条职工信息记录
			this.first();
		}
		if(e.getSource()==jbArray[3])
		{//显示数据库中最后一条职工信息记录
			this.last();
		}
		if(e.getSource()==jbArray[1])
		{//显示当前显示信息的前一条信息记录
			this.before();
		}
		if(e.getSource()==jbArray[2])
		{//显示当前显示信息的下一条信息记录
			this.next();
		}		
	}
	public void before()
	{
		if(count==0)
		{//当到达数据库第一条信息处，将跳到最后一条信息处
			count=vtemp.size()-1;
			String[] str = vtemp.get(count);				
		}
		else
		{//否则，到达上一条记录处
			count--;
		}
		String[] str = vtemp.get(count);
		for(int i=0;i<21;i++)
		{//将信息添进窗体的文本框中
			jtxt[i].setText(str[i]);
		}			
		this.setPic(image.get(count));//显示该职工的照片
	}
	public void next()
	{
		if(count==(vtemp.size()-1))
		{//当到达数据库最后一条信息处，将跳到第一条信息处
			count=0;
			String[] str = vtemp.get(count);
		}
		else
		{//否则到达下一条记录处
			count++;
		}
		String[] str = vtemp.get(count);
		for(int i=0;i<21;i++)
		{//将从数据库查询的信息添进窗体的文本框中
			jtxt[i].setText(str[i]);				
		}			
		this.setPic(image.get(count));//显示职工照片
	}
	public void first()
	{//显示数据库中第一条记录
		String[] str = vtemp.get(0);
		for(int i=0;i<21;i++)
		{//显示到文本框
			jtxt[i].setText(str[i]);
		}
		this.setPic(image.get(0));//显示职工照片	
	}
	public void last()
	{//显示数据库最后一条记录
		String[] str = vtemp.get(vtemp.size()-1);
		for(int i=0;i<21;i++)
		{//将信息显示在窗体的文本框中
			jtxt[i].setText(str[i]);
		}
		this.setPic(image.get(image.size()-1));//显示照片	
	}
	public void setPic(Image Pic)
	{		
		final int width=150;//设置照片默认宽度
		final int height=170;//	设置照片默认高度
		if(Pic!=null)
		{
			int pw=Pic.getWidth(Person.this);//得到照片的实际宽度
			int ph=Pic.getHeight(Person.this);//得到照片的实际高度
			if(pw>ph)
			{//如果图片宽度大于高度按宽度缩放
				Pic=Pic.getScaledInstance(width,width*ph/pw,Image.SCALE_SMOOTH);
			}
			else
			{//如果图片宽度小于高度按高度缩放
				Pic=Pic.getScaledInstance(height*pw/ph,height,Image.SCALE_SMOOTH);
			}
			jl.setIcon(new ImageIcon(Pic));//将照片添加进照片标签
			jl.setHorizontalAlignment(JLabel.CENTER);//照片水平居中
			jl.setVerticalAlignment(JLabel.CENTER);//照片垂直居中
		}
		else
		{//无照片，将在照片标签中显示默认图片
			jl.setIcon(null);
		}		
	}
	public void insertPerson()
	{		
		for(int i=0;i<21;i++)
		{//获得文本框所输入的信息
			str[i]=jtxt[i].getText().trim();
		}
		if(str[0].equals("")&&str[1].equals("")&&str[2].equals("")&&str[3].equals("")
				&&str[4].equals("")&&str[5].equals(""))
		{//必要信息输入为空提示
			JOptionPane.showMessageDialog(this,	"职工信息不能为空！！！",
					"消息",JOptionPane.INFORMATION_MESSAGE);
			return;	
		}
		if(!str[0].equals("")&&!str[1].equals("")&&!str[2].equals("")
				&&!str[3].equals("")&&!str[4].equals("")&&!str[5].equals(""))
		{//添加职工信息进数据库
			int in=Integer.parseInt(jtxt[0].getText().trim());
			sql="insert into Person(EmployeeID,Name,Sex,Date,City,Nation,Polity,"
					+"Culture,Marriage,Graduate,Spec,Speci,Wtype,Duty,Depart,IDcard,"+
					"Address,Postcode,HomePhone,Mobile,Email) values("+in+",'"+str[1]+"','"
					+str[2]+"','"+str[3]+"','"+str[4]+"','"+str[5]+"','"+str[6]+"','"
					+str[7]+"','"+str[8]+"','"+str[9]+"','"+str[10]+"','"+str[11]+"','"
					+str[12]+"','"+str[13]+"','"+str[14]+"','"+str[15]+"','"+str[16]+"','"
					+str[17]+"','"+str[18]+"','"+str[19]+"','"+str[20]+"')";
			db=new DataBase();
			int i=db.updateDb(sql);
			String path=jtxt[21].getText();
			if(!path.equals("")&&i==1)
			{//将照片添加进标签
				db.updatePic(path,in);				
			}
			JOptionPane.showMessageDialog(this,"添加成功！！","消息!!",
					JOptionPane.INFORMATION_MESSAGE);//添加成功提示
			vtemp = db.getPerson();//重新得到联系人信息			                                   
			return;
		}
	}
	public void deletePerson()
	{
		String s=jtxt[0].getText().trim();//得到职工ID
		if(s.equals(""))
		{//未入职工ID
			JOptionPane.showMessageDialog(this,	"职工编号不能为空！！！",
					"消息",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		else if(!s.equals(""))
		{	int cd=Integer.parseInt(s);
		sql="select * from Person where EmployeeID="+cd+"";//查询该职工信息是否存在
		db=new DataBase();
		db.selectDb(sql);
		try
		{
			if(db.rs.next())
			{//结果集不为空执行删除功能
				sql="delete from Person where EmployeeID="+cd+"";
				db=new DataBase();
				db.updateDb(sql);
				JOptionPane.showMessageDialog(this,	"成功删除该职工信息记录！！",
						"消息",JOptionPane.INFORMATION_MESSAGE);
				vtemp = db.getPerson();//重新得到联系人信息
				return;	
			}
			else
			{//弹出提示对话框
				JOptionPane.showMessageDialog(this,	"没有该职工！",
						"消息",JOptionPane.INFORMATION_MESSAGE);
				return;			        
			}
		}
		catch(Exception e)
		{//捕获异常
			e.printStackTrace();
		}		
		}	
	}
	public void updatePerson()
	{
		int in=Integer.parseInt(jtxt[0].getText().trim());//得到职工ID文本框的内容并将其转为int型
		for(int i=0;i<21;i++)
		{//获得文本框所输入的信息
			str[i]=jtxt[i].getText().trim();
		}
		if(!str[0].equals("")&&!str[1].equals("")&&!str[2].equals("")&&!str[3].equals("")
				&&!str[4].equals("")&&!str[5].equals(""))
		{//实现职工信息的更新
			sql="update person set Name='"+str[1]+"',Sex='"
					+str[2]+"',Date='"+str[3]+"',City='"+str[4]+"',Nation='"
					+str[5] +"',Polity='"+str[6]+"',Culture='"+str[7]+"',Marriage='" +str[8] 
							+"',Graduate='"+str[9]+"',Spec='"+str[10]+"',Speci='"+str[11]+
							"',Wtype='"+str[12]+"',Duty='" +str[13]+"',Depart='"+str[14] 
									+"',IDcard='"+str[15] +"',Address='"+str[16]+"',Postcode='"+str[17]
											+"',HomePhone='"+str[18]+"',Mobile='"+str[19]+"',Email='"
											+str[20] +"' where EmployeeID='"+str[0]+"'";	     
			db=new DataBase();
			db.updateDb(sql);
			JOptionPane.showMessageDialog(this,"修改信息成功！！","消息!!",
					JOptionPane.INFORMATION_MESSAGE);//修改成功提示				                               
			return;
		}
		else
		{//输入为空弹出提示对话框
			JOptionPane.showMessageDialog(this,"请输入需要修改的信息！！",
					"消息!!",JOptionPane.INFORMATION_MESSAGE);
			return;
		}								
	}	
	public static void main(String[]args)
	{
		new Person();
	}
}
