$(function(){
	$(".topa1").on("click",function(){
		$(".topa1").removeClass("show").addClass("hide");
		$(".topa2").removeClass("hide").addClass("show");
		$(".top_detail").removeClass("hide").addClass("show");
	});

	$(".topa2").on("click",function(){
		$(".topa2").removeClass("show").addClass("hide");
		$(".topa1").removeClass("hide").addClass("show");
		$(".top_detail").removeClass("show").addClass("hide");
	})
})




