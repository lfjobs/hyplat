$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	// .jqDrag('.drag');//
  if(type=="message"){
  	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : title,
				minheight : 80,
				buttons : [ {
					name : '查看',
					bclass : 'edit',
					onpress : action
			
					}, {
					separator : true
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
  	
  }else{
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '单位往来关系',
				minheight : 80,
				buttons : [{
					name : '删除',
					bclass : 'delete',
					onpress : action
						// �
					}, {
					separator : true
				}, {
					name : '查看',
					bclass : 'edit',
					onpress : action
						// �
					}, {
					separator : true
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// �
					}, {
					separator : true
				}, {
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
  }
	function action(com, grid) {
		switch (com) {
			case '查看' :
				var str = 0;
				$("input[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								contactConnectionID = $(this).val();
								str++;
							}
						});

				if (str != 1) {
					alert('请选择具体一个往来单位!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("div#jqModel");
				$p = $("tr#" + contactConnectionID);
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());
						});
				$("#jqModel").jqmShow();
				break;
			case '删除' :
				var str = "";
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								str += $(this).val() + ",";
							}
						});
				if (str == "" || str.length == 0) {
					alert('请选择');
					return
				}
				$f = $('#cstaffForm');
				$f.find(':input#contactConnectionID').val(str);
				if (confirm("确定继续？")) {
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/contactConnection/ea_delContactConnection.jspa?pageNumber="
											+ pNumber);
					document.cstaffForm.submit.click();
					token = 2;
				}
				break;
			case '查询' :
				$("#contactConnections").children('option:eq(0)').attr("selected","selected");
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = basePath
						+ "ea/contactConnection/ea_showExcel.jspa?search="
						+ search;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/contactConnection/ea_getListContactConnection.jspa?search="
						+ search+"&type="+type+"&title="+title+"&cview.contactConnections="+contactConnections+"&cview.companyID="+companyID+"&typemes="+typemes;
				numback(url);
				break;
		}
	}
	$(".chx").live("click", function(event) {
				var b = $(this).attr("checked");
				$(this).attr("checked", !b);
			});
	$(".JQueryflexme tr[id]").click(function() {
				var d = $("input.chx", $(this)).attr("checked");
				$("input.chx", $(this)).attr("checked", !d);
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('查看');
			});
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	$("#tosearch").click(function() { // 查询
		var conValue = $("#contactConnections","#jqModelSearch").children('option:selected').text();
		var conVal = '';
		if(conValue != '请选择'){
			conVal = conValue.substring(conValue.indexOf('├')+1); //单位往来关系名称
		}
		$("#SearchForm")
				.attr(
						"action",
						basePath
								+ "ea/contactConnection/ea_toSearch.jspa?pageNumber="
								+ pNumber + "&cview.contactConnections=" + conVal);
		document.SearchForm.submit.click();
	});
});

$(function() {
   	var treeid = 'scode20110224xpd2t2jvda0000000002'; //单位往来关系
	var url = basePath + "ea/ccode/sajax_ea_getAllListCCodeByPID.jspa?codeID="+treeid+"&date="+new Date().toLocaleString(); 
	$.ajax({
		    url:encodeURI(url),
		    type: "get",
			async: true,
			dataType: "json",
			success: function cbf(data){
				var member = eval("(" + data + ")");
                var oList = member.codeList;
                var data2 = new Array();
                //var selid;
		        data2[0] = {
	                id: treeid,
	                pid: '-1',
	                text: '请选择'
	            };
                for (var i = 0; i < oList.length; i++) {
                    data2[i + 1] = {
                        id: oList[i].codeID,
                        pid: oList[i].codePID,
                        text: oList[i].codeValue
                    };
                }
                ts = new TreeSelector($("#contactConnections","#jqModelSearch")[0], data2, -1);
        		ts.createTree();
			},
			error: function cbf(data){
				alert("数据获取失败！");
			}
	});
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/contactConnection/ea_getListContactConnection.jspa?pageNumber="
				+ pNumber + "&search=" + search + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}