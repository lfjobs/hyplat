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
        <title>员工级别变更单</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
         
        <style type="text/css"> 
        .windowJqm{
		    left:55%;
		    width:840px;
		    margin-left:-450px;;	
		}
		.reason {
			padding-right: 60px;
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
	        var prID = "";
	        var b=true;
	        var basePath='<%=basePath%>';
	        var ppageNumber=${pageNumber};
	        var psearch='${search}';
	        var token = 0;
	        var acceName = '';  //附件查看赋值
	        var parm = '';
	        var vouch = '';  //凭证号传值
	        
	    $(document).ready(function() {    //获取凭证号
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
		    },
	              error: function cbf(data){
					         alert("数据获取失败！")
					 }
			});
	    });
        $(document).ready(function() {  //级别下拉框显示
			var url = basePath + "ea/publicreceipts/sajax_ea_getScaleList.jspa";
			$.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if(nologin){
						document.location.href ="<%=basePath%>page/ea/not_login.jsp";
					}
					var persons = member.payScaleList;
					var str = "<option value=''>请选择级别</option>";
					for(var i=0;i<persons.length;i++){ 
						var obj =persons[i];
						str += "<option value='"+obj[2]+"@"+obj[0]+"'>"+obj[0]+" / "+obj[1]+"</option>";
					}
					$("#rankShenzhigrade",$("#cstaffForm")).html(str);
				}
			});
        });
           
        //选择责任人       
		function searchCoach(){
			 if(parm == ''){
			 	parm = treeID;
			 }
			 var url = "ea/cosincumbent/ea_getStaffForCashier.jspa?checkOrgID="+ parm;
			 getValueForParm('cstaffForm','partnerName','childPartnerName',url);
		}
		
		function getValueForParm(attachTable,parm,parmNum,url){ //打开页面
			 $("#myform",$("#jqmWindow2")).attr("value",attachTable);
			 $("#parm",$("#jqmWindow2")).attr("value",parm);
			 $("#parmNum",$("#jqmWindow2")).attr("value",parmNum);
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
				var parmNum = $("#parmNum",$("#jqmWindow2")).attr("value");
			 	
				var value1 = window.frames["ifr"].opertionID;//弹出框的页面必须声明opertionID这个参数接收id
				if(value1 == ""){
					alert("请选择")
					return;
				}
				var value2 = window.frames["ifr"].$('tr#'+value1).find("span#"+parm).text();//弹出框的页面存在于span中才取得到
				var value3 = window.frames["ifr"].$('tr#'+value1).find("span#"+parmNum).text();
				if(parm != "")
				$("#"+parm,$("#"+myfrom)).attr("value",value2).trigger("blur");
				if(parmNum != "")
				$("#"+parmNum,$("#"+myfrom)).attr("value",value3).trigger("blur").css("color","black");
				$("#principalID",$("#"+myfrom)).attr("value",value1).trigger("blur");
				$("#ifr").attr("src","");
		        $("#jqmWindow2").jqmHide();
		        
		        $("#rankOldleve").css("color","black");
		        $("#rankStartdate").css("color","black");
		        
		        /*********************自动获取原级别明细*********************/
		        var url = basePath+"ea/publicreceipts/sajax_ea_getLevel.jspa?staffIDvalue="+value1;
                $.ajax({
					url : encodeURI(url),
					type : "get",
					async : false,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var nologin = member.nologin;
						if(nologin){
							document.location.href ="<%=basePath%>page/ea/not_login.jsp";
						}
						var scale = member.scale;
						var entry_date = member.entry_date;
						$("input#rankStartdate").attr("value",entry_date);
						if(scale != null&&scale.payScaleID){
							$("#rankOldleve").val(scale.scale);
							$("#oldpayscaleID").val(scale.payScaleID);
						}else{
							$("#partnerName").attr("value","");
							alert("该责任人没有原级别!");
						}
					},
					error : function cbf(data) {
						alert("获取数据失败!");
					}
				});
		    });
		});
		
        </script>
        <script type="text/javascript" src="<%=basePath%>js/ea/office_ea/StaffRank.js"></script>

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
                             人员编号
                        </th>
                        <th width="110" align="center">
                             升降级别
                        </th>
                        <th width="110" align="center">
                             生效日期
                        </th>
                        <th width="110" align="center">
                             原级别明细
                        </th>
                        <th width="110" align="center">
                             起时间
                        </th>
                        <th width="110" align="center">
                             止时间
                        </th>
                        <th width="110" align="center">
                             变动理由
                        </th>
                        <th width="110" align="center">
                             工作内容
                        </th>
                        <th width="110" align="center">
                             自我评定
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
                                <span id="applyDate" class="datas">${fn:substring(arr[6], 0, 10)}</span>
                            </td>
                            <td>
                                <span id="operator">${arr[7]}</span>
                            </td>
                            <td>
                                <span id="childPartnerName1">${arr[8]}</span>
                            </td>
                            <td>
                                <span id="rankShenzhigrade1">${arr[9]}</span>
                            </td>
                            <td>
                                <span id="rankEffectivedate1">${arr[10]}</span>
                            </td>
                            <td>
                                <span id="rankOldlevel">${arr[11]}</span>
                            </td>
                            <td>
                                <span id="rankStartdate1">${arr[12]}</span>
                            </td>
                            <td>
                                <span id="rankEnddate1">${arr[13]}</span>
                            </td>
                            <td>
                                <span id="rankChangeReason1">${arr[14]}</span>
                            </td>
                            <td>
                                <span id="rankContent1">${arr[15]}</span>
                            </td>
                            <td>
                                <span id="rankExamine1">${arr[16]}</span>
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
                <c:param name="actionPath" value="ea/publicreceipts/ea_getRankPublicreceipt.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
        </div>
 <div class="contentbannb jqmWindow windowJqm" style="top: 5%" id="jqModel">
 <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
     <input type="submit" name="submit" style="display:none"/>
     <div class="content">
  <div class="contentbannb">
  	<div class="drag">员工级别变更单
    <div class="close"></div>
    </div>
  </div>
  <table width="840 " height="50" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;margin-bottom:5px;">
    <tr>
      <td width="60" align="right">公司：</td>
      <td width="200"><input name="company.companyName" value="${company.companyName}" readonly="readonly" class="input yincang" id="companyName" size="30"/>
      				  <span class="xianshi" id="companyName1"></span>
      </td>
      <td width="120" align="right">部门：</td>
      <td width="160">
      <select id="principalOrganizationID" class="input yincang" name="publicreceipts.principalOrganizationID" style="margin-left:2px;"></select>
      <span class="xianshi" id="organizationName"></span>
      </td>
      <td align="right" width="80" ><span class="xx">*</span>责任人：</td>
      <td class="td_bg disName" width="220">
      <input name="publicreceipts.principalID"  id="principalID" type="hidden" class="input"/>
	  <input type="text" id="partnerName" class="put3 input yincang" name="publicreceipts.principal"
			 readonly="readonly" value="${publicreceipts.principal}" size="10" style="margin-left:2px;"/>
	  <a href="#" class="yincang" id="nameChoose" onclick="searchCoach();">选择</a>
	  <span class="xianshi" id="principal"></span>
      </td>
    </tr>
    <tr>
      <td align="right">凭证号：</td>
      <td><input readonly="readonly" name="publicreceipts.voucherNum" id="voucherNum1" class="input yincang" size="25" style="margin-left:2px;"/>
	  	  <span id="voucherNum" class="xianshi"></span>
	  </td>
      <td align="right">申请日期：</td>
      <td ><input name="publicreceipts.applyDate" readonly="readonly" id="applyDate1" class="input yincang" size="15" style="margin-left:2px;"/>
           <span id="applyDate" class="xianshi"></span>
      </td>
      <td align="right">附件：</td>
      <td><input name="photoFileName"  class="fileNum input"  type="hidden" id="accessory" size="15" style="margin-left:2px;"/>
      				  <input name="photo" type="file" id="accessoryName" class="input yincang" size="15" contentEditable="false" style="margin-left:2px;"/>
      				  <span id="isNull" style="display:none;margin-left:2px;" class="hideAll">无</span>
                      <span id="isLook" style="display:none;margin-left:2px;" class="hideAll" onclick="lookImage(acceName);"><a href="#">查看</a></span>
                      <span id="isLoad" style="display:none;margin-left:2px;" class="hideAll"><a href='#'>下载</a></span>
      </td>
      
    </tr>
  </table>
  <table width="840" height="240"  border="0" align="center" cellpadding="0" cellspacing="0" class="table" style="background:#FFFFFF;">
    <tr>
      <td width="100" height="30" align="right">人员编号：</td>
      <td width="160"><input text="hidden" name="publicreceiptsChild.rankHumanID" class="input yincang" readonly="readonly" id="childPartnerName" size="20" style="margin-left:2px;"/>
      				  <span id="childPartnerName1" class="input xianshi sty"></span>
      </td>
      <td width="100" align="right">原级别明细：</td>
      <td width="170"><input name="publicreceiptsChild.rankOldScale" class="input yincang" readonly="readonly" id="rankOldleve" size="20" style="margin-left:2px;"/>
      				  <input type="hidden" name="publicreceiptsChild.rankOldlevel" id="oldpayscaleID"/>
      				  <span id="rankOldlevel" class="input xianshi sty"></span>
      				  </td>
      <td width="80" align="right"><span class="xx">*</span>升降级别：</td>
      <td width="223" class="disName">
      <select name="publicreceiptsChild.rankNewScale" id='rankShenzhigrade' class="put3 yincang">
	  </select>
	  <span id="rankShenzhigrade1"  class="input xianshi sty"></span>
     </td>
    </tr>
    <tr>
      <td height="30"  align="right">原级别起日期：</td>
      <td><input name="publicreceiptsChild.rankStartdate" class="input yincang" readonly="readonly" id="rankStartdate" size="20" style="margin-left:2px;"/>
      	  <span id="rankStartdate1" class="input xianshi sty"></span>
      </td>
      <td align="right">原级别止日期：</td>
      <td><input class="input yincang" id="rankEnddate" size="20" style="margin-left:2px;"/>
      	  <span id="rankEnddate1" class="input xianshi sty"></span>
      </td>
      <td align="right"><span class="xx">*</span>生效日期：</td>
      <td class="disName"><input name="publicreceiptsChild.rankEffectivedate" class="input put3 yincang" onfocus="date(this);"  id="rankEffectivedate" size="20" style="margin-left:2px;"/>
      					  <span id="rankEffectivedate1" class="input xianshi sty"></span>
      </td>
    </tr>
    <tr>
      <td height="30" align="right"><span class="xx">*</span>变动理由：</td>
      <td colspan="6" id="rankChangeReason">
      		<input type="radio" name="publicreceiptsChild.rankChangeReason" class="yincang" number="1" style="margin-left:5px;" value="晋升"/>
      		<font class="reason yincang">晋升</font>
      		<input type="radio" name="publicreceiptsChild.rankChangeReason" class="yincang" number="2" value="岗位变动"/>
      		<font class="reason yincang">岗位变动</font>
      		<input type="radio" name="publicreceiptsChild.rankChangeReason" class="yincang" number="3" value="考核结果"/>
      		<font class="reason yincang">考核结果</font>
      		<input type="radio" name="publicreceiptsChild.rankChangeReason" class="yincang" number="4" value="年资增加"/>
      		<font class="reason yincang">年资增加</font>
      		<input type="radio" name="publicreceiptsChild.rankChangeReason" class="yincang" number="5" value="其他"/>
      		<font class="yincang">其他</font><font class="others" style="display:none;">：</font><input id="other" class="others" style="display:none;"/>
          <span id="rankChangeReason1" class="input xianshi sty"></span>
      </td>
    </tr>
    <tr>
      <td height="60" align="right"><span class="xx">*</span>工作内容：</td>
      <td colspan="6"><textarea name="publicreceiptsChild.rankContent" cols="90" rows="4" class="input yincang ckTextLength" maxlength="250" id="rankContent" style="margin-left:2px;"></textarea>
      				<textarea id="rankContent1" class="input xianshi sty" readonly="readonly" cols="101" rows="4" style="margin-left:2px;"></textarea>
      </td>
    </tr>
    <tr>
      <td height="60" align="right"><span class="xx">*</span>自我评定：<br />(业绩、优缺点)</td>
      <td colspan="6"><textarea name="publicreceiptsChild.rankExamine" cols="90" rows="4" class="input yincang ckTextLength" maxlength="250" id="rankExamine" style="margin-left:2px;"></textarea>
      			<textarea id="rankExamine1" class="input xianshi sty" readonly="readonly" cols="101" rows="4" style="margin-left:2px;"></textarea>
      </td>
    </tr>
  </table>
 
  <table width="840" height="40" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px; margin-bottom:5px;">
    <tr>
      <td colspan="10" align="center">
                   <input type="hidden" name="publicreceiptsChild.prID" id="prID" />
                   <input type="hidden" name="publicreceiptsChild.orKey" id="orKey" />
      <input type="button"  class="input-button JQuerySubmitPrint xianshi" style="cursor:pointer;width:80px; display:none;" value="打印预览" />
      <input type="button"  class="input-button JQuerySubmit yincang" style="cursor:pointer;width:80px;" value="提交审核" />
      <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="返回" /></td>
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
                         责任人：                      
                   </td>
                   <td>
                       <input name="publicreceipts.principal" />
                    </td>
                    </tr>
                    <tr>
                        <td align="right">
                            部门名称：                        
                        </td>
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
        
          <div class="jqmWindow" style="width: 400px;right: 25%;" id="newamount">
                                            <div class="drag">
                                                文件：
                                            </div>
                                            
                                           <img id="wenjian" width="350" height="400"/>
                                            
         </div>
         <!-- 从当前部门的员工中选择责任人 -->
         <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
		 <div id="jqmWindow2" class="jqmWindow"
			style="width: 95%; height: 420px; absolute; display: none; left: 1%; top: 1%; background: #eff">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="myform" />
				<input style="display: none;" id="parm" />
				<input style="display: none;" id="parmNum"/>
			</div>
			<iframe name="ifr" id="ifr" width="100%" height="380px"
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