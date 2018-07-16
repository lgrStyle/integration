$(function(){
	$(".viewImage").on("click",function(event){
		let id = $(this).text();
		let url = root+"/workflow/processImage?processDefinitionId="+id;
		let name = "_blank";
		openFullWindow(url,name);
	});
	
	$(".deleteDeploy").on("click",function(){
		let cascade = false;
		let deploymentId = $(this).parent("td").prevAll(".deploymentId").text();
		delDeployment(deploymentId,cascade);
	});
	
	$(".completeDelete").on("click",function(){
		let cascade = true;
		let deploymentId = $(this).parent("td").prevAll(".deploymentId").text();
		delDeployment(deploymentId,cascade);
	});
	
	function delDeployment(deploymentId,cascade){
		$.ajax({
			url : "/workflow/deleteDeploy",
			type : "get",
			data : {"deploymentId":deploymentId,"cascade":cascade},
			dataType : "html",
			async: true,
			success : function(data){
				window.location.reload();
			}
		});
	}
	
	$("#refresh").on("click",function(){
		window.location.reload();
	});
	
});