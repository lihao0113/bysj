$("#updatePass").click(function () {
    $('#updatePassModal input').each(function () {
        this.value = "";
    });
});

$("#myInfo").click(function () {
    $('#infoModal input').each(function () {
        this.value = "";
    });
});

$('#project_lab').click(function () {
    ajax(path + '/user/myself', null, loginUserCallback1);

    function loginUserCallback1(res) {
        if (res.code == 1) {
            var user = res.data;
            if (user.role == 2) {
                $('#project_iframe').attr('src', path + '/my_task_list');
            } else {
                $('#project_iframe').attr('src', path + '/projectList');
            }
        }
    }
});

$('#team_lab').click(function () {
    ajax(path + '/user/myself', null, loginUserCallback2);

    function loginUserCallback2(res) {
        if (res.code == 1) {
            var user = res.data;
            if (user.role == 0) {
                $('#team_iframe').attr('src', path + '/userList');
            } else {
                $('#team_iframe').attr('src', path + '/lack_of_authority');
            }
        }
    }
});

var currentUser;
ajax(path + "/user/myself", null, loginUserCallback);

function loginUserCallback(res) {
    if (res.code == 1) {
        currentUser = res.data;
        var createTime = moment(currentUser.createTime).format('YYYY-MM-DD hh:mm:ss');
        var roleStr = getRoleStr(currentUser.role);
            $('#loginUser').text(currentUser.username);
        $('#idLab').text(currentUser.id);
        $('#nameLab').text(currentUser.username);
        $('#timeLab').text(createTime);
        $('#roleLab').text(roleStr);
    }
}

function getRoleStr(role) {
	if (role == 0) {
	    return "系统管理员";
    }
    if (role == 1) {
        return "项目经理";
    }
    if (role == 2) {
        return "普通员工";
    }
}

$('#sumbitPass').click(function () {
    var oldPassword = $('#oldPassword').val();
    var newPassword = $('#newPassword').val();
    if (currentUser.password == oldPassword) {
        ajax(path + "/user/updatePass", {newPassword: newPassword, oldPassword: oldPassword}, updatePassCallback);

        function updatePassCallback(res) {
            if (res.code == 1) {
                var message = res.data;
                showToast(message, 'success');
                $("#updatePassModal").modal('hide');
            } else {
                showToast(message, 'error');
            }
        }
    } else {
        showToast("原密码错误，请重新输入", 'error');
    }

});

$('#test').attr("style", "color: #000;font-style: italic;font-weight: bold;");

$('#about').click(function () {
    window.open(path + '/about');
});

$('#tabTop').children('li').click(function () {
    $('#tabTop').children('li').each(function (index, item) {
        $(item).attr("style", "");
    });
    $(this).attr("style", "font-style: italic;font-weight: bold;");
});

$("#exit").click(function () {
    ajax(path + "/user/exit", null, null);
    clearCookie();
    window.location.href = path + "/login";

});

function clearCookie() {
    document.cookie = "userId" + '=0;expires=' + new Date(0).toUTCString()
}

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