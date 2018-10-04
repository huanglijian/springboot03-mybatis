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
    jQuery.validator.addMethod("payoutnumtrue",function (value,element) {
        var flag=1;
        $.ajax({
            url:'/promcenter/prompayin',
            async:false,
            success:function (result) {
                if(value > result.money){
                    flag=0;
                }else{
                    flag=1;
                }
            }
        });
        return this.optional(element)||(flag==1&&value>0);
    },"提现金额不能超过现有账户余额");
})
//以下为官方示例
$().ready(function() {
	var icon = "<p class='fa fa-times-circle'></p> ";
	$("#signupForm").validate({
			rules: {
				paynumber: {
					required: true,
					number:true,
				},
                payoutnumber: {
                    required: true,
                    number:true,
                    payoutnumtrue:true,
                },
                paypwd:{
                    required: true,
                    paypwdlength:true,
                    paypwdequal:true,
                },
			},
			messages: {
				paynumber: {
					required: icon + "充值金额不能为空，请重新输入",
					number:icon + "请输入有效数字",
				},
                payoutnumber: {
                    required: icon + "充值金额不能为空，请重新输入",
                    number:icon + "请输入有效数字",
                    payoutnumtrue:icon + "提现金额不能超过现有账户余额",
                },
                paypwd:{
                    required: icon + "支付密码不能为空，请重新输入",
                    paypwdlength:icon+"请输入6位支付密码",
                    paypwdequal:icon+"支付密码错误，请重新输入",
                },
			}
	});
});