import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SeleStu extends JFrame{
	JLabel jLabel1=new JLabel();
	JLabel jLabel2=new JLabel();
	JLabel jLabel3=new JLabel();
	JLabel jLabel4=new JLabel();
	JTextField txtname=new JTextField();
	JTextField txtid=new JTextField();
	JTextField txtaddr=new JTextField();
	JButton OK=new JButton();
	
	public SeleStu(){
		try{
			init();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		SeleStu selestu=new SeleStu();
		selestu.setSize(new Dimension(400,320));
		selestu.setVisible(true);
	}
	
	private void init() throws Exception{
		this.setBounds(500, 200, 340, 260);
		getContentPane().setLayout(null);
		jLabel1.setBorder(BorderFactory.createEtchedBorder());
		jLabel1.setBounds(new Rectangle(6,4,390,47));
		jLabel1.setText("请输入查询的姓名");
		jLabel2.setBorder(BorderFactory.createEtchedBorder());
		jLabel2.setBounds(new Rectangle(6,58,388,235));
		
		jLabel3.setBounds(new Rectangle(13,77,91,26));
		jLabel3.setText("此学生ID为：");
		jLabel4.setBounds(new Rectangle(13,110,83,21));
		jLabel4.setText("此学生地址为：");
		
		txtname.setBounds(new Rectangle(110,16,146,26));
		txtid.setEditable(false);
		txtid.setBounds(new Rectangle(110,82,144,25));
		txtaddr.setEditable(false);
		txtaddr.setBounds(new Rectangle(110,114,146,25));	
		//绘画添加和删除两个按钮
		OK.setBounds(new Rectangle(288,17,59,25));
		OK.setText("OK");
		OK.addActionListener(new SelectStuActionAdapter());
		
		this.getContentPane().add(jLabel1);
		this.getContentPane().add(jLabel2);
		this.getContentPane().add(jLabel3);
		this.getContentPane().add(jLabel4);
		this.getContentPane().add(txtname);
		this.getContentPane().add(txtid);
		this.getContentPane().add(txtaddr);
		this.getContentPane().add(OK);	
	}
	
	//当用户单击“确定”按钮的时候，执行如下事件
	class SelectStuActionAdapter implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			//得到要查询的person姓名
			String name=txtname.getText();
			//创建前面我们写好的业务PersonBiz对象
			PersonBiz personBiz=new PersonBiz();
			boolean flag=false;
			String[] datatmp=null;
			try{
				//调用业务方法，返回数据
				datatmp=personBiz.selectDate(name);
				flag=true;
			}catch(Exception ex){}
			if(flag){
				//以返回的数据填充文本域
				txtid.setText(datatmp[1]);
				txtaddr.setText(datatmp[2]);
			}
		}
		
	}
	
}













