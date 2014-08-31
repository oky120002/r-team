<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link  href="<c:url value="/res/css/layout.css" />" rel="stylesheet" type="text/css"/>
<script src="<c:url value="/res/jquery/jquery-1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/res/js/common.js" />" type="text/javascript"></script>
<script src="<c:url value="/res/js/admin/voteItemAdd.js" />" type="text/javascript"></script>
<title>新增问卷项</title>
</head>
<body>
	<div>
		<div class="dmenu" title="新增问卷项"> 
			<ul class="icon">
				<li title="返回"><a href="<c:url value="/admin/index" />"><i class="ntt_li_ntiBack"></i>返回</a></li>
			</ul>
			<p class="arrow">新增问卷项</p>
		</div>
		<form id="voteitem_add_form" action="<c:url value="/admin/func/save" />">
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
					
					<!-- 是非模块 -->
					<tr id="tr_yesno" class="start_hide vitr">
						<td colspan="2">
							<table class="list_style_1">
								<tbody>
									<tr><td style="width: 89px;">问题 : </td><td colspan="3"><input type="text" name="question" class="text_line add_voteitem_question"/> </td></tr>
									<tr>
										<td>选项描述 : </td>
										<td colspan="3">
											<label for="yesno_yes_text">选项yes : </label><input id="yesno_yes_text" type="text" name="answerYes" class="text_line"/>
											<label for="yesno_no_text">选项no : </label><input id="yesno_no_text" type="text" name="answerNo" class="text_line"/>
										</td>
									</tr>
									<tr>
										<td>正确答案 : </td>
										<td colspan="3">
											<input type="radio" id="yesno_yes_radio" name="answer" value="true" /> <label id="yesno_yes_label" for="yesno_yes_radio"></label>
											<span class="placeholder"></span>
											<input type="radio" id="yesno_no_radio" name="answer" value="false" /><label id="yesno_no_label" for="yesno_no_radio"></label> 
										</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					
					<!-- 单选模块 -->
					<tr id="tr_single" class="start_hide vitr">
						<td colspan="2">
							<table class="list_style_1">
								<tbody>
									<tr><td style="width: 89px;">问题 : </td><td colspan="3"><input type="text" name="question" class="text_line add_voteitem_question"/> </td></tr>
									<tr>
										<td>选项描述 : </td>
										<td colspan="3">
											<label for="single_1_text">选项A : </label><input id="single_1_text" type="text" name="answer1" class="text_line"/>
											<span class="placeholder"></span>
											<label for="single_2_text">选项B : </label><input id="single_2_text" type="text" name="answer2" class="text_line"/>
											<span class="placeholder"></span>
											<label for="single_3_text">选项C : </label><input id="single_3_text" type="text" name="answer3" class="text_line"/>
											<span class="placeholder"></span>
											<label for="single_4_text">选项D : </label><input id="single_4_text" type="text" name="answer4" class="text_line"/>
										</td>
									</tr>
									<tr>
										<td>正确答案 : </td>
										<td colspan="3">
											<input type="radio" id="single_1_radio" name="answer" value="1" /> <label id="single_1_label" for="single_1_radio">选项A : </label>
											<span class="placeholder"></span>
											<input type="radio" id="single_2_radio" name="answer" value="2" /><label id="single_2_label" for="single_2_radio">选项B : </label>
											<span class="placeholder"></span>
											<input type="radio" id="single_3_radio" name="answer" value="3" /> <label id="single_3_label" for="single_3_radio">选项C : </label>
											<span class="placeholder"></span>
											<input type="radio" id="single_4_radio" name="answer" value="4" /> <label id="single_4_label" for="single_4_radio">选项D : </label> 
										</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					
					<!-- 多选模块 -->
					<tr id="tr_multiple" class="start_hide vitr">
						<td colspan="2">
							<table class="list_style_1">
								<tbody>
									<tr><td style="width: 89px;">问题 : </td><td colspan="3"><input type="text" name="question" class="text_line add_voteitem_question"/> </td></tr>
									<tr>
										<td>选项描述 : </td>
										<td colspan="3">
											<label for="multiple_1_text">选项A : </label><input id="multiple_1_text" type="text" name="answer1" class="text_line"/>
											<span class="placeholder"></span>
											<label for="multiple_2_text">选项B : </label><input id="multiple_2_text" type="text" name="answer2" class="text_line"/>
											<span class="placeholder"></span>
											<label for="multiple_3_text">选项C : </label><input id="multiple_3_text" type="text" name="answer3" class="text_line"/>
											<span class="placeholder"></span>
											<label for="multiple_4_text">选项D : </label><input id="multiple_4_text" type="text" name="answer4" class="text_line"/>
										</td>
									</tr>
									<tr>
										<td>正确答案 : </td>
										<td colspan="3">
											<span id="multiple_span">
												<input type="checkbox" id="multiple_1_checkbox" name="answer" value="1" /> <label id="multiple_1_label" for="multiple_1_checkbox">选项A : </label>
												<span class="placeholder"></span>
												<input type="checkbox" id="multiple_2_checkbox" name="answer" value="2" /> <label id="multiple_2_label" for="multiple_2_checkbox">选项B : </label>
												<span class="placeholder"></span>
												<input type="checkbox" id="multiple_3_checkbox" name="answer" value="3" /> <label id="multiple_3_label" for="multiple_3_checkbox">选项C : </label>
												<span class="placeholder"></span>
												<input type="checkbox" id="multiple_4_checkbox" name="answer" value="4" /> <label id="multiple_4_label" for="multiple_4_checkbox">选项D : </label> 
											</span>
										</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					
					<!-- 填空题模块 -->
					<tr id="tr_completion" class="start_hide vitr">
						<td colspan="2">
							<table class="list_style_1">
								<tbody>
									<tr>
										<td>填空数量 : </td>
										<td colspan="3">
											<input id="completion_1_radio" type="radio" value="1" class="completion_radio" name="completion" checked="checked"/><label for="completion_1_radio">1个空位</label>
											<span class="placeholder"></span>
											<input id="completion_2_radio" type="radio" value="2" class="completion_radio"  name="completion"/><label for="completion_2_radio">2个空位</label>
											<span class="placeholder"></span>
											<input id="completion_3_radio" type="radio" value="3" class="completion_radio"  name="completion"/><label for="completion_3_radio">3个空位</label>
											<span class="placeholder"></span>
											<input id="completion_4_radio" type="radio" value="4" class="completion_radio"  name="completion"/><label for="completion_4_radio">4个空位</label>
										</td>
									</tr>
									<tr><td style="width: 89px;">问题 : </td>
										<td colspan="3">
											<textarea rows="3" cols="100" name="question" class="text_line add_voteitem_question"></textarea>
											<span style="color: red;">请在需要填空处使用{}来代替。</span>
										</td>
									</tr>
									<tr>
										<td>正确答案 : </td>
										<td colspan="3">
											<span id="completion_span">
												<label id="completion_1_label" for="completion_1_text">答案A : </label><input type="text" id="completion_1_text" name="answer1" class="text_line" /> 
												<span class="placeholder" ></span>
												<label id="completion_2_label" for="completion_2_text" class="start_hide">答案B : </label><input type="text" id="completion_2_text" name="answer2" class="text_line start_hide" />
												<span class="placeholder"></span>
												<label id="completion_3_label" for="completion_3_text" class="start_hide">答案C : </label><input type="text" id="completion_3_text" name="answer3" class="text_line start_hide" /> 
												<span class="placeholder"></span>
												<label id="completion_4_label" for="completion_4_text" class="start_hide">答案D : </label> <input type="text" id="completion_4_text" name="answer4" class="text_line start_hide" /> 
											</span>
										</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td>备注 : </td>
						<td><input type="text" name="remark" class="text_line add_voteitem_question"/></td>
					</tr>
					<tr>
						<td>状态 : </td>
						<td>
							<input id="stutas_yes" type="radio" name="isEnable" value="true" checked="checked"/><label for="stutas_yes">启用</label>
							<span style="display:inline-block; width:10px"></span>
							<input id="stutas_no" type="radio" name="isEnable" value="false"/><label for="stutas_no">禁用</label>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<button id="btn_add" class="button_03" >新增</button>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>