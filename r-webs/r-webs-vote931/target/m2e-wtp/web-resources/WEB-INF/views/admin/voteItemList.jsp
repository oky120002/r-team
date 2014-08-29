<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link  href="<c:url value="/res/css/layout.css" />" rel="stylesheet" type="text/css"/>
<script src="<c:url value="/res/jquery/jquery-1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/res/js/admin/voteItemList.js" />" type="text/javascript"></script>
<title>问卷项管理</title>
</head>
<body>
	<div>
		<div class="dmenu" title="问卷项列表">
			<ul class="icon">
				<li title="新增 "><a onclick="ntiAdd();" href="<c:url value="/admin/page/add" />"><i class="ntt_li_ntiAdd"></i>新增 </a></li>
			</ul>
			<p class="arrow">问卷项列表</p>
		</div>
		<table class="list_style_1">
			<thead>
				<tr>
					<th>编号</th>
					<th>类型</th>
					<th>问题</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${voteItems }" var="voteitem">
				<tr>
					<td>${voteitem.no }</td>
					<td>${voteitem.type.cnName }</td>
					<td>${voteitem.question }</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>