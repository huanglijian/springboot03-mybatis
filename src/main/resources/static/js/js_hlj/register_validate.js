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
			con_password: {
				required: true,
				minlength: 6,
				equalTo: "#password"
			},
			agree: "required"
		},
		messages: {
			email: {
				required: icon + "邮箱不能为空，请重新输入",
				email: icon + "邮箱格式错误，请重新输入",
			},
			code: {
				required: icon + "验证码不能为空，请重新输入",
			},
			name: {
				required: icon + "用户名不能为空，请重新输入",
				minlength: icon + "用户名必须2个字符以上",
			},
			mobile: {
				required: icon + "请输入您的联系电话",
                minlength: icon + "请填写11位的手机号",
                maxlength: icon + "*请填写11位的手机号",
                isphoneNum: icon + "请填写正确的手机号码"
			},
			pay: {
				required: icon + "请输入您的支付宝账号",
				isZhifubao: icon + "请输入正确的支付宝账号(手机号/邮箱)"
			},
			password: {
				required: icon + "请输入您的密码",
				minlength: icon + "密码必须6个字符以上"
			},
			con_password: {
				required: icon + "请再次输入密码",
				minlength: icon + "密码必须6个字符以上",
				equalTo: icon + "两次输入的密码不一致"
			},
			agree: {
				required: icon + "必须同意协议后才能修改",
				element: '#agree-error'
			}
		}
	});
});

$().ready(function() {

	var icon = "<p class='fa fa-times-circle'></p> ";
	
	
	$("#signupForm").validate({
		rules: {
			email: {
				required: true,
				email: true,
			},
			
			
			agree: "required"
		},
		
		messages: {
			email: {
				required: icon + "邮箱不能为空，请重新输入",
				email: icon + "邮箱格式错误，请重新输入",
			}
		}
	});
});