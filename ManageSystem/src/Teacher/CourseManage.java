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
	//������ſγ̱���ͷ��Vector
	private Vector<String> columnNames1=new Vector<String>();
	//������ſ�ѡ�γ̱���ͷ��Vector
	private Vector<String> columnNames2=new Vector<String>();
	//������ſγ̱�����ݵ�Vector
	private Vector<Vector> rowData1=new Vector<Vector>();
	//������ſ�ѡ�γ̱�����ݵ�Vector
	private Vector<Vector> rowData2=new Vector<Vector>();
	//������ʾ��Ϣ��
	private JLabel[] jlArray={new JLabel("���пγ��б�"),new JLabel("�Ѱ��ſγ��б�"),
			new JLabel("��������Ҫ���ŵĿγ̵Ŀγ̺ţ�"),new JLabel("��ʦ"),new JLabel("�Ͽ�ʱ�䣺"),
			new JLabel("����"),new JLabel("����"),new JLabel("��������Ҫ�Ƴ��Ŀγ̵Ŀγ̺ţ�"),
			new JLabel("����"),new JLabel("����")};
	private static final String[] xingqi={"1","2","3","4","5","6","7"};
	private static final String[] jiangci={"1","2","3","4","5"};
	private JButton[] jbArray={new JButton("�ύ�ÿγ�"),new JButton("�Ƴ��ÿγ�"),
			new JButton("����ѡ��"),new JButton("ֹͣѡ��")};
	private JTextField[] jtfArray={new JTextField(),new JTextField(),
			new JTextField(),new JTextField()};
	//��������������
	private JComboBox[] jcbArray={new JComboBox(xingqi),new JComboBox(jiangci),
			new JComboBox(xingqi),new JComboBox(jiangci),};
	private JTable jt1=null;//�ϱ���JTable����
	private JTable jt2=null;//�±��
	private JScrollPane jsp1=null;//������������
	private JScrollPane jsp2=null;//������������
	
	public CourseManage(String coll_id,String host){
		this.host=host;
		this.coll_id=coll_id;
		this.initialData();
		this.initialFrame();
		this.initialListener();
	}
	//��ʼ������
	public void initialData(){
		this.initialHead();
		this.initialData1();
		this.initialData2();
	}
	
	//��ʼ����ͷ
	public void initialHead(){
		this.columnNames1.add("�γ̺�");this.columnNames1.add("�γ���");
		this.columnNames1.add("ѧ��");this.columnNames1.add("����רҵ");
		
		this.columnNames2.add("�γ̺�");this.columnNames2.add("�γ���");
		this.columnNames2.add("����");this.columnNames2.add("����");
		this.columnNames2.add("�ο���ʦ");
	}
	
	//��ʼ���γ̱������
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
				//����Ϣ��ӽ���������
				this.rowData1.add(v);
			}
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
	}
	
	//��ʼ����ѡ�γ̱��ķ���
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
	
	//��ʼ������ķ���
	public void initialFrame(){
		this.setLayout(null);
		jt1=new JTable(new DefaultTableModel(rowData1,columnNames1));
		jt2=new JTable(new DefaultTableModel(rowData2,columnNames2));
		jsp1=new JScrollPane(jt1);
		jsp2=new JScrollPane(jt2);
		//��ʼ���ؼ���λ��
		jlArray[0].setBounds(30, 10, 150, 30);this.add(jlArray[0]);//���пγ̱�ǩ
		jsp1.setBounds(30, 50, 500, 200);this.add(jsp1);//�ָ�����1
		jlArray[1].setBounds(30, 300, 150, 30);this.add(jlArray[1]);//���ſγ̱�ǩ
		jsp2.setBounds(30,350,500,200);this.add(jsp2);//�ָ�����2
		jlArray[2].setBounds(30, 580, 160, 30);this.add(jlArray[2]);//���ſγ̺�
		jtfArray[0].setBounds(200, 580, 100, 30);this.add(jtfArray[0]);//���ſγ̺������
		jlArray[3].setBounds(330,580,50,30);this.add(jlArray[3]);//��ʦ
		jtfArray[1].setBounds(380,580,100,30);this.add(jtfArray[1]);//��ʦ�����
		jlArray[4].setBounds(30, 630, 70, 30);this.add(jlArray[4]);//�Ͽ�ʱ��
		jlArray[5].setBounds(110, 630, 30, 30);this.add(jlArray[5]);//����
		jcbArray[0].setBounds(150, 630, 50, 30);this.add(jcbArray[0]);//���������б�
		jlArray[6].setBounds(210, 630, 30, 30);this.add(jlArray[6]);//���α�ǩ
		jcbArray[1].setBounds(250, 630, 50, 30);this.add(jcbArray[1]);//���������б�
		jbArray[0].setBounds(350, 630, 150, 30);this.add(jbArray[0]);//�ύ�γ̰�ť
		jlArray[7].setBounds(30, 700, 150, 30);this.add(jlArray[7]);//�Ƴ��γ̱�ǩ
		jtfArray[2].setBounds(200, 700, 100, 30);this.add(jtfArray[2]);//�Ƴ��γ������
		jlArray[8].setBounds(30, 750, 30, 30);this.add(jlArray[8]);//����
		jcbArray[2].setBounds(80, 750, 50, 30);this.add(jcbArray[2]);
		jlArray[9].setBounds(140, 750, 30, 30);this.add(jlArray[9]);//����
		jcbArray[3].setBounds(180, 750, 50, 30);this.add(jcbArray[3]);//���������б�
		jbArray[1].setBounds(250, 750, 150, 30);this.add(jbArray[1]);//�Ƴ��γ̰�ť
		jbArray[2].setBounds(100, 800, 150, 30);this.add(jbArray[2]);//����ѡ�ΰ�ť
		jbArray[3].setBounds(300,800,150,30);this.add(jbArray[3]);//ֹͣѡ�ΰ�ť
		
	}
	
	//ע�������
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
				JOptionPane.showMessageDialog(this,"������γ̺�","����",
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
				JOptionPane.showMessageDialog(this,"���ʧ�ܣ���ѧԺû�иÿγ̣�","����",
						JOptionPane.ERROR_MESSAGE);
				jtfArray[0].setText("");
				jtfArray[1].setText("");
				return;
			}
			rs.close();
			String cou_day=(String)jcbArray[0].getSelectedItem();//��ÿ���ʱ��
			String cou_time=(String)jcbArray[1].getSelectedItem();
			String teacher=jtfArray[1].getText().trim();//�����ʦ����
			if(teacher.equals("")){
				JOptionPane.showMessageDialog(this,"��������ʦ","����",
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
				JOptionPane.showMessageDialog(this,"���ʧ�ܣ������Ƿ����Ժ�γ��ظ���","����",
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
				JOptionPane.showMessageDialog(this,"������γ̺�","����",
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
				JOptionPane.showMessageDialog(this,"��û��ѡ��ÿγ̣�","����",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			rs.close();

			String sql="delete from courseinfo where cou_id='"+cou_id+"'";
			int i=stmt.executeUpdate(sql);
			
			if(i!=1){
				JOptionPane.showMessageDialog(this,"ɾ��ʧ�ܣ�","����",
						JOptionPane.ERROR_MESSAGE);
				return;
			}else{
				JOptionPane.showMessageDialog(this,"ɾ���ɹ���");
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
	
	//ѡ��ʱ�����
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
	
	//���±������
	public void updateTable(){
		this.initialConnection();
		rowData1.removeAllElements();
		this.initialData1();
		((DefaultTableModel)jt1.getModel()).setDataVector(rowData1, columnNames1);
		((DefaultTableModel)jt1.getModel()).fireTableStructureChanged();
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

}
