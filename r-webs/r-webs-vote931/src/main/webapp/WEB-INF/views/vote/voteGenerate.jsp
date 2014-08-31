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
		<table class="list_style_1">
			<tbody>
				<tr>
					<td style="width: 95px;">请选生成方式 : </td>
					<td>
						<input id="type_default" type="radio" value="default" name="type" class="getype"/><label for="type_default">默认方式</label>
					</td>
				</tr>
				<tr>
					<td>问卷标题 : </td>
					<td><input type="text" name="votetitle" class="text_line add_voteitem_question"/></td>
				</tr>
				
				<!-- 默认模块 -->
				<tr id="tr_default" class="getr">
					<td colspan="2">
						<table class="list_style_1">
							<tbody>
								<tr>
									<td style="width: 89px;">问卷项数量 : </td>
									<td><input type="text" name="votetitle" class="text_line"/></td>
								</tr>
								<tr>
									<td>答题时间 : </td>
									<td><input type="text" name="votetime" class="text_line"/><label>秒(0为不限时)</label></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>