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
	<script type="text/javascript" src="icomet/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="icomet/icomet.js"></script>
	<script type="text/javascript" src="icomet/json2"></script>
	<script type="text/javascript" src="icomet/jquery.qrcode.min.js"></script>
	<script>
		var conf = {
			channel : "zhouet",
			signUrl : 'http://121.40.133.24:8000/sign',
			subUrl : 'http://121.40.133.24:8100/sub',
			callback : function(content) {
				alert(content);
			}
		};
		
		var comet = new iComet(conf);
	
	</script>

  </head>
  
  <body>
    This is my JSP page. <br><br>
    <div id="test">
    	<form action="" method="post">
    	</form>
    </div>
  </body>
</html>
