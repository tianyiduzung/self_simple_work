import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/*
������ -������࣬����������
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
		//���ñ���
		setTitle("������������");
		//��������Ӳ˵�
		menuBar=new JMenuBar();
		//��ʼ���˵���
		sysMenu=new JMenu("ϵͳ");
		//��ʼ���˵�
		startMenuItem=new JMenuItem("���¿�ʼ");
		exitMenuItem=new JMenuItem("�˳�");
		backMenuItem=new JMenuItem("����");
		//��ʼ���˵���
		sysMenu.add(startMenuItem);
		//�������˵�����ӵ��˵���
		sysMenu.add(backMenuItem);
		sysMenu.add(exitMenuItem);
		MyItemListener lis=new MyItemListener();
		//��ʼ����ť�¼��������ڲ���
		this.startMenuItem.addActionListener(lis);
		//�������˵���ע�ᵽ�¼���������
		backMenuItem.addActionListener(lis);
		exitMenuItem.addActionListener(lis);
		menuBar.add(sysMenu);
		//����ϵͳ���˵���ӵ��˵�����
		setJMenuBar(menuBar);
		//��menuBar����Ϊ�˵���
		toolbar=new JPanel();
		//���������ʵ����
		
		chessBoard=new ChessBoard();
		
		btnStart=new JButton("���¿�ʼ");
		//������ť��ʼ��
		btnBack=new JButton("����");
		btnExit=new JButton("�˳�");
		toolbar.setLayout(new FlowLayout(FlowLayout.CENTER));
		//��������尴ť��FlowLayout����
		toolbar.add(btnStart);
		//��������ť��ӵ����������
		toolbar.add(btnBack);
		toolbar.add(btnExit);
		btnStart.addActionListener(lis);
		//��������ťע������¼�
		btnBack.addActionListener(lis);
		btnExit.addActionListener(lis);
		add(toolbar,BorderLayout.SOUTH);
		//��������岼�ֵ����桰�ϡ�����Ҳ��������
		add(chessBoard,BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//���ý���ر��¼�
		this.setBounds(400, 10, 800, 750);
		pack();
		//����Ӧ��С
	}
	
	public static void main(String[] args)
	{
		StartChessJFrame f=new StartChessJFrame();
		//���������
		f.setVisible(true);
		//��ʾ�����
		
	}
	
	//�¼��������ڲ���
	private class MyItemListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Object obj=e.getSource();
			//ȡ���¼�Դ
			if(obj==StartChessJFrame.this.startMenuItem|| obj==btnStart)
			{
				//���¿�ʼ
				//JFiveFrame.this�ڲ��������ⲿ��
				System.out.println("���¿�ʼ������");
				chessBoard.restartGame();
			}
			else if(obj==exitMenuItem || obj==btnExit)
			{
				System.exit(0);
			}
			else if(obj==backMenuItem || obj==btnBack)
			{
				//����
				System.out.println("����...");
				chessBoard.goback();
			}
		}
		
	}
	
}


