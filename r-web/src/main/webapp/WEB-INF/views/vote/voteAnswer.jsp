<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link  href="<c:url value="/res/css/layout.css" />" rel="stylesheet" type="text/css"/>
<script src="<c:url value="/res/jquery/jquery-1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/res/js/common.js" />" type="text/javascript"></script>
<script src="<c:url value="/res/js/vote/voteAnswer.js" />" type="text/javascript"></script>
<title>问卷 - ${vote.title }</title>
</head>
<body>
	<div>
		<div class="dmenu" title="问卷">
			<p class="arrow">问卷</p>
		</div>
		<form id="voteresult_answer_form" action="<c:url value="/vote/func/answer" />">
			<input name="vid" type="hidden" value="${vote.id }" />
			<table class="list_style_1">
				<tbody>
					<tr><td colspan="2" align="center" valign="baseline" class="vote_title_tr">${vote.title }<c:if test="${vote.subTitle != null }"><span class="vote_subtitle_span"> - ${vote.subTitle }</span></c:if></td></tr> 
					<tr><td colspan="2" >问卷生成时间  <span class="vote_option_tr_span">${vote.createDate }</span><span class="placeholder">|</span>签名: <span class="vote_option_tr_span">${vote.signature }</span></td> </tr>
					<c:forEach items="${vote.voteItems }" var="vi" varStatus="status">
						<tr>
							<td colspan="2">
								<input type="hidden" name="viid" value="${vi.id }" />
								<input type="hidden" name="type" value="${vi.voteBaseItem.type.name }" />
								<table class="list_style_1 voteitem_question_table"> 
									<tbody>
										<tr><td align="center" valign="baseline" class="voteitem_question_tr">问题 ${status.count }  ${vi.voteBaseItem.question }</td></tr>
										<c:choose>
											<c:when test="${vi.voteBaseItem.type.name == 'yesno'}">
											<!-- 是非题 -->
												<tr>
													<td>
														<input type="radio" id="${vi.id }_answer_yes" name="${vi.id }_answer" value="1"/><label for="${vi.id }_answer_yes">  ${vi.voteBaseItem.answerYes }</label>
														<span class="placeholder"></span>
														<input type="radio" id="${vi.id }_answer_no" name="${vi.id }_answer" value="0"/><label for="${vi.id }_answer_no">  ${vi.voteBaseItem.answerNo }</label>
													</td>
												</tr>
											</c:when>
											<c:when test="${vi.voteBaseItem.type.name == 'single'}">
											<!-- 单选题 -->
												<tr>
													<td>
														<input type="radio" id="${vi.id }_answer_1" name="${vi.id }_answer" value="1"/><label for="${vi.id }_answer_1">  ${vi.voteBaseItem.answer1 }</label>
														<span class="placeholder"></span>
														<input type="radio" id="${vi.id }_answer_2" name="${vi.id }_answer" value="2"/><label for="${vi.id }_answer_2">  ${vi.voteBaseItem.answer2 }</label>
														<span class="placeholder"></span>
														<input type="radio" id="${vi.id }_answer_3" name="${vi.id }_answer" value="3"/><label for="${vi.id }_answer_3">  ${vi.voteBaseItem.answer3 }</label>
														<span class="placeholder"></span>
														<input type="radio" id="${vi.id }_answer_4" name="${vi.id }_answer" value="4"/><label for="${vi.id }_answer_4">  ${vi.voteBaseItem.answer4 }</label>
													</td>
												</tr>
											</c:when>
											<c:when test="${vi.voteBaseItem.type.name == 'multiple'}">
											<!-- 多选题 -->
												<tr>
													<td>
														<input type="checkbox" id="${vi.id }_answer_1" name="${vi.id }_answer_1" value="1"/><label for="${vi.id }_answer_1">  ${vi.voteBaseItem.answer1 }</label>
														<span class="placeholder"></span>
														<input type="checkbox" id="${vi.id }_answer_2" name="${vi.id }_answer_2" value="2"/><label for="${vi.id }_answer_2">  ${vi.voteBaseItem.answer2 }</label>
														<span class="placeholder"></span>
														<input type="checkbox" id="${vi.id }_answer_3" name="${vi.id }_answer_3" value="3"/><label for="${vi.id }_answer_3">  ${vi.voteBaseItem.answer3 }</label>
														<span class="placeholder"></span>
														<input type="checkbox" id="${vi.id }_answer_4" name="${vi.id }_answer_4" value="4"/><label for="${vi.id }_answer_4">  ${vi.voteBaseItem.answer4 }</label>
													</td>
												</tr>
											</c:when>
											<c:when test="${vi.voteBaseItem.type.name == 'completion'}">
											<!-- 填空题 -->
												<tr>
													<td>
														<c:forEach items="${vi.voteBaseItem.completionAnswerLabels }" var="label" varStatus="vistatus">
														<label>${label }</label><input type="text" name="${vi.id }_answer_${vistatus.count }" class="text_line"/><span class="placeholder" ></span>
														</c:forEach> 
													</td>
												</tr>
											</c:when>
										</c:choose>
									</tbody>
								</table>
							</td>
						</tr>
					</c:forEach>
						<tr>
							<td>备注 : </td>
							<td><input type="text" name="remark" class="text_line voteitem_question_input"/></td>
						</tr>
						<tr>
							<td colspan="2" align="center" style="text-align: center;"><input id="btn_submit_answer" type="button" class="button_03" value="确定"/></td>
						</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>