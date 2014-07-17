<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<c:url value="/res/css/layout.css" />" />
<script src="<c:url value="/res/jquery/jquery-1.11.1.min.js" />" type="text/javascript"></script>
<script src="<c:url value="http://localhost:8080/boda/res/js/upload.client.js" />" type="text/javascript"></script>

<script type="text/javascript">
	function show(){
		showUploadDialog({
			group : "r-boda-test",
			tags : [
					"abcd","efgh","321123","111111111111"
				],
		});
	}
</script>

<title>测试页面</title>
</head>
<body>
	<input type="button" onclick='show();' value="上传">
</body>
</html>