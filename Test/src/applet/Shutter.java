package applet;
import java.awt.*;
import java.applet.*;
import java.io.*;
import java.awt.image.*;
public class Shutter extends Applet implements Runnable{

	private Image myIMG[],myImageToShow;//定义图像数组和要显示的图像变量
	private MediaTracker imageTracker;//定义图像跟踪器
	//定义图像的宽度、高度、图像总数、当前图像编号、下一个图像编号
	private int myIMGWidth,myIMGHeight,totalImage=5,currentImage,nextImage;
	private Thread mythread;
	private int delay;
	//定义整形数组，分别用来存放各图像的像素
	private int totalPix,pix1[],pix2[],pix3[],pix4[],pix5[],pixA[],pixB[];

	public void init()
	{
		this.setBackground(Color.black);
		//从Html文件中提取图像，并加入图像跟踪器
		myIMG=new Image[totalImage];
		imageTracker=new MediaTracker(this);
		String s=new String("");
		for(int i=0;i<totalImage;i++)
		{
			
			myIMG[i]=getImage(getCodeBase(),"Pic"+(i+1));
			imageTracker.addImage(myIMG[i],0);
		}
		try {
			imageTracker.waitForID(0);
		} catch (InterruptedException e) {
			// TODO: handle exception
		}
		//设置延迟3秒钟
		if(getParameter("delay")==null)
		{
			delay=3000;
		}else{
			delay=Integer.parseInt(getParameter("delay"));
		}
		//以第一幅图像的尺寸为显示尺寸
		myIMGWidth=myIMG[0].getWidth(this);
		myIMGHeight=myIMG[0].getHeight(this);
		totalPix=myIMGWidth*myIMGHeight;
		//获取第一幅图像的像素
		pix1=new int[totalPix];
		PixelGrabber PG1=new PixelGrabber(myIMG[0], 0, 0, myIMGWidth,
				myIMGHeight, pix1,0,myIMGWidth);
		try {
			PG1.grabPixels();
		} catch (Exception e) {
			// TODO: handle exception
		}
		//获取第二幅图像的像素
		pix2=new int[totalPix];
		PixelGrabber PG2=new PixelGrabber(myIMG[1], 0, 0, myIMGWidth,
				myIMGHeight, pix2,0,myIMGWidth);
		try {
			PG2.grabPixels();
		} catch (Exception e) {
			// TODO: handle exception
		}
		//获取第三幅图像的像素
		pix3=new int[totalPix];
		PixelGrabber PG3=new PixelGrabber(myIMG[2], 0, 0, myIMGWidth,
				myIMGHeight, pix3,0,myIMGWidth);
		try {
			PG3.grabPixels();
		} catch (Exception e) {
			// TODO: handle exception
		}
		//获取第四幅图像的像素
		pix4=new int[totalPix];
		PixelGrabber PG4=new PixelGrabber(myIMG[3], 0, 0, myIMGWidth,
				myIMGHeight, pix4,0,myIMGWidth);
		try {
			PG4.grabPixels();
		} catch (Exception e) {
			// TODO: handle exception
		}
		//获取第五幅图像的像素
		pix5=new int[totalPix];
		PixelGrabber PG5=new PixelGrabber(myIMG[4], 0, 0, myIMGWidth,
				myIMGHeight, pix5,0,myIMGWidth);
		try {
			PG5.grabPixels();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		currentImage=0;
		pixA=new int[totalPix];
		pixB=new int[totalPix];
		myImageToShow=myIMG[0];
		mythread=new Thread(this);
		mythread.start();
	}
	
	public void paint(Graphics g)
	{
		g.drawImage(myImageToShow, 0, 0, this);
	}
	public void update(Graphics g)
	{
		paint(g);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(mythread==null)
		{
			mythread=new Thread(this);
			mythread.start();
		}
		while(true)
		{
			try {
				mythread.sleep(delay);
				nextImage=((currentImage+1)%totalImage);
				if(currentImage==0)//显示第一幅图片
				{
					//使用数组复制arraycopy方法，复制图片像素
					//复制第一幅图片像素到pixA
					System.arraycopy(pix1, 0, pixA, 0, totalPix);
					//复制第二幅图片像素到pixB
					System.arraycopy(pix2, 0, pixB, 0, totalPix);
					//用MemoryImageSource方法将像素数组pixA对应到图像
					myImageToShow=createImage(new MemoryImageSource(myIMGWidth,
							myIMGHeight, pixA, 0, myIMGWidth));
					repaint();
				}
				if(currentImage==1)//显示第二幅图片
				{
					System.arraycopy(pix2, 0, pixA, 0, totalPix);
					//复制第二幅图片像素到pixB
					System.arraycopy(pix3, 0, pixB, 0, totalPix);
					//用MemoryImageSource方法将像素数组pixA对应到图像
					myImageToShow=createImage(new MemoryImageSource(myIMGWidth,
							myIMGHeight, pixA, 0, myIMGWidth));
					repaint();
				}
				if(currentImage==2)//显示第三幅图片
				{
					System.arraycopy(pix3, 0, pixA, 0, totalPix);
					//复制第二幅图片像素到pixB
					System.arraycopy(pix4, 0, pixB, 0, totalPix);
					//用MemoryImageSource方法将像素数组pixA对应到图像
					myImageToShow=createImage(new MemoryImageSource(myIMGWidth,
							myIMGHeight, pixA, 0, myIMGWidth));
					repaint();
				}
				if(currentImage==3)//显示第四幅图片
				{
					System.arraycopy(pix4, 0, pixA, 0, totalPix);
					//复制第二幅图片像素到pixB
					System.arraycopy(pix5, 0, pixB, 0, totalPix);
					//用MemoryImageSource方法将像素数组pixA对应到图像
					myImageToShow=createImage(new MemoryImageSource(myIMGWidth,
							myIMGHeight, pixA, 0, myIMGWidth));
					repaint();
				}
				if(currentImage==4)//显示第五幅图片
				{
					System.arraycopy(pix5, 0, pixA, 0, totalPix);
					//复制第二幅图片像素到pixB
					System.arraycopy(pix1, 0, pixB, 0, totalPix);
					//用MemoryImageSource方法将像素数组pixA对应到图像
					myImageToShow=createImage(new MemoryImageSource(myIMGWidth,
							myIMGHeight, pixA, 0, myIMGWidth));
					repaint();
				}
				
				while(true)
				{
					//产生myIMGHeight/10个像素区域
					for(int i=0;i<(int)(myIMGHeight/10);i++)
					{
						try {
							mythread.sleep(20);
							for(int j=0;j<myIMGHeight;j+=(int)(myIMGHeight/10))
							{
								for(int k=0;k<myIMGWidth;k++)
								{
									//pixA对应的部分图像用pixB对应的图像代替
									pixA[myIMGWidth*(j+i)+k]=pixB[myIMGWidth*(j+i)+k];
								}
							}
						} catch (InterruptedException e) {
							// TODO: handle exception
						}
						myImageToShow=createImage(new MemoryImageSource(myIMGWidth,
								myIMGHeight, pixA, 0, myIMGWidth));
						repaint();
					}
					break;
				}
				currentImage=nextImage;//循环显示图像
				repaint();
				
			} catch (InterruptedException e) {
				// TODO: handle exception
			}
		}
	}
}
