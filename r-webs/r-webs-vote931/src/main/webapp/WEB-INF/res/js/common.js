function isEmpty(obj) {
	if(obj == undefined || obj == null || obj == "" || jQuery.isEmptyObject(obj)){
		return true;
	}
	return false;
}

function isInArrays(target,arr){
	var flag = false;
	$.each(arr, function(index, ar) {
		if(target == ar){
			flag = true;
			return false;
		}
	});
	return flag;
}

/**刷新页面*/
function f5(){
	window.location.reload();
	window.location.href = window.location.href;
	location.replace(location.href);
}

/**
 * 提交数据
 * 
 * @param url
 *            地址
 * @param formId
 *            表单id
 * @param callback
 *            执行成功后的回调方法
 * @param errorback
 *            执行失败后的回调方法
 */
function submitDatas(url, formId, callback, errorback) {
	var form;
	// 默认值设置
	if (formId) {
		form = $('#' + formId);
	} else {
		form = $('form');
	}

	// 数据提交
	$.ajax({
		type : 'POST',
		url : url ? url : form.attr('action'),
		cache : false,
		data : form.serialize(),
		success : function(data, textStatus, XMLHttpRequest) {
			if (data) {
				if (data.success == 'true' || data.success == true) {
					try {
						callback(data);
					} catch (e) {
					}
				} else {
					try {
						errorback(data);
					} catch (e) {
					}
				}
			} else { 
				try {
					errorback(data);
				} catch (e) {
				}
			}
		}, 
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			errorMsg = XMLHttpRequest.responseText;
			if (/{error:([^}]+)}/ig.test(errorMsg)) {
				errorMsg = /{error:([^}]+)}/ig.exec(errorMsg)[1];
			}
			if (/Ψ<\/div>/ig.test(errorMsg)) {
				errorMsg = /Ψ<\/div>([^Ψ]+)/ig.exec(errorMsg)[1];
				errorMsg = errorMsg.replace(/<div style="display:none">/gi, '');
			}
			try {
				errorback(errorMsg);
			} catch (e) {
			}
		}
	});
}

