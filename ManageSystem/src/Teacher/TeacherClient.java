package Teacher;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.tree.*;

import Student.*;

public class TeacherClient extends JFrame{

	private String host;	//host=数据库主机IP+“：”+端口号
	String coll_id;
	//创建树的各个节点
	private DefaultMutableTreeNode dmtnRoot=	//创建根节点
			new DefaultMutableTreeNode(new MyNode("操作选项","0"));
	private DefaultMutableTreeNode dmtn1=
			new DefaultMutableTreeNode(new MyNode("系统选项","1"));
	private DefaultMutableTreeNode dmtn2=
			new DefaultMutableTreeNode(new MyNode("学生信息管理","2"));
	private DefaultMutableTreeNode dmtn3=
			new DefaultMutableTreeNode(new MyNode("课程管理","3"));
	private DefaultMutableTreeNode dmtn4=
			new DefaultMutableTreeNode(new MyNode("班级设置","4"));
	private DefaultMutableTreeNode dmtn11=
			new DefaultMutableTreeNode(new MyNode("退出","11"));
	private DefaultMutableTreeNode dmtn13=
			new DefaultMutableTreeNode(new MyNode("密码修改","13"));
	private DefaultMutableTreeNode dmtn21=
			new DefaultMutableTreeNode(new MyNode("新生报到","21"));
	private DefaultMutableTreeNode dmtn22=
			new DefaultMutableTreeNode(new MyNode("学生信息查询","22"));
	private DefaultMutableTreeNode dmtn221=
			new DefaultMutableTreeNode(new MyNode("基本信息查询","221"));
	private DefaultMutableTreeNode dmtn222=
			new DefaultMutableTreeNode(new MyNode("成绩查询","222"));
	private DefaultMutableTreeNode dmtn31=
			new DefaultMutableTreeNode(new MyNode("开题选项设置","31"));
	private DefaultMutableTreeNode dmtn32=
			new DefaultMutableTreeNode(new MyNode("课程成绩录入","32"));
	private DefaultMutableTreeNode dmtn34=
			new DefaultMutableTreeNode(new MyNode("添加课程","34"));
	private DefaultMutableTreeNode dmtn42=
			new DefaultMutableTreeNode(new MyNode("增加班级","42"));
	
	private DefaultTreeModel dtm=new DefaultTreeModel(dmtnRoot);
	private JTree jt=new JTree(dtm);
	private JScrollPane jspz=new JScrollPane(jt);
	private JPanel jpy=new JPanel();
	private JSplitPane jspl=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jspz,jpy);
	
	//声明功能模块引用
	CardLayout cl;
	private ChangePwdTeacher changepwdteacher;
	private NewStu newstu;
	private TeachSearchInfo teachsearchinfo;
	private StuScore stuscore;
	private CourseManage coursemanage;
	private GradeInDB gradeindb;
	private NewCourse newcourse;
	private NewClass newclass;
	private Welcome welcome;
	
	public TeacherClient(String coll_id,String host){
		this.host=host;this.coll_id=coll_id;
		this.initialTree();
		this.initialPanel();
		this.addListener();
		this.initialJpy();
		this.initialFrame();
	}
	
	//初始化各功能模块
	public void initialPanel(){
		changepwdteacher=new ChangePwdTeacher(host);
		newstu=new NewStu(coll_id,host);
		teachsearchinfo=new TeachSearchInfo(host);
		stuscore=new StuScore(host);
		coursemanage=new CourseManage(coll_id, host);
		gradeindb=new GradeInDB(coll_id, host);
		newcourse=new NewCourse(coll_id, host);
		newclass=new NewClass(coll_id, host);
		welcome=new Welcome("成绩管理系统");
		
	}
	
	//初始化树状列表控件的方法
	public void initialTree(){
		dmtnRoot.add(dmtn1);dmtnRoot.add(dmtn2);
		dmtnRoot.add(dmtn3);dmtnRoot.add(dmtn4);
		dmtn1.add(dmtn11);dmtn1.add(dmtn13);
		dmtn2.add(dmtn21);dmtn2.add(dmtn22);
		dmtn22.add(dmtn221);dmtn22.add(dmtn222);
		dmtn3.add(dmtn31);dmtn3.add(dmtn32);dmtn3.add(dmtn34);
		dmtn4.add(dmtn42);
	}
	
	//将各功能模块添加到面板中
	public void initialJpy(){
		jpy.setLayout(new CardLayout());
		cl=(CardLayout)jpy.getLayout();
		/*各功能模块将在后面各模块的开发过程中逐一添加*/
		jpy.add(welcome,"welcome");
		jpy.add(changepwdteacher,"changepwdteacher");
		jpy.add(newstu,"newstu");
		jpy.add(teachsearchinfo,"teachsearchinfo");
		jpy.add(stuscore,"stuscore");
		jpy.add(coursemanage,"coursemanage");
		jpy.add(gradeindb,"gradeindb");
		jpy.add(newcourse,"newcourse");
		jpy.add(newclass,"newclass");
		
	}
	
	//设置窗体的标题、大小及其可见性
	public void initialFrame(){
		this.add(jspl);
		jspl.setDividerLocation(200);//设置分隔窗体的位置
		jspl.setDividerSize(4);//设置分隔窗体的宽度
		Image image=new ImageIcon("ico.jpg").getImage();//设置窗体的图标
		this.setIconImage(image);
		this.setTitle("教师客户端");
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=900;
		int h=650;
		this.setBounds(centerX-w/2, centerY-h/2-30, w, h);
		this.setVisible(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);//窗体全屏
	}
	
	//为树状列表控件注册鼠标事件监听器
	public void addListener(){
		jt.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				DefaultMutableTreeNode dmtntemp=//得到当前选中的节点对象
						(DefaultMutableTreeNode)jt.getLastSelectedPathComponent();
				MyNode mynode=(MyNode)dmtntemp.getUserObject();
				
				String id=mynode.getId();
				//根据id值显示不同的卡片
				if(id.equals("0")){
					/*欢迎页面*/
					cl.show(jpy, "welcome");
				}else if(id.equals("11")){
					int i=JOptionPane.showConfirmDialog(jpy, "您确定要退出系统吗",
							"询问",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					if(i==0){System.exit(0);}
				}else if(id.equals("13")){
					//更改密码界面
					cl.show(jpy,"changepwdteacher");
					changepwdteacher.setFocus();
				}else if(id.equals("21")){
					//添加学生界面
					cl.show(jpy,"newstu");
					newstu.setFocus();
				}else if(id.equals("221")){
					//学生信息查询界面
					cl.show(jpy, "teachsearchinfo");
					teachsearchinfo.setFocus();
				}else if(id.equals("222")){
					//成绩查询界面
					cl.show(jpy, "stuscore");
					stuscore.setFocus();
				}else if(id.equals("31")){
					//选课管理界面
					cl.show(jpy,"coursemanage");
					coursemanage.updateTable();
				}else if(id.equals("32")){
					//成绩录入界面
					cl.show(jpy, "gradeindb");
				}else if(id.equals("34")){
					//添加课程界面
					cl.show(jpy,"newcourse");
					newcourse.setFocus();
				}else if(id.equals("42")){
					//添加班级页面
					cl.show(jpy, "newclass");
					newclass.setFocus();
				}
			}
			
		});
		jt.setToggleClickCount(1);//将展开节点的鼠标单机次数设为1
	}
	
/*	public static void main(String[] args) {
		new TeacherClient("01","127.0.0.1:3306");
	}*/

	
	
}

class MyNode{	//自定义的初始化树节点的数据对象的类
	private String values;	//声明values属性
	private String id;	//用于确定节点的id属性
	public MyNode(String values,String id){	//构造器
		this.values=values;
		this.id=id;
	}
	public String toString(){return this.values;}	//重写toString方法
	public String getId(){	//Id的get方法
		return  this.id;
	}
}