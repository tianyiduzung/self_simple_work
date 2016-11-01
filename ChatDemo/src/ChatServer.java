import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 聊天室的服务器端
 * @author Administrator
 *
 */
public class ChatServer {
	/** 用来保存所有客户端的Socket*/
	private List<Socket> sockets=new ArrayList<Socket>();
	public ChatServer() throws IOException{
		this.init();
	}
	//启动监听
	public void init() throws IOException{
		ServerSocket ss=new ServerSocket(8888);
		Socket soc=new Socket("192.168.1.131",8888);
		String name="服务器";
		new ChatClient(soc,name);
		
		//System.out.println("服务器已经监听在8888端口了....");
		//监听并接收客户端
		while(true){
			Socket socket=ss.accept();
			sockets.add(socket);
			//String ip=socket.getInetAddress().getHostAddress();
			//System.out.println("有一个客户端来了..它的IP是："+ip);
			//针对每个客户端都启动一个线程单独跟它通信
			Thread thread=new Thread(new ServerRunner(sockets,socket));
			thread.start();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args){
		try{
			new ChatServer();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
//服务器端跟客户端通信的线程类
//把每个客户端发过来的数据转发给所有的客户端
class ServerRunner implements Runnable{
	private List<Socket> sockets;
	private Socket currentSocket;
	
	public ServerRunner(List<Socket> sockets,Socket currentSocket){
		this.sockets=sockets;
		this.currentSocket=currentSocket;
	}
	public void run(){
		//String ip=currentSocket.getInetAddress().getHostAddress();
		BufferedReader br=null;
		try{
			br=new BufferedReader(new InputStreamReader(currentSocket.getInputStream()));
			
			String str=null;
			while((str=br.readLine())!=null){
				//System.out.println(ip+"说："+str);
				//往所有的客户端Socket写信息
				for(Socket temp:sockets){
					PrintWriter pw=new PrintWriter(new OutputStreamWriter(temp.getOutputStream()));
					pw.println(str);
					pw.flush();
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}

