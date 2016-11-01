import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/*
五子棋 -主框架类，程序启动类
*/
@SuppressWarnings("serial")
public class StartChessJFrame extends JFrame 
{
	private JPanel toolbar;
	private JButton btnStart;
	private JButton btnBack;
	private JButton btnExit;
	private JMenuBar menuBar;
	private JMenu sysMenu;
	private JMenuItem startMenuItem;
	private JMenuItem exitMenuItem;
	private JMenuItem backMenuItem;
	private ChessBoard chessBoard;
	
	public StartChessJFrame()
	{
		//设置标题
		setTitle("单机版五子棋");
		//创建和添加菜单
		menuBar=new JMenuBar();
		//初始化菜单栏
		sysMenu=new JMenu("系统");
		//初始化菜单
		startMenuItem=new JMenuItem("重新开始");
		exitMenuItem=new JMenuItem("退出");
		backMenuItem=new JMenuItem("悔棋");
		//初始化菜单项
		sysMenu.add(startMenuItem);
		//将三个菜单项添加到菜单上
		sysMenu.add(backMenuItem);
		sysMenu.add(exitMenuItem);
		MyItemListener lis=new MyItemListener();
		//初始化按钮事件监听器内部类
		this.startMenuItem.addActionListener(lis);
		//将三个菜单项注册到事件监听器上
		backMenuItem.addActionListener(lis);
		exitMenuItem.addActionListener(lis);
		menuBar.add(sysMenu);
		//将“系统”菜单添加到菜单栏上
		setJMenuBar(menuBar);
		//将menuBar设置为菜单栏
		toolbar=new JPanel();
		//工具面板栏实例化
		
		chessBoard=new ChessBoard();
		
		btnStart=new JButton("重新开始");
		//三个按钮初始化
		btnBack=new JButton("悔棋");
		btnExit=new JButton("退出");
		toolbar.setLayout(new FlowLayout(FlowLayout.CENTER));
		//将工具面板按钮用FlowLayout布局
		toolbar.add(btnStart);
		//将三个按钮添加到工具面板上
		toolbar.add(btnBack);
		toolbar.add(btnExit);
		btnStart.addActionListener(lis);
		//对三个按钮注册监听事件
		btnBack.addActionListener(lis);
		btnExit.addActionListener(lis);
		add(toolbar,BorderLayout.SOUTH);
		//将工具面板布局到界面“南”方，也就是下面
		add(chessBoard,BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置界面关闭事件
		this.setBounds(400, 10, 800, 750);
		pack();
		//自适应大小
	}
	
	public static void main(String[] args)
	{
		StartChessJFrame f=new StartChessJFrame();
		//创建主框架
		f.setVisible(true);
		//显示主框架
		
	}
	
	//事件监听器内部类
	private class MyItemListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Object obj=e.getSource();
			//取得事件源
			if(obj==StartChessJFrame.this.startMenuItem|| obj==btnStart)
			{
				//重新开始
				//JFiveFrame.this内部类引用外部类
				System.out.println("重新开始。。。");
				chessBoard.restartGame();
			}
			else if(obj==exitMenuItem || obj==btnExit)
			{
				System.exit(0);
			}
			else if(obj==backMenuItem || obj==btnBack)
			{
				//悔棋
				System.out.println("悔棋...");
				chessBoard.goback();
			}
		}
		
	}
	
}


