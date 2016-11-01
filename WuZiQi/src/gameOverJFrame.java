import java.awt.Label;
import javax.swing.*;
import java.awt.event.*;

public class gameOverJFrame{
	private JFrame jf;
	private JPanel jp;
	private JButton queding;
	private Label lab;
	public static String colorName;
	
	public gameOverJFrame(){
		jf=new JFrame("消息");
		jf.setBounds(550,300,300,200);
		jf.setLayout(null);
		jp=new JPanel();
		jp.setBounds(80, 50, 120, 100);
		lab=new Label();	
		lab.setBounds(60,50,40,30);
		colorName=ChessBoard.getcolorName();
		lab.setText("恭喜，"+colorName+"赢了！");
		jp.add(lab);
		queding=new JButton("确定");
		queding.setLocation(60,120);
		jp.add(queding);
		jf.add(jp);
		
		myEvent();
		jf.setVisible(true);
	}
	
	private void myEvent(){
		jf.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		
		queding.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jf.setVisible(false);
			}
		});
	}
	
}
