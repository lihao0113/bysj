
$("#home").click(function() {
	$('#myFrame').attr("src", "/bysj/home");
	$('#')
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