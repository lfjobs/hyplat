$(document).ready(function(){
		var token=0;
		$("div.na_back_dh").click(function(event){
			if(this.getAttribute('onclick')==null||this.getAttribute('onclick')=="")return;
				if(token){
					return;
				}
				token=1;
				var hrefSrc="window.parent.document.getElementById('rightFrame').contentWindow."+this.getAttribute('onclick').replace(/[ ]/g,"");
				
				var elementA=$("<a onclick="+hrefSrc+"></a>").attr("href","javascript:;").text($(this).next(".center_a").text())	
				$("#navigationRecords ul:first",$(window.parent.document.getElementById("daohang").contentWindow.document)).append($("<li/>").append(elementA));
				$("#navigationRecords ul:first",$(window.parent.document.getElementById("daohang").contentWindow.document)).append("<li><a href='#'>&gt;</a></li>");
		}); 
		$("div.na_back_img").click(function(event){
			if(this.getAttribute('onclick')==null||this.getAttribute('onclick')=="")return;
				if(token){
					return;
				}
				token=1;
				var hrefSrc="window.parent.document.getElementById('rightFrame').contentWindow."+this.getAttribute('onclick').replace(/[ ]/g,"");
				
				var elementA=$("<a onclick="+hrefSrc+"></a>").attr("href","javascript:;").text($(this).next(".center_a").text())	
				$("#navigationRecords ul:first",$(window.parent.document.getElementById("daohang").contentWindow.document)).append($("<li/>").append(elementA));
				$("#navigationRecords ul:first",$(window.parent.document.getElementById("daohang").contentWindow.document)).append("<li><a href='#'>&gt;</a></li>");
		}); 
});
window.history.forward(1);
/**var pageLocal=window.location;
		$("#navigationRecords ul:first",$(window.parent.document.getElementById("daohang").contentWindow.document)).children().each(function(){
			var navigationLocal=$(this).find("a")[0].getAttribute('onclick');
			if((navigationLocal!=null?navigationLocal.indexOf(pageLocal):-1)!=-1){
				$(this).next().nextAll().remove(); 
		}
});*/