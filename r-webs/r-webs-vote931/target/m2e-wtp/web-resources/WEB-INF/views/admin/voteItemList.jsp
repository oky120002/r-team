<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<div>
		<table>
			<thead></thead>
			<tbody>
				<c:forEach items="${voteItems }" var="voteitem">
					<tr>
						<td>${voteitem.no }</td>
						<td>${voteitem.type.name }</td>
						<td>${voteitem.question }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>