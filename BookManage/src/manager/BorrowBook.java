package manager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class BorrowBook extends JPanel implements ActionListener{
	//�����ָ��Ϊ���µ�JSplitePane����
	private JSplitPane jsp1=new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
	private JPanel jp2=new JPanel();
	//������ť����
	int flag;
	String sql;
	DataBase db;
	private JButton jb2=new JButton("ȷ��");
	private JLabel jl3=new JLabel("��Ҫ���Ļ�ԤԼ�����");
	private JLabel jl4=new JLabel("����������ѧ��");
	//��jsp1����ı���
	private JTextField jtxt3=new JTextField();
	private JTextField jtxt4=new JTextField();
	//��jp2���õ�ѡ��
	private JRadioButton[] jrbArray=
		{new JRadioButton("����ͼ��",true),new JRadioButton("ԤԼͼ��")};
	private ButtonGroup bg=new ButtonGroup();
	Vector<String> head = new Vector<String>();	//��������
	{
		head.add("���");
		head.add("����");
		head.add("����");
		head.add("������");
		head.add("�Ƿ����");
		head.add("�Ƿ�ԤԼ");
	}	
	Vector<Vector> data=new Vector<Vector>();//���������������    
	DefaultTableModel dtm=new DefaultTableModel(data,head);//�������ģ��   
	JTable jt=new JTable(dtm); //����Jtable����	
	JScrollPane jspn=new JScrollPane(jt);//��JTable�Ž���������
	public BorrowBook()
	{
		this.setLayout(new GridLayout(1,1));
		//��jsp2���õ�jsp1���ϲ�����
		jsp1.setTopComponent(jp2);
		//����jsp1���²�����
		jsp1.setBottomComponent(jspn);
		//����jsp1��jsp2�зָ����ĳ�ʼλ��
		jsp1.setDividerLocation(100);//���÷ָ�ؼ�λ��
		jsp1.setDividerSize(4);//���÷ָ�ؼ����
		jp2.setLayout(null);    	
		jb2.setBounds(380,20,100,20);//���ð�ť�Ĵ�С��λ��
		//����ť��ӽ�JPanel
		jp2.add(jb2);
		jb2.addActionListener(this);
		//����JLabel������
		jl3.setBounds(80,60,130,20);
		jl4.setBounds(330,60,100,20);
		//��JLabel��ӽ�JPanel
		jp2.add(jl3);
		jp2.add(jl4);	
		jtxt3.setBounds(220,60,100,20);
		jtxt4.setBounds(430,60,100,20);
		jp2.add(jtxt3);
		jp2.add(jtxt4);
		for(int i=0;i<2;i++)
		{
			jrbArray[i].setBounds(70+i*150,20,150,20);
			jp2.add(jrbArray[i]);
			bg.add(jrbArray[i]);
		}
		this.add(jsp1);
		//���ô���ı��⣬��Сλ�ü��ɼ���
		this.setBounds(10,10,800,600);
		this.setVisible(true);  
	}	
	//Ϊ�¼����صļ��������ϴ����¼�
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==jb2){
			if(jtxt4.getText().equals("")){//Ϊ����Ϊ�յ�������д���
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
					return;
					
				}
				else{//�õ�����ѧ�ŵ�ѧ���������Ͱ༶
					String stuName=db.rs.getString(2).trim();
					String classes=db.rs.getString(5).trim();
					//stuName = new String(stuName.getBytes("ISO-8859-1"),"gb2312");
					//classes = new String(classes.getBytes("ISO-8859-1"),"gb2312");
					//��ѧ����ȷ�������ѧ���Ƿ���Ȩ�޽����ԤԼ
					if(db.rs.getString(8).trim().equals("��")){//����Ȩ���������ʾ�Ի���
						JOptionPane.showMessageDialog(this,"���޴�Ȩ�ޣ���","��Ϣ",
								JOptionPane.INFORMATION_MESSAGE);
						return ;
					}
					else{//����Ȩ�ޣ�����������������Ƿ������Book����
						sql="select * from Book where BookNO="
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
							if(i==1){//
								String str=db.rs.getString(i).trim();
								//									str=new String(str.getBytes("ISO-8859-1"),"gb2312");
								v.add(str);
							}
							if(i==4){//
								String str=db.rs.getString(i).trim();
								//									str=new String(str.getBytes("ISO-8859-1"),"gb2312");
								v.add(str);
							}
						}
						vtemp.add(v);//���½�����е�����
						dtm.setDataVector(vtemp,head);
						jt.updateUI();
						jt.repaint();	

						if(jrbArray[0].isSelected()){//ѡ���˽�ͼ��
							if(str6.trim().equals("��")){//�û��������Ѿ������ߣ������ʾ��Ϣ��
								JOptionPane.showMessageDialog(this,
										"�����Ѿ�����","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
							}
							else if(str7.trim().equals("��")){//�û��������ѱ�Լ���ܽ裬��ʾ
								JOptionPane.showMessageDialog(this,
										"�����Ѿ���ԤԼ�������ٽ�","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
							}
							else{// �������ڶ����Ի�õ�ǰ��������¼����ʱ���Ӧ��ʱ��-
								Date now =new Date();
								sql="update BOOK set Borrowed='��' where BookNO="
										+Integer.parseInt(jtxt3.getText().trim());
								db.updateDb(sql);//�ɹ��������ø����Borrowed��Ϊ"��",�������ɹ���Ϣ��
								JOptionPane.showMessageDialog(this,
										"����ɹ�","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
								sql="insert into RECORD values("+Integer.parseInt(jtxt3.getText().trim())+","
										+Integer.parseInt(jtxt4.getText().trim())+",'"+(now.getYear()+1900)+"."
										+(now.getMonth()+1)+"."+now.getDate()+"',"+"'"+(now.getYear()+1900)+"."
										+(now.getMonth()+2)+"."+now.getDate()+"','��','��')";
								db.updateDb(sql);//�������¼����Record����
							}
						}	
						if(jrbArray[1].isSelected()){//ѡ����ԤԼͼ��
							if(str7.trim().equals("��")){	//�����Ѿ���ԤԼ�������ʾ��Ϣ��
								JOptionPane.showMessageDialog(this,
										"�����Ѿ���ԤԼ","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
							}
							else{//ԤԼ�ɹ�������ԤԼ��OrderedΪ�ǣ���ʾ�����Ѿ���ԤԼ
								sql="update BOOK set Ordered='��' where BookNO="
										+Integer.parseInt(jtxt3.getText().trim());
								db.updateDb(sql);
								//���ԤԼ�ɹ���Ϣ��
								JOptionPane.showMessageDialog(this,
										"ԤԼ�ɹ�","��Ϣ",JOptionPane.INFORMATION_MESSAGE);	
								sql="insert into ORDERREPORT values("+Integer.parseInt(jtxt3.getText().trim())
										+",'"+stuName+"','"+classes+"','"+bookName+"',"
										+Integer.parseInt(jtxt4.getText().trim())+",'"+author+"')";
								db.updateDb(sql);
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
