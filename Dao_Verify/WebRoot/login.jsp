<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<html>  
  <head>  
    <title>验证码</title>  
    <style type="text/css">
     #verify{
    	width:80px;
    	height:20px;
    	margin:auto;
    }
    </style>
    <script type="text/javascript">
   		function changeImg(){
   			document.getElementById("vimg").src="./AuthImage?"+Math.random();
   		}
    </script>  
  </head>  
  <body>  
    <form action="./CheckServlet" method="get">  
        <input id="validateCode" type="text" name="validateCode"/>
        <img id="vimg" src="AuthImage">
        <a href="javascript:void(0)" onclick="changeImg()">看不清，换一张</a></br>
        <input type="submit" value="submit">  
    </form>  
  </body>  
</html> 