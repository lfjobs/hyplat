$(document).ready(function(){
	var PageIndex = $("#PageIndex").text();
	var photo = "";
	if(PageIndex=="")
	{
		/*$.ajax({
			url:basePath + "ea/mobileelectricity/ea_getListForPage.jspa?pageNumber="+pageNumber,
			dataType:"json",  
			asnyc:false,
			success:function(data){
				var str = "";
				if(data!=null)
				{
					var pageCount = data.pageCount;
					var Data = data.list;
					for(var i in Data){ 
						str+="<li><div class='in'>用户名："+Data[i].username+"</div></li><li><div class='in'>购电量："+Data[i].amount+"</div></li>";
						str+="<li><div class='in'>实际用量："+Data[i].usedamount+"</div></li><li><div class='in'>核对人："+Data[i].examiner+"</div></li>";
					}
					$("#result").html(str);
					InitPager(pageCount, 1);
				}
				else
				{
					str+="<span>没有数据</span>";
					$("#result").html(str);
				}
			},
			error:function(data){
				
			}
		});*/
	}
	(function($) {
		//设定页码方法，初始化
		$.fn.setPager = function(options) {
			var opts = $.extend({}, pagerDefaults, options);
			return this.each(function() {
				$(this).empty().append(setPagerHtml(parseInt(options.RecordCount), parseInt(options.PageIndex), options.buttonClick));
			});
		};
    	//设定页数及html
    	function setPagerHtml(RecordCount, PageIndex, pagerClick) {
			var $content = $("<ul class=\"pages\"></ul>");
			var startPageIndex = 1;
        	//若页码超出
        	if (RecordCount <= 0) RecordCount = pagerDefaults.PageSize;
        	//末页
        	var endPageIndex = parseInt(RecordCount % parseInt(pagerDefaults.PageSize)) > 0 ? 
				parseInt(RecordCount / parseInt(pagerDefaults.PageSize)) + 1 : RecordCount / parseInt(pagerDefaults.PageSize)
        	if (PageIndex > endPageIndex) PageIndex = endPageIndex;
        	if (PageIndex <= 0) PageIndex = startPageIndex;
        	var nextPageIndex = PageIndex + 1;
        	var prevPageIndex = PageIndex - 1;
        	if (PageIndex == startPageIndex) {
				$content.append($("<li><span>首页</span></li>"));
				$content.append($("<li><span>上一页</span></li>"));
        	} else {
            	$content.append(renderButton(RecordCount, 1, pagerClick, "首页"));
            	$content.append(renderButton(RecordCount, prevPageIndex, pagerClick, "上一页"));
        	}
        	//这里判断是否显示页码
        	if (pagerDefaults.ShowPageNumber) {
				$content.append($('<li><span>第' + PageIndex + '页 / 共' + endPageIndex + '页</li></span>'));
			}
    	    if (PageIndex == endPageIndex) {
				$content.append($("<li><span>下一页</span></li>"));
    	        $content.append($("<li><span>末页</span></li>"));
			} else {
    	        $content.append(renderButton(RecordCount, nextPageIndex, pagerClick, "下一页"));
        	    $content.append(renderButton(RecordCount, endPageIndex, pagerClick, "末页"));
        	}
        	return $content;
    	}
		function renderButton(recordCount, goPageIndex, EventHander, text) {
        	var $goto = $("<li><a title=\"第" + goPageIndex + "页\">" + text + "</a></li>\"");
			$("#PageIndex").text(goPageIndex);
        	$goto.click(function() {
        		$.ajax({
					url:basePath + "ea/mobileelectricity/ea_getListForPage.jspa?pageNumber="+goPageIndex+"&search=search",
					dataType:"json",  
					asnyc:false,
					success:function(data){
						var str = "";
						if(data!=null)
						{
							var pageCount = data.pageCount;
							var Data = data.list;
							for(var i in Data){ 
								str+="<li><div class='in'>用户名："+Data[i].username+"</div></li><li><div class='in'>购电量："+Data[i].amount+"</div></li>";
								str+="<li><div class='in'>实际用量："+Data[i].usedamount+"</div></li><li><div class='in'>核对人："+Data[i].examiner+"</div></li>";
							}
							$("#result").html(str);
							InitPager(pageCount, 1);
							EventHander(recordCount, goPageIndex);
						}
						else
						{
							str+="<span>没有数据</span>";
							$("#result").html(str);
						}
					},
					error:function(data){
			
					}
				});
			});
			return $goto;
		}
		$("#tosearch").click(function(){
			var options = {
				url:basePath + "ea/mobileelectricity/ea_toSearch.jspa?pageNumber="+pageNumber,
				dataType: "json",
				asnyc: false,
				success: function(data){
					var str = "";
					if(data!=null)
					{
						var pageCount = data.pageCount;
						var Data = data.list;
						for (var i in Data) {
							if (Data[i].dimissionDate != null) {
								dimissionDate = Data[i].dimissionDate;
							}
							if (Data[i].birthday != null) {
								birthday = Data[i].birthday;
							}
							if (Data[i].verifyTime != null) {
								verifyTime = Data[i].verifyTime;
							}
							str+="<li><div class='in'>用户名："+Data[i].username+"</div></li><li><div class='in'>购电量："+Data[i].amount+"</div></li>";
							str+="<li><div class='in'>实际用量："+Data[i].usedamount+"</div></li><li><div class='in'>核对人："+Data[i].examiner+"</div></li>";
						}
						$("#result").html(str);
						InitPager(pageCount, 1);	
					}
					else
					{
						str+="你所查询的数据不存在!!!";
						$("#result").html(str);
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown){
					alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus);
				}
			}	
			$("#postSearchForm").ajaxSubmit(options); 
			$("#jqModelSearch").css('display','none');
			return false;
		});
    	var pagerDefaults = {
        	DefaultPageCount: 1,
        	DefaultPageIndex: 1,
        	PageSize: 1,
    	    ShowPageNumber: true //是否显示页码
    	};
	})(jQuery);
});