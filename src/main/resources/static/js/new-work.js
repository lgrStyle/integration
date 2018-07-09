function createWork(key,name,type){
	window.processKey = key;
	window.processName = name;
	$("#msgTip").hide();
	$("#suffixForm").hide();
	$("#prefixForm").hide();
	$("#titleForm").hide();
	$("#suffix").val('');
	$("#prefix").val('');
	$('#title').val('');
	switch(type){
		case 0: $("#msgTip").show();break;
		case 1: $("#suffixForm").show();break;
		case 2: $("#prefixForm").show();break;
		case 3: $("#titleForm").show();break;
	}
	$("#title-modal").modal('show');
}

function viewGraph(key){
	let url = "/workflow/process-image?processDefinitionKey="+key;
	openFullWindow(url);
}

function doCreate(){
	let param = {
		'suffix': $('#suffix').val(),
		'prefix' : $('#prefix').val(),
		'title' : $('#title').val(),
		'processKey' : window.processKey,
		'processName' : window.processName,
	};
	$.ajax({
		url: '/workflow/startFlow',
		data: param,
		type: 'get',
		dataType: 'json',
		async: true,
		success: function(data,status,xhr){
			debugger
		}
	});
}