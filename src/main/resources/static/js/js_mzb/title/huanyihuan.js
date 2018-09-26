		var changeindex=1;
		var clickindex=2;
		//index 相当于 for中的i，从 0 开始
		// element 相当于当前元素
		$(".changeone li").each(function(index,element){      
			if(index/4<changeindex){
                
				element.className="change"+changeindex;
			}else{
				changeindex++;
				element.className="change"+changeindex;
			}
		})
		$(".change1").siblings().css("display","none");
		$(".change1").show();
		$(".huan").click(function(){
			if(clickindex<=changeindex){
				$(".change"+clickindex).siblings().css("display","none");
				$(".change"+clickindex).show();
				clickindex++;
			}else{
				clickindex=1;
				$(".change"+clickindex).siblings().css("display","none");
				$(".change"+clickindex).show();
			}

		});