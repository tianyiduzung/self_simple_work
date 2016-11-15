package Teacher;

import javax.swing.*;

public class Welcome extends JLabel{

	String str;
	static Icon icon=new ImageIcon("xs.jpg");
	public Welcome(String str){
		this.str=str;
		this.initialFrame();
	}
	public void initialFrame(){
		this.setIcon(icon);
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setVerticalAlignment(JLabel.CENTER);
	}
}
