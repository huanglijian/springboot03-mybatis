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
		}else {
			error.appendTo(element.parent());
		}
	},
	errorClass: "help-block m-b-none",
	validClass: "help-block m-b-none"

});


$(function() {
	jQuery.validator.addMethod("paypwdlength",function (value,element) {
		var length=value.length;
		return this.optional(element)||(length==6);
	},"请输入6位支付密码");
    jQuery.validator.addMethod("paypwdequal",function (value,element) {
        var flag=1;
        $.ajax({
            url:'/promcenter/prompayin',
            async:false,
            success:function (result) {
                if(value==result.pwd){
                    flag=1;
                }else{
                    flag=0;
                }
            }
        });
        return this.optional(element)||(flag==1);
    },"请输入正确支付密码");
})

//以下为官方示例
$().ready(function() {
	// validate the comment form when it is submitted
	$("#commentForm").validate();

	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules: {
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
				paypwdlength:true,
				paypwdequal:true,
			},
			newpaypwd: {
				required: true,
				paypwdlength:true
			},
			againpaypwd: {
				required: true,
				equalTo: "#newpaypwd"
			}
		},
		messages: {			
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
				paypwdlength: icon + "请输入您当前6位支付密码",
                paypwdequal:icon+"您的支付密码输入错误，请重新输入",
			},
			newpaypwd: {
				required: icon + "新密码不能为空，请重新输入",
				paypwdlength: icon + "请输入6位新密码"
			},
			againpaypwd: {
				required: icon + "确认密码不能为空，请重新输入",
				equalTo: icon + "两次密码输入不一致"
			}
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