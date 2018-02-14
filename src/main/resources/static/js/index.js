ajax(path + "/user/myself", null, loginUserCallback);
function loginUserCallback (res){
	if (res.code == 1){
		$('#loginUser').text(res.data.username);
	}
}

$('#test').attr("style", "color: #000;font-style: italic;font-weight: bold;");

$('#about').click(function (){
	window.open(path + '/about');
});

$('#tabTop').children('li').click(function() {
	$('#tabTop').children('li').each(function(index, item) {
		$(item).attr("style", "");
	});
	$(this).attr("style", "font-style: italic;font-weight: bold;");
});

$("#exit").click(function() {
	clearCookie();
	window.location.href = path + "/login";

});

function clearCookie() {
	document.cookie = "userId" + '=0;expires=' + new Date(0).toUTCString()
}

function showToast(message, state) {
	$.Toast("", message, state, {
		stack : true,
		has_icon : false,
		has_close_btn : true,
		fullscreen : false,
		timeout : 2000,
		sticky : true,
		has_progress : false,
		rtl : false,
	});
}