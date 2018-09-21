//以下为修改jQuery Validation插件兼容Bootstrap的方法，没有直接写在插件中是为了便于插件升级
        $.validator.setDefaults({
            highlight: function (element) {
                $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
            },
            success: function (element) {
                element.closest('.form-group').removeClass('has-error').addClass('has-success');
            },
            errorElement: "span",
            errorPlacement: function (error, element) {
                if (element.is(":radio") || element.is(":checkbox")) {
                    error.appendTo(element.parent().parent().parent());
                } else {
                    error.appendTo(element.parent());
                }
            },
            errorClass: "help-block m-b-none",
            validClass: "help-block m-b-none"


        });

        //以下为官方示例
        $().ready(function () {
            // validate the comment form when it is submitted
            $("#commentForm").validate();

            // validate signup form on keyup and submit
            var icon = "<i class='fa fa-times-circle'></i> ";
            $("#signupForm").validate({
                rules: {
                    firstname: "required",
                    lastname: "required",
                    username: {
                        required: true,
                        minlength: 2
                    },
					codename: {
						required: true,
						minlength: 2
					},
					yuanchuang_file: {
						filelength:true
					},
                    zhifubao:{
                    	required: true,
                    	isZhifubao:true,
                    },
                    mobile:{
                    	required: true,
                    	minlength: 11,
                    	maxlength:11,
                    	isphoneNum:true
                    },
					used_password: {
						required: true,
						minlength: 5
					},
					used_pay_password: {
						required: true,
						minlength: 6,
						maxlength: 6
					},
					pay_password: {
						required: true,
						minlength: 6,
						maxlength: 6
					},
					confirm_pay_password: {
						required: true,
						minlength: 6,
						maxlength: 6,
                        equalTo: "#pay_password"
					},
                    password: {
                        required: true,
                        minlength: 5
                    },
                    confirm_password: {
                        required: true,
                        minlength: 5,
                        equalTo: "#password"
                    },
                    email: {
                        required: true,
                        email: true
                    },
                    topic: {
                        required: "#newsletter:checked",
                        minlength: 2
                    },
                    agree: "required"
                },
                messages: {
                    firstname: icon + "请输入你的姓",
                    lastname: icon + "请输入您的名字",
                    username: {
                        required: icon + "请输入您的用户名",
                        minlength: icon + "用户名必须2个字符以上"
                    },
					yuanchuang_file: {
						filelength: icon + "请选择文件"
					},
					codename: {
						required: icon + "请输入您的资源名称",
						minlength: icon + "资源名必须2个字符以上"
					},
                    zhifubao: {
                    	required: icon + "请输入您的支付宝账号",
                    	isZhifubao: icon + "请输入正确的支付宝账号(手机号/邮箱)"
                    },
                    mobile: {
                    	required: icon + "请输入您的联系电话",
                        minlength: icon + "请填写11位的手机号",
                        maxlength: icon + "*请填写11位的手机号",
                    	isphoneNum: icon + "请填写正确的手机号码"
                    },
					used_password: {
						required: icon + "请输入旧密码",
						minlength: icon + "密码必须5个字符以上"
					},
					used_pay_password: {
                        required: icon + "请输入旧的支付密码",
                        minlength: icon + "密码必须为6个字符",
						maxlength: icon + "密码必须为6个字符"
                    },
					pay_password: {
						required: icon + "请输入新的支付密码",
						minlength: icon + "密码必须为6个字符",
						maxlength: icon + "密码必须为6个字符"
					},
					confirm_pay_password: {
						required: icon + "请再次输入新的支付密码",
						minlength: icon + "密码必须为6个字符",
						maxlength: icon + "密码必须为6个字符",
                        equalTo: icon + "两次输入的密码不一致"
					},
                    password: {
                        required: icon + "请输入您的密码",
                        minlength: icon + "密码必须5个字符以上"
                    },
                    confirm_password: {
                        required: icon + "请再次输入密码",
                        minlength: icon + "密码必须5个字符以上",
                        equalTo: icon + "两次输入的密码不一致"
                    },
                    email: icon + "请输入正确的的E-mail",
                    agree: {
                        required: icon + "必须同意协议后才能修改",
                        element: '#agree-error'
                    }
                }
            });

            // propose username by combining first- and lastname
            $("#username").focus(function () {
                var firstname = $("#firstname").val();
                var lastname = $("#lastname").val();
                if (firstname && lastname && !this.value) {
                    this.value = firstname + "." + lastname;
                }
            });
        });
