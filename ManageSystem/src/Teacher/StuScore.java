package Teacher;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

public class StuScore extends JPanel implements ActionListener {

	private String host;
	private JLabel jl=new JLabel("������ѧ��ѧ�ţ�");
	private JLabel jl1=new JLabel("����ѧ�֣�");
	private JLabel jl2=new JLabel("");
	private JTextField jtf=new JTextField();
	private JButton jb=new JButton("��ѯ");
	private JTable jt;
	private JScrollPane jsp;
	private Vector v_head=new Vector();
	private Vector v_data=new Vector();
	private GetScore gs;
	
	public StuScore(String host){
		this.host=host;
		gs=new GetScore(host);
		this.initialData();
		this.initialFrame();
	}
	
	public void initialData(){
		v_head.add("�γ���");
		v_head.add("����");
		v_head.add("ѧ��");
	}
	
	public void initialFrame(){
		this.setLayout(null);
		jl.setBounds(60, 20, 150, 30);
		this.add(jl);
		jtf.setBounds(195, 20, 150, 30);
		this.add(jtf);
		jtf.addActionListener(this);
		jb.setBounds(350, 20, 100, 30);
		this.add(jb);
		jb.addActionListener(this);
		
		DefaultTableModel dtm=new DefaultTableModel(v_data,v_head);
		jt=new JTable(dtm);
		jsp=new JScrollPane(jt);
		jsp.setBounds(60, 60, 500, 500);
		this.add(jsp);
		jl1.setBounds(60, 570, 130, 30);
		this.add(jl1);
		jl2.setBounds(200, 570, 130, 30);
		this.add(jl2);
	}
	
	public void setFocus(){
		this.jtf.requestFocus(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb||e.getSource()==jtf){
			String stu_id=jtf.getText().trim();
			if(stu_id.equals("")){
				JOptionPane.showMessageDialog(this, "������ѧ��ѧ��","����",
						JOptionPane.ERROR_MESSAGE);
				return;
			}else{
				v_data=gs.getAllScore(stu_id);
				DefaultTableModel dtm=new DefaultTableModel(v_data,v_head);
				jt.setModel(dtm);
				//������ʾ
				((DefaultTableModel)jt.getModel()).fireTableStructureChanged();
				String xuefen=gs.getXueFen(stu_id)+"";
				jl2.setText(xuefen);
			}
		}
	}

}
