
ajax(path + "/file/fileList", null, fileList);
function fileList(res) {
	if (res.code == 1) {
		var data = res.data;
		for (var i = 0; i < data.length; i++) {
			var sort = i + 1;
			//			$('#fileList').append('<li class="list-group-item">' + sort + '</li><li class="list-group-item">' + data[i] + '</li><li class="list-group-item"><button class="btn btn-primary" onclick="download(this)">下载</button><button onclick="del(this)" class="btn btn-danger">删除</button></li>');
			$('#fileList').append('<li style="height:45px;position:relative;" class="list-group-item"><span>' + sort + '</span><span>' + data[i] +
				'</span><span><button class="btn btn-xs btn-default" onclick="download(this)"><span class="delspan glyphicon glyphicon-download-alt"></span></button><button class="btn btn-xs btn-default" onclick="del(this)" ><span class="delspan glyphicon glyphicon-trash"></span></button></span></li>');
		}
		$('#fileList li span').attr("style", "float:left;width:33.3333%;text-align: center;");
		$('.delspan').attr("style", "");
		$('#fileList li:odd').css("background","#F1F1F1");
	}
}

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
		$('#fileList').html('<li style="height:45px;position:relative;" class="list-group-item">' +
			'<span style="float:left;width:33.3333%;text-align: center;font-weight:bold;">序号</span>' +
			'<span style="float:left;width:33.3333%;text-align: center;font-weight:bold;">文件名</span>' +
			'<span style="float:left;width:33.3333%;text-align: center;font-weight:bold;">下载/删除</span>' +
			'</li>');
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
			$('#fileList').html('<li style="height:45px;position:relative;" class="list-group-item">' +
				'<span style="float:left;width:33.3333%;text-align: center;font-weight:bold;">序号</span>' +
				'<span style="float:left;width:33.3333%;text-align: center;font-weight:bold;">文件名</span>' +
				'<span style="float:left;width:33.3333%;text-align: center;font-weight:bold;">下载/删除</span>' +
				'</li>');
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