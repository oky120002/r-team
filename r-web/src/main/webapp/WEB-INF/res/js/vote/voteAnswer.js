$(document).ready(function() {
	$('#btn_submit_answer').on('click',function(){
		var btn = $(this);
		btn.hide();
		
		submitDatas(null,"voteresult_answer_form",function(support){
			if(support.success == true || support.success == 'true'){
				alert(support.tips);
				//f5();
			} else {
				alert(support.tips);
				//btn.show();
			}
		},function(errorMsg){ 
			alert(errorMsg);
		});
		return false;
	});
});