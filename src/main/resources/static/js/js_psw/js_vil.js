$(function () {
	jQuery.validator.addMethod("isphoneNum", function (value, element) {
		var length = value.length;
		var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
		return this.optional(element) || (length == 11 && mobile.test(value));
	}, "请正确填写您的手机号码");
	jQuery.validator.addMethod("isZhifubao", function (value, element) {
		var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
		var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
		return this.optional(element) || (mobile.test(value) || reg.test(value));
	}, "请正确填写您的手机号码");
	jQuery.validator.addMethod("filelength", function(value, element) {
			var length = element.files.length;
			if(length==0){
				return false;
			}else{
				return true;
			}
	},);
})
