$(function(){
	$('.menu-0 li').on('click',function(){
		$(this).siblings().removeClass('on');
		$(this).attr('class','on');
	});
	
	$('#close').on('click',function(){
		window.close();
	})
	
	$('#submit').on('click',function(){
		let processInstanceId = $('#processInstanceId').text();
		let taskId = $('#taskId').text();
		let processKey = $('#processKey').text();
		$.ajax({
			url : '/workflow/completeTask',
			data : {processInstanceId,taskId,processKey},
			dataType: 'json',
			async: true,
			success : function(response,state,xhr){
				if(response.state == 'error'){
					alert(response.message);
				}else{
					window.close();
				}
			}
		});
	})
});