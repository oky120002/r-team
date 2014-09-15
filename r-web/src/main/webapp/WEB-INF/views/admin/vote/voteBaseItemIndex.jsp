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
		<div class="dmenu" title="基础问卷项列表">
			<ul class="icon">
				<li title="问卷管理"><a href="<c:url value="/admin/page/vote/index" />"><i class="ntt_li_ntiStart"></i>问卷管理</a></li>
				<li title="新增"><a href="<c:url value="/admin/page/votebaseitem/add" />"><i class="ntt_li_ntiAdd"></i>新增</a></li>
			</ul>
			<p class="arrow">问卷项列表</p>
		</div>
		<table class="list_style_1">
			<thead>
				<tr>
					<th>编号</th>
					<th>类型</th>
					<th>问题</th>
					<th>选项描述</th>
					<th width="135px">创建时间</th>
					<th width="34px">操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${voteBaseItems }" var="votebaseitem" varStatus="status">
				<c:choose>
					<c:when test="${votebaseitem.isEnable == false}">
						<tr style="color: green;">
					</c:when>
					<c:otherwise>
						<tr>
					</c:otherwise>
				</c:choose>
					<td>${votebaseitem.no }</td>
					<td>${votebaseitem.type.cnName }</td>
					<td>${votebaseitem.question }</td>
					<td>
						<c:choose>
							<c:when test="${votebaseitem.type.name == 'yesno'}">
								<label class="answer1">选项Yes : ${votebaseitem.answerYes }</label>
								<span class="placeholder"></span>
								<label class="answer2">选项No : ${votebaseitem.answerNo }</label>
							</c:when>
							<c:when test="${votebaseitem.type.name == 'single'}">
								<label class="answer1">选项A : ${votebaseitem.answer1 }</label>
								<span class="placeholder"></span>
								<label class="answer2">选项B : ${votebaseitem.answer2 }</label>
								<span class="placeholder"></span>
								<label class="answer3">选项C : ${votebaseitem.answer3 }</label>
								<span class="placeholder"></span>
								<label class="answer4">选项D : ${votebaseitem.answer4 }</label>
							</c:when>
							<c:when test="${votebaseitem.type.name == 'multiple'}">
								<label class="answer1">选项A : ${votebaseitem.answer1 }</label>
								<span class="placeholder"></span>
								<label class="answer2">选项B : ${votebaseitem.answer2 }</label>
								<span class="placeholder"></span>
								<label class="answer3">选项C : ${votebaseitem.answer3 }</label>
								<span class="placeholder"></span>
								<label class="answer4">选项D : ${votebaseitem.answer4 }</label>
							</c:when>
						</c:choose>
					</td>
					<td><label>${votebaseitem.createDate }</label></td>
					<td>
						<c:choose>
							<c:when test="${votebaseitem.isEnable == true}">
								<a href="<c:url value="/admin/func/voteitem/changestatus/${voteitem.id }" />">禁用</a>
							</c:when>
							<c:otherwise>
								<a href="<c:url value="/admin/func/voteitem/changestatus/${voteitem.id }" />">启用</a>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>