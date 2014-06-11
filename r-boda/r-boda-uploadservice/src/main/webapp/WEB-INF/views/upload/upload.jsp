<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<c:url value="/res/css/layout.css" />" />
<script src="<c:url value="/res/jquery/jquery-1.11.1.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/res/js/ajaxfileupload.js" />" type="text/javascript"></script>

<script type="text/javascript">
	function callback() {
		$.ajax({
			type : "post",
			url : "/boda/upload/fileUploadStatus",//响应文件上传进度的servlet  
			success : function(msg) {
				document.getElementById("span").innerHTML = "已上传：" + msg;//显示读取百分比  
				document.getElementById("table").width = msg;//通过表格宽度 实现进度条  
			}
		})
	}
	
	function callUpload(){
		$.ajaxFileUpload({
			url:'/boda/upload/up',
			data : $('#ceeditRating_add').serialize(),
			secureuri:false,
			fileElementId:'file',
			//dataType: 'json',
			success: function (data, status){
			},
			error	: function(XMLHttpRequest, textStatus, errorThrown){
			}
		});
	}
</script>

<title>Insert title here</title>
</head>
<body>
	<form id="ceeditRating_add"  action="/boda/upload/up" method="post" enctype="multipart/form-data" target="_parent" name="form">
		<input type="file" name="file" id="file">
		<input type="button" onclick="callUpload();" value="提交">
		<input type="button" onclick='window.setInterval("callback()", 500);' value="进度条1">
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

</body>
</html>