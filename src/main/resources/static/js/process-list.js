$(function(){
	$(".viewImage").on("click",function(event){
		let id = $(this).text();
		let url = root+"/workflow/process-image?processDefinitionId="+id;
		let name = "流程图";
		openFullWindow(url,name,"left=0,top=0,height=800,width=1200");
	});
});