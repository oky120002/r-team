var httpUrl = 'http://localhost:8080/boda/';

/**
 * 
 * @param args success成功回调,error失败回调
 */
function showUploadDialog(args){
	window.showModalDialog(httpUrl + "upload/uploadpage", args, "dialogWidth=800px;dialogHeight=600px");
}