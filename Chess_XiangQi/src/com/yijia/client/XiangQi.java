package com.yijia.client;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;

import javax.swing.*;

public class XiangQi extends JFrame implements ActionListener{

	public static final Color bgColor=new Color(245,250,160);
	public static final Color focusbg1=new Color(249,183,173);
	public static final Color focusbg2=Color.white;
	public static final Color focuschar=new Color(96,95,91);
	public static final Color focusline1=new Color(255,0,0);
	public static final Color focusline2=new Color(0,191,255);
	
	public static final Color color1=new Color(249,183,173);
	public static final Color color2=Color.white;
	JLabel jlHost=new JLabel("������");
	JLabel jlPort=new JLabel("�˿ں�");
	JLabel jlNickName=new JLabel("�ǳ�");
	JTextField jtfHost=new JTextField("10.3.76.116");
	JTextField jtfPort=new JTextField("9999");
	JTextField jtfNickName=new JTextField("Play1");
	JButton jbConnect=new JButton("����");
	JButton jbDisconnect=new JButton("�Ͽ�");
	JButton jbFail=new JButton("����");
	JComboBox jcbNickList=new JComboBox();
	JButton jbChallenge=new JButton("��ս");
	JButton jbYChallenge=new JButton("������ս");
	JButton jbNChallenge=new JButton("�ܾ���ս");
	
	JButton jbSend=new JButton("����");
	JButton jbClear=new JButton("���");
	JTextArea jta=new JTextArea();
	JTextArea jta1=new JTextArea();
	JScrollPane jscp=new JScrollPane(jta);
	JScrollPane jscp1=new JScrollPane(jta1);
	
	int width=60;
	QiZi[][] qiZi=new QiZi[9][10];
	QiPan jpz=new QiPan(qiZi,width,this);
	//JPanel jpz=new JPanel();
	JPanel jpy=new JPanel();
	JSplitPane jsp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jpz,jpy);
	
	boolean caiPan=false;
	int color=0;
	Socket sc;
	ClientAgentThread cat;
	public XiangQi(){
		this.initialComponent();
		this.addListener();
		this.initialState();
		this.initialQiZi();
		this.initialFrame();
	}
	
	private void initialComponent() {
		// TODO Auto-generated method stub
		jpy.setLayout(null);
		this.jlHost.setBounds(10, 10, 50, 20);
		jpy.add(this.jlHost);
		this.jtfHost.setBounds(70, 10, 80, 20);
		jpy.add(this.jtfHost);
		this.jlPort.setBounds(10, 40, 50, 20);
		jpy.add(this.jlPort);
		this.jtfPort.setBounds(70, 40, 80, 20);
		jpy.add(this.jtfPort);
		this.jlNickName.setBounds(10, 70, 50, 20);
		jpy.add(this.jlNickName);
		this.jtfNickName.setBounds(70, 70, 80, 20);
		jpy.add(this.jtfNickName);
		this.jbConnect.setBounds(10,100,80,20);
		jpy.add(this.jbConnect);//���"����"��ť
		this.jbDisconnect.setBounds(100,100,80,20);
		jpy.add(this.jbDisconnect);//���"�Ͽ�"��ť
		this.jcbNickList.setBounds(20,130,130,20);
		jpy.add(this.jcbNickList);//���������ʾ��ǰ�û��������б��
		this.jbChallenge.setBounds(10,160,80,20);
		jpy.add(this.jbChallenge);//���"��ս"��ť
		this.jbFail.setBounds(100,160,80,20);
		jpy.add(this.jbFail);//���"����"��ť
		this.jbYChallenge.setBounds(5,190,86,20);
		jpy.add(this.jbYChallenge);//���"������ս"��ť
		this.jbNChallenge.setBounds(100,190,86,20);
		jpy.add(this.jbNChallenge);//���"�ܾ���ս"��ť
		
		this.jscp.setBounds(5, 240, 180, 300);
		this.jta.setEditable(false);
		jpy.add(this.jscp);
		this.jscp1.setBounds(5, 550, 180, 100);
		jpy.add(this.jscp1);
		this.jbSend.setBounds(10, 680, 80, 20);
		jpy.add(this.jbSend);
		this.jbClear.setBounds(100, 680, 80, 20);
		jpy.add(this.jbClear);
		
		jpz.setLayout(null);//��������Ϊ�ղ���
		jpz.setBounds(0,0,700,700);//���ô�С
		
	}




	private void addListener() {
		// TODO Auto-generated method stub
		this.jbConnect.addActionListener(this);//Ϊ"����"��ťע���¼�������
		this.jbDisconnect.addActionListener(this);//Ϊ"�Ͽ�"��ťע���¼�������
		this.jbChallenge.addActionListener(this);//Ϊ"��ս"��ťע���¼�������
		this.jbFail.addActionListener(this);//Ϊ"����"��ťע���¼�������
		this.jbYChallenge.addActionListener(this);//Ϊ"ͬ����ս"��ťע���¼�������
		this.jbNChallenge.addActionListener(this);//Ϊ"�ܾ���ս"��ťע���¼�������
		this.jbSend.addActionListener(this);
		this.jbClear.addActionListener(this);
	}

	private void initialState() {
		// TODO Auto-generated method stub
		this.jbDisconnect.setEnabled(false);//��"�Ͽ�"��ť��Ϊ������
		this.jbChallenge.setEnabled(false);//��"��ս"��ť��Ϊ������
		this.jbYChallenge.setEnabled(false);//��"������ս"��ť��Ϊ������
		this.jbNChallenge.setEnabled(false);//��"�ܾ���ս"��ť��Ϊ������
		this.jbFail.setEnabled(false);//��"����"��ť��Ϊ������
		this.jta1.setEditable(false);
	}

	private void initialQiZi() {
		// TODO Auto-generated method stub
		qiZi[0][0]=new QiZi(color1,"܇",0,0);
		qiZi[1][0]=new QiZi(color1,"�R",1,0);
		qiZi[2][0]=new QiZi(color1,"��",2,0);
		qiZi[3][0]=new QiZi(color1,"��",3,0);
		qiZi[4][0]=new QiZi(color1,"��",4,0);
		qiZi[5][0]=new QiZi(color1,"��",5,0);
		qiZi[6][0]=new QiZi(color1,"��",6,0);
		qiZi[7][0]=new QiZi(color1,"�R",7,0);
		qiZi[8][0]=new QiZi(color1,"܇",8,0);
		qiZi[1][2]=new QiZi(color1,"�h",1,2);
		qiZi[7][2]=new QiZi(color1,"�h",7,2);
		qiZi[0][3]=new QiZi(color1,"��",0,3);
		qiZi[2][3]=new QiZi(color1,"��",2,3);
		qiZi[4][3]=new QiZi(color1,"��",4,3);
		qiZi[6][3]=new QiZi(color1,"��",6,3);
		qiZi[8][3]=new QiZi(color1,"��",8,3);
		qiZi[0][9]=new QiZi(color2,"܇",0,9);
		qiZi[1][9]=new QiZi(color2,"�R",1,9);
		qiZi[2][9]=new QiZi(color2,"��",2,9);
		qiZi[3][9]=new QiZi(color2,"ʿ",3,9);
		qiZi[4][9]=new QiZi(color2,"��",4,9);
		qiZi[5][9]=new QiZi(color2,"ʿ",5,9);
		qiZi[6][9]=new QiZi(color2,"��",6,9);
		qiZi[7][9]=new QiZi(color2,"�R",7,9);
		qiZi[8][9]=new QiZi(color2,"܇",8,9);
		qiZi[1][7]=new QiZi(color2,"��",1,7);
		qiZi[7][7]=new QiZi(color2,"��",7,7);
		qiZi[0][6]=new QiZi(color2,"��",0,6);
		qiZi[2][6]=new QiZi(color2,"��",2,6);
		qiZi[4][6]=new QiZi(color2,"��",4,6);
		qiZi[6][6]=new QiZi(color2,"��",6,6);
		qiZi[8][6]=new QiZi(color2,"��",8,6);
	}

	private void initialFrame() {
		// TODO Auto-generated method stub
		this.setTitle("�й�����--�ͻ���");
		Image image=new ImageIcon("ico.gif").getImage();
		this.setIconImage(image);
		this.add(this.jsp);
		jsp.setDividerLocation(730);
		jsp.setDividerSize(4);
		this.setBounds(30, 30, 930, 730);
		this.setVisible(true);
		this.addWindowListener(
				new WindowAdapter(){
					public void windowClosing(WindowEvent e){
						if(cat==null){
							System.exit(0);
							return;
						}
						try {
							if(cat.tiaoZhanZhe!=null){
								try {
									cat.dout.writeUTF("<#RENSHU#>"+cat.tiaoZhanZhe);
								} catch (Exception e2) {
									// TODO: handle exception
									e2.printStackTrace();
								}
							}
							cat.dout.writeUTF("<#CLIENT_LEAVE#>");
							cat.flag=false;
							cat=null;
						} catch (Exception e2) {
							// TODO: handle exception
							e2.printStackTrace();
						}
						System.exit(0);
					}
				}
		);
				
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.jbConnect){
			this.jbConnect_event();
		}else if(e.getSource()==this.jbDisconnect){
			this.jbDisconnect_event();
		}else if(e.getSource()==this.jbChallenge){
			this.jbChallenge_event();
		}else if(e.getSource()==this.jbYChallenge){
			this.jbYChallenge_event();
		}else if(e.getSource()==this.jbNChallenge){
			this.jbNChallenge_event();
		}else if(e.getSource()==this.jbFail){
			this.jbFail_event();
		}else if(e.getSource()==this.jbSend){
			this.jbSend_event();
		}else if(e.getSource()==jbClear){
			this.jta1.setText("");
		}
	}

	private void jbSend_event() {
		// TODO Auto-generated method stub
		String text=jta1.getText();
		String name2=(String)this.jcbNickList.getSelectedItem();
		try {
			this.jta.append("�ң�"+text+"\n");
			this.cat.dout.writeUTF("<#SEND#>"+name2+"<#SEND#>"+text);
			this.jta1.setText("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void jbConnect_event() {
		// TODO Auto-generated method stub
		int port=0;
		try
		{//����û�����ĶϿںŲ�ת��Ϊ����
			port=Integer.parseInt(this.jtfPort.getText().trim());
		}
		catch(Exception ee)
		{//��������������������ʾ
			JOptionPane.showMessageDialog(this,"�˿ں�ֻ��������","����",
			                              JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(port>65535||port<0)
		{//�˿ںŲ��Ϸ�������������ʾ
			JOptionPane.showMessageDialog(this,"�˿ں�ֻ����0-65535������","����",
			                              JOptionPane.ERROR_MESSAGE);
			return;
		}
		String name=this.jtfNickName.getText().trim();//����ǳ�
		if(name.length()==0)
		{//�ǳ�Ϊ�գ�����������ʾ��Ϣ
			JOptionPane.showMessageDialog(this,"�����������Ϊ��","����",
			                              JOptionPane.ERROR_MESSAGE);
			return;
		}
		try
		{
			sc=new Socket(this.jtfHost.getText().trim(),port);//����Socket����
			cat=new ClientAgentThread(this);//�����ͻ��˴����߳�
			cat.start();//�����ͻ��˴����߳�	
			this.jtfHost.setEnabled(false);//�������������������ı�����Ϊ������
			this.jtfPort.setEnabled(false);//����������˿ںŵ��ı�����Ϊ������
			this.jtfNickName.setEnabled(false);//�����������ǳƵ��ı�����Ϊ������
			this.jbConnect.setEnabled(false);//��"����"��ť��Ϊ������
			this.jbDisconnect.setEnabled(true);//��"�Ͽ�"��ť��Ϊ����
			this.jbChallenge.setEnabled(true);//��"��ս"��ť��Ϊ����
			this.jbYChallenge.setEnabled(false);//��"������ս"��ť��Ϊ������
			this.jbNChallenge.setEnabled(false);//��"�ܾ���ս"��ť��Ϊ������
			this.jbFail.setEnabled(false);//��"����"��ť��Ϊ������
			JOptionPane.showMessageDialog(this,"�����ӵ�������","��ʾ",
			            JOptionPane.INFORMATION_MESSAGE);//���ӳɹ���������ʾ��Ϣ	
		}
		catch(Exception ee)
		{
			JOptionPane.showMessageDialog(this,"���ӷ�����ʧ��","����",
			        JOptionPane.ERROR_MESSAGE);//����ʧ�ܣ�������ʾ��Ϣ
			return;
		}
	}

	private void jbDisconnect_event() {
		// TODO Auto-generated method stub
		try
		{
			this.cat.dout.writeUTF("<#CLIENT_LEAVE#>");//������������뿪����Ϣ
			this.cat.flag=false;//��ֹ�ͻ��˴����߳�
			this.cat=null;
			this.jtfHost.setEnabled(!false);//�������������������ı�����Ϊ����
			this.jtfPort.setEnabled(!false);//����������˿ںŵ��ı�����Ϊ����
			this.jtfNickName.setEnabled(!false);//�����������ǳƵ��ı�����Ϊ����
			this.jbConnect.setEnabled(!false);//��"����"��ť��Ϊ����
			this.jbDisconnect.setEnabled(!true);//��"�Ͽ�"��ť��Ϊ������
			this.jbChallenge.setEnabled(!true);//��"��ս"��ť��Ϊ������
			this.jbYChallenge.setEnabled(false);//��"������ս"��ť��Ϊ������
			this.jbNChallenge.setEnabled(false);//��"�ܾ���ս"��ť��Ϊ������
			this.jbFail.setEnabled(false);//��"����"��ť��Ϊ������
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}

	private void jbChallenge_event() {
		// TODO Auto-generated method stub
		Object o=this.jcbNickList.getSelectedItem();
		if(o==null||((String)o).equals("")){
			JOptionPane.showMessageDialog(this, "��ѡ��Է�����","����",
					JOptionPane.ERROR_MESSAGE);
		}else{
			String name2=(String)this.jcbNickList.getSelectedItem();
			try {
				this.jtfHost.setEnabled(false);//�������������������ı�����Ϊ������
				this.jtfPort.setEnabled(false);//����������˿ںŵ��ı�����Ϊ������
				this.jtfNickName.setEnabled(false);//�����������ǳƵ��ı�����Ϊ������
				this.jbConnect.setEnabled(false);//��"����"��ť��Ϊ������
				this.jbDisconnect.setEnabled(!true);//��"�Ͽ�"��ť��Ϊ������
				this.jbChallenge.setEnabled(!true);//��"��ս"��ť��Ϊ������
				this.jbYChallenge.setEnabled(false);//��"������ս"��ť��Ϊ������
				this.jbNChallenge.setEnabled(false);//��"�ܾ���ս"��ť��Ϊ������
				this.jbFail.setEnabled(false);//��"����"��ť��Ϊ������
				this.cat.tiaoZhanZhe=name2;//������ս����
//				this.caiPan=true;//��caiPan��Ϊtrue
				this.color=0;//��color��Ϊ0
				this.cat.dout.writeUTF("<#TIAO_ZHAN#>"+name2);//������ս��Ϣ
				JOptionPane.showMessageDialog(this,"�������ս,��ȴ��ָ�...","��ʾ",
				           JOptionPane.INFORMATION_MESSAGE);//������Ϣ��������ʾ��Ϣ
			} catch (Exception ee) {
				// TODO: handle exception
				ee.printStackTrace();
			}
		}
	}

	private void jbYChallenge_event() {
		// TODO Auto-generated method stub
		try{	//����ͬ����ս����Ϣ
			this.cat.dout.writeUTF("<#TONG_YI#>"+this.cat.tiaoZhanZhe);
			this.caiPan=false;//��caiPan��Ϊfalse
			this.color=1;//��color��Ϊ1
			this.jtfHost.setEnabled(false);//�������������������ı�����Ϊ������
			this.jtfPort.setEnabled(false);//����������˿ںŵ��ı�����Ϊ������
			this.jtfNickName.setEnabled(false);//�����������ǳƵ��ı�����Ϊ������
			this.jbConnect.setEnabled(false);//��"����"��ť��Ϊ������
			this.jbDisconnect.setEnabled(!true);//��"�Ͽ�"��ť��Ϊ������
			this.jbChallenge.setEnabled(!true);//��"��ս"��ť��Ϊ������
			this.jbYChallenge.setEnabled(false);//��"������ս"��ť��Ϊ������
			this.jbNChallenge.setEnabled(false);//��"�ܾ���ս"��ť��Ϊ������
			this.jbFail.setEnabled(!false);//��"����"��ť��Ϊ����
			this.jta1.setEditable(true);
		}
		catch(Exception ee){ee.printStackTrace();}
	}

	private void jbNChallenge_event() {
		// TODO Auto-generated method stub
		try{   //���;ܾ���ս����Ϣ
			this.cat.dout.writeUTF("<#BUTONG_YI#>"+this.cat.tiaoZhanZhe);
			this.cat.tiaoZhanZhe=null;//��tiaoZhanZhe��Ϊ��
			this.jtfHost.setEnabled(false);//�������������������ı�����Ϊ������
			this.jtfPort.setEnabled(false);//����������˿ںŵ��ı�����Ϊ������
			this.jtfNickName.setEnabled(false);//�����������ǳƵ��ı�����Ϊ������
			this.jbConnect.setEnabled(false);//��"����"��ť��Ϊ������
			this.jbDisconnect.setEnabled(true);//��"�Ͽ�"��ť��Ϊ����
			this.jbChallenge.setEnabled(true);//��"��ս"��ť��Ϊ����
			this.jbYChallenge.setEnabled(false);//��"������ս"��ť��Ϊ������
			this.jbNChallenge.setEnabled(false);//��"�ܾ���ս"��ť��Ϊ������
			this.jbFail.setEnabled(false);//��"����"��ť��Ϊ������
		}
		catch(Exception ee){ee.printStackTrace();}
	}

	private void jbFail_event() {
		// TODO Auto-generated method stub
		try{   //�����������Ϣ
			this.cat.dout.writeUTF("<#RENSHU#>"+this.cat.tiaoZhanZhe);
			this.cat.tiaoZhanZhe=null;//��tiaoZhanZhe��Ϊ��
			this.color=0;//��color��Ϊ0
			this.caiPan=false;//��caiPan��Ϊfalse
			this.next();//��ʼ����һ��
			this.jtfHost.setEnabled(false);//�������������������ı�����Ϊ������
			this.jtfPort.setEnabled(false);//����������˿ںŵ��ı�����Ϊ������
			this.jtfNickName.setEnabled(false);//�����������ǳƵ��ı�����Ϊ������
			this.jbConnect.setEnabled(false);//��"����"��ť��Ϊ������
			this.jbDisconnect.setEnabled(true);//��"�Ͽ�"��ť��Ϊ����
			this.jbChallenge.setEnabled(true);//��"��ս"��ť��Ϊ����
			this.jbYChallenge.setEnabled(false);//��"������ս"��ť��Ϊ������
			this.jbNChallenge.setEnabled(false);//��"�ܾ���ս"��ť��Ϊ������
			this.jbFail.setEnabled(false);//��"����"��ť��Ϊ������
			this.jta1.setEditable(false);
		}
		catch(Exception ee){ee.printStackTrace();}	
	}
	
	public void next(){
		for(int i=0;i<9;i++){
			for(int j=0;j<10;j++){
				this.qiZi[i][j]=null;
			}
		}
		this.caiPan=false;
		this.initialQiZi();
		this.repaint();
	}
	
	public static void main(String[] args) {
		new XiangQi();
	}

}
