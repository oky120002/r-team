<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>css布局案例实验页面-www.divcss5.com.cn</title>
<link rel="stylesheet"  type="text/css"  href="<c:url value="/res/css/layout.css" />" />
</head>
<body>
	<div id="page">
		<div id="header">我是头部（上）</div>
		<div id="centers">
			<div class="c_left">我是中的左</div>
			<div class="c_right">
				<iframe id="content_iframe"  src="http://www.baidu.com"  width="100%"  height="100%"></iframe>
			</div>
			<div class="clear">&nbsp;</div>
		</div>
		<div id="footer">我是底部（下）</div>
	</div>
</body>
</html>