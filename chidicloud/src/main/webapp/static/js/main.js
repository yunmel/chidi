var $curmenu, lastIndex;
var webHistory = Webit.history;
jQuery(function($) {
	initMenuClick();
	$(document).on('click','[data-mode]',function(){
		var data = $(this).data();
		if(undefined != data['data'] && typeof data['data'] != "object") {
			data['data'] = eval("("+data.data+")");
		}
		$.cuslayer(data);
	});
});

function initMenuClick() {
	var menus = $("#sidebar .sidebar-menu a[id]");
	menus.on("click",function(){
		var hash = webHistory.get(),href = $(this).attr("href");
		if( ("#"+hash) == href ){
			webHistory.justShow("#");
			webHistory.go(hash);
		}
	});
	
	var $main_content = $("#content-wrapper");
	webHistory.add("ajax", function(str, action, token) {
		$main_content.html(loadHtmlPage(str));
		var curMenu = $("#sidebar .sidebar-menu li").find("a[href='#" + token + "']");
		changeMenu(curMenu);
	});
	
	webHistory.init();
	
}

function changeMenu(obj) {
	$this = $curmenu = obj, pli = $this.parents("li");
	$this.parent().parent().find("li").removeClass("active");
	$this.parent().addClass("active");
}

function loadHtmlPage(path, data) {
	var result;
	$.ajax({
		url : path,
		method : 'post',
		data : data,
		dataType : "text",
		async : false,
		success : function(html) {
			result = html;
		}
	});
	return result;
}