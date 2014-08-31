$(document).ready(function() {
	// 开始问卷
	$('#btn_start').on('click', function() {
		var btn = $(this);
		btn.hide();
		submitDatas(null, 'vote_option_form', function(support) {
			alert(support.tips);
			if (support.success == true || support.success == 'true') {
				window.location.href = support.model;
			} else {
				btn.show();
			}
		}, function(errorMsg) {
			alert(errorMsg);
		})
	});
	return false;
});