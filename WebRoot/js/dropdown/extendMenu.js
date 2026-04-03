$(document).ready(function() {
		
		window.parent.document.getElementById("mainContent").rows="60,*";		 
		$('li.hasmenu').hover(function(){	
			 setTimeout(function(){ 
				 var divHeight=$("#mydroplinemenu ul:visible").length>=2?$("#mydroplinemenu ul:visible").length:2;
				 window.parent.document.getElementById("mainContent").rows=""+(30*divHeight)+",*";
		 	},100);
		},function(){   
			 setTimeout(function(){ 
				 var divHeight=$("#mydroplinemenu ul:visible").length>=2?$("#mydroplinemenu ul:visible").length:2;
				 window.parent.document.getElementById("mainContent").rows=""+(30*divHeight)+",*";
		 	},100);
		});
		$("a",$("div#mydroplinemenu")).bind("click",function(){
			if(this.getAttribute('onclick')==null)return;
			$("div#navigationRecords ul li:nth-child(2)").nextAll().remove();
			var eventThis=$(this);
			do{
				var addClassValue=eventThis.parent().parent().hasClass("zero")?"zero":eventThis.parent().parent().attr("class");
				$("div#navigationRecords ul li:nth-child(2)").after("<li><a href='#'>&gt;</a></li>");
				$("div#navigationRecords ul li:nth-child(2)").after($("<li/>").append(eventThis.clone().empty().text(eventThis.text())).addClass(addClassValue));
				eventThis=eventThis.parent().parent().parent().find(">a");
			}while(eventThis[0]!=undefined)
		});
		$("a",$("div#navigationRecords")).live('click', function() {
			if(this.getAttribute('onclick')==null)return;
			var hrefSrc="window.parent.document.getElementById('rightFrame').contentWindow."+this.getAttribute('onclick');
			eval(hrefSrc);
			$(this).parent().next().nextAll().remove();
		});
});
//document.oncontextmenu=function(e){return false;};		
		