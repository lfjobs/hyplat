$(document).ready(function() {
	var len = $("#tbwid").find(".trclass").length;
	parent.document.getElementById("mainframe11").style.height = 80 + len * 27 + "px";
	$('.contact').flexigrid({
				height : 'auto',
				width : 'auto',
				minwidth : 30,
				minheight : 80,
				buttons : [{
					name : '归档信息展示',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
	}
});