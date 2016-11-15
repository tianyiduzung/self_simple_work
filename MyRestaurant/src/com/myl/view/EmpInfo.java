/**
 *������ʾ��ʶ����Ľ��� 
 */
package com.myl.view;
import java.awt.*;
import javax.swing.*;
import com.mhl.model.*;
import com.mhl.tools.ImagePanel;
import com.mhl.tools.MyTools;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class EmpInfo extends JPanel implements ActionListener{
	
	//������Ҫ�ĸ������
	JPanel p1, p2, p3, p4, p5;
	JLabel p1_lab1, p3_lab1;
	JTextField p1_jtf1;
	JButton p1_jb1, p4_jb1, p4_jb2, p4_jb3, p4_jb4;
	//������ʾ������Ϣ��table
	JTable jtable;  
	JScrollPane jsp;   //���������
	EmpModel em=null;
	//���캯��
	public EmpInfo()
	{
		//������Ҫ�����
		p1=new JPanel(new FlowLayout(FlowLayout.CENTER));
		p1_lab1=new JLabel("����������(Ա���Ż�ְλ)"); p1_lab1.setFont(MyTools.f2);
		p1_jtf1=new JTextField(20);
		p1_jb1=new JButton("��ѯ"); p1_jb1.setFont(MyTools.f3);
		//�����Ǽ��뵽p1
		p1.add(p1_lab1);
		p1.add(p1_jtf1);
		p1.add(p1_jb1);
		
		
		//�����м�
		em=new EmpModel();
		String []paras={"1"};
		em.query("select cleId  , cleSex, clezw from clerkinfo where 1=?", paras);
		jtable=new JTable(em);
		p2=new JPanel(new BorderLayout());
		jsp=new JScrollPane(jtable);
		p2.add(jsp);
		
		
		//�����ϱߵ�
		p3=new JPanel(new FlowLayout(FlowLayout.LEFT));
		p3_lab1=new JLabel("�ܼ�¼����**��");
		p3_lab1.setFont(MyTools.f3);
		p3.add(p3_lab1);
		p4=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p4_jb1=new JButton("��ϸ��Ϣ");
		p4_jb1.setFont(MyTools.f3);
		p4_jb2=new JButton("���");
		p4_jb2.setFont(MyTools.f3);
		p4_jb3=new JButton("�޸�");
		p4_jb3.setFont(MyTools.f3);
		p4_jb4=new JButton("ɾ��");
		p4_jb4.addActionListener(this);
		p4_jb4.setFont(MyTools.f3);
		p4.add(p4_jb1);
		p4.add(p4_jb2);
		p4.add(p4_jb3);
		p4.add(p4_jb4);
		p5=new JPanel(new BorderLayout());
		
		p5.add(p3,"West");
		
		p5.add(p4,"South");
		
		//���ܵ�JPanel����ΪBorderLayout
		this.setLayout(new BorderLayout());
		
		//��p1���뵽JPanel
		this.add(p1,"North");
		this.add(p2,"Center");
		this.add(p5, "South");
		// ���ñ�����ɫ
	//	this.setBackground(Color.pink);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		
		//����û�Ҫɾ��ĳ��Ա��
		if(this.p4_jb4==e.getSource())
		{
			int selRowNum=jtable.getSelectedRow();   //�õ�ѡ�е���
			String empNo=(String)em.getValueAt(selRowNum, 0);   //�õ�ѡ�е��еĵ�һ�� ���
			if(em.delEmpById(empNo))
			{
				JOptionPane.showMessageDialog(null, "��ϲ��ɾ���ɹ�");
			}else{
				JOptionPane.showMessageDialog(null, "ɾ��ʧ��");
			}
			String []paras={"1"};
			em=new EmpModel();
			//���²�ѯ
			em.query(" select cleId  , cleSex, clezw from clerkinfo where 1=?", paras);
			jtable.setModel(em);
		}
		
	}
}
