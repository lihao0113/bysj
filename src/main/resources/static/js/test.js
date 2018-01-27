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

function clearCookie() {  
   document.cookie = "userId" + '=0;expires=' + new Date(0).toUTCString()  
}

