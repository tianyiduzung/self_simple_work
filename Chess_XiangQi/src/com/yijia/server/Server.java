package com.yijia.server;

import java.awt.Image;
import java.awt.event.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

public class Server extends JFrame implements ActionListener{

	JLabel jlPort=new JLabel("端口号");
	JTextField jtfPort=new JTextField("9999");
	JButton jbStart=new JButton("启动");
	JButton jbStop=new JButton("关闭");
	JPanel jps=new JPanel();
	JList jlUserOnline=new JList();
	JScrollPane jspx=new JScrollPane(jlUserOnline);
	JSplitPane jspz=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jspx,jps);

	ServerSocket ss;
	ServerThread st;
	Vector onlineList=new Vector();
	public Server(){
		this.initialComponent();
		this.addListener();
		this.initialFrame();
	}

	private void initialComponent() {
		// TODO Auto-generated method stub
		jps.setLayout(null);
		jlPort.setBounds(20,20,50,20);
		jps.add(jlPort);
		this.jtfPort.setBounds(85,20,60,20);
		jps.add(this.jtfPort);
		this.jbStart.setBounds(18, 50, 60, 20);
		jps.add(this.jbStart);
		this.jbStop.setBounds(85, 50, 60, 20);
		jps.add(this.jbStop);
		this.jbStop.setEnabled(false);
	}
	private void addListener() {
		// TODO Auto-generated method stub
		this.jbStart.addActionListener(this);
		this.jbStop.addActionListener(this);
	}

	private void initialFrame() {
		// TODO Auto-generated method stub
		this.setTitle("象棋--服务器端");
		Image image=new ImageIcon("ico.gif").getImage();
		this.setIconImage(image);
		this.add(jspz);
		jspz.setDividerLocation(250);
		jspz.setDividerSize(4);
		jspz.setBounds(20, 20, 420, 320);
		this.setBounds(20, 20, 420, 320);
		this.setVisible(true);
		this.addWindowListener(
				new WindowAdapter() {
					public void windowClosing(WindowEvent e){
						if(st==null){
							System.exit(0);
							return;
						}
						try{
							Vector v=onlineList;
							int size=v.size();
							for(int i=0;i<size;i++){
								ServerAgentThread tempSat=(ServerAgentThread)v.get(i);
								tempSat.dout.writeUTF("<#SERVER_DOWN#>");
								tempSat.flag=false;
							}
							st.flag=false;
							st=null;
							ss.close();
							v.clear();
							refreshList();
						}catch(Exception ee){
							ee.printStackTrace();
						}
						System.exit(0);
					}
				});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.jbStart){
			this.jbStart_event();
		}else if(e.getSource()==this.jbStop){
			this.jbStop_event();
		}
	}

	public void jbStart_event() {
		// TODO Auto-generated method stub
		int port=0;
		try {
			port=Integer.parseInt(this.jtfPort.getText().trim());
		} catch (Exception ee) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(this, "端口号只能是整数","错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(port>65535||port<0){
			JOptionPane.showMessageDialog(this, "端口号只能是0～65535的整数","错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			this.jbStart.setEnabled(false);
			this.jtfPort.setEnabled(false);
			this.jbStop.setEnabled(true);
			ss=new ServerSocket(port);
			st=new ServerThread(this);//创建开启一个服务器线程
			st.start();
			JOptionPane.showMessageDialog(this, "服务器启动成功","提示",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(this, "服务器启动失败","错误",
					JOptionPane.ERROR_MESSAGE);
			this.jbStart.setEnabled(true);
			this.jtfPort.setEnabled(true);
			this.jbStop.setEnabled(false);		
		}
	}

	public void jbStop_event() {
		// TODO Auto-generated method stub
		try {
			Vector v=onlineList;
			int size=v.size();
			for(int i=0;i<size;i++){
				ServerAgentThread tempSat=(ServerAgentThread)v.get(i);
				tempSat.dout.writeUTF("<#SERVER_DOWN#>");
				tempSat.flag=false;
			}
			st.flag=false;
			st=null;
			ss.close();
			v.clear();
			refreshList();
			this.jbStart.setEnabled(true);
			this.jtfPort.setEditable(true);
			this.jbStop.setEnabled(false);
		} catch (Exception ee) {
			// TODO: handle exception
			ee.printStackTrace();
		}
	}

	public void refreshList(){
		Vector v=new Vector();
		int size=this.onlineList.size();
		for(int i=0;i<size;i++){
			ServerAgentThread tempSat=(ServerAgentThread)this.onlineList.get(i);
			String temps=tempSat.sc.getInetAddress().toString();//得到IP地址
			temps=temps+"|"+tempSat.getName();
			v.add(temps);
		}
		this.jlUserOnline.setListData(v);
	}

	public static void main(String[] args) {
		new Server();
	}

}

