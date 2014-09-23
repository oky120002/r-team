<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<c:url value="/res/css/layout.css" />" />
<script src="<c:url value="/res/jquery/jquery-1.11.1.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/res/js/upload.client.js" />" type="text/javascript"></script>

<script type="text/javascript">
	function show(){
		showUploadDialog({
			group : "lms_2c909297481f70bb01481f7742e00005_2c909297481f70bb01481f7743c90007",
			tags : "申请文件,授权书文件,身份证明,收入证明,工作证明/经营证明,资产证明,住址证明,征信报告,合同文件", 
			sysname : "lms",
			emp : "2c90928a303e987301303f6429ee03fa",
			aut : {
				"申请文件": 31,
				"授权书文件": 15,
				"身份证明": 7, 
				"收入证明" : 3,
				"工作证明/经营证明" : 1,
				"资产证明" : 5,
				"住址证明" : 9,
				"征信报告" : 17,
				"合同文件" : 19,
			}
		});
	}
</script>

<title>测试页面</title>
</head>
<body>
	<input type="button" onclick='show();' value="上传">
</body>
</html>