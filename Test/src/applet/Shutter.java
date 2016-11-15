package applet;
import java.awt.*;
import java.applet.*;
import java.io.*;
import java.awt.image.*;
public class Shutter extends Applet implements Runnable{

	private Image myIMG[],myImageToShow;//����ͼ�������Ҫ��ʾ��ͼ�����
	private MediaTracker imageTracker;//����ͼ�������
	//����ͼ��Ŀ�ȡ��߶ȡ�ͼ����������ǰͼ���š���һ��ͼ����
	private int myIMGWidth,myIMGHeight,totalImage=5,currentImage,nextImage;
	private Thread mythread;
	private int delay;
	//�����������飬�ֱ�������Ÿ�ͼ�������
	private int totalPix,pix1[],pix2[],pix3[],pix4[],pix5[],pixA[],pixB[];

	public void init()
	{
		this.setBackground(Color.black);
		//��Html�ļ�����ȡͼ�񣬲�����ͼ�������
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
		//�����ӳ�3����
		if(getParameter("delay")==null)
		{
			delay=3000;
		}else{
			delay=Integer.parseInt(getParameter("delay"));
		}
		//�Ե�һ��ͼ��ĳߴ�Ϊ��ʾ�ߴ�
		myIMGWidth=myIMG[0].getWidth(this);
		myIMGHeight=myIMG[0].getHeight(this);
		totalPix=myIMGWidth*myIMGHeight;
		//��ȡ��һ��ͼ�������
		pix1=new int[totalPix];
		PixelGrabber PG1=new PixelGrabber(myIMG[0], 0, 0, myIMGWidth,
				myIMGHeight, pix1,0,myIMGWidth);
		try {
			PG1.grabPixels();
		} catch (Exception e) {
			// TODO: handle exception
		}
		//��ȡ�ڶ���ͼ�������
		pix2=new int[totalPix];
		PixelGrabber PG2=new PixelGrabber(myIMG[1], 0, 0, myIMGWidth,
				myIMGHeight, pix2,0,myIMGWidth);
		try {
			PG2.grabPixels();
		} catch (Exception e) {
			// TODO: handle exception
		}
		//��ȡ������ͼ�������
		pix3=new int[totalPix];
		PixelGrabber PG3=new PixelGrabber(myIMG[2], 0, 0, myIMGWidth,
				myIMGHeight, pix3,0,myIMGWidth);
		try {
			PG3.grabPixels();
		} catch (Exception e) {
			// TODO: handle exception
		}
		//��ȡ���ķ�ͼ�������
		pix4=new int[totalPix];
		PixelGrabber PG4=new PixelGrabber(myIMG[3], 0, 0, myIMGWidth,
				myIMGHeight, pix4,0,myIMGWidth);
		try {
			PG4.grabPixels();
		} catch (Exception e) {
			// TODO: handle exception
		}
		//��ȡ�����ͼ�������
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
				if(currentImage==0)//��ʾ��һ��ͼƬ
				{
					//ʹ�����鸴��arraycopy����������ͼƬ����
					//���Ƶ�һ��ͼƬ���ص�pixA
					System.arraycopy(pix1, 0, pixA, 0, totalPix);
					//���Ƶڶ���ͼƬ���ص�pixB
					System.arraycopy(pix2, 0, pixB, 0, totalPix);
					//��MemoryImageSource��������������pixA��Ӧ��ͼ��
					myImageToShow=createImage(new MemoryImageSource(myIMGWidth,
							myIMGHeight, pixA, 0, myIMGWidth));
					repaint();
				}
				if(currentImage==1)//��ʾ�ڶ���ͼƬ
				{
					System.arraycopy(pix2, 0, pixA, 0, totalPix);
					//���Ƶڶ���ͼƬ���ص�pixB
					System.arraycopy(pix3, 0, pixB, 0, totalPix);
					//��MemoryImageSource��������������pixA��Ӧ��ͼ��
					myImageToShow=createImage(new MemoryImageSource(myIMGWidth,
							myIMGHeight, pixA, 0, myIMGWidth));
					repaint();
				}
				if(currentImage==2)//��ʾ������ͼƬ
				{
					System.arraycopy(pix3, 0, pixA, 0, totalPix);
					//���Ƶڶ���ͼƬ���ص�pixB
					System.arraycopy(pix4, 0, pixB, 0, totalPix);
					//��MemoryImageSource��������������pixA��Ӧ��ͼ��
					myImageToShow=createImage(new MemoryImageSource(myIMGWidth,
							myIMGHeight, pixA, 0, myIMGWidth));
					repaint();
				}
				if(currentImage==3)//��ʾ���ķ�ͼƬ
				{
					System.arraycopy(pix4, 0, pixA, 0, totalPix);
					//���Ƶڶ���ͼƬ���ص�pixB
					System.arraycopy(pix5, 0, pixB, 0, totalPix);
					//��MemoryImageSource��������������pixA��Ӧ��ͼ��
					myImageToShow=createImage(new MemoryImageSource(myIMGWidth,
							myIMGHeight, pixA, 0, myIMGWidth));
					repaint();
				}
				if(currentImage==4)//��ʾ�����ͼƬ
				{
					System.arraycopy(pix5, 0, pixA, 0, totalPix);
					//���Ƶڶ���ͼƬ���ص�pixB
					System.arraycopy(pix1, 0, pixB, 0, totalPix);
					//��MemoryImageSource��������������pixA��Ӧ��ͼ��
					myImageToShow=createImage(new MemoryImageSource(myIMGWidth,
							myIMGHeight, pixA, 0, myIMGWidth));
					repaint();
				}
				
				while(true)
				{
					//����myIMGHeight/10����������
					for(int i=0;i<(int)(myIMGHeight/10);i++)
					{
						try {
							mythread.sleep(20);
							for(int j=0;j<myIMGHeight;j+=(int)(myIMGHeight/10))
							{
								for(int k=0;k<myIMGWidth;k++)
								{
									//pixA��Ӧ�Ĳ���ͼ����pixB��Ӧ��ͼ�����
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
				currentImage=nextImage;//ѭ����ʾͼ��
				repaint();
				
			} catch (InterruptedException e) {
				// TODO: handle exception
			}
		}
	}
}
