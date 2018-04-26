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
                switch (row.taskState) {
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
                return "<button type=\"button\" class=\"btn btn-xs btn-default command-info\" data-row-id=\"" + row.id + "\"><span class=\"glyphicon glyphicon-info-sign\"></span></button> " +
                    "<button type=\"button\" class=\"btn btn-xs btn-default command-start\" data-row-id=\"" + row.id + "\"><span class=\"glyphicon glyphicon-play\"></span></button>" +
                    "<button type=\"button\" class=\"btn btn-xs btn-default command-finshed\" data-row-id=\"" + row.id + "\"><span class=\"glyphicon glyphicon-ok\"></span></button> ";
            }
        }
    }).on("loaded.rs.jquery.bootgrid", function () {
        grid.find(".command-info").on("click", function (e) {
            var id = $(this).data("row-id");
            ajax(path + "/task/findOne", {
                id: id
            }, getInfoCallback);

            function getInfoCallback(res) {
                if (res.code == 1) {
                    var task = res.data;
                    $('#taskName').val(task.taskName);
                    $('#remarke').val(task.remark);
                    $('#projectName').val(task.projectName);
                    $('#assignName').val(task.assignName);
                    $('#seeTaskModal').modal("show");
                } else {
                    showToast(res.info, 'error');
                }
            }
        }).end().find(".command-start").on("click", function (e) {
            var id = $(this).data("row-id");
            ajax(path + "/task/start", {
                id: id
            }, startCallback);

            function startCallback(res) {
                if (res.code == 1) {
                    showToast(res.info, 'success');
                } else {
                    showToast(res.info, 'error');
                }
                $("#grid-data").bootgrid("reload");
            }

            $("#grid-data").bootgrid("reload");
        }).end().find(".command-finshed").on("click", function (e) {
            ajax(path + "/task/finshed", {
                id: $(this).data("row-id")
            }, finshedCallback);

            function finshedCallback(res) {
                if (res.code == 1) {
                    showToast(res.info, 'success');
                } else {
                    showToast(res.info, 'error');
                }
                $("#grid-data").bootgrid("reload");
            }

            $("#grid-data").bootgrid("reload");
        });

    });

    function showToast(message, state) {
        $.Toast("", message, state, {
            stack: true,
            has_icon: false,
            has_close_btn: true,
            fullscreen: false,
            timeout: 2000,
            sticky: true,
            has_progress: false,
            rtl: false,
        });
    }
});
