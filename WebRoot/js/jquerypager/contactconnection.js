$(document).ready(function(){
	var PageIndex = $("#PageIndex").text();
	if(PageIndex=="")
	{
		/*$.ajax({
			url:basePath + "ea/mobilecontactConnection/ea_getListContactConnection.jspa?pageNumber="+pageNumber,
			dataType:"json",  
			asnyc:false,
			success:function(data){
				var str = "";
				if(data!=null)
				{
					var pageCount = data.pageCount;
					var Data = data.list;
					$("#result").html("");
					var str = "";
					for(var i in Data){ 
						str+="<li><div class='in'>单位名称："+Data[i].companyName+"</div></li><li><div class='in'>单位往来关系："+Data[i].contactConnections+"</div></li>";
						str+="<li><div class='in'>单位地址："+Data[i].companyAddr+"</div></li><li><div class='in'>单位电话："+Data[i].companyTel+"</div></li>";
						str+="<li><div class='in'>单位负责人："+Data[i].cresponsible+"</div></li><li><div class='in'>单位负责人电话："+Data[i].responsibleTel+"</div></li>";
						str+="<li><div class='in'>行业类别："+Data[i].industryType+"</div></li><li><div class='in'>单位备注："+Data[i].remark+"</div></li>";
					}
					$("#result").html(str);
					InitPager(pageCount, 1);
				}
				else
				{
					str+="<span>没有数据</span>";
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
					url:basePath + "ea/mobilecontactConnection/ea_getListContactConnection.jspa?pageNumber="+goPageIndex+"&search=search",
					dataType:"json",  
					asnyc:false,
					success:function(data){
						var str = "";
						if(data!=null)
						{
							var pageCount = data.pageCount;
							var Data = data.list;
							$("#result").html("");
							var str = "";
							for(var i in Data){ 
								str+="<li><div class='in'>单位名称："+Data[i].companyName+"</div></li><li><div class='in'>单位往来关系："+Data[i].contactConnections+"</div></li>";
								str+="<li><div class='in'>单位地址："+Data[i].companyAddr+"</div></li><li><div class='in'>单位电话："+Data[i].companyTel+"</div></li>";
								str+="<li><div class='in'>单位负责人："+Data[i].cresponsible+"</div></li><li><div class='in'>单位负责人电话："+Data[i].responsibleTel+"</div></li>";
								str+="<li><div class='in'>行业类别："+Data[i].industryType+"</div></li><li><div class='in'>单位备注："+Data[i].remark+"</div></li>";
							}
							$("#result").html(str);
							InitPager(pageCount, 1);
							EventHander(recordCount, goPageIndex);
						}
						else
						{
							str+="<span>没有数据</span>";
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
				url: basePath + "ea/mobilecontactConnection/ea_toSearch.jspa?pageNumber=" + pageNumber,
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
							str+="<li><div class='in'>单位名称："+Data[i].companyName+"</div></li><li><div class='in'>单位往来关系："+Data[i].contactConnections+"</div></li>";
							str+="<li><div class='in'>单位地址："+Data[i].companyAddr+"</div></li><li><div class='in'>单位电话："+Data[i].companyTel+"</div></li>";
							str+="<li><div class='in'>单位负责人："+Data[i].cresponsible+"</div></li><li><div class='in'>单位负责人电话："+Data[i].responsibleTel+"</div></li>";
							str+="<li><div class='in'>行业类别："+Data[i].industryType+"</div></li><li><div class='in'>单位备注："+Data[i].remark+"</div></li>";
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
			$("#SearchForm").ajaxSubmit(options); 
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