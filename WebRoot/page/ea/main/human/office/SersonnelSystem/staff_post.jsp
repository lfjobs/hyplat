        <%@page import="java.util.Date"%>
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>岗位分配-售前</title>
<!-- 售前  人员入职 -->
<link href="<%=basePath%>css/ea/human/staff_post.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link rel="shortcut icon" href="<%=basePath%>js/tree/common/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css" type="text/css" media="screen" />
<script  src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script  src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css"/>
<script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
</head>
<body>
<form method="post" id="staffform" name="cstaffForm">
<s:token></s:token>
<input type="hidden" name="audition.auditionID" value="<%=request.getParameter("auditionID")%>" />
</form>
<div id="body_02">
          <table width="100%" height="26" align="center" cellspacing="0" cellpadding="1" style="font-size:12px;" class="bannb_01">
            <tr>
                <td height="24" align="left" valign="top" class="txt01" >&nbsp;岗位分配</td>
            </tr>
          </table>
  			<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px; margin-bottom:5px;" class="clear">
              <tr>
                <td width="1%" height="26">&nbsp;</td>
                <td width="2%"><div class="list_add01"></div></td>
                <td width="97%"><span class="link02" id="staffname">岗位分配-<%=request.getParameter("staffName")%></span></td>
              </tr>
            </table>
            <table width="70%" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px; margin-bottom:5px;" >
              <tr>
                <td width="23%" height="30"></td>
                <td>
                	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                		<tr>
                			<td width="10%" align="right">员工类别：</td>
			             	<td width="24%"><select id="staffType" style="width:140px;"></select></td>
			             	<td width="10%" align="right">职务级别：</td>
			             	<td>
			             		<select class="PayScale" style="width:280px;">
			             			<option value="">--请选择--</option> </select>
			             		<a href="<%=basePath%>/ea/payscale/ea_getListPayScale.jspa">设置</a>
			             	</td>
                		</tr>
                	</table>
                </td>
              </tr>
              
              <tr>
              	<td align="right" height="30">选择专岗：</td>
              	<td id="iskelong">
              		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              			<tr id="kelong">
              				<td width="10%" height="30" align="right">选择部门：</td>
			                <td width="24%" id="upper"><input type="text" id="oraganizationName" style="width:120px;" readonly="readonly" value="请选择部门"/><a href="#" onclick="javascript:getPID($(this).parent().parent().attr('id'))"><img src="<%=basePath %>images/up.jpg" style="border: 0;"/></a>
                    		</td>
			              	<td width="10%" align="right">选择岗位：</td>
			              	<td>
			              		<select id="organizationPost" style="width:200px;">
			              			<option value="">请先选择部门</option></select>
			                	<a href="<%=basePath%>/ea/departmentpost/ea_toSaveDepartmentPost.jspa?reValue=1&staffName=<%=request.getParameter("staffName")%>&staffID=<%=request.getParameter("staffID")%>&auditionID=<%=request.getParameter("auditionID")%>">设置</a>
			              	</td>
              			</tr>
              		</table>
              	</td>
              </tr>
              
              <tr>
              	<td align="right" valign="top">
              		<input type="button" class="btn02 addParttime" value="添加兼岗" style="margin-top:5px;"/>
              	</td>
              	<td>
              		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              			<tr id="beforekelong"></tr>
              		</table>
              	</td>
              </tr>
            </table>
			<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                 <td align="center">
                  <input type="button" class="btn02 jquerysub"  name="button" value="确定" />
                  <input type="button" class="btn02 clo" name="bu" value="关闭" /></td>
                </tr>
  </table>
</div>
<div style="display:none;width: 200px; height: 240px;position: absolute;left: 36.5%;z-index: 4 ; background-color:#e1ecfc; filter : Alpha(opacity=100);"
				id="jqModel"></div>
</body>
 <script>
    	var basePath = "<%=basePath%>";
    	var staffID='<%=request.getParameter("staffID")%>';
    	var staffCategoryID="";
    	var codeID="";
 		var codeValue="";
    	var select=1; 
    	var organizations = new Array();
    	var deptposts = new Array();
    	var comID = '${account.companyID}';
    	var index = 0; //删除兼岗个数
    	var arrnum = 0; //删除时判断条件
    	
		var companyName='<%=request.getParameter("companyName")%>';
		var parentID;
		var treeid = null;
		var treename;
		var parentid;
		var parentname;
		var organizationid;
		var tree;
		var me = document.getElementById("oraganizationName").value;
		tree = new dhtmlXTreeObject("jqModel", "100%", "100%", 0);
		tree.enableDragAndDrop(false);
		tree.enableHighlighting(1);
		tree.enableCheckBoxes(0);
		tree.enableThreeStateCheckboxes(false);
		tree.setSkin(basePath + 'js/tree/dhx_skyblue');
		tree.setImagePath(basePath + "js/tree/codebase/imgs/");
		tree.loadXML(basePath + "js/tree/common/tree_b.xml");
		tree.insertNewChild("0", comID, companyName, 0, 0, 0, 0);
		$.ajaxSetup({async:false});
		
		tree.setOnClickHandler(function() {
			getDatas();
		});
		
		function getPID(heights) {
	    	if(document.getElementById("jqModel").style.display=="none"){
	    		$("#"+heights).find("img","#upper").attr("src","<%=basePath %>images/down.jpg");
	    		if(heights == 'kelong'){
	    			$("#jqModel").css("top","28%");
	    		}else{
	    			var hei = 28 + heights.substring(6)*7;
	    			if(arrnum != 0 && heights.substring(6) > arrnum){
	    				hei = 28 + (heights.substring(6)-index)*7;
	    			}
	    			$("#jqModel").css("top",hei+"%");
	    		}
				$("#jqModel").show();
			}else{
				$("#"+heights).find("img","#upper").attr("src","<%=basePath %>images/up.jpg");
				$("#jqModel").hide();
			}
			tree.setOnDblClickHandler(function(){
				if(tree.getSelectedItemId() != comID){
					if(heights != '' && $("#"+heights).find("img","#upper").attr("src") == "<%=basePath %>images/down.jpg"){
						$("#"+heights).find("#oraganizationName").attr("value",treename);
						if(heights == 'kelong'){
							organizations[0] = treeid;
						}else{
							organizations[heights.substring(6)] = treeid;
						}
						getOrgpost(treeid,"#"+heights);
					}
					heights = '';
					$("#jqModel").hide();
				}
			});
		}
		function getDatas(){
			treeid = tree.getSelectedItemId();
			treename = tree.getItemText(treeid);
			parentid = tree.getParentId(treeid);
			parentname = tree.getItemText(parentid);
			tree.deleteChildItems(treeid);
			var url1 = basePath + "ea/organization/sajax_ea_getOrganizationList.jspa?organizationID="+treeid+"&date="+new Date().toLocaleString();
			$.ajax({
				url: encodeURI(url1),
				type: "get",
				async: true,
				dataType: "json",
				success: function cbf(data){
						var member = eval("(" + data + ")");
						var nologin = member.nologin;
	                  	if(nologin){
	                  		document.location.href =basePath+"page/ea/not_login.jsp";
	                    }
						var organizationList = member.organizationList;
						if (null == organizationList) {
							return;
						}
						for (var i = 0; i < organizationList.length; i++) {
							tree.insertNewChild(treeid,
									organizationList[i].organizationID,
									organizationList[i].organizationName,
									0, 0, 0, 0);
	
						}
				},
				error: function cbf(data){
				alert("数据获取失败！")
				}
			});
		}
    	
    	//员工类别
       	var url = basePath +"ea/saudition/sajax_n_ea_getBillID.jspa?date="+new Date();
        $.ajax({
                    url: url,
                    type: "get",
                    async: false,
                    dataType: "json",
                    success: function cbf(data){
		           		var member = eval("(" + data + ")");
		           		var nologin = member.nologin;
						if(nologin){
		                	document.location.href ="<%=basePath%>page/ea/not_login.jsp";
		                }
		           		var staffTypeList = member.staffTypeList;
		           		if (null != staffTypeList) {
		           			var htmlStr="<option value=''>请选择</option>";
			               	for (var i = 0; i < staffTypeList.length; i++) {
			               		htmlStr+="<option value='"+staffTypeList[i].codeID+"'>"+staffTypeList[i].codeValue+"</option>"
			             	}
			             	$("#staffType").html(htmlStr);
			          if(staffCategoryID!="")
	                $("#staffType").find("option[value='"+staffCategoryID+"']").attr("selected","selected");
          				}
           			},error: function cbf(data){
                          		alert("数据获取失败！")
                    }
        });
    	
    	//获取工资级别
       	var url1 = basePath +"ea/soincumbent/sajax_n_ea_getStaffListForPost.jspa?staffID="+staffID+"&date="+new Date();
        $.ajax({
                url: url1,
                type: "get",
                async: true,
                dataType: "json",
                success: function cbf(data){
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if(nologin){
					document.location.href ="<%=basePath%>page/ea/not_login.jsp";
				}
           		var paylist = member.paylist;
				if (null != paylist) {
					for (var j = 0; j < paylist.length; j++) {
						$op = $("<option/>")
						$op.val(paylist[j].payScaleID).text(paylist[j].position+"/"+paylist[j].scale);
						$(".PayScale").append($op);
		     		}
				}
       			},error: function cbf(data){
                      alert("数据获取失败！")
                }
        });
    	
		//添加兼岗
		$(".addParttime").click(function(){
			if($("#iskelong").find("#organizationPost").val() == ''){
				alert("请先选择专岗！");
				return;
			}
            //克隆一行并添加name
            $("#beforekelong").before($("tr#kelong").clone().attr("id", "kelong" + select).addClass("checkgoods"));
            $("tr#kelong" + select).find("#organizationPost").next().after("<a href='#' id='rem' style='margin-left:5px;'>删除</a>");
            
            $("tr#kelong" + select).find("#oraganizationName").val("请选择部门");
            var opt = "<option value=''>请先选择部门</option>";
            $("tr#kelong" + select).find("#organizationPost").html(opt);
            $("tr#kelong" + select).show();
            select++;
            
			$(".checkgoods").find("#organizationPost").change(function() { // 获取选中兼岗
				if($(this).val() == $("#iskelong").find("#organizationPost").val()){
					$(this).find("#firstpost").attr("selected","selected");
					alert("兼岗不能与专岗相同！");
					return false;
				}
				deptposts[$(this).parent().parent().attr("id").substring(6)] = $(this).val();
			})
			
			//点击删除事件
			$(".checkgoods").find("#rem").click(function(){
				$(this).parent().parent().remove();
				if(arrnum != $(this).parent().parent().attr("id").substring(6)){
					index ++;
				}
				arrnum = $(this).parent().parent().attr("id").substring(6);
				organizations[arrnum] = ''; //兼岗所在部门设为空
				deptposts[arrnum] = ''; //兼岗设为空
			})
    	})
		
		$("#iskelong").find("#organizationPost").change(function() { // 获取选中专岗
			$p = $(this);
			var err = '';
			$(".checkgoods").find("#organizationPost").each(function(){
				if($p.val() == $(this).val()){
					$p.find("#firstpost").attr("selected","selected");
					err = 1;
				}
			})
			if(err){
				alert("专岗不能与兼岗相同！");
				return false;
			}
			deptposts[0] = $p.val();
		})
		
		//获取岗位调用
		function getOrgpost(orgID,trval){
			var url = basePath +"ea/departmentpost/sajax_ea_getOrganizationPost.jspa?departmentPost.organizationID="+orgID+"&date="+new Date();
			$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var persons = member.departmentPostlist;
						var str = "";
						if(persons.length == 0){
							str = "<option value=''>此部门无岗位</option>";
						}else{
							str = "<option value='' id='firstpost'>请选择岗位</option>";
							for(var i=0;i<persons.length;i++){
								var obj = persons[i].postName;
								var objID = persons[i].depPostID;
								str += "<option value='"+objID+"'>"+obj+"</option>";
							}
						}
						$(trval).find("#organizationPost select").empty();
						$(trval).find("#organizationPost").html(str);
					}
			});
		}
		
        //保存按钮
        $(".jquerysub").click(function(){
        	codeID=$.trim($("#staffType").attr("value"));
         	codeValue=$.trim($("#staffType").find("option:selected").text());
        	if(codeID == ''){
        		alert("请选择员工类别!");
	          	return false;
        	}
            var payScaleID =$("select.PayScale").attr("value");
            if(payScaleID == ""){
	          	alert("请选择职务级别!");
	          	return false;
            }
            var orgPost = $("#organizationPost").find(":selected").val();
            if(orgPost == ""){
	             alert("请选择专岗！");
	             return false;
            }
            
            var errs='';
            $(".checkgoods").find("#organizationPost").each(function(){
				if($(this).parent().parent().find("#organizationID").val() != '' && $(this).val() == ''){
	             	errs=1;
				}
			})
			if(errs){
				alert("选择部门后，兼岗不能为空！");
				return false;
			}
			
            $("#staffform").attr("action",basePath+"ea/saudition/t_ea_saveCos.jspa?pageNumber=${pageNumber}&csp.payScaleID="
            										+payScaleID+"&status=2&staffID="+staffID+"&codeID="+codeID+"&codeValue="
            										+codeValue+"&organizations="+organizations+"&deptposts="+deptposts);
            document.getElementById("staffform").submit(); 
        })
        
        //关闭按钮
        $(".clo").click(function(){
          document.location.href= basePath+"ea/saudition/ea_getauditionList.jspa?pageNumber=${pageNumber}&status=2";
        })
    </script>
</html>