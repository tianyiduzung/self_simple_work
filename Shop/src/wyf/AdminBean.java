package wyf;

public class AdminBean
{
	private int curPage = 1;
	private String sql;
	private int totalPage = 1;
	
	public int getCurPage()
	{//得到用户当前页
		return this.curPage;
	}
	
	public void setCurPage(int curPage)
	{//记录用户当前页
		this.curPage = curPage;
	}
	
	public void setSql(String sql)
	{
		this.sql = sql;
	}
	
	public String getSql()
	{
		return this.sql;
	}
	
	public int getTotalPage()
	{
		return this.totalPage;
	}
	
	public void setTotalPage(int totalPage)
	{
		this.totalPage = totalPage;
	}
	
	public String manageStr(String str)
	{//得到商品的信息说明
		String info = "";
		String[] msg = str.split("\\|");
		for(String temp:msg)
		{
			info = info+temp+"   ";
		}
		return info;
	}
}
