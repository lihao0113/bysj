ajax(path + "/file/fileList", null, fileList);
function fileList(res) {
	if (res.code == 1) {
		var data = res.data;
		for (var i = 0; i < data.length; i++) {
			var sort = i + 1;
			$('#fileTable tbody').append('<tr><td>' + sort + '</td><td>' + data[i] + '</td><td><button class="btn btn-primary" onclick="download(this)">下载</button><button onclick="del(this)" class="btn btn-danger">删除</button></td></tr>');
			$('#fileTable td').attr("style", "text-align:center");
		}
	}
}

$('#fileTable td').attr("style", "text-align:center");

function fileUpload() {
	var formData = new FormData();
	formData.append('file', $('#file')[0].files[0]);
	$.ajax({
		url : 'file/upload',
		type : 'POST',
		cache : false,
		data : formData,
		processData : false,
		contentType : false
	}).done(function(res) {
		showToast(res, 'success');
		$('#fileTable tbody').html('');
		ajax(path + "/file/fileList", null, fileList);
	}).fail(function(res) {
		showToast(res, 'error');
	});
}

function download(which) {
	var filename = $(which).parent().prev().text();
	window.location.href = "file/download?filename=" + filename;
}

function del(which) {
	if (confirm("确定删除？")) {
		var filename = $(which).parent().prev().text();
		ajax(path + "/file/delete", {
			filename : filename
		}, deleteFile);
		function deleteFile(res) {
			if (res.code == 1) {
				showToast(res.data, 'success');
			} else {
				showToast(res.data, 'error');
			}
			$('#fileTable tbody').html('');
			ajax(path + "/file/fileList", null, fileList);
		}
	} else {
		return;
	}
}

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