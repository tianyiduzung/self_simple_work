package Teacher;

import java.awt.event.*;

import javax.swing.*;

public class TeachSearchInfo extends JPanel implements ActionListener{

	private String host;
	private JLabel[] jlArray={new JLabel("ѧ    ��"),new JLabel("��    ��"),
			new JLabel("��    ��"),new JLabel("��������"),new JLabel("��    ��"),
			new JLabel("ѧ    Ժ"),new JLabel("ר    ҵ"),new JLabel("��    ��"),
			new JLabel("��ѧʱ��"),new JLabel("��"),new JLabel("��"),
			new JLabel("��"),new JLabel("��"),new JLabel("��"),new JLabel("��")};
	private JLabel jl=new JLabel("��������Ҫ��ѯѧ����ѧ��");
	private JTextField jtf=new JTextField();
	private JButton jb=new JButton("��ѯ");
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
				JOptionPane.showMessageDialog(this, "������ѧ��ѧ��","����",
						JOptionPane.ERROR_MESSAGE);
				return;
			}else{	//����GetStuInfo�ķ������ָ��ѧ���Ļ�����Ϣ
				String[] baseinfo=getsi.getBaseInfo(stu_id);
				if(baseinfo[0]==null){
					JOptionPane.showMessageDialog(this, "û�и�ѧ��","����",
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
					
					//ѧ��
					jlArray[0].setBounds(30, 50, 100, 30);
					this.add(jlArray[0]);//���ѧ�ñ�ǩ
					jlArray2[0].setBounds(180, 50, 150, 30);
					this.add(jlArray2[0]);//���ѧ�����������
					//����
					jlArray[1].setBounds(30, 100, 100, 30);
					this.add(jlArray[1]);//���������ǩ
					jlArray2[1].setBounds(180, 100, 150, 30);
					this.add(jlArray2[1]);//����������������
					//�Ա�
					jlArray[2].setBounds(30, 150, 100, 30);
					this.add(jlArray[2]);//�Ա��ǩ
					jlArray2[2].setBounds(180,150,50,30);
					this.add(jlArray2[2]);
					//��������
					jlArray[3].setBounds(30, 200, 100, 30);
					this.add(jlArray[3]);//�������ڱ�ǩ
					jlArray2[3].setBounds(180,200,80,30);
					this.add(jlArray2[3]);//��ݿ�
					jlArray[9].setBounds(260, 200, 20, 30);
					this.add(jlArray[9]);//���ǩ
					jlArray2[4].setBounds(280,200,50,30);
					this.add(jlArray2[4]);//�·ݿ�
					jlArray[10].setBounds(330, 200, 20, 30);
					this.add(jlArray[10]);//�±�ǩ
					jlArray2[5].setBounds(350,200,50,30);
					this.add(jlArray2[5]);//�տ�
					jlArray[11].setBounds(4000, 200, 20, 30);
					this.add(jlArray[11]);//�ձ�ǩ
					//����
					jlArray[4].setBounds(30, 250, 100, 30);
					this.add(jlArray[4]);//�����ǩ
					jlArray2[6].setBounds(180, 250, 150, 30);
					this.add(jlArray2[6]);//���������
					//ѧԺ
					jlArray[5].setBounds(30, 300, 100, 30);
					this.add(jlArray[5]);//ѧԺ��ǩ
					jlArray2[7].setBounds(180,300,200,30);
					this.add(jlArray2[7]);//ѧԺ��
					//רҵ
					jlArray[6].setBounds(30, 350, 100, 30);
					this.add(jlArray[6]);
					jlArray2[8].setBounds(180,350,200,30);
					this.add(jlArray2[8]);//רҵ��
					//�༶
					jlArray[7].setBounds(30, 400, 100, 30);
					this.add(jlArray[7]);
					jlArray2[9].setBounds(180,400,200,30);
					this.add(jlArray2[9]);//�༶��
					//��ѧ����
					jlArray[8].setBounds(30, 450, 100, 30);
					this.add(jlArray[8]);//��ѧ��ǩ
					jlArray2[10].setBounds(180,450,80,30);
					this.add(jlArray2[10]);//���
					jlArray[12].setBounds(260, 450, 20, 30);
					this.add(jlArray[12]);//���ǩ
					jlArray2[11].setBounds(280,450,50,30);
					this.add(jlArray2[11]);//�·ݿ�
					jlArray[13].setBounds(330, 450, 20, 30);
					this.add(jlArray[13]);//�±�ǩ
					jlArray2[12].setBounds(350,450,50,30);
					this.add(jlArray2[12]);//�տ�
					jlArray[14].setBounds(400, 450, 20, 30);
					this.add(jlArray[14]);//�ձ�ǩ
					
					//�ػ����
					this.repaint();
				}
			}
		}
	}
	
	public void setFocus(){
		this.jtf.requestFocus(true);
	}

}
