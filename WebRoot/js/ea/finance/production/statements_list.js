$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$(".jqmreturn").click(function() {
				notoken = 0;
				$("#jqmAdd").jqmHide();
				showDocument = false;
			});
	$('.flexme11').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : "公司单据归档",
				minheight : 80,
				buttons : [{
					name : '查看',
					bclass : 'see',
					onpress : action
						// 当点击调用方法
				}, {
					separator : true
				},{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
				}, {
					separator : true
				}, {
					name : '生成报表',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
				}, {
					separator : true
				},{
					name : '现金收入、支出情况柱状图',
					bclass : 'chartbar',
					onpress : action
						// 当点击调用方法
				}, {
					separator : true
				},{
					name : '打印预览',
					bclass : 'printer',
					onpress : action
						// 当点击调用方法
				}, {
					separator : true
				},  {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
				}, {
					separator : true
				}]
			});

	function action(com, grid) {
		switch (com) {
			
			case '查看':
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				document.location.href = basePath
						+ "/ea/archivest/ea_toCashier.jspa?pageNumber="
						+ pNumber + "&tt=x&historyCashierBills.cashierBillsID="
						+ cashierBillsID + "&search=" + search
						+ "&pagepageNumber=" + $("#pageNumber").attr("value");
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			//case '导出':
				//typedao='01';
				///$("#FormsSearch").jqmShow();
				//break;
			case '生成报表' :
				//typedao='10';
				$("#FormsSearch").jqmShow();
				break;
			case '现金收入、支出情况柱状图' :
				$("#tubiao2").jqmShow();
				break;
			case '打印预览' :
			if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				window
						.open(basePath
								+ "/ea/printcashierbills/ea_toprintArchive.jspa?cashierBillsVO.cashierBillsID="
								+ cashierBillsID);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/archivest/ea_getArchivesList.jspa?tt=x&search="
						+ search + "&sdate=" + sdate + "&edate=" + edate;
				numback(url);
				break;
		}
	}
	
	$("#jqChart2").jqChart({
        title: { text: "公司"+new Date().getFullYear()+"年现金收入、支出情况  (单位：万元)" },
        animation: { duration: 1 },
        axes: [
                {
                    location: "left",
                    minimum: 0,
                    maximum: 100
                }
              ],
        series: [
                    {
					  	  title: "支出",
					      type: "Columnar",
					      data: line2
					 },
                     {
                     	title: "收入",
                         type: "Columnar",
                         data: line1
                     }
                ]
    }); 
	
	$(".flexme11 tr[id]").click(function() {
				cashierBillsID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("#tosearch").click(function() {
		var opt=$("#btype").find("option:selected").text();
		if(opt=="请选择"){
			$("#btype").find("option:selected").attr("value","");
		}else{
			var leng=opt.length;
			var num=$("#btype").find("option:selected").text().indexOf("├");
			$("#btype").find("option:selected").attr("value",$("#btype").find("option:selected").text().substr(num+1,leng));
		}
		$("#SearchForm")
				.attr(
						"action",
						basePath
								+ "/ea/archivest/ea_toSearch.jspa?tt=x&pageNumber="
								+ pNumber);
		document.SearchForm.submit.click();
	});
	$("#toFormsSearch").click(function() {
		var opt=$("#btypes").find("option:selected").text();
		if(opt=="请选择"){
			var ss="";
		}else{
			var leng=opt.length;
			var num=$("#btypes").find("option:selected").text().indexOf("├");
			var ss=$("#btypes").find("option:selected").text().substr(num+1,leng);
		}
		chuanzhi="&historyVO.billsType="+ss+"&historyVO.departmentID="+$("#departmentIDs").val()+
		"&historyVO.ccompanyname="+$("#danwei").val()+"&historyVO.contactUserName="+$("#peo").val()+
		"&historyVO.staffID="+$("#zeren").find("option:selected").val()+
		"&historyVO.contactConnections="+$("#danid").find("option:selected").val()+
		"&historyVO.phone="+$("#pepid").find("option:selected").val();
		window.open(encodeURI(basePath + "/ea/archivest/ea_toStatements.jspa?tt=x&sfdate=" + $("#sfdate").val() + "&efdate=" + $("#efdate").val()+"&chuanzhi="+chuanzhi));
		/*if(typedao=='10'){
		}else{
		 url = basePath+ "/ea/archivest/ea_toshow.jspa?tt=x&sfdate=" + $("#sfdate").val() + "&efdate=" + $("#efdate").val();
		open(url);	
		}*/
		$("#FormsSearch").jqmHide();
	});
	$(".flexme11 tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				cashierBillsID = this.id;
				action("查看");
			});
		});
		

$(document).ready(function() {
	// 删除
	$("#delButton").click(function() {
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				$("tr#"+cashierBillsID).remove();
				docNull-=1;
	});
	
	// 这一行的单击事件
	$("table#gostable tr[id]").live("click", function(event) {
				cashierBillsID = this.id;
				$("input.ra", $(this)).attr("checked", "checked");
			});
			
	
	
	$(function() {
		//var treeName = parent.frames["leftFrame"].tree.getItemText(treeID);
		var treePID = treeID;
		var treePName = treeNames;
		var url = basePath
				+ "ea/responsibilities/sajax_n_ea_getoList.jspa?date="
				+ new Date().toLocaleString();
		$.ajax({
			url : encodeURI(url),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var oList = member.organizationlist;

				var data1 = new Array();
				data1[0] = {
					id : treePID,
					pid : '-1',
					text : treePName
				};
				for (var i = 0; i < oList.length; i++) {
					data1[i + 1] = {
						id : oList[i].organizationID,
						pid : oList[i].organizationPID,
						text : oList[i].organizationName
					};
				}
				$t = $("table#SearchTable");

				var ts3 = new TreeSelector($("#departmentID")[0], data1, -1);
				ts3.createTree();
				
				var bb3=new TreeSelector($("#departmentIDs")[0], data1, -1);
				bb3.createTree();
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});
		
		var treeid = 'scode20101101dfs3uhdprp0000000029'; //单据类别
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
               var ts = new TreeSelector($("#btype")[0], data2, -1);
        		ts.createTree();
        	
        	   var  bb=new TreeSelector($("#btypes")[0], data2, -1);
        	    bb.createTree();
			},
			error: function cbf(data){
				alert("数据获取失败！");
			}
	});

	});
});
function re_load() {
	
	document.location.href = basePath
			+ "/ea/archivest/ea_getArchivesList.jspa?pageNumber="
			+ pNumber + "&pageForm.pageNumber="	+ $("#pageNumber").attr("value")
			+ "&sdate=" + sdate + "&edate="+ edate+"&search="+ search+"&tt=x";
}

