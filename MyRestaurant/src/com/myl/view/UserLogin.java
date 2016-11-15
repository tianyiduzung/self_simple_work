package com.myl.view;
import com.mhl.tools.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.imageio.*;
import java.io.*;
import com.mhl.model.*;
public class UserLogin extends 	JDialog implements ActionListener{
	
	//������Ҫ�����
	JLabel jl1, jl2, jl3;
	JTextField jName;
	JPasswordField jPasswd;
	JButton jCon, jCancel;
	
	public static void main(String [] args)
	{
		new UserLogin();
	}
	
	public UserLogin()
	{
		Container ct=this.getContentPane();
		//���óɿղ���
		this.setLayout(null);
		//�����������
		jl1=new JLabel("�������û���");
		jl1.setBounds(60, 190, 150, 15);
		jl1.setFont(MyTools.f1);
		//����
		ct.add(jl1);
		
		jName=new JTextField(20);
		jName.setFont(MyTools.f1);
		jName.setBounds(180, 190,120 , 30);
		//�����°��ĸо�
		jName.setBorder(BorderFactory.createLoweredBevelBorder());
		ct.add(jName);
		
		jl2=new JLabel ("(Ա����)");
		jl2.setFont(MyTools.f2);
		//����ǰ��ɫ
		jl2.setForeground(Color.red);
		jl2.setBounds(100, 210, 100, 30);
		ct.add(jl2);
		
		//��ʾ��������
		jl3=new JLabel ("����������");
		jl3.setFont(MyTools.f1);
		jl3.setBounds(60,240,150,30);
		ct.add(jl3);
		
		//�����
		jPasswd=new JPasswordField(20);
		jPasswd.setFont(MyTools.f1);
		jPasswd.setBounds(180, 240,120 , 30);
		//�����°��ĸо�
		jPasswd.setBorder(BorderFactory.createLoweredBevelBorder());
		ct.add(jPasswd);
		
		
		jCon=new JButton("ȷ��");
		jCon.addActionListener(this);
		jCon.setFont(MyTools.f1);
		jCon.setBounds(110, 300, 70, 30);
		//���뵽����
		ct.add(jCon);
		
		//����ȡ����ť
		jCancel=new JButton("ȡ��");
		jCancel.addActionListener(this);
		jCancel.setFont(MyTools.f1);
		jCancel.setBounds(220, 300, 70, 30);
		ct.add(jCancel);
		
		
//		jl2=new JLabel("�������û���");
//		jl3=new JLabel("�������û���");
		
		
		
		//����һ��BackImage����
		BackImage bi=new BackImage();
		//����ͼƬ��λ��
		bi.setBounds(0, 0, 360, 360);
		//���������¿�
		//��һ��������뵽JFrame,����JDialog�п���ֱ�ӷ���
	//	this.add(bi);
		//Ҳ����������
		
		ct.add(bi);
		this.setUndecorated(true);
		this.setSize(360,360);
		//ȷ��JWindow�ĳ�ʼλ��
		int width=Toolkit.getDefaultToolkit().getScreenSize().width;
		int height=Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(width/2-200, height/2-150);
		this.setVisible(true);
	}
	//�ڲ���
	class BackImage extends JPanel
	{
		Image im;
		public BackImage()
		{
			try {
				im=ImageIO.read(new File("image\\login.gif"));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		public void paintComponent(Graphics g)
		{
			g.drawImage(im, 0,0, 360, 360, this);
		}
	}
	//��Ӧ�û���¼������
	public void actionPerformed(ActionEvent arg0) {
		
		//�ж��Ƿ���ȷ����ť
		if(arg0.getSource()==jCon)
		{
			//ȡ��Ա���ź�����
			String u=this.jName.getText().trim();
			String p=new String (this.jPasswd.getPassword());
			UserModel um=new UserModel();
			String res=um.checkUser(u, p);
			if(res.equals("����")||res.equals("����")||res.equals("����Ա"))
			{
				new Windows1();
				//�رյ�¼����
				this.dispose();
			}
		}else if(arg0.getSource()==this.jCancel)
		{
			this.dispose();
		}
		
	}
}
