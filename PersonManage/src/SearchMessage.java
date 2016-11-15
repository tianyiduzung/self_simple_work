
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class SearchMessage extends JPanel implements ActionListener{
	int flag,fla;
	String sql;
	DataBase db;
    //创建分割方向为上下的JSplitePane对象
    private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
	private JPanel jpt=new JPanel();//创建JPanel对象
	private JPanel jpb=new JPanel();
	//创建表示下拉列表框数据模型的字符串数组
	private String[] str={"职工ID","姓名","工种","身份证号"};
	private JComboBox jcb=new JComboBox(str);//创建下拉列表框
	private JButton jb=new JButton("提交");	//创建按钮
	private JButton jb1=new JButton("清空");	
	private JLabel[] jlArray=new JLabel[]{
		new JLabel("       姓    名"),
		new JLabel("     工    种"),
		new JLabel("文凭")
	};	
	private JTextField[] jtxtArray=new JTextField[]{//创建文本框
		new JTextField(),new JTextField(),
	    new JTextField(),new JTextField()
	};
	private JRadioButton[] jrbArray={//创建单选按钮
		new JRadioButton("简单查询",true),
		new JRadioButton("高级查询")
	};	
	private ButtonGroup bg=new ButtonGroup();//创建按钮组
	Vector<String> head = new Vector<String>();
	{//定义表头
		head.add("职工ID");head.add("姓名");
		head.add("性别");head.add("出生年月");
		head.add("籍贯");head.add("民族");
		head.add("政治面貌");head.add("文凭");
		head.add("婚姻状况");head.add("毕业学校");
		head.add("所学专业");head.add("个人特长");
		head.add("工种");head.add("职务");
		head.add("部门名称");head.add("身份证号");
		head.add("家庭住址");head.add("邮政编码");
		head.add("住宅电话");head.add("手机");
		head.add("Email");
	}
	Vector<Vector> data=new Vector<Vector>();//定义检索出的的基本信息
    DefaultTableModel dtm=new DefaultTableModel(data,head);	//创建表格模型
	JTable jt=new JTable(dtm); //创建Jtable对象
	JScrollPane jspn=new JScrollPane(jt);//将JTable封装到滚动窗格
	public SearchMessage(){
		this.setLayout(new GridLayout(1,1));//设置界面为网格布局
		jpt.setLayout(null);//设置整个界面上下部分均为空布局管理器
		jpb.setLayout(null);
		jpt.add(jcb);
		jcb.setBounds(160,20,150,20);//设置单选框的大小、位置	
	    jcb.addActionListener(this);//为单选框添加事件监听器
		jpt.add(jb);
		jb.setBounds(510,20,80,20); //设置JButton其大小位置
		jb.addActionListener(this);
		jpt.add(jb1);
		jb1.setBounds(600,20,80,20);
		jb1.addActionListener(this);
		for(int i=0;i<2;i++){//对单选按钮进行设置
			jrbArray[i].setBounds(20,20+i*40,100,20);
			jpt.add(jrbArray[i]);
			jrbArray[i].addActionListener(this);
			bg.add(jrbArray[i]);
		}
		for(int i=0;i<3;i++){//设置标签和文本框的坐标，并将其添加进JPanel
			jlArray[i].setBounds(120+i*200,60,80,20);
			jtxtArray[i].setBounds(200+i*180,60,120,20);
			jpt.add(jtxtArray[i]);	
			jpt.add(jlArray[i]);
		}
		for(int i=0;i<3;i++){//设置文本框为不可用
			jtxtArray[i].setEditable(false);
		}
    	//设置文本框的坐标,并添加进jpt
		jtxtArray[3].setBounds(350,20,120,20);
		jpt.add(jtxtArray[3]);
        jsp.setTopComponent(jpt);//把jpt设置到jsp的上部窗格
        jsp.setBottomComponent(jspn);
        jsp.setDividerSize(4);
       	this.add(jsp);
    	jsp.setDividerLocation(100);//设置jsp中分割条的初始位置
		//设置窗体的大小位置及可见性
        this.setBounds(3,10,600,400);
        this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==jb1)
		{
			for(int i=0;i<4;i++)
			{//清空文本框
				jtxtArray[i].setText("");
			}
	        int r=dtm.getRowCount();
	        for(int p=0;p<r;p++)
	        {//清空表格
	        	dtm.removeRow(p);
	        }
		}
	    if(jrbArray[0].isSelected())
	    {//"简单查询"单选按钮被选中
	    	jtxtArray[3].setEditable(true);
	    	for(int i=0;i<jtxtArray.length-1;i++)
	    	{//设置文本框为不可编辑
	    		jtxtArray[i].setEditable(false);
	    	}
	    	if(jcb.getSelectedIndex()>=0&&jcb.getSelectedIndex()<4)
	    	{
		    	jtxtArray[3].requestFocus();	    
			    if(e.getSource()==jb)
			    {
			    	int ba=this.simplesearch();
			    	if(ba!=0){return;}
			    	db=new DataBase();
			    	db.selectDb(sql);
					this.table();//显示查询结果			
				}
	        }
	    }
		if(jrbArray[1].isSelected())
		{//"高级查询"单选按钮被选中
			 jtxtArray[0].requestFocus(); //获得输入焦点
			 jtxtArray[3].setEditable(false);
	       	 for(int i=0;i<jtxtArray.length-1;i++)
	       	 {//将高级查询所涉及的文本框设为可编辑
	    		jtxtArray[i].setEditable(true);
	    	 }
			 if(e.getSource()==jb)
			 {//点击"提交"按钮
			 	int bz=this.seniorSearch();
			 	if(bz!=0){return;}
			 	db=new DataBase();
				db.selectDb(sql);
				this.table();		
			 } 	
		}    
	}
	public int simplesearch()
	{//如果事件源为"提交"按钮，则执行检索
		int fla=0;
		String str=jtxtArray[3].getText().trim();
		if(str.equals(""))
		{
			JOptionPane.showMessageDialog(this,"请输入必要的信息！！！",
							"消息",JOptionPane.INFORMATION_MESSAGE);
			fla++;
		}
	    if(jcb.getSelectedIndex()==0)
	    {//根据职工ID进行查询
			sql="select * from Person where EmployeeID='"+str+"'";
	    	jtxtArray[3].setText("");
		}
		else if(jcb.getSelectedIndex()==1)
		{//根据姓名进行查询
			sql="select * from Person where Name='"+str+"'";
			jtxtArray[3].setText("");
		}
		else if(jcb.getSelectedIndex()==2)
		{//根据工种进行查询
			sql="select * from Person where Wtype='"+str+"'";
			jtxtArray[3].setText("");
		}
		else
		{//根据身份证号进行查询
			sql="select * from Person where IDcard='"+str+"'";
			jtxtArray[3].setText("");
		}
		return fla;
//		try
//		{//进行转码
//			sql = new String(sql.getBytes(),"ISO-8859-1");
//		}
//		catch(Exception ae){ae.printStackTrace();}
		
	}
	public int seniorSearch()
	{
		int flag=0;//设置标志位
		String str0=jtxtArray[0].getText().trim();
		String str1=jtxtArray[1].getText().trim();
		String str2=jtxtArray[2].getText().trim();
		if(str0.equals("")&&str1.equals("")&&str2.equals("")){//文本框输入为空
			JOptionPane.showMessageDialog(this,"请输入必要的信息！！！",
								"消息",JOptionPane.INFORMATION_MESSAGE);
			flag++;
		}
		if(((!str0.equals(""))&&(str1.equals(""))&&(str2.equals("")))
		     ||((str0.equals(""))&&(!str1.equals(""))&&(str2.equals("")))
		     ||((str0.equals(""))&&(str1.equals(""))&&(!str2.equals(""))))
		{
			JOptionPane.showMessageDialog(this,"请使用简单查询！！！",
								"消息",JOptionPane.INFORMATION_MESSAGE);
			flag++;
		}
	    if((!str0.equals(""))&&(!str1.equals(""))&&(str2.equals("")))
	    {//姓名和工种组合
			sql="select * from Person where Name='"+str0+"' and Wtype='"+str1+"'";
			jtxtArray[0].setText("");jtxtArray[1].setText("");
		}
		if((!str0.equals(""))&&(str1.equals(""))&&(!str2.equals("")))
		{//姓名和文化程度组合
			sql="select * from Person where Name='"+str0+"' and Culture='"+str2+"'";
			jtxtArray[0].setText("");jtxtArray[2].setText("");
		}
		if((str0.equals(""))&&(!str1.equals(""))&&(!str2.equals("")))
		{//工种和文化程度组合
			sql="select * from Person where Wtype='"+str1+"' and Culture='"+str2+"'";
			jtxtArray[1].setText("");jtxtArray[2].setText("");
		}
		if((!str0.equals(""))&&(!str1.equals(""))&&(!str2.equals("")))
		{//三者组合
			sql="select * from Person where Name='"+str0
						+"' and Culture='"+str2+"' and Wtype='"+str1+"'";
			jtxtArray[0].setText("");jtxtArray[1].setText("");jtxtArray[2].setText("");
		}
		return flag;
	}
	public void table()
	{//从表中检索成功后，把查到的的所有信息显示在界面下部分的表中     
		try
		{
			 Vector <Vector> v=new Vector<Vector>();
		     while(db.rs.next())
		     {
		       	 Vector <String> vtemp = new Vector<String>();
			     for(int i=0;i<21;i++)
			     {//取到结果集   
//			    	 字符转换
//			    	 String str1=new String(db.rs.getString(i+1).getBytes("ISO-8859-1"),"gb2312");
			         String str1=new String(db.rs.getString(i+1).getBytes());
			         vtemp.add(str1);//将每列添加到临时数组v
			     }
			     v.add(vtemp);//将各条记录添加到表里							
		     }
		     if(v.size()==0)
		     {//查询失败，则弹出提示对话框
		     	JOptionPane.showMessageDialog(this,"您所查询的职工信息不存在！！","提示",
			     JOptionPane.INFORMATION_MESSAGE);
			    return;
		     }
			dtm.setDataVector(v,head);//更新table
			jt.updateUI();
		    jt.repaint();
		    JOptionPane.showMessageDialog(this,"您已经成功查询该职工信息！！","提示",
					     JOptionPane.INFORMATION_MESSAGE);//查询成功，则弹出提示对话框
			return;        
		 }
	     catch(Exception e)
	     {//捕获异常
		     e.printStackTrace();
	     }
	}
	public static void main(String[]args)
	{
		new SearchMessage();
	}
}