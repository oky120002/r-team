<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<c:url value="/res/css/layout.css" />" />
<script src="<c:url value="/res/jquery/jquery-1.11.1.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/res/js/upload.service.js" />" type="text/javascript"></script>

<script type="text/javascript">
	var args = window.dialogArguments;
	$(document).ready(function() {
		if(args.group){ 
			$('#uploadgroup').val(args.group);
		}
	});
	
 	/*增加一行附件*/
	function addRow() {
		$('<input type="file" name="uploadfile" id="file"><br />').appendTo($('#formid'));
	};
	
	/**上传*/
	function upload(){
		startProgress();
		$.ajaxFileUpload({
			url: '/boda/upload/uploads',
			fileElementName: 'uploadfile',
			data: $('#formid').serializeArray(),
			secureuri: false,
			success: function (data, status){
				stopProgress();
				try {
					args.success(data,status);
				} catch (e) {
				}
				
			},
			error	: function(XMLHttpRequest, textStatus, errorThrown){
				stopProgress();
				try {
					args.error(XMLHttpRequest,textStatus,errorThrown);
				} catch (e) {
				}
			},
		});
	}
	
	var callbackId;
	/**启动进度条*/
	function startProgress(){
		try {
			callbackId = window.setInterval("_progress();", 500);
		} catch (e) {
		}
	}
	
	/**只听进度条*/
	function stopProgress(){
		try {
			window.clearInterval(callbackId);
		} catch (e) {
		}
	}
	
	/**进度条*/
	function _progress(){
 		$.ajax({
 			type : "post",
 			url : "/boda/upload/fileUploadStatus",//响应文件上传进度的servlet  
 			success : function(msg) {
 				if(msg){
 	 				document.getElementById("span").innerHTML = msg.message;//显示读取百分比  
 	 				document.getElementById("table").width = msg.percent;//通过表格宽度 实现进度条  
 				} else {
 				}
 			}
 		});
	}
</script>

<title>上传测试</title>
</head>
<body>
	<form id="formid">
		<input type="hidden" id="uploadgroup" name="uploadgroup"/>
		<input type="button" onclick="addRow();" value="+附件" />
		<input type="button" onclick="upload();" value="提交" />
		<br />
	</form>

	<span id="span">已上传: 0</span>
	<table width="300px;" border="0">
		<tr>
			<td>
				<table id="table" height="20px;" style="background-color: gray;">
					<tr>
						<td></td>
					</tr>
				</table>//用来实现进度条显示
			</td>
		</tr>
	</table>
	
	<c:if test="${isok }">
		<table>
			<thead>
				<tr>
					<th>文件名</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${uploads }" var="upload">
				<tr>
					<td>${upload.fileName }</td>
					<td><a href="/boda/upload/deleteUpload/false/${upload.id }">删除</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</c:if>
</body>
</html>
