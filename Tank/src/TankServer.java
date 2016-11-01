import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;

import javax.swing.*;

public class TankServer extends JFrame implements ActionListener,KeyListener{

	public static final int frame_width=50;
	public static final int frame_height=35;
	public static final int frame_opration=10;
	public static final int block_width=20;
	public static final int block_height=20;
	ServerWar jpz=new ServerWar(this);//创建服务器端主面板
	JPanel jpy=new JPanel();
	JLabel jlPort=new JLabel("端口号");
	JTextField jtfPort=new JTextField("9999");
	JLabel jlNickName=new JLabel("昵称");
	JTextField jtfNickName=new JTextField("Player2");
	JButton jbNew=new JButton("建主");
	JButton jbStop=new JButton("停止");
	JButton jbStart=new JButton("开始");
	JSplitPane jsp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jpz,jpy);
	
	ServerSocket ss;
	int times=0;
	ServerThread st;
	ServerAgentThread sat;
	public TankServer(){
		this.addComponent();
		this.addListener();
		this.initialFrame();
		this.setFocusable(true);
		this.requestFocus(true);
	}
	
	public void setState(boolean state){
		jtfPort.setEnabled(state);
		jtfNickName.setEnabled(state);
		jbNew.setEnabled(state);
		jbStop.setEnabled(!state);
		jbStart.setEnabled(!state);
	}
	
	private void addComponent() {
		// TODO Auto-generated method stub
		jpy.setLayout(null);//设为空布局
		jlPort.setBounds(10,20,40,25);
		jpy.add(jlPort);//添加"端口号"标签
		jtfPort.setBounds(55,20,100,25);
		jpy.add(jtfPort);//添加输入端口号的文本框
		jlNickName.setBounds(10,50,40,25);
		jpy.add(jlNickName);//添加"昵称"标签
		jtfNickName.setBounds(55,50,100,25);
		jpy.add(jtfNickName);//添加输入昵称的文本框
		jbNew.setBounds(20,85,60,20);
		jpy.add(jbNew);//添加"建主"按钮
		jbStop.setBounds(100,85,60,20);
		jpy.add(jbStop);//添加"停止"按钮
		jbStart.setBounds(20,120,140,30);
		jpy.add(jbStart);//添加"开始"按钮
		jsp.setDividerLocation((frame_width-frame_opration)*block_width);
		jsp.setDividerSize(4);//设置JSplitPane分割线的位置及宽度
		this.add(jsp);//添加jsp
	}

	private void initialFrame() {
		// TODO Auto-generated method stub
		this.setState(true);
		Image image=new ImageIcon("ico.gif").getImage();  
		this.setIconImage(image);//设置图标
		this.setTitle("TankWar--Server");//设置标题
		this.setResizable(false);
		Dimension screenSize = //获得屏幕尺寸
		        Toolkit.getDefaultToolkit().getScreenSize();
		int centerX=screenSize.width/2;//计算屏幕的中心坐标
		int centerY=screenSize.height/2;
		int width=frame_width*block_width;//计算窗口的高度和宽度
		int height=frame_height*block_height;
		this.setBounds(centerX-width/2,centerY-height/2-30,width,height);//使窗体居中显示
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				if(st==null)
				{
					System.exit(0);
					return;
				}
				try {
					if(sat!=null)
					{
						sat.dout.writeUTF("<#SERVER_DOWN#>");
						sat.setFlag(false);
					}
					st.setFlag(false);
					ss.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
					System.exit(0);
				}
			}
		});
		this.setVisible(true);
	}
	
	private void addListener() {
		// TODO Auto-generated method stub
		jbNew.addActionListener(this);
		jbStop.addActionListener(this);
		jbStart.addActionListener(this);
		jpz.addKeyListener(this);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.jbNew)
		{
			int port=0;
			try {
				port=Integer.parseInt(this.jtfPort.getText().trim());
			} catch (Exception e2) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(this, "端口号只能是整数",
						"错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(port>65535||port<0){//判断端口号是否在有效范围内
				JOptionPane.showMessageDialog(this,"端口号只能是0-65535的整数",
				                         "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				ss=new ServerSocket(port);
				st=new ServerThread(this, ss);
				st.start();
				JOptionPane.showMessageDialog(this, "服务器启动成功",
						"提示",JOptionPane.INFORMATION_MESSAGE);
				this.setState(false);
				this.jbStart.setEnabled(false);
			} catch (Exception e2) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(this, "服务器启动失败",
						"提示",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else if(e.getSource()==this.jbStop)
		{
			try {
				if(sat!=null)
				{
					sat.dout.writeUTF("<#SERVER_DOWN#>");
					sat.setFlag(false);
				}
				st.setFlag(false);
				ss.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
			this.setState(true);
			jpz.setStart(false);//设置游戏状态
			jpz.initialTank();//重新初始化坦克
		}
		else if(e.getSource()==this.jbStart)
		{
			try{sat.dout.writeUTF("<#START#>");}
			catch(IOException ee){ee.printStackTrace();}
			jpz.setStart(true);//开始游戏
			jbStart.setEnabled(false);
		}
		jpz.requestFocus(true);
	}
	
	
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		times=0;
		if(e.getKeyCode()==32){//当为空格是，发射子弹
			jpz.hostFire();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		times++;
		int key=e.getKeyCode();
		if(times==1){//当时第一次时，更新方向
			if(key==38){
				jpz.setHostDir(1);//将方向设为上
			}
			else if(key==40){
				jpz.setHostDir(2);//将方向设为下
			}
			else if(key==37){
				jpz.setHostDir(3);//将方向设为左
			}
			else if(key==39){
				jpz.setHostDir(4);//将方向设为右
			}
		}		
		if(key==37||key==38||key==39||key==40){
			jpz.hostMove();//移动坦克
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	public static void main(String[] args) {
		new TankServer();
	}

}
