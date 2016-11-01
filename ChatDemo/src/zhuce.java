
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
		this.setTitle("注册界面");
		//绘画四个Label
		this.setBounds(500, 300, 340, 260);
		getContentPane().setLayout(null);
		jLabel1.setBorder(BorderFactory.createEtchedBorder());
		jLabel1.setBounds(new Rectangle(0,0,339,257));
		jLabel2.setBounds(new Rectangle(30,15,80,30));
		jLabel2.setText("请输入昵称：");
		jLabel3.setBounds(new Rectangle(30,55,80,30));
		jLabel3.setText("请输入密码：");
		jLabel4.setBounds(30, 95, 80, 30);
		jLabel4.setText("确认密码：");

		//绘画三个文本区域
		txtname.setBounds(new Rectangle(100,16,148,21));
		txtpass.setBounds(new Rectangle(100,56,149,24));
		txtpass2.setBounds(new Rectangle(100,96,149,24));
			
		//绘画添加和删除两个按钮
		btnadd.setBounds(new Rectangle(30,150,80,25));
		btnadd.setText("确定");
		btnadd.addActionListener(new AddStuActionAdapter(this));	
		clear.setBounds(new Rectangle(120,150,80,26));
		clear.setText("清空");
		//给clear添加清除监听
		clear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				txtpass.setText("");
				txtname.setText("");
				txtpass2.setText("");
			}
		});
		fanhui.setBounds(210, 150, 80, 25);
		fanhui.setText("返回");
		fanhui.addActionListener(new FanhuiAction(this));
		
		//将绘画的添加到面板上
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
	
	//当用户单击Add按钮的时候，执行如下事件
	class AddStuActionAdapter implements ActionListener{
		private JFrame sf;
		public AddStuActionAdapter(JFrame sf){
			this.sf=sf;
		}
		public void actionPerformed(ActionEvent e) {
			//创建前面我们写好的业务PersonBiz对象
			PersonChat personBiz=new PersonChat();
			//获得界面上用户输入的名称、ID、地址
			String tpass=new String(txtpass.getPassword());
			String tpass2=new String(txtpass2.getPassword());
			String name=txtname.getText();
			boolean flag=false;
			//判断用户名和密码是否为空
			if(tpass.equals("")||name.equals("")||tpass2.equals("")){
				JOptionPane.showMessageDialog(null,"用户名或密码不能为空");
				return;
			}
			//判断两次密码输入是否一致
			if(!(tpass.equals(tpass2))){
				JOptionPane.showMessageDialog(null,"两次密码不一致！");
				return;
			}
			//判断用户名是否已存在
			try{
				if(personBiz.readDate(name)){
				JOptionPane.showMessageDialog(null,"用户名已存在");
				return;
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			//执行注册操作
			try{
				//调用业务方法，传入数据，插入数据库
				personBiz.insertDate(name,tpass);
				flag=true;
			}catch(Exception ex){}
			if(flag){
				
				JOptionPane.showMessageDialog(null,"注册成功");
				sf.setVisible(false);
				new ClientLogin();
				
			}else{
				JOptionPane.showMessageDialog(sf,"注册失败","错误",JOptionPane.ERROR_MESSAGE);
			}
			//清空界面文本域的内容
			txtpass.setText("");
			txtname.setText("");
			txtpass2.setText("");		
		}
		
	}
}
