<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache"/>
		<title>组织机构--部门</title>
        <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<!-- 所有部门的组织机构 -->
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>		
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>		
	</head>
	<body>
		<form method="post" name="sortchildren" id="sortchildren"><%-- 不告诉你--%>
			<input type="submit" name="submit" style="display:none"/>
			<input id="oID" name="organizationID" type="hidden"/>
			<input id="oName" name="organizationName" type="hidden"/>
		</form>
		<form name="organizationform" id="organizationform" method="post">
			<input type="submit" name="submit" style="display:none"/>
			<input name="porganization.organizationID" type="hidden"
				id="porganizationID" />
			<input name="porganization.organizationName" type="hidden"
				id="porganizationName" />
			<input name="organization.organizationName" type="hidden"
				id="organizationName" />
			<input name="organization.organizationID" type="hidden"
				id="organizationID" />
			<div class="main_main">
							<table class="flexme11">
								<thead>
							    	   	<tr>
							    	   		<th width="30" align="center">
												选择
											</th>
							             	<th width="30" align="center">
												序号
											</th>
											<th width="100" align="center">
												机构编号
											</th>
											<th width="100" align="center">
												部门名称
											</th>
											<th width="80" align="center">
												是否微分金店
											</th>
											<th width="100" align="center">
												岗位编号
											</th>											
											<th width="100" align="center">
												岗位名称
											</th>
											<th width="100" align="center">
												职务编号
											</th>
											<th width="100" align="center">
												职务名称
											</th>
											<th width="120" align="center">
												负责工作范围
											</th>	
											<th width="80"  align="center">
												操作
											</th>
							            </tr>
							    </thead>
							    <%
									int number = 1;
								%>
							    <tbody>
							         	<s:iterator value="pageForm.list">
							    	<tr ondblclick="toedit('<s:property value="organizationID"/>')" id="<s:property value="organizationID"/>">
							    		 <td>
                             			   <input type="radio" name="a"  class="JQuerypersonvalue" value="<s:property value="organizationID"/>"/>
                          				  </td>
							            	<td><%=number%></td>
							            <td><span  id="ocode">
											<s:property value="ocode" /></span>
										</td>
										<td><span  id="organizationName">
											<s:property value="organizationName" /></span>
										</td>
										<td><span  id="isWfj">
											<s:property value="isWfj" /></span>
										</td>
										<td><span  id="opostCode">
											<s:property value="opostCode" /></span>
										</td>
										<td><span  id="opostName">
											<s:property value="opostName" /></span>
										</td>
										<td><span  id="odutiesID">
											<s:property value="odutiesID" /></span>
										</td>
										<td><span  id="odutiesName">
											<s:property value="odutiesName" /></span>
										</td>
										<td>
											 <s:select list="%{#request.SInterfaceList}"  listKey="interfaceUrl"  listValue="interfaceName"  name="organizationUrl" theme="simple" disabled="true">
                                             </s:select>
										</td>
										<td>
										   <a href="#" onclick="toedit('<s:property value="organizationID"/>')"><img src="<%=basePath%>images/ea/main/edit.gif" width="16" height="16"  title="修改" border="0"/></a>
	                                       <a href="#" onclick="deleteid('<s:property value="organizationID"/>')"><img src="<%=basePath%>images/ea/main/gtk-del.png" width="16" height="16" title="禁用" border="0"/></a>
										</td>
										<%
											number++;
										%>
							            </tr>
							            </s:iterator>
							    </tbody>
							</table>
						<c:import url="../../../page_navigator.jsp">
							<c:param name="actionPath"
								value="ea/organization/ea_subordinateList.jspa?organizationID=${organizationID}&pageNumber=${pageNumber}&search=${search}"></c:param>
						</c:import> 					
				<s:token />
			</div>
		</form>
		<iframe src="" name="mainframe" marginwidth="0"
						scrolling="no" marginheight="0" frameborder="0" style="height:0;width:100%;"
						id="mainframe" border="0" framespacing="0" noresize="noResize"
						vspale="0"></iframe> 
				<!--搜索窗口 -->
		<div class="jqmWindow " style="width: 270px;right: 35%; top:20%" id="jqModelSearch">
            <form name="postSearchFormss" id="postSearchFormss" method="post">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
                    <tr>
                        <td>
                            查询条件
                        </td>
                    </tr>
                    <tr>
                        <td>
                           下属机构编号：
                        </td>
                        <td>   
                          <input   name="organization.ocode" />      
                        </td>
                    </tr>
					<tr>
                        <td>
                           部门名称：
                        </td>
                        <td>
                           <input  name="organization.organizationName" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                           岗位编号：
                        </td>
                        <td>
                           <input  name="organization.opostCode" />
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
		<div id="jqmWindow2" class="jqmWindow"
			style="width: 95%; height: 400px; absolute; display: none; left: 1%; top: 20px; background: #eff">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="myform" />
				<input style="display: none;" id="parm1" />
				<input style="display: none;" id="parm2" />
				<input style="display: none;" id="parm3" />
				<input style="display: none;" id="parm4" />
			</div>
			<iframe name="ifr" id="ifr" style="width:100%;height:0;"
				frameborder="0"></iframe>
			<div align="center">
				<input type="button" class="input-button" id="isBack" value="返回"
					style="cursor: hand" />
				<input type="button" class="input-button" id="isSubmit"
					value="确定" style="cursor: hand" />
			</div>
		</div>
		
<script type="text/javascript">
var  personurl='';
var  organizationPID='';
var  basePath='<%=basePath%>';
var  companyName='';
var  search= '${search}';   
$(function(){
	$(".jqmWindow").jqm({
                    modal: true,// 限制输入（鼠标点击，按键）的对话  
                    overlay: 20 // 遮罩程度%  
                }).jqmAddClose('.close');// 添加触发关闭的selector
	//保存或修改后,修正机构树
	var oid = '${organization.organizationID}';
	var oname = '${organization.organizationName}';
	if(window.parent.tree.getItemText(oid) != '0'){
		window.parent.tree.setItemText(oid,oname,0);
	}else if(oid != ""){
		window.parent.tree.insertNewChild(parent.treeid,oid,oname,0,0,0,0);
	}
	//保存或修改后,修正机构树
	
    $('.flexme11').flexigrid({
        allDouble:true,
		height: 100,
		width: 'auto',
		minwidth: 30,
		title: '当前机构--' +parent.tree.getSelectedItemText(),
		minheight: 80 ,
		  buttons: [ 
		 {
            name: '添加下级',
            bclass: 'add',
			onpress : action//当点击调用方法
        }, {
            // 设置分割线  
            separator: true
        }, {
            name: '禁用当前机构',
            bclass: 'delete', 
			onpress : action//当点击调用方法
        }, {
            separator: true
        }, {
            name: '修改当前机构',
            bclass: 'edit',
			onpress : action//当点击调用方法
        }, {
            separator: true
        },{
            name: '导出',
            bclass: 'excel',
			onpress : action//当点击调用方法
        }, {
            separator: true
        },{
            name: '查询',
            bclass: 'mysearch',
			onpress : action//
        },{
            separator: true
        },{
            name: '设置每页显示条数',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        },{
            separator: true
        }/*,{
            name: '机构银行帐号',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        },{
            separator: true
        }
        ,{
            name: '机构负责人',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        },{
            separator: true
        },{
            name: '人员分配管理',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        }*/
        ]
	});
    function action(com, grid){
        switch (com) {
            case '添加下级':
               if ("" == parent.tree.getSelectedItemId()) {
                    alert("请选择机构"); 
                    return;
                }
                open(basePath + "ea/corganization/ea_toAdd.jspa?pageNumber=${pageNumber}&porganization.organizationID="
					 + parent.treeid + "&porganization.organizationName=" + parent.treename + "&organization.organizationID=");
				
                //$("#porganizationID").attr("value", parent.treeid);
               // $("#porganizationName").attr("value", parent.treename);
               // $("#organizationID").remove();
				//$("#organizationform").attr("action",basePath + "ea/organization/ea_subordinateToAdd.jspa?pageNumber=${pageNumber}");
				//document.organizationform.submit.click();
                break;
            case '禁用当前机构':
                var treeid = parent.treeid;
                 if ("" == parent.tree.getSelectedItemId()) {
                    alert("请选择机构");
                    return;
                }
                var companyID = parent.companyID;
                if (treeid == companyID) {
                    alert('不能禁用单位');
                    return;
                }
                if (confirm("禁用节点会丢失其下的数据？是否禁用？")) {
                    window.parent.tree.deleteItem(treeid);
					$("#organizationform").attr("action",basePath + "ea/organization/t_ea_subordinateDel.jspa?pageNumber=${pageNumber}&organizationID=" +
                    treeid +
                    "&selectedtreeID=" +
                    parent.treeid);
					document.organizationform.submit.click();
                }
                break;
            case '修改当前机构':
                treeid = parent.treeid;
                companyID = parent.companyID;
                 if ("" == parent.tree.getSelectedItemId()) {
                    alert("请选择机构");
                    return;
                }
                if (companyID == treeid) {
                    alert("不能修改单位");
                    return;
                }
                parentid = parent.parentid;
                parentname = parent.parentname;
                open(basePath + "ea/corganization/ea_toAdd.jspa?pageNumber=${pageNumber}&porganization.organizationID="
					 + parentid + "&porganization.organizationName=" + parentname + "&organization.organizationID="
					 + treeid);
                break;
//			case '排序下属机构':
//				$("#oID").val(parent.treeid);
//				$("#oName").val(parent.treename);
//				$("#sortchildren").attr("action",basePath + "ea/organization/ea_toSortChildOrganization.jspa?")
//				document.sortchildren.submit.click();
//				break;
			case '导出':
			 if ("" == parent.tree.getSelectedItemId()) {
                    alert("请选择机构");
                    return;
                }
				treeid= parent.treeid;
   			    url= basePath + "ea/organization/ea_showExcel.jspa?organizationID="+treeid;
				open(url);
				break;
			case '设置每页显示条数':
			    var url= basePath + "ea/organization/ea_subordinateList.jspa?organizationID=${organizationID}&search="+search;
				numback(url);
				break; 
			case '查询':
                    $("#jqModelSearch").jqmShow();
                   break;	
		case '机构银行帐号':            //机构设置
			
               if (organizationPID == "") {
                             alert("请选择具体单位！");
                              return;
                         }
               personurl = basePath + "ea/institutionsregistration/ea_getListInstitutionsRegistration.jspa?institutionsRegistration.organizationPID="+organizationPID+"&pageNumber="+pageNumber;
               $("#mainframe").css({"height":"auto"}).attr("src", personurl);       
               $(window).resize();         
              break;
		case '机构负责人':            //机构设置			
               if (organizationPID == "") {
                   alert("请选择具体单位！");
                   return;
               }
               personurl = basePath + "ea/agencies/ea_getListAgencies.jspa?pageNumber="+0+"&agencies.organizationPID="+organizationPID;
               $("#mainframe").css({"height":"auto"}).attr("src",personurl);
               $(window).resize();  
              break;
        }
    }
    $("#org").text(parent.treename);
    $(document).ready(function() {
                // 返回
			$("#isBack").click(function(){
                  $("#jqmWindow2").jqmHide();
              });
				// 选择确定
                $("#isSubmit").click(function(){
                    var myfrom =$("#myform",$("#jqmWindow2")).attr("value");
                    var parm1 = $("#parm1",$("#jqmWindow2")).attr("value");
                    var parm2 = $("#parm2",$("#jqmWindow2")).attr("value");
                    var parm3 = $("#parm3",$("#jqmWindow2")).attr("value");
                    var parm4 = $("#parm4",$("#jqmWindow2")).attr("value");
					//弹出框的页面必须声明opertionID这个参数接收id
                    var value1 = window.frames["ifr"].opertionID;
                    if(value1 == ""){
                         alert("请选择");
                         return;
                    }
					//弹出框的页面存在于span中才取得到
                    var value2 = window.frames["ifr"].$('tr#'+value1).find("span#"+parm2).text();
                    var value3 = window.frames["ifr"].$('tr#'+value1).find("span#"+parm3).text();
                    var value4 = window.frames["ifr"].$('tr#'+value1).find("span#"+parm4).text();
                   // alert(value1+"--"+parm1+"--" +myfrom )
	                if(parm1 != "")
	                   window.frames["mainframe"].$("#"+myfrom).find("#"+parm1).attr("value",value1);
                    if(parm2 != "")
                    	window.frames["mainframe"].$("#"+myfrom).find("#"+parm2).attr("value",value2);
                    if(parm3 != "")
                    	window.frames["mainframe"].$("#"+myfrom).find("#"+parm3).attr("value",value3);
                    if(parm4 != ""){
                    	if(value4 != ""){
                    		window.frames["mainframe"].$("#"+myfrom).find("#"+parm4).attr("value",value4).attr("readonly",true);
                    	}else{
                    		window.frames["mainframe"].$("#"+myfrom).find("#"+parm4).attr("value",value4).attr("readonly",false);
                    	}
                    }
                    	
                    $("#ifr").attr("src","");
                    $("#jqmWindow2").jqmHide();
              });
           });
    
    $(".tt").click(function(){
        $(".tt").css("background", "#FFFFFF");
        $(this).css("background", "#eaf1f7");        
    });
    $("#tosearch").click(function(){
	  	 $("form :input").trigger("blur");
	  	 if ($("form .error").length) {
   	   		  return false;
 		   }
          $("#postSearchFormss").attr("action", basePath+"ea/organization/ea_toSearch.jspa?pageNumber=${pageNumber}&organizationID=${organizationID}");
          document.postSearchFormss.submit.click();
      });
	 $(".flexme11").find("select").each(function(){
					$s = $(this).hide();
					$o = $("<span/>").text($s.find("option:selected").text());
					$o.insertAfter($s);					
				});
	$(".flexme11 tr[id]").click(function(){
                    $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
                    organizationPID =this.id;
                    if(personurl){                              
                    $("#mainframe").attr("src",personurl + organizationPID);
                    }       
                    companyName = $(this).find("span#organizationName").text();      
                           
                });
});
//function toedit(organizationID) {
//	$("#organizationID").attr("value", organizationID);
//	$("#porganizationID").attr("value", parent.treeid);//后查不到就代表上级是单位.查到了就用查到的替换
//	$("#porganizationName").attr("value", parent.treename);
//	$("#organizationform").attr("action",basePath + "ea/organization/ea_subordinateToAdd.jspa?pageNumber=${pageNumber}");
//	document.organizationform.submit.click();
//}
function toedit(organizationID) {
	open(basePath + "ea/corganization/ea_toAdd.jspa?pageNumber=${pageNumber}&porganization.organizationID="
			 + parent.treeid + "&porganization.organizationName=" + parent.treename + "&organization.organizationID="
			 + organizationID);
}
//子窗口调用
function refresh(){
	window.location.reload();
}
function deleteid(treeid) {
	if ("" == treeid)
		treeid = parent.treeid;
	if ("" == treeid) {
		alert("请选择单位");
		return;
	}
	companyID = parent.companyID;
	if (treeid == companyID) {
		return;
	}
	if (confirm("禁用节点会丢失其下的数据？是否禁用？")) {
	     window.parent.tree.deleteItem(treeid);
		 $("#organizationform").attr("action", basePath + "ea/organization/t_ea_subordinateDel.jspa?pageNumber=${pageNumber}&organizationID="
				+ treeid+"&selectedtreeID="+parent.treeid);
	document.organizationform.submit.click();
	}
}
////function alee(organizationID) {
///	$("#organizationID").attr("value", organizationID);
//////	$("#porganizationID").attr("value", parent.treeid);//后查不到就代表上级是单位.查到了就用查到的替换
//	$("#porganizationName").attr("value", parent.treename);
//	$("#organizationform").attr("action",  basePath + "ea/organization/ea_subordinateToAdd.jspa?pageNumber=${pageNumber}");
//	document.organizationform.submit.click();
//}
</script>
<script type="text/javascript">
   $(function(){
       setTimeout(function(){ 
		   var _height = $(window).height();		
		   if($("#mainframe").height() > 0){
		       $(".bDiv").css({"height": _height /2 - 30 - 26 - 50 + "px"});
		       $("#mainframe").css({"height": _height / 2 - 20 + "px"});
		   }else{		    
		       $(".bDiv").css({"height": _height - 31 - 30 - 26 - 30 + "px"});
		       $("#mainframe").css({"height": 0 + "px"});
		   }
		},100);
    
	    $(window).resize(function(){ 
		setTimeout(function(){ 
		    var _height = $(window).height();		
		    if($("#mainframe").height() > 0){
		        $(".bDiv").css({"height": _height /2 - 30 - 26 - 50 + "px"});
			    $("#mainframe").css({"height": _height / 2 - 10 + "px"});
		    }else{		    
			$(".bDiv").css({"height": _height - 31 - 30 - 26 - 30 + "px"});
			$("#mainframe").css({"height": 0 + "px"});
		    }
		},100);
	    }); 
   });
</script>   
</body>
</html>