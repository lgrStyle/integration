$(function(){
	$(".viewImage").on("click",function(event){
		let id = $(this).text();
		let url = root+"/workflow/process-image?processDefinitionId="+id;
		let name = "_blank";
		openFullWindow(url,name);
	});
});