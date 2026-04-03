$(document).ready(function() {
	$("input.inputtext1").blur(function() {
		var option=$(this).val();
		if(option.length<1){
			var str=$(this).parents("tr").find("td").eq(0).text();
			str=str.substr(0,str.length-1);
			alert(str+"不能为空");
		}
	});
});