package verify;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class CheckServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String clientCheckcode = (String)request.getParameter("validateCode");//接收客户端浏览器提交上来的验证码
		String serverCheckcode = (String) request.getSession().getAttribute("rand");//从服务器端的session中取出验证码
		System.out.println("client:"+clientCheckcode);
		System.out.println("server:"+serverCheckcode);
		if (clientCheckcode.equalsIgnoreCase(serverCheckcode)) {//将客户端验证码和服务器端验证比较，如果相等，则表示验证通过
			System.out.println("验证码验证通过！");
		}else {
			System.out.println("验证码验证失败！");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
