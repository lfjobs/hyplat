$(function(){
                $(".jqmWindow").jqm({
                    modal: true,// 限制输入（鼠标点击，按键）的对话  
                    overlay: 20 // 遮罩程度%  
                }).jqmAddClose('.close');// 添加触发关闭的selector  
               // .jqDrag('.drag');// 添加拖拽的selector
                $('.JQueryflexme').flexigrid({   
                    height: 360,
                    width: 'auto',
                    minwidth: 30,
                    title: '卡记录管理',
                    minheight: 80,
                    buttons: [{
                        name: '返回',
                        bclass: 'delete',
                        onpress: action//当点击调用方法
                    },{
                        separator: true
                    }, {
                        name: '查询',
                        bclass: 'mysearch',
                        onpress: action//当点击调用方法
                    }, {
                        separator: true
                    },{
			            name: '设置每页显示条数',
			            bclass: 'mysearch',
						onpress : action//当点击调用方法
			        },{
			            separator: true
			        }]
                });
                function action(com, grid){
                    switch (com) {
                    	case '返回':
                             document.location.href=basePath+"ea/cardmanage/ea_getCardInfoList.jspa?pageNumber="+pNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value");
                            break;
                         case '查询':
                           $("#jqModelSearch").jqmShow();
                            break;
                      
                        case '设置每页显示条数':
						  pNumber = prompt("输入显示条数","请输入小于50正整数");
						   if(pNumber <0 || pNumber!=parseInt(pNumber)||pNumber>50) 
							{ 
							    alert("请输入小于50的正整数"); 
							    return;
							} 
						   document.location.href= basePath+"ea/cardmanage/ea_getCardRecordList.jspa?search="+search+"&pageNumber="+pNumber+"&cardCode="+cardCode;
							break;     
                    }
                }
                $(".JQueryflexme tr[id]").dblclick(function(){
                    action('修改');//当双击时出发 action方法.等价于先选中再点击修改按钮
                });
                $(".JQueryflexme tr[id]").click(function(){
                    cardReaderID = this.id;
                    $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
                });
                $("input.JQuerySubmit").click(function(){// 保存
	                $(".put3").trigger("blur");
                    if ($("form .error").length) {
                        return;
                    }
                    
	                $("#cstaffForm").attr("target","hidden").attr("action", basePath+"ea/cardmanage/ea_saveCardInfo.jspa?pageNumber="+pNumber+"&search="+search);
	                document.cstaffForm.submit.click();
	                document.cstaffForm.reset();
	                $("#cstaffForm").find("#staffID").trigger("change");
                    token = 1;
	                return;

                    
                });
                  $("input.JQueryreturn").click(function(){// 取消
                    $("#jqModel").jqmHide();
                    re_load();
                });
                   $(".close").click(function(){// 取消
                    $("#jqModel").jqmHide();
                    re_load();
                   
                });
                $("#tosearch").click(function(){
                    $("#postSearchForm").attr("action", basePath+"ea/cardmanage/ea_toSearchRecord.jspa?pageNumber="+pNumber+"&cardCode="+cardCode);
                    document.postSearchForm.submit.click();
                });
              $(".JQueryflexme").find("select").each(function(){
                    $s = $(this).hide();
                    $o = $("<span/>").text($s.find("option:selected").text());
                    $o.insertAfter($s);
                });
 
   getCardReader(cardCode);
 });

       
       
function re_load(){
     if(token)
     document.location.href=basePath+"ea/cardmanage/ea_getCardInfoList.jspa?pageNumber="+pNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value");
 }

 //跟踪读卡器使用记录,即车辆出入记录
 function showCardRecord(cardCode){
 document.location.href=basePath+"ea/cardreader/ea_getcardReaderList.jspa?pageNumber="+pNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value")+"&cardCode="+cardCode;	
 }
 //获得进出读卡器
 function getCardReader(cardCode){
 	
 	var url =basePath+"ea/cardmanage/sajax_n_ea_getCardReader.jspa";
 	$.ajax({
 	 url:url,
 	 type:"post",
 	 async:"true",
 	 dataType:"json",
 	 data:{ 	
 	 	cardCode:cardCode
 	 },
 	 success:function(data){
 	 	var member = eval("("+data+")");
 	 	var ListR = member.result;
 	    var str1 = "<option value=''>请选择器读卡器</option>";
			for (var i = 0; i < ListR.length; i++) {
				var obj = ListR[i];
				str1 += "<option title='" + obj.readerEnterName + "'value='" + obj.readerEnter + "'>" + obj.readerEnterName
						+ "</option>";
						
			}
			$("#postSearchForm #readerEnter").html(str1);
 	 	
 	 },
 	 error:function(data){
 	 	alert("数据获取失败");
 	 }
 	});
 		
 		

 	
 }