import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class UpdateStu extends JFrame{
	JLabel jLabel1=new JLabel();
	JLabel jLabel2=new JLabel();
	JLabel jLabel3=new JLabel();
	JLabel jLabel4=new JLabel();
	JTextField txtname=new JTextField();
	JTextField txtid=new JTextField();
	JTextField txtaddr=new JTextField();
	JButton query=new JButton();
	JButton update=new JButton();
	
	public UpdateStu(){
		try{
			init();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		UpdateStu updateStu=new UpdateStu();
		updateStu.setSize(new Dimension(400,320));
		updateStu.setVisible(true);
	}
	
	private void init() throws Exception{
		this.setBounds(500, 200, 340, 260);
		this.getContentPane().setLayout(null);
		jLabel1.setBorder(BorderFactory.createEtchedBorder());
		jLabel1.setBounds(new Rectangle(6,4,390,47));
		jLabel1.setText("�������ѯ������");
		jLabel2.setBorder(BorderFactory.createEtchedBorder());
		jLabel2.setBounds(new Rectangle(6,58,388,235));
		
		jLabel3.setBounds(new Rectangle(13,77,91,26));
		jLabel3.setText("��ѧ��IDΪ��");
		jLabel4.setBounds(new Rectangle(13,110,83,21));
		jLabel4.setText("��ѧ����ַΪ��");
		
		txtname.setBounds(new Rectangle(110,16,146,26));
		txtid.setEditable(true);
		txtid.setBounds(new Rectangle(110,82,144,25));
		txtaddr.setEditable(true);
		txtaddr.setBounds(new Rectangle(110,114,146,25));	

		query.setBounds(new Rectangle(288,17,59,25));
		query.setText("��ѯ");
		query.addActionListener(new PreUpdateStuActionAdapter());
		update.setBounds(new Rectangle(290,98,91,27));
		update.setText("update");
		update.addActionListener(new UpdateStuActionAdapter());
		
		
		this.getContentPane().add(jLabel1);
		this.getContentPane().add(jLabel2);
		this.getContentPane().add(jLabel3);
		this.getContentPane().add(jLabel4);
		this.getContentPane().add(txtname);
		this.getContentPane().add(txtid);
		this.getContentPane().add(txtaddr);
		this.getContentPane().add(query);
		this.getContentPane().add(update);
	}
	
	//���û�������ȷ������ť��ʱ��ִ�������¼�
	class PreUpdateStuActionAdapter implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			//����ı���Person������
			String name=txtname.getText();
			//����ǰ������д�õ�ҵ��PersonBiz����
			PersonBiz personBiz=new PersonBiz();
			boolean flag=false;
			String[] datatmp=null;
			try{
				//����ҵ�񷽷�����������
				datatmp=personBiz.selectDate(name);		
				flag=true;					
			}catch(Exception ex){}
			if(flag){
				//��ʾ��ѯ������person��Ϣ
				txtid.setText(datatmp[1]);
				txtaddr.setText(datatmp[2]);
			}else{
				JOptionPane.showMessageDialog(null,"���û������ڣ�","����",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	//���Ҫ���µ��û�����
	class UpdateStuActionAdapter implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			//����ı���Person������
			String name=txtname.getText();
			String tid=txtid.getText();
			String addr=txtaddr.getText();
			int id=0;
			if(tid!=null){
				id=Integer.parseInt(tid);
			}
			//����ǰ������д�õ�ҵ��PersonBiz����
			PersonBiz personBiz=new PersonBiz();
			boolean flag=false;
			try{
				//����ҵ�񷽷�����������
				personBiz.updateDate(name, id, addr);
				flag=true;
			}catch(Exception ex){}
			if(flag){
				JOptionPane.showMessageDialog(null,"���ݸ��³ɹ�");
			}	
		}	
	}
}









