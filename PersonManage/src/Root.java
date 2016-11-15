
import java.awt.*;import java.awt.event.*;
import javax.swing.event.*;import javax.swing.*;
import javax.swing.tree.*;import javax.xml.parsers.*;
import org.w3c.dom.*;import java.io.*;import java.net.*;
public class Root extends JFrame{
    DefaultMutableTreeNode[]  dmtn={new DefaultMutableTreeNode(new NodeValue("人事管理系统")),
	new DefaultMutableTreeNode(new NodeValue("个人资料档案")),new DefaultMutableTreeNode(new NodeValue("个人简历")),
	new DefaultMutableTreeNode(new NodeValue("查询职工资料")),new DefaultMutableTreeNode(new NodeValue("工资管理")),
	new DefaultMutableTreeNode(new NodeValue("考勤管理")),new DefaultMutableTreeNode(new NodeValue("退出"))};//创建节点数组
    DefaultTreeModel dtm=new DefaultTreeModel(dmtn[0]);//创建树模型，指定根节点为"人事管理系统"
    JTree jt=new JTree(dtm);//创建包含dtm树模型的JTree对象
    JScrollPane jsp=new JScrollPane(jt); //为JTree创建滚动窗体
    private JSplitPane jsplr=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private JPanel jp=new JPanel();//创建JPanel对象
	private JLabel jlRoot=new JLabel();
	CardLayout cl=new CardLayout();//获取卡片布局管理器引用
    public Root(){
        for(int i=1;i<7;i++){dtm.insertNodeInto(dmtn[i],dmtn[0],i-1);}//向根节点添加子节点	
		jt.setEditable(false);//设置该树中节点是不可编辑的
		this.add(jsplr);//将包含树的滚动窗口添加进窗体
		jsplr.setLeftComponent(jt);//将包含树的滚动窗口添加进左边的子窗口
		jsplr.setRightComponent(jp);        
        jsplr.setDividerLocation(200);//设置分隔条的初始位置    
        jsplr.setDividerSize(4); //设置分隔条的宽度 
        jlRoot.setFont(new Font("Courier",Font.PLAIN,30));
		jlRoot.setHorizontalAlignment(JLabel.CENTER);
		jlRoot.setVerticalAlignment(JLabel.CENTER);	
		jt.setShowsRootHandles(true);//设置显示根节点的控制图标
    	jp.setLayout(cl);
    	jp.add(jlRoot,"root");//初始化各节点
    	jp.add(new Person(),"person");
    	jp.add(new Introduce(),"introduce");
    	jp.add(new SearchMessage(),"Search");
    	jp.add(new Wage(),"wage");
    	jp.add(new Attend(),"attend");
    	jt.addTreeSelectionListener(new TreeSelectionListener(){//用内部类显示树的各选择节点
			public void valueChanged(TreeSelectionEvent e){
				DefaultMutableTreeNode cdmtn=(DefaultMutableTreeNode)e.getPath().getLastPathComponent();				
				NodeValue cnv=(NodeValue)cdmtn.getUserObject();
				if(cnv.value.equals("人事管理系统")){cl.show(jp,"root");}//显示主界面
                if(cnv.value.equals("个人资料档案")){cl.show(jp,"person");}
				if(cnv.value.equals("个人简历")){cl.show(jp,"introduce");}
				if(cnv.value.equals("查询职工资料")){cl.show(jp,"Search");}//显示查询职工资料界面
				if(cnv.value.equals("工资管理")){cl.show(jp,"wage");}
				if(cnv.value.equals("考勤管理")){cl.show(jp,"attend");}
				else if(cnv.value.equals("退出")){//用选择菜单提示是否退出系统
					int i=JOptionPane.showConfirmDialog(Root.this,"是否退出系统?",
							"消息",JOptionPane.YES_NO_OPTION);
					if(i==JOptionPane.YES_OPTION){System.exit(0);}}								
			}
		});
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置窗体的关闭动作	
 		Image image=new ImageIcon("ico.gif").getImage();//对logo图片进行初始化	 
 		this.setIconImage(image);
 		Image icon = Toolkit.getDefaultToolkit().getImage("rs.jpg");//设置其界面的图片
		ImageIcon ic = new ImageIcon(icon);//初始化图片
		jlRoot.setIcon(ic);
		this.setTitle("长河实业人事管理系统");	
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX=screenSize.width/2;//设置窗体首次出现的大小和位置--自动居中
		int centerY=screenSize.height/2;
		int w=800;//本窗体宽度
		int h=600;//本窗体高度
		this.setBounds(centerX-w/2,centerY-h/2-100,w,h);//设置窗体出现在屏幕中央
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);//窗体全屏
		this.setVisible(true);
    }
    public static void main(String[]args){new Root();}
}
class NodeValue{//自定义的初始化树节点的数据对象的类
	String value;	
	public NodeValue(String value){this.value=value;}//构造器
	public String getValue(){return this.value;}
	@Override
	public String toString(){return value;}//重写的方法
}