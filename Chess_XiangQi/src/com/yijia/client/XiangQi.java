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
	JLabel jlHost=new JLabel("主机名");
	JLabel jlPort=new JLabel("端口号");
	JLabel jlNickName=new JLabel("昵称");
	JTextField jtfHost=new JTextField("10.3.76.116");
	JTextField jtfPort=new JTextField("9999");
	JTextField jtfNickName=new JTextField("Play1");
	JButton jbConnect=new JButton("连接");
	JButton jbDisconnect=new JButton("断开");
	JButton jbFail=new JButton("认输");
	JComboBox jcbNickList=new JComboBox();
	JButton jbChallenge=new JButton("挑战");
	JButton jbYChallenge=new JButton("接受挑战");
	JButton jbNChallenge=new JButton("拒绝挑战");
	
	JButton jbSend=new JButton("发送");
	JButton jbClear=new JButton("清空");
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
		jpy.add(this.jbConnect);//添加"连接"按钮
		this.jbDisconnect.setBounds(100,100,80,20);
		jpy.add(this.jbDisconnect);//添加"断开"按钮
		this.jcbNickList.setBounds(20,130,130,20);
		jpy.add(this.jcbNickList);//添加用于显示当前用户的下拉列表框
		this.jbChallenge.setBounds(10,160,80,20);
		jpy.add(this.jbChallenge);//添加"挑战"按钮
		this.jbFail.setBounds(100,160,80,20);
		jpy.add(this.jbFail);//添加"认输"按钮
		this.jbYChallenge.setBounds(5,190,86,20);
		jpy.add(this.jbYChallenge);//添加"接受挑战"按钮
		this.jbNChallenge.setBounds(100,190,86,20);
		jpy.add(this.jbNChallenge);//添加"拒绝挑战"按钮
		
		this.jscp.setBounds(5, 240, 180, 300);
		this.jta.setEditable(false);
		jpy.add(this.jscp);
		this.jscp1.setBounds(5, 550, 180, 100);
		jpy.add(this.jscp1);
		this.jbSend.setBounds(10, 680, 80, 20);
		jpy.add(this.jbSend);
		this.jbClear.setBounds(100, 680, 80, 20);
		jpy.add(this.jbClear);
		
		jpz.setLayout(null);//将棋盘设为空布局
		jpz.setBounds(0,0,700,700);//设置大小
		
	}




	private void addListener() {
		// TODO Auto-generated method stub
		this.jbConnect.addActionListener(this);//为"连接"按钮注册事件监听器
		this.jbDisconnect.addActionListener(this);//为"断开"按钮注册事件监听器
		this.jbChallenge.addActionListener(this);//为"挑战"按钮注册事件监听器
		this.jbFail.addActionListener(this);//为"认输"按钮注册事件监听器
		this.jbYChallenge.addActionListener(this);//为"同意挑战"按钮注册事件监听器
		this.jbNChallenge.addActionListener(this);//为"拒绝挑战"按钮注册事件监听器
		this.jbSend.addActionListener(this);
		this.jbClear.addActionListener(this);
	}

	private void initialState() {
		// TODO Auto-generated method stub
		this.jbDisconnect.setEnabled(false);//将"断开"按钮设为不可用
		this.jbChallenge.setEnabled(false);//将"挑战"按钮设为不可用
		this.jbYChallenge.setEnabled(false);//将"接受挑战"按钮设为不可用
		this.jbNChallenge.setEnabled(false);//将"拒绝挑战"按钮设为不可用
		this.jbFail.setEnabled(false);//将"认输"按钮设为不可用
		this.jta1.setEditable(false);
	}

	private void initialQiZi() {
		// TODO Auto-generated method stub
		qiZi[0][0]=new QiZi(color1,"車",0,0);
		qiZi[1][0]=new QiZi(color1,"馬",1,0);
		qiZi[2][0]=new QiZi(color1,"相",2,0);
		qiZi[3][0]=new QiZi(color1,"仕",3,0);
		qiZi[4][0]=new QiZi(color1,"帥",4,0);
		qiZi[5][0]=new QiZi(color1,"仕",5,0);
		qiZi[6][0]=new QiZi(color1,"相",6,0);
		qiZi[7][0]=new QiZi(color1,"馬",7,0);
		qiZi[8][0]=new QiZi(color1,"車",8,0);
		qiZi[1][2]=new QiZi(color1,"砲",1,2);
		qiZi[7][2]=new QiZi(color1,"砲",7,2);
		qiZi[0][3]=new QiZi(color1,"兵",0,3);
		qiZi[2][3]=new QiZi(color1,"兵",2,3);
		qiZi[4][3]=new QiZi(color1,"兵",4,3);
		qiZi[6][3]=new QiZi(color1,"兵",6,3);
		qiZi[8][3]=new QiZi(color1,"兵",8,3);
		qiZi[0][9]=new QiZi(color2,"車",0,9);
		qiZi[1][9]=new QiZi(color2,"馬",1,9);
		qiZi[2][9]=new QiZi(color2,"象",2,9);
		qiZi[3][9]=new QiZi(color2,"士",3,9);
		qiZi[4][9]=new QiZi(color2,"將",4,9);
		qiZi[5][9]=new QiZi(color2,"士",5,9);
		qiZi[6][9]=new QiZi(color2,"象",6,9);
		qiZi[7][9]=new QiZi(color2,"馬",7,9);
		qiZi[8][9]=new QiZi(color2,"車",8,9);
		qiZi[1][7]=new QiZi(color2,"炮",1,7);
		qiZi[7][7]=new QiZi(color2,"炮",7,7);
		qiZi[0][6]=new QiZi(color2,"卒",0,6);
		qiZi[2][6]=new QiZi(color2,"卒",2,6);
		qiZi[4][6]=new QiZi(color2,"卒",4,6);
		qiZi[6][6]=new QiZi(color2,"卒",6,6);
		qiZi[8][6]=new QiZi(color2,"卒",8,6);
	}

	private void initialFrame() {
		// TODO Auto-generated method stub
		this.setTitle("中国象棋--客户端");
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
			this.jta.append("我："+text+"\n");
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
		{//获得用户输入的断口号并转化为整型
			port=Integer.parseInt(this.jtfPort.getText().trim());
		}
		catch(Exception ee)
		{//不是整数，给出错误提示
			JOptionPane.showMessageDialog(this,"端口号只能是整数","错误",
			                              JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(port>65535||port<0)
		{//端口号不合法，给出错误提示
			JOptionPane.showMessageDialog(this,"端口号只能是0-65535的整数","错误",
			                              JOptionPane.ERROR_MESSAGE);
			return;
		}
		String name=this.jtfNickName.getText().trim();//获得昵称
		if(name.length()==0)
		{//昵称为空，给出错误提示信息
			JOptionPane.showMessageDialog(this,"玩家姓名不能为空","错误",
			                              JOptionPane.ERROR_MESSAGE);
			return;
		}
		try
		{
			sc=new Socket(this.jtfHost.getText().trim(),port);//创建Socket对象
			cat=new ClientAgentThread(this);//创建客户端代理线程
			cat.start();//启动客户端代理线程	
			this.jtfHost.setEnabled(false);//将用于输入主机名的文本框设为不可用
			this.jtfPort.setEnabled(false);//将用于输入端口号的文本框设为不可用
			this.jtfNickName.setEnabled(false);//将用于输入昵称的文本框设为不可用
			this.jbConnect.setEnabled(false);//将"连接"按钮设为不可用
			this.jbDisconnect.setEnabled(true);//将"断开"按钮设为可用
			this.jbChallenge.setEnabled(true);//将"挑战"按钮设为可用
			this.jbYChallenge.setEnabled(false);//将"接受挑战"按钮设为不可用
			this.jbNChallenge.setEnabled(false);//将"拒绝挑战"按钮设为不可用
			this.jbFail.setEnabled(false);//将"认输"按钮设为不可用
			JOptionPane.showMessageDialog(this,"已连接到服务器","提示",
			            JOptionPane.INFORMATION_MESSAGE);//连接成功，给出提示信息	
		}
		catch(Exception ee)
		{
			JOptionPane.showMessageDialog(this,"连接服务器失败","错误",
			        JOptionPane.ERROR_MESSAGE);//连接失败，给出提示信息
			return;
		}
	}

	private void jbDisconnect_event() {
		// TODO Auto-generated method stub
		try
		{
			this.cat.dout.writeUTF("<#CLIENT_LEAVE#>");//向服务器发送离开的信息
			this.cat.flag=false;//终止客户端代理线程
			this.cat=null;
			this.jtfHost.setEnabled(!false);//将用于输入主机名的文本框设为可用
			this.jtfPort.setEnabled(!false);//将用于输入端口号的文本框设为可用
			this.jtfNickName.setEnabled(!false);//将用于输入昵称的文本框设为可用
			this.jbConnect.setEnabled(!false);//将"连接"按钮设为可用
			this.jbDisconnect.setEnabled(!true);//将"断开"按钮设为不可用
			this.jbChallenge.setEnabled(!true);//将"挑战"按钮设为不可用
			this.jbYChallenge.setEnabled(false);//将"接受挑战"按钮设为不可用
			this.jbNChallenge.setEnabled(false);//将"拒绝挑战"按钮设为不可用
			this.jbFail.setEnabled(false);//将"认输"按钮设为不可用
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
			JOptionPane.showMessageDialog(this, "请选择对方名字","错误",
					JOptionPane.ERROR_MESSAGE);
		}else{
			String name2=(String)this.jcbNickList.getSelectedItem();
			try {
				this.jtfHost.setEnabled(false);//将用于输入主机名的文本框设为不可用
				this.jtfPort.setEnabled(false);//将用于输入端口号的文本框设为不可用
				this.jtfNickName.setEnabled(false);//将用于输入昵称的文本框设为不可用
				this.jbConnect.setEnabled(false);//将"连接"按钮设为不可用
				this.jbDisconnect.setEnabled(!true);//将"断开"按钮设为不可用
				this.jbChallenge.setEnabled(!true);//将"挑战"按钮设为不可用
				this.jbYChallenge.setEnabled(false);//将"接受挑战"按钮设为不可用
				this.jbNChallenge.setEnabled(false);//将"拒绝挑战"按钮设为不可用
				this.jbFail.setEnabled(false);//将"认输"按钮设为不可用
				this.cat.tiaoZhanZhe=name2;//设置挑战对象
//				this.caiPan=true;//将caiPan设为true
				this.color=0;//将color设为0
				this.cat.dout.writeUTF("<#TIAO_ZHAN#>"+name2);//发送挑战信息
				JOptionPane.showMessageDialog(this,"已提出挑战,请等待恢复...","提示",
				           JOptionPane.INFORMATION_MESSAGE);//给出信息发出的提示信息
			} catch (Exception ee) {
				// TODO: handle exception
				ee.printStackTrace();
			}
		}
	}

	private void jbYChallenge_event() {
		// TODO Auto-generated method stub
		try{	//发送同意挑战的信息
			this.cat.dout.writeUTF("<#TONG_YI#>"+this.cat.tiaoZhanZhe);
			this.caiPan=false;//将caiPan设为false
			this.color=1;//将color设为1
			this.jtfHost.setEnabled(false);//将用于输入主机名的文本框设为不可用
			this.jtfPort.setEnabled(false);//将用于输入端口号的文本框设为不可用
			this.jtfNickName.setEnabled(false);//将用于输入昵称的文本框设为不可用
			this.jbConnect.setEnabled(false);//将"连接"按钮设为不可用
			this.jbDisconnect.setEnabled(!true);//将"断开"按钮设为不可用
			this.jbChallenge.setEnabled(!true);//将"挑战"按钮设为不可用
			this.jbYChallenge.setEnabled(false);//将"接受挑战"按钮设为不可用
			this.jbNChallenge.setEnabled(false);//将"拒绝挑战"按钮设为不可用
			this.jbFail.setEnabled(!false);//将"认输"按钮设为可用
			this.jta1.setEditable(true);
		}
		catch(Exception ee){ee.printStackTrace();}
	}

	private void jbNChallenge_event() {
		// TODO Auto-generated method stub
		try{   //发送拒绝挑战的信息
			this.cat.dout.writeUTF("<#BUTONG_YI#>"+this.cat.tiaoZhanZhe);
			this.cat.tiaoZhanZhe=null;//将tiaoZhanZhe设为空
			this.jtfHost.setEnabled(false);//将用于输入主机名的文本框设为不可用
			this.jtfPort.setEnabled(false);//将用于输入端口号的文本框设为不可用
			this.jtfNickName.setEnabled(false);//将用于输入昵称的文本框设为不可用
			this.jbConnect.setEnabled(false);//将"连接"按钮设为不可用
			this.jbDisconnect.setEnabled(true);//将"断开"按钮设为可用
			this.jbChallenge.setEnabled(true);//将"挑战"按钮设为可用
			this.jbYChallenge.setEnabled(false);//将"接受挑战"按钮设为不可用
			this.jbNChallenge.setEnabled(false);//将"拒绝挑战"按钮设为不可用
			this.jbFail.setEnabled(false);//将"认输"按钮设为不可用
		}
		catch(Exception ee){ee.printStackTrace();}
	}

	private void jbFail_event() {
		// TODO Auto-generated method stub
		try{   //发送认输的信息
			this.cat.dout.writeUTF("<#RENSHU#>"+this.cat.tiaoZhanZhe);
			this.cat.tiaoZhanZhe=null;//将tiaoZhanZhe设为空
			this.color=0;//将color设为0
			this.caiPan=false;//将caiPan设为false
			this.next();//初始化下一局
			this.jtfHost.setEnabled(false);//将用于输入主机名的文本框设为不可用
			this.jtfPort.setEnabled(false);//将用于输入端口号的文本框设为不可用
			this.jtfNickName.setEnabled(false);//将用于输入昵称的文本框设为不可用
			this.jbConnect.setEnabled(false);//将"连接"按钮设为不可用
			this.jbDisconnect.setEnabled(true);//将"断开"按钮设为可用
			this.jbChallenge.setEnabled(true);//将"挑战"按钮设为可用
			this.jbYChallenge.setEnabled(false);//将"接受挑战"按钮设为不可用
			this.jbNChallenge.setEnabled(false);//将"拒绝挑战"按钮设为不可用
			this.jbFail.setEnabled(false);//将"认输"按钮设为不可用
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
