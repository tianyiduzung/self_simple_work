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
 * 聊天室的客户端登录界面
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
		this.setTitle("聊天室登录界面");
		this.getContentPane().setBackground(Color.BLUE);
		this.setSize(430, 330);
		int x = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int y = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((x - this.getWidth()) / 2, (y - this.getHeight()) / 2);
		this.setResizable(false);
		// 把Logo放置到JFrame的北边
		URL url=this.getClass().getResource("/loginlogo.png");
		Icon icon=new ImageIcon(url);
		JLabel label=new JLabel(icon);
		this.add(label,BorderLayout.NORTH);
		// 登录信息面板
		JPanel mainPanel = new JPanel();
		Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		mainPanel.setBorder(BorderFactory.createTitledBorder(border, "输入登录信息",
				TitledBorder.CENTER, TitledBorder.TOP));
		this.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(null);
		JLabel nameLbl = new JLabel("昵称：");
		nameLbl.setBounds(100, 30, 50, 22);
		mainPanel.add(nameLbl);
		nameTxt = new JTextField();
		nameTxt.setBounds(165, 30, 150, 22);
		mainPanel.add(nameTxt);
		JLabel pwdLbl = new JLabel("密码：");
		pwdLbl.setBounds(100, 60, 50, 22);
		mainPanel.add(pwdLbl);
		pwdFld = new JPasswordField();
		pwdFld.setBounds(165, 60, 150, 22);
		mainPanel.add(pwdFld);
		// 按钮面板放置在JFrame的南边
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.add(btnPanel, BorderLayout.SOUTH);

		JButton zhuce = new JButton("注册");
		zhuce.addActionListener(new zhuceAction(this));
		btnPanel.add(zhuce);

		JButton resetBtn = new JButton("重置");
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nameTxt.setText("");
				pwdFld.setText("");
			}
		});
		btnPanel.add(resetBtn);
		JButton submitBtn = new JButton("登录");
		// 注意这里传入的this
		submitBtn.addActionListener(new LoginAction(this));
		btnPanel.add(submitBtn);
	}

	// “登录”事件处理类
	class LoginAction implements ActionListener {
		private JFrame self;
		//实现点击登录界面后，关闭登录界面
		public LoginAction(JFrame self) {
			this.self = self;
		}
		//对登录用户名和密码进行判断，并实现打开客户端
		public void actionPerformed(ActionEvent e) {
			PersonChat personchat = new PersonChat();
			String name = nameTxt.getText();
			String password = new String(pwdFld.getPassword());
			
			//判断是否开启了数据库
			try{
				personchat.readDate();
			}catch(Exception el){
				JOptionPane.showMessageDialog(null, "无法连接到服务器，请检查网络连接!");
				el.printStackTrace();
				return;
			}
			// 判断用户名和密码是否为空
			if (password.equals("") || name.equals("")) {
				JOptionPane.showMessageDialog(null, "用户名或密码不能为空");
				return;
			}
			// 判断用户名是否存在，不存在则提示注册信息信息！
			try {
				if (!(personchat.readDate(name))) {
					JOptionPane.showMessageDialog(null, "用户名不存在，请先注册！");
					return;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			// 判断用户名和密码是否正确
			try {
				if (!(personchat.readDate(name, password))) {
					JOptionPane.showMessageDialog(null, "用户名或密码错误！");
					return;
				}
			} catch (Exception es) {
				es.printStackTrace();
			}

			//System.out.println("用户名是" + name + ",密码是" + password);
			try {
				// 连接到服务器...
				Socket socket = new Socket("192.168.1.131", 8888);

				// 连接上之后，显示聊天窗口
				new ChatClient(socket, nameTxt.getText());
				// 打开聊天面板时，关闭登录窗体。所以调用dispose方法
				self.dispose();
			} catch (UnknownHostException el) {
				el.printStackTrace();
				JOptionPane.showConfirmDialog(self, "找不到指定的服务器!", "连接失败",
						JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
			} catch (IOException el) {
				el.printStackTrace();
				JOptionPane.showConfirmDialog(self, "连接服务器出错，请重试!", "连接失败",
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
		//使用提供 的皮肤工具
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
