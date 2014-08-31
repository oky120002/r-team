$(document).ready(function() {
	$('.start_hide').hide();
	
	// 绑定类型选择单选框
	$('.vitype').on('click',function(){
		var name = $(this).val();
		// 隐藏和禁用所有的表单区域
		$('.vitr').hide();
		$('.vitr input').attr("disabled", "disabled");
		$('.vitr textarea').attr("disabled", "disabled");
		// 显示和启用对应的表单区域
		$('#tr_' + name).show("slow");
		$('#tr_' + name + " input").removeAttr("disabled");
		$('#tr_' + name + " textarea").removeAttr("disabled");
	});
	
	// 绑定是非题事件
	$('#yesno_yes_text').on('blur',function(){
		$('#yesno_yes_label').html(' ' + $(this).val());
	});
	$('#yesno_no_text').on('blur',function(){
		$('#yesno_no_label').html(' ' + $(this).val());
	});
	
	// 绑定单选题事件
	$('#single_1_text').on('blur',function(){
		$('#single_1_label').html(' A : ' + $(this).val());
	});
	$('#single_2_text').on('blur',function(){
		$('#single_2_label').html(' B : ' + $(this).val());
	});
	$('#single_3_text').on('blur',function(){
		$('#single_3_label').html(' C : ' + $(this).val());
	});
	$('#single_4_text').on('blur',function(){
		$('#single_4_label').html(' D : ' + $(this).val());
	});
	
	// 绑定多选题事件
	$('#multiple_1_text').on('blur',function(){
		$('#multiple_1_label').html(' A : ' + $(this).val());
	});
	$('#multiple_2_text').on('blur',function(){
		$('#multiple_2_label').html(' B : ' + $(this).val());
	});
	$('#multiple_3_text').on('blur',function(){
		$('#multiple_3_label').html(' C : ' + $(this).val());
	});
	$('#multiple_4_text').on('blur',function(){
		$('#multiple_4_label').html(' D : ' + $(this).val());
	});

	// 绑定填空题事件
	$('.completion_radio').on('click',function(){
		$('#completion_span').hide();
		$('#completion_1_label').hide();
		$('#completion_1_text').hide();
		$('#completion_2_label').hide();
		$('#completion_2_text').hide();
		$('#completion_3_label').hide();
		$('#completion_3_text').hide();
		$('#completion_4_label').hide();
		$('#completion_4_text').hide();
		switch($(this).val()){
			case '4':
				$('#completion_4_label').show();
				$('#completion_4_text').show();
			case '3':
				$('#completion_3_label').show();
				$('#completion_3_text').show();
			case '2':
				$('#completion_2_label').show();
				$('#completion_2_text').show();
			case '1':
				$('#completion_1_label').show();
				$('#completion_1_text').show();
		}
		$('#completion_span').show('slow');
	});
	
	// 保存
	$('#btn_add').on('click',function(){
		var btn = $(this);
		btn.hide();
		submitDatas(null,"voteitem_add_form",function(support){
			if(support.success == true || support.success == 'true'){
				alert(support.tips);
				f5();
			} else {
				alert(support.tips);
				btn.show();
			}
		},function(errorMsg){ 
			alert(errorMsg);
		});
		return false;
	});
});