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
	private String coll_id;	//ѧԺ��
	
	//���ѧԺ���ƺ�ѧԺ�ţ�����ѧԺ����ֵ��ѧԺ��
	private Map<String,String>	map_college=new HashMap<String, String>();
	//���רҵ����רҵ��,����רҵ����ֵ��רҵ��
	private Map<String,String> map_dept=new HashMap<String,String>();
	//���רҵ�źͰ�ļ��ϣ���ļ���Ҳ��Map�����ǰ�����ֵ�ǰ��
	private Map<String,Map> map_class=new HashMap<String,Map>();
	static int year=new Date().getYear()+1900;
	private JLabel[] jlArray={new JLabel("ѧ    ��"),new JLabel("��    ��"),
			new JLabel("��    ��"),new JLabel("��������"),new JLabel("��    ��"),
			new JLabel("ѧ    Ժ"),new JLabel("ר    ҵ"),new JLabel("��    ��"),
			new JLabel("��ѧʱ��"),new JLabel("��"),new JLabel("��"),
			new JLabel("��"),new JLabel("��"),new JLabel("��"),new JLabel("��")};
	private JTextField[] jtfArray={new JTextField(),new JTextField(),new JTextField()};
	String[] str_gender={"��","Ů"};
	//������ų�����ŵ�����
	static String[] str_year1=new String[20];
	static{
		for(int i=15;i<35;i++)
		{
			str_year1[i-15]=year-i+"";
		}
	}
	//������ѧ��ŵ�����
	String[] str_year={year+"",year-1+"",year-2+"",year-3+"",year-4+"",year-5+""};
	static String[] str_month=new String[12];
	//��ʼ���·�����
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
	JButton[] jbArray={new JButton("�ύ"),new JButton("����")};
	
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
			//��ѯ���ݿ��õ�ǰ�û�����ѧԺ������
			String sql1="select coll_name from college where coll_id='"+coll_id+"'";
			rs=stmt.executeQuery(sql1);
			if(rs.next()){
				String coll_name=new String(rs.getString(1).getBytes("gb2312"));
				jcb[4].addItem(coll_name);
			}
			rs.close();
			//��ѯ���ݿ⣬���רҵ����רҵ�ţ�����map_dept
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
				//���ݸ���רҵ�Ż�ø�רҵ�����Ϣ����map_class
				String dept_name=(String)ii.next();
				if(i==0){initial_dept_name=dept_name;}
				jcb[5].addItem(dept_name);//���༶���Ƽ��������б�
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
		//ѧ��
		jlArray[0].setBounds(30, 50, 100, 30);
		this.add(jlArray[0]);//���ѧ�ñ�ǩ
		jtfArray[0].setBounds(180, 50, 150, 30);
		this.add(jtfArray[0]);//���ѧ�����������
		//����
		jlArray[1].setBounds(30, 100, 100, 30);
		this.add(jlArray[1]);//���������ǩ
		jtfArray[1].setBounds(180, 100, 150, 30);
		this.add(jtfArray[1]);//����������������
		//�Ա�
		jlArray[2].setBounds(30, 150, 100, 30);
		this.add(jlArray[2]);//�Ա��ǩ
		jcb[0].setBounds(180,150,50,30);
		this.add(jcb[0]);
		//��������
		jlArray[3].setBounds(30, 200, 100, 30);
		this.add(jlArray[3]);//�������ڱ�ǩ
		jcb[1].setBounds(180,200,80,30);
		this.add(jcb[1]);//��ݿ�
		jlArray[9].setBounds(260, 200, 20, 30);
		this.add(jlArray[9]);//���ǩ
		jcb[2].setBounds(280,200,50,30);
		this.add(jcb[2]);//�·ݿ�
		jlArray[10].setBounds(330, 200, 20, 30);
		this.add(jlArray[10]);//�±�ǩ
		jcb[3].setBounds(350,200,50,30);
		this.add(jcb[3]);//�տ�
		jlArray[11].setBounds(4000, 200, 20, 30);
		this.add(jlArray[11]);//�ձ�ǩ
		//����
		jlArray[4].setBounds(30, 250, 100, 30);
		this.add(jlArray[4]);//�����ǩ
		jtfArray[2].setBounds(180, 250, 150, 30);
		this.add(jtfArray[2]);//���������
		//ѧԺ
		jlArray[5].setBounds(30, 300, 100, 30);
		this.add(jlArray[5]);//ѧԺ��ǩ
		jcb[4].setBounds(180,300,200,30);
		this.add(jcb[4]);//ѧԺ��
		//רҵ
		jlArray[6].setBounds(30, 350, 100, 30);
		this.add(jlArray[6]);
		jcb[5].setBounds(180,350,200,30);
		this.add(jcb[5]);//רҵ��
		//�༶
		jlArray[7].setBounds(30, 400, 100, 30);
		this.add(jlArray[7]);
		jcb[6].setBounds(180,400,200,30);
		this.add(jcb[6]);//�༶��
		//��ѧ����
		jlArray[8].setBounds(30, 450, 100, 30);
		this.add(jlArray[8]);//��ѧ��ǩ
		jcb[7].setBounds(180,450,80,30);
		this.add(jcb[7]);//���
		jlArray[12].setBounds(260, 450, 20, 30);
		this.add(jlArray[12]);//���ǩ
		jcb[8].setBounds(280,450,50,30);
		this.add(jcb[8]);//�·ݿ�
		jlArray[13].setBounds(330, 450, 20, 30);
		this.add(jlArray[13]);//�±�ǩ
		jcb[9].setBounds(350,450,50,30);
		this.add(jcb[9]);//�տ�
		jlArray[14].setBounds(400, 450, 20, 30);
		this.add(jlArray[14]);//�ձ�ǩ
		
		//�ύ��ť
		jbArray[0].setBounds(180, 510, 100, 30);
		this.add(jbArray[0]);
		//���ð�ť
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
			String deptname=(String)jcb[5].getSelectedItem();//����רҵ�仯
			String deptid=map_dept.get(deptname);
			Map classmap=(HashMap)map_class.get(deptid);
			//�������map(classmap)�����°�������б���Ϣ
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
	
	//�ύ��ť����ʵ��
	public void submitStu(){
		String stu_id=jtfArray[0].getText().trim();
		String patternStr="[0-9]{10}";
		if(stu_id.equals("")){
			JOptionPane.showMessageDialog(this, "������ѧ��","����",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(!stu_id.matches(patternStr)){
			JOptionPane.showMessageDialog(this, "ѧ�ű�����10����","����",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		String stu_name=jtfArray[1].getText().trim();
		if(stu_name.equals("")){
			JOptionPane.showMessageDialog(this, "����������","����",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(stu_name.length()>10){
			JOptionPane.showMessageDialog(this, "�������������������Ƿ���ȷ","����",
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
			JOptionPane.showMessageDialog(this, "���뼮��","����",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(nativeplace.length()>30){
			JOptionPane.showMessageDialog(this, "����������������Ҫ����30�֣�","����",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		String coll_id=this.coll_id;//���ѧԺ��
		//���רҵ��
		String dept_id=map_dept.get((String)jcb[5].getSelectedItem());
		//���ѧ��
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
				JOptionPane.showMessageDialog(this, "��ӳɹ�","��ʾ",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}else{
				conn.rollback();
				conn.setAutoCommit(true);
				JOptionPane.showMessageDialog(this, "���ʧ�ܣ�","����",
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
	
	
	
	//�Զ���ĳ�ʼ�����ݿ����ӵķ���
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
		
		//�ر����ݿ����ӵķ���
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
