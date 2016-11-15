package Student;
import java.awt.Component;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.*;
public class CourseTable extends JPanel{

	private String host;
	//声明Connection引用、Statement对象引用与结果集引用
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private String stu_id;//声明标志学生学号的引用
	private String[] s_head={"讲次","星期一","星期二","星期三",
			"星期四","星期五","星期六","星期日"};//初始化表头
	private String[][] s_data=new String[5][8];//创建存放课程信息的二维数组
	private JTable jt;//声明表格引用
	private JScrollPane jsp;
	public CourseTable(String stu_id,String host)
	{
		this.host=host;
		this.stu_id=stu_id;
		this.initialData();//初始化数据
		this.initialTable();//初始化表格
		this.initialFrame();//初始化窗体
	}

	public void initialFrame(){//将JScrollPane添加到相应位置
		this.setLayout(null);
		jsp.setBounds(30,30,620,450);this.add(jsp);
	}

	public void initialTable(){//创建表格并设置表格显示方式
		DefaultTableModel dtm=new DefaultTableModel(s_data,s_head);
		jt=new JTable(dtm);
		jt.setRowHeight(85);
		jt.setDefaultRenderer(Object.class, new TableViewRenderer());
		jsp=new JScrollPane(jt);
	}

	public void initialData()
	{//初始化表格数据
		//初始化第一列数据
		s_data[0][0]="第一讲";s_data[1][0]="第二讲";s_data[2][0]="第三讲";
		s_data[3][0]="第四讲";s_data[4][0]="第五讲";
		try{//查询数据库，初始化数据
			String sql="select cou_name,teacher,cou_day,cou_time from course,courseinfo,"+
					"grade where grade.stu_id='"+stu_id+"' and grade.cou_id=course.cou_id and "+
					"grade.cou_id=courseinfo.cou_id and grade.isdual=0";
			this.initialConnection();
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				String cou_name=new String(rs.getString(1).getBytes("gb2312"));
				String teacher=new String(rs.getString(2).getBytes("gb2312"));
				int cou_day=Integer.parseInt(rs.getString(3));
				int cou_time=Integer.parseInt(rs.getString(4));
				s_data[cou_time-1][cou_day]=cou_name+"\n(教师："+teacher+")";
			}
		}
		catch(Exception e){e.printStackTrace();}
	}

	public void updataview()//更新表格模型
	{
		((DefaultTableModel)jt.getModel()).setDataVector(s_data,s_head);
		((DefaultTableModel)jt.getModel()).fireTableStructureChanged();
	}

	//自定义的初始化数据库连接的方法
	public void  initialConnection()
	{
		try
		{
			Class.forName("org.gjt.mm.mysql.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://"+host+"/managesystem","root","123456");
			stmt=conn.createStatement();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	//关闭连接的方法
	public void closeConn()
	{
		try
		{
			if(rs!=null)
			{
				rs.close();
			}
			if(stmt!=null)
			{
				stmt.close();
			}
			if(conn!=null)
			{
				conn.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	//自定义的表格绘制器
	class TableViewRenderer extends JTextArea implements TableCellRenderer 
	{ 
		public TableViewRenderer() 
		{ 
			//将表格设为自动换行
			setLineWrap(true); 
		}
		//实现抽象方法
		public Component getTableCellRendererComponent(JTable jtable, Object obj, 
				boolean isSelected, boolean hasFocus, int row, int column) 
		{ 
			//设置文本的显示信息
			setText(obj == null ? "" : obj.toString()); 
			return this; 
		} 
	} 
}
