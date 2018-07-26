$(function(){
	let processInstanceId = $('#processInstanceId').text();
	let taskId = $('#taskId').text();
	let processKey = $('#processKey').text();
	
	$('.menu-0 li').on('click',function(){
		$(this).siblings().removeClass('on');
		$(this).attr('class','on');
	});
	
	$('#close').on('click',function(){
		window.close();
	})
	
	
	$('#submit').on('click',function(){
		switch(processKey){
			case 'leaveProcess': {
				let param = {
						processInstanceId,
						taskId,
						processKey,
				}
				task('/workflow/leaveTask',param);
				break;
			}
			case 'testProcess': {
				let param = {
						processInstanceId,
						taskId,
						processKey,
				}
				task('/workflow/testTask',param);
				break;
			}
			case 'jpaProcess': {
				let param = {
						processInstanceId,
						taskId,
						processKey,
				}
				task('/workflow/jpaTask',param);
				break;
			}
		}
	})
	
	$('#process-image').on('click',function(){
		$('.iframe-0').addClass('display-none');
		$('.iframe-1').removeClass('display-none');
		
	})
	
	$('#information').on('click',function(){
		$('.iframe-1').addClass('display-none');
		$('.iframe-0').removeClass('display-none');
	})
	
	function task(url,param){
		$.ajax({
			url : url,
			data : param,
			dataType: 'json',
			async: true,
			type: 'post',
			success : function(response,state,xhr){
				if(response.state == 'error'){
					alert(response.message);
				}else{
					window.close();
				}
			}
		});
	}
	
});