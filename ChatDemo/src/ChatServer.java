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
 * �����ҵķ�������
 * @author Administrator
 *
 */
public class ChatServer {
	/** �����������пͻ��˵�Socket*/
	private List<Socket> sockets=new ArrayList<Socket>();
	public ChatServer() throws IOException{
		this.init();
	}
	//��������
	public void init() throws IOException{
		ServerSocket ss=new ServerSocket(8888);
		Socket soc=new Socket("192.168.1.131",8888);
		String name="������";
		new ChatClient(soc,name);
		
		//System.out.println("�������Ѿ�������8888�˿���....");
		//���������տͻ���
		while(true){
			Socket socket=ss.accept();
			sockets.add(socket);
			//String ip=socket.getInetAddress().getHostAddress();
			//System.out.println("��һ���ͻ�������..����IP�ǣ�"+ip);
			//���ÿ���ͻ��˶�����һ���̵߳�������ͨ��
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
//�������˸��ͻ���ͨ�ŵ��߳���
//��ÿ���ͻ��˷�����������ת�������еĿͻ���
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
				//System.out.println(ip+"˵��"+str);
				//�����еĿͻ���Socketд��Ϣ
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

