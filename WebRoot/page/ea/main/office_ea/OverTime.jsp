<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ page import="hy.ea.bo.Company"%>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
       <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; 
        Company c = (Company)session.getAttribute("currentcompany");
        %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>员工加班申请单</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <style type="text/css"> 
        .windowJqm{
		    left:55%;
		    width:870px;
		    margin-left:-450px;;	
		}
		.sort {
			padding-right: 80px;
		}
		.underline {
			text-decoration: underline;
		}
		.sty{
			padding-left:5px;
		}
		</style>
        
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
          <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
          <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
         <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
          <link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
        <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
        <script type="text/javascript">
        	var treeID = "<%=session.getAttribute("organizationID")%>";
	        var treeName = "<%=session.getAttribute("organizationName")%>";
	        var treePID = '<%=c.getCompanyID()%>';
	        var treePName = '<%=c.getCompanyName()%>';
 			var token = 0;
       		var prID = "";
        	var b=true;
        	var basePath='<%=basePath%>';
       		var ppageNumber=${pageNumber};
        	var psearch='${search}';
        	var acceName = '';  //附件查看赋值
        	var parm = '';
        	var times = '0';
        	var vouch = '';  //凭证号传值
        	
        	
		    function getID(){
		    	var url="<%=basePath%>ea/cashierbills/sajax_ea_getBillID.jspa?date="+new Date().toLocaleString();
				$.ajax({
		                url: url,
		                type: "get",
		                async: true,
		                dataType: "json",
		                success: function cbf(data){
					    var member = eval("(" + data + ")");
					    var nologin = member.nologin;
						if(nologin){
							document.location.href ="<%=basePath%>page/ea/not_login.jsp";
						}
				        vouch = member.BillID;
				        $("#voucherNum1").val(vouch);
			    },
		              error: function cbf(data){
						         alert("数据获取失败!")
						 }
				});
			}
	        //选择责任人       
			function searchCoach(){
				 if(parm == ''){
				 	parm = treeID;
				 }
				 var url = "ea/cosincumbent/ea_getStaffForCashier.jspa?checkOrgID="+ parm;
				 getValueForParm('cstaffForm','partnerName',url);
			}
			
			function getValueForParm(attachTable,parm,url){ //打开页面
				 $("#myform",$("#jqmWindow2")).attr("value",attachTable);
				 $("#parm",$("#jqmWindow2")).attr("value",parm);
			  	 $("#ifr").attr("src",basePath+url);
			  	 $("#jqmWindow2").jqmShow();
			}
			
			$(document).ready(function() {
				$("#isBack").click(function(){// 返回
			       $("#jqmWindow2").jqmHide();
			    }); 
			   
				$("#isSubmit").click(function(){// 选择确定
					var myfrom =$("#myform",$("#jqmWindow2")).attr("value");
					var parm = $("#parm",$("#jqmWindow2")).attr("value");
				 	
					var value1 = window.frames["ifr"].opertionID;//弹出框的页面必须声明opertionID这个参数接收id
					if(value1 == ""){
						alert("请选择")
						return;
					}
					
					var value2 = window.frames["ifr"].$('tr#'+value1).find("span#"+parm).text();//弹出框的页面存在于span中才取得到
					if(parm != ""){
						$("#"+parm,$("#"+myfrom)).attr("value",value2).trigger("blur");
						$("#principalID",$("#"+myfrom)).attr("value",value1).trigger("blur");
						$.ajax({
							url : encodeURI(basePath + "ea/departmentpost/sajax_ea_getStaffPost.jspa?staffid=" + value1),
							type : "get",
							async : true,
							dataType : "json",
							success : function cbf(data) {
								var member = eval("(" + data + ")");
								var postname = member.postname;
								if(postname == ''){
									$("#overTimePostName").val("");
								}else{
									$("#overTimePostName").val(postname);
								}
							}
						});
					}
					$("#ifr").attr("src","");
			        $("#jqmWindow2").jqmHide();
			    });
			});   
		</script>
		<script type="text/javascript" src="<%=basePath%>js/ea/office_ea/OverTime.js"></script>
		

	</head>
	<body >
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center">
                            请选择
                        </th>
                        <th width="170" align="center">
                            公司
                        </th>
                        <th width="80" align="center">
                            部门
                        </th>
                        <th width="150" align="center">
                            凭证号
                        </th>
                        <th width="110" align="center">
                             责任人
                        </th>
                        <th width="110" align="center">
                             单据类型
                        </th>
                        <th width="110" align="center">
                             申请日期   
                        </th>
                        <th width="110" align="center">
                             制单人
                        </th>
                        <th width="110" align="center">
                             岗位
                        </th>
                        <th width="110" align="center">
                             加班类别
                        </th>
                        <th width="110" align="center">
                             起时间
                        </th>
                        <th width="110" align="center">
                             止时间
                        </th>
                        <th width="110" align="center">
                             加班天数
                        </th>
                        <th width="110" align="center">
                             加班小时
                        </th>
                        <th width="110" align="center">
                             加班工资评分
                        </th>
                        <th width="110" align="center">
                             加班事由
                        </th>
                        <th width="110" align="center">
                             加班内容
                        </th>
                        <th width="110" align="center">
                             部门主管审核人
                        </th>
                        <th width="110" align="center">
                             人力资源部审核人
                        </th>
                        <th width="110" align="center">
                             单据状态
                        </th>
                        <th width="110" align="center">
                             附件
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <c:forEach var='arr' items="${pageForm.list}">
                        <tr id="${arr[0]}"  >
                            <td>
                                <input type="radio" name="a" class="JQuerypersonvalue"
								value="${arr[0]}" />
                            </td>
                            <td>
                                <span id="companyName1">${arr[1]}</span>
                            </td>
                            <td>
                            	<span id="organizationName">${arr[2]}</span>
                            </td>
                            <td>
                                <span id="voucherNum">${arr[3]}</span>
                            </td>
                            <td>
                                <span id="principal">${arr[4]}</span>
                            </td>
                            <td>
                                <span id="type">${arr[5]}</span>
                            </td>
                            <td>
                                <span id="applyDate" class="datas">${fn:substring(arr[6],0, 10)}</span>
                            </td>
                            <td>
                                <span id="operator">${arr[7]}</span>
                            </td>
                            <td>
                                <span id="overTimePostName1">${arr[8]}</span>
                            </td>
                            <td>
                                <span id="overTimeSort1">${arr[9]}</span>
                            </td>
                            <td>
                                <span id="overTimeStartDate1">${arr[10]}</span>
                            </td>
                            <td>
                                <span id="overTimeEndDate1">${arr[11]}</span>
                            </td>
                            <td>
                                <span id="overTimeDays1">${arr[12]}</span>
                            </td>
                            <td>
                                <span id="overTimeHour1">${arr[13]}</span>
                            </td>
                            <td>
                                <span id="overtimeWages1">${arr[14]}</span>
                            </td>
                            <td>
                                <span id="overTimeReason1">${arr[15]}</span>
                            </td>
                            <td>
                                <span id="overTimeContent1">${arr[16]}</span>
                            </td>
                            <td>
                                <span id="firstAuditor">${arr[17]}</span>
                            </td>
                            <td>
                                <span id="secondAuditor">${arr[18]}</span>
                            </td>
                            <td>
                                <span id="receiptsStatus">${arr[19]}</span>
                            </td>
                            <td >
                             	<span id="look1" style="display:none">${arr[20]}</span>
                            	<span id="wu" style="display:none">无</span>
                            	<span id="look" style="display:none" onclick="lookImage('${arr[20]}');"><a href="#">查看</a></span>
                             	<span id="load" style="display:none"><a href='<%=basePath%>ea/publicreceipts/ea_downFile.jspa?downLoadPath=${arr[20]}'>下载</a></span>
                            </td>
                        </tr>
                        <%
                        number++; %>
                    </c:forEach>
                </tbody>
            </table>
            <c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/publicreceipts/ea_getOverPublicreceipts.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
        </div>
        <div class="contentbannb jqmWindow windowJqm" style="top: 5%" id="jqModel">
            <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                <div class="content">
<div class="contentbannb">
  <div class="drag">员工加班申请单
    <div class="close"></div>
  </div>
  </div>
  <table width="870 " height="46" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px; margin-bottom:5px;">
    <tr>
      <td width="80" align="right">公司：</td>
      <td width="220"><input name="company.companyName" class="input yincang" id="companyName" readonly="readonly" value="${company.companyName}" size="30"/>
      				  <span class="xianshi" id="companyName1"></span>
      </td>
      <td width="120" align="right">部门：</td>
      <td width="200"><select id="principalOrganizationID" class="yincang" name="publicreceipts.principalOrganizationID"></select>
      <span id="organizationName" class="xianshi"></span>
      </td>
      <td align="left">附件：<input name="publicreceipts.accessory"  class="fileNum"  type="hidden" id="accessory" size="15"/>
      				  <input name="photo" type="file" id="accessoryName" class="input yincang" size="20" contentEditable="false"/>
      				  <span id="isNull" style="display:none" class="hideAll">无</span>
                      <span id="isLook" style="display:none" class="hideAll" onclick="lookImage(acceName);"><a href="#">查看</a></span>
                      <span id="isLoad" style="display:none" class="hideAll"><a href='#'>下载</a></span>
    </tr>
    <tr>
      <td align="right">凭证号：</td>
      <td>
      <input readonly="readonly" name="publicreceipts.voucherNum" id="voucherNum1" class="yincang" size="25"/>
      <span id="voucherNum" class="xianshi"></span>
      </td>
      <td align="right"><span class="xx">*</span>责任人：</td>
      	<td class="disName"><input name="publicreceipts.principalID"  id="principalID" type="hidden"/>
		<input type="text" id="partnerName" class="put3 yincang" name="publicreceipts.principal"
			 readonly="readonly" value="${publicreceipts.principal}" size="6"/>
		<a href="#" class="yincang" onclick="searchCoach();">选择</a>
		<span class="xianshi" id="principal"></span>
	  </td>
	  <td align="left">岗位：
	  <input name="publicreceiptsChild.overTimePostName" class="yincang" id="overTimePostName" readonly="readonly" size="12" />
	  <span class="xianshi" id="overTimePostName1"></span>
	  </td>
      
    </tr>
  </table>
  <table width="870" height="244"  border="0" align="center" cellpadding="0" cellspacing="0" class="table" style="background:#FFFFFF;">
    <tr>
      <td height="30"  width="106" align="right"><span class="xx">*</span>加班类别：</td>
      <td width="760" id="overTimeSorts">
		  <input type="radio" name="publicreceiptsChild.overTimeSort" class="yincang" value="工作日加班" />
		  <font class="sort yincang">工作日加班</font>
		  <input type="radio" name="publicreceiptsChild.overTimeSort" class="yincang" value="休息日加班" />
		  <font class="sort yincang">休息日加班</font>
		  <input type="radio" name="publicreceiptsChild.overTimeSort" class="yincang" value="法定假日加班"/>
		  <font class="sort yincang">法定假日加班</font>
	   	  <span id="overTimeSort1" class="xianshi sty"></span>
      </td>
    </tr>
    <tr>
      <td height="30" align="right"><span class="xx">*</span>加班时间及工资：</td>
      <td class="errortime">
          <input name="publicreceiptsChild.overTimeStartDate"  onfocus="daytime(this);" id="overTimeStartDate" class="input yincang" size="18" style="margin-left:2px;"/>
      	  <span class="underline xianshi sty">&nbsp;&nbsp;&nbsp;</span><span id="overTimeStartDate1" class="underline xianshi"></span><span class="underline xianshi">&nbsp;&nbsp;&nbsp;</span>
      	  至
          <input name="publicreceiptsChild.overTimeEndDate"  onfocus="daytime(this);" id="overTimeEndDate" class="input yincang" size="18" style="margin-left:2px;"/>
          <span class="underline xianshi">&nbsp;&nbsp;&nbsp;</span><span id="overTimeEndDate1" class="underline xianshi"></span><span class="underline xianshi">&nbsp;&nbsp;&nbsp;</span>
           共：
      	  <input name="publicreceiptsChild.overTimeDays" class="input yincang" id="overTimeDays" disabled="disabled" size="3"/>
      	  <span class="underline xianshi">&nbsp;&nbsp;&nbsp;&nbsp;</span><span id="overTimeDays1" class="underline xianshi"></span><span class="underline xianshi">&nbsp;&nbsp;&nbsp;&nbsp;</span>
      	  天
          <input name="publicreceiptsChild.overTimeHour" class="input yincang" id="overTimeHour" disabled="disabled" size="3"/>
          <span class="underline xianshi">&nbsp;&nbsp;&nbsp;&nbsp;</span><span id="overTimeHour1" class="underline xianshi"></span><span class="underline xianshi">&nbsp;&nbsp;&nbsp;&nbsp;</span>
           小时
          <font class="dis" disabled="disabled">加班工资</font><input disabled="disabled" class="yincang" id="notShow" size="3"/>
          <span class="underline xianshi">&nbsp;&nbsp;&nbsp;&nbsp;</span><span id="overtimeWages1" class="underline xianshi"></span><span class="underline xianshi">&nbsp;&nbsp;&nbsp;&nbsp;</span>
          <font class="dis" disabled="disabled">分</font>
      </td>
    </tr>
    <tr>
      <td height="40" align="right"><span class="xx">*</span>加班事由：</td>
      <td><textarea name="publicreceiptsChild.overTimeReason" cols="93" rows="5" class="input yincang ckTextLength" maxlength="250" id="overTimeReason" style="margin-left:2px;"></textarea>
      	  <textarea id="overTimeReason1" class="xianshi sty" readonly="readonly" cols="104" rows="5" style="margin-left:2px;"></textarea>
      </td>
    </tr>
    <tr>
      <td height="40" align="right"><span class="xx">*</span>加班内容：</td>
      <td><textarea name="publicreceiptsChild.overTimeContent" cols="93" rows="5" class="input yincang ckTextLength" maxlength="250" id="overTimeContent" style="margin-left:2px;"></textarea>
      	  <textarea id="overTimeContent1" class="xianshi sty" readonly="readonly" cols="104" rows="5" style="margin-left:2px;"></textarea>
      </td>
    </tr>
  </table>
  <table width="870" height="40" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px; margin-bottom:5px;">
    <tr>
      <td colspan="10" align="center">
        <input type="hidden" name="overTime.overID" id="overID" />
        <input type="hidden" name="overTime.overKey" id="overKey" />
        <input type="button" class="input-button JQuerySubmitPrint xianshi" style="cursor:pointer;width:80px; display:none;" value="打印预览" />
      	<input type="button" class="input-button JQuerySubmit" style="cursor:pointer;width:80px;" value="提交审核" />
        <input type="button" class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="返回" /></td>
    </tr>
  </table>
</div>
    <s:token></s:token>
            </form>
        </div>
       <!--搜索窗口 -->
        <div class="jqmWindow" style="width: 400px;right: 25%;;top: 10%" id="jqModelSearch">
          <form name="postSearchForm" id="postSearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
            <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
            </div>
                <table width="396" id="cataffSearchTable">
                  <tr>
                  	<td align="right">
                         凭证号：                      
                  	</td>
                  	<td>
                  	<input name="publicreceipts.voucherNum" />
                  	</td>
                  </tr>
                  <tr>
                  <td align="right">
                     加班申请责任人：   
                  </td>
                  <td>
                       <input name="publicreceipts.principal" />
                   </td>
                    </tr>
                    <tr>
                        <td align="right">
                            部门名称：                        </td>
                        <td>
                        <select id="principalOrganizationID" name="publicreceipts.principalOrganizationID" >
                        <option value="">全部</option>
                        </select>
                        </td>
                    </tr>
                    <tr>
                    	<td align="right">
	                          单据状态：                      
	                    </td>
	                  	<td>
	                  	<select name="publicreceipts.receiptsStatus">
	                  		<option value="">全部</option>
	                  		<option value="P">待审</option>
	                  		<option value="F">部门主管审核通过</option>
	                  		<option value="S">人力资源部审核通过</option>
	                  		<option value="A">总经理审核通过</option>
	                  		<option value="R">驳回作废</option>
	                  		<option value="B">撤销</option>
	                  	</select>
	                  	</td>
                  </tr>
                </table>
            <div align="center">
              <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
            </div>
            </form>
        </div>
         <!-- 从当前部门的员工中选择责任人 -->
         <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
		 <div id="jqmWindow2" class="jqmWindow"
			style="width: 95%; height: 380px; absolute; display: none; left: 1%; top: 5%; background: #eff">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="myform" />
				<input style="display: none;" id="parm" />
			</div>
			<iframe name="ifr" id="ifr" width="100%" height="340px"
				frameborder="0"></iframe>
			<div align="center">
				<input type="button" class="input-button" id="isSubmit" value=" 确定 "
					style="cursor: hand" />
				<input type="button" class="input-button" id="isBack" value=" 关闭 "
					style="cursor: hand" />
			</div>
		 </div>
    </body>
</html>