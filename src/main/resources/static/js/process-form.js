$(function(){
	$('.menu-0 li').on('mouseover',function(){
		$(this).siblings().removeClass('on');
		$(this).attr('class','on');
	});
});