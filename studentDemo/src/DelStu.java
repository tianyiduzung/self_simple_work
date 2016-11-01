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
		jLabel2.setText("请输入要删除的姓名");
		jLabel2.setBounds(new Rectangle(37,44,113,29));	
		txtname.setBounds(new Rectangle(150,45,174,28));
		
		btndel.addActionListener(new DelStuActionAdapter());	
		btndel.setBounds(new Rectangle(96,114,176,34));
		btndel.setText("确定");
		
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
	
	//当用户单击“确定”按钮时，执行如下事件
	class DelStuActionAdapter implements ActionListener{
		public void actionPerformed(ActionEvent e){
			//获取文本域用户输入的内容
			String name=txtname.getText();
			//创建前面我们写好的业务PersonBiz对象
			PersonBiz personBiz=new PersonBiz();
			boolean flag=false;
			try{
				//调用业务方法，传入要删除的用户姓名
				personBiz.deleteDate(name);
				flag=true;
			}catch(Exception ex){}
			if(flag){
				JOptionPane.showMessageDialog(null,"删除数据成功");
			}else{
				JOptionPane.showMessageDialog(null,"删除数据失败","错误",JOptionPane.ERROR_MESSAGE);
			}
			//清空文本域的内容
			txtname.setText("");
		}
	}
}









