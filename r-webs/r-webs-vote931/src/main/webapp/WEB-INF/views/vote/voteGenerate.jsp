<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link  href="<c:url value="/res/css/layout.css" />" rel="stylesheet" type="text/css"/>
<script src="<c:url value="/res/jquery/jquery-1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/res/js/common.js" />" type="text/javascript"></script>
<script src="<c:url value="/res/js/vote/voteGenerate.js" />" type="text/javascript"></script>
<title>生成问卷表</title>
</head>
<body>
	<div>
		<div class="dmenu" title="生成问卷">
			<p class="arrow">生成问卷</p>
		</div>
		<form id="vote_option_form" action="<c:url value="/vote/func/createVote" />">
			<table class="list_style_1">
				<tbody>
					<tr>
						<td>答题人签名 : </td>
						<td><input type="text" name="signature" class="text_line add_voteitem_question"/></td>
					</tr>
					<tr>
						<td>问卷标题 : </td>
						<td><input type="text" name="title" class="text_line add_voteitem_question"/></td>
					</tr>
					<tr>
						<td style="width: 89px;">问卷项数量 : </td>
						<td><input type="text" name="visize" value="25" class="text_line"/><label>题</label></td>
					</tr>
					<tr>
						<td colspan="2"><input id="btn_start" type="button" value="开始" class="button_03" /></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>