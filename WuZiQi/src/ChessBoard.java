import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/*五子棋的棋盘设计
 */
@SuppressWarnings("serial")
public class ChessBoard extends JPanel{
	public static final int MARGIN=30; //边距
	public static final int GRID_SPAN=35; //网格间距
	public static final int ROWS=16; //棋盘行数
	public static final int COLS=16; //棋盘列数
	int chessCount;//当前棋盘的棋子个数
	Point[] chessList=new Point[(ROWS+1)*(COLS+1)];
	int xIndex,yIndex;//当前刚下的棋子的索引
	boolean isBlack=true;//默认开始是黑棋先下
    boolean gameOver=false;
    public static String colorName;//获取棋子当前颜色名称，用于在胜出后显示
    public int continueCount=1;//连续棋子的个数

	public ChessBoard(){
		//设置背景颜色为橘黄色
		setBackground(Color.ORANGE);
		//鼠标按键在组件上按下时调用
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){				
				if(gameOver)
					return;			
				colorName=isBlack?"黑棋":"白棋";
				xIndex=(e.getX()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
				//将鼠标单击的坐标位置转换成网格索引
				yIndex=(e.getY()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
				//落在棋盘外，不能下
				if(xIndex<0||xIndex>ROWS||yIndex<0||yIndex>COLS)
					return;
				//判断此处是否已有棋子，有则返回，不下
				if(findChess(xIndex,yIndex))
					return;
					
				Point ch=new Point(xIndex,yIndex,isBlack?Color.black:Color.white);
				chessList[chessCount++]=ch;
				repaint();//通知系统重新绘制			
				if(isWin()){	
					gameOver=true;
					new gameOverJFrame();
				}
		
				isBlack=!isBlack;//注意，此句代码必须放于isWin()方法之后
			}
		});
		
		addMouseMotionListener(new MouseMotionListener(){//匿名内部类
			public void mouseDragged(MouseEvent e){
				//鼠标按键在组件上按下并拖动时需要实现此方法，此处用不上，所以采用空实现
			}
			//监听鼠标移动的区域从而决定它显示的形状
			public void mouseMoved(MouseEvent e){
				//int x1=(e.getX()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
				//将鼠标单击的坐标位置转换成网格索引
				//int y1=(e.getY()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
				//游戏已经结束，不能下
				//落在棋盘外，不能下
				setCursor(new Cursor(Cursor.HAND_CURSOR));//设置成手型
			}
		});
	
	}
	
	//获取棋子颜色方法
	public static String getcolorName(){
		return colorName;
	}
	
	//绘制棋子
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//画棋盘
		for(int i=0;i<=ROWS;i++){//画横线
			g.drawLine(MARGIN, MARGIN+i*GRID_SPAN, MARGIN+COLS*GRID_SPAN, MARGIN+i*GRID_SPAN);
		}
		for(int i=0;i<=COLS;i++){ //画直线
			g.drawLine(MARGIN+i*GRID_SPAN,MARGIN,MARGIN+i*GRID_SPAN, MARGIN+ROWS*GRID_SPAN);
		}
		
		//画棋子，把索引值转换为坐标值
		for(int i=0;i<chessCount;i++){
			int xPos=chessList[i].getX()*GRID_SPAN+MARGIN;
			//网格交叉点的x坐标
			int yPos=chessList[i].getY()*GRID_SPAN+MARGIN;
			//网格交叉点的y坐标
			g.setColor(chessList[i].getColor());
			//设置颜色
			g.fillOval(xPos-Point.DIAMETER/2,yPos-Point.DIAMETER /2,Point.DIAMETER ,Point.DIAMETER);
			//标记最后一个棋子的红矩形框
			if(i==chessCount-1){
				//最后一个棋子
				g.setColor(Color.red);
				g.drawRect(xPos-Point.DIAMETER/2, yPos-Point.DIAMETER/2, Point.DIAMETER , Point.DIAMETER );
			}
		}
	}
	
	//在棋子数组中查找是否有索引为x、y的棋子存在
	private boolean findChess(int x,int y){
		for(Point c:chessList){
			if(c!=null&&c.getX()==x&&c.getY()==y)
				return true;
		}
		return false;
	}
	
	//判断哪方赢
	private boolean isWin(){	
		
		Color c=isBlack?Color.black :Color.white ;
		//横向向西查找
		for(int x=xIndex-1;x>=0;x--){
			if(getChess(x,yIndex,c)!=null){
				continueCount++;
			}else
				break;
		}
		//横向向东查找
		for(int x=xIndex+1;x<=COLS;x++){
			if(getChess(x,yIndex,c)!=null){
				continueCount++;
			}else
				break;
		}
		//判断记录数大于等于5，即表示此方取胜
		if(continueCount>=5){
			return true;
		}else
			continueCount=1;
		
		//继续另一种情况的搜索：纵向
		
		//纵向向上寻找
		for(int y=yIndex-1;y>=0;y--){
			if(getChess(xIndex,y,c)!=null){
				continueCount++;
			}else
				break;
		}
		//纵向向下寻找
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
		
		//继续另一种情况的搜索：斜向
		
		//东北寻找
		for(int x=xIndex+1,y=yIndex-1;y>=0&&x<=COLS;x++,y--){
			if(getChess(x,y,c)!=null){
				continueCount++;
			}else
				break;
		}
		//西南寻找
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
		
		//继续另一种情况的搜索：斜向
		
		//西北寻找
		for(int x=xIndex-1,y=yIndex-1;y>=0&&x>=0;x--,y--){
			if(getChess(x,y,c)!=null){
				continueCount++;
			}else
				break;
		}
		//西南寻找
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
	
	//获取当前位置和棋子颜色对其进行判断
	private Point getChess(int xIndex,int yIndex,Color color){
		for(Point c:chessList){
			if(c!=null && c.getX()==xIndex && c.getY()==yIndex && c.getColor()==color)
				return c;
		}
		return null;
	}
	
	//设置面板的首先尺寸
	public Dimension getPreferredSize(){
		return new Dimension(MARGIN*2+GRID_SPAN*COLS,MARGIN*2+GRID_SPAN*ROWS);
	}
	
	//重新开始游戏
	public void restartGame(){
		gameOver=false;
		for(int i=chessCount;i>=0;i--){
			chessList[i]=null;
			repaint();		
		}
		chessCount=0;
		continueCount=1;	
	}
	
	//悔棋
	public void goback(){
		if(!gameOver){
			chessList[--chessCount]=null;
			isBlack=!isBlack;
			repaint();
		}		
	}
	
}

