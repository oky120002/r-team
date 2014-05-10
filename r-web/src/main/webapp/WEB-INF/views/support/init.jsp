<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>初始化系统</title>
</head>
<body>
	<div id="page">
	<form action="<c:url value='/init/complete' />" method="post">
		后台管理员账号:<input type="text" name="username"  /><p>
		后台管理员密码:<input type="password" name="password"  /><p>
		后台管理员密码:<input type="submit" value="提交"  /><p>
	</form>
	</div>
</body>
</html>