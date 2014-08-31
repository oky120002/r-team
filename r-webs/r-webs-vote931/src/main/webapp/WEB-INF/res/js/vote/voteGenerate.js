$(document).ready(function() {
	$('.getr').hide();

	// 绑定类型选择单选框
	$('.getype').on('click', function() {
		var val = $(this).val();
		// 隐藏和禁用所有的表单区域
		$('.getr').hide();
		// 显示和启用对应的表单区域
		$('#tr_' + val).show("slow");
	});
});