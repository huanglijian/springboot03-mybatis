$(function () {
	$(".more_btn").click(function () {
		if ($(".more_d").is(':hidden')) {
			$(".more_d").show();
		} else {
			$(".more_d").hide();
		}
	});
})
