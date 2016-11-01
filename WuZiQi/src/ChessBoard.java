import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/*��������������
 */
@SuppressWarnings("serial")
public class ChessBoard extends JPanel{
	public static final int MARGIN=30; //�߾�
	public static final int GRID_SPAN=35; //������
	public static final int ROWS=16; //��������
	public static final int COLS=16; //��������
	int chessCount;//��ǰ���̵����Ӹ���
	Point[] chessList=new Point[(ROWS+1)*(COLS+1)];
	int xIndex,yIndex;//��ǰ���µ����ӵ�����
	boolean isBlack=true;//Ĭ�Ͽ�ʼ�Ǻ�������
    boolean gameOver=false;
    public static String colorName;//��ȡ���ӵ�ǰ��ɫ���ƣ�������ʤ������ʾ
    public int continueCount=1;//�������ӵĸ���

	public ChessBoard(){
		//���ñ�����ɫΪ�ٻ�ɫ
		setBackground(Color.ORANGE);
		//��갴��������ϰ���ʱ����
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){				
				if(gameOver)
					return;			
				colorName=isBlack?"����":"����";
				xIndex=(e.getX()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
				//����굥��������λ��ת������������
				yIndex=(e.getY()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
				//���������⣬������
				if(xIndex<0||xIndex>ROWS||yIndex<0||yIndex>COLS)
					return;
				//�жϴ˴��Ƿ��������ӣ����򷵻أ�����
				if(findChess(xIndex,yIndex))
					return;
					
				Point ch=new Point(xIndex,yIndex,isBlack?Color.black:Color.white);
				chessList[chessCount++]=ch;
				repaint();//֪ͨϵͳ���»���			
				if(isWin()){	
					gameOver=true;
					new gameOverJFrame();
				}
		
				isBlack=!isBlack;//ע�⣬�˾����������isWin()����֮��
			}
		});
		
		addMouseMotionListener(new MouseMotionListener(){//�����ڲ���
			public void mouseDragged(MouseEvent e){
				//��갴��������ϰ��²��϶�ʱ��Ҫʵ�ִ˷������˴��ò��ϣ����Բ��ÿ�ʵ��
			}
			//��������ƶ�������Ӷ���������ʾ����״
			public void mouseMoved(MouseEvent e){
				//int x1=(e.getX()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
				//����굥��������λ��ת������������
				//int y1=(e.getY()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
				//��Ϸ�Ѿ�������������
				//���������⣬������
				setCursor(new Cursor(Cursor.HAND_CURSOR));//���ó�����
			}
		});
	
	}
	
	//��ȡ������ɫ����
	public static String getcolorName(){
		return colorName;
	}
	
	//��������
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//������
		for(int i=0;i<=ROWS;i++){//������
			g.drawLine(MARGIN, MARGIN+i*GRID_SPAN, MARGIN+COLS*GRID_SPAN, MARGIN+i*GRID_SPAN);
		}
		for(int i=0;i<=COLS;i++){ //��ֱ��
			g.drawLine(MARGIN+i*GRID_SPAN,MARGIN,MARGIN+i*GRID_SPAN, MARGIN+ROWS*GRID_SPAN);
		}
		
		//�����ӣ�������ֵת��Ϊ����ֵ
		for(int i=0;i<chessCount;i++){
			int xPos=chessList[i].getX()*GRID_SPAN+MARGIN;
			//���񽻲���x����
			int yPos=chessList[i].getY()*GRID_SPAN+MARGIN;
			//���񽻲���y����
			g.setColor(chessList[i].getColor());
			//������ɫ
			g.fillOval(xPos-Point.DIAMETER/2,yPos-Point.DIAMETER /2,Point.DIAMETER ,Point.DIAMETER);
			//������һ�����ӵĺ���ο�
			if(i==chessCount-1){
				//���һ������
				g.setColor(Color.red);
				g.drawRect(xPos-Point.DIAMETER/2, yPos-Point.DIAMETER/2, Point.DIAMETER , Point.DIAMETER );
			}
		}
	}
	
	//�����������в����Ƿ�������Ϊx��y�����Ӵ���
	private boolean findChess(int x,int y){
		for(Point c:chessList){
			if(c!=null&&c.getX()==x&&c.getY()==y)
				return true;
		}
		return false;
	}
	
	//�ж��ķ�Ӯ
	private boolean isWin(){	
		
		Color c=isBlack?Color.black :Color.white ;
		//������������
		for(int x=xIndex-1;x>=0;x--){
			if(getChess(x,yIndex,c)!=null){
				continueCount++;
			}else
				break;
		}
		//�����򶫲���
		for(int x=xIndex+1;x<=COLS;x++){
			if(getChess(x,yIndex,c)!=null){
				continueCount++;
			}else
				break;
		}
		//�жϼ�¼�����ڵ���5������ʾ�˷�ȡʤ
		if(continueCount>=5){
			return true;
		}else
			continueCount=1;
		
		//������һ�����������������
		
		//��������Ѱ��
		for(int y=yIndex-1;y>=0;y--){
			if(getChess(xIndex,y,c)!=null){
				continueCount++;
			}else
				break;
		}
		//��������Ѱ��
		for(int y=yIndex+1;y<=ROWS;y++){
			if(getChess(xIndex,y,c)!=null){
				continueCount++;
			}else
				break;
		}
		if(continueCount>=5){
			return true;
		}else
			continueCount=1;
		
		//������һ�������������б��
		
		//����Ѱ��
		for(int x=xIndex+1,y=yIndex-1;y>=0&&x<=COLS;x++,y--){
			if(getChess(x,y,c)!=null){
				continueCount++;
			}else
				break;
		}
		//����Ѱ��
		for(int x=xIndex-1,y=yIndex+1;y<=ROWS&&x>=0;x--,y++){
			if(getChess(x,y,c)!=null){
				continueCount++;
			}else
				break;
		}
		if(continueCount>=5){
			return true;
		}else
			continueCount=1;
		
		//������һ�������������б��
		
		//����Ѱ��
		for(int x=xIndex-1,y=yIndex-1;y>=0&&x>=0;x--,y--){
			if(getChess(x,y,c)!=null){
				continueCount++;
			}else
				break;
		}
		//����Ѱ��
		for(int x=xIndex+1,y=yIndex+1;y<=ROWS&&x<=COLS;x++,y++){
			if(getChess(x,y,c)!=null){
				continueCount++;
			}else
				break;
		}
		if(continueCount>=5){
			return true;
		}else
			continueCount=1;
		
		return false;
	}
	
	//��ȡ��ǰλ�ú�������ɫ��������ж�
	private Point getChess(int xIndex,int yIndex,Color color){
		for(Point c:chessList){
			if(c!=null && c.getX()==xIndex && c.getY()==yIndex && c.getColor()==color)
				return c;
		}
		return null;
	}
	
	//�����������ȳߴ�
	public Dimension getPreferredSize(){
		return new Dimension(MARGIN*2+GRID_SPAN*COLS,MARGIN*2+GRID_SPAN*ROWS);
	}
	
	//���¿�ʼ��Ϸ
	public void restartGame(){
		gameOver=false;
		for(int i=chessCount;i>=0;i--){
			chessList[i]=null;
			repaint();		
		}
		chessCount=0;
		continueCount=1;	
	}
	
	//����
	public void goback(){
		if(!gameOver){
			chessList[--chessCount]=null;
			isBlack=!isBlack;
			repaint();
		}		
	}
	
}

