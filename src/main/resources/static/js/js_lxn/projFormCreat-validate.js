//以下为修改jQuery Validation插件兼容Bootstrap的方法，没有直接写在插件中是为了便于插件升级
$.validator.setDefaults({
	highlight: function(element) {
		$(element).closest('.form-group').removeClass('has-success').addClass('has-error');
	},
	success: function(element) {
		element.closest('.form-group').removeClass('has-error').addClass('has-success');
	},
	errorElement: "span",
	errorPlacement: function(error, element) {
		if(element.is(":radio") || element.is(":checkbox")) {
			error.appendTo(element.parent().parent().parent());
		}else if(element.is(":file")){
			error.appendTo(element.parent().parent().parent().parent());
		}else if(element.is("textarea")){
			error.appendTo(element.parent());
		}else {
			error.appendTo(element.parent().parent());
		}
	},
	errorClass: "help-block m-b-none",
	validClass: "help-block m-b-none"

});


$(function() {
	jQuery.validator.addMethod("filelength", function(value, element) {
		var length = element.files.length;
		if(length == 0) {
			return false;
		} else {
			return true;
		}
	}, "请插入图片");
	
	jQuery.validator.addMethod("paypwdlength",function (value,element) {
		var length=value.length;
		return this.optional(element)||(length==6);
	},"请输入6位支付密码");
	
//	jQuery.validator.addMethod("tagnull",function (value,element) {
////		var length=$(".label-selected").children("li").length;
//		var length=element.parent().children("li").length; 
//		if(length==0){
//			return false;
//		}else{
//			return true;
//		}		
//	},"请至少选择1个标签");
})

//以下为官方示例
$().ready(function() {
	// validate the comment form when it is submitted
	$("#commentForm").validate();

	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules: {
			firstname: "required",
			lastname: "required",
			proj_name: {
				required: true,
				minlength: 2
			},
			proj_type: {
				required: true,
			},
			proj_money: {
				required: true
			},
			proj_cycletime: {
				required: true,
				digits: true,
				range: [1, 9999]
			},
			proj_intro: {
				required: true,
				minlength: 50,
				maxlength: 500
			},
			oldpwd: {
				required: true,
				minlength: 6
			},
			newpwd: {
				required: true,
				minlength: 6
			},
			againpwd: {
				required: true,
				equalTo: "#newpwd"
			},
			oldpaypwd: {
				required: true,
				paypwdlength:true
			},
			newpaypwd: {
				required: true,
				paypwdlength:true
			},
			againpaypwd: {
				required: true,
				equalTo: "#newpaypwd"
			},
			img: {
				filelength: true,
			},
			agree: "required"
		},
		messages: {
			firstname: icon + "请输入你的姓",
			lastname: icon + "请输入您的名字",
			proj_name: {
				required: icon + "请输入您的项目名",
				minlength: icon + "项目名必须2个字符以上"
			},
			proj_type: {
				required: icon + "请选择您的项目类型"
			},
			proj_money: {
				required: icon + "请输入项目的费用",
			},
			proj_cycletime: {
				required: icon + "请输入项目开发周期",
				digits: icon + "您输入的数额不是整数，请重新输入",
				range: icon + "您输入的时间不在范围内"
			},
			proj_intro: {
				required: icon + "请输入项目详细描述",
				minlength: icon + "您输入的字数少于50个字，请重新输入",
				maxlength: icon + "您输入的字数多于500个字，请重新输入"
			},
			agree: {
				required: icon + "必须同意协议后才能修改",
				element: '#agree-error'
			},
			oldpwd: {
				required: icon + "当前登录密码不能为空，请重新输入",
				minlength: icon + "您输入的密码长度少于6，请重新输入"
			},
			newpwd: {
				required: icon + "新密码不能为空，请重新输入",
				minlength: icon + "您输入的密码长度少于6，请重新输入"
			},
			againpwd: {
				required: icon + "确认密码不能为空，请重新输入",
				equalTo: icon + "两次密码输入不一致"
			},
			oldpaypwd: {
				required: icon + "当前支付密码不能为空，请重新输入",
				paypwdlength: icon + "请输入您当前6位支付密码"
			},
			newpaypwd: {
				required: icon + "新密码不能为空，请重新输入",
				paypwdlength: icon + "请输入6位新密码"
			},
			img: {
				filelength: icon + "项目封面不能为空",
			},
			againpaypwd: {
				required: icon + "确认密码不能为空，请重新输入",
				equalTo: icon + "两次密码输入不一致"
			},
		}
	});

	// propose username by combining first- and lastname
	$("#username").focus(function() {
		var firstname = $("#firstname").val();
		var lastname = $("#lastname").val();
		if(firstname && lastname && !this.value) {
			this.value = firstname + "." + lastname;
		}
	});
});