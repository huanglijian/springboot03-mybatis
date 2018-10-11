	    $(function(){
	      var mySelect= $("#mySelect").mySelect({
	          mult:true,//true为多选,false为单选
	          option:[//选项数据
	              {label:"网站开发",value:"网站开发"},
	              {label:"桌面应用",value:"桌面应用"},
	              {label:"APP",value:"APP"},
	              {label:"UI设计",value:"UI设计"},
	              {label:"数据采集与分析",value:"数据采集与分析"},
	              {label:"嵌入式与智能硬件",value:"嵌入式与智能硬件"},
	              {label:"微信开发",value:"微信开发"},
	              {label:"管理系统",value:"管理系统"},
	              {label:"其它分类项目",value:"其它分类项目"}
	          ],
	          onChange:function(res){	//选择框值变化返回结果
	            console.log(res)
	          }
	      });
	      
	      /*mySelect.setResult(["1","2"]);*/
	      
	
	    })