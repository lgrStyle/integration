var root = window.location.origin;

function openFullWindow(url,name){
	let features = "menubar=0,toolbar=0,status=0,resizable=1,scrollbars=1";
	let top = 0;
	let left = 0;
	let height = window.screen.availHeight - 80;
	let width = window.screen.availWidth - 10;

	features += ",width=" + width;
	features += ",height=" + height;
	features += ",top=" + top;
	features += ",left=" + left;
	
	window.open(url,name,features);
}

