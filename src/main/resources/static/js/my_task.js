$(document).ready(function () {

    var grid = $("#grid-data").bootgrid({
        ajax: true,
        url: path + "/task/myTaskAll",
        converters: {
            datetime2: {
                from: function (value) {
                    return moment(value);
                },
                to: function (value) {
                    if (value === undefined) {
                        return "";
                    }
                    return moment(value).format('YYYY-MM-DD');
                }
            }
        },
        formatters: {
            "taskState": function (column, row) {
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
            "commands": function (column, row) {
                return "<button type=\"button\" class=\"btn btn-xs btn-default command-edit\" data-row-id=\"" + row.id + "\"><span class=\"glyphicon glyphicon-pencil\"></span></button> " +
                    "<button type=\"button\" class=\"btn btn-xs btn-default command-finshed\" data-row-id=\"" + row.id + "\"><span class=\"glyphicon glyphicon-ok\"></span></button>" +
                    "<button type=\"button\" class=\"btn btn-xs btn-default command-delete\" data-row-id=\"" + row.id + "\"><span class=\"glyphicon glyphicon-trash\"></span></button> ";
            },
            "progress": function (column, row) {
                var value = 0;
                $.ajax({
                    type: 'POST',
                    url: path + "/project/progress",
                    data: {id: row.id},
                    dataType: 'json',
                    async: false,
                    success: function (res) {
                        value = parseInt(res.value);
                    }
                });
                return "<div class=\"progress\" style=\"width:120px\">" +
                    "<div class=\"progress-bar progress-bar-success\" role=\"progressbar\" aria-valuenow=\"" + value + "\"" +
                    "aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width:" + parseInt(value) + "%;\">" +
                    "<span class=\"sr-only\">" + value + "% 完成</span>" +
                    "</div>" +
                    "</div>";
            }
        }
    }).on("loaded.rs.jquery.bootgrid", function () {
        grid.find(".command-edit").on("click", function (e) {
            var id = $(this).data("row-id");
            ajax(path + "/project/findOne", {
                id: id
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
        }).end().find(".command-delete").on("click", function (e) {
            if (confirm("你确定要删除此条记录?")) {
                ajax(path + "/project/delete", {
                    id: $(this).data("row-id")
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
        }).end().find(".command-finshed").on("click", function (e) {
            if (confirm("你确定要完成此项目吗?")) {
                ajax(path + "/project/finshed", {
                    id: $(this).data("row-id")
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
});
