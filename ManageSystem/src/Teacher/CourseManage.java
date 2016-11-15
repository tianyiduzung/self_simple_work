package Teacher;

import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

public class CourseManage extends JPanel implements ActionListener{

	private String host;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private String coll_id;
	//创建存放课程表格表头的Vector
	private Vector<String> columnNames1=new Vector<String>();
	//创建存放可选课程表格表头的Vector
	private Vector<String> columnNames2=new Vector<String>();
	//创建存放课程表格数据的Vector
	private Vector<Vector> rowData1=new Vector<Vector>();
	//创建存放可选课程表格数据的Vector
	private Vector<Vector> rowData2=new Vector<Vector>();
	//创建提示信息组
	private JLabel[] jlArray={new JLabel("现有课程列表："),new JLabel("已安排课程列表："),
			new JLabel("请输入您要安排的课程的课程号："),new JLabel("老师"),new JLabel("上课时间："),
			new JLabel("星期"),new JLabel("讲次"),new JLabel("请输入您要移除的课程的课程号："),
			new JLabel("星期"),new JLabel("讲次")};
	private static final String[] xingqi={"1","2","3","4","5","6","7"};
	private static final String[] jiangci={"1","2","3","4","5"};
	private JButton[] jbArray={new JButton("提交该课程"),new JButton("移除该课程"),
			new JButton("允许选课"),new JButton("停止选课")};
	private JTextField[] jtfArray={new JTextField(),new JTextField(),
			new JTextField(),new JTextField()};
	//创建下拉表数组
	private JComboBox[] jcbArray={new JComboBox(xingqi),new JComboBox(jiangci),
			new JComboBox(xingqi),new JComboBox(jiangci),};
	private JTable jt1=null;//上表格的JTable引用
	private JTable jt2=null;//下表格
	private JScrollPane jsp1=null;//滚动窗体引用
	private JScrollPane jsp2=null;//滚动窗体引用
	
	public CourseManage(String coll_id,String host){
		this.host=host;
		this.coll_id=coll_id;
		this.initialData();
		this.initialFrame();
		this.initialListener();
	}
	//初始化数据
	public void initialData(){
		this.initialHead();
		this.initialData1();
		this.initialData2();
	}
	
	//初始化表头
	public void initialHead(){
		this.columnNames1.add("课程号");this.columnNames1.add("课程名");
		this.columnNames1.add("学分");this.columnNames1.add("所属专业");
		
		this.columnNames2.add("课程号");this.columnNames2.add("课程名");
		this.columnNames2.add("星期");this.columnNames2.add("讲次");
		this.columnNames2.add("任课老师");
	}
	
	//初始化课程表格数据
	public void initialData1(){
		try{
			String sql="select cou_id,cou_name,xuefen,dept.dept_name from "+
					"course,dept where dept.dept_id=course.dept_id and"+
					" course.coll_id='"+coll_id+"'";
			this.initialConnection();
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				Vector v=new Vector();
				String cou_id=rs.getString(1);
				String cou_name=new String(rs.getString(2).getBytes("gb2312"));
				String xuefen=rs.getDouble(3)+"";
				String dept_name=new String(rs.getString(4).getBytes("gb2312"));
				v.add(cou_id);v.add(cou_name);
				v.add(xuefen);v.add(dept_name);
				//将信息添加进向量集合
				this.rowData1.add(v);
			}
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
	}
	
	//初始化可选课程表格的方法
	public void initialData2(){
		try{
			String sql="select courseinfo.cou_id,course.cou_name,cou_day,cou_time,teacher "+
					"from courseinfo,course where course.coll_id='"+coll_id+"'"+
					" and courseinfo.cou_id=course.cou_id";
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				Vector v=new Vector();
				String cou_id=rs.getString(1);
				String cou_name=new String(rs.getString(2).getBytes("gb2312"));
				String cou_day=rs.getString(3);
				String cou_time=rs.getString(4);
				String teacher=new String(rs.getString(5).getBytes("gb2312"));
				v.add(cou_id);v.add(cou_name);
				v.add(cou_day);v.add(cou_time);
				v.add(teacher);
				this.rowData2.add(v);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}finally{
			this.closeConn();
		}
	}
	
	//初始化窗体的方法
	public void initialFrame(){
		this.setLayout(null);
		jt1=new JTable(new DefaultTableModel(rowData1,columnNames1));
		jt2=new JTable(new DefaultTableModel(rowData2,columnNames2));
		jsp1=new JScrollPane(jt1);
		jsp2=new JScrollPane(jt2);
		//初始化控件的位置
		jlArray[0].setBounds(30, 10, 150, 30);this.add(jlArray[0]);//现有课程标签
		jsp1.setBounds(30, 50, 500, 200);this.add(jsp1);//分隔窗体1
		jlArray[1].setBounds(30, 300, 150, 30);this.add(jlArray[1]);//已排课程标签
		jsp2.setBounds(30,350,500,200);this.add(jsp2);//分隔窗体2
		jlArray[2].setBounds(30, 580, 160, 30);this.add(jlArray[2]);//安排课程号
		jtfArray[0].setBounds(200, 580, 100, 30);this.add(jtfArray[0]);//安排课程号输入框
		jlArray[3].setBounds(330,580,50,30);this.add(jlArray[3]);//老师
		jtfArray[1].setBounds(380,580,100,30);this.add(jtfArray[1]);//老师输入框
		jlArray[4].setBounds(30, 630, 70, 30);this.add(jlArray[4]);//上课时间
		jlArray[5].setBounds(110, 630, 30, 30);this.add(jlArray[5]);//星期
		jcbArray[0].setBounds(150, 630, 50, 30);this.add(jcbArray[0]);//星期下拉列表
		jlArray[6].setBounds(210, 630, 30, 30);this.add(jlArray[6]);//讲次标签
		jcbArray[1].setBounds(250, 630, 50, 30);this.add(jcbArray[1]);//讲次下拉列表
		jbArray[0].setBounds(350, 630, 150, 30);this.add(jbArray[0]);//提交课程按钮
		jlArray[7].setBounds(30, 700, 150, 30);this.add(jlArray[7]);//移除课程标签
		jtfArray[2].setBounds(200, 700, 100, 30);this.add(jtfArray[2]);//移除课程输入框
		jlArray[8].setBounds(30, 750, 30, 30);this.add(jlArray[8]);//星期
		jcbArray[2].setBounds(80, 750, 50, 30);this.add(jcbArray[2]);
		jlArray[9].setBounds(140, 750, 30, 30);this.add(jlArray[9]);//讲次
		jcbArray[3].setBounds(180, 750, 50, 30);this.add(jcbArray[3]);//讲次下拉列表
		jbArray[1].setBounds(250, 750, 150, 30);this.add(jbArray[1]);//移除课程按钮
		jbArray[2].setBounds(100, 800, 150, 30);this.add(jbArray[2]);//允许选课按钮
		jbArray[3].setBounds(300,800,150,30);this.add(jbArray[3]);//停止选课按钮
		
	}
	
	//注册监听器
	public void initialListener(){
		jbArray[0].addActionListener(this);
		jbArray[1].addActionListener(this);
		jbArray[2].addActionListener(this);
		jbArray[3].addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jbArray[0]){
			this.submitCourse();
		}else if(e.getSource()==jbArray[1]){
			this.removeCourse();
		}else if(e.getSource()==jbArray[2]){
			this.permitChose();
		}else if(e.getSource()==jbArray[3]){
			this.stopChose();
		}
	}
	
	public void submitCourse(){
		try{
			this.initialConnection();
			String cou_id=jtfArray[0].getText().trim();
			if(cou_id.equals("")){
				JOptionPane.showMessageDialog(this,"请输入课程号","错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			Vector v=new Vector();
			String sql1="select dept_name,cou_name from dept,course "+
					"where dept.dept_id=course.dept_id and "+
					"course.cou_id='"+cou_id+"' and course.coll_id='"+coll_id+"'";
			rs=stmt.executeQuery(sql1);

			if(rs.next()){
				String dept_name=new String(rs.getString(1).getBytes("gb2312"));
				String cou_name=new String(rs.getString(2).getBytes("gb2312"));
				v.add(cou_id);
				v.add(cou_name);

			}else{
				JOptionPane.showMessageDialog(this,"添加失败，本学院没有该课程！","错误",
						JOptionPane.ERROR_MESSAGE);
				jtfArray[0].setText("");
				jtfArray[1].setText("");
				return;
			}
			rs.close();
			String cou_day=(String)jcbArray[0].getSelectedItem();//获得开课时间
			String cou_time=(String)jcbArray[1].getSelectedItem();
			String teacher=jtfArray[1].getText().trim();//获得老师姓名
			if(teacher.equals("")){
				JOptionPane.showMessageDialog(this,"请输入老师","错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			String sql="insert into courseinfo values"+
					"('"+cou_id+"','"+cou_day+"','"+cou_time+"',"+
					"'"+new String(teacher.getBytes(),"gb2312")+"','0')";
			int i=stmt.executeUpdate(sql);
			jtfArray[0].setText("");
			jtfArray[1].setText("");
			if(i!=1){
				JOptionPane.showMessageDialog(this,"添加失败，请检查是否与以后课程重复！","错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}else{
				v.add(cou_day);v.add(cou_time);v.add(teacher);
				DefaultTableModel temp=(DefaultTableModel)jt2.getModel();
				
				temp.getDataVector().add(v);
				((DefaultTableModel)jt2.getModel()).fireTableStructureChanged();
				
			}
		}catch(SQLException ea){
			ea.printStackTrace();
		}catch(UnsupportedEncodingException ea){
			ea.printStackTrace();
		}finally{
			this.closeConn();
		}
	}
	
	
	public void removeCourse(){
		try{
			this.initialConnection();
			String cou_id=jtfArray[2].getText().trim();
			if(cou_id.equals("")){
				JOptionPane.showMessageDialog(this,"请输入课程号","错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			Vector v=new Vector();
			String sql1="select c.cou_name,ci.cou_day,ci.cou_time,ci.teacher " +
					"from courseinfo ci,course c where ci.cou_id='"+cou_id+"'"+
					" and ci.cou_id=c.cou_id and c.coll_id='"+coll_id+"'";
			rs=stmt.executeQuery(sql1);
			jtfArray[2].setText("");
			if(rs.next()){
				String cou_name=new String(rs.getString(1).getBytes("gb2312"));
				String cou_day=new String(rs.getString(2).getBytes("gb2312"));
				String cou_time=new String(rs.getString(3).getBytes("gb2312"));
				String teacher=new String(rs.getString(4).getBytes("gb2312"));
				v.add(cou_id);v.add(cou_name);
				v.add(cou_day);v.add(cou_time);v.add(teacher);
			}else{
				JOptionPane.showMessageDialog(this,"你没有选择该课程！","错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			rs.close();

			String sql="delete from courseinfo where cou_id='"+cou_id+"'";
			int i=stmt.executeUpdate(sql);
			
			if(i!=1){
				JOptionPane.showMessageDialog(this,"删除失败！","错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}else{
				JOptionPane.showMessageDialog(this,"删除成功！");
				DefaultTableModel temp=(DefaultTableModel)jt2.getModel();
				temp.getDataVector().remove(v);
				((DefaultTableModel)jt2.getModel()).fireTableStructureChanged();
				
			}
		}catch(SQLException ea){
			ea.printStackTrace();
		}catch(UnsupportedEncodingException ea){
			ea.printStackTrace();
		}finally{
			this.closeConn();
		}
	}
	
	//选课时间控制
	public void permitChose(){
		String sql="update courseinfo ci,course c set onchosing='1' where "+
				"ci.cou_id=c.cou_id and c.coll_id='"+coll_id+"'";
		try{
			this.initialConnection();
			stmt.executeUpdate(sql);
			this.closeConn();
		}catch(Exception ea){
			ea.printStackTrace();
		}
	}
	public void stopChose(){
		String sql="update courseinfo ci,course c set onchosing='0' where "+
				"ci.cou_id=c.cou_id and c.coll_id='"+coll_id+"'";
		try{
			this.initialConnection();
			stmt.executeUpdate(sql);
			this.closeConn();
		}catch(Exception ea){
			ea.printStackTrace();
		}
	}
	
	//更新表格数据
	public void updateTable(){
		this.initialConnection();
		rowData1.removeAllElements();
		this.initialData1();
		((DefaultTableModel)jt1.getModel()).setDataVector(rowData1, columnNames1);
		((DefaultTableModel)jt1.getModel()).fireTableStructureChanged();
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
