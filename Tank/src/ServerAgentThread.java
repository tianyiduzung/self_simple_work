import java.io.*;
import java.net.*;

import javax.swing.*;

public class ServerAgentThread extends Thread{

	TankServer father;
	ServerThread st;
	Socket sc;
	DataInputStream din;
	DataOutputStream dout;
	boolean flag=true;
	public ServerAgentThread(TankServer father,Socket sc,ServerThread st)
	{
		this.father=father;
		this.st=st;
		this.sc=sc;
		try {
			din=new DataInputStream(sc.getInputStream());
			dout=new DataOutputStream(sc.getOutputStream());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		while(flag)
		{
			try {
				String msg=din.readUTF().trim();
				if(msg.startsWith("<#NICK_NAME#>")){//如果传来的是名字
					JOptionPane.showMessageDialog(father,msg.substring(13)+"加入游戏",
					"提示",JOptionPane.INFORMATION_MESSAGE);//弹出加入游戏的对话框
					father.jbStart.setEnabled(true);//将"开始"按钮设为可用
				}
				else if(msg.startsWith("<#LEAVE#>")){//如果客户端离开了
					this.flag=false;//结束该线程
					din.close();//关闭输入输出流
					dout.close();
					sc.close();//关闭Socket
					st.setHasPerson(false);//设置服务器线程标志位
					father.sat=null;//将服务器代理线程设为空
					father.jpz.setStart(false);//设置游戏状态
					JOptionPane.showMessageDialog(father,"副机离开游戏","提示",
					        JOptionPane.INFORMATION_MESSAGE);//给出提示信息
					father.jpz.initialTank();//重新初始化坦克
				}
				else if(msg.startsWith("<#CLIENT#>")){//如果是更新副机的恶信息
					String info=msg.substring(10);
					String[] detail=info.split("/");//获取需要的信息
					int direction=new Integer(detail[0]);
					int x=new Integer(detail[1]);//将信息转化成需要的格式
					int y=new Integer(detail[2]);
					father.jpz.updateClient(direction,x,y);//更新副机
				}
				else if(msg.startsWith("<#FIRECLIENT#>")){//收到副机发射子弹的信息
					String info=msg.substring(14);
					String[] detail=info.split("/");//获得子弹的相应信息
					int direction=new Integer(detail[0]);
					int x=new Integer(detail[1]);
					int y=new Integer(detail[2]);
					this.father.jpz.clientFire(direction,x,y);//添加客户机子弹
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	public void setFlag(boolean flag)
	{
		this.flag=flag;
	}
}
