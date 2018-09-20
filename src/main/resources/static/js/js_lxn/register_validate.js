//以下为修改jQuery Validation插件兼容Bootstrap的方法，没有直接写在插件中是为了便于插件升级
$.validator.setDefaults({
	highlight: function(element) {
		$(element).closest('.form-group').removeClass('has-success').addClass('has-error');
		$(element).addClass("has-error");
	},
	success: function(element) {
		element.closest('.form-group').removeClass('has-error').addClass('has-success');
		element.addClass("has-success");
	},
	errorElement: "span",
	errorPlacement: function(error, element) {
		if(element.is(":radio") || element.is(":checkbox")) {
			error.appendTo(element.parent().parent().parent());
		} else {
			error.appendTo(element.parent().parent());
		}

	},
	errorClass: "help-block m-b-none",
	validClass: "help-block m-b-none"

});

$(function() {
	jQuery.validator.addMethod("isphoneNum", function(value, element) {
		var length = value.length;
		var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
		return this.optional(element) || (length == 11 && mobile.test(value));
	}, "请正确填写您的手机号码");
	jQuery.validator.addMethod("isZhifubao", function(value, element) {
		var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
		var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
		return this.optional(element) || (mobile.test(value) || reg.test(value));
	}, "请正确填写您的支付宝号码");
	jQuery.validator.addMethod("paypwdlength",function (value,element) {
		var length=value.length;
		return this.optional(element)||(length==6);
	},"请输入6位支付密码");
})

//以下为官方示例
$().ready(function() {

	var icon = "<p class='fa fa-times-circle'></p> ";
	$("#signupForm").validate({
		rules: {
			email: {
				required: true,
				email: true,
			},
			code: {
				required: true,
			},
			name: {
				required: true,
				minlength: 2
			},
			phone: {
				required: true,
				minlength: 11,
				maxlength: 11,
				isphoneNum: true
			},
			pay: {
				required: true,
				isZhifubao: true,
			},
			password: {
				required: true,
				minlength: 6
			},
			paypwd: {
				required: true,
				paypwdlength:true,
				
			},
			agree: "required"
		},
		messages: {
			email: {
				required: icon + "邮箱不能为空，请重新输入",
				email: icon + "邮箱格式错误，请重新输入",
			},
			code: {
				required: icon + "验证码不能为空",
			},
			name: {
				required: icon + "用户名不能为空，请重新输入",
				minlength: icon + "用户名必须2个字符以上",
			},
			phone: {
				required: icon + "手机号码不能为空，请重新输入",
				minlength: icon + "请填写11位的手机号",
				maxlength: icon + "请填写11位的手机号",
				isphoneNum: icon + "请填写正确的手机号码"
			},
			pay: {
				required: icon + "请输入您的支付宝账号",
				isZhifubao: icon + "请输入正确的支付宝账号(手机号/邮箱)"
			},
			password: {
				required: icon + "请输入您的登陆密码",
				minlength: icon + "密码必须6个字符以上"
			},
			paypwd: {
				required: icon + "请输入您的支付密码",
				paypwdlength: icon + "支付密码必须为6位数"
				
			},
			agree: {
				required: icon + "必须同意协议后才能修改",
				element: '#agree-error'
			}
		}
	});
});