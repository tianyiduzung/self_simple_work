/**
 * ����һ�����Զ�̬�ļ���һ��ͼƬ��������JPanel
 */
package com.mhl.tools;
import javax.swing.*;
import java.awt.*;
public class ImagePanel extends JPanel{
		Image im;
		//���캯��ȥָ����Panle�Ĵ�С
		public ImagePanel(Image im)
		{
			this.im=im;
			//ϣ�����Ĵ�С����Ӧ
			int w=Toolkit.getDefaultToolkit().getScreenSize().width;
			int h=Toolkit.getDefaultToolkit().getScreenSize().height;
		}
		
		//��������
		public void paintComponent(Graphics g)
		{
			//����
			super.paintComponent(g);
			g.drawImage(im, 0, 0, this.getWidth(), this.getHeight(), this);
		}
}
