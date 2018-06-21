$(function(){
	$(".sidebar-menu .sidebar-ul li").mouseover(function(){
		$(this).siblings().removeClass("menu-on");
		$(this).addClass("menu-on");
		let array = this.id.split("-");
		let id = "#menu-m2-"+array[2];
		$(id).parent().removeClass("display-none");
		
	});
	
	$(".sidebar-menu .sidebar-ul li").mouseleave(function(){
		let array = this.id.split("-");
		let id = "#menu-m2-"+array[2];
		$(id).parent().addClass("display-none");
	});
	
	$(".sidebar-menu-2 .two-menu").mouseover(function(){
		$(this).removeClass("display-none");
		let element = $(this).find("ul")[0];
		let array = element.id.split("-");
		let id = "#menu-m1-"+array[2];
		$(id).addClass("menu-on");
	});
	
	$(".sidebar-menu-2 .two-menu").mouseleave(function(){
		$(this).addClass("display-none");
	});
	
	$(".start-up").click(function(){
		$(".sidebar").toggleClass("sidebar-narrow");
		$(".sidebar-nav .sidebar-menu a").toggleClass("display-none");
		$(".sidebar-nav .sidebar-menu-2 .two-menu").toggleClass("menu-narrow");
		$(".start-up span").toggleClass("glyphicon-menu-left");
		$(".start-up span").toggleClass("glyphicon-menu-right");
	});
	
	$(document).on('click',".head-tab",function(){
		let flag = $(this).hasClass("select-tab");
		if(!flag){
			$(this).siblings().removeClass("select-tab");
			$(this).addClass("select-tab");
			let num = this.id.split("-")[1];
			$("iframe").addClass("display-none");
			$("#iframe-"+num).removeClass("display-none");
		}
	});
	
	$(document).on('click',".head-tab i",function(){
		let id = $(this).parent(".head-tab").attr("id");
		let num = id.split("-")[1];
		let prevId = $("#"+id).prev().attr("id");
		let prevNum = prevId.split("-")[1];
		$("#"+id).remove();
		$("#iframe-"+num).remove();
		$("#"+prevId).addClass("select-tab");
		$("#iframe-"+prevNum).removeClass("display-none");
		
	});
	
	$(".sidebar-menu-2 a").click(function(){
		let menuname = $(this).attr("menuname");
		let menucode = $(this).attr("menucode");
		let array = $(".head-tab span");
		let flag = false;
		let i=0;
		for(; i<array.length; i++){
			if(menuname == array[i].innerText){
				flag = true;
				break;
			}
		}
		if(flag){
			$(array[i]).parent(".head-tab").siblings().removeClass("select-tab");
			$(array[i]).parent(".head-tab").addClass("select-tab");
		}else{
			$("iframe").addClass("display-none");
			let e1 = '<div id="tab-'+i+'" class="head-tab"><span>'+menuname+'</span><i>&times;</i></div>';
			let e2 = '<iframe src="'+menucode+'" id="iframe-'+i+'"></iframe>';
			$("#head-tabs").append(e1);
			$("#main-content").append(e2);
			$("#tab-"+i).siblings().removeClass("select-tab");
			$("#tab-"+i).addClass("select-tab");
		}
		
	});
	
	
});