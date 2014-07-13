<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/res/css/layout.css" />" />
<script src="<c:url value="/res/jquery/jquery-1.11.1.min.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/res/js/upload.service.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/res/js/common.js" />" type="text/javascript"></script>

<script type="text/javascript">
	var args = window.dialogArguments;
	var tags = args.tags; // 标签
	$(document).ready(function() {
		alert(tags);
	});

	/*增加一行附件*/
	function addRow() {
		$('<input type="file" name="uploadfile" class="br_uploadfile" id="uploadfile"><br class="br_uploadfile" />').appendTo($('#formid'));
	};

	function isEmpty(obj) {
		if(obj == undefined || obj == null || obj == "" || jQuery.isEmptyObject(obj)){
			return true;
		}
		return false;
	}

	function addTableRow(upload) {

		var id = "deletetr" + upload.id;
		var tr = "";
		tr += '<tr id="' + id + '">';
		tr += '<td>' + (isEmpty(upload.tag) ? "": upload.tag) + '</td>'; // 标签列
		tr += '<td>' + upload.fileName + '</td>'; // 文件名列
		tr += '<td>';
		tr += '<button onclick="lookUploadFile(\'' + upload.id + '\');">查看</button>';
		tr += '<button onclick="downloadFile(\'' + upload.id + '\');">下载</button>';
		tr += '<button onclick="deleteFile(\'' + upload.id + '\');">删除</button>';
		tr += '</td>';
		tr += '</tr>';
		$('#filetabletbody').append(tr);
	}

	/**上传*/
	function upload() {
		$('#btn_upload').attr("disabled", "disabled");
		$('#btn_addrow').attr("disabled", "disabled");
		startProgress();
		$.ajaxFileUpload({
			url : '/boda/upload/uploads?format=json',
			fileElementName : 'uploadfile',
			data : $('#formid').serializeArray(),
			secureuri : false,
			dataType : 'json',
			success : function(data, status) {
				stopProgress();
				_progress();
				$('#btn_upload').removeAttr("disabled");
				$('#btn_addrow').removeAttr("disabled");
				$.each($('.br_uploadfile'), function(index, upload) {
					upload.remove();
				});
				$.each(data.entities, function(index, upload) {
					addTableRow(upload);
				});
				alert(data.tips);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				stopProgress();
			},
		});
	}

	var callbackId;
	/**启动进度条*/
	function startProgress() {
		try {
			callbackId = window.setInterval("_progress();", 500);
		} catch (e) {
		}
	}

	/**停止进度条*/
	function stopProgress() {
		try {
			window.clearInterval(callbackId);
		} catch (e) {
		}
	}

	/**进度条*/
	function _progress() {
		submitDatas('/boda/upload/fileUploadStatus', null, function(data) {
			var model = data.model;
			document.getElementById("span").innerHTML = model.percent + "%";//显示读取百分比  
			document.getElementById("table").width = model.percent + "%";//通过表格宽度 实现进度条  
		});
	}

	/**删除文件*/
	function deleteFile(id) {
		submitDatas('/boda/upload/deleteUpload/false/' + id, null, function(data) {
			$('#deletetr' + id).remove();
		}, function(message) {
			alert(message);
		});
	}
	
	/**查看文件*/
	function lookUploadFile(id) {
		var url = "/boda/upload/lookUploadFile/" + id;
		window.showModalDialog(url, null, "dialogWidth=800px;dialogHeight=600px");
	}
	
	/**下载文件*/
	function downloadFile(id) {
		var url = "/boda/upload/downloadFile/" + id;
		window.showModalDialog(url, null, "dialogWidth=800px;dialogHeight=600px");
		//$.get("/boda/upload/downloadFile/" + id);
	}
</script>

<title>上传测试</title>
</head>
<body>
	<form id="formid">
		<input type="hidden" id="uploadgroup" name="uploadgroup" value="${uploadgroup }" /> 
		<input type="button" id="btn_addrow" onclick="addRow();" value="+附件" />
		<input type="button" id="btn_upload" onclick="upload();" value="提交" /> <br />
	</form>

	<table width="100%" border="0">
		<tr>
			<td>
				<table id="table" height="20px;" style="background-color: gray;">
					<tr>
						<td><span id="span" style="color: red;">已上传: 0%</span></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>

	<table class="list_style_1">
		<thead>
			<tr>
				<th>标签</th>
				<th>文件名</th>
				<th></th>
			</tr>
		</thead>
		<tbody id="filetabletbody">
			<c:if test="${isok }">
				<c:forEach items="${uploads }" var="upload">
					<tr id="deletetr${upload.id }">
						<td>${upload.tag }</td>
						<td>${upload.fileName }</td>
						<td><button onclick="lookUploadFile('${upload.id }');">查看</button><button onclick="downloadFile('${upload.id }');">下载</button><button onclick="deleteFile('${upload.id }');">删除</button></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</body>
</html>
