<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
</head>
<body>
	<!-- jsp_body -->
	<div id="frame_main">
		<!-- jsp_body_contents -->
		<div id="frame_contents">
			<div>
				<form id="loginform" action='<c:url value="/j_spring_security_check"/>' method="post">
					<p><input id="j_username" name="j_username" type="text" /></p>
					<p><input id="j_password" name="j_password" type="password" /></p>
					<p><input id="submitBtn" name="submitBtn" type="submit" value="登陆" /> </p>
				</form>
				<b> <font color="red" size="3"> ${SPRING_SECURITY_LAST_EXCEPTION.message} </font> </b>
			</div>
		</div>
	</div>
</body>
</html>
