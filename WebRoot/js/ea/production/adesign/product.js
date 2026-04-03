// JavaScript Document

$(function() {

	var s = function() {
		$(this).toggleClass("xuanzhong");
	};

	// 添加1
	$("#add1").click(
			function() {
				var size = $(".size_con input").val();
				var id = "label_" + new Date().getTime();

				if (size == "") {
					$(".alert").css("display", "block");
					alert("亲，还没有填写尺码哟!");
					return;
				} else {
					var bool = 0 ;
				    $(".size_con").find("label").each(function(){
				    	if($(this).text()==size){
				    		bool=1;
				    		return;
				    	}

					});
				    if(bool==1){
				    	alert("添加内容不可重复");
				    	return;
				    }
					
					
					$(this).parents(".size_con").find(".tianjia").before("<label id='" + id + "' class='xuanzhong'>" + size + "</label>");
					$(this).parents(".size_con").find(".tianjia").val("");
					$("#" + id).click(s);
				}

			});

	// 添加2
	$("#add2")
			.click(
					function() {
						var color_text = $("#color .tianjia").val();
						var color_img = $("#color img").attr("src");
						var color_yid = $("#color img").attr("id");
						var color_id = "input_" + new Date().getTime();

						if (color_text == "") {
							alert("亲，还没有填写颜色哟！");
							return;
						} else {
							if (color_yid == "add_img")

							{
								alert("亲，还没有选择图片哟！");
							} else {
								
								var bool = 0 ;
								$(".color_con input[type='text']").each(function(){
							    	if($(this).attr("readonly")==true){
							    		if($(this).val()==color_text){
								    		bool=1;
								    		return;
								    	}
							    	}
							    	

								});
							    if(bool==1){
							    	alert("添加内容不可重复");
							    	return;
							    }
								
								
								
								var $add = $("#color").clone(true);
						        var $fore = $(".style_list li:nth-last-of-type(1)");
						        $fore.attr("id", "");
						        $fore.find(".tianjia").attr("readonly", "readonly")
								.attr("id", color_id).addClass("xuanzhong")
								.removeClass("tianjia");
								$("#" + color_id).click(s);
								
								$fore.after($add);
								
								
								$add.find("img").attr("src",basePath+"images/WFJClient/add.png").attr("id","add_img");
								$add.find(".tianjia").val("");
							
								

							}

						}

					});

	// 选中
	$(".size_con label").click(s);

	$(".style_list li .xuanzhong").not(".tianjia").click(s);

	// 警告框
	$(".alert .alert_bg").click(function() {
		$(".alert_con button").css({
			"border" : "#3399ff solid 1px"
		});
	});
	// 取消警告框
	$(".alert_con").find("button").click(function() {

		$(".alert").css("display", "none");
		$(".alert_con button").css({
			"border" : "#a1a1a1 solid 1px"
		});
	});
	
	
	//
	$(".color_con .style_list").find("img").live("click",function(){
		$(this).parent().find(".btn_file").trigger("click");
		
	});
	
	
	
	
	//选择图片，并显示
	$('input[type=file]').change(function(){
		var obj = $(this);
	    var file=this.files[0];
	    var type = file.type;
	    if(type.indexOf("image")==-1){
	    	alert("只能上传图片");
	    	return;
	    }

	    var reader=new FileReader();
	    reader.onload=function(){
	        var url=reader.result;
	        setImageURL(url,obj);
	    };
	    reader.readAsDataURL(file);
	});

	function setImageURL(url,obj){
		 var image=new Image();
	     image.src=url;
	     var $img = $(image);
	     $(obj).parent().find("img").attr("src",$img.attr("src")).attr("id","").attr("class","sp");
	     $(obj).parents("li").find(".imgurl").attr("src",$img.attr("src"));

	}

});
