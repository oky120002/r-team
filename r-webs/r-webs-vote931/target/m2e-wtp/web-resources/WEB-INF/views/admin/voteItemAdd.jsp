<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link  href="<c:url value="/res/css/layout.css" />" rel="stylesheet" type="text/css"/>
<script src="<c:url value="/res/jquery/jquery-1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/res/js/admin/voteItemAdd.js" />" type="text/javascript"></script>
<title>新增问卷项</title>
</head>
<body>
	<div>
		<div class="dmenu" title="新增问卷项"> 
			<p class="arrow">新增问卷项</p>
		</div>
		<form action="">
			<table class="list_style_1">
				<tbody>
					<tr>
						<td style="width: 95px;">请选择类型 : </td>
						<td>
							<c:forEach items="${types }" var="type">
								<input id="type_${type.name }" type="radio" value="${type.name }" name="type" class="vitype"/>
								<label for="type_${type.name }">${type.cnName }</label>
							</c:forEach>
						</td>
					</tr>
					<tr id="tr_yesno" class="vitr">
						<td colspan="2">
							<table class="list_style_1">
								<tbody>
									<tr><td style="width: 89px;">问题 : </td><td colspan="3"><input type="text" name="question" class="text_line"/> </td></tr>
									<tr>
										<td>选项描述 : </td>
										<td colspan="3">
											<label for="yesno_yes_text">yes : </label><input id="yesno_yes_text" type="text" name="answerYes" class="text_line"/>
											<label for="yesno_no_text">no : </label><input id="yesno_no_text" type="text" name="answerNo" class="text_line"/>
										</td>
									</tr>
									<tr>
										<td>正确答案 : </td>
										<td colspan="3">
											<input type="radio" id="yesno_yes_radio" name="answer" value="true" /> <label id="yesno_yes_label" for="yesno_yes_radio"></label>
											<span style="display:inline-block; width:50px"></span>
											<input type="radio" id="yesno_no_radio" name="answer" value="false" /><label id="yesno_no_label" for="yesno_no_radio"></label> 
										</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					<tr id="tr_single" class="vitr">
						<td colspan="2">tr_single</td>
					</tr>
					<tr id="tr_multiple" class="vitr">
						<td colspan="2">tr_multiple</td>
					</tr>
					<tr id="tr_completion" class="vitr">
						<td colspan="2">tr_completion</td>
					</tr>
					<tr>
						<td>备注 : </td>
						<td><input type="text" name="remark" class="text_line"/></td>
					</tr>
					<tr>
						<td>状态 : </td>
						<td>
							<input id="stutas_yes" type="radio" name="isEnable" value="true"/><label for="stutas_yes">启用</label>
							<span style="display:inline-block; width:10px"></span>
							<input id="stutas_no" type="radio" name="isEnable" value="false"/><label for="stutas_no">禁用</label>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>