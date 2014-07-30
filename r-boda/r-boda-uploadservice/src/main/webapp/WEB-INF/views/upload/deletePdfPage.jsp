<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
function deletePdfPage() {
	var url = '<c:url value="/upload/deletePageByPdf/" />${fileId}/' + $('#start').val() + '/' + $('#end').val();
	submitDatas(url, null, function(data) {
		$('#span').html(data.params.pdfNumber);
		alert(data.tips);
	}, function(message) {
		alert(message);
	});
};
</script>

<title>删除pdf页</title>
</head>
<body>
	<table>
		<tr>
			<td>从:</td>
			<td><input type="text" id="start" /></td>
			<td>到</td>
			<td><input type="text" id="end"/></td>
			<td>/<span id="span">${pdfPageNumber }</span></td>
			<td><input type="button" value="删除" onclick="deletePdfPage();" /> </td>
		</tr>
	</table>
</body>
</html>
