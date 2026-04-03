$(document).ready(function(){
	var photo = "";
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
					url:basePath + "ea/mobileresponsibilitiessummary/ea_getResponsibilitiesList.jspa?pageNumber="+goPageIndex,
					dataType:"json",  
					asnyc:false,
					success:function(data){
						var str = "";
						if(data!=null)
						{
							var startDate = "";
							var endDate = "";
							var pageCount = data.pageCount;
							var Data = data.list;
							for(var i in Data){
								if(Data[i].startDate!=null)
								{
									startDate = Data[i].startDate;
								}
								if(Data[i].endDate!=null)
								{
									endDate = Data[i].endDate
								}
								str+="<li><div class='in'>公司名称："+Data[i].companyIDName+"</div></li><li><div class='in'>部门名称："+Data[i].departmentIDName+"</div></li>";
								str+="<li><div class='in'>员工编号："+Data[i].staffCode+"</div></li><li><div class='in'>员工姓名："+Data[i].staffName+"</div></li>";
								str+="<li><div class='in'>岗位职责编号："+Data[i].responsibilitiesNum+"</div></li><li><div class='in'>岗位职责档案编号："+Data[i].recordNum+"</div></li>";
								str+="<li><div class='in'>岗位起始时间："+startDate+"</div></li><li><div class='in'>岗位截止时间："+endDate+"</div></li>";
								str+="<li><div class='in'>岗位名称："+Data[i].postName+"</div></li><li><div class='in'>职务名称："+Data[i].dutyName+"</div></li>";
								str+="<li><div class='in'>岗位情况管理："+Data[i].postmanage+"</div></li><li><div class='in'>工作单位名称："+Data[i].departmentIDName+"</div></li>";
								str+="<li><div class='in'>直接行政上级："+Data[i].organizationPIDName+"</div></li><li><div class='in'>直接行政下级："+Data[i].organizationCIDName+"</div></li>";
								str+="<li><div class='in'>管理范围："+Data[i].managesCope+"</div></li><li><div class='in'>工作内容1："+Data[i].jobContent1+"</div></li>";
								str+="<li><div class='in'>工作内容2："+Data[i].jobContent2+"</div></li><li><div class='in'>工作内容3："+Data[i].jobContent3+"</div></li>";
								str+="<li><div class='in'>工作内容4："+Data[i].jobContent4+"</div></li><li><div class='in'>工作内容5："+Data[i].jobContent5+"</div></li>";
								if(Data[i].fileNum!="")
								{
									photo = Data[i].fileNum;
									str+="<li><div class='in'>图片：<span id='lookfileNum'><a href='#'>查看</a></span></div></li>";
								}
								else
								{
									str+="<li><div class='in'>图片：无</div></li>";
								}
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
		$("#tosearch").click(function(){
			var options = {
				url:basePath + "ea/mobileresponsibilitiessummary/ea_toSearch.jspa?pageNumber="+pageNumber,
				dataType: "json",
				asnyc: false,
				success: function(data){
					var str = "";
					if(data!=null)
					{
						var startDate = "";
						var endDate = "";
						var pageCount = data.pageCount;
						var Data = data.list;
						for (var i in Data) {
							if(Data[i].startDate!=null)
							{
								startDate = Data[i].startDate;
							}
							if(Data[i].endDate!=null)
							{
								endDate = Data[i].endDate
							}
							str+="<li><div class='in'>公司名称："+Data[i].companyIDName+"</div></li><li><div class='in'>部门名称："+Data[i].departmentIDName+"</div></li>";
							str+="<li><div class='in'>员工编号："+Data[i].staffCode+"</div></li><li><div class='in'>员工姓名："+Data[i].staffName+"</div></li>";
							str+="<li><div class='in'>岗位职责编号："+Data[i].responsibilitiesNum+"</div></li><li><div class='in'>岗位职责档案编号："+Data[i].recordNum+"</div></li>";
							str+="<li><div class='in'>岗位起始时间："+startDate+"</div></li><li><div class='in'>岗位截止时间："+endDate+"</div></li>";
							str+="<li><div class='in'>岗位名称："+Data[i].postName+"</div></li><li><div class='in'>职务名称："+Data[i].dutyName+"</div></li>";
							str+="<li><div class='in'>岗位情况管理："+Data[i].postmanage+"</div></li><li><div class='in'>工作单位名称："+Data[i].departmentIDName+"</div></li>";
							str+="<li><div class='in'>直接行政上级："+Data[i].organizationPIDName+"</div></li><li><div class='in'>直接行政下级："+Data[i].organizationCIDName+"</div></li>";
							str+="<li><div class='in'>管理范围："+Data[i].managesCope+"</div></li><li><div class='in'>工作内容1："+Data[i].jobContent1+"</div></li>";
							str+="<li><div class='in'>工作内容2："+Data[i].jobContent2+"</div></li><li><div class='in'>工作内容3："+Data[i].jobContent3+"</div></li>";
							str+="<li><div class='in'>工作内容4："+Data[i].jobContent4+"</div></li><li><div class='in'>工作内容5："+Data[i].jobContent5+"</div></li>";
							if(Data[i].fileNum!="")
							{
								photo = Data[i].fileNum;
								str+="<li><div class='in'>图片：<span id='lookfileNum'><a href='#'>查看</a></span></div></li>";
							}
							else
							{
								str+="<li><div class='in'>图片：无</div></li>";
							}
						}
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
	$(function(){
		var treeName = parent.frames["leftFrame"].tree.getItemText(treeID);
		var treePID = parent.frames["leftFrame"].tree.getParentId(treeID);
		var treePName =parent.frames["leftFrame"].tree.getItemText(treePID);
		var url = basePath+"ea/responsibilities/sajax_n_ea_getoList.jspa?date2="+new Date();
		$.ajax({
			url: encodeURI(url),
			type: "get",
			async: true,
			dataType: "json",
			success: function cbf(data){
				var member = eval("(" + data + ")");
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
				$t = $("table#cataffSearchTable");
				var ts3 = new TreeSelector($("#deptID")[0], data1, -1);
				ts3.createTree();
				var s = "2010-9-9";
				var d = new Date(Date.parse(s.replace(/-/g, "/")));      
			},
			error: function cbf(data){
				alert("数据获取失败！")
			}
		});
	})
});