$(document).ready(function() {
	var tempName;
	$("#addUser").click(function() {
		$('#addUserModal input').each(function() {
			this.value = "";
		});
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
			var id = $(this).data("row-id");
			console.log(id);
			ajax(path + "/user/findOne", {
				id : id
			}, getInfoCallback);
			function getInfoCallback(res) {
				if (res.code == 1) {
					var user = res.data;
					tempName = user.username;
					$('#username1').val(user.username);
					$('#password1').val(user.password);
					$('#userRole1').val(parseInt(user.role));
					$('#updateUserModal').modal("show");
				} else {
					showToast(res.data, 'error');
				}
			}
		}).end().find(".command-delete").on("click", function(e) {
			if (confirm("你确定要删除此条记录?")) {
				ajax(path + "/user/delete", {
					id : $(this).data("row-id")
				}, deleteCallback);
				function deleteCallback(res) {
					if (res.code == 1) {
						showToast(res.data, 'success');
					} else {
						showToast(res.data, 'error');
					}
				}
				$("#grid-data").bootgrid("reload");
			}
		});

	});
	$("#updateUserSumbit").click(function() {
		var user = {};
		user.username = $('#username1').val();
		user.password = $('#password1').val();
		user.role = $('#userRole1').val();
		ajax(path + "/user/update", {
			user : JSON.stringify(user),
			username : tempName
		}, updateCallback);
		function updateCallback(res) {
			$("#grid-data").bootgrid("reload");
			if (res.code == 1) {
				showToast(res.data, 'success');
			} else {
				showToast(res.data, 'error');
			}
		}
		$("#updateUserModal").modal('hide');
		$("#grid-data").bootgrid("reload");
	});

	$("#addUserSumbit").click(function() {
		var user = {};
		user.username = $('#username').val();
		user.password = $('#password').val();
		user.role = $('#userRole').val();
		ajax(path + "/user/add", {
			user : JSON.stringify(user)
		}, addCallback);
		function addCallback(res) {
			$("#grid-data").bootgrid("reload");
			if (res.code == 1) {
				showToast(res.data, 'success');
				$("#addUserModal").modal('hide');
			} else {
				showToast(res.data, 'error');
			}
		}
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
});