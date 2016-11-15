package student;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class OrderBook extends JPanel implements ActionListener{
	//�����ָ��Ϊ���µ�JSplitePane����
	private JSplitPane jsp1=new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
	private JPanel jp2=new JPanel();
	//������ť����
	int flag;
	String sql;
	DataBase db;
	private JButton jb2=new JButton("ȷ��");
	private JLabel jl3=new JLabel("������ҪԤԼ�����");
	private JLabel jl4=new JLabel("����������ѧ��");
	//��jsp1����ı���
	private JTextField jtxt3=new JTextField();
	private JTextField jtxt4=new JTextField();
	Vector<String> head = new Vector<String>();	//��������
	{
		head.add("���");
		head.add("����");
		head.add("����");
		head.add("������");
		head.add("�Ƿ����");
		head.add("�Ƿ�ԤԼ");
	}
	//��jp3�����ñ��
	Vector<Vector> data=new Vector<Vector>();
	//�������ģ��
	DefaultTableModel dtm=new DefaultTableModel(data,head);
	//����Jtable����
	JTable jt=new JTable(dtm);
	//��JTable��װ����������
	JScrollPane jspn=new JScrollPane(jt);
	public OrderBook()
	{
		this.setLayout(new GridLayout(1,1));
		//��jsp2���õ�jsp1���ϲ�����
		jsp1.setTopComponent(jp2);
		//����jsp1���²�����
		jsp1.setBottomComponent(jspn);
		//����jsp1��jsp2�зָ����ĳ�ʼλ��
		jsp1.setDividerLocation(80);
		//����jsp1��jsp2�зָ����Ŀ��
		jsp1.setDividerSize(4);
		//����jp1��jp2Ϊ�ղ��ֹ�����
		jp2.setLayout(null);
		//���ð�ť�Ĵ�С��λ��
		jb2.setBounds(500,30,60,20);
		//����ť��ӽ�JPanel
		jp2.add(jb2);
		jb2.addActionListener(this);
		//����JLabel������
		jl3.setBounds(30,30,120,20);
		jl4.setBounds(280,30,95,20);
		//��JLabel��ӽ�JPanel
		jp2.add(jl3);
		jp2.add(jl4);	
		jtxt3.setBounds(155,30,100,20);
		jtxt4.setBounds(380,30,100,20);
		jp2.add(jtxt3);
		jp2.add(jtxt4);
		this.add(jsp1);
		//���ô���ı��⣬��Сλ�ü��ɼ���
		this.setBounds(10,10,800,600);
		this.setVisible(true);  
	}	
	//Ϊ�¼����صļ��������ϴ����¼�

	public void actionPerformed(ActionEvent e){
		if(e.getSource()==jb2){
			if(jtxt4.getText().equals("")||jtxt3.getText().equals("")){//Ϊ����Ϊ�յ�������д���
				JOptionPane.showMessageDialog(this,"���벻��Ϊ�գ����������룡����",
						"��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			//��ѯѧ���ı�������ѧ���Ƿ������STUDENT����
			sql="select * from STUDENT where StuNO="+Integer.parseInt(jtxt4.getText().trim());
			db=new DataBase();
			db.selectDb(sql);					
			Vector<Vector> vtemp = new Vector<Vector>();
			try{
				if(!(db.rs.next())){//��ѧ�Ŵ��������ʾ�Ի���
					JOptionPane.showMessageDialog(this,"�����˴����ѧ��","��Ϣ",
							JOptionPane.INFORMATION_MESSAGE);
				}
				else{//�õ�����ѧ�ŵ�ѧ���������Ͱ༶
					String stuName=db.rs.getString(2).trim();
					String classes=db.rs.getString(5).trim();
					//					stuName = new String(stuName.getBytes("ISO-8859-1"),"gb2312");
					//					classes = new String(classes.getBytes("ISO-8859-1"),"gb2312");
					//��ѧ����ȷ�������ѧ���Ƿ���Ȩ�޽����ԤԼ
					if(db.rs.getString(8).trim().equals("��")){//����Ȩ���������ʾ�Ի���
						JOptionPane.showMessageDialog(this,"���޴�Ȩ�ޣ���","��Ϣ",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					else{//����Ȩ�ޣ�����������������Ƿ������Book����
						sql="select * from book where BookNO="
								+Integer.parseInt(jtxt3.getText().trim());
						db.selectDb(sql);

						//str6��Book���м�¼�е�6�str7���7��
						String str6=null;
						String str7=null;
						//���������������Ӧ�������������
						String bookName=null;
						String author=null;
						if(!(db.rs.next())){//��Book����û�и���ţ��������ʾ�Ի���
							JOptionPane.showMessageDialog(this,	"û����Ҫ���ҵ�����",
									"��Ϣ",JOptionPane.INFORMATION_MESSAGE);
							return;
						}

						Vector<String> v = new Vector<String>();
						for(int i=1;i<=7;i++){//˳��ﵽ���ѵ��Ľ���еĸ����¼
							if(i==1){//
								String str=db.rs.getString(i).trim();
								//									str=new String(str.getBytes("ISO-8859-1"),"gb2312");
								v.add(str);
							}
							if(i==2){//
								bookName=db.rs.getString(i).trim();
								//									bookName=new String(bookName.getBytes("ISO-8859-1"),"gb2312");
								v.add(bookName);
							}
							if(i==3){//
								author=db.rs.getString(i).trim();
								//									author=new String(author.getBytes("ISO-8859-1"),"gb2312");
								v.add(author);	
							}	

							if(i==4){//
								String str=db.rs.getString(i).trim();
								//									str=new String(str.getBytes("ISO-8859-1"),"gb2312");
								v.add(str);
							}
							if(i==5){//
								str6=db.rs.getString(i+1);
								//									str6=new String(str6.getBytes("ISO-8859-1"),"gb2312");
								v.add(str6);
							}
							if(i==6){//
								str7=db.rs.getString(i+1);
								//									str7=new String(str7.getBytes("ISO-8859-1"),"gb2312");
								v.add(str7);
							}

						}
						vtemp.add(v);//���½�����е�����
						dtm.setDataVector(vtemp,head);
						jt.updateUI();
						jt.repaint();


						if(str7.trim().equals("��")){	//�����Ѿ���ԤԼ�������ʾ��Ϣ��
							JOptionPane.showMessageDialog(this,
									"�����Ѿ���ԤԼ","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						else{//ԤԼ�ɹ�������ԤԼ��OrderedΪ�ǣ���ʾ�����Ѿ���ԤԼ
							sql="update BOOK set Ordered='��' where BookNO="
									+Integer.parseInt(jtxt3.getText().trim());
							int j=db.updateDb(sql);

							sql="insert into ORDERREPORT values("+Integer.parseInt(jtxt3.getText().trim())
									+",'"+stuName+"','"+classes+"','"+bookName+"',"
									+Integer.parseInt(jtxt4.getText().trim())+",'"+author+"')";
							int k=db.updateDb(sql);
							if(j!=0&&k!=0){
								//���ԤԼ�ɹ���Ϣ��
								JOptionPane.showMessageDialog(this,
										"ԤԼ�ɹ�","��Ϣ",JOptionPane.INFORMATION_MESSAGE);	
							}
						}						
					}
				}
			}
			catch(Exception ex){ex.printStackTrace();}
			db.dbClose();//�ر����ݿ�����
		}
	}
}
