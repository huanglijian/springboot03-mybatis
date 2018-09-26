	    var form = $('#jobForm');
	    $(document).ready(function () {
	
	        form.bootstrapValidator({
	            message: '输入值不合法',
	            feedbackIcons: {
	                valid: 'glyphicon glyphicon-ok',
	                invalid: 'glyphicon glyphicon-remove',
	                validating: 'glyphicon glyphicon-refresh'
	            },
	            fields: {
	                jobName: {
	                    message: '招聘岗位不合法',
	                    validators: {
	                        notEmpty: {
	                            message: '招聘岗位不能为空'
	                        },
	                        stringLength: {
	                            min: 3,
	                            max: 15,
	                            message: '请输入3到30个字符'
	                        }

	                    }
	                }
	                , jobNum: {
	                    validators: {
	                        notEmpty: {
	                            message: '招聘人数不能为空'
	                        }
	                    }
	                }, jobRange: {
	                    validators: {
	                        notEmpty: {
	                            message: '薪资范围不能为空'
	                        }
	                }
	               },     jobDemand: {
	                    validators: {
	                        notEmpty: {
	                            message: '职位要求不能为空'
	                        }, stringLength: {
	                            min: 50,
	                            max: 500,
	                            message: '请输入50到500个字符'
	                        }
	                    }
	                }, jobIntro: {
	                    validators: {
	                        notEmpty: {
	                            message: '职位简介不能为空'
	                        }, stringLength: {
	                            min: 50,
	                            max: 500,
	                            message: '请输入50到500个字符'
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