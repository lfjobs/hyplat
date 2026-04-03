$(function(){
	$("input[type='button']").css({ color: "#000", background: "#ccc" });
	$(".jqmWindow").jqm({
                    modal: false,// 限制输入（鼠标点击，按键）的对话  
                    overlay: 20 // 遮罩程度%  
                }).jqmAddClose('.close');// 添加触发关闭的selector  
		//.jqDrag('.drag');// 添加拖拽的selector

       //显示发送窗口
       $(".SHOWSEND").click(function(){
			document.mForm.reset();
       		$('#jqModelSend').jqmShow();
       }); 
       //手工输入电话号码
       $(".ADDTEL").click(function(){
       		var tn = $("#telPhotoNumber").val();
       		var telNumber = $("#cumID").val();
			var tel = [];
			if(!checkMobile(tn)){
       			alert("请输入正确的手机号码！");
       			$("#telPhotoNumber").val("");
       			return;
       		}
			if(telNumber!=null)
			{
				tel = telNumber.split(";");
				for(var i=0;i<tel.length;i++)
				{
					if(tn==tel[i])
					{
						alert("已添加该手机号");
						$("#telPhotoNumber").val("");
						return;	
					}
				}
			}
			$("#cumID").val($("#cumID").val()+tn+";");
			var fulldata = $("#fulldata").val();
			$("#fulldata").val(fulldata+tn+"_"+" "+"_"+" "+"_"+" "+"_person,");
       		$("#telPhotoNumber").val("");
			$(".SEND").removeClass("disable").removeAttr('disabled');
       		//alert("已添加电话号码:"+tn);
       });
       //清空号码
       $(".RESET").click(function(){
       		$('#cData input').remove(); 
       		document.getElementById("cumID").value='';
       	//	$('#cumID').text("");
       		j;
			$(".SEND").addClass("disable").attr({disabled: true}); 
       }); 
       //发送
        $(".SEND").click(function(){
        	if(!$.trim($("#cumID").val())){
        		alert("发送号码不能为空！");
        		return;
        	}
        	if(!$.trim($("#content").val())){
        		alert("发送内容不能为空！");
        		return;
        	}
       

//         	var treePID = parent.frames["leftFrame"].treeid;
//         	
//         	var treePName; //=parent.frames["leftFrame"].tree.getItemText(treePID);//获取当前公司名称
//         	if( parent.frames["leftFrame"].document.getElementById("browser").children[0]){
//         	    treePName = parent.frames["leftFrame"].document.getElementById("browser").children[0].attributes["title"].value;
//         	}
         	
         	$("#companyName","form#mForm").attr("value",treePName);
       		$('#mForm').attr("target","MRList").attr("action",basePath+"ea/telmessage/ea_sendMessage.jspa?pageNumber="+pNumber);
			    document.mForm.submit.click();
			    
			    $('#cData input').remove();
       		    $('#cumID').text("");
			    $('#content').text("");
			    $('#jqModelSend').jqmHide();
			    
			    token=2; 
       });   
       
       
       
         //批量重新发送提交
        $(".SEND2").click(function(){
        	var telNumber = $("#cumID2").val();
        	if(!$.trim(telNumber)){
        		alert("请选择失败号码日志！");
        		return;
        	}
        	if(!$.trim($("#content2").val())){
        		alert("发送内容不能为空！");
        		return;
        	}
       		$('#remForm').attr("target","MRList").attr("action",basePath+"ea/telmessage/eas_resendMessage.jspa?pageNumber="+pNumber);
			 document.remForm.submit.click();
			    
       		    $('#cumID2').text("");
			    $('#content2').text("");
			    $("#errorcause").text("");
			    $('#jqModelReSends').jqmHide();
			    
			    
			    token=2; 
       }); 
       //清空内容
       $(".RMCONTENT").click(function(){
       		//$('#content').text(""); 
       		document.getElementById("content").value='';
       		$("#fulldata").val("");
//       		alert($("#fulldata").val());
       });
       
              //清空内容
       $(".RMCONTENT2").click(function(){
       		document.getElementById("content2").value='';


       });
       $("#closep").click(function(){
	   		 $(".SEND").addClass("disable").attr({disabled: true});
       		 $('#cData input').remove();
       		 $('#cumID').text("");
       		//token=2;
       		  re_load();
       });

    $("#close").click(function(){
        $("#jqmWindow2").jqmHide();
        $("#jqModelSend").jqmShow();
        $("#searchForm").attr("src","");
    });
    //查询窗口
       $(".openfm").click(function(){
       		$("#jqmWindow2").jqmShow();
       		 
       }); 
        $(".close2").click(function(){ 
        	$("#jqmWindow2").jqmHide();
        	$("#jqModelSend").jqmShow();
			$("#searchForm").attr("src","");
       }); 
       
       //新增常用短信
       
       $(".addQms").click(function(){
        document.QMSForm.reset();
        $("#jqModelQMS").jqmShow();
       
        
        
       	
       });
       
       //删除常用短信
       
       $(".deleteQms").click(function(){
       	var qmsID = $("#selectqms").val();
       	if(qmsID==null){
       		
       		alert("请选择");
       		return;
       	}
       	if(confirm("确定删除?")){
       		 var url = basePath+"ea/telmessage/sajax_ea_deleteQuickMessage.jspa";
       var qmscontent = $("#tqms").val();
       $.ajax({
        url:url,
        type:"post",
        async:false,
        dataType:"json",
        data:{
        	"tquickmessage.qmsID":qmsID
        	
        },
        success:function(data){
        	var member = eval("("+data+")");
        	var result = member.result;
        	if(result=="suc"){
        		$("#selectqms").find("option[value="+qmsID+"]").remove();
        	}
           
        },
        error:function(data){
        	alert("删除失败");
        }
       
       
       });
       		
       	}
       	
        
       	
       });
       //双击常用短信将短信内容赋予短信内容框
       $("#selectqms").dblclick(function(data){
          $("#mForm #content").val($(this).find("option:selected").text());
       });
       
         //双击失败号码记录内容赋予短信内容框，号码赋予号码框
       $("#selectfaillog").dblclick(function(data){
       	var filename = $(this).val();
         var url = basePath+"ea/telmessage/sajax_ea_getFailDetail.jspa";
       	 $.ajax({
        url:url,
        type:"post",
        async:false,
        dataType:"json",
        data:{
        	filename:filename
        },
        success:function(data){
        	var member = eval("("+data+")");
        	$("#telID2").val(member.telID);
        	$("#cumID2").text(member.telnumber);
        	$("#content2").text(member.sendcontent);
        	$("#errorcause").text(member.error);
        	$("#filename").val(filename);
        
           
        },
        error:function(data){
        	
        }
       
       
       });
       	
       });
       
       //关闭新增常用短信窗口
       $(".close2").click(function(){
           $("#jqModelQMS").jqmHide();
       	
       });
       //点击提交新增常用短信
       $("#submitqms").click(function(){
       var url = basePath+"ea/telmessage/sajax_ea_addQuickMessage.jspa";
       var qmscontent = $("#tqms").val();
       $.ajax({
        url:url,
        type:"post",
        async:false,
        dataType:"json",
        data:{
        	"tquickmessage.content":qmscontent
        	
        },
        success:function(data){
        	var member = eval("("+data+")");
        	var qmsID = member.result;
        	$("#selectqms").append("<option title='"+qmscontent+"' value='"+qmsID+"'>"+qmscontent+"</option>");
           
        },
        error:function(data){
        	
        }
       
       
       });
       	
       	 $("#jqModelQMS").jqmHide();
       });
       
       
       
       
       
       //设定查询方式 
	   $("#search").click(function(){
	   		checkText=$("#selType").find("option:selected").text();
			if(checkText=='公司'){ 
				$("#searchForm").attr("src",basePath+'ea/contactConnection/ea_getContactConnection.jspa?pageNumber=4'); 
			}
			if(checkText=='个人'){ 
				$("#searchForm").attr("src",basePath+'ea/contactuser/ea_getRelationList.jspa?pageNumber=4'); 
			}
	   });
		$("#checked").click(function(){
			var t = $('#searchForm').contents().find('tr[id]');
			var count = "";
			count = $('#searchForm').contents().find('#RecordCount').text();
			if(count>0)
			{
				checkText=$("#selType").find("option:selected").text();
	  			var x=$(t).find('input:checked');
	  			if(x.length<1){
					if(checkText=='公司')
					{
						$('#searchForm').contents().find(".JQuerypersonvalue").attr('checked',true);
					}
					if(checkText=='个人')
					{
						$('#searchForm').contents().find(".JQuerypersonvalue").attr('checked',true);
					}
	  			}
				else
				{
					$('#searchForm').contents().find(".JQuerypersonvalue").attr('checked',false);
				}	
			}
			else
			{
				alert("没有数据");
			}
			
		});		
	  //添加查询出来的 号码
	  $("#searchAdd").click(function(){
	  		var t = $('#searchForm').contents().find('tr[id]'); 
	  		var x=$(t).find('input:checked');
	  		if(x.length<1){
	  			alert("请选择");
	  			return;
	  		}
			var count = 0;
	  		$(t).each(function(){
	  			var chk = $(this).find('.JQuerypersonvalue').attr('checked');
					if(chk){ 
						//checkText=$("#selType").find("option:selected").text();
						 checkText = $("#searchtype").val();
							if(checkText=='company'){ 
								  var ids = $(this).find('#ccompanyID').text();  
								  var telNum = $(this).find("#responsibleTel").text(); 
								  var telNumber = $("#cumID").val();
								  var tel = [];
								   if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(telNum))){  
								       //alert('添加失败！不是手机号码');
										return ;
									}
									if(telNumber!=null)
									{
										tel = telNumber.split(";");
										for(var i=0;i<tel.length;i++)
										{
											if(telNum==tel[i])
											{
												//alert("已添加该手机号");
												return;	
											}
										}
									}
							      document.getElementById('cData').innerHTML+= ("<input type='text'"+" name='messageReceivemap["+ftj+"].telNum' value='"+telNum+"'/>");
	                        	  document.getElementById('cData').innerHTML+= ("<input type='text'"+" name='messageReceivemap["+ftj+"].ccompanyID' value='"+ids+"'/>");
	                        	  document.getElementById('cumID').value=(document.getElementById('cumID').value+telNum+";");
	                        	  ftj++;  
	                        	  var companyname = $(this).find('#companyName').text();  
	                        	  
	                        	  var ralation = $(this).find('#contactConnections').text();
	                        	  var fulldata = $("#fulldata").val();
	                        	  var receivername = $(this).find('#cresponsible').text();
	                        	  $("#fulldata").val(fulldata+telNum+"_"+companyname+"_"+receivername+"_"+ralation+"_company,");
							}
							if(checkText=='person'){ 
									var ids =  $(this).find('#contactUserID').text();  
									var telNum = $(this).find("#reference").text();
									var telNumber = $("#cumID").val();
									if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(telNum))){  
										//alert('添加失败！不是手机号码');
										return ;  
										
									}
									if(telNumber!=null)
									{
										tel = telNumber.split(";");
										for(var i=0;i<tel.length;i++)
										{
											if(telNum==tel[i])
											{
												//alert("已添加该手机号");
												return;	
											}
										}
									}
									document.getElementById('cData').innerHTML+= ("<input type='text'"+" name='messageReceivemap["+ftj+"].telNum' value='"+telNum+"'/>");
		                        	document.getElementById('cData').innerHTML+= ("<input type='text'"+" name='messageReceivemap["+ftj+"].staffID' value='"+ids+"'/>");
		                        	document.getElementById('cumID').value=(document.getElementById('cumID').value+telNum+";");
		                        	ftj++;
		                        	  var receivername = $(this).find('#staffName').text();
		                        	  var companyname = $(this).find('#companyName').text();  
		                        	  var relation = $(this).find('#relation').text();
		                        	  var fulldata = $("#fulldata").val();
		                        	 if(companyname==""){
		                        	 	companyname=" ";
		                        	 	
		                        	 }
		                        	  $("#fulldata").val(fulldata+telNum+"_"+companyname+"_"+receivername+"_"+relation+"_person,");
							}
								 
							count++;
							//alert('成功！');
							$(".SEND").removeClass("disable").removeAttr('disabled');
						}
	  		}); 
	  		
       
	  		    $('#searchForm').contents().find(".JQuerypersonvalue").attr('checked',false);
	  			if(count>0)
				{
					var relation = $.trim($('#searchForm').contents().find('#Relation').text());
					if(!confirm("成功添加"+count+"个"+relation+"是否继续添加")){
						$("#jqmWindow2").jqmHide();
						$("#searchForm").attr("src","");
					}
				}
				else
				{
					if(!confirm("添加失败是否继续添加")){
						$("#jqmWindow2").jqmHide();
						$("#searchForm").attr("src","");
					}
				}
	  }); 
	 getGroupInner();
	  
	  

});
function re_load(){
	if(token){
		 document.location.href=basePath+"ea/telmessage/ea_goMessageIndex.jspa?pageNumber="+pNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value")+"&type="+type+"&orgDetail="+orgDetail;
	}
}

//测试电话号码
function checkMobile(sMobile){   

	if(!(/^1[3|4|5|8][0-9]\d{8}$/.test(sMobile))){  
		return false; 
	}else{
		return true;
	}
} 
//获取集团内部人员
function getGroupInner(){
	 //获取各公司
	  var url1 = basePath
			+ "ea/documentcommon/sajax_ea_getAllCompanyByCurrent.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
		url : url1,
		type : "get",
		dataType : "json",

		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath + "page/ea/not_login.jsp";
			}
			var oList = member.companylist;;
			var data = new Array();
			var result = "<ul id='browser' class='filetree'><li><span class='folder'>集团机构树</span>";
			for (var i = 0; i < oList.length; i++) {
				data[i] = {
					id : oList[i].companyID,
					text : oList[i].companyName
				};
				result += "<ul><li onclick='javascript:childMenu(\""
						+ data[i].id + "\",\""+data[i].text+"\")' title='"
						+ data[i].text 
						+ "' class='curor expandable closed'><span class='folder'>" + data[i].text
						+ "</span><ul id='"+data[i].id+"'>";
				result += "</ul></li></ul>";
				

			}
			result+="</li></ul>";
			$(result).appendTo("#tree1");
		    $("#browser").treeview();

		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});
	
	
}


function childMenu(companyID,companyName){
	if($("ul#"+companyID+">li").length>0){
			return;
		}
	var	result = "<li><span onclick='javascript:childInner(\""
						+ companyID + "\",\""
						+ companyName + "\")'  class='folder'>在职员工</span><ul id='n"+companyID+"'>";
				result += "</ul></li></ul></li>" +
			"<li><span class='folder' onclick='javascript:childCcompany(\""
						+ companyID + "\",\""+companyName+"\")'>往来单位</span><ul id='c"+companyID+"'>";
				result += "</ul></li></ul></li>" +
			"<li><span class='folder'  onclick='javascript:childCmember(\""
						+ companyID + "\",\""
						+ companyName + "\")'>往来个人</span><ul id='m"+companyID+"'>";
				result += "</ul></li></ul></li>";
				
	$(result).appendTo("#"+companyID);

			

	
}

//获取部门
function childInner(companyID,companyName){
	$("#searchtype").val("person");
	var title = companyName+"——在职员工";
	
	//获取该公司下的所有在职员工
	
	$("#searchForm").attr("src",basePath+"ea/telmessage/eas_getAllStaffOfCompany.jspa?companyID="+companyID+"&title="+encodeURI(title)+"&companyName="+encodeURI(companyName)); 
	
	
if($("ul#n"+companyID+">li").length>0){
			return;
		}
		var url2 = basePath
				+ "ea/documentcommon/sajax_ea_getAllOrganizations.jspa?date="
				+ new Date().toLocaleString();
		$.ajax({
			url : encodeURI(url2),
			type : "post",
			async : false,
			dataType : "json",
			data : {
				companyID : companyID
			},
			success : function cbf(data) {

				/** **添加部门列表** */

				var member = eval("(" + data + ")");
				var orglist = member.orgaizationlist;
				var data = new Array();
				var result="";
				for (var i = 0; i < orglist.length; i++) {
					data[i] = {
					id : orglist[i].organizationID,
					text : orglist[i].organizationName
				    };
					result += "<li ><a href='#'><span id='"
								+ data[i].id
								+ "' class='folder curor' onclick='javascript:getPerson(\""
								+ companyID + "\",\"" + data[i].id + "\",\"" + data[i].text + "\",\"" + companyName + "\")' title='"
								+ data[i].text
								+ "'>"
								+ data[i].text + "</span></a></li>";
				}
				
				$(result).appendTo("#n"+companyID);

			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});
	
	
}

//获取往来单位往来关系
function childCcompany(companyID,companyName){
	
	$("#searchtype").val("company");
	getCCompany(companyID,"",companyName);
	if($("ul#c"+companyID+">li").length>0){
			return;
		}
	var url = basePath+"ea/telmessage/sajax_ea_getCCompanyRalation.jspa";
	$.ajax({
	url:url,
	type:"get",
	async:false,
	data:{
		companyID:companyID
	},
	success:function(data){
	   var member = eval("("+data+")");
	   var ralationlist = member.relationlist;
       var result="";
	   for (var i = 0; i < ralationlist.length; i++) {
	   	var codeID = ralationlist[i].codeID;
	   	var codeValue = ralationlist[i].codeValue;
					
		result += "<li><a href='#'><span id='"
								+ codeValue
								+ "' class='folder curor' onclick='getCCompany(\""+companyID+"\",\""+codeValue+"\",\""+companyName+"\")'>"
								+ codeValue + "</span></a></li>";
	  }
	  $(result).appendTo("#c"+companyID);
					
	},
	error:function(data){
		
	}
	
	});

	
}

//获取往来个人关系

function childCmember(companyID,companyName){
	$("#searchtype").val("person");
   getCCmember(companyID,"",companyName);

	
 if($("ul#m"+companyID+">li").length>0){
			return;
		}
	var url = basePath+"ea/telmessage/sajax_ea_getCodeCmRalation.jspa";
	$.ajax({
	url:url,
	type:"get",
	async:false,
	data:{
		companyID:companyID
	},
	success:function(data){
	   var member = eval("("+data+")");
	   var ralationlist = member.relationlist;
       var result="";
	   for (var i = 0; i < ralationlist.length; i++) {
	   	var codeID = ralationlist[i].codeID;
	   	var codeValue = ralationlist[i].codeValue;
					
		result += "<li><a href='#'><span id='"
								+ codeValue
								+ "' class='folder curor' onclick='getCCmember(\""+companyID+"\",\""+codeValue+"\",\""+companyName+"\");'>"
								+ codeValue + "</span></a></li>";
	  }
	  $(result).appendTo("#m"+companyID);
					
	},
	error:function(data){
		
	}
	
	});
	
}

// 根据部门和公司回获取在职员工
function getPerson(companyID, orgID, orgname, companyName) {
	$("#searchtype").val("person");
	
	var title = companyName + "——在职员工——"+orgname;
	$("#searchForm").attr(
			"src",
			basePath + "ea/telmessage/eas_getPersonByDept.jspa?companyID="
					+ companyID + "&orgID=" + orgID + "&title="
					+ encodeURI(title)+"&companyName="+encodeURI(companyName));

}



// 往来个人
function getCCmember(companyID,relation,companyName) {
	$("#searchtype").val("person");
    var title = companyName+"——往来个人——"+relation+"关系";
	$("#searchForm").attr(
			"src",
			basePath + "ea/contactuser/ea_toSearch.jspa?contactUser.companyID=" + companyID
					+ "&contactUser.relation=" + encodeURI(relation)+"&search=search&type=message&title="+encodeURI(title)+"&companyName="+encodeURI(companyName)+"&typemes=tree");

}


// 往来单位
function getCCompany(companyID, contactConnections,companyName) {
$("#searchtype").val("company");
 var title = companyName+"——往来公司——"+contactConnections+"关系";
	$("#searchForm").attr(
			"src",
			basePath + "ea/contactConnection/ea_toSearch.jspa?cview.companyID="
					+ companyID + "&cview.contactConnections="
					+ contactConnections + "&search=search&type=message&title="+encodeURI(title)+"&typemes=tree");

}


function getQms(){
	
	 var url = basePath+"ea/telmessage/sajax_ea_getQuickMessage.jspa";
       	 $.ajax({
        url:url,
        type:"post",
        async:false,
        dataType:"json",
        success:function(data){
        	var member = eval("("+data+")");
        	var qmslist = member.qmslist;
        	var str="";
        	var obj;
        	for(var i = 0;i<qmslist.length;i++){
        		obj = qmslist[i];
        		str+="<option title='"+obj.content+"' value='"+obj.qmsID+"'>"+obj.content+"</option>";
        		
        	}
        	$("#selectqms").html(str);
        	
           
        },
        error:function(data){
        	
        }
       
       
       });
       	
}


function getFailLog(){
	
	 var url = basePath+"ea/telmessage/sajax_ea_getFailLog.jspa";
       	 $.ajax({
        url:url,
        type:"post",
        async:false,
        dataType:"json",
        success:function(data){
        	var member = eval("("+data+")");
        	var filelist = member.filelist;
        	var str="";
        	var obj;
        	for(var i = 0;i<filelist.length;i++){
        		obj = filelist[i];
        		str+="<option title='"+obj+"' value='"+obj+"'>"+obj+"</option>";
        		
        	}
        	$("#selectfaillog").html(str);
        	
           
        },
        error:function(data){
        	
        }
       
       
       });
       	
}

