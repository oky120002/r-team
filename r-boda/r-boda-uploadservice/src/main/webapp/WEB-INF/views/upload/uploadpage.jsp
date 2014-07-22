<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<c:url value="/res/css/layout.css" />" />
<script src="<c:url value="/res/jquery/jquery-1.11.1.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/res/js/upload.service.js" />" type="text/javascript"></script>
<script src="<c:url value="/res/js/common.js" />" type="text/javascript"></script>

<script type="text/javascript">
	var args = window.dialogArguments;
	var tags = args.tags; // 标签
	var havetags = ${havetags};	//  已经存在的标签
	var isTagModel = $.isArray(args.tags);	// 是否是标签模式
	
	/*增加一行附件*/
	function addRow(tag) {
		var name = "uploadfile" + new Date().getTime();
		var tr = '<tr class="tr_uploadfile">';
		if(tag){
			tr += '<td><label>' + tag + '</label><td>';
		}
		tr += '<td><input type="file" name="' + name + '" id="uploadfile" /> <input type="hidden" name="' + name + '_tag" value="' + tag + '" /> </td>';
		tr += '</tr>';
		$(tr).appendTo($('#formtable'));
	};

	/**增加一行列表数据*/
	function addTableRow(upload) {
		var deleteid = "deletetr" + upload.id;
		var tr = "";
		tr += '<tr id="' + deleteid + '">';
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
			fileElementIds : ['uploadfile'],
			data : $('#formid').serializeArray(),
			secureuri : false,
			dataType : 'json',
			success : function(data, status) {
				alert(data.tips);
				f5();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				stopProgress();
			}
		});
	}

	var callbackId;
	/**启动进度条*/
	function startProgress() {
		try {
			callbackId = window.setInterval("_progress();", 500);
		} catch (e) { };
	}

	/**停止进度条*/
	function stopProgress() {
		try {
			window.clearInterval(callbackId);
		} catch (e) { };
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
			// 如果有标签功能.则不能进行自由的添加附件
			f5();
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
	}
	
	/*删除pdf页面*/
	function deletePdf(id){
		
	}
	
	/*向pdf插入页面*/
	function insertPdf(id){
		
	}
	
	$(document).ready(function() {
		// 如果有标签功能.则不能进行自由的添加附件
		if(isTagModel){
			$('.untag').hide();
			$('.tag').show();
			
			$.each(tags, function(index, tag) {
				if(!isInArrays(tag,havetags)){
					addRow(tag);
				}
			});
		} else {
			$('.untag').show();
			$('.tag').hide();
		}
	});
</script>

<title>上传测试</title>
</head>
<body>
	<form id="formid">
		<input type="hidden" id="uploadgroup" name="uploadgroup" value="${uploadgroup }" /> 
		<input type="button" id="btn_addrow" onclick="addRow();" value="+附件" class="untag"/>
		<input type="button" id="btn_upload" onclick="upload();" value="提交" /> <br />
		<table id="formtable"></table>
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
				<th class="tag">标签</th>
				<th>文件名</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="filetabletbody">
			<c:if test="${isok }">
				<c:forEach items="${uploads }" var="upload">
					<tr id="deletetr${upload.id }">
						<td class="tag">${upload.tag }</td>
						<td>${upload.fileName }</td>
						<td>
							<button onclick="lookUploadFile('${upload.id }');">查看</button>
							<button onclick="downloadFile('${upload.id }');">下载</button>
							<button onclick="deleteFile('${upload.id }');">删除</button>
							<c:if test="${upload.fileType.responseDataType.name eq 'pdf' }">
								<button onclick="deletePdf('${upload.id }');">删除页面</button>
								<button onclick="insertPdf('${upload.id }');">插入页面</button>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</body>
</html>
