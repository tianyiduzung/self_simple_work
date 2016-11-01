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
		//绘画四个Label
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
		//绘画三个文本区域
		txtname.setBounds(new Rectangle(78,62,118,21));
		txtid.setBounds(new Rectangle(77,98,119,24));
		txtadd.setBounds(new Rectangle(77,134,272,26));	
		//绘画添加和删除两个按钮
		btnadd.setBounds(new Rectangle(81,233,103,25));
		btnadd.setText("OK");
		btnadd.addActionListener(new AddStuActionAdapter());	
		clear.setBounds(new Rectangle(196,232,118,26));
		clear.setText("Clear");
		//给clear添加清除监听
		clear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				txtid.setText("");
				txtadd.setText("");
				txtname.setText("");
			}
		});
		//将绘画的添加到面板上
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
	
	//当用户单击Add按钮的时候，执行如下事件
	class AddStuActionAdapter implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			//获得界面上用户输入的名称、ID、地址
			String tid=txtid.getText();
			String name=txtname.getText();
			String addr=txtadd.getText();
			
			//创建前面我们写好的业务PersonBiz对象
			PersonBiz personBiz=new PersonBiz();
			int id=Integer.parseInt(tid);
			boolean flag=false;
			try{
				//调用业务方法，传入数据，插入数据库
				personBiz.insertDate(name,id,addr);
				flag=true;
			}catch(Exception ex){}
			if(flag){
				JOptionPane.showMessageDialog(null,"数据插入成功");
			}else{
				JOptionPane.showMessageDialog(null,"数据插入失败","错误",JOptionPane.ERROR_MESSAGE);
			}
			//清空界面文本域的内容
			txtid.setText("");
			txtadd.setText("");
			txtname.setText("");
		}
		
	}
}






