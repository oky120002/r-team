<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link  href="<c:url value="/res/css/boda.css" />" rel="stylesheet" type="text/css"/>
<script src="<c:url value="/res/jquery/jquery-1.11.1.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/res/js/upload.service.js" />" type="text/javascript"></script>
<script src="<c:url value="/res/js/common.js" />" type="text/javascript"></script>
<base target="_self">
<script type="text/javascript">
	var tags = ${tags}; // 标签
	var havetags = ${havetags};	//  已经存在的标签
	var isTagModel = $.isArray(tags);	// 是否是标签模式
	
	function _getRandomString(len) {
		len = len || 32;
		var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz'; // 默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1和数字2345678
		var maxPos = $chars.length;
		var pwd = '';
		for ( var i = 0; i < len; i++) {
			pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
		}
		return pwd;
	}
	
	/*增加一行附件*/
	function addRow(tag) {
		if(tag){
			var name = _getRandomString(16);
			var tr = '<tr class="tr_uploadfile">';
			if(tag){
				tr += '<td><label>' + tag + '</label><td>';
			}
			tr += '<td><input type="file" name="' + name + '" id="uploadfile"/> <input type="hidden" name="' + name + '_tag" value="' + tag + '" /> </td>';
			tr += '</tr>';
			$(tr).appendTo($('#formtable'));
		}
	};

	/**增加一行列表数据*/
	function addTableRow(upload) {
		if(upload){
			var deleteid = "deletetr" + upload.id;
			var tr = "";
			tr += '<tr id="' + deleteid + '">';
			tr += '<td>' + (isEmpty(upload.tag) ? "": upload.tag) + '</td>'; // 标签列
			tr += '<td>' + upload.fileName + '</td>'; // 文件名列
			tr += '<td>';
			tr += '<button onclick="lookUploadFile(\'' + upload.id + '\');" class="tableButton">查看</button>';
			tr += '<button onclick="downloadFile(\'' + upload.id + '\');" class="tableButton">下载</button>';
			tr += '<button onclick="deleteFile(\'' + upload.id + '\');" class="tableButton">删除</button>';
			tr += '</td>';
			tr += '</tr>';
			$('#filetabletbody').append(tr);
		}
	}

	/**上传*/
	function upload() {
		$('#btn_upload').attr("disabled", "disabled");
		$('#btn_addrow').attr("disabled", "disabled");
		startProgress();
		$.ajaxFileUpload({
			url : '<c:url value="/upload/uploads" />',
			fileElementIds : ['uploadfile'],
			data : $('#formid').serializeArray(),
			secureuri : false,
			dataType : 'json',
			success : function(data, status) {
				alert(data.tips);
				reload.click(); 
			},
			error : function(xml, status, errorThrown) {
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
		var url = '<c:url value="/upload/fileUploadStatus" />';
		submitDatas(url, null, function(data) {
			var model = data.model;
			$('#span').html(model.percent + "%");//显示读取百分比  
			$('#table').width(model.percent + "%");//通过表格宽度 实现进度条  
		});
	}

	/**删除文件*/
	function deleteFile(id) {
		if(confirm("是否确认删除?")) { 
			var url = '<c:url value="/upload/deleteUpload/" />' + id;
			submitDatas(url, null, function(data) {
				reload.click();
			}, function(message) {
				alert(message);
			});
		}
	}
	
	/**查看文件*/
	function lookUploadFile(id) {
		var url = '<c:url value="/upload/lookUploadFile/" />' + id;
		window.open(url, null, "alwaysRaised=yes;dialogWidth=800px;dialogHeight=600px");
	}
	
	/**下载文件*/
	function downloadFile(id) {
		var url = '<c:url value="/upload/downloadFile/" />' + id;
		window.open(url, null, "dialogWidth=800px;dialogHeight=600px");
	}
	
	/*删除pdf页面*/
	function deletePdf(id){
		var url = '<c:url value="/upload/deletePageByPdf/" />' + id;
		window.showModalDialog(url, null, "dialogWidth=400px;dialogHeight=50px");
	}
	
	/*向pdf插入页面*/
	function insertPdf(id){
		var url = '<c:url value="/upload/insertPdfPage/" />' + id;
		window.showModalDialog(url, null, "dialogWidth=500px;dialogHeight=50px");
	}
	
	function isInArrays(target,arr){
		var flg = false;
		$.each(arr, function(index, ar) {
			if(target == ar){
				flg = true;
			}
		});
		return flg;
	}
	
	$(document).ready(function() {
		// 如果有标签功能.则不能进行自由的添加附件
		if(isTagModel){
			$('.untag').hide();
			$('.tag').show();
			$.each(tags, function(index, tag) {
				if(!isInArrays(tag, havetags)){
					addRow(tag);
				}
			});
		} else {
			$('.untag').show();
			$('.tag').hide();
		}
		
		$('#reload').attr('href',window.location.href);
	});
</script>

<title>上传测试</title>
</head>
<body>
	<a id="reload" style="display: none;">reload...</a>
	<form id="formid">
		<table id="formtable"></table>
		<input type="hidden" id="uploadgroup" name="uploadgroup" value="${uploadgroup }" /> 
		<input type="button" id="btn_addrow" onclick="addRow();" value="+附件" class="untag tableButton"/>
		<input type="button" id="btn_upload" onclick="upload();" value="提交" class="tableButton" /> <br />
	</form>
	<table border="0">
		<tr>
			<td>
				<table id="table" width="100%" height="20px;" style="background-color: gray;" class="table">
					<tr>
						<td><span id="span" style="color: red;">已上传: 0%</span></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>

	<table width="100%" cellpadding="0" cellspacing="0" border="0" class="table">
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
							<button onclick="lookUploadFile('${upload.id }');" class="tableButton" >查看</button>
							<button onclick="downloadFile('${upload.id }');" class="tableButton" >下载</button>
							<button onclick="deleteFile('${upload.id }');" class="tableButton" >删除</button>
							<c:if test="${upload.fileType.responseDataType.name eq 'PDF' }">
								<button onclick="deletePdf('${upload.id }');" class="tableButton" >删除页面</button>
								<button onclick="insertPdf('${upload.id }');" class="tableButton" >插入页面</button>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table> 
</body>
</html>
