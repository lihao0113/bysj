$("#submit").click(function(){
	var username = $("#username").val();
	var password = $("#password").val();

    ajax(path + "/user/signin", {username:username, password:password},login);

    function login(res) {
        if (res.code == 1){
            document.cookie = "userId=" + res.data.id;

            window.location.href = path + "/index";
        } else {
            alert(res.info)
        }
    }
	
    // $.ajax({
    // url:"",    //请求的url地址
    // dataType:"json",   //返回格式为json
    // data:{id:"value"},
    // type:"GET",
    // success:function(req){
    // },
    // error:function(){
    // }
// });
	// ajax(path + "/rwkh/loadEvaluation", {personId:"1"},showOption);
});
