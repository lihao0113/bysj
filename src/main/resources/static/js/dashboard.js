ajax(path + "/logger/findCurrent", null, getLogger20Callback);

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
		return parseInt(diff_min/60) + "小时前";
	}
	if (diff_min >= 3600) {
		return parseInt(diff_min/3600) + "天前";
	}
	
}

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
			data : [ 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun' ],
			axisTick : {
				alignWithLabel : true
			}
		}
	],
	yAxis : [
		{
			type : 'value'
		}
	],
	series : [
		{
			name : '直接访问',
			type : 'bar',
			barWidth : '60%',
			data : [ 10, 52, 200, 334, 390, 330, 220 ]
		}
	]
};

var demo = echarts.init(document.getElementById("echart_demo"));
demo.setOption(option);