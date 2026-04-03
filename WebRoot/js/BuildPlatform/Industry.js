
$(document).ready(function() {
	loaded();
	
});


function loaded() {
	url = basePath + "ea/WfjIndustryPlatform/sajax_ea_getIndustry.jspa?";
	$.ajax({
		url: url,
		type: "post",
		async: false,
		dateType: "json",
		success: function(data) {
			var member = eval("(" + data + ")");
			var industryList = member.industryList;
			$(".last").removeClass("last");
			var htl = new Array();
			
			if (industryList != null) {
				for (var i = 0; i < industryList.length; i++) {
					var pp = industryList[i];
					
					htl.push( '<ul onclick="dj(\''+pp.codeValue+'\')">');
					htl.push( '<li > ');
					htl.push('<div class="left"><img src="'+basePath+'images/BuildPlatform/ico-L.png" alt=""></div>');
					htl.push('<div class="right" >');
					htl.push(' <p>' + pp.codeValue + '经济平台</p>');
					htl.push('</div></li></ul>');
				}	
			}
			$(".gjpt_zzy").append(htl.join(""));
			
		},
		error: function(data) {
			alert("获取项目失败")
		}
	})
}
