package Teacher;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.tree.*;

import Student.*;

public class TeacherClient extends JFrame{

	private String host;	//host=���ݿ�����IP+������+�˿ں�
	String coll_id;
	//�������ĸ����ڵ�
	private DefaultMutableTreeNode dmtnRoot=	//�������ڵ�
			new DefaultMutableTreeNode(new MyNode("����ѡ��","0"));
	private DefaultMutableTreeNode dmtn1=
			new DefaultMutableTreeNode(new MyNode("ϵͳѡ��","1"));
	private DefaultMutableTreeNode dmtn2=
			new DefaultMutableTreeNode(new MyNode("ѧ����Ϣ����","2"));
	private DefaultMutableTreeNode dmtn3=
			new DefaultMutableTreeNode(new MyNode("�γ̹���","3"));
	private DefaultMutableTreeNode dmtn4=
			new DefaultMutableTreeNode(new MyNode("�༶����","4"));
	private DefaultMutableTreeNode dmtn11=
			new DefaultMutableTreeNode(new MyNode("�˳�","11"));
	private DefaultMutableTreeNode dmtn13=
			new DefaultMutableTreeNode(new MyNode("�����޸�","13"));
	private DefaultMutableTreeNode dmtn21=
			new DefaultMutableTreeNode(new MyNode("��������","21"));
	private DefaultMutableTreeNode dmtn22=
			new DefaultMutableTreeNode(new MyNode("ѧ����Ϣ��ѯ","22"));
	private DefaultMutableTreeNode dmtn221=
			new DefaultMutableTreeNode(new MyNode("������Ϣ��ѯ","221"));
	private DefaultMutableTreeNode dmtn222=
			new DefaultMutableTreeNode(new MyNode("�ɼ���ѯ","222"));
	private DefaultMutableTreeNode dmtn31=
			new DefaultMutableTreeNode(new MyNode("����ѡ������","31"));
	private DefaultMutableTreeNode dmtn32=
			new DefaultMutableTreeNode(new MyNode("�γ̳ɼ�¼��","32"));
	private DefaultMutableTreeNode dmtn34=
			new DefaultMutableTreeNode(new MyNode("��ӿγ�","34"));
	private DefaultMutableTreeNode dmtn42=
			new DefaultMutableTreeNode(new MyNode("���Ӱ༶","42"));
	
	private DefaultTreeModel dtm=new DefaultTreeModel(dmtnRoot);
	private JTree jt=new JTree(dtm);
	private JScrollPane jspz=new JScrollPane(jt);
	private JPanel jpy=new JPanel();
	private JSplitPane jspl=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jspz,jpy);
	
	//��������ģ������
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
	
	//��ʼ��������ģ��
	public void initialPanel(){
		changepwdteacher=new ChangePwdTeacher(host);
		newstu=new NewStu(coll_id,host);
		teachsearchinfo=new TeachSearchInfo(host);
		stuscore=new StuScore(host);
		coursemanage=new CourseManage(coll_id, host);
		gradeindb=new GradeInDB(coll_id, host);
		newcourse=new NewCourse(coll_id, host);
		newclass=new NewClass(coll_id, host);
		welcome=new Welcome("�ɼ�����ϵͳ");
		
	}
	
	//��ʼ����״�б�ؼ��ķ���
	public void initialTree(){
		dmtnRoot.add(dmtn1);dmtnRoot.add(dmtn2);
		dmtnRoot.add(dmtn3);dmtnRoot.add(dmtn4);
		dmtn1.add(dmtn11);dmtn1.add(dmtn13);
		dmtn2.add(dmtn21);dmtn2.add(dmtn22);
		dmtn22.add(dmtn221);dmtn22.add(dmtn222);
		dmtn3.add(dmtn31);dmtn3.add(dmtn32);dmtn3.add(dmtn34);
		dmtn4.add(dmtn42);
	}
	
	//��������ģ����ӵ������
	public void initialJpy(){
		jpy.setLayout(new CardLayout());
		cl=(CardLayout)jpy.getLayout();
		/*������ģ�齫�ں����ģ��Ŀ�����������һ���*/
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
	
	//���ô���ı��⡢��С����ɼ���
	public void initialFrame(){
		this.add(jspl);
		jspl.setDividerLocation(200);//���÷ָ������λ��
		jspl.setDividerSize(4);//���÷ָ�����Ŀ��
		Image image=new ImageIcon("ico.jpg").getImage();//���ô����ͼ��
		this.setIconImage(image);
		this.setTitle("��ʦ�ͻ���");
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=900;
		int h=650;
		this.setBounds(centerX-w/2, centerY-h/2-30, w, h);
		this.setVisible(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);//����ȫ��
	}
	
	//Ϊ��״�б�ؼ�ע������¼�������
	public void addListener(){
		jt.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				DefaultMutableTreeNode dmtntemp=//�õ���ǰѡ�еĽڵ����
						(DefaultMutableTreeNode)jt.getLastSelectedPathComponent();
				MyNode mynode=(MyNode)dmtntemp.getUserObject();
				
				String id=mynode.getId();
				//����idֵ��ʾ��ͬ�Ŀ�Ƭ
				if(id.equals("0")){
					/*��ӭҳ��*/
					cl.show(jpy, "welcome");
				}else if(id.equals("11")){
					int i=JOptionPane.showConfirmDialog(jpy, "��ȷ��Ҫ�˳�ϵͳ��",
							"ѯ��",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					if(i==0){System.exit(0);}
				}else if(id.equals("13")){
					//�����������
					cl.show(jpy,"changepwdteacher");
					changepwdteacher.setFocus();
				}else if(id.equals("21")){
					//���ѧ������
					cl.show(jpy,"newstu");
					newstu.setFocus();
				}else if(id.equals("221")){
					//ѧ����Ϣ��ѯ����
					cl.show(jpy, "teachsearchinfo");
					teachsearchinfo.setFocus();
				}else if(id.equals("222")){
					//�ɼ���ѯ����
					cl.show(jpy, "stuscore");
					stuscore.setFocus();
				}else if(id.equals("31")){
					//ѡ�ι������
					cl.show(jpy,"coursemanage");
					coursemanage.updateTable();
				}else if(id.equals("32")){
					//�ɼ�¼�����
					cl.show(jpy, "gradeindb");
				}else if(id.equals("34")){
					//��ӿγ̽���
					cl.show(jpy,"newcourse");
					newcourse.setFocus();
				}else if(id.equals("42")){
					//��Ӱ༶ҳ��
					cl.show(jpy, "newclass");
					newclass.setFocus();
				}
			}
			
		});
		jt.setToggleClickCount(1);//��չ���ڵ����굥��������Ϊ1
	}
	
/*	public static void main(String[] args) {
		new TeacherClient("01","127.0.0.1:3306");
	}*/

	
	
}

class MyNode{	//�Զ���ĳ�ʼ�����ڵ�����ݶ������
	private String values;	//����values����
	private String id;	//����ȷ���ڵ��id����
	public MyNode(String values,String id){	//������
		this.values=values;
		this.id=id;
	}
	public String toString(){return this.values;}	//��дtoString����
	public String getId(){	//Id��get����
		return  this.id;
	}
}