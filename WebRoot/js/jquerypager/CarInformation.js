$(document).ready(function(){
	var PageIndex = $("#PageIndex").text();
	var photo = "";
	if(PageIndex=="")
	{
		$.ajax({
			url:basePath + "ea/mobilecar/ea_getCarInformationList.jspa?pageNumber="+pageNumber,
			dataType:"json",  
			asnyc:false,
			success:function(data){
				var str = "";
				if(data!=null)
				{
					var pageCount = data.pageCount;
					var Data = data.list;
					for(var i in Data){ 
						str+="<li><div class='in'>车牌号："+Data[i].carNum+"</div></li><li><div class='in'>发动机号："+Data[i].engineNum+"</div></li>";
						str+="<li><div class='in'>车辆类型："+Data[i].carType+"</div></li><li><div class='in'>购买价格："+Data[i].carPrice+"</div></li>";
						str+="<li><div class='in'>购买日期："+Data[i].buyDate+"</div></li><li><div class='in'>当前状况："+Data[i].conditions+"</div></li>";
						if(Data[i].photo!="")
						{
							photo = Data[i].photo;
							str+="<li><div class='in'>图片：<span id='lookfileNum'><a href='#'>查看</a></span></div></li>";
						}
						else
						{
							str+="<li><div class='in'>图片：无</div></li>";
						}
						str+="<li><div class='in'>司机："+Data[i].companyName+"</div></li><li><div class='in'>购买单位："+Data[i].driver+"</div></li>";
					}
					$("#result").html(str);
					InitPager(pageCount, 1);
					$("#lookfileNum").click(function(photo){
						if(!photo)
						{
							alert("不存在文件号！");
							return;
						}
						var imgurl =basePath+photo;
						newwin=window.open('about:blank','','width=800,height=600,left=0,top=0'); 
						newwin.document.write('<html><head></head><body style="margin: 0px"><div align="center"><img id="img1" src="'+imgurl+'"></div></body></html>');
						newwin.resizeTo(screen.availWidth,screen.availHeight);
					});
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
					url:basePath + "ea/mobilecar/ea_getCarInformationList.jspa?pageNumber="+goPageIndex,
					dataType:"json",  
					asnyc:false,
					success:function(data){
						var str = "";
						if(data!=null)
						{
							var pageCount = data.pageCount;
							var Data = data.list;
							for(var i in Data){ 
								str+="<li><div class='in'>车牌号："+Data[i].carNum+"</div></li><li><div class='in'>发动机号："+Data[i].engineNum+"</div></li>";
								str+="<li><div class='in'>车辆类型："+Data[i].carType+"</div></li><li><div class='in'>购买价格："+Data[i].carPrice+"</div></li>";
								str+="<li><div class='in'>购买日期："+Data[i].buyDate+"</div></li><li><div class='in'>当前状况："+Data[i].conditions+"</div></li>";
								if(Data[i].photo!="")
								{
									photo = Data[i].photo;
									str+="<li><div class='in'>图片：<span id='lookfileNum'><a href='#'>查看</a></span></div></li>";
								}
								else
								{
									str+="<li><div class='in'>图片：无</div></li>";
								}
								str+="<li><div class='in'>司机："+Data[i].companyName+"</div></li><li><div class='in'>购买单位："+Data[i].driver+"</div></li>";
							}
							$("#result").html(str);
							InitPager(pageCount, 1);
							EventHander(recordCount, goPageIndex);
							$("#lookfileNum").click(function(photo){
								if(!photo)
								{
									alert("不存在文件号！");
									return;
								}
								var imgurl =basePath+photo;
								newwin=window.open('about:blank','','width=800,height=600,left=0,top=0'); 
								newwin.document.write('<html><head></head><body style="margin: 0px"><div align="center"><img id="img1" src="'+imgurl+'"></div></body></html>');
								newwin.resizeTo(screen.availWidth,screen.availHeight);
							});
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
    	var pagerDefaults = {
        	DefaultPageCount: 1,
        	DefaultPageIndex: 1,
        	PageSize: 1,
    	    ShowPageNumber: true //是否显示页码
    	};
	})(jQuery);
});