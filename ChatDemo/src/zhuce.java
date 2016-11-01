
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jvnet.substance.skin.SubstanceBusinessLookAndFeel;


@SuppressWarnings("serial")
public class zhuce extends JFrame{
	JLabel jLabel1=new JLabel();
	JLabel jLabel2=new JLabel();
	JLabel jLabel3=new JLabel();
	JLabel jLabel4=new JLabel();
	
	JTextField txtname=new JTextField();
	JPasswordField txtpass=new JPasswordField();
	JPasswordField txtpass2=new JPasswordField();
	
	JButton btnadd=new JButton();
	JButton clear=new JButton();
	JButton fanhui=new JButton();
	
	public zhuce(){
		try{
			UIManager.setLookAndFeel(new SubstanceBusinessLookAndFeel());
		} catch(UnsupportedLookAndFeelException e){ 
			   e.printStackTrace();
		}
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		
		try{
			init();
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
	
	private void init() throws Exception{
		this.setTitle("ע�����");
		//�滭�ĸ�Label
		this.setBounds(500, 300, 340, 260);
		getContentPane().setLayout(null);
		jLabel1.setBorder(BorderFactory.createEtchedBorder());
		jLabel1.setBounds(new Rectangle(0,0,339,257));
		jLabel2.setBounds(new Rectangle(30,15,80,30));
		jLabel2.setText("�������ǳƣ�");
		jLabel3.setBounds(new Rectangle(30,55,80,30));
		jLabel3.setText("���������룺");
		jLabel4.setBounds(30, 95, 80, 30);
		jLabel4.setText("ȷ�����룺");

		//�滭�����ı�����
		txtname.setBounds(new Rectangle(100,16,148,21));
		txtpass.setBounds(new Rectangle(100,56,149,24));
		txtpass2.setBounds(new Rectangle(100,96,149,24));
			
		//�滭��Ӻ�ɾ��������ť
		btnadd.setBounds(new Rectangle(30,150,80,25));
		btnadd.setText("ȷ��");
		btnadd.addActionListener(new AddStuActionAdapter(this));	
		clear.setBounds(new Rectangle(120,150,80,26));
		clear.setText("���");
		//��clear����������
		clear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				txtpass.setText("");
				txtname.setText("");
				txtpass2.setText("");
			}
		});
		fanhui.setBounds(210, 150, 80, 25);
		fanhui.setText("����");
		fanhui.addActionListener(new FanhuiAction(this));
		
		//���滭����ӵ������
		this.getContentPane().add(jLabel1);
		this.getContentPane().add(jLabel2);
		this.getContentPane().add(jLabel3);
		this.getContentPane().add(jLabel4);
		
		this.getContentPane().add(txtname);
		this.getContentPane().add(txtpass);
		this.getContentPane().add(txtpass2);
		
		this.getContentPane().add(btnadd);	
		this.getContentPane().add(clear);
		this.getContentPane().add(fanhui);
	}
	
	class FanhuiAction implements ActionListener{
		private JFrame sf;
		public FanhuiAction(JFrame sf){
			this.sf=sf;
		}
		public void actionPerformed(ActionEvent e){
			sf.setVisible(false);
			new ClientLogin();
		}
	}
	
	//���û�����Add��ť��ʱ��ִ�������¼�
	class AddStuActionAdapter implements ActionListener{
		private JFrame sf;
		public AddStuActionAdapter(JFrame sf){
			this.sf=sf;
		}
		public void actionPerformed(ActionEvent e) {
			//����ǰ������д�õ�ҵ��PersonBiz����
			PersonChat personBiz=new PersonChat();
			//��ý������û���������ơ�ID����ַ
			String tpass=new String(txtpass.getPassword());
			String tpass2=new String(txtpass2.getPassword());
			String name=txtname.getText();
			boolean flag=false;
			//�ж��û����������Ƿ�Ϊ��
			if(tpass.equals("")||name.equals("")||tpass2.equals("")){
				JOptionPane.showMessageDialog(null,"�û��������벻��Ϊ��");
				return;
			}
			//�ж��������������Ƿ�һ��
			if(!(tpass.equals(tpass2))){
				JOptionPane.showMessageDialog(null,"�������벻һ�£�");
				return;
			}
			//�ж��û����Ƿ��Ѵ���
			try{
				if(personBiz.readDate(name)){
				JOptionPane.showMessageDialog(null,"�û����Ѵ���");
				return;
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			//ִ��ע�����
			try{
				//����ҵ�񷽷����������ݣ��������ݿ�
				personBiz.insertDate(name,tpass);
				flag=true;
			}catch(Exception ex){}
			if(flag){
				
				JOptionPane.showMessageDialog(null,"ע��ɹ�");
				sf.setVisible(false);
				new ClientLogin();
				
			}else{
				JOptionPane.showMessageDialog(sf,"ע��ʧ��","����",JOptionPane.ERROR_MESSAGE);
			}
			//��ս����ı��������
			txtpass.setText("");
			txtname.setText("");
			txtpass2.setText("");		
		}
		
	}
}
