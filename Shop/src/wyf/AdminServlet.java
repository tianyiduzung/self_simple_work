package wyf;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class AdminServlet extends HttpServlet
{
	public  void doGet(HttpServletRequest request,HttpServletResponse response)
              							throws ServletException,IOException
    {
    	this.doPost(request,response);
    }
	public  void doPost(HttpServletRequest request,HttpServletResponse response)
              							throws ServletException,IOException
    {
    	//设置编码格式
    	request.setCharacterEncoding("gb2312");
    	response.setContentType("text/html;charset=gb2312");
    	response.setCharacterEncoding("gb2312");
    	//拿到session对象
    	HttpSession session = request.getSession(true);
    	//拿到输出流对象
    	PrintWriter out = response.getWriter();
    	//拿到请求的动作
    	String action = request.getParameter("action").trim();
    	if(action.equals("login"))
    	{//管理员登陆时
    		//得到登陆的用户名和密码
    		String apwd = request.getParameter("apwd").trim();
    		String aname = request.getParameter("aname").trim();
    		String sql = "select Aid,Alevel from AdminInfo where Aname='"+
    								aname+"' and Apwd='"+apwd+"'";
    		sql = new String(sql.getBytes(),"ISO-8859-1");
    		//判断该用户是否正确
    		boolean flag = DBcart.isLegal(sql);
    		String level=DBcart.getLevel(sql);
    		if(flag)
    		{//管理员登陆成功
    			session.setAttribute("admin",aname);
    			session.setAttribute("level", level);
    			response.sendRedirect("adminindex.jsp");
    		}
    		else
    		{//登陆失败
    			String msg = "对不起,登陆失败,请重新登陆!!!";
    			String url = "/error.jsp";
				pageForward(msg,url,request,response);
    		}
    	}
    	else if(action.equals("logout"))
    	{//管理员注销时
    		//使session失效
    		request.getSession(true).invalidate();
    		response.sendRedirect("index.jsp");
    	}
    	else if(action.equals("pageChange"))
    	{//商品换页时
    		AdminBean adBean = (AdminBean)session.getAttribute("adBean");
    		if(adBean==null)
    		{
    			adBean = new AdminBean();
    		}
	    	//得到请求的页面
    		String curPage = request.getParameter("curPage");
    		if(curPage!=null)
    		{//用户通过点击上,下一页按钮时
    			int page = Integer.parseInt(curPage.trim());
    			//记住当前页
    			adBean.setCurPage(page);
    		}
    		else
    		{//当用户点击下拉列表框时
    			String selPage = request.getParameter("selPage").trim();
    			int page = Integer.parseInt(selPage);
    			adBean.setCurPage(page);
    		}
    		String sql = adBean.getSql();
    		//得到换页后页面的内容
    		Vector<String[]> vgoods = DBcart.getPageContent(adBean.getCurPage(),sql);
			request.setAttribute("vgoods",vgoods);
    		session.setAttribute("adBean",adBean);
    		//forward到修改的主页面
			String url = "/adminindex.jsp";
    		ServletContext sc = getServletContext(); 
			RequestDispatcher rd = sc.getRequestDispatcher(url); 
			rd.forward(request,response);	
    	}
    	else if(action.equals("search"))
    	{//搜索商品时
    		//得到javaBean对象
    		AdminBean adBean = (AdminBean)session.getAttribute("adBean");
    		if(adBean==null)
    		{
    			adBean = new AdminBean();
    		}
    		adBean.setCurPage(1);
    		//得到要搜索的信息并转码
    		String tsearch = request.getParameter("tsearch");
    		String cname = request.getParameter("cname");
    		String sql = "";
    		String sqlpage = "";
    		if(cname==null)
    		{//按输入的文字搜索时
	    		tsearch = new String(tsearch.trim().getBytes(),"ISO-8859-1");
	    		//得到搜索信息的sql和信息条数的sql
	    		sql = "select Gimgurl,Gname,Gintro,Gclass,Gprice,Glook,Gid,"+
	    						"Gdate from GoodsInfo where Gname like '%"+tsearch+"%'";
	    		sqlpage = "select count(*) from GoodsInfo "+
	    									"where Gname like '%"+tsearch+"%'";    			
    		}
    		else
    		{//按类别搜索时
//    			cname = new String(cname.trim().getBytes(),"ISO-8859-1");
	    		//得到搜索类别信息的sql和信息条数的sql
	    		sql = "select Gimgurl,Gname,Gintro,Gclass,Gprice,Glook,Gid,"+
	    						"Gdate from GoodsInfo where Gclass='"+cname.trim()+"'";
	    		sqlpage = "select count(*) from GoodsInfo "+
	    									"where Gclass='"+cname.trim()+"'";    			
    		}
    		adBean.setSql(sql);
    		//设置总页数
    		int totalpage = DBcart.getTotalPage(sqlpage);
    		adBean.setTotalPage(totalpage);
    		session.setAttribute("adBean",adBean);
    		//得到第一页的内容
    		Vector<String[]> vgoods = DBcart.getPageContent(1,sql);
    		if(vgoods.size()==0)
    		{//没有搜索到用户要找的商品
    			String msg = "对不起,没有搜到你要的商品!!!";
    			String url = "/error.jsp";
    			pageForward(msg,url,request,response);
    		}
    		else
    		{//搜索到信息并返回
    			request.setAttribute("vgoods",vgoods);
    			String url = "/adminindex.jsp";
	    		ServletContext sc = getServletContext(); 
				RequestDispatcher rd = sc.getRequestDispatcher(url); 
				rd.forward(request,response);
    		}
    	}
    	else if(action.equals("goodsManage"))
    	{//管理商品时
    		//得到要修改或删除商品的ID
    		String gid = request.getParameter("gid").trim();
    		String sql = "select Gid,Gname,Gprice,Gamount,Gclass,Gdate,Gimgurl,"+
    						"Gintro,Gbrief from GoodsInfo where Gid="+Integer.parseInt(gid);
    		//得到该商品的信息
    		Vector<String[]> vgoods = DBcart.getInfoArr(sql);	
			request.setAttribute("vgoods",vgoods);
			ServletContext sc = getServletContext(); 
			RequestDispatcher rd = sc.getRequestDispatcher("/modifygoods.jsp"); 
			rd.forward(request,response);
    	}
    	else if(action.equals("addgoods"))
    	{//增加商品
			//接受新添加商品的各个属性
    		String gname = request.getParameter("gname").trim();
    		String gprice = request.getParameter("gprice").trim();
    		String gamount = request.getParameter("gamount").trim();
    		String gclass = request.getParameter("gclass").trim();
    		String gurl = request.getParameter("gurl").trim();
    		String gintro = request.getParameter("gintro").trim();
    		String gbrief = request.getParameter("gbrief").trim();
    		int gid = DBcart.getID("GoodsInfo","Gid");
    		String sql = "";
    		if(!gclass.equals("")&&!gurl.equals(""))
    		{//均输入商品类别和图片URL
	    		//组合成sql语句
	    		sql = "insert into GoodsInfo(Gid,Gname,Gprice,"+
	    						"Gamount,Gdate,Gclass,Gimgurl,Gintro,Gbrief)"+
	    						" values("+gid+",'"+gname+"',"+Double.parseDouble(gprice)+
	    						","+Integer.parseInt(gamount)+",now(),'"+gclass+
	    						"','"+gurl+"','"+gintro+"','"+gbrief+"')";    			
    		}
    		else if(!gclass.equals("")&&gurl.equals(""))
    		{//输入商品类别,但图片URL没有输入
	    		sql = "insert into GoodsInfo(Gid,Gname,Gprice,"+
	    						"Gamount,Gdate,Gclass,Gintro,Gbrief)"+
	    						" values("+gid+",'"+gname+"',"+Double.parseDouble(gprice)+
	    						","+Integer.parseInt(gamount)+",now(),'"+gclass+
	    						"','"+gintro+"','"+gbrief+"')";   			
    		}
    		else if(gclass.equals("")&&!gurl.equals(""))
    		{//输入图片URL,但没有商品类别
	    		sql = "insert into GoodsInfo(Gid,Gname,Gprice,"+
	    						"Gamount,Gdate,Gimgurl,Gintro,Gbrief)"+
	    						" values("+gid+",'"+gname+"',"+Double.parseDouble(gprice)+
	    						","+Integer.parseInt(gamount)+",now(),'"+
	    						gurl+"','"+gintro+"','"+gbrief+"')"; 
    		}
    		else
    		{//商品类别和图片URL均没有输入
	    		sql = "insert into GoodsInfo(Gid,Gname,Gprice,"+
	    						"Gamount,Gdate,Gintro,Gbrief)"+
	    						" values("+gid+",'"+gname+"',"+Double.parseDouble(gprice)+
	    						","+Integer.parseInt(gamount)+",now(),'"+gintro+"','"+gbrief+"')"; 
    		}
    		//将gb2312转码为iso-8859-1并更新数据库
    		sql = new String(sql.getBytes(),"ISO-8859-1");
    		int i = DBcart.updateTable(sql);
    		String msg = "";
    		if(i==1)
    		{
    			msg = "恭喜您,商品添加成功!!!";
    		}
    		else
    		{
    			msg = "对不起,商品添加失败!!!";
    		}
    		pageForward(msg,"/error.jsp",request,response);
    	}
    	else if(action.equals("modify"))
    	{//修改商品信息
    		//接受修改后商品的各个属性值
    		String gid = request.getParameter("gid").trim();
    		String gname = request.getParameter("gname").trim();
    		String gprice = request.getParameter("gprice").trim();
    		String gamount = request.getParameter("gamount").trim();
    		String gclass = request.getParameter("gclass").trim();
    		String gdate = request.getParameter("gdate").trim();
    		String gurl = request.getParameter("gurl").trim();
    		String gintro = request.getParameter("gintro").trim();
    		String gbrief = request.getParameter("gbrief").trim();
    		//将字符串转换为数值型
    		int id = Integer.parseInt(gid);
    		double price = Double.parseDouble(gprice);
    		int amount = Integer.parseInt(gamount);
    		//得到要更新的Sql语句
    		String sql = "update GoodsInfo set gname=\""+gname+"\","+
    						"gprice="+price+",gamount="+amount+",gclass='"+
    						gclass+"',gdate='"+gdate+"',gimgurl='"+gurl+"',"+
    						"gintro='"+gintro+"',gbrief='"+gbrief+"' where gid="+id;
    		//将sql转码,并执行更新
    		sql = new String(sql.getBytes(),"ISO-8859-1");
    		int i = DBcart.updateTable(sql);
    		String msg = "";
    		if(i==1)
    		{
    			msg = "恭喜您,商品修改成功!!!";
    		}
    		else
    		{
    			msg = "对不起,商品修改失败!!!";
    		}
    		pageForward(msg,"/error.jsp",request,response);
    	}
    	else if(action.equals("delete"))
    	{//删除商品
    		//得到要删除商品的ID
    		String gid = request.getParameter("gid");
    		//将ID转换为数值型
    		int id = Integer.parseInt(gid);
    		//当删除商品时,只将该商品数量置为0
    		String sql = "update GoodsInfo set gamount=0 where Gid="+id;
    		int i = DBcart.updateTable(sql);
    		String msg = "";
    		if(i==1)
    		{
    			msg = "恭喜您,商品修改成功!!!";
    		}
    		else
    		{
    			msg = "对不起,商品修改失败!!!";
    		}
    		pageForward(msg,"/error.jsp",request,response);
    	}
    	else if(action.equals("orderPageChange"))
    	{//订单换页时
    		//得到javaBean对象
    		AdminBean adBean = (AdminBean)session.getAttribute("adBean");
    		if(adBean==null)
    		{
    			adBean = new AdminBean();
    		}
    		//接受参数
    		String curPage = request.getParameter("curPage");
    		String selPage = request.getParameter("selPage");
    		//设置当前页记录条数为10
    		DBcart.setSpan(10);
    		if(curPage!=null)
    		{//用户通过点击上,下一页按钮时
    			int page = Integer.parseInt(curPage.trim());
    			//记住当前页
    			adBean.setCurPage(page);
    		}
    		else
    		{//当用户点击下拉列表框时
    			int page = Integer.parseInt(selPage.trim());
    			adBean.setCurPage(page);
    		}
    		//得到当前换页所执行的sql
    		String sql = adBean.getSql();
    		//得到换页后页面的内容
    		Vector<String[]> vorder = DBcart.getPageContent(adBean.getCurPage(),sql);
			request.setAttribute("vorder",vorder);
    		session.setAttribute("adBean",adBean);
    		//恢复记录跨度为2
    		DBcart.setSpan(2);
    		//forward到修改的主页面
			String url = "/ordermanage.jsp";
    		ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(url);
			rd.forward(request,response);
    	}
    	else if(action.equals("orderSearch"))
    	{//订单搜索
    		//得到javaBean对象
    		AdminBean adBean = (AdminBean)session.getAttribute("adBean");
    		if(adBean==null)
    		{
    			adBean = new AdminBean();
    		}
    		String txtsearch = request.getParameter("txtsearch");
    		String type = request.getParameter("type");
    		String sql = "";
    		//将每页记录数定为10
    		DBcart.setSpan(10);
    		adBean.setCurPage(1);
    		if(txtsearch!=null)
    		{//用户在文本框中输入内容搜索
	    		int oid = Integer.parseInt(txtsearch.trim());
	  			sql = "select Oid,Uname,Odate,Ostate from OrderInfo,UserInfo"+
	  						" where Oid="+oid+" and OrderInfo.Uid=UserInfo.Uid";
	    		adBean.setSql(sql);
	    		//设置总页数
	    		adBean.setTotalPage(1);    			
    		}
    		else
    		{
    			String sqlpage = "";
    			if(type.trim().equals("all"))
    			{//查询所有定单
  					sql = "select Oid,Uname,Odate,Ostate from OrderInfo,UserInfo"+
  						" where OrderInfo.Uid=UserInfo.Uid";
  					sqlpage = "select count(*) from OrderInfo";
    			}
    			else if(type.trim().equals("yes"))
    			{//查询所有已发货定单
			  		sql = "select Oid,Uname,Odate,Ostate from OrderInfo,UserInfo"+
			  				" where Ostate='已发货' and OrderInfo.Uid=UserInfo.Uid order by Oid";
			  		sqlpage = "select count(*) from OrderInfo where Ostate='已发货'";
    			}
    			else if(type.trim().equals("no"))
    			{//查询所有未发货定单
			  		sql = "select Oid,Uname,Odate,Ostate from OrderInfo,UserInfo"+
			  				" where Ostate='未发货' and OrderInfo.Uid=UserInfo.Uid order by Oid";
			  		sqlpage = "select count(*) from OrderInfo where Ostate='未发货'";
    			}
    			//转码
		  		sqlpage = new String(sqlpage.getBytes(),"ISO-8859-1");
		  		sql = new String(sql.getBytes(),"ISO-8859-1");
		  		int totalpage = DBcart.getTotalPage(sqlpage);
		  		adBean.setSql(sql);
		  		//记住当前总页数
		  		adBean.setTotalPage(totalpage);
    		}
    		session.setAttribute("adBean",adBean);
    		//得到第一页的内容
    		Vector<String[]> vorder = DBcart.getPageContent(1,sql);
		  	DBcart.setSpan(2);
    		if(vorder.size()==0)
    		{//没有搜索到用户要找的商品
    			String msg = "对不起,没有搜到你要查询的定单!!!";
    			String url = "/error.jsp";
    			pageForward(msg,url,request,response);
    		}
    		else
    		{//搜索到信息并返回
    			request.setAttribute("vorder",vorder);
    			String url = "/ordermanage.jsp";
	    		ServletContext sc = getServletContext(); 
				RequestDispatcher rd = sc.getRequestDispatcher(url); 
				rd.forward(request,response);
    		}
    	}
    	else if(action.equals("ordermanage"))
    	{//点击查看/管理时
    		String oid = request.getParameter("oid").trim();
    		int id = Integer.parseInt(oid);
    		//得到订单的基本信息
    		String osql = "select Orecname,Orecadr,Orectel,Odate,Ostate,Oid from OrderInfo"+
    							" where Oid="+id;
    		Vector<String[]> vorderinfo = DBcart.getInfoArr(osql);
    		//得到订单中货物信息
    		String ogsql = "select Gname,OGamount,OGtotalprice from GoodsInfo,"+
    							"OrderGoods where GoodsInfo.Gid=OrderGoods.Gid"+
    							" and Oid="+id;
    		Vector<String[]> vordergoods = DBcart.getInfoArr(ogsql);
			request.setAttribute("vorderinfo",vorderinfo);
			request.setAttribute("vordergoods",vordergoods);
			String url = "/ordermodify.jsp";
			ServletContext sc = getServletContext(); 
			RequestDispatcher rd = sc.getRequestDispatcher(url); 
			rd.forward(request,response);
    	}
    	else if(action.equals("orderEnsure"))
    	{//某订单确认时
    		String oid = request.getParameter("oid");
    		int id = Integer.parseInt(oid);
    		String aname = (String)session.getAttribute("admin");
    		int aid = DBcart.getSelectId("select Aid from AdminInfo where Aname='"+aname+"'");		
    		String sql = "update OrderInfo set Aid="+aid+",Ostate='已发货' where Oid="+id;
    		String temp = "select Gid,OGamount from OrderGoods where Oid="+id;
    		Vector<String[]> vtemp = DBcart.getInfoArr(temp);
    		String[] str = new String[vtemp.size()+1];
    		for(int i=0;i<vtemp.size();i++)
    		{
    			String[] arr = vtemp.get(i);
    			str[i] = "update GoodsInfo set Gamount=Gamount-"+
    							Integer.parseInt(arr[1])+" where Gid="+arr[0];
    		}
    		str[vtemp.size()] = sql;
    		boolean flag = DBcart.batchSql(str);
    		String msg = "";
    		if(flag)
    		{
    			msg = "恭喜您,订单确定成功!!!";
    		}
    		else
    		{
    			msg = "对不起,订单确定失败!!!";
    		}
    		pageForward(msg,"/error.jsp",request,response);
    	}
    	else if(action.equals("orderDelete"))
    	{//删除某订单时
    		String oid = request.getParameter("oid");
    		int id = Integer.parseInt(oid);
    		String[] str = new String[2];
    		str[0] = "delete from OrderGoods where Oid="+id;
    		str[1] = "delete from OrderInfo where Oid="+id;
    		boolean flag = DBcart.batchSql(str);
    		String msg = "";
    		if(flag)
    		{
    			msg = "恭喜您,订单删除成功!!!";
    		}
    		else
    		{
    			msg = "对不起,订单删除失败!!!";
    		}
    		pageForward(msg,"/error.jsp",request,response);
    	}
    	else if(action.equals("changePwd"))
    	{//修改密码时
    		String aname = (String)session.getAttribute("admin");
    		String oldpwd = request.getParameter("oldpwd").trim();
    		String newpwd = request.getParameter("firpwd").trim();
    		String sql = "select Aid from AdminInfo where Aname='"+aname+
    							"' and Apwd='"+oldpwd+"'";
    		sql = new String(sql.getBytes(),"ISO-8859-1");
    		boolean flag = DBcart.isLegal(sql);
    		String msg = "";
    		if(flag)
    		{
    			String temp = "update AdminInfo set Apwd='"+
    								newpwd+"' where Aname='"+aname+"'";
    			temp = new String(temp.getBytes(),"ISO-8859-1");
    			int i = DBcart.updateTable(temp);
    			if(i==1)
    			{
    				msg = "恭喜您,密码修改成功!!!";
    			}
    			else
    			{
    				msg = "对不起,密码修改失败!!!";
    			}
    			pageForward(msg,"/error.jsp",request,response);
    		}
    		else
    		{
    			msg = "对不起,旧密码输入有误!!!";
    			pageForward(msg,"/error.jsp",request,response);
    		}
    	}
    	else if(action.equals("adminManage"))
    	{//管理员管理时
    		String aname = (String)session.getAttribute("admin");
    		String sql = "select Aid from AdminInfo where Aname='"+aname
    							+"' and Alevel='超级'";
    		sql = new String(sql.getBytes(),"ISO-8859-1");
    		boolean flag = DBcart.isLegal(sql);
      		if(flag)
    		{
    			session.setAttribute("level","超级");
    			response.sendRedirect("adminmanage.jsp");
    		}
    		else
    		{
    			String msg = "对不起,您没有权限来进行管理!!!";
    			pageForward(msg,"/error.jsp",request,response);
    		}
    	}
    	else if(action.equals("adminAdd"))
    	{//添加管理员
    		String aname = request.getParameter("aname").trim();
    		String apwd = request.getParameter("apwd").trim();
    		int aid = DBcart.getID("AdminInfo","Aid");
    		String temp = "select Aid from AdminInfo where aname='"+aname+"'";
    		temp = new String(temp.getBytes(),"ISO-8859-1");
    		boolean flag = DBcart.isLegal(temp);
    		String msg = "";
    		if(flag)
    		{
    			msg = "对不起该用户已经存在!!!";
    		}
    		else
    		{
	    		String sql = "insert into AdminInfo(Aid,Aname,Apwd,Alevel)"+
	    							" values("+aid+",'"+aname+"','"+apwd+"','普通')";
	    		sql = new String(sql.getBytes(),"ISO-8859-1");
	    		int i = DBcart.updateTable(sql);
	    		if(i==1)
	    		{
	    			msg = "恭喜您,管理员添加成功!!!";
	    		}
	    		else
	    		{
	    			msg = "对不起,管理员添加失败!!!";
	    		}    			
    		}
    		pageForward(msg,"/error.jsp",request,response);
    	}
    	else if(action.equals("adminDelete"))
    	{//删除管理员
    		String aid = request.getParameter("aid").trim();
    		int id = Integer.parseInt(aid);
    		String temp = "select Aid from AdminInfo where Aid="+
    										id+" and Alevel='超级'";
    		temp = new String(temp.getBytes(),"ISO-8859-1");
    		boolean flag = DBcart.isLegal(temp);
    		String msg = "";
    		if(!flag)
    		{
	    		String sql = "delete from AdminInfo where Aid="+id;
	    		int i = DBcart.updateTable(sql);
	    		if(i==1)
	    		{
	    			msg = "恭喜您,管理员删除成功!!!";
	    		}
	    		else
	    		{
	    			msg = "对不起,管理员删除失败!!!";
	    		}    			
    		}
    		else
    		{
    			msg = "对不起,超级管理员不可以删除!!!";
    		}

    		pageForward(msg,"/error.jsp",request,response);
    	}
    	else if(action.equals("resetpwd"))
    	{//重置密码时
    		String aname = request.getParameter("aname").trim();
    		String apwd = request.getParameter("apwd").trim();
    		String temp = "select Aid from AdminInfo where aname='"+aname+"'";
    		temp = new String(temp.getBytes(),"ISO-8859-1");
    		boolean flag = DBcart.isLegal(temp);
    		String msg = "";
    		if(!flag)
    		{
    			msg = "对不起,用户名输入错误!!!";
    		}
    		else
    		{
    			String sql = "update AdminInfo set Apwd='"+apwd+"' where aname='"+aname+"'";
    			sql = new String(sql.getBytes(),"ISO-8859-1");
    			int i = DBcart.updateTable(sql);
    			if(i==1)
    			{
    				msg = "恭喜您,密码重置成功!!!";
    			}
    			else
    			{
    				msg = "对不起,密码重置失败!!!";
    			}
    		}
    		pageForward(msg,"/error.jsp",request,response);
    	}
    }
    public void pageForward(String msg,String url,HttpServletRequest request,
    					HttpServletResponse response)throws ServletException,IOException
    {
		request.setAttribute("msg",msg);
		ServletContext sc = getServletContext(); 
		RequestDispatcher rd = sc.getRequestDispatcher(url); 
		rd.forward(request,response);   	
    }
}
