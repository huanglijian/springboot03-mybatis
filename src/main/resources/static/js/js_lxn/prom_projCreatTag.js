$(function(){
  $(".show-labelitem").on("click",function(){
    $(this).hide();
    $(".hide-labelitem").show();
    $("#labelItem").show();
  });
  $(".hide-labelitem").on("click",function(){
    $(this).hide();
    $(".show-labelitem").show();
    $("#labelItem").hide();
  });
  $(".label-item").on("click","li",function(){
    var id = $(this).attr("data");
    var text = $(this).children("span").html();
    var labelHTML = "<li data='"+id+"''>x "+text+"</li>";
    if($(this).hasClass("selected")){
      return false;
    }else if($(".label-selected").children("li").length >= 5){
    	alert("最多可以选择5个哦");
      return false;
    }
    $(".label-selected").append(labelHTML);
    val = '';
    for(var i = 0; i < $(".label-selected").children("li").length; i++){
      val += $(".label-selected").children("li").eq(i).attr("data")+',';
    }
    $("input[name='label']").val(val);
    $(this).addClass("selected");
  });
  var val = "";
  $(".label-selected").on("click","li",function(){
    var id = $(this).attr("data");
    val = '';
    $(this).remove();
    for(var i = 0; i < $(".label-selected").children("li").length; i++){
      val += $(".label-selected").children("li").eq(i).attr("data")+',';
    }
    $("input[name='label']").val(val);
    $(".label-item").find("li[data='"+id+"']").removeClass("selected");
  });

$(".replacelable").on("click",function(){
		var lable1=$(".label1");
		var lable2=$(".lable2");
		
		if(lable1.hasClass("show")){
				lable1.removeClass("show").addClass("hide");
				lable2.removeClass("hide").addClass("show");
		}
		else{
				lable2.removeClass("show").addClass("hide");
				lable1.removeClass("hide").addClass("show");
		}
		
})

////获取标签
//$("#cell").on("click",function(){
//	if($("input[name='label']").val() == ""){
//		return false;
//	}else{
//		layer.msg("标签内容为："+$("input[name='label']").val());
//	}
//})
})