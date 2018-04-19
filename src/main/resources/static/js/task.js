$(document).ready(function () {
    var taskId;
    Datetime();
    var grid = $("#grid-data").bootgrid({
        ajax: true,
        url: path + "/task/pageAll",
        converters: {
            datetime1: {
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
            "projectName": function (column, row) {
                return "<a onclick='linkToTask(this)'>" + row.projectName + "</a>";
            },

            "commands": function (column, row) {
                $(column).attr('style', 'width: 15%;');
                return "<button type=\"button\" class=\"btn btn-xs btn-default command-edit\" data-row-id=\"" + row.id + "\"><span class=\"glyphicon glyphicon-pencil\"></span></button> " +
                    "<button type=\"button\" class=\"btn btn-xs btn-default command-start\" data-row-id=\"" + row.id + "\"><span class=\"glyphicon glyphicon-play\"></span></button> " +
                    "<button type=\"button\" data-toggle=\"modal\" data-target=\"#zhipaiModal\"  class=\"btn btn-xs btn-default command-zhipai\" data-row-id=\"" + row.id + "\"><span class=\"glyphicon glyphicon-hand-right\"></span></button> " +
                    "<button type=\"button\" class=\"btn btn-xs btn-default command-finshed\" data-row-id=\"" + row.id + "\"><span class=\"glyphicon glyphicon-ok\"></span></button>" +
                    "<button type=\"button\" class=\"btn btn-xs btn-default command-delete\" data-row-id=\"" + row.id + "\"><span class=\"glyphicon glyphicon-trash\"></span></button> ";
            },
        }
    }).on("loaded.rs.jquery.bootgrid", function () {
        grid.find(".command-edit").on("click", function (e) {
            var id = $(this).data("row-id");
            taskId = id;
            ajax(path + "/task/findOne", {
                id: id
            }, getInfoCallback);
            function getInfoCallback(res) {
                if (res.code == 1) {
                    var data = res.data;
                    $('#taskName1').val(data.taskName);
                    $('#remarke1').val(data.remark);
                    $('#updateTaskModal').modal('show')
                } else {
                    showToast(res.data, 'success');
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
        }).end().find(".command-zhipai").on("click", function (e) {
            var id = $(this).data("row-id");
            taskId = id;
            var assginName;
            ajax(path + "/task/findOne", {
                id: id
            }, getTaskCallback);
            function getTaskCallback(res) {
                if (res.code == 1) {
                    var data = res.data;
                    $('#remarke2').val(data.remark);
                    assginName = data.assignName;
                }
            }

            $('#zhipaiModal').modal('show')
            $('#userSel1').find('option').remove();
            ajax(path + "/user/findAll", null, getUserList1);
            function getUserList1(res) {
                if (res.code == 1) {
                    var data = res.data;
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].username == assginName) {
                            $('#userSel1').append('<option value ="' + data[i].username + '" selected>' + data[i].username + '</option>');
                        } else {
                            $('#userSel1').append('<option value ="' + data[i].username + '">' + data[i].username + '</option>');
                        }
                    }
                }
            }
        }).end().find(".command-delete").on("click", function (e) {
            if (confirm("你确定要删除此条记录?")) {
                ajax(path + "/task/delete", {
                    id: $(this).data("row-id")
                }, deleteCallback);
                function deleteCallback(res) {
                    if (res.code == 1) {
                        showToast(res.info, 'success');
                    } else {
                        showToast(res.info, 'error');
                    }
                    $("#grid-data").bootgrid("reload");
                }

                $("#grid-data").bootgrid("reload");
            }
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

    $('#addTask').click(function () {
        $('#addTaskModal input').each(function () {
            this.value = "";
        });
        $('#addTaskModal textarea').each(function () {
            this.value = "";
        });
        $('#userSel').html('');
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

    $("#updateTaskSumbit").click(function () {
        var task = {};
        task.id = taskId;
        task.taskName = $('#taskName1').val();
        task.remark = $('#remarke1').val();
        ajax(path + "/task/update", {
            task: JSON.stringify(task)
        }, updateCallback);
        function updateCallback(res) {
            if (res.code == 1) {
                showToast(res.info, 'success');
            } else {
                showToast(res.info, 'error');
            }
            $("#updateTaskModal").modal('hide');
            $("#grid-data").bootgrid("reload");
        }
    });

    $('#zhipaiSumbit').click(function () {
        var task = {};
        task.id = taskId;
        task.assignName = $('#userSel1').val();
        task.remark = $('#remarke2').val();
        ajax(path + "/task/updateZhipai", {
            task: JSON.stringify(task)
        }, zhipaiCallback);
        function zhipaiCallback(res) {
            if (res.code == 1) {
                showToast(res.info, 'success');
            } else {
                showToast(res.info, 'error');
            }
            $("#zhipaiModal").modal('hide');
            $("#grid-data").bootgrid("reload");
        }
    });

    $("#addTaskSumbit").click(function () {
        var task = {};
        task.remark = $('#remarke').val();
        task.taskName = $('#taskName').val();
        if (task.taskName == '') {
            alert('任务名称不可为空！')
            return;
        }
        task.assignName = $('#userSel').find('option:selected').text();
        task.expriyTime = document.getElementById("expriyTime1").value;
        ajax(path + "/task/add", {
            task: JSON.stringify(task)
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

    function Datetime() {
        $('#datetimepicker1').datetimepicker({
            language: 'zh-CN', //显示中文
            format: 'yyyy-mm-dd', //显示格式
            minView: "month", //设置只显示到月份
            initialDate: new Date(),
            autoclose: true, //选中自动关闭
            todayBtn: true, //显示今日按钮
            locale: moment.locale('zh-cn')
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
    }

});