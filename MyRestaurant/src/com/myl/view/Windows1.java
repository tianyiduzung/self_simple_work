/**
 * �����ϵͳ����Ա���������ܿ��Խ���Ĳ�������
 * ��ɽ����˳�򣬴��ϵ��£������ң�
 */
package com.myl.view;
import com.mhl.tools.*;
import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;

import javax.imageio.*;
import java.io.*;
import java.util.*;
public class Windows1 extends JFrame implements ActionListener , MouseListener{

	//������Ҫ�����
	Image titleIcon, timeGg;    //����Сͼ��  ��������ϵ�Сͼ��
	JMenuBar jmb;     //����˵���
	//����һ���˵�
	JMenu jm1, jm2, jm3, jm4, jm5, jm6;   //����˵�ѡ��
	//��������˵�
	JMenuItem jmm1, jmm2, jmm3, jmm4, jmm5;   
	
	//ͼ��
	ImageIcon jmm1_icon1, jmm1_icon2, jmm1_icon3, jmm1_icon4, jmm1_icon5;
	//������
	JToolBar jtb;
	JButton jb1, jb2, jb3, jb4, jb5, jb6, jb7, jb8, jb9, jb10;
	//������Ҫ�����JPanel
	JPanel p1, p2, p3, p4, p5;
	//��ʾ��ǰʱ��
	JLabel timeNow;
	JLabel p1_lab1,p1_lab2,p1_lab3,p1_lab4,p1_lab5,p1_lab6,p1_lab7,p1_lab8;
	//��p2��嶨����Ҫ��JLable
	JLabel p2_lab1, p2_lab2;
	//javax.swing ���е�Timer���Զ�ʱ�Ĵ���Action�¼��� ���ǿ������������һЩ��
	javax.swing.Timer t;
	ImagePanel p1_imgPanel;
	JSplitPane jsp1;
	CardLayout cardp3;
	CardLayout cardp2;
	Container ct;
	public static void main(String[] args) {
		
		new Windows1();
	}
	
	//��ʼ���м��Panel
	public void initallPanels()
	{
		//����p1���
		p1=new JPanel(new BorderLayout());
		Image p1_bg=null;
		try {
			p1_bg = ImageIO.read(new File("image\\jp1.jpg"));
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		
		//����һ����״���
		Cursor myCursor=new Cursor(Cursor.HAND_CURSOR);
		
		this.p1_imgPanel=new ImagePanel(p1_bg);
		this.p1_imgPanel.setLayout(new GridLayout(8,1));
		//p1�ĵ�һ��Label
		p1_lab1=new JLabel(new ImageIcon("image\\label_1.gif"));
		p1_lab1.setFont(MyTools.f3);
		p1_imgPanel.add(p1_lab1);
		//�ڶ���label
		p1_lab2=new JLabel("�� �� �� ��",new ImageIcon("image\\label_2.jpg"),0);
		p1_lab2.setFont(MyTools.f3);
		p1_lab2.setCursor(myCursor);
		//�ø�lable ������
		p1_lab2.setEnabled(false);
		//ע������
		p1_lab2.addMouseListener(this);
		p1_imgPanel.add(p1_lab2);
		//������
		p1_lab3=new JLabel("�� ¼ �� ��",new ImageIcon("image\\label_3.jpg"),0);
		p1_lab3.setFont(MyTools.f3);
		p1_lab3.setCursor(myCursor);
		p1_lab3.setEnabled(false);
		p1_lab3.addMouseListener(this);
		p1_imgPanel.add(p1_lab3);
		p1_lab4=new JLabel("�� �� �� ��",new ImageIcon("image\\label_4.jpg"),0);
		p1_lab4.setFont(MyTools.f3);
		p1_lab4.setCursor(myCursor);
		p1_lab4.setEnabled(false);
		p1_lab4.addMouseListener(this);
		p1_imgPanel.add(p1_lab4);
		p1_lab5=new JLabel("�� �� ͳ ��",new ImageIcon("image\\label_5.jpg"),0);
		p1_lab5.setFont(MyTools.f3);
		p1_lab5.setCursor(myCursor);
		p1_lab5.setEnabled(false);
		p1_lab5.addMouseListener(this);
		p1_imgPanel.add(p1_lab5);
		p1_lab6=new JLabel("�� �� �� �� ��",new ImageIcon("image\\label_6.jpg"),0);
		p1_lab6.setFont(MyTools.f3);
		p1_lab6.setCursor(myCursor);
		p1_lab6.setEnabled(false);
		p1_lab6.addMouseListener(this);
		p1_imgPanel.add(p1_lab6);
		p1_lab7=new JLabel("ϵ ͳ �� ��",new ImageIcon("image\\label_7.jpg"),0);
		p1_lab7.setFont(MyTools.f3);
		p1_lab7.setCursor(myCursor);
		p1_lab7.setEnabled(false);
		p1_lab7.addMouseListener(this);
		p1_imgPanel.add(p1_lab7);
		p1_lab8=new JLabel("�� �� �� ��",new ImageIcon("image\\label_8.jpg"),0);
		p1_lab8.setFont(MyTools.f3);
		p1_lab8.setCursor(myCursor);
		p1_lab8.setEnabled(false);
		p1_lab8.addMouseListener(this);
		p1_imgPanel.add(p1_lab8);
		p1.add(this.p1_imgPanel);
		
		
		//����p2, p3, p4,
		p4=new JPanel(new BorderLayout());
		cardp2=new CardLayout();
		p2=new JPanel(this.cardp2);
		p2_lab1=new JLabel(new ImageIcon("image\\jp2_left.jpg"));
		p2_lab1.addMouseListener(this);
		p2_lab2=new JLabel(new ImageIcon("image\\jp2_right.jpg"));
		p2_lab2.addMouseListener(this);
		//��p2_lab1...���뵽p2��
		p2.add(p2_lab1,"0");
		p2.add(p2_lab2,"1");
	
		this.cardp3=new CardLayout();
		p3=new JPanel(this.cardp3);
		//�ȸ�p3����һ�������濨Ƭ
		Image zhu_image=null;
		try {
			zhu_image = ImageIO.read(new File("image\\zhujiemian.jpg"));
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		ImagePanel ip=new  ImagePanel(zhu_image);
		p3.add(ip, "0");
		//��p3����Ӽ���JLable
		//����EmpInfo���� JPanel
		EmpInfo eInfo=new EmpInfo();
		p3.add(eInfo,"1");
		JLabel dlgl=new JLabel (new ImageIcon("image\\dlgl.jpg"));
		p3.add(dlgl,"2");
		//��p2,p3, ���뵽p4
		p4.add(p2,"West");
		p4.add(p3,"Center");
	}
	
	//��ʼ��������
	public void initToolBar()
	{
		//�������������
		jtb=new JToolBar();
		jb1=new JButton(new ImageIcon("image\\jb1.jpg"));
		jb2=new JButton(new ImageIcon("image\\jb2.jpg"));
		jb3=new JButton(new ImageIcon("image\\jb3.jpg"));
		jb4=new JButton(new ImageIcon("image\\jb4.jpg"));
		jb5=new JButton(new ImageIcon("image\\jb5.jpg"));
		jb6=new JButton(new ImageIcon("image\\jb6.jpg"));
		jb7=new JButton(new ImageIcon("image\\jb7.jpg"));
		jb8=new JButton(new ImageIcon("image\\jb8.jpg"));
		jb9=new JButton(new ImageIcon("image\\jb9.jpg"));
		jb10=new JButton(new ImageIcon("image\\jb10.jpg"));
		//�Ѱ�ť�ӵ�jb1��
		jtb.add(jb1);
		jtb.add(jb2);
		jtb.add(jb3);
		jtb.add(jb4);
		jtb.add(jb5);
		jtb.add(jb6);
		jtb.add(jb7);
		jtb.add(jb8);
		jtb.add(jb9);
		jtb.add(jb10);
	}
	
	//��ʼ���˵�
	public void initMenu()
	{
		//����ͼ��
		jmm1_icon1=new ImageIcon("image\\jm1_icon1.jpg");
		jmm1_icon2=new ImageIcon("image\\jm1_icon2.jpg");
		jmm1_icon3=new ImageIcon("image\\jm1_icon3.jpg");
		jmm1_icon4=new ImageIcon("image\\jm1_icon4.jpg");
		jmm1_icon5=new ImageIcon("image\\jm1_icon5.jpg");
		
		//����һ���˵�
		jm1=new JMenu("ϵͳ����");
		jm1.setFont(MyTools.f1);
		//���������˵�
		jmm1=new JMenuItem("�л����û�����", jmm1_icon1);
		jmm1.setFont(MyTools.f2);
		jmm2=new JMenuItem("�л����տ����", jmm1_icon2);
		jmm2.setFont(MyTools.f2);
		jmm3=new JMenuItem("��¼����", jmm1_icon3);
		jmm3.setFont(MyTools.f2);
		jmm4=new JMenuItem("������", jmm1_icon4);
		jmm4.setFont(MyTools.f2);
		jmm5=new JMenuItem("�˳�", jmm1_icon5);
		jmm5.setFont(MyTools.f2);
		//����
		jm1.add(jmm1);
		jm1.add(jmm2);
		jm1.add(jmm3);
		jm1.add(jmm4);
		jm1.add(jmm5);
		jm2=new JMenu("���¹���");
		jm2.setFont(MyTools.f1);
		jm3=new JMenu("�˵�����");
		jm3.setFont(MyTools.f1);
		jm4=new JMenu("����ͳ��");
		jm4.setFont(MyTools.f1);
		jm5=new JMenu("�ɱ����ⷿ");
		jm5.setFont(MyTools.f1);
		jm6=new JMenu("����");
		jm6.setFont(MyTools.f1);
		
		//��һ���˵����뵽JMenuBar
		jmb=new JMenuBar();
		jmb.add(jm1);
		jmb.add(jm2);
		jmb.add(jm3);
		jmb.add(jm4);
		jmb.add(jm5);
		jmb.add(jm6);
	}
	
	
	public Windows1()
	{
		//�������
		try {
			titleIcon=ImageIO.read(new File("image\\jiubei.gif"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		//���ó�ʼ���˵�����
		this.initMenu();
		//��ʼ��������
		this.initToolBar();
		//���ó�ʼ�����ĺ���
		this.initallPanels();
		
		//��JMenuBar��ӵ�JFrame��
		this.setJMenuBar(jmb);
		
				
		//��һ����ִ��ڷֱ���P1����p4
		jsp1=new JSplitPane (JSplitPane.HORIZONTAL_SPLIT, true, p1, p4);
		//ָ����ߵ����ռ��������
		
		//ͨ��һ��ʼ���÷ָ��ߵ�λ�ÿ���ʵ�֣��ս�ȥ�Ļ���ֻ��p1��Ч�����������һ��JLabel������������������������÷ָ���λ��
		jsp1.setDividerLocation(Toolkit.getDefaultToolkit().getScreenSize().width);
		//�ѱ߽�������Ϊ0
		jsp1.setDividerSize(0);
		//����p5���
		p5=new JPanel(new BorderLayout());
		//����Timer
		t=new Timer(1000, this);  //ÿ��1��ȥ����һ��acction event ʱ��
		t.start();
		
		
		timeNow=new JLabel(Calendar.getInstance().getTime().toLocaleString()+"  ");
		timeNow.setFont(MyTools.f2);
		try {
			timeGg=ImageIO.read(new File("image\\zhuangtailan.jpg"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		ImagePanel ip1=new ImagePanel(timeGg);
		
		ip1.setLayout(new BorderLayout());
		ip1.add(timeNow, "East");
		p5.add(ip1);
		
		//��JFrame ��ȡ��	Container
		ct=this.getContentPane();
		
		ct.add(jtb, "North");
		ct.add(jsp1, "Center");	
		ct.add(p5, "South");
		//���ù������Ƿ���Ը���
		jtb.setFloatable(false);
		//���ô�С
		int w=Toolkit.getDefaultToolkit().getScreenSize().width;
		int h=Toolkit.getDefaultToolkit().getScreenSize().height;
		//�رմ����Ǻ��˳�ϵͳ
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//���ô��ڵ�ͼƬ
		this.setIconImage(titleIcon);
		this.setTitle("����¥����ϵͳ");
		this.setSize(w, h-30);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent arg0) {
		
		this.timeNow.setText("��ǰʱ�� "+Calendar.getInstance().getTime().toLocaleString()+"  ");
	}
	public void mouseClicked(MouseEvent arg0) {
		
		//�ж��û�����Ǹ�lable
		if(arg0.getSource()==this.p1_lab2)
		{	
			jsp1.setDividerLocation(120);
			this.cardp3.show(p3, "1");
		}else if(arg0.getSource()==this.p1_lab3)
		{
			jsp1.setDividerLocation(120);
			this.cardp3.show(p3, "2");
		}else if(arg0.getSource()==this.p2_lab1)
		{
			//����p1
			//��ʾ���ҵļ�ͷ
			this.cardp2.show(p2, "1");
			this.jsp1.setDividerLocation(0);
		}else if(arg0.getSource()==this.p2_lab2)
		{
			//չ��p1
			//��ʾ����ļ�ͷ
			this.cardp2.show(p2, "0");
			this.jsp1.setDividerLocation(120);
		}
	}
	public void mouseEntered(MouseEvent arg0) {
	
		//����û�ѡ����ĳ������jlabel�����
		if(arg0.getSource()==this.p1_lab2)
		{
			//��굽��label������ʾ����
			this.p1_lab2.setEnabled(true);
		}else if(arg0.getSource()==this.p1_lab3)
		{
			this.p1_lab3.setEnabled(true);
		}else if(arg0.getSource()==this.p1_lab4)
		{
			this.p1_lab4.setEnabled(true);
		}else if(arg0.getSource()==this.p1_lab5)
		{
			this.p1_lab5.setEnabled(true);
		}else if(arg0.getSource()==this.p1_lab6)
		{
			this.p1_lab6.setEnabled(true);
		}else if(arg0.getSource()==this.p1_lab7)
		{
			this.p1_lab7.setEnabled(true);
		}else if(arg0.getSource()==this.p1_lab8)
		{
			this.p1_lab8.setEnabled(true);
		}
	}
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//����û�ѡ����ĳ������jlabel�����
		if(arg0.getSource()==this.p1_lab2)
		{
			this.p1_lab2.setEnabled(false);
		}else if(arg0.getSource()==this.p1_lab3)
		{
			this.p1_lab3.setEnabled(false);
		}else if(arg0.getSource()==this.p1_lab4)
		{
			this.p1_lab4.setEnabled(false);
		}else if(arg0.getSource()==this.p1_lab5)
		{
			this.p1_lab5.setEnabled(false);
		}else if(arg0.getSource()==this.p1_lab6)
		{
			this.p1_lab6.setEnabled(false);
		}else if(arg0.getSource()==this.p1_lab7)
		{
			this.p1_lab7.setEnabled(false);
		}else if(arg0.getSource()==this.p1_lab8)
		{
			this.p1_lab8.setEnabled(false);
		}
	}
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}