import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.jvnet.substance.skin.SubstanceBusinessLookAndFeel;

/**
 * �����ҵĿͻ��˵�¼����
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
public class ClientLogin extends JFrame {
	private JTextField nameTxt;
	private JPasswordField pwdFld;

	public ClientLogin() {
		this.init();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void init() {
		this.setTitle("�����ҵ�¼����");
		this.getContentPane().setBackground(Color.BLUE);
		this.setSize(430, 330);
		int x = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int y = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((x - this.getWidth()) / 2, (y - this.getHeight()) / 2);
		this.setResizable(false);
		// ��Logo���õ�JFrame�ı���
		URL url=this.getClass().getResource("/loginlogo.png");
		Icon icon=new ImageIcon(url);
		JLabel label=new JLabel(icon);
		this.add(label,BorderLayout.NORTH);
		// ��¼��Ϣ���
		JPanel mainPanel = new JPanel();
		Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		mainPanel.setBorder(BorderFactory.createTitledBorder(border, "�����¼��Ϣ",
				TitledBorder.CENTER, TitledBorder.TOP));
		this.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(null);
		JLabel nameLbl = new JLabel("�ǳƣ�");
		nameLbl.setBounds(100, 30, 50, 22);
		mainPanel.add(nameLbl);
		nameTxt = new JTextField();
		nameTxt.setBounds(165, 30, 150, 22);
		mainPanel.add(nameTxt);
		JLabel pwdLbl = new JLabel("���룺");
		pwdLbl.setBounds(100, 60, 50, 22);
		mainPanel.add(pwdLbl);
		pwdFld = new JPasswordField();
		pwdFld.setBounds(165, 60, 150, 22);
		mainPanel.add(pwdFld);
		// ��ť��������JFrame���ϱ�
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.add(btnPanel, BorderLayout.SOUTH);

		JButton zhuce = new JButton("ע��");
		zhuce.addActionListener(new zhuceAction(this));
		btnPanel.add(zhuce);

		JButton resetBtn = new JButton("����");
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nameTxt.setText("");
				pwdFld.setText("");
			}
		});
		btnPanel.add(resetBtn);
		JButton submitBtn = new JButton("��¼");
		// ע�����ﴫ���this
		submitBtn.addActionListener(new LoginAction(this));
		btnPanel.add(submitBtn);
	}

	// ����¼���¼�������
	class LoginAction implements ActionListener {
		private JFrame self;
		//ʵ�ֵ����¼����󣬹رյ�¼����
		public LoginAction(JFrame self) {
			this.self = self;
		}
		//�Ե�¼�û�������������жϣ���ʵ�ִ򿪿ͻ���
		public void actionPerformed(ActionEvent e) {
			PersonChat personchat = new PersonChat();
			String name = nameTxt.getText();
			String password = new String(pwdFld.getPassword());
			
			//�ж��Ƿ��������ݿ�
			try{
				personchat.readDate();
			}catch(Exception el){
				JOptionPane.showMessageDialog(null, "�޷����ӵ���������������������!");
				el.printStackTrace();
				return;
			}
			// �ж��û����������Ƿ�Ϊ��
			if (password.equals("") || name.equals("")) {
				JOptionPane.showMessageDialog(null, "�û��������벻��Ϊ��");
				return;
			}
			// �ж��û����Ƿ���ڣ�����������ʾע����Ϣ��Ϣ��
			try {
				if (!(personchat.readDate(name))) {
					JOptionPane.showMessageDialog(null, "�û��������ڣ�����ע�ᣡ");
					return;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			// �ж��û����������Ƿ���ȷ
			try {
				if (!(personchat.readDate(name, password))) {
					JOptionPane.showMessageDialog(null, "�û������������");
					return;
				}
			} catch (Exception es) {
				es.printStackTrace();
			}

			//System.out.println("�û�����" + name + ",������" + password);
			try {
				// ���ӵ�������...
				Socket socket = new Socket("192.168.1.131", 8888);

				// ������֮����ʾ���촰��
				new ChatClient(socket, nameTxt.getText());
				// ���������ʱ���رյ�¼���塣���Ե���dispose����
				self.dispose();
			} catch (UnknownHostException el) {
				el.printStackTrace();
				JOptionPane.showConfirmDialog(self, "�Ҳ���ָ���ķ�����!", "����ʧ��",
						JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
			} catch (IOException el) {
				el.printStackTrace();
				JOptionPane.showConfirmDialog(self, "���ӷ���������������!", "����ʧ��",
						JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
		
	class zhuceAction implements ActionListener {
		private JFrame sf;
		public zhuceAction(JFrame sf) {
			this.sf = sf;
		}

		public void actionPerformed(ActionEvent e) {
			try {
				zhuce zc = new zhuce();
				zc.setVisible(true);
				sf.setVisible(false);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		//ʹ���ṩ ��Ƥ������
		try{
			UIManager.setLookAndFeel(new SubstanceBusinessLookAndFeel());
		} catch(UnsupportedLookAndFeelException e){ 
			   e.printStackTrace();
		}
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		new ClientLogin();
	}
}
