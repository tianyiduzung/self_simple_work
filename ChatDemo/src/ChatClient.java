import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jvnet.substance.skin.SubstanceBusinessLookAndFeel;

/**
 * 聊天室的客户端
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class ChatClient extends JFrame{
	
	/** 负责跟服务器通信的Socket*/
	private Socket socket;
	/** 要发送的内容区域*/
	private JTextArea sendArea;
	/** 聊天记录区域*/
	private JTextArea contentArea;
	/** 当前用户的昵称*/
	private String name;
	
	public ChatClient(Socket socket,String name){
		this.socket=socket;
		this.name=name;
		
		try{  
			UIManager.setLookAndFeel(new SubstanceBusinessLookAndFeel());
		} catch(UnsupportedLookAndFeelException e){ 
			   e.printStackTrace();
		}
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		
		this.init();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		//启动一个单独的线程，专门从服务器中读取数据
		ClientThread thread=new ClientThread(socket,contentArea);
		thread.start();
	}
	
	public void init(){
		this.setTitle("我的聊天室");
		this.setSize(300,400);
		int x=(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int y=(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((x-this.getWidth())/2,(y-this.getHeight())/2);
		this.setResizable(false);
		
		contentArea=new JTextArea();
		contentArea.setLineWrap(true);
		contentArea.setEditable(false);
		JScrollPane logPanel=new JScrollPane(contentArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		sendArea=new JTextArea();
		sendArea.setLineWrap(true);
		JScrollPane sendPanel=new JScrollPane(sendArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		//创建一个分隔窗格
		JSplitPane splitPane=new JSplitPane(JSplitPane.VERTICAL_SPLIT,logPanel,sendPanel);
		
		splitPane.setDividerLocation(250);
		this.add(splitPane,BorderLayout.CENTER);
		
		//按钮面板
		JPanel btnPanel=new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.add(btnPanel,BorderLayout.SOUTH);
		
		JLabel nameLbl=new JLabel("昵称："+this.name+" ");
		btnPanel.add(nameLbl);
		
		JButton resetBtn=new JButton("关闭");
		resetBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){ System.exit(0);}
		});
		btnPanel.add(resetBtn);
		JButton submitBtn=new JButton("发送");
		
		//发送数据到服务器
		submitBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//获取要发送的内容
				String str=sendArea.getText();
				SimpleDateFormat formater=new SimpleDateFormat("HH:mm:ss");
				String time=formater.format(new Date());
				String sendStr=name+" "+time+"说："+str;
				//往服务器发
				PrintWriter out=null;
				try{
					out=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
					out.println(sendStr);
					out.flush();
				}catch(IOException el){
					el.printStackTrace();
				}
				//发送完毕，置空发送区域内容
				sendArea.setText("");
			}
		});
		btnPanel.add(submitBtn);
	}
}
//客户端跟服务器通信 的线程类
class ClientThread extends Thread{
	/** 客户端跟服务器连接上的Socket*/
	private Socket socket;
	/** 聊天记录区域*/
	private JTextArea contentArea;
	
	public ClientThread(Socket socket,JTextArea contentArea){
		this.socket=socket;
		this.contentArea=contentArea;
	}
	
	public void run(){
		BufferedReader br=null;
		try{
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//从输入流中读取数据，添加到聊天记录区域中
			String str=null;
			while((str=br.readLine())!=null){
				//System.out.println(str);
				contentArea.append(str);
				contentArea.append("\n");
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(br!=null){
				try{
					br.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
}

