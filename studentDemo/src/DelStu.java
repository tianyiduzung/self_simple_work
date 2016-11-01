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
public class DelStu extends JFrame{
	JLabel jLabel1=new JLabel();
	JLabel jLabel2=new JLabel();
	JTextField txtname=new JTextField();
	JButton btndel=new JButton();
	
	public DelStu(){
		try{
			init();
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
	
	private void init() throws Exception{
		this.setBounds(500, 200, 340, 260);
		getContentPane().setLayout(null);
		jLabel1.setBorder(BorderFactory.createEtchedBorder());
		jLabel1.setBounds(new Rectangle(22,23,357,262));
		jLabel2.setText("������Ҫɾ��������");
		jLabel2.setBounds(new Rectangle(37,44,113,29));	
		txtname.setBounds(new Rectangle(150,45,174,28));
		
		btndel.addActionListener(new DelStuActionAdapter());	
		btndel.setBounds(new Rectangle(96,114,176,34));
		btndel.setText("ȷ��");
		
		this.getContentPane().add(jLabel1);
		this.getContentPane().add(jLabel2);
		this.getContentPane().add(txtname);
		this.getContentPane().add(btndel);
	}
	
	public static void main(String[] args){
		DelStu delstu=new DelStu();
		delstu.setSize(new Dimension(400,320));
		delstu.setVisible(true);
	}
	
	//���û�������ȷ������ťʱ��ִ�������¼�
	class DelStuActionAdapter implements ActionListener{
		public void actionPerformed(ActionEvent e){
			//��ȡ�ı����û����������
			String name=txtname.getText();
			//����ǰ������д�õ�ҵ��PersonBiz����
			PersonBiz personBiz=new PersonBiz();
			boolean flag=false;
			try{
				//����ҵ�񷽷�������Ҫɾ�����û�����
				personBiz.deleteDate(name);
				flag=true;
			}catch(Exception ex){}
			if(flag){
				JOptionPane.showMessageDialog(null,"ɾ�����ݳɹ�");
			}else{
				JOptionPane.showMessageDialog(null,"ɾ������ʧ��","����",JOptionPane.ERROR_MESSAGE);
			}
			//����ı��������
			txtname.setText("");
		}
	}
}









