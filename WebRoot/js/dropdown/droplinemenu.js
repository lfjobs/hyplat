(function($){
	$.fn.extend({
		droplinemenu: function(configs) {
			var configs = $.extend({				
				over: 200,
				out: 100, 
				rightdown:basePath+'css/dropdown/down.gif' 
			},configs||{});
			this.find(">ul").addClass("dropmenu");
			this.find("ul li:has(ul)").addClass('hasmenu').find(">a").append("<img class='downarrowclass' src='"+configs.rightdown+"'>");
			var currentobj;
			return $('li.hasmenu').hover(function(){	
				if ($.browser.msie) {
					$(this).parent("ul").css({'overflow': 'visible'}); 
				}
				$(this).find(">ul").stop(true, true).css('top',$(this).height()).slideDown(configs.over);
			},function(){ 
				$(this).find(">ul").stop(true, true).slideUp(configs.out);
			});
		}
	});
})(jQuery);
 

