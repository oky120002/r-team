var httpUrl = 'http://localhost:8080/boda/';

/**
 * 
 * @param args
 *            success成功回调,error失败回调
 */
function showUploadDialog(args) {
	var url = httpUrl + "upload/uploadpage";
	if (args.group) {
		url += "?uploadgroup=" + args.group;
	}
	window.showModalDialog(url, args, "dialogWidth=800px;dialogHeight=600px");
}
