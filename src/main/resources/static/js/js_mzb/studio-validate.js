
	    var form = $('#studioForm');
	    $(document).ready(function () {
	
	        form.bootstrapValidator({
	            message: '输入值不合法',
	            feedbackIcons: {
	                valid: 'glyphicon glyphicon-ok',
	                invalid: 'glyphicon glyphicon-remove',
	                validating: 'glyphicon glyphicon-refresh'
	            },
	            fields: {
	                stuName: {
	                    message: '工作室名称不合法',
	                    validators: {
	                        notEmpty: {
	                            message: '工作室名称不能为空'
	                        },
	                        stringLength: {
	                            min: 4,
	                            max: 7,
	                            message: '请输入4到7个字符'
	                        }

	                    }
	                }
                     ,
                    stuTag: {
	                    validators: {
	                        notEmpty: {
	                            message: '技能标签不能为空'
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
	                        $("#returnMessage").hide().html('<label class="label label-danger">失败!</label>').show(500);
	                    }
	                }, error: function () {
	                    $("#returnMessage").hide().html('<label class="label label-danger">失败!</label>').show(500);
	                }
	            })
	        }
	    });