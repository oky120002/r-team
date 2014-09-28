//var httpUrl = 'http://190.100.100.50:8080/boda/';
 var httpUrl = 'http://192.168.0.154:8080/boda/';
// var httpUrl = 'http://192.168.1.2:8080/boda/';

/**
 * 
 * @param args
 *            success成功回调,error失败回调
 */
function showUploadDialog(args) {
	$.post(httpUrl + "upload/sessionparams", {
		group : args.group,
		tags : args.tags,
		sysname : args.sysname,
		emp : args.emp,
		aut : args.aut,
	}, function(data) {
		window.showModalDialog(data.params.url, null, "dialogWidth=800px;dialogHeight=600px");
	});
}
