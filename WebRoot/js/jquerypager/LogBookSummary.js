$(document).ready(function(){	
	var startDate = "";
	var attachment = "";
	var endDate = "";
	var status = "";
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
					url: basePath + "ea/mobilelogbooksummary/ea_getListLogBook.jspa?pageNumber="+goPageIndex+"&search=search",
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
								if(Data[i].status == 00)
								{
									status = "未加锁";
								}
								else if(Data[i].status == 01)
								{
									status = "已加锁";
								}
								str+="<li><div class='in'>公司名称："+Data[i].companyName+"</div></li><li><div class='in'>员工编号："+Data[i].staffCode+"</div></li>";
								str+="<li><div class='in'>员工姓名："+Data[i].staffName+"</div></li><li><div class='in'>当天日期："+Data[i].todaydate+"</div></li>";
								str+="<li><div class='in'>起时间："+startDate+"</div></li><li><div class='in'>止时间："+endDate+"</div></li>";
								str+="<li><div class='in'>工作地点："+Data[i].jobLocation+"</div></li><li><div class='in'></li><li><div class='in'>完成工作内容："+Data[i].jobContent+"</div></li>";
								str+="<li><div class='in'>得分类别："+Data[i].scoreSortName+"</div></li><li><div class='in'>得分："+Data[i].bisect+"</div></li>";
								if(Data[i].attachment != null)
								{
									attachment = Data[i].attachment;
									str+="<li><div class='in'>附件类别及名称：<span id='lookfileNum'><a href='#'>查看</a></span></div></li>";
								}
								else
								{
									str+="<li><div class='in'>附件类别及名称：无</div></li>";
								}
								str+="<li><div class='in'>主管签字："+Data[i].supervisor+"</div></li><li><div class='in'>公司经理："+Data[i].manager+"</div></li>";
								str+="<li><div class='in'>备注："+status+"</div></li>";
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
				url: basePath + "ea/mobilelogbooksummary/ea_toSearch.jspa?pageNumber="+pageNumber,
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
								if(Data[i].status == 00)
								{
									status = "未加锁";
								}
								else if(Data[i].status == 01)
								{
									status = "已加锁";
								}
								str+="<li><div class='in'>公司名称："+Data[i].companyName+"</div></li><li><div class='in'>员工编号："+Data[i].staffCode+"</div></li>";
								str+="<li><div class='in'>员工姓名："+Data[i].staffName+"</div></li><li><div class='in'>当天日期："+Data[i].todaydate+"</div></li>";
								str+="<li><div class='in'>起时间："+startDate+"</div></li><li><div class='in'>止时间："+endDate+"</div></li>";
								str+="<li><div class='in'>工作地点："+Data[i].jobLocation+"</div></li><li><div class='in'></li><li><div class='in'>完成工作内容："+Data[i].jobContent+"</div></li>";
								str+="<li><div class='in'>得分类别："+Data[i].scoreSortName+"</div></li><li><div class='in'>得分："+Data[i].bisect+"</div></li>";
								if(Data[i].attachment != null)
								{
									attachment = Data[i].attachment;
									str+="<li><div class='in'>附件类别及名称：<span id='lookfileNum'><a href='#'>查看</a></span></div></li>";
								}
								else
								{
									str+="<li><div class='in'>附件类别及名称：无</div></li>";
								}
								str+="<li><div class='in'>主管签字："+Data[i].supervisor+"</div></li><li><div class='in'>公司经理："+Data[i].manager+"</div></li>";
								str+="<li><div class='in'>备注："+status+"</div></li>";
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
	$(function(){
        var treeName = parent.frames["leftFrame"].tree.getItemText(treeID);
        var treePID = parent.frames["leftFrame"].tree.getParentId(treeID);
        var treePName = parent.frames["leftFrame"].tree.getItemText(treePID);
        var url = basePath+"ea/responsibilities/sajax_n_ea_getoList.jspa?date="+new Date().toLocaleString();
         $.ajax({
			url: encodeURI(url),
			type: "get",
			async: true,
			dataType: "json",
			success: function cbf(cc){
				var member = eval("(" + cc + ")");
				var nologin = member.nologin;
				if(nologin){
					document.location.href =basePath+"page/ea/not_login.jsp";
				}
				var oList = member.organizationlist;
				var data1 = new Array();
				data1[0] = {
					id: treePID,
					pid: '-1',
					text: treePName
				}; 
				for (var i = 0; i < oList.length; i++) {
					data1[i + 1] = {
						id: oList[i].organizationID,
						pid: oList[i].organizationPID,
						text: oList[i].organizationName
					};
				}   
				var ts3 = new TreeSelector($("#organizationID")[0], data1, -1);
				ts3.createTree();      
			},
			error: function cbf(data){
				alert("数据获取失败！")
			}
		});
	})
});