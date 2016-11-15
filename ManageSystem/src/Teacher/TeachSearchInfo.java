package Teacher;

import java.awt.event.*;

import javax.swing.*;

public class TeachSearchInfo extends JPanel implements ActionListener{

	private String host;
	private JLabel[] jlArray={new JLabel("学    号"),new JLabel("姓    名"),
			new JLabel("性    别"),new JLabel("出生日期"),new JLabel("籍    贯"),
			new JLabel("学    院"),new JLabel("专    业"),new JLabel("班    级"),
			new JLabel("入学时间"),new JLabel("年"),new JLabel("月"),
			new JLabel("日"),new JLabel("年"),new JLabel("月"),new JLabel("日")};
	private JLabel jl=new JLabel("请输入您要查询学生的学号");
	private JTextField jtf=new JTextField();
	private JButton jb=new JButton("查询");
	private JLabel[] jlArray2=new JLabel[13];
	private GetStuInfo getsi;
	
	public TeachSearchInfo(String host){
		this.host=host;
		getsi=new GetStuInfo(host);
		this.initialFrame();
	}
	
	public void initialFrame(){
		this.setLayout(null);
		jl.setBounds(30, 20, 150, 30);
		this.add(jl);
		jtf.setBounds(175, 20, 150, 30);
		this.add(jtf);
		jtf.addActionListener(this);
		jb.setBounds(330, 20, 100, 30);
		this.add(jb);
		jb.addActionListener(this);
	}
	

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb||e.getSource()==jtf){
			String stu_id=jtf.getText();
			if(stu_id.equals("")){
				JOptionPane.showMessageDialog(this, "请输入学生学号","错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}else{	//调用GetStuInfo的方法获得指定学生的基本信息
				String[] baseinfo=getsi.getBaseInfo(stu_id);
				if(baseinfo[0]==null){
					JOptionPane.showMessageDialog(this, "没有该学生","错误",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				else{
					this.removeAll();
					for(int i=0;i<13;i++){
						jlArray2[i]=new JLabel(baseinfo[i]);
					}
					jl.setBounds(30, 20, 150, 30);
					this.add(jl);
					
					//学号
					jlArray[0].setBounds(30, 50, 100, 30);
					this.add(jlArray[0]);//添加学好标签
					jlArray2[0].setBounds(180, 50, 150, 30);
					this.add(jlArray2[0]);//添加学号区域输入框
					//姓名
					jlArray[1].setBounds(30, 100, 100, 30);
					this.add(jlArray[1]);//添加姓名标签
					jlArray2[1].setBounds(180, 100, 150, 30);
					this.add(jlArray2[1]);//添加姓名区域输入框
					//性别
					jlArray[2].setBounds(30, 150, 100, 30);
					this.add(jlArray[2]);//性别标签
					jlArray2[2].setBounds(180,150,50,30);
					this.add(jlArray2[2]);
					//出生日期
					jlArray[3].setBounds(30, 200, 100, 30);
					this.add(jlArray[3]);//出生日期标签
					jlArray2[3].setBounds(180,200,80,30);
					this.add(jlArray2[3]);//年份框
					jlArray[9].setBounds(260, 200, 20, 30);
					this.add(jlArray[9]);//年标签
					jlArray2[4].setBounds(280,200,50,30);
					this.add(jlArray2[4]);//月份框
					jlArray[10].setBounds(330, 200, 20, 30);
					this.add(jlArray[10]);//月标签
					jlArray2[5].setBounds(350,200,50,30);
					this.add(jlArray2[5]);//日框
					jlArray[11].setBounds(4000, 200, 20, 30);
					this.add(jlArray[11]);//日标签
					//籍贯
					jlArray[4].setBounds(30, 250, 100, 30);
					this.add(jlArray[4]);//籍贯标签
					jlArray2[6].setBounds(180, 250, 150, 30);
					this.add(jlArray2[6]);//籍贯输入框
					//学院
					jlArray[5].setBounds(30, 300, 100, 30);
					this.add(jlArray[5]);//学院标签
					jlArray2[7].setBounds(180,300,200,30);
					this.add(jlArray2[7]);//学院框
					//专业
					jlArray[6].setBounds(30, 350, 100, 30);
					this.add(jlArray[6]);
					jlArray2[8].setBounds(180,350,200,30);
					this.add(jlArray2[8]);//专业框
					//班级
					jlArray[7].setBounds(30, 400, 100, 30);
					this.add(jlArray[7]);
					jlArray2[9].setBounds(180,400,200,30);
					this.add(jlArray2[9]);//班级框
					//入学日期
					jlArray[8].setBounds(30, 450, 100, 30);
					this.add(jlArray[8]);//入学标签
					jlArray2[10].setBounds(180,450,80,30);
					this.add(jlArray2[10]);//年框
					jlArray[12].setBounds(260, 450, 20, 30);
					this.add(jlArray[12]);//年标签
					jlArray2[11].setBounds(280,450,50,30);
					this.add(jlArray2[11]);//月份框
					jlArray[13].setBounds(330, 450, 20, 30);
					this.add(jlArray[13]);//月标签
					jlArray2[12].setBounds(350,450,50,30);
					this.add(jlArray2[12]);//日框
					jlArray[14].setBounds(400, 450, 20, 30);
					this.add(jlArray[14]);//日标签
					
					//重绘面板
					this.repaint();
				}
			}
		}
	}
	
	public void setFocus(){
		this.jtf.requestFocus(true);
	}

}
