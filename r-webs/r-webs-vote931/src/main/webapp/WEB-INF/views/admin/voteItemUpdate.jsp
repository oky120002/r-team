<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link  href="<c:url value="/res/css/layout.css" />" rel="stylesheet" type="text/css"/>
<script src="<c:url value="/res/jquery/jquery-1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/res/js/admin/voteItemAdd.js" />" type="text/javascript"></script>
<title>编辑问卷项</title>
</head>
<body>
	<div>
		<div class="dmenu" title="编辑问卷项"> 
			<p class="arrow">编辑问卷项</p>
		</div>
		<form action="">
			<table class="list_style_1">
				<tbody>
					<tr>
						<td>请选择类型 : </td>
						<td align="left">
						<c:forEach items="${types }" var="type">
							<input id="type_${type.name }" type="radio" value="${type.name }" name="type" />
							<label for="type_${type.name }">${type.cnName }</label>
						</c:forEach>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>