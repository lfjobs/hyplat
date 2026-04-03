<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
       <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %><html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>奖罚单审核</title>
        
        <style type="text/css"> 
		.beizhutable {
			border-collapse:collapse;
			border-left:1px solid #a8c7ce;
			border-right:1px solid #a8c7ce;
			font-size:12px;
			table-layout:fixed
		}
		</style>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
          <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
               <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script type="text/javascript">
            var ppageNumber = ${pageNumber};
            var basePath = '<%=basePath %>';
            var acceName = '${listGamJeom[0][18]}';  //附件查看赋值
            var search ='${search}';
            var labelTag='${labelTag}';
            var token=0;
            var notoken=0;
            var prID = '${listGamJeom[0][0]}';
            var backType = '${listGamJeom[0][6]}';  //撤销单据类型
            var pnums = '${pageForm.pageNumber}';
            
            $(document).ready(function(){
            
			     if(acceName != ''){
			    	var onload =acceName.substring(acceName.lastIndexOf("."),acceName.length);
			    	if(onload.toLowerCase()!=".jpg" && onload.toLowerCase()!=".gif" && onload.toLowerCase()!=".png"){
			    		$("#look").hide();
			 			$("#load").find("a").attr("href",basePath+"ea/publicreceipts/ea_downFile.jspa?downLoadPath="+acceName);
			   			$("#load").show();
			   		}
			    }
            	if(labelTag=='00'){
            	$("input.JQuerySubmit").attr("value","主管审核通过")
            	$("input.JQueryNoSubmit").attr("value","主管驳回作废")
            	}else{
            	$("input.JQuerySubmit").attr("value","人事审核通过")
            	$("input.JQueryNoSubmit").attr("value","人事驳回作废")
            	
            	if($.trim($("span#receiptsStatus").text()) == '人力资源部审核通过'){  //撤销显示
            		$("input.JQuerySubmit").hide();
            		$("input.JQueryNoSubmit").hide();
            		$("input.JQueryBackSubmit").show();
            	}
            	}
            	$("input.JQueryBackSubmit").click(function(){   //撤销
            		if(confirm("确定撤销？")){
		                document.location.href=basePath+"ea/publicreceipts/ea_toBackBills.jspa?prID="+prID+"&publicreceipts.type="+backType+"&labelTag=01";
	                }           	
            	})
            	$("input.JQueryreturn").click(function(){  //返回
            		document.location.href=basePath+"ea/publicreceipts/ea_getListPublicreceipts.jspa?pageNumber="+ppageNumber+"&pageForm.pageNumber="+pnums+"&search="+search+"&labelTag="+labelTag;           	
            	})
            	$("input.JQuerySubmitPrint").click(function(){  //打印预览
            		window.open(basePath+"ea/publicreceipts/ea_printReward.jspa?prID="+prID);           	
            	})
            })
            
              function AjaxFunc(types){
              		if(notoken){
              		alert("正在加载数据")
              		return;
              		}
              		notoken=1;
                	 var receiptsStatus=$.trim($("span#receiptsStatus").text()); 
                	 var prID=$.trim($("input#prID").attr("value"));
                          if(receiptsStatus==(labelTag=='00'?'待审':'部门主管审核通过')){
                          	 var url = basePath+"ea/publicreceipts/sajax_ea_AjaxPublicreceiptsAudit.jspa?prID="+prID+"&date1="+new Date()+"&types="+types;
          						   $.ajax({
						                        url: encodeURI(url),
						                        type: "get",
						                        async: false,
						                        dataType: "json",
						                        success: function cbf(data){
						                        var member = eval("(" + data + ")");
									            var publicrece = member.publicrece;
									            var typet;
									            if(types=='主管审核通过'){
									           		 typet="部门主管审核通过";
									           		 $("span#firstAuditor").text(publicrece)
									            }else if(types=='主管驳回作废'){
									            	 typet="驳回作废";
									            	 $("span#firstAuditor").text(publicrece)
									            }else if(types=='人事审核通过'){
									            	 typet="人力资源部审核通过";
									            	 $("span#secondAuditor").text(publicrece)
									            }else if(types=='人事驳回作废'){
									            	 typet="驳回作废";
									            	 $("span#secondAuditor").text(publicrece)
									            }
									            $("span#receiptsStatus").text(typet);
									               alert("操作成功!");
									               document.location.href=basePath+"ea/publicreceipts/ea_getListPublicreceipts.jspa?pageNumber="+ppageNumber+"&search="+search+"&labelTag="+labelTag;
						                        },
						                        error: function cbf(data){
						                           alert("数据获取失败！")
						                           notoken=0;
						                        }
						                    }); 
                          }else{
                          	alert("数据已归档");
                          	notoken=0;
                          	return;
                          }
                
                }	
		</script>
	</head>
	<body >
 <div class="contentbannb"  style="position: absolute;margin-left: 15%;">
  <div class="content">
  <table width="850 " height="46" border="0" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">
  	<tr ><td align="center" height="30" colspan="6" style="font-weight:bold;font-size:15px"">${listGamJeom[0][6]}</td></tr>
    <tr>
      <td width="60" align="right">公司：</td>
      <td width="220">
      <span id="companyName">${listGamJeom[0][1]}</span>
      </td>
      <td width="160" align="right">部门：</td>
      <td width="160"><span id="principalOrganizationID">${listGamJeom[0][2]}</span></td>
      <td width="110" align="right">责任人：</td>
      <td width="180"><span>${listGamJeom[0][4]}</span>
    </tr>
    <tr>
      <td align="right">凭证号：</td>
      <td><span>${listGamJeom[0][3]}</span></td>
      <td align="right">申请日期：</td>
      <td><span>${fn:substring(listGamJeom[0][7], 0, 10)}</span></td>
      <td align="right">附件：</td>
      <td><s:if test="listGamJeom[0][17] == null"><span id="isNull">无</span></s:if>
	  	  <s:else><span id="look" onclick="lookImage(acceName);"><a id="isLook" href="#">查看</a></span>
	  		  	  <span id="load" style="display:none"><a href="#">下载</a></span>
	      </s:else>
      </td>
    </tr>
  </table>
  <table width="890" height="98"  border="0" align="center" cellpadding="0" cellspacing="0" class="table" style="background:#FFFFFF;">
    <tr>
      <td width="40" align="right" height="30">奖罚分：</td>
      <td width="60"><span>${listGamJeom[0][9]}</span></td>
       <td align="right" width="60">制单人：</td>
      <td width="60"><span>${listGamJeom[0][8]}</span></td>
      <td  align="right" width="60">奖罚日期：</td>
      <td width="60"><span>${listGamJeom[0][10]}</span></td>
    </tr>
    <tr>
      <td height="40" align="right" width="40">奖罚金额(大写)</td>
       <td colspan="5" align="left" style="padding-left:5px;">${listGamJeom[0][11]}</td>
    </tr>
    </table>
    <table width="890" height="60" border="0" align="center"
			cellpadding="0" cellspacing="0" class="beizhutable"
			style="background: #FFFFFF;">
		 <tr>
	      <td height="76" align="right" width="104" style="border-right:1px solid #a8c7ce;">奖罚原因：</td>
	      <td>${listGamJeom[0][12]}</td>   
	          <input id="prID" type="hidden" value="${listGamJeom[0][0]}"/>
	     </tr>
   </table>
   <table width="882" height="40"  border="0" align="center" cellpadding="0" cellspacing="0" class="table" style="background:#FFFFFF;">
   <tr>
      <td width="104" align="right">部门主管审核：</td>
      <td width="100" align="center"><span id="firstAuditor">${listGamJeom[0][13]}</span></td>
      <td width="100" align="right">人力资源部审核：</td>
      <td width="100" align="center"><span id="secondAuditor">${listGamJeom[0][14]}</span></td>
      <td width="100" align="right" width="90">总经理审核：</td>
      <td width="100" align="center"><span id="finalAuditor">${listGamJeom[0][15]}</span></td>
      <td width="100" align="right">审核状态：</td>
      <td width="176" align="center"><span id="receiptsStatus">${listGamJeom[0][16]}</span></td>
    </tr>
  </table>
  <table width="890" height="40" border="0" align="center" >
  	<tr>
      <td align="center">
      <input type="button" class="input-button JQuerySubmitPrint" style="cursor:pointer;width:80px;" value="打印预览" />
      <input type="button" class="input-button JQuerySubmit" style="cursor:pointer;width:80px;" onclick="AjaxFunc(this.value);" value="审核通过" />
      <input type="button" class="input-button JQueryNoSubmit" style="cursor:pointer;width:80px;" onclick="AjaxFunc(this.value)" value="驳回作废" />
      <input type="button" class="input-button JQueryBackSubmit" style="cursor:pointer;width:80px;display:none;" value="撤销" />
      <input type="button" class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="返回" /></td>
    </tr>
  </table>
</div>
        </div>
    </body>
</html>