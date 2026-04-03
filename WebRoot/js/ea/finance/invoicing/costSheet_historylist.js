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
						height : 350,
						width : 'auto',
						minwidth : 30,
						title : "项目预算传阅记录",
						minheight : 80,
						buttons : [{
							name : '查看',
							bclass : 'see',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						}, {
							name : '查询',
							bclass : 'mysearch',
							onpress : action
								// 当点击调用方法
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

     
               
                function action(com, grid){
                    switch (com) {
                    
                        case '查看':
                            if(csbID==""){
                              alert("请选择！");
                              return;
                            }
                            if(treeType==""){
                            	treeType = "0";
                            }
                            document.location.href = basePath+"/ea/costsheet/ea_toCostSheet.jspa?pageNumber="+pNumber+"&costSheetBill.csbID="+csbID+"&type="+type+"&search="+search+"&treeType="+treeType+"&jumptype="+jumptype+"&toSee=history";
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
						+ "/ea/costsheet/ea_getCostSheetListHistory.jspa?search="
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
						 case '传阅':
						   	 if(csbID==""){
                                	alert("请选择！");
                                	return;
                               }
                         	 var pj=$("span#pJournalNum").html();
                             if($("span#pJournalNum").html()!=$("span#journalNum",$("tr#"+csbID)).html()){
                               	alert("只能传阅父项目");
                               	return;
                             }
                              document.SendForm.reset();
				              $("div#titlem").text("传阅项目预算单");
				              $("#jqModelSend").jqmShow();

				              getAllCompanyOfGroups();
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
                    $("#SearchForm").attr("action", basePath+"/ea/costsheet/ea_toSearchHistory.jspa?pageNumber="+pNumber+"&treeType="+treeType+"&jumptype="+jumptype);
                    document.SearchForm.submit.click();
                });
				//这一行的双击事件
                  $(".flexme11 tr[id]").dblclick(function(){
                    $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
                    csbID =this.id;
                    action("修改");
                });
                
                
 });

function re_load(){
	document.location.href=basePath+"/ea/costsheet/ea_getCostSheetListHistory.jspa?pageNumber="+pNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value")+"&type="+type+"&search="+search+"&sdate="+sdate+"&edate="+edate+"&treeType="+treeType+"&jumptype="+jumptype;
}

function fj(cID){
	var statusfj= $("tr#"+cID).find("span#status").text();
	if(statusfj!='01'){
		alert("已经归档不能修改附件");
		return;
	}
    window.open(basePath+"ea/uploadfile/ea_getListImage.jspa?loadFile.parmeterID="+cID);
}




     