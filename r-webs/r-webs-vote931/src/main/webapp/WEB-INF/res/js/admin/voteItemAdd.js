$(document).ready(function() {
	$('.vitr').hide();
	
	// 绑定类型选择单选框
	$('.vitype').on('click',function(){
		var name = $(this).val();
		$('.vitr').hide();
		$('#tr_' + name).show("slow");
	});
	
	// 绑定是非题备选答案描述框
	$('#yesno_yes_text').on('blur',function(){
		$('#yesno_yes_label').html($(this).val());
	});
	$('#yesno_no_text').on('blur',function(){
		$('#yesno_no_label').html($(this).val());
	});
});