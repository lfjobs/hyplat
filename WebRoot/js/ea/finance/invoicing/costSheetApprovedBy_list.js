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
                    title: "预算审核管理",
                    minheight: 80,
                    buttons: [{
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
		            	name: '设置每页显示条数',
		            	bclass: 'mysearch',
						onpress : action//当点击调用方法
		        	}, {
		            	separator: true
		        	}, {
                        name: '同意',
                        bclass: 'edit',
                        onpress: action//当点击调用方法
                    }, {
                        separator: true
                    }, {
                        name: '驳回',
                        bclass: 'delete',
                        onpress: action//当点击调用方法
                    }, {
                        separator: true
                    } ]
                });
				
                function action(com, grid){
                    switch (com) {
                        case '查看':
                            if(csbID==""){
                              alert("请选择！");
                              return;
                            }
                            if(status=="待审核"){
                            	status="00";
                            }
                            document.location.href = basePath+"/ea/costsheetapprovedby/ea_toApprovedBy.jspa?pageNumber="+pNumber+"&costSheetBill.csbID="+csbID+"&type="+type+ "&status=" + status+"&search="+search;
                            break;
                        case '同意':
							$form =$("#costSheetForm"); 
							if(csbID==""){
                              alert("请选择！");
                              return;
                            }
                            if(status!="待审核"){
                        		alert("历史数据不可更改");
                        		return ;
                       	 	}
							if (confirm("确定同意？")){
	                            $form.attr("target","hidden").attr("action", basePath+"/ea/costsheetapprovedby/ea_updateResponsible.jspa?search="+search+"&pageNumber="+pNumber);
	                            $form.find("input#csbID").val(csbID);
	                            $form.find("input#billStatus").val("01");
	                            document.costSheetForm.submit.click();
	                            $("tr#"+csbID).remove();
	                            cashierBillsID = "";
	                            token = 12;
                            }
                            break;
                        case '驳回':
							$form =$("#costSheetForm"); 
							if(csbID==""){
                              alert("请选择！");
                              return;
                            }
                            if(status!="待审核"){
                        		alert("历史数据不可更改");
                        		return ;
                       	 	}
							if (confirm("确定驳回作废？")){
	                            $form.attr("target","hidden").attr("action", basePath+"/ea/costsheetapprovedby/ea_updateResponsible.jspa?search="+search+"&pageNumber="+pNumber);
	                            $form.find("input#csbID").val(csbID);
	                            $form.find("input#billStatus").val("10");
	                            document.costSheetForm.submit.click();
	                            $("tr#"+csbID).remove();
	                            cashierBillsID = "";
	                            token = 12;
                            }
                            break;
                        case '打印预览':
                           	if(csbID==""){
                             	alert("请选择！");
                             	return;
                            }
							window.open(basePath + "/ea/costsheetapprovedby/ea_toprintcsb.jspa?costSheetBill.csbID="+csbID);
                            break; 
                        case '设置每页显示条数':
						    var url = basePath
						+ "/ea/costsheetapprovedby/ea_getApprovedByList.jspa?search="
						+ search + "&status=" + status + "&sdate=" + sdate
						+ "&edate=" + edate + "&type=" + type;
							numback(url);
							break;  
					    case '查询':
                            $("#jqModelSearch").jqmShow();
                            $("input#journalNum").focus();
                            break;  
                    }
                }
                
                //这一行的单击事件
				 $(".flexme11 tr[id]").click(function(){
                    csbID = this.id;
                    status=$("span#billStatus",$(this)).text();
                    $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
                });
                
                //查询按钮单击事件
                 $("#tosearch").click(function(){
                    $("#SearchForm").attr("action", basePath+"/ea/costsheetapprovedby/ea_toSearch.jspa?pageNumber="+pNumber);
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
	document.location.href=basePath+"/ea/costsheetapprovedby/ea_getApprovedByList.jspa?pageNumber="+pNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value")+"&type="+type+"&search="+search+"&sdate="+sdate+"&edate="+edate;
}

function fj(cID){
	var statusfj= $("tr#"+cID).find("span#status").text();
	if(statusfj!='01'){
		alert("已经归档不能修改附件");
		return;
	}
    window.open(basePath+"ea/uploadfile/ea_getListImage.jspa?loadFile.parmeterID="+cID);
}


	          