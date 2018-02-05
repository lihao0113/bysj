$("#addBtn").click(function (){
	var user ={};
	user.username = $('#username').val();
	user.password = $('#password').val();
	user.role = $('#role').val();
	ajax(path + "/user/add", {user:JSON.stringify(user)},addCallback);
	function addCallback(res){
		alert(res.data)
	}
});

