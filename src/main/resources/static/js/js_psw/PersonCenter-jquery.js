/*!
 * jQuery JavaScript Library v3.0.0
 * https://jquery.com/
 */

			//控制左边导航栏图标的变化
			$(".lefthead_li_a").click(function () {
				if($(this).parent().find("ul").is(':hidden')){
					$(this).parent().find("ul").show();
					$(this).parent().find("span").removeClass();
					$(this).parent().find("span").addClass("glyphicon glyphicon-chevron-up");
				}else{
					$(this).parent().find("ul").hide();
					$(this).parent().find("span").removeClass();
					$(this).parent().find("span").addClass("glyphicon glyphicon-chevron-down");
				}
			});
			
			//控制导航栏鼠标点击区域的颜色
			$(".panel-collapse li a").click(function(){
				$("li").siblings().removeClass("current");
				$(this).parents().addClass("current");
			})
			
