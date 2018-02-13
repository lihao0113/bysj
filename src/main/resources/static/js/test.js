
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

$("#exit").click(function() {
	clearCookie();
	window.location.href = path + "/login";

});

$("#toastTest").click(function() {
	showToast('测试成功', 'success');
});

$("#userTest").click(function() {
	ajax(path + "/project/add", {
		project : '11111'
	}, projectCallback);
	function projectCallback(res) {
		alert(111)
	}
});

function clearCookie() {
	document.cookie = "userId" + '=0;expires=' + new Date(0).toUTCString()
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