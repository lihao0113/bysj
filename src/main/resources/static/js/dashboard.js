ajax(path + "/logger/findCurrent", null, getLogger20Callback);
ajax(path + "/project/findIng", null, getIngProject);

function getIngProject(res) {
	if (res.code == 1) {
		var data = res.data;
		for (var i = 0; i < data.length; i++) {
			$('#project_ul').append('<li class="list-group-item" style="height:45px;">' +
				'<span class="pro_data">' + data[i].projectName + '</span>' + '<span  class="pro_data">' + data[i].createUser + '</span>' + '<span  class="pro_data">' + getDate(data[i].createTime) + '</span>' + '<span  class="pro_data1">' + getProgrss(data[i]) + '</span>' +
				'</li>');
		}
		$('#project_ul li').attr("height", "10px");
		$('#project_ul li:odd').css("background", "#F1F1F1");
	}
	$('.pro_data').attr("style", "width:260px;float:left;text-align:center;");
	$('.pro_data1').attr("style", "width:260px;float:left;text-align:center;padding-left:50px;");
}

function getDate(value) {
	if (value != null) {
		var date = new Date(value);
		return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
	}
}

function getProgrss(row) {
	var value = 0;
	$.ajax({
		type : 'POST',
		url : path + "/project/progress",
		data : {
			id : row.id
		},
		dataType : 'json',
		async : false,
		success : function(res) {
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

//$('#link_to_pro').click(function () {
//	var frame = window.parent.getElementById('link_to_pro');
//});

function getLogger20Callback(res) {
	if (res.code == 1) {
		var now = new Date();
		for (var i = 0; i < res.data.length; i++) {
			var time = new Date(res.data[i].time);
			var interval = now.valueOf() - time.valueOf();
			var intervalStr = getInterval(now, time);
			$('#log_ul').append('<li class="list-group-item" style="height:35px;">' + intervalStr + res.data[i].describtion + '</li>');
		}
		$('#log_ul li').attr("height", "10px");
		$('#log_ul li:odd').css("background", "#F1F1F1");
	}
}

ajax(path + "/logger/findTop100", null, getLogger100Callback);
function getLogger100Callback(res) {
	if (res.code == 1) {
		var now = new Date();
		for (var i = 0; i < res.data.length; i++) {
			var time = new Date(res.data[i].time);
			var interval = now.valueOf() - time.valueOf();
			var intervalStr = getInterval(now, time);
			$('#more_logger_ul').append('<li class="list-group-item" style="height:35px;">' + intervalStr + res.data[i].describtion + '</li>');
		}
		$('#more_logger_ul li').attr("height", "10px");
		$('#more_logger_ul li:odd').css("background", "#F1F1F1");
	}
}

function getInterval(now, time) {
	var now = new Date();
	var time = new Date(time);
	var interval = now.valueOf() - time.valueOf();
	var diff_min = parseInt(interval / (1000 * 60));
	if (diff_min < 60) {
		return diff_min + "分钟前";
	}
	if (diff_min >= 60 && diff_min < 3600) {
		return parseInt(diff_min / 60) + "小时前";
	}
	if (diff_min >= 3600) {
		return parseInt(diff_min / 3600) + "天前";
	}

}

ajax(path + "/project/indexEcharts", null, echartsCallback);

function echartsCallback(res) {
	var xData = res.xArr;
	var yData = res.yArr;
	var option = {
			color : [ '#3398DB' ],
			tooltip : {
				trigger : 'axis',
				axisPointer : { // 坐标轴指示器，坐标轴触发有效
					type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
				}
			},
			grid : {
				left : '3%',
				right : '4%',
				bottom : '3%',
				containLabel : true
			},
			xAxis : [
				{
					type : 'category',
					data : xData,
					axisTick : {
						alignWithLabel : true
					}
				}
			],
			yAxis : [
				{
					type : 'value',
                    max: 100
				}
			],
			series : [
				{
					name : '完成进度',
					type : 'bar',
					barWidth : '60%',
					data : yData
				}
			],
			label : {
				normal:{
					show: true,
					position : 'top',
					textStyle : {
						color : 'black'
					}
				}
			}
		};

		var demo = echarts.init(document.getElementById("echart_demo"));
		demo.setOption(option);
}

