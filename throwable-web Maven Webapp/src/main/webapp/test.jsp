<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script type="text/javascript" src="js/jquery.js"></script>
	<script>
	$(document).ready(function(){
		/*	$.post("question/getQuestion", {id: 1000}, function(result){
				alert("oookk");
				alert(result);
				$("#test").html(result.Hello + " <br>" + result.id);
				}, "json");*/
	});
	</script>

  </head>
  
  <body>
    This is my JSP page. <br><br>
    <div id="test">
    	<form action="user/register" method="post">
    		用户名: <input type="text" id="username" name="username" />
    		<br>
    		邮箱: <input type="text" id="email" name="email" />
    		<br>
    		昵称: <input type="text" id="nickname" name="nickname" />
    		<br>
    		密码: <input type="password" id="password" name="password" />
    		<br>
    		<input type="submit" value="注册" /> 
    	</form>
    </div>
  </body>
</html>
