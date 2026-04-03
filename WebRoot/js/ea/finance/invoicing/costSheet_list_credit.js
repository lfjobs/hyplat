 $(function(){
             $(".jqmWindow").jqm({
                    modal: true,// 限制输入（鼠标点击，按键）的对话  
                    overlay: 20 // 遮罩程度%  
                }).jqmAddClose('.close');// 添加触发关闭的selector 
      
       $(".jqmreturn").click(function(){
    		notoken = 0;
     		$("#documentsjqModel").jqmHide();
      		$("#previewjqModel").jqmHide();
      		$("#journalNumAjax").attr("value","");
      		$("#taxDateAjax").attr("value","");
      		showDocument=false;
    	});
                $('.flexme11').flexigrid({
                    height: 350,
                    width: 'auto',
                    minwidth: 30,
                    title: '项目已审核未审核',
                    minheight: 80,
                    buttons: [{
                        name: '生成出纳单',
                        bclass: 'add',
                        onpress: action//当点击调用方法
                    }, {
                        separator: true
                    }, {
                        name: '查看',
                        bclass: 'see',
                        onpress: action//当点击调用方法
                    }, {
                        separator: true
                    }, {
                        name: '查询',
                        bclass: 'mysearch',
                        onpress: action//当点击调用方法
                    }, {
                        separator: true
                    }, {
                        name: '打印预览',
                        bclass: 'printer',
                        onpress: action//当点击调用方法
                    }, {
                        separator: true
                    }, {
                        name: '导出',
                        bclass: 'excel',
                        onpress: action//当点击调用方法
                    }, {
                        separator: true
                    }, {
			            name: '设置每页显示条数',
			            bclass: 'mysearch',
						onpress : action//当点击调用方法
			        },{
			            separator: true
			        }]
                });
				
                function action(com, grid){
                    switch (com) {
                    	case '生成出纳单':
                    		var num=$("tr#" + csbID).find("span#billStatus").text();
                    		if(num=='审核通过'){
                    			var subRuleurl = basePath+ "ea/costsheet/sajax_ea_getbills.jspa?date="+ new Date().toLocaleString()+"&costSheetBill.csbID="+csbID;
                    				$.ajax({
											url : encodeURI(subRuleurl),
											type : "post",
											async : true,
											dataType : "json",
											success : function cbf(data) {
												var member = eval("(" + data + ")");
												var nologin = member.nologin;
												if (nologin) {
													document.location.href = basePath+ "page/ea/not_login.jsp";
												}
												var counts = member.counts;
												if(parseInt(counts)>0){
													alert("该项目已生成出纳单据！");
													notoken = 0;
													re_load();
												}else{
													if(confirm("确定生成？")){
													    document.location.href=basePath+"ea/costsheet/ea_cashier.jspa?costSheetBill.csbID="+csbID+"&treeType="+treeType+"&jumptype="+jumptype+"&type="+type;  
													  }
												}
											},
											error : function cbf(data) {
												alert("数据获取失败！");
											}
											});
                    		}else{
                    			alert("该项目未审核通过！");
                    			return;
                    		}
                    		break;
                        case '查看':
                            if(csbID==""){
                              alert("请选择！");
                              return;
                            }
                            if(treeType==""){
                            	treeType = "0";
                            }
                            document.location.href = basePath+"/ea/costsheet/ea_toCostSheet.jspa?pageNumber="+pNumber+"&costSheetBill.csbID="+csbID+"&type="+type+"&search="+search+"&treeType="+treeType+"&jumptype="+jumptype;
                            break;
                        case '打印预览':
                           	if(csbID==""){
                             	alert("请选择！");
                             	return;
                            }
							window.open(basePath + "/ea/costsheet/ea_toprintcsb.jspa?costSheetBill.csbID="+csbID);
                            break; 
                        case '设置每页显示条数':
						    var url = basePath
						+ "/ea/costsheet/ea_getCostSheetList.jspa?search="
						+ search + "&sdate=" + sdate + "&edate=" + edate
						+ "&type=" + type+"&treeType="+treeType+"&jumptype="+jumptype;
							numback(url);
							break;  
					    case '查询':
                            $("#jqModelSearch").jqmShow();
                            $("input#journalNum").focus();
                            break;
                        case '导出':
							var url = basePath+ "ea/costsheet/ea_showCostSheetList.jspa?type="+type+"&search="+search+"&sdate="+sdate+"&edate="+edate+"&treeType="+treeType+"&jumptype="+jumptype;
							open(url);
							break;
                    }
                }
                //这一行的单击事件
				 $(".flexme11 tr[id]").click(function(){
                    csbID = this.id;
                    $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
                });
                
                //查询按钮单击事件
                 $("#tosearch").click(function(){
                 	if(treeType==""){
                        treeType = "0";
                    }
                    $("#SearchForm").attr("action", basePath+"/ea/costsheet/ea_toSearch.jspa?pageNumber="+pNumber+"&treeType="+treeType+"&jumptype="+jumptype);
                    document.SearchForm.submit.click();
                });
				//这一行的双击事件
                  $(".flexme11 tr[id]").dblclick(function(){
                    $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
                    csbID =this.id;
                    action("查看");
                });
});

function re_load(){
	document.location.href=basePath+"/ea/costsheet/ea_getCostSheetList.jspa?pageNumber="+pNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value")+"&type="+type+"&search="+search+"&sdate="+sdate+"&edate="+edate+"&treeType="+treeType+"&jumptype="+jumptype;
}


	          