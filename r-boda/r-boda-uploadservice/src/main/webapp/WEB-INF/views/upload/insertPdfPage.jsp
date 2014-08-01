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
	function insertPdfPage() {
		$('#inserBtn').attr("disabled", "disabled");
		/// 校验插入的页码
		var start = $('#start').val();
		try {
			if($.isNumeric(start)){
				start = start * 1;
			} else {
				start = 1;
			}
		} catch (e) {
			start = 1;
		}
		$('#start').val(start);
		
		
		var u = '<c:url value="/upload/insertPdfPage/" />${fileId}/' + start;
		$.ajaxFileUpload({
			url : u,
			fileElementIds : ['uploadfile'],
			//data : $('#formid').serializeArray(),
			secureuri : false,
			dataType : 'json',
			success : function(data, status) {
				$('#span').html(data.params.pdfNumber);
				$('#inserBtn').removeAttr("disabled");
				alert(data.tips);
			},
			error : function(xml, status, errorThrown) {
				alert(xml);
			}
		});
	};
</script>
<title>插入pdf页</title>
</head>
<body>
	<form id="formid" action="">
		<table>
			<tr>
				<td>从:</td>
				<td><input type="text" id="start" /></td>
				<td>/<span id="span">${pdfPageNumber }</span></td>
				<td><input type="file" name="uploadfile" id="uploadfile" /> </td>
				<td><input type="button" value="插入" onclick="insertPdfPage();" id="inserBtn" /> </td>
			</tr>
		</table>
	</form>
</body>
</html>
