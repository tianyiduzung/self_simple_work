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
public class AddStu extends JFrame{
	JLabel jLabel1=new JLabel();
	JLabel jLabel2=new JLabel();
	JLabel jLabel3=new JLabel();
	JLabel jLabel4=new JLabel();
	JTextField txtname=new JTextField();
	JTextField txtid=new JTextField();
	JTextField txtadd=new JTextField();
	JButton btnadd=new JButton();
	JButton clear=new JButton();
	
	public AddStu(){
		try{
			init();
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
	
	private void init() throws Exception{
		//�滭�ĸ�Label
		this.setBounds(500, 200, 340, 260);
		getContentPane().setLayout(null);
		jLabel1.setBorder(BorderFactory.createEtchedBorder());
		jLabel1.setBounds(new Rectangle(27,23,339,257));
		jLabel2.setBounds(new Rectangle(36,57,50,30));
		jLabel2.setText("name");
		jLabel3.setBounds(new Rectangle(40,100,35,18));
		jLabel3.setText("id");
		jLabel4.setBounds(new Rectangle(34,129,35,24));
		jLabel4.setText("addr");
		//�滭�����ı�����
		txtname.setBounds(new Rectangle(78,62,118,21));
		txtid.setBounds(new Rectangle(77,98,119,24));
		txtadd.setBounds(new Rectangle(77,134,272,26));	
		//�滭��Ӻ�ɾ��������ť
		btnadd.setBounds(new Rectangle(81,233,103,25));
		btnadd.setText("OK");
		btnadd.addActionListener(new AddStuActionAdapter());	
		clear.setBounds(new Rectangle(196,232,118,26));
		clear.setText("Clear");
		//��clear����������
		clear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				txtid.setText("");
				txtadd.setText("");
				txtname.setText("");
			}
		});
		//���滭����ӵ������
		this.getContentPane().add(jLabel1);
		this.getContentPane().add(jLabel2);
		this.getContentPane().add(jLabel3);
		this.getContentPane().add(jLabel4);
		this.getContentPane().add(txtname);
		this.getContentPane().add(txtid);
		this.getContentPane().add(txtadd);
		this.getContentPane().add(btnadd);	
		this.getContentPane().add(clear);
	}
	public static void main(String[] args){
		AddStu frame1=new AddStu();
		frame1.setSize(new Dimension(400,320));
		frame1.setVisible(true);	
	}
	
	//���û�����Add��ť��ʱ��ִ�������¼�
	class AddStuActionAdapter implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			//��ý������û���������ơ�ID����ַ
			String tid=txtid.getText();
			String name=txtname.getText();
			String addr=txtadd.getText();
			
			//����ǰ������д�õ�ҵ��PersonBiz����
			PersonBiz personBiz=new PersonBiz();
			int id=Integer.parseInt(tid);
			boolean flag=false;
			try{
				//����ҵ�񷽷����������ݣ��������ݿ�
				personBiz.insertDate(name,id,addr);
				flag=true;
			}catch(Exception ex){}
			if(flag){
				JOptionPane.showMessageDialog(null,"���ݲ���ɹ�");
			}else{
				JOptionPane.showMessageDialog(null,"���ݲ���ʧ��","����",JOptionPane.ERROR_MESSAGE);
			}
			//��ս����ı��������
			txtid.setText("");
			txtadd.setText("");
			txtname.setText("");
		}
		
	}
}






