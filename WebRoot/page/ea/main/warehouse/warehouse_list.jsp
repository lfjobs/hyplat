<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>安全检查类别</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <!-- 办公室办公--后勤管理-- 安全检查类别表-->
		
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/warehouse/warehouse_list.js"  type="text/javascript"></script>
<script type="text/javascript">
var token = 0 ;
var basePath = '<%=basePath%>';
var id = '';
var ppageNumber =${pageNumber};
var  search='${search}';  
var aryParam;
var notoken=0;
</script>

<script type="text/javascript">

	function toCCode(ary){
	 aryParam=ary;
	 $("span#kuName").text(aryParam=='1'?"库名称:":aryParam=='2'?"区名称:":aryParam=='3'?"架名称:":"位名称:");
	 var pware = $.trim($("select#pware").find("option:selected") .val()) 
 	 var parea = $.trim($("select#parea").find("option:selected") .val())
  	 var pframe = $.trim($("select#pframe").find("option:selected") .val()) 
		if(ary=='2'&&pware==""){
			alert("添加 (区) 必须选择 (库)!!!")
			return;
		}
		if(ary=='3'&&parea==""){
			alert("添加 (架) 必须选择 (区)!!!")
			return;
		}
		if(ary=='4'&&pframe==""){
			alert("添加 (位) 必须选择 (架)!!!")
			return;
		}
    $(".jqmWindow").jqmHide();
    $("input#pware",$("#newccode")).attr("value",pware)
    $("input#parea",$("#newccode")).attr("value",parea)
     $("input#pframe",$("#newccode")).attr("value",pframe)
    $("input#wareType",$("#newccode")).attr("value",aryParam)
    $("#newccode").jqmShow();
}

//AJAX保存仓库管理
function saveCCode(){
	if(notoken){
	alert("正在加载数据")
	return;
	}
	notoken=1;
  var pware =  $("input#pware",$("#newccode")).attr("value");
  var parea = $("input#parea",$("#newccode")).attr("value");
   var pframe = $("input#pframe",$("#newccode")).attr("value");
   var wareType = $("input#wareType",$("#newccode")).attr("value");
   var wareName=$("#wareName",$("#newccode")).attr("value")
   if($.trim(wareName)==""){
   		alert("不能为空!")
   		notoken=0;
   		return;   		
   }
  var url =  basePath+"/ea/warehouse/sajax_ea_saveWareHouseAjax.jspa?warehouse.pware="+pware+"&warehouse.parea="+parea+"&warehouse.pframe="+pframe+"&warehouse.wareType="+wareType+"&warehouse.wareName="+wareName+"&date="+new Date();
        $.ajax({
						                        url: encodeURI(url),
						                        type: "get",
						                        async: true,
						                        dataType: "json",
						                        success: function cbf(data){
						                         var member = eval("(" + data + ")");
						                         var nologin = member.nologin;
						                         var alerts=member.alert;
						                         if(alerts!=""){
						                         	alert(alerts);
						                         	$("#wareName",$("#newccode")).alert("value","");
						                         	notoken=0;
						                         	return;
						                         }
								                  if(nologin){
								                  document.location.href =basePath+"page/ea/not_login.jsp";
								                  }
											      var warehouse = member.warehouse;
											      $("#newccode").jqmHide();
											      $op = $("<option selected='selected'/>");
											      $op.attr("value",warehouse.wareID).text(warehouse.wareName);
											       $("select[title='"+wareType+"']").append($op);
											      $("#jqModel").jqmShow();
											      $("input#wareName").attr("value","");
											      notoken=0;
											      token=1;
											       $("select[title='"+wareType+"']").trigger("change");
						                        },
						                        error: function cbf(data){
						                        	 notoken=0;
						                           alert("数据获取失败！")
						                        }
						                    });
}
$(document).ready(function() {
//取消返回
$("input.JQueryreturn1").click(function(){// 取消
					$("input#wareName").attr("value","");
                    $("#newccode").jqmHide();
                   $("#jqModel").jqmShow();
         });   
                //change事件加载下一级内容
                $("select.warehouse").change(function(){
                		if(notoken){
							alert("正在加载数据")
							return;
						  }
						notoken=1;
                		if($.trim($(this).attr("title"))=='4'){
                		notoken=0;
                		return;
                		}
                		aryParam=$.trim($(this).attr("title"));
                		var wareID=$.trim($(this).children("option:selected").val());
                		 var url =  basePath+"/ea/warehouse/sajax_ea_getListWareHouseZiAjax.jspa?warehouse.wareID="+wareID+"&warehouse.wareType="+aryParam+"&date="+new Date();
       							 $.ajax({
						                        url: encodeURI(url),
						                        type: "get",
						                        async: false,
						                        dataType: "json",
						                        success: function cbf(data){
						                         var member = eval("(" + data + ")");
						                         var nologin = member.nologin;
								                  if(nologin){
								                  document.location.href =basePath+"page/ea/not_login.jsp";
								                  }
											      var wareHouseList = member.wareHouseList;
											        var se="";
											      if(wareHouseList != null&&wareHouseList.length>0){
												      for(var i=0;i<wareHouseList.length;i++){
												    		var op="<option value='"+wareHouseList[i].wareID+"'>"+wareHouseList[i].wareName+"</option>";
												      		se+=op;
												      }
												      var xe=parseInt(aryParam)+1;
												      $("select[title='"+ xe +"'] option").remove();
												      $("select[title='"+ xe +"']").append("<option value=''>无</option>");
											          $("select[title='"+ xe +"']").append(se);
											      }else{
											      	 var xe=parseInt(aryParam)+1;
											      	for(var i=xe;i<=4;i++){
											      	  $("select[title='"+ i +"'] option").remove();
												      $("select[title='"+ i +"']").append("<option value='' selected='selected'>无</option>");
											      	
											      	}
											      }
											       notoken=0;
						                        },
						                        error: function cbf(data){
						                           notoken=0;
						                           alert("数据获取失败！")
						                        }
						                    });
                			})
                }); 


</script>

	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							请选择
						</th>
						<th width="40" align="center">
							序号
						</th>
						<th width="100" align="center">
							公司名称
						</th>
						<th width="150" align="center">
							库名称
						</th>
						<th width="200" align="center">
							区名称
						</th>
						<th width="200" align="center">
							架名称
						</th>
						<th width="100" align="center">
							位名称
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list" var="tolist">
						<tr id="${tolist[0]}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${tolist[0]}" />
							</td>
							<td><span>
							<%=number%>
							</span>
							</td>
							<td>
								<span id="companyname">${tolist[1]}</span>
							</td>
							<td>
								<span id="warename1">${tolist[2]}</span>
							</td>
							<td>
								<span id="warename2">${tolist[3]}</span>
							</td>
                            <td>
								<span id="warename3" >${tolist[4]}</span>
							</td>
							<td>
							<span id="warename4" >${tolist[5]}</span>
							</td>
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/warehouse/ea_getListWareHouse.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
		<form name="cstaffForm" id="cstaffForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss3" style="top: 20%;width: 300px"
				id="jqModel">
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							仓库管理
							<div class="close"></div>
						</div>
					</div>
						<table width="300px"  border="0" id="stafftable2" align="center"
									cellpadding="0" cellspacing="0"
									style="margin-top: 5px; margin-bottom: 50px;">
									<tr>
										<td align="right">
											库名称：
										</td>
										<td>
											<select class="warehouse" id="pware" style="width: 100px" title="1">
												<option value="" >无</option>
												<c:forEach items="${warehouseList}" var="warehouse">
													<option value="${warehouse.wareID}" >${warehouse.wareName}</option>
												</c:forEach>
											</select>
											<a href="#"
												onclick="toCCode(this.id)" id="1">新添</a>
										</td>
									<tr>
									<tr>
										<td align="right">
											区名称：
										</td>
										<td>
											<select class="warehouse" id="parea" style="width: 100px" title="2">
												<option value="" >无</option>
											</select>
											<a href="#"
												onclick="toCCode(this.id)" id="2">新添</a>
										</td>
									<tr>
									<tr>
										<td align="right">
											架名称：
										</td>
										<td>
											<select class="warehouse" id="pframe" style="width: 100px" title="3">
												<option value="" >无</option>
											</select>
											<a href="#"
												onclick="toCCode(this.id)" id="3">新添</a>
										</td>
									<tr>
									<tr>
										<td align="right">
											位名称：
										</td>
										<td>
											<select class="warehouse" id="place" style="width: 100px" title="4">
												<option value="" >无</option>
											</select>
											<a href="#"
												onclick="toCCode(this.id)" id="4">新添</a>
										</td>
								</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		<!--搜索窗口 -->
        <div class="jqmWindow" style="width: 350px;right: 35%; top:20%" id="jqModelSearch">
            <form name="SearchForm" id="SearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable" style="margin-left: 75px">
                    <tr>
                        <td align="right">
                          库：
                        </td>
                        <td>         
                         <input  name="pware"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                           区：
                        </td>
                        <td>         
                           <input name="parea" />
                        </td>
                    </tr>
                      <tr>
                        <td align="right">
                           架：
                        </td>
                        <td>         
                           <input name="pframe" />
                        </td>
                    </tr>
                      <tr>
                        <td align="right">
                           位：
                        </td>
                        <td>         
                           <input name="place" />
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value="查询" /><input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
        
        <div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
			id="newccode">
			<div class="drag">
				添加
			</div>
			<table >
				<tr align="center">
					<td>
						<span id="kuName">库 区 架 位名称：</span>
					</td>
					<td>
						<input id="wareName" />
						<input id="pware" type="hidden" />
						<input id="parea" type="hidden" />
						<input id="pframe" type="hidden" />
						<input id="wareType" type="hidden" />
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" onclick="saveCCode()"
					value="确定" />
				<input type="button" class="input-button JQueryreturn1" value="取消" />
			</div>
		</div>
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>