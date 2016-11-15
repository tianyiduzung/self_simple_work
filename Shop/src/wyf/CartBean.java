package wyf;

import java.util.*;

public class CartBean
{
	private Map<String,Integer> cart = new HashMap<String,Integer>();
	private int curPage = 1;
	private String sql;
	private int totalPage = 1;
	
	public Map<String,Integer> getCart()
	{
		return this.cart;
	}
	
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
	
	public void buy(String sid)
	{
		if(cart.containsKey(sid))
		{//用户不是第一次购买商品
			//该种商品数量加1
			cart.put(sid,cart.get(sid)+1);
		}
		else
		{//用户第一次购买
			cart.put(sid,1);
		}
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
	
	public Vector<String[]> getCartContent()
	{
		Vector<String[]> vgoods = new Vector<String[]>();
		//得到Map中的键值
		Set<String> gid = cart.keySet();
		//得到各物品的信息
		for(String str:gid)
		{
			String[] arr = new String[4];
			arr[3] = str;
			//得到商品数量
			arr[2] = cart.get(str).toString();
			//得到商品名称和价格
			String sql = "select Gname,Gprice from GoodsInfo where Gid="+Integer.parseInt(str);
			Vector<String[]> vtemp = DBcart.getInfoArr(sql);
			String[] ginfo = vtemp.get(0);
			arr[0] = ginfo[0];
			arr[1] = ginfo[1];
			vgoods.add(arr);
		}
		return vgoods;
	}
	
	public double getAccount()
	{
		double account = 0.0;
		//得到所买商品的信息
		Vector<String[]> ginfo = this.getCartContent();
		for(String[] str:ginfo)
		{//得到商品总价
			account += Integer.parseInt(str[2])*Double.parseDouble(str[1]);
		}
		//使商品保留两位小数
		account=Math.round(account*100)/100.0;
		return account;
	}
	
	public void removeItem(String gid)
	{
		cart.remove(gid);
	}
}