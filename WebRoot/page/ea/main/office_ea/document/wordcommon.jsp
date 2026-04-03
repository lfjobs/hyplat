<%@ page language="java" 
	import="java.util.*,com.zhuozhengsoft.pageoffice.*,java.sql.*,java.io.*,javax.servlet.*,javax.servlet.http.*"
	pageEncoding="gb2312"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
    String fileType=request.getParameter("fileType");
    String docPath = request.getParameter("docPath");
   // String id=request.getParameter("id");
    String isRead = request.getParameter("isRead");
    String stage = request.getParameter("stage");

   
   
    
    //***************************卓正PageOffice组件的使用********************************
	PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
	
	poCtrl1.setCustomToolbar(false);
	poCtrl1.setCaption(stage);
	poCtrl1.setServerPage(basePath +"poserver.zz"); //此行必须
	poCtrl1.setSaveFilePage(basePath
			+ "ea/zoffice/ea_saveOffice.jspa?docPath=" +docPath);
   
	poCtrl1.setJsFunction_AfterDocumentOpened("AfterDocumentOpened");
	
	poCtrl1.getRibbonBar().setSharedVisible("FileSave", false);
	
	//poCtrl1.setCustomMenuCaption("自定义菜单");
    //poCtrl1.addCustomMenuItem("显示痕迹", "ShowRevisions", true);
    //poCtrl1.addCustomMenuItem("隐藏痕迹", "HiddenRevisions", true);
    //poCtrl1.addCustomMenuItem("-", "", false);
    // poCtrl1.addCustomMenuItem("显示标题", "ShowTitle", true);
    //poCtrl1.addCustomMenuItem("-", "", false);
   // poCtrl1.addCustomMenuItem("领导签批", "InsertHandSign", true);
   // poCtrl1.addCustomMenuItem("插入印章", "InsertSeal", true);
   // poCtrl1.addCustomMenuItem("接受所有修订", "AcceptAllRevisions", true);
    //poCtrl1.addCustomMenuItem("-", "", false);
    //poCtrl1.addCustomMenuItem("分层显示手写批注", "ShowHandDrawDispBar", true);
    
    poCtrl1.setCustomToolbar(true);
    if(isRead.equals("1")){
    
        poCtrl1.setMenubar(false);//隐藏自定义菜单栏
    	poCtrl1.setOfficeToolbars(false);//隐藏Office工具栏 xlsReadOnly,docReadOnly
    if(!stage.equals("查看")){
    	//poCtrl1.addCustomToolButton("打印设置","setPrint()",6);
    	poCtrl1.addCustomToolButton("打印预览","setPrintPreview()",6);
    	poCtrl1.addCustomToolButton("打印","directPrint()",6);
    	
    }
    }else{
     
       poCtrl1.addCustomToolButton("保存", "Save", 1);
     if(stage.equals("未审批")){
    	 poCtrl1.addCustomToolButton("领导签批", "InsertHandSign", 3);
    	 poCtrl1.addCustomToolButton("领导圈阅", "StartHandDraw", 3);
    	 poCtrl1.addCustomToolButton("插入键盘批注", "StartRemark", 3);
    	 poCtrl1.addCustomToolButton("显示/隐藏痕迹", "Show_HidRevisions", 5);
    	 poCtrl1.addCustomToolButton("接受所有修订", "AcceptAllRevisions", 5);
         poCtrl1.addCustomToolButton("分层显示手写批注", "ShowHandDrawDispBar", 7);
      }
     if(stage.equals("未盖章")){
    	 poCtrl1.setZoomSealServer("http://123.57.28.135:8816/ZoomSeal/poserver.zz");
    	 poCtrl1.addCustomToolButton("插入印章/签名", "InsertSeal", 2);
    	 poCtrl1.addCustomToolButton("插入键盘批注", "StartRemark", 3);
    	 poCtrl1.addCustomToolButton("显示/隐藏痕迹", "Show_HidRevisions", 5);
    	 poCtrl1.addCustomToolButton("接受所有修订", "AcceptAllRevisions", 5);
         poCtrl1.addCustomToolButton("分层显示手写批注", "ShowHandDrawDispBar", 7);
        
      }
       //poCtrl1.addCustomToolButton("另存为Html", "SaveAsHtml", 0);
     
        
      }
      //poCtrl1.getRibbonBar().setSharedVisible("FileSave", false);

	if(stage.equals("拟稿")||stage.equals("收件箱")||stage.equals("设置模板")){
		poCtrl1.setAllowCopy(true);
	}else{
		poCtrl1.setAllowCopy(false);
	}
      poCtrl1.addCustomToolButton("全屏/还原", "IsFullScreen", 4);
      poCtrl1.setJsFunction_BeforeDocumentSaved("BeforeDocumentSaved()");
	   if (fileType == "W" || fileType.equals("W")) {
		if(isRead.equals("1")){
			poCtrl1.webOpen(basePath+docPath,OpenModeType.docReadOnly, "张三");
		}else{
		    poCtrl1.webOpen(basePath+docPath,OpenModeType.docNormalEdit, "张三");
		}
	   } else {
		if(isRead.equals("1")){
			poCtrl1.webOpen(basePath+docPath,OpenModeType.xlsReadOnly, "张三");
		}else{
		    poCtrl1.webOpen(basePath+docPath,OpenModeType.xlsNormalEdit, "张三");
		}
		

	}
	
	
	poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须				  							  
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <title></title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main111.css" />	
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
    <script type="text/javascript"  src="<%=basePath%>js/jqModal/jqModal.js"></script>
    <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
    <script charset="utf-8" type="text/javascript" src="<%=basePath%>js/ea/office_ea/document/controlnewdoc.js"></script>
   
			
     
    <script type="text/javascript">
   var basePath="<%=basePath%>";
   var stage = "<%=stage%>";
  $(function(){  	
        if(stage=="拟稿"||stage=="收件箱"){
        	
        	$("#drafttbl").show();
        	 var obj = window.opener.getParam();
        	  
            for(id in obj){
          	  if(id!=""){
          	  
          	  if(id=="numTime"){
          		  $("#"+id).text(obj[id]);
          	  }else if(id=="module"){
          		  if(obj[id]=="contract"){
          			  $(".hetong").show();
          		  }else{
          			  $(".hetong").hide();
          		  }
          	  }else{
          		  
          		  $("#"+id).val(obj[id]); 
          	  }
          	 }
          	  
            }
        }else{
        	$("#drafttbl").hide();
        }
	  
	  
      	
          
          
          
  });

    </script>
    <!-- *************************PageOffice组件的使用************************************ -->
    <script type="text/javascript">
    
   


    var stage = "<%=stage%>";
    
    
    
  //保存前
    function BeforeDocumentSaved() {
  
    	if(stage=="拟稿"||stage=="收件箱"){
    	var fileShowNameField = document.getElementById("title").value;
    	var themes = document.getElementById("theme").value;
    	var  numWord = document.getElementById("numWord").value;
            if(fileShowNameField == null || fileShowNameField == ""){
			// alert("请输入文件标题！");
               return false;
		     }
		     if (themes == null || themes == "") {
			 //   alert("请输入主题词！");
			    return false;

		     }
		     if(numWord==null||numWord==""){

		     //  alert("请输入正式编号！");
		       return false;

		
		     }
		     
		     
		     
		       
				
				
    	}
        return true;
    }
    
    
    function Save() {
    

    	
        document.getElementById("PageOfficeCtrl1").WebSave();
        if(stage=="拟稿"||stage=="收件箱"){
        var ret = new Object();
        
		$("#drafttbl").find(":input").each(function(){
                  ret[this.id]=$(this).val();
                  ret["docTypeText"]=$("#docType").find("option:selected").text(); 
                  ret["numTime"]=$("#drafttbl").find("#numTime").text(); 
             
    	});
		ret.docPath = "<%=docPath%>";
		
		
		 window.opener.closeOpen(ret);
	  //   window.close();
        }
	


    }
    //打印设置
    function setPrint() {
        document.getElementById("PageOfficeCtrl1").ShowDialog(5);
    }
    //打印预览
    function setPrintPreview(){
    	document.getElementById("PageOfficeCtrl1").PrintPreview(); 

    }
    function directPrint(){
       // document.getElementById("PageOfficeCtrl1").PrintOut();
        document.getElementById("PageOfficeCtrl1").ShowDialog(4);
    }

    function ShowRevisions() {
        document.getElementById("PageOfficeCtrl1").ShowRevisions = true;
    }
    function HiddenRevisions() {
        document.getElementById("PageOfficeCtrl1").ShowRevisions = false;
    }
    function Show_HidRevisions() {
        document.getElementById("PageOfficeCtrl1").ShowRevisions = !document.getElementById("PageOfficeCtrl1").ShowRevisions;
    }

    //领导圈阅签字
    function StartHandDraw() {
        document.getElementById("PageOfficeCtrl1").HandDraw.SetPenWidth(5);
        document.getElementById("PageOfficeCtrl1").HandDraw.Start();
    }

    //接受所有修订
    function AcceptAllRevisions() {
        document.getElementById("PageOfficeCtrl1").AcceptAllRevisions();
    }

    //分层显示手写批注
    function ShowHandDrawDispBar() {
        document.getElementById("PageOfficeCtrl1").HandDraw.ShowLayerBar();
    }

    //全屏/还原
    function IsFullScreen() {
        document.getElementById("PageOfficeCtrl1").FullScreen = !document.getElementById("PageOfficeCtrl1").FullScreen;
    }

    //显示菜单
    function ShowTitle() {
        alert("该菜单的标题是：" + document.getElementById("PageOfficeCtrl1").caption);
    }

    //插入电子印章
    function InsertSeal() {
        //alert("请使用此用户的印章测试\r\n用户名：李志 \r\n初始密码：111111");

        var zoomseal = document.getElementById("PageOfficeCtrl1").ZoomSeal;
        if (zoomseal != null)
            zoomseal.AddSeal();


    }
    // 签批
    function InsertHandSign() {
        //alert("请使用此用户测试\r\n用户名：李志 \r\n初始密码：111111");

        var zoomseal = document.getElementById("PageOfficeCtrl1").ZoomSeal;
        if (zoomseal != null)
            zoomseal.AddHandSign();
    }

    //文档另存为Html，并发布到web服务器
    function SaveAsHtml() {
        document.getElementById("PageOfficeCtrl1").WebSaveAsMHT();
        window.open("htmldoc.jsp?type=word&id=");
    }
    
    //领导圈阅签字
    function StartHandDraw() {
        document.getElementById("PageOfficeCtrl1").HandDraw.SetPenWidth(5);
        document.getElementById("PageOfficeCtrl1").HandDraw.Start();
    }
	// 插入键盘批注
    function StartRemark() {
        var appObj = document.getElementById("PageOfficeCtrl1").WordInsertComment();

    }	


    </script>
    <!-- *************************PageOffice组件的使用************************************ -->
</head>
<body>
    <form id="form2">
    <div id="header">
       			<TABLE class="info" height="21" cellSpacing="10" cellPadding="5" width="100%" id="drafttbl"
				bgcolor="#FFFFFF">
				
					<TR class="nodirect">
						
						<TD style="font-size: 11pt" width=177 nowrap>
							<nobr>
								当前操作状态：
								<font color="#FF0000">拟稿状态</font>
							</nobr>
						</TD>
						<TD>
							<SPAN class="ltsep">|</SPAN>
						</TD>
						<TD style="font-size: 11pt" width="100%">
							<span class="xx">*</span>文件标题：
							<input type="text" name="fileShowNameField" class="inputbottom"
								id="title" value="" style="width:150px;"/>
							&nbsp;<span class="xx">*</span>主题词：
							<input type="text" id="theme" value="" class="inputbottom" style="width:120px;"/>
							&nbsp;文件缓急：
							<select id="emergencyType" style="width:85px;">
								<option value="普通" selected="selected">
									普通
								</option>
								<option value="急件">
									急件
								</option>
								<option value="特急">
									特急
								</option>
							</select>
							
							
						</TD>
	

					</TR>
					<TR class="nodirect">
						
						<TD style="font-size: 11pt" width="177">
							
						</TD>
						<TD>
							<SPAN class="ltsep"></SPAN>
						</TD>
						<TD style="font-size: 11pt" >
						  <span class="xx">*</span>正式编号：
							<input type="text" id="numWord" class="inputbottom" style="width:80px;"
													title="请输入正式编号" name="document.numWord"
													/>
												字【<span id="numTime">77</span>】第
												<input type="text" id="numCode" class="inputbottom"
													name="document.numCode" value=""
													title="无需输入,自动生成" readonly style="width:70px;"/>
												号
											
						        &nbsp;&nbsp;公文类型：
                              <select id="docType" name="document.docType" class="docType">
												
												<option value="aa" selected>
													董事会会议决定文件
												</option>
												<option value="bb">
													董事长办公室文件
												</option>
												<option value="cc">
													总裁办公室文件
												</option>
												<option value="dd">
													总部人事处文件
												</option>
												<option value="ee">
													总部办公室文件
												</option>
												<option value="ff">
													总部财务处文件
												</option>
												<option value="gg">
													总部教务(生产)处文件
												</option>
												<option value="hh">
													总部营销处文件
												</option>
												<option value="ii">
													总部服务(创收)平台
												</option>
												<option value="jj">
													总部教务部文件
												</option>
											</select>
						</TD>
	

					</TR>
				
					
						<TR class="nodirect hetong">
						
						<TD style="font-size: 11pt" width=177 nowrap>
							
						</TD>
						<TD>
							<SPAN class="ltsep"></SPAN>
						</TD>
						<TD style="font-size: 11pt" >
							<div>
							甲&nbsp;&nbsp;&nbsp;&nbsp;方：
												&nbsp;&nbsp;公司&nbsp;
												<input type="text" name="document.partyAName" id="partyAName"
													value="" class="inputbottom" readonly style="width:150px;"/>
												<input type="hidden" name="document.partyA" id="partyA"
													/>
												<input type="button" class="btncon" onclick="importGY2('ea/documentcommon/ea_getListContactCompany.jspa','partyAD')"/>
												<input type="button" class="btndel" onclick="clears('partyAD');"/>		
												
											&nbsp;责任人
												<input type="text" name="document.partyAstaffnames" 
													id="partyAstaffnames" 
													class="inputbottom" readonly />
											&nbsp;身份证号
												<input type="text" name="document.staffIdentityCardA"  style="width:150px;"
													class="inputbottom" readonly  id="staffIdentityCardA" />
												<input type="button" class="btncon" onclick="importGY2('ea/documentcommon/ea_getSocialInfoList.jspa','partyA')"/>
												<input type="button" class="btndel" onclick="clears('partyA');"/>	
												
												
											</div>
											<input type="hidden" name="document.partyAstaff"
												id="partyAstaff" value="${document.partyAstaff}" />
						</TD>
	

					</TR>
					
					<TR class="nodirect hetong">
						
						<TD style="font-size: 11pt" width=177 nowrap>
							
						</TD>
						<TD>
							<SPAN class="ltsep"></SPAN>
						</TD>
						<TD style="font-size: 11pt" >
							<div>
							乙&nbsp;&nbsp;&nbsp;&nbsp;方：
												
												&nbsp;&nbsp;公司&nbsp;
												<input type="text" name="document.partyBName" id="partyBName"
													 class="inputbottom" readonly style="width:150px;"/>
												<input type="hidden" name="document.partyB" id="partyB"
													 />
												<input type="button" class="btncon" onclick="importGY2('ea/documentcommon/ea_getListContactCompany.jspa','partyBD')"/>	
												<input type="button" class="btndel" onclick="clears('partyBD');"/>	
												
												&nbsp;责任人
												<input type="text" name="document.partyBstaffnames"
													id="partyBstaffnames" 
													class="inputbottom" readonly />
												&nbsp;身份证号
												<input type="text" name="document.staffIdentityCardB"
													id="staffIdentityCardB"  style="width:150px;"
													class="inputbottom" readonly />
												<input type="button" class="btncon" onclick="importGY2('ea/documentcommon/ea_getSocialInfoList.jspa','partyB')"/>	
												 <input type="button" class="btndel" onclick="clears('partyB');"/>	
												
												
											</div>
											<input type="hidden" name="document.partyBstaff"
												id="partyBstaff" value="${document.partyBstaff}" />
						</TD>
	

					</TR>
					
					<TR class="nodirect hetong">
						
						<TD style="font-size: 11pt" width=177 nowrap>
							
						</TD>
						<TD>
							<SPAN class="ltsep"></SPAN>
						</TD>
						<TD style="font-size: 11pt">
							有&nbsp;效&nbsp;&nbsp;期： 从
							<input type="text" name="document.startValidity" id="startDate"
								class="inputbottom"
								onFocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', onpicked:function(){endDate.focus();}})"
								readonly 
								title="请选择生效日期" />

							至
							<input type="text" name="document.endValidity" id="endDate"
								onFocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'startDate\')}'})"
								readonly class="inputbottom"
								
								title="请选择失效日期" />
							&nbsp;&nbsp;&nbsp;	
						    所属项目规划：<input type="text" class="inputbottom" name="document.projectName"  id="projectName" readonly/><input type="hidden" name="document.journalNum" id ="journalNum" />
						    <input type="button" class="btncon" onclick="importGY2('/ea/productdesign/ea_getListProductdesign.jspa','project')"/>	
						   <input type="button" class="btndel" onclick="clears('project');"/>	

						</TD>
					


					</TR>
				
		
			</TABLE>
     
    </div>
    <div id="content">
        <div id="textcontent" style="width: 100%; height: 800px;">
         
            <!--**************   卓正 PageOffice组件 ************************-->
             <po:PageOfficeCtrl id="PageOfficeCtrl1" />
        </div>
    </div>
    <div id="footer">
        <hr width="1000" />
        <div>
         <TABLE width="80%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<TR>
					<TD width="100%" align="center" style="font-size: 15px;">
						<strong>京ICP备10034132号-2 版权所有 北京天太世统科技有限公司</strong>
						<br />
						Copyright 2009-2010 www.ttst2010.cn Corporation, All Rights
						Reserved
						<br />
						公司地址：北京市东直门外大街宇飞大厦801室 服务热线：64164005
					</TD>
				</TR>
			</TABLE>
    </div>
    
    
    </form>
    
    
    <div id="socialJqm2" class="jqmWindow"
			style="width: 73%;height: 400px; absolute; display: none; left: 20%; top:1%; z-index: 9999; background: #DAE7F6; overflow-y: hidden;">
			<div style="background: #DAE7F6; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="type2" value="" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="270px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;">
			</iframe>
			<div style="height: 30px; border: 1;">
				<input type="hidden" value="" id="markid" />
				<input type="button" class="input-button" id="DaoRuFanqd"
					onclick="DaoruConfirm2();" value=" 确定 "
					style="cursor: hand; border: 1; margin-left: 320px; height: 25px; width: 60px" />
				<input type="button" class="input-button" id="DaoRuFan"
					onclick="cancelJqm2();" value=" 关闭 "
					style="cursor: hand; border: 1; margin-left: 40px; height: 25px; width: 60px" />
			</div>

		</div>
		<!--JS遮罩层-->
		<div id="fullbg"></div>
		 <script type="text/javascript">
		 
        function AfterDocumentOpened() {
     
            document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(4, false); // 禁止另存
        
          
        }
    </script>
</body>
</html>
