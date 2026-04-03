$(document).ready(function(){
	var verifyTime = "";
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
					url:basePath + "ea/mobilecstaff/ea_getListCStaffByCompanyID.jspa?pageNumber="+goPageIndex+"&search=search",
					dataType:"json",  
					asnyc:false,
					success:function(data){
						var str = "";
						if(data!=null)
						{
							var Data = data.list;
							var pageCount = data.pageCount;
							for(var i in Data){ 
								if(Data[i].verifyTime!=null)
								{
									verifyTime = Data[i].verifyTime;
								}
								str+="<li><div class='in'>人员编号："+Data[i].staffCode+"</div></li><li><div class='in'>档案编号："+Data[i].recordCode+"</div></li>";
								str+="<li><div class='in'>人员姓名："+Data[i].staffName+"</div></li><li><div class='in'>曾用名："+Data[i].usedNmae+"</div></li>";
								str+="<li><div class='in'>性别："+Data[i].sex+"</div></li><li><div class='in'>出生日期："+Data[i].birthday+"</div></li>";
								str+="<li><div class='in'>籍贯："+Data[i].nativePlace+"</div></li><li><div class='in'>国籍："+Data[i].nationality+"</div></li>";
								str+="<li><div class='in'>民族："+Data[i].nation+"</div></li><li><div class='in'>身份证："+Data[i].staffIdentityCard+"</div></li>";
								str+="<li><div class='in'>身份证地址："+Data[i].staffAddress+"</div></li><li><div class='in'>电话："+Data[i].reference+"</div></li>";
								str+="<li><div class='in'>QQ："+Data[i].referenceCode+"</div></li><li><div class='in'>邮箱："+Data[i].referenceOrganization+"</div></li>";
								str+="<li><div class='in'>录入时间："+verifyTime+"</div></li><li><div class='in'>政治面貌："+Data[i].politicsStatus+"</div></li>";
								str+="<li><div class='in'>文化程度："+Data[i].culturalDegree+"</div></li><li><div class='in'>婚姻状况："+Data[i].marriage+"</div></li>";
								str+="<li><div class='in'>健康状况："+Data[i].health+"</div></li><li><div class='in'>银行账号："+Data[i].bankNum+"</div></li>";
								str+="<li><div class='in'>备注："+Data[i].staffDesc+"</div></li>";
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
		$("#searchStaff").click(function(){
			var options = {
				url:basePath + "ea/mobilecstaff/ea_toSearchCStaff.jspa?pageNumber="+pageNumber,
				dataType: "json",
				asnyc: false,
				success: function(data){
					var str = "";
					if(data!=null)
					{
						var pageCount = data.pageCount;
						var Data = data.list;
						for (var i in Data) {
							if (Data[i].verifyTime != null) {
								verifyTime = Data[i].verifyTime;
							}
							str+="<li><div class='in'>人员编号："+Data[i].staffCode+"</div></li><li><div class='in'>档案编号："+Data[i].recordCode+"</div></li>";
							str+="<li><div class='in'>人员姓名："+Data[i].staffName+"</div></li><li><div class='in'>曾用名："+Data[i].usedNmae+"</div></li>";
							str+="<li><div class='in'>性别："+Data[i].sex+"</div></li><li><div class='in'>出生日期："+Data[i].birthday+"</div></li>";
							str+="<li><div class='in'>籍贯："+Data[i].nativePlace+"</div></li><li><div class='in'>国籍："+Data[i].nationality+"</div></li>";
							str+="<li><div class='in'>民族："+Data[i].nation+"</div></li><li><div class='in'>身份证："+Data[i].staffIdentityCard+"</div></li>";
							str+="<li><div class='in'>身份证地址："+Data[i].staffAddress+"</div></li><li><div class='in'>电话："+Data[i].reference+"</div></li>";
							str+="<li><div class='in'>QQ："+Data[i].referenceCode+"</div></li><li><div class='in'>邮箱："+Data[i].referenceOrganization+"</div></li>";
							str+="<li><div class='in'>录入时间："+verifyTime+"</div></li><li><div class='in'>政治面貌："+Data[i].politicsStatus+"</div></li>";
							str+="<li><div class='in'>文化程度："+Data[i].culturalDegree+"</div></li><li><div class='in'>婚姻状况："+Data[i].marriage+"</div></li>";
							str+="<li><div class='in'>健康状况："+Data[i].health+"</div></li><li><div class='in'>银行账号："+Data[i].bankNum+"</div></li>";
							str+="<li><div class='in'>备注："+Data[i].staffDesc+"</div></li>";
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
			var addr="";
			$(".JQueryaddresssearch").find("select").find("option[value]:selected").each(function(){
				if($(this).text()!='--新增--'&&$(this).text()!='--请选择--')
				addr=addr+$(this).text(); 
			})   
            $("#cstaffSearchForm").find("input#staffAddress").val(addr);
			$("#cstaffSearchForm").ajaxSubmit(options); 
			$("#jqModelSearch").css('display','none');
			return false;
		});
		$td = $("td.JQueryaddresssearch");
		$td.children('select').empty();
		$select = "<option selected='selected'>--请选择--</option>";
		$("#province", $td).append($select);
		$td = $("td.JQueryaddresssearch");
		var url = basePath+"ea/mobilecstaff/sajax_n_ea_getCDistricts.jspa?districtPID=0&date="+new Date().toLocaleString();
		$.ajax({
			url: url,
			type: "get",
			async: true,
			dataType: "json",
			success: function cbf(data){
				var member = eval("(" + data + ")");
				var distinctlist = member.distinctlist;
				for (var i = 0; i < distinctlist.length; i++) {
					$op = $("<option />");
					$op.attr("value", distinctlist[i].districtID).attr("id", distinctlist[i].districtID).text(distinctlist[i].districtName);
					$("#province", $td).append($op);
				}
				retoken = 0;
			},
			error: function cbf(data){
				retoken = 0;
				alert("数据获取失败！")
			}
		});
		$('td.JQueryaddresssearch select[number]').change(function(){
			if(retoken)return;
			retoken =1 ;
			var province = this.id;
			var number = $(this).attr("number");
			var num = number;
			num++;
			$td = $("td.JQueryaddresssearch");
			searchrovince = "#" + province;
			$('#newdistrict', $td).hide()
			$td.children('select:gt(' + number + ')').empty();
			var D = $(searchrovince, $td).children("option:selected").attr("class");
			$($td).children('select:gt(' + number + ')').attr("disabled", false);
			var searchdistrictPID = $(searchrovince, $td).children("option:selected").val();
			if(searchdistrictPID == '--请选择--'){
				retoken=0;
				return;
			}
			var url = basePath+"ea/mobilecstaff/sajax_n_ea_getCDistricts.jspa?districtPID="+searchdistrictPID+"&date="+new Date().toLocaleString();
			$.ajax({
				url: encodeURI(url),
				type: "get",
				async: true,
				dataType: "json",
				success: function cbf(data){
					var member = eval("(" + data + ")");
					var distinctlist = member.distinctlist;
					if(distinctlist.length>0)
					{
						$td.children('select:eq(' + num + ')').show();
						$select = "<option selected='selected'>--请选择--</option>";
						$(searchrovince, $td).next().append($select);						
					}
					if (distinctlist.length) {
						for (var i = 0; i < distinctlist.length; i++) {
							$op = $("<option/>");
							$op.attr("value", distinctlist[i].districtID).attr("id", distinctlist[i].districtID).text(distinctlist[i].districtName);
							$(searchrovince, $td).next().append($op);
						}
					}
					$td.find('input#address').val(searchdistrictPID);
						retoken = 0 ;
				},
				error: function cbf(data){
					retoken = 0 ;
					alert("数据获取失败！")
				}
			});
		});
    	var pagerDefaults = {
        	DefaultPageCount: 1,
        	DefaultPageIndex: 1,
        	PageSize: 1,
    	    ShowPageNumber: true //是否显示页码
    	};
	})(jQuery);
});