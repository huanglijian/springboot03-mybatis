
	    var form = $('#bidForm');
	    $(document).ready(function () {
	
	        form.bootstrapValidator({
	            message: '输入值不合法',
	            feedbackIcons: {
	                valid: 'glyphicon glyphicon-ok',
	                invalid: 'glyphicon glyphicon-remove',
	                validating: 'glyphicon glyphicon-refresh'
	            },
	            fields: {
	                proMoney: {
	                    
	                    validators: {
	                        notEmpty: {
	                            message: '项目报价不能为空'
	                        },
	                        stringLength: {
	                            min: 0,
	                            max: 99999999,
	                            message: '请输入至少一个字符'
	                        }
/*	                        regexp: {
	                            regexp: /^[a-zA-Z0-9_\. \u4e00-\u9fa5 ]+$/,
	                            message: '用户名只能由字母、数字、点、下划线和汉字组成 '
	                        }*/
	                    }
	                },
	                proCycletime: {
	                    validators: {
	                        notEmpty: {
	                            message: '开发周期不能为空'
	                        },
	                        stringLength: {
	                            min: 0,
	                            max: 99999999,
	                            message: '请输入至少一个字符'
	                        }
	                    }
	                }
                          
	            }
	        });
	    });
	    
	    
	    
	    $("#submitBtn").click(function () {
	        //进行表单验证
	        var bv = form.data('bootstrapValidator');
	        bv.validate();
	        if (bv.isValid()) {
	            console.log(form.serialize());
	            //发送ajax请求
	            $.ajax({
	                url: 'validator.json',
	                async: false,//同步，会阻塞操作
	                type: 'GET',//PUT DELETE POST
	                data: form.serialize(),
	                complete: function (msg) {
	                    console.log('完成了');
	                },
	                success: function (result) {
	                    console.log(result);
	                    if (result) {
	                        window.location.reload();
	                    } else {
	                        $("#returnMessage").hide().html('<label class="label label-danger">修改失败!</label>').show(500);
	                    }
	                }, error: function () {
	                    $("#returnMessage").hide().html('<label class="label label-danger">修改失败!</label>').show(500);
	                }
	            })
	        }
	    });