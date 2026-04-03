	//弹出窗口
	function pop(id){
		//将窗口居中
		makeCenter(id);

	}
	//隐藏窗口
	function hide(id)
	{
		$('#'+id).css("display","none");
	}




	function makeCenter(id)
	{
		$('#'+id).css("display","block");
		$('#'+id).css("position","absolute");
		$('#'+id).css("top", Math.max(0, (($(window).height() - $('#'+id).outerHeight()) / 2) + $(window).scrollTop()) + "px");
		$('#'+id).css("left", Math.max(0, (($(window).width() - $('#'+id).outerWidth()) / 2) + $(window).scrollLeft()) + "px");
	}