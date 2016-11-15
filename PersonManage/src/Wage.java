
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.io.*;

public class Wage extends JPanel implements ActionListener {
	private JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);// 设置分割方向
	private JPanel jpt = new JPanel();
	String[] str = new String[10];// 声明字符串数组
	DataBase db;
	String sql;// 声明SQL变量
	Vector<String[]> vv;// 声明字符串向量
	int count = 0;
	private JLabel[] jlArray = {// 声明标签并为其指定文本
	new JLabel("   职 工 ID"), new JLabel("    姓    名"), new JLabel("  基本工资"),
			new JLabel("  基本奖金"), new JLabel("  效益奖金"), new JLabel("  养老保险"),
			new JLabel("  医疗保险"), new JLabel("  扣发工资"), new JLabel("  扣发奖金"),
			new JLabel("  考勤扣发") };
	private JLabel jl = new JLabel("(查询请输入职工ID和姓名)");
	private JTextField[] jtxtArray = new JTextField[] {// 声明文本框数组
	new JTextField(), new JTextField(), new JTextField(), new JTextField(),
			new JTextField(), new JTextField(), new JTextField(),
			new JTextField(), new JTextField(), new JTextField() };
	private JButton[] jbArray = {// 声明JButton按钮设置文本
	new JButton("最前"), new JButton("上一个"), new JButton("下一个"),
			new JButton("最后"), new JButton("添加记录"), new JButton("修改记录"),
			new JButton("查询"), new JButton("清空") };
	Vector<String> head = new Vector<String>();
	{// 创建标题
		head.add("职工ID");
		head.add("姓名");
		head.add("基本工资");
		head.add("基本奖金");
		head.add("效益奖金");
		head.add("养老保险");
		head.add("医疗保险");
		head.add("扣发工资");
		head.add("扣发奖金");
		head.add("考勤扣发");
	}
	Vector<Vector> data = new Vector<Vector>();// 在下部子窗口中设置表格标题
	DefaultTableModel dtm = new DefaultTableModel(data, head);// 创建表格模型
	JTable jt = new JTable(dtm);// 创建Jtable对象
	JScrollPane jspn = new JScrollPane(jt);// 将JTable封装到滚动窗格

	public Wage() {
		this.setLayout(new GridLayout(1, 1));
		jpt.setLayout(null);// 设置面板的上部分为空布局管理器
		jsp.setDividerLocation(205);// 设置jsp中分割条的初始位置
		jsp.setDividerSize(4);// 设置分隔条的宽度
		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(jspn);
		for (int i = 0; i < 10; i++) {// 初始化标签和文本框
			jpt.add(jtxtArray[i]);
			jpt.add(jlArray[i]);
			if (i < 5) {// 设置第一行标签和文本框的大小位置并为其添加监听器
				jlArray[i].setBounds(15 + i * 160, 20, 70, 20);
				jtxtArray[i].setBounds(85 + i * 160, 20, 90, 20);
				jtxtArray[i].addActionListener(this);
			} else {// 设置第二行标签和文本框的大小位置并为其添加监听器
				jlArray[i].setBounds(15 + (i - 5) * 160, 60, 70, 20);
				jtxtArray[i].setBounds(85 + (i - 5) * 160, 60, 90, 20);
				jtxtArray[i].addActionListener(this);
			}
		}
		this.add(jsp);
		jpt.add(jl);
		jl.setBounds(405, 175, 150, 20);// 设置标签的大小位置
		jsp.setBottomComponent(jspn);// 设置下部子窗格
		for (int i = 0; i < 8; i++) {
			jpt.add(jbArray[i]);// 将JButton添加进jpt
			if (i < 4) {// 设置第一行按钮的大小位置
				jbArray[i].setBounds(125 + 150 * i, 105, 100, 25);
			} else {// 设置第二行按钮的大小位置
				jbArray[i].setBounds(125 + 150 * (i - 4), 145, 100, 25);
			}
			jbArray[i].addActionListener(this);// 为按钮添加监听器
		}
		db = new DataBase();
		vv = db.getWage();// 调用DataBase类中的getWage方法实现wage信息的引入
		db.dbClose();
		// 设置窗体的大小位置及可见性
		this.setBounds(5, 5, 600, 500);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jtxtArray[0]) {// 当焦点在jtxtArray[0]文本框时，单击回车键，焦点跳转到jtxtArray[1]
			jtxtArray[1].requestFocus();
		}
		if (e.getSource() == jtxtArray[1]) {// 当焦点在jtxtArray[1]文本框时，单击回车键，焦点跳转到jtxtArray[2]
			jtxtArray[2].requestFocus();
		}
		if (e.getSource() == jtxtArray[2]) {// 当焦点在jtxtArray[2]文本框时，单击回车键，焦点跳转到jtxtArray[3]
			jtxtArray[3].requestFocus();
		}
		if (e.getSource() == jtxtArray[3]) {// 当焦点在jtxtArray[3]文本框时，单击回车键，焦点跳转到jtxtArray[4]
			jtxtArray[4].requestFocus();
		}
		if (e.getSource() == jtxtArray[4]) {// 当焦点在jtxtArray[4]文本框时，单击回车键，焦点跳转到jtxtArray[5]
			jtxtArray[5].requestFocus();
		}
		if (e.getSource() == jtxtArray[5]) {// 当焦点在jtxtArray[5]文本框时，单击回车键，焦点跳转到jtxtArray[6]
			jtxtArray[6].requestFocus();
		}
		if (e.getSource() == jtxtArray[6]) {// 当焦点在jtxtArray[6]文本框时，单击回车键，焦点跳转到jtxtArray[7]
			jtxtArray[7].requestFocus();
		}
		if (e.getSource() == jtxtArray[7]) {// 当焦点在jtxtArray[7]文本框时，单击回车键，焦点跳转到jtxtArray[8]
			jtxtArray[8].requestFocus();
		}
		if (e.getSource() == jtxtArray[8]) {// 当焦点在jtxtArray[8]文本框时，单击回车键，焦点跳转到jtxtArray[9]
			jtxtArray[9].requestFocus();
		}
		if (e.getSource() == jbArray[4]) {// 当单击"添加记录"按钮，执行插入操作
			this.insert();
		}
		if (e.getSource() == jbArray[5]) {// 当单击"修改"按钮，将执行信息的更新操作
			this.update();
		}
		if (e.getSource() == jbArray[6]) {// 当单击"查询"按钮，将执行查询操作
			this.select();
		}
		if (e.getSource() == jbArray[7]) {// 当单击"清空"按钮，将文本框清空
			for (int i = 0; i < 10; i++) {// 执行清空操作
				jtxtArray[i].setText("");
			}
		}
		if (e.getSource() == jbArray[0]) {// 当单击"最前"按钮，显示数据库中的第一个wage信息
			this.first();
		}

		if (e.getSource() == jbArray[1]) {// 当单击"上一个"按钮，将会循环执行显示数据库中上一条信息
			this.before();
		}
		if (e.getSource() == jbArray[2]) {// 当单击"下一个"按钮，
			this.next();
		}
		if (e.getSource() == jbArray[3]) {// 当单击"最后"按钮，显示最后一条记录
			this.last();
		}
	}

	public void first() {// 显示第一条信息
		String[] str = vv.get(0);// 得到第一条信息
		for (int i = 0; i < 10; i++) {// 将第一条信息显示到文本框
			jtxtArray[i].setText(str[i]);
		}
	}

	public void last() {// 显示最后一条信息
		String[] str = vv.get(vv.size() - 1);// 得到最后一条信息
		for (int i = 0; i < 10; i++) {// 将最后一条信息显示
			jtxtArray[i].setText(str[i]);
		}
	}

	public void before() {
		if (count == 0) {// 当在数据库的第一条记录处则跳到最后一条循环显示
			count = vv.size() - 1;
		} else {// 当不是第一条记录，将显示上一条记录
			count--;
		}
		String[] str = vv.get(count);// 获得数据库信息
		for (int i = 0; i < 10; i++) {// 将数据库信息显示在文本框中
			jtxtArray[i].setText(str[i]);
		}
	}

	public void next() {
		if (count == (vv.size() - 1)) {// 当在数据库的最后一条记录处则跳到第一条循环显示
			count = 0;
		} else {// 如不是最后一条记录，则显示下一条
			count++;
		}
		String[] str = vv.get(count);// 获得数据库信息
		for (int i = 0; i < 10; i++) {// 将数据库信息显示在文本框中
			jtxtArray[i].setText(str[i]);
		}
	}

	public void select() {
		String s1 = jtxtArray[0].getText().trim();// 得到职工ID
		String s2 = jtxtArray[1].getText().trim();// 得到职工姓名
		if (!s1.equals("") && !s2.equals("")) {// 如果职工ID和下面文本框不为空，则对该员工的信息进行查询
			sql = "select * from Wage where Name='" + s2 + "' and EmployeeID="
					+ Integer.parseInt(s1);
			db = new DataBase();
			db.selectDb(sql);
			try {
				Vector<Vector> v = new Vector<Vector>();// 声明向量
				while (db.rs.next()) {// 结果集不为空
					Vector<String> vtemp = new Vector<String>();
					for (int i = 0; i < 10; i++) {// 对从结果集中的到的信息进行转码
						String str1 = new String(db.rs.getString(i + 1).getBytes());
						vtemp.add(str1);// 将结果集信息添加到临时数组
					}
					v.add(vtemp);// 将搜索到的信息进行显示

				}
				if (v.size() == 0) {// 结果集为空进行提示
					JOptionPane.showMessageDialog(this, "您所查询的职工信息不存在！！", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				dtm.setDataVector(v, head);// 更新table
				jt.updateUI();
				jt.repaint();
				JOptionPane.showMessageDialog(this, "您已经成功查询该职工信息！！", "提示",
						JOptionPane.INFORMATION_MESSAGE);//
				jtxtArray[0].setText("");
				jtxtArray[1].setText("");
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void insert() {// 添加功能的开发
		for (int i = 0; i < 10; i++) {// 获得文本框中的内容
			str[i] = jtxtArray[i].getText().trim();
		}
		if (str[0].equals("") && str[1].equals("") && str[2].equals("")
				&& str[3].equals("") && str[4].equals("") && str[5].equals("")
				&& str[6].equals("") && str[7].equals("") && str[8].equals("")
				&& str[9].equals("")) {// 当文本框均为空进行提示
			JOptionPane.showMessageDialog(this, "信息不能为空！！！", "消息",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		int in = Integer.parseInt(jtxtArray[0].getText().trim());// 将得到职工ID转为int型
		if (!str[0].equals("") && !str[1].equals("") && !str[2].equals("")
				&& !str[3].equals("") && !str[4].equals("")
				&& !str[5].equals("") && !str[6].equals("")
				&& !str[7].equals("") && !str[8].equals("")
				&& !str[9].equals("")) {// 当文本框均不为空，进行信息添加
			sql = "select EmployeeID from Person where EmployeeID=" + in;// 搜索该职工是否存在
			db = new DataBase();
			db.selectDb(sql);
			try {
				if (db.rs.next()) {// 该职工存在
					int[] s = new int[8];
					for (int i = 0; i < 8; i++) {// 得到所输入的信息
						s[i] = Integer.parseInt(str[i + 2]);
					}
					sql = "select EmployeeID from Wage where EmployeeID=" + in;// 查询该职工的职工ID
					db = new DataBase();
					db.selectDb(sql);
					if (!db.rs.next()) {// 如果不存在就将职工的信息添加进数据库
						sql = "insert into Wage(EmployeeID,Name,Base_pay,Baseprize,Benifitprize,Insurance"
								+ ",Medicare,Deprivepay,Depriveprize,Depriveattend) values("+ in+ ",'"+ str[1]+ "'," +
										""+ s[0]+ ","+ s[1]+ ","+ s[2]+ ","+ s[3]+ "," +
												""+ s[4]+ ","+ s[5] + "," + s[6] + "," + s[7] + ")";
						db = new DataBase();
						db.updateDb(sql);
						JOptionPane.showMessageDialog(this, "添加成功！！", "消息!!",
								JOptionPane.INFORMATION_MESSAGE);// 添加成功提示
						return;
					} else {// 主键重复提示
						JOptionPane.showMessageDialog(this,
								"该职工此信息已经添加,不必重复！！", "消息!!",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				} else {// 该公司无此员工的提示
					JOptionPane.showMessageDialog(this, "本公司没有这名职工！！", "消息!!",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void update() {
		int in = Integer.parseInt(jtxtArray[0].getText().trim());// 得到职工ID
		for (int i = 0; i < 10; i++) {// 得到文本框所输入内容
			str[i] = jtxtArray[i].getText().trim();
		}
		if (!str[0].equals("") && !str[1].equals("") && !str[2].equals("")
				&& !str[3].equals("") && !str[4].equals("")
				&& !str[5].equals("") && !str[6].equals("")
				&& !str[7].equals("") && !str[8].equals("")
				&& !str[9].equals("")) {
			int[] s = new int[9];// 定义整型数组
			for (int i = 0; i < 8; i++) {// 对数组各项进行赋值
				s[i] = Integer.parseInt(str[i + 2]);
			}
			sql = "update Attend set Name='" + str[1] + "',Basepay=" + s[0]
					+ ",Baseprize=" + s[1] + ",Benifitprize=" + s[2]
					+ ",Insurance=" + s[3] + ",Medicare=" + s[4]
					+ ",Deprivepay=" + s[5] + ",Depriveprize=" + s[6]
					+ ",Depriveattend=" + s[7] + " where EmployeeID='" + str[0]
					+ "'";// 更新各项信息
			db = new DataBase();
			db.updateDb(sql);
			JOptionPane.showMessageDialog(this, "修改信息成功！！", "消息!!",
					JOptionPane.INFORMATION_MESSAGE);// 修改信息成功提示
			return;
		} else {// 文本框输入为空提示
			JOptionPane.showMessageDialog(this, "请输入需要修改的信息！！", "消息!!",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

	}

	public static void main(String[] args) {
		new Wage();
	}
}