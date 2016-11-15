package Teacher;

import java.awt.event.*;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.*;
import java.util.Date;

import javax.swing.*;

public class NewStu extends JPanel implements ActionListener {

	private String host;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private String coll_id;	//学院号
	
	//存放学院名称和学院号，键是学院名，值是学院号
	private Map<String,String>	map_college=new HashMap<String, String>();
	//存放专业名和专业号,键是专业名，值是专业号
	private Map<String,String> map_dept=new HashMap<String,String>();
	//存放专业号和班的集合，班的集合也用Map，键是班名，值是班号
	private Map<String,Map> map_class=new HashMap<String,Map>();
	static int year=new Date().getYear()+1900;
	private JLabel[] jlArray={new JLabel("学    号"),new JLabel("姓    名"),
			new JLabel("性    别"),new JLabel("出生日期"),new JLabel("籍    贯"),
			new JLabel("学    院"),new JLabel("专    业"),new JLabel("班    级"),
			new JLabel("入学时间"),new JLabel("年"),new JLabel("月"),
			new JLabel("日"),new JLabel("年"),new JLabel("月"),new JLabel("日")};
	private JTextField[] jtfArray={new JTextField(),new JTextField(),new JTextField()};
	String[] str_gender={"男","女"};
	//创建存放出生年号的数组
	static String[] str_year1=new String[20];
	static{
		for(int i=15;i<35;i++)
		{
			str_year1[i-15]=year-i+"";
		}
	}
	//创建入学年号的数组
	String[] str_year={year+"",year-1+"",year-2+"",year-3+"",year-4+"",year-5+""};
	static String[] str_month=new String[12];
	//初始化月份数组
	static{
		for(int i=1;i<=12;i++)
		{
			str_month[i-1]=i+"";
		}
	}
	static String[] str_day=new String[31];
	static{
		for(int i=1;i<=31;i++)
		{
			str_day[i-1]=i+"";
		}
	}
	
	private JComboBox[] jcb={new JComboBox(str_gender),new JComboBox(str_year1),
			new JComboBox(str_month),new JComboBox(str_day),
			new JComboBox(),new JComboBox(),new JComboBox(),
			new JComboBox(str_year),new JComboBox(str_month),
			new JComboBox(str_day)};
	JButton[] jbArray={new JButton("提交"),new JButton("重置")};
	
	public NewStu(String coll_id,String host){
		this.host=host;
		this.coll_id=coll_id;
		this.initialData();
		this.initialFrame();
		this.addListener();
	}
	
	
	public void initialData(){
		try{
			this.initialConnection();		
			//查询数据库获得当前用户所属学院的名称
			String sql1="select coll_name from college where coll_id='"+coll_id+"'";
			rs=stmt.executeQuery(sql1);
			if(rs.next()){
				String coll_name=new String(rs.getString(1).getBytes("gb2312"));
				jcb[4].addItem(coll_name);
			}
			rs.close();
			//查询数据库，获得专业名与专业号，存入map_dept
			String sql2="select dept_name,dept_id from dept where " +
					"coll_id='"+coll_id+"'";
			rs=stmt.executeQuery(sql2);
			while(rs.next()){
				String  dept_name=new String(rs.getString(1).getBytes("gb2312"));
				String dept_id=rs.getString(2);
				map_dept.put(dept_name, dept_id);
			}
			rs.close();
			Set keyset=map_dept.keySet();
			Iterator ii=keyset.iterator();
			int i=0;
			String initial_dept_name=null;
			while(ii.hasNext()){
				//根据各个专业号获得该专业班的信息存入map_class
				String dept_name=(String)ii.next();
				if(i==0){initial_dept_name=dept_name;}
				jcb[5].addItem(dept_name);//将班级名称加入下拉列表
				String dept_id=map_dept.get(dept_name);
				String sql3="select class_id,class_name from class where dept_id='"+dept_id+"'";
				rs=stmt.executeQuery(sql3);
				Map class_map=new HashMap();
				while(rs.next()){
					String class_id=rs.getString(1);
					String class_name=new String(rs.getString(2).getBytes("gb2312"));
					class_map.put(class_name, class_id);
				}
				rs.close();
				map_class.put(dept_id, class_map);
				i++;
			}
			this.closeConn();
			jcb[5].setSelectedItem(initial_dept_name);
			String initial_dept_id=map_dept.get(initial_dept_name);
			Map classmap=(HashMap)map_class.get(initial_dept_id);
			Set keyset1=classmap.keySet();
			Iterator ii1=keyset1.iterator();
			while(ii1.hasNext()){
				String s=(String)ii1.next();
				jcb[6].addItem(s);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	public void initialFrame(){
		this.setLayout(null);
		//学号
		jlArray[0].setBounds(30, 50, 100, 30);
		this.add(jlArray[0]);//添加学好标签
		jtfArray[0].setBounds(180, 50, 150, 30);
		this.add(jtfArray[0]);//添加学号区域输入框
		//姓名
		jlArray[1].setBounds(30, 100, 100, 30);
		this.add(jlArray[1]);//添加姓名标签
		jtfArray[1].setBounds(180, 100, 150, 30);
		this.add(jtfArray[1]);//添加姓名区域输入框
		//性别
		jlArray[2].setBounds(30, 150, 100, 30);
		this.add(jlArray[2]);//性别标签
		jcb[0].setBounds(180,150,50,30);
		this.add(jcb[0]);
		//出生日期
		jlArray[3].setBounds(30, 200, 100, 30);
		this.add(jlArray[3]);//出生日期标签
		jcb[1].setBounds(180,200,80,30);
		this.add(jcb[1]);//年份框
		jlArray[9].setBounds(260, 200, 20, 30);
		this.add(jlArray[9]);//年标签
		jcb[2].setBounds(280,200,50,30);
		this.add(jcb[2]);//月份框
		jlArray[10].setBounds(330, 200, 20, 30);
		this.add(jlArray[10]);//月标签
		jcb[3].setBounds(350,200,50,30);
		this.add(jcb[3]);//日框
		jlArray[11].setBounds(4000, 200, 20, 30);
		this.add(jlArray[11]);//日标签
		//籍贯
		jlArray[4].setBounds(30, 250, 100, 30);
		this.add(jlArray[4]);//籍贯标签
		jtfArray[2].setBounds(180, 250, 150, 30);
		this.add(jtfArray[2]);//籍贯输入框
		//学院
		jlArray[5].setBounds(30, 300, 100, 30);
		this.add(jlArray[5]);//学院标签
		jcb[4].setBounds(180,300,200,30);
		this.add(jcb[4]);//学院框
		//专业
		jlArray[6].setBounds(30, 350, 100, 30);
		this.add(jlArray[6]);
		jcb[5].setBounds(180,350,200,30);
		this.add(jcb[5]);//专业框
		//班级
		jlArray[7].setBounds(30, 400, 100, 30);
		this.add(jlArray[7]);
		jcb[6].setBounds(180,400,200,30);
		this.add(jcb[6]);//班级框
		//入学日期
		jlArray[8].setBounds(30, 450, 100, 30);
		this.add(jlArray[8]);//入学标签
		jcb[7].setBounds(180,450,80,30);
		this.add(jcb[7]);//年框
		jlArray[12].setBounds(260, 450, 20, 30);
		this.add(jlArray[12]);//年标签
		jcb[8].setBounds(280,450,50,30);
		this.add(jcb[8]);//月份框
		jlArray[13].setBounds(330, 450, 20, 30);
		this.add(jlArray[13]);//月标签
		jcb[9].setBounds(350,450,50,30);
		this.add(jcb[9]);//日框
		jlArray[14].setBounds(400, 450, 20, 30);
		this.add(jlArray[14]);//日标签
		
		//提交按钮
		jbArray[0].setBounds(180, 510, 100, 30);
		this.add(jbArray[0]);
		//重置按钮
		jbArray[1].setBounds(320, 510, 80, 30);
		this.add(jbArray[1]);
		
	}
	
	public void addListener(){
		jtfArray[0].addActionListener(this);
		jtfArray[1].addActionListener(this);
		jbArray[0].addActionListener(this);
		jbArray[1].addActionListener(this);
		jcb[5].addActionListener(this);
		jcb[4].addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jcb[5]){
			String deptname=(String)jcb[5].getSelectedItem();//处理专业变化
			String deptid=map_dept.get(deptname);
			Map classmap=(HashMap)map_class.get(deptid);
			//遍历班的map(classmap)，更新班的下拉列表信息
			Set keyset=classmap.keySet();
			Iterator ii=keyset.iterator();
			jcb[6].removeAllItems();
			while(ii.hasNext()){
				String s=(String)ii.next();
				jcb[6].addItem(s);
			}
		}else if(e.getSource()==this.jbArray[0]){
			this.submitStu();
		}else if(e.getSource()==this.jbArray[1]){
			for(int i=0;i<jtfArray.length;i++){
				jtfArray[i].setText("");
			}
		}else if(e.getSource()==jtfArray[0]){
			jtfArray[1].requestFocus(true);
		}else if(e.getSource()==jtfArray[1]){
			jcb[0].requestFocus();
		}
	}
	
	//提交按钮功能实现
	public void submitStu(){
		String stu_id=jtfArray[0].getText().trim();
		String patternStr="[0-9]{10}";
		if(stu_id.equals("")){
			JOptionPane.showMessageDialog(this, "请输入学号","错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(!stu_id.matches(patternStr)){
			JOptionPane.showMessageDialog(this, "学号必须是10数字","错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		String stu_name=jtfArray[1].getText().trim();
		if(stu_name.equals("")){
			JOptionPane.showMessageDialog(this, "请输入姓名","错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(stu_name.length()>10){
			JOptionPane.showMessageDialog(this, "输入姓名过长，请检查是否正确","错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		String stu_gender=((String)jcb[0].getSelectedItem()).trim();
		String bir_year=((String)jcb[1].getSelectedItem()).trim();
		String bir_month=((String)jcb[2].getSelectedItem()).trim();
		String bir_day=((String)jcb[3].getSelectedItem()).trim();
		String stu_birth=bir_year+"-"+bir_month+"-"+bir_day;
		
		String nativeplace=jtfArray[2].getText().trim();
		if(nativeplace.equals("")){
			JOptionPane.showMessageDialog(this, "输入籍贯","错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(nativeplace.length()>30){
			JOptionPane.showMessageDialog(this, "籍贯字数过长，不要超过30字！","错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		String coll_id=this.coll_id;//获得学院号
		//获得专业号
		String dept_id=map_dept.get((String)jcb[5].getSelectedItem());
		//获得学号
		String class_id=(String)(((HashMap)map_class.get(dept_id)).get(jcb[6].getSelectedItem()));
		
		String come_year=(String)jcb[7].getSelectedItem();
		String come_month=(String)jcb[8].getSelectedItem();
		String come_day=(String)jcb[9].getSelectedItem();
		String cometime=come_year+"-"+come_month+"-"+come_day;
		
		this.initialConnection();
		try{
			String sql="insert into student values('"+stu_id+"'," +
					"'"+new String(stu_name.getBytes(),"gb2312")+"',"+
					"'"+new String(stu_gender.getBytes(),"gb2312")+"',"+
					"'"+stu_birth+"',"+
					"'"+new String(nativeplace.getBytes(),"gb2312")+"',"+
					"'"+coll_id+"','"+dept_id+"','"+class_id+"',"+
					"'"+cometime+"')";
			conn.setAutoCommit(false);
			int i=stmt.executeUpdate(sql);
			String sql1="insert into user_stu values('"+stu_id+"','"+stu_id+"')";
			
			int j=stmt.executeUpdate(sql1);
			if(i==1&&j==1){
				conn.commit();
				conn.setAutoCommit(true);
				JOptionPane.showMessageDialog(this, "添加成功","提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}else{
				conn.rollback();
				conn.setAutoCommit(true);
				JOptionPane.showMessageDialog(this, "添加失败！","错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}catch(SQLException ea){
			ea.printStackTrace();
		}catch(UnsupportedEncodingException eb){
			eb.printStackTrace();
		}
		finally{
			this.closeConn();
		}
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
		public void setFocus(){
			this.jtfArray[0].requestFocus(true);
		}
		


}
