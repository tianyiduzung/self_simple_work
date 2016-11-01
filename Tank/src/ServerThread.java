import java.net.*;

public class ServerThread extends Thread{

	TankServer father;
	ServerSocket ss;
	boolean flag=true;
	boolean hasPerson=false;
	public ServerThread(TankServer father,ServerSocket ss)
	{
		this.father=father;
		this.ss=ss;
	}
	public void run()
	{
		while(flag)
		{
			if(hasPerson==false)
			{
				try {
					Socket sc=ss.accept();
					ServerAgentThread sat=new ServerAgentThread(father,sc,this);
					sat.start();
					father.sat=sat;
					this.hasPerson=true;
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			else{
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	}
	
	public void setFlag(boolean flag)
	{
		this.flag=flag;
	}
	public void setHasPerson(boolean hasPerson)
	{
		this.hasPerson=hasPerson;
	}
}
