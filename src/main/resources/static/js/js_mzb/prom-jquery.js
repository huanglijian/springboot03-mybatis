/*!
 * jQuery JavaScript Library v3.0.0
 * https://jquery.com/
 */

//控制左边导航栏图标的变化
function selectimg(id) {
	var spanid = "#" + id + " span";
	var classvalue = $(spanid).attr("class");
	var up = "glyphicon glyphicon-chevron-up"
	if(classvalue == up) {
		$(spanid).removeClass();
		$(spanid).addClass("glyphicon glyphicon-chevron-down");
	} else {
		$(spanid).removeClass();
		$(spanid).addClass("glyphicon glyphicon-chevron-up");
	}
}

//控制导航栏鼠标点击区域的颜色
$(".panel-collapse li a").click(function() {
	$(".panel-collapse li").siblings().removeClass("current");
	$(this).parents().addClass("current");
})

//控制竞标项目页面小界面的切换
$("#projBidul li a").click(function(){
	$("#projBidul li").siblings().removeClass("current");
	$(this).parents().addClass("current");
	$("#projBidul li a").removeClass("current");
	$(this).addClass("current");
	
})
