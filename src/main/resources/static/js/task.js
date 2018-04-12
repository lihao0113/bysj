$(document).ready(function() {
	Datetime();
	var grid = $("#grid-data").bootgrid({
		ajax : true,
		url : path + "/task/pageAll",
		converters : {
			datetime : {
				from : function(value) {
					return moment(value);
				},
				to : function(value) {
					if (value === undefined) {
						return "";
					}
					return moment(value).format('YYYY-MM-DD hh:mm:ss');
				}
			}
		},
		formatters : {
			"taskState" : function(column, row) {
				var result;
				switch (row.projectState) {
				case "0":
					result = "未开始"
					break;
				case "1":
					result = "进行中"
					break;
				case "2":
					result = "已完成"
					break;
				}
				return "<span>" + result + " </span>";
			},
			"projectName" : function(column, row) {
				return "<a onclick='linkToTask(this)'>" + row.projectName + "</a>";
			},
			"commands" : function(column, row) {
				return "<button type=\"button\" class=\"btn btn-xs btn-default command-edit\" data-row-id=\"" + row.id + "\"><span class=\"glyphicon glyphicon-pencil\"></span></button> " +
					"<button type=\"button\" class=\"btn btn-xs btn-default command-finshed\" data-row-id=\"" + row.id + "\"><span class=\"glyphicon glyphicon-ok\"></span></button>" +
					"<button type=\"button\" class=\"btn btn-xs btn-default command-delete\" data-row-id=\"" + row.id + "\"><span class=\"glyphicon glyphicon-trash\"></span></button> ";
			},
		}
	}).on("loaded.rs.jquery.bootgrid", function() {
		grid.find(".command-edit").on("click", function(e) {
			var id = $(this).data("row-id");
			ajax(path + "/project/findOne", {
				id : id
			}, getInfoCallback);
			function getInfoCallback(res) {
				if (res.code == 1) {
					var project = res.data;
					tempName = project.projectName;
					$('#projectName1').val(tempName);
					$('#remarke1').val(project.remark);
					$('#updateProjectModal').modal("show");
				} else {
					showToast(res.data, 'success');
				}
			}
		}).end().find(".command-delete").on("click", function(e) {
			if (confirm("你确定要删除此条记录?")) {
				ajax(path + "/task/delete", {
					id : $(this).data("row-id")
				}, deleteCallback);
				function deleteCallback(res) {
					if (res.code == 1) {
						showToast(res.data, 'success');
					} else {
						showToast(res.data, 'error');
					}
					$("#grid-data").bootgrid("reload");
				}
				$("#grid-data").bootgrid("reload");
			}
		}).end().find(".command-finshed").on("click", function(e) {
			if (confirm("你确定要完成此项目吗?")) {
				ajax(path + "/task/finshed", {
					id : $(this).data("row-id")
				}, finshedCallback);
				function finshedCallback(res) {
					if (res.code == 1) {
						showToast(res.data, 'success');
					} else {
						showToast(res.data, 'error');
					}
					$("#grid-data").bootgrid("reload");
				}
				$("#grid-data").bootgrid("reload");
			}
		});

	});

	$('#addTask').click(function() {
		$('#addTaskModal input').each(function() {
			this.value = "";
		});
		$('#addTaskModal textarea').each(function() {
			this.value = "";
		});
		ajax(path + "/user/findAll", null, getUserList);
		function getUserList(res) {
			if (res.code == 1) {
				var data = res.data;
				for (var i = 0; i < data.length; i++) {
					$('#userSel').append('<option value ="' + data[i].username + '">' + data[i].username + '</option>');
				}
			}
		}
	});

	$("#updateTaskSumbit").click(function() {
		var project = {};
		project.projectName = $('#projectName1').val();
		project.remark = $('#remarke1').val();
		ajax(path + "/task/update", {
			project : JSON.stringify(project),
			projectName : tempName
		}, updateCallback);
		function updateCallback(res) {
			$("#grid-data").bootgrid("reload");
			if (res.code == 1) {
				showToast(res.data, 'success');
			} else {
				showToast(res.data, 'error');
			}
		}
		$("#updateTaskModal").modal('hide');
		$("#grid-data").bootgrid("reload");
	});

	$("#addTaskSumbit").click(function() {
		var task = {};
		task.remark = $('#remarke').val();
		task.taskName = $('#taskName').val();
		task.assignName = $('#userSel').find('option:selected').text();
		task.expriyTime = Datetime();
		ajax(path + "/task/add", {
			task : JSON.stringify(task)
		}, addCallback);
		function addCallback(res) {
			$("#grid-data").bootgrid("reload");
			if (res.code == 1) {
				showToast(res.data, 'success');
				$("#addTaskModal").modal('hide');
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

	function Datetime() {
		$('#datetimepicker1').datetimepicker({
			language : 'zh-CN', //显示中文
			format : 'yyyy-mm-dd', //显示格式
			minView : "month", //设置只显示到月份
			initialDate : new Date(),
			autoclose : true, //选中自动关闭
			todayBtn : true, //显示今日按钮
			locale : moment.locale('zh-cn')
		});
		//默认获取当前日期
		var today = new Date();
		var nowdate = (today.getFullYear()) + "-" + (today.getMonth() + 1) + "-" + today.getDate();
		//对日期格式进行处理
		var date = new Date(nowdate);
		var mon = date.getMonth() + 1;
		var day = date.getDate();
		var mydate = date.getFullYear() + "-" + (mon < 10 ? "0" + mon : mon) + "-" + (day < 10 ? "0" + day : day);
		document.getElementById("expriyTime1").value = mydate;
		return mydate;
	}

});