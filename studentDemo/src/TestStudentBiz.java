class TestStudentBiz 
{
	public static void main(String[] args) 
	{
		StudentBiz td=new StudentBiz();
		try
		{
			//�����ò���������
			td.readDate();
			//td.insertDateByName("zhang","02",87);
			//td.updateDateByName("zah");
			//td.deleDateByName("zah");	
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
