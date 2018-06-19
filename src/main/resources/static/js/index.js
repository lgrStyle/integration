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
	
});