ajax(path + "/user/pageAll", {pageNumber:1,pageSize:2},pageTest);
function pageTest(res) {
    if (res.code == 1){
        var data = res.data;
        console.info(data);
    }
}

$("#exit").click(function (){
	clearCookie();
	window.location.href = path + "/login";
	
});

$("#toastTest").click(function (){
	showToast('测试成功', 'success');
});

$("#userTest").click(function (){
	ajax(path + "/project/add", {project:'11111'},projectCallback);
	function projectCallback(res) {
    	alert(111)
    }
});

$("#modalTest").click(function (){
	$('#example').modal('show')
});

function clearCookie() {  
   document.cookie = "userId" + '=0;expires=' + new Date(0).toUTCString()  
}

function showToast(message,state) {
	$.Toast("", message, state, {
    	stack: true,
        has_icon:false,
        has_close_btn:true,
        fullscreen:false,
        timeout:2000,
        sticky:true,
        has_progress:false,
        rtl:false,
    });	
}
