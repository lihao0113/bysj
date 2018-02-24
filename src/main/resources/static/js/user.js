var grid = $("#grid-data").bootgrid({
	ajax : true,
	url : path + "/user/pageAll",
/*	post : function() {
		return {
			current : 1,
			rowCount : 10
		}
	},*/
	converters : {
		datetime : {
			from : function(value) {
				return moment(value);
			},
			to : function(value) {
				return moment(value).format('YYYY-MM-DD');
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
	/* Executes after data is loaded and rendered */
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