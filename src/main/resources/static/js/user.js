$("#addUser").click(function (){
	$('#addUserModal input').each(function (){
		this.value = "";
	});
});

$("#addUserSumbit").click(function (){
	var user ={};
	user.username = $('#username').val();
	user.password = $('#password').val();
	user.role = $('#userRole').val();
	ajax(path + "/user/add", {user:JSON.stringify(user)},addCallback);
	function addCallback(res){
		if (res.code == 1){
			showToast(res.data, 'success');
			$("#addUserModal").modal('hide');
		} else {
			showToast(res.data, 'error');
		}
	}
});
var grid = $("#grid-data").bootgrid({
	ajax : true,
	url : path + "/user/pageAll",
	converters : {
		datetime : {
			from : function(value) {
				return moment(value);
			},
			to : function(value) {
				return moment(value).format('YYYY-MM-DD hh:mm:ss');
			}
		}
	},
	formatters : {
		"commands" : function(column, row) {
			return "<button type=\"button\" class=\"btn btn-xs btn-default command-edit\" data-row-id=\"" + row.id + "\"><span class=\"glyphicon glyphicon-pencil\"></span></button> " +
				"<button type=\"button\" class=\"btn btn-xs btn-default command-delete\" data-row-id=\"" + row.id + "\"><span class=\"glyphicon glyphicon-trash\"></span></button>";
		}
	}
}).on("loaded.rs.jquery.bootgrid", function() {
	grid.find(".command-edit").on("click", function(e) {
		alert("You pressed delete on row: " + $(this).data("row-id"));
		/*	$("#detail-mod").removeData("bs.modal");　　　　　　
			$("#detail-mod").modal({　　　　　　　　
				remote: "../UserCenter/UserDetail?type=detail&userId=" + $(this).data("row-id") + "" //点击详细按钮时请求路径  
			});*/

	}).end().find(".command-delete").on("click", function(e) {
		alert("You pressed delete on row: " + $(this).data("row-id"));

	});

});

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