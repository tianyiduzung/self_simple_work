package manager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class ReturnBook extends JPanel implements ActionListener
{
	public Root root;
	DataBase db;
	String sql;
	String str;
	//�����ָ��Ϊ���µ�JSplitePane����
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
	//����JPanel����
	private JPanel jpt=new JPanel();
	private JPanel jpb=new JPanel();
	//������ť����
	private JButton[] jbArray=new JButton[]
			{
			new JButton	("��ʧ"),
			new JButton	("�黹"),
			new JButton	("ȷ��")
			};
	private JLabel jl=new JLabel("���������ѧ��:");

	private JTextField jtxt=new JTextField();

	//��������
	Vector<String> head = new Vector<String>();
	{
		head.add("���");
		head.add("ѧ��");
		head.add("����ʱ��");
		head.add("����ʱ��");
		head.add("�Ƿ����");
		head.add("�Ƿ�ԤԼ");
	}
	//
	Vector<Vector> data=new Vector<Vector>();
	//�������ģ��
	DefaultTableModel dtm=new DefaultTableModel(data,head);
	//����Jtable����
	JTable jt=new JTable(dtm);
	//��JTable��װ����������
	JScrollPane jspn=new JScrollPane(jt);
	public ReturnBook()
	{
		this.setLayout(new GridLayout(1,1));
		//��������RetrunBook�������²��־�Ϊ�ղ��ֹ�����
		jpt.setLayout(null);
		jpb.setLayout(null);
		//����Label�Ĵ�С��λ��
		jl.setBounds(5,15,100,20);
		//��Jlabel��ӵ�jpt�����
		jpt.add(jl);
		//ΪJTextField���ô�С��λ��
		jtxt.setBounds(105,15,300,20);
		//��JTextField��ӵ�jpt
		jpt.add(jtxt);
		//����JBuuton�Ĵ�С��λ��
		jbArray[0].setBounds(5,50,100,20);
		jbArray[1].setBounds(150,50,100,20);
		jbArray[2].setBounds(295,50,100,20);
		//���JButton����������¼�������
		for(int i=0;i<3;i++)
		{
			jpt.add(jbArray[i]);
			jbArray[i].addActionListener(this);
		}

		//��jpt���õ�jsp���ϲ�����
		jsp.setTopComponent(jpt);
		//jpb.add(jspn);
		jsp.setBottomComponent(jspn);
		jsp.setDividerSize(4);
		this.add(jsp);
		//����jsp�зָ����ĳ�ʼλ��
		jsp.setDividerLocation(80);
		//���ô���Ĵ�Сλ�ü��ɼ���
		this.setBounds(10,10,800,600);
		this.setVisible(true); 
	} 
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jbArray[2]){//�¼�ԴΪ"ȷ��"��ť
			if(jtxt.getText().trim().equals("")){//ѧ������Ϊ�գ���ʾ
				JOptionPane.showMessageDialog(this,"������ѧ��",
						"��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			else{//����ѧ�Ž��в�ѯ
				sql="select * from RECORD where StuNO="+jtxt.getText().trim();
				db=new DataBase();
				db.selectDb(sql);
				Vector<Vector> vtemp = new Vector<Vector>();
				try{//����������쳣����
					int k=0;
					while(db.rs.next()){//ȡ�������
						k++;
						Vector<String> v = new Vector<String>();
						for(int i=1;i<7;i++){//��ÿ����ӵ���ʱ����v
							String str=db.rs.getString(i);
							//str=new String(str.getBytes("ISO-8859-1"),"gb2312");
							v.add(str);
						}
						vtemp.add(v);//��������¼��ӵ���ʱ����vtemp
						//����table
						jt.clearSelection();
						dtm.setDataVector(vtemp,head);
						jt.updateUI();
						jt.repaint();	
					}
					if(k==0){//��ʾ
						JOptionPane.showMessageDialog(this,"�����˴����ѧ�Ż��ѧ��û�н����¼",
								"��Ϣ",JOptionPane.INFORMATION_MESSAGE);
						return;
					}			
				}
				catch(Exception ea){ea.printStackTrace();}
			}
		}
		if(e.getSource()==jbArray[1]){//��Ҫ�黹ͼ��
			int row=jt.getSelectedRow();
			if(row<0){//���δѡ���²����е�ĳЩ���ݣ�������ʾ

				JOptionPane.showMessageDialog(this,"��ѡ��Ҫ�黹����!!!","��Ϣ",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			str=(String)jt.getValueAt(row,0);//�õ����
			int sno=Integer.parseInt((String)jt.getValueAt(row,1));
			int bno=Integer.parseInt(str);
			int flag=checkTime(sno,bno);	//�ж��Ƿ���
			if(flag==-1){//���ͼ�鳬�ڣ���ȡ����ͬѧ�Ľ���Ȩ��
				db=new DataBase();
				sql="update student set permitted='��' where StuNO="+sno;
				db.updateDb(sql);
				db.dbClose();
			}
			if(flag==0){return;}//���ͼ��δ���ڣ�����й黹����
			sql="Delete from RECORD where BookNO="+Integer.parseInt(str);
			db=new DataBase();
			db.updateDb(sql);
			sql="update book set borrowed='��' where BookNO="+Integer.parseInt(str);
			db.updateDb(sql);//������ͼ���¼�������ŵ���Ϊ�ɽ�
			db.dbClose();
			updateTable();
		}
		if(e.getSource()==jbArray[0]){//��Ҫ��ʧͼ��
			int row=jt.getSelectedRow();
			if(row<0){
				JOptionPane.showMessageDialog(this,"��ѡ��Ҫ��ʧ����!!!","��Ϣ",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			loseBook(row);
			updateTable();
		}
	}

	public void loseBook(int row){
		String bname="";
		int lbno=0;
		int bno=Integer.parseInt((String)jt.getValueAt(row,0));//�õ���ʧ������
		String sno=(String)jt.getValueAt(row,1);//�õ���ʧ�����ѧ��
		sql="select BookName from BOOK where BookNO="+bno;
		db=new DataBase();
		db.selectDb(sql);
		try{
			if(db.rs.next()){
				bname=db.rs.getString(1).trim();//�õ���ʧ�������
			}
		}
		catch(Exception e){e.printStackTrace();}
		//�ҵ����Ķ�ʧ��¼��
		sql="select MAX(LbNO) from LoseBook";
		db.selectDb(sql);
		try{
			if(db.rs.next()){
				lbno=db.rs.getInt(1);//�õ����Ķ�ʧ��¼��
				lbno++;
			}
		} 
		catch(Exception ea){ea.printStackTrace();}
		sql="insert into LOSEBOOK values("+lbno+","+sno+","+bno+",'"+bname+"')";//�����¼���в����¼
		db.updateDb(sql);
		sql="select BookNo from ORDERREPORT where BookNO="+bno;//���ԤԼ�����Ƿ�ԤԼ���飬���У�ɾ��
		db.selectDb(sql);
		try{
			while(db.rs.next()){//ɾ����¼
				sql="delect from ORDERREPORT where BookNO="+bno;
				db.updateDb(sql);
			}
		}
		catch(Exception e){e.printStackTrace();}
		sql="select BookNo from EXCEEDTIME where BookNO="+bno;//��鳬�ڱ����Ƿ��и��飬���У�ɾ��
		db.selectDb(sql);
		try{
			while(db.rs.next()){//�ӳ��ڱ���ɾ����¼
				sql="delect from EXCEEDTIME where BookNO="+bno;
				db.updateDb(sql);
			}
		}
		catch(Exception e){e.printStackTrace();}
		sql="delete from RECORD where BookNO="+bno;//�ӽ�����н���ʧͼ��ļ�¼ɾ��
		db.updateDb(sql);
		sql="delete from BooK where BookNo="+bno;//��ͼ����н���ʧ����ɾ��
		int i=db.updateDb(sql);
		db.dbClose();
		if(i>0){//��ʾ��ʧ�ɹ�
			JOptionPane.showMessageDialog(this,"��ϲ�㣬��ʧ�ɹ�!!!","��Ϣ",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		else{//��ʾ��ʧʧ��
			JOptionPane.showMessageDialog(this,"�Բ��𣬹�ʧʧ��!!!",
					"��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}	
	public void updateTable(){//ʵ�ֽ����²����ĸ���
		sql="select * from RECORD where StuNO="+jtxt.getText().trim();
		db=new DataBase();
		db.selectDb(sql);//��ͼ����Ϣ�����ݿ���ȡ��
		Vector<Vector> vtemp = new Vector<Vector>();
		try{
			while(db.rs.next()){
				Vector<String> v = new Vector<String>();
				for(int i=1;i<7;i++){//��ÿ����ӵ���ʱ����v
					String str=db.rs.getString(i);
					//					str=new String(str.getBytes("ISO-8859-1"),"gb2312");
					v.add(str);
				}
				vtemp.add(v);//��������¼��ӵ���ʱ����vtemp
			}
			db.dbClose();	
		}
		catch(Exception ea){ea.printStackTrace();}
		jt.clearSelection();			
		dtm.setDataVector(vtemp,head);//����table	
		jt.updateUI();
		jt.repaint();   		
	}	
	public int checkTime(int sno,int bno)
	{//-1������û������  0����������   1��������������  -2��ʾ���ڽ�����
		int day=0;
		int flag=0;
		String bname="";
		Date now=new Date();
		String returntime="";
		sql="select ReturnTime from RECORD where StuNO="+sno+" and BookNO="+bno;
		db=new DataBase();
		db.selectDb(sql);
		try{
			if(db.rs.next()){
				returntime=db.rs.getString(1);//��ȡ�黹ʱ��
			}
		}
		catch(Exception e){e.printStackTrace();}
		String[] strday=returntime.split("\\.");//����ʹ���˼򵥵�����ʽ���涨��ʱ��ĸ�ʽ
		int ryear=Integer.parseInt(strday[0].trim());
		int rmonth=Integer.parseInt(strday[1].trim());
		int rday=Integer.parseInt(strday[2].trim());
		day=(now.getYear()+1900-ryear)*365+(now.getMonth()+1-rmonth)*30+(now.getDate()-rday);
		if(day==-30)
		{//��ʾ��������
			JOptionPane.showMessageDialog(this,"�������鲻�ܻ�������",
					"��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			flag=0;
		}
		else if(day>0)
		{//��������
			int i=JOptionPane.showConfirmDialog(this,"�����ѹ���,Ӧ������Ϊ"
					+day*0.1+"Ԫ,�Ƿ���ɷ���?","��Ϣ",JOptionPane.YES_NO_OPTION);
			if(i==JOptionPane.YES_OPTION){
				JOptionPane.showMessageDialog(this,"���ѳɹ�����"+day*0.1+"Ԫ",
						"��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				flag=-2;
			}
			else
			{//������û����
				flag=-1;
				sql="select BookName from BOOK where BookNO="+bno;
				db.selectDb(sql);
				try{
					if(db.rs.next()){bname=db.rs.getString(1).trim();}
				}
				catch(Exception e){e.printStackTrace();}
				sql="insert into EXCEEDTIME(StuNO,BookNO,BookName,DelayTime) values("+sno+","+bno+",'"+bname+"',"+day+")";
				db.updateDb(sql);
			}
		}
		else
		{//��ʾ���������黹����
			flag=1;
		}
		db.dbClose();
		return flag;
	}

	public static void main(String[] args)
	{
		new ReturnBook();
	}



}
