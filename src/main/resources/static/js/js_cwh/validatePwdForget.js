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
    jQuery.validator.addMethod("passwordConfirm",function (value,element) {
        var newPwd = $("#newPwd").val();
        return this.optional(element)||(newPwd === value);
    },"请再次输入密码");
})

//以下为官方示例
$().ready(function() {
    var icon = "<p class='fa fa-times-circle'></p> ";
    $("#pwdforgetform").validate({
        rules: {
            password: {
                required: true,
                minlength: 6
            },
            passwordConfirm:{
                required:true,
                passwordConfirm: true,
            }
        },
        messages: {
            password: {
                required: icon + "请输入您的登陆密码",
                minlength: icon + "密码必须6个字符以上"
            },
            passwordConfirm: {
                required: icon + "请再次输入密码",
                passwordConfirm: icon + "两次输入的密码不同",
            },
        }
    });
});