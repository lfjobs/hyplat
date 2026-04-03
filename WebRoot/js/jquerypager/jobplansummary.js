$(document).ready(function(){	
	var startDate = "";
	var endDate = "";
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
        	$goto.click(function() {
				$.ajax({
					url: basePath + "ea/mobilejobplan/ea_getJobPlanListSummary.jspa?pageNumber="+goPageIndex+"&search=search",
					dataType: "json",
					asnyc: false,
					success: function(data){
						var str = "";
						if(data!=null)
						{
							var pageCount = data.pageCount;
							var Data = data.list;
							for (var i in Data) {
								if (Data[i].startDate != null) {
									startDate = Data[i].startDate;
								}
								if (Data[i].endDate != null) {
									endDate = Data[i].endDate;
								}
								str+="<li><div class='in'>公司："+Data[i].companyName+"</div></li><li><div class='in'>部门："+Data[i].organizationName+"</div></li>";
								str+="<li><div class='in'>姓名："+Data[i].staffName+"</div></li><li><div class='in'>编号："+Data[i].staffCode+"</div></li>";
								str+="<li><div class='in'>起始日期："+startDate+"</div></li><li><div class='in'>结束日期："+endDate+"</div></li>";
								str+="<li><div class='in'>工作名称："+Data[i].jobName+"</div></li><li><div class='in'>岗位管理情况："+Data[i].postmanage+"</div></li>";
								str+="<li><div class='in'>工作内容："+Data[i].jobContent+"</div></li><li><div class='in'>主管签字："+Data[i].supervisor+"</div></li>";
								str+="<li><div class='in'>人事主管管理："+Data[i].humanSupervisor+"</div></li><li><div class='in'>公司经理："+Data[i].manager+"</div></li>";
							}
							$("#result").html(str);
							InitPager(pageCount, 1);
							EventHander(recordCount, goPageIndex);	
						}
						else
						{
							str+="你查询的人员不存在!!!";
							$("#result").html(str);
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown){
						alert(XMLHttpRequest.status+" "+XMLHttpRequest.readyState+" "+textStatus);
					}
				});	
			});
        	return $goto;
    	}
		$("#tosearch").click(function(){
			var options = {
				url: basePath + "ea/mobilejobplan/ea_toSearchSummary.jspa?pageNumber="+pageNumber,
				dataType: "json",
				asnyc: false,
				success: function(data){
					var str = "";
					if(data!=null)
					{
						var pageCount = data.pageCount;
						var Data = data.list;
						for (var i in Data) {
							if (Data[i].startDate != null) {
								startDate = Data[i].startDate;
							}
							if (Data[i].endDate != null) {
								endDate = Data[i].endDate;
							}
							str+="<li><div class='in'>公司："+Data[i].companyName+"</div></li><li><div class='in'>部门："+Data[i].organizationName+"</div></li>";
							str+="<li><div class='in'>姓名："+Data[i].staffName+"</div></li><li><div class='in'>编号："+Data[i].staffCode+"</div></li>";
							str+="<li><div class='in'>起始日期："+startDate+"</div></li><li><div class='in'>结束日期："+endDate+"</div></li>";
							str+="<li><div class='in'>工作名称："+Data[i].jobName+"</div></li><li><div class='in'>岗位管理情况："+Data[i].postmanage+"</div></li>";
							str+="<li><div class='in'>工作内容："+Data[i].jobContent+"</div></li><li><div class='in'>主管签字："+Data[i].supervisor+"</div></li>";
							str+="<li><div class='in'>人事主管管理："+Data[i].humanSupervisor+"</div></li><li><div class='in'>公司经理："+Data[i].manager+"</div></li>";
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
					alert(XMLHttpRequest.status+" "+XMLHttpRequest.readyState+" "+textStatus);
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