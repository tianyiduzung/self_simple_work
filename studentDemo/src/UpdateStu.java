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
		jLabel1.setText("请输入查询的姓名");
		jLabel2.setBorder(BorderFactory.createEtchedBorder());
		jLabel2.setBounds(new Rectangle(6,58,388,235));
		
		jLabel3.setBounds(new Rectangle(13,77,91,26));
		jLabel3.setText("此学生ID为：");
		jLabel4.setBounds(new Rectangle(13,110,83,21));
		jLabel4.setText("此学生地址为：");
		
		txtname.setBounds(new Rectangle(110,16,146,26));
		txtid.setEditable(true);
		txtid.setBounds(new Rectangle(110,82,144,25));
		txtaddr.setEditable(true);
		txtaddr.setBounds(new Rectangle(110,114,146,25));	

		query.setBounds(new Rectangle(288,17,59,25));
		query.setText("查询");
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
	
	//当用户单击“确定”按钮的时候，执行如下事件
	class PreUpdateStuActionAdapter implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			//获得文本框Person的姓名
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
				//显示查询出来的person信息
				txtid.setText(datatmp[1]);
				txtaddr.setText(datatmp[2]);
			}else{
				JOptionPane.showMessageDialog(null,"此用户不存在！","错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	//查出要更新的用户资料
	class UpdateStuActionAdapter implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			//获得文本框Person的姓名
			String name=txtname.getText();
			String tid=txtid.getText();
			String addr=txtaddr.getText();
			int id=0;
			if(tid!=null){
				id=Integer.parseInt(tid);
			}
			//创建前面我们写好的业务PersonBiz对象
			PersonBiz personBiz=new PersonBiz();
			boolean flag=false;
			try{
				//调用业务方法，返回数据
				personBiz.updateDate(name, id, addr);
				flag=true;
			}catch(Exception ex){}
			if(flag){
				JOptionPane.showMessageDialog(null,"数据更新成功");
			}	
		}	
	}
}









