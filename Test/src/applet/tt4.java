package applet;
import java.applet.*;
import java.awt.*;
import java.awt.image.*;

public class tt4 extends Applet
{
	Image art,Buf;
	int onced=0;
	boolean is_color=true;
	Graphics Bufg;//使用双缓冲区技术抑制闪烁
	Dimension xy=null;
	public void init()
	{
		art=getImage(getDocumentBase(),"test.jpg");
		resize(700,600);//装入图片
	}
	public void paint(Graphics g)
	{
		if(onced==0)
		{
			g.drawImage(art,0,0,this);
		}
		if(onced==1||onced==2)
		{
			g.setColor(new Color(255,200,0));
			g.drawString("running!",1,30);
		}
		if(onced==3)
		{
			if(is_color) g.drawImage(Buf,0,0,this);
			else g.drawImage(art,0,0,this);
			is_color=!is_color;
		}
	}

	public boolean mouseDown(Event evt,int x,int y)
	{
		if(onced==0)
		{
			onced=1;
		}
		repaint();
		return true;
	}
	public boolean mouseUp(Event evt,int x,int y)
	{
		if(onced==1)
		{
			onced=2;
			int wd=art.getWidth(this);
			int ht=art.getHeight(this);
			GetPixels(art,0,0,wd,ht);
		}
		return true;
	}

	public void GetPixels(Image img,int x,int y,int w,int h)
	{
		int[] pixels=new int[w*h];
		//定义一块内存空间
		int gray;
		PixelGrabber pg=new PixelGrabber(img,x,y,w,h,pixels,0,w);
		try{
			pg.grabPixels();
		}catch(InterruptedException e)
		{
			System.err.println("interrupt waiting for pixels!");
			return;
		}
		for(int j=0;j<h;j++)
		{
			for(int i=0;i<w;i++)
			{
				gray=(int)(((pixels[w*j+i]>>16)&0xff)*0.3);
				gray+=(int)(((pixels[w*j+i]>>8)&0xff)*0.59);
				gray+=(int)(((pixels[w*j+i])&0xff)*0.11);
				pixels[w*j+i]=(255<<24)|(gray<<16)|(gray<<8)|gray;
			}
		}
		Image pic=createImage(new MemoryImageSource(w,h,pixels,0,w));
		Bufg.drawImage(pic,0,0,this);
		onced=3;
		repaint();
	}
	public void update(Graphics g)
	{
		if(xy==null)
		{
			xy=this.size();
			Buf=createImage(xy.width,xy.height);
			Bufg=Buf.getGraphics();
		}
		paint(g);
	}

}