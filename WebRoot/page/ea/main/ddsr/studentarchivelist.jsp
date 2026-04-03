<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>学员归档列表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">
   var token = 0;
	var notoken = 0;
	var studentarchiveID = '${stua.studentarchivesid}';
    var basePath = '<%=basePath%>';
</script>
<style>
@charset "utf-8";
/* CSS Document */
body{margin:0 auto;}
html,body,div,p,ul,li,dl,dt,dd,h1,h2,h3,h4,h5,h6,form,input,select,button,textarea,iframe,table,th,td{margin:0 0; padding:0px 0px; font-size:12px; color:#666; }
img{ cursor:pointer;}
a,a:link { color:#666; text-decoration: none; } 
a:visited {  }   
a:active,a:hover { text-decoration: none; }
a:focus{ outline: none; }
.more{ float: right; }
.more a{ font-weight: normal; font-size: 12px; }
.fl{ display: inline; float: left; } 
.fr{ display: inline;  float: right; }
.fixed:after { content: "."; display: block; clear: both; height: 0; visibility: hidden; }   
.fixed { display: block; min-height: 1%; }   
*html .fixed{ height: 1%; }    
.clear {width:100%; diplay: block!important; float: none!important; clear: both; overflow: hidden; width: auto!important; height: 0!important; margin: 0 auto!important; padding: 0!important; font-size: 0; line-height: 0; }
img{border:none;}
.pos{ position:relative;}
body{ background-color:#FFF; width:100%;}
.head_title{ width:100%; height:40px; line-height:40px; font-size:20px; text-align:center; color:#000;}
.archives{ width:850px; margin:auto; border:1px solid #99bbe8;}
.jbxx{ width:840px; height:222px; margin-left:5px; margin-top:5px;}
.jbxx .jbxx_l { width:680px; height:222px;}
.jbxx .jbxx_l .jb_tit{ width:100%; height:40px; line-height:40px; text-align:center; font-size:16px; color:#169bd6; font-weight:bold; background:url(<%=basePath%>/images/title_bg1.png) no-repeat;}
.jbxx .jbxx_l h6{ width:580px; height:24px; line-height:24px; text-align:right; margin-right:75px; font-weight:normal;}
.jbxx .jbxx_r{ width:156px; height:220px; background-color:#bad4f7;}
.jbxx .jbxx_r a{ width:108px; height:152px; overflow:hidden; float:left; margin-left:26px; margin-top:26px;}
.bmqk{width:840px; margin-left:5px; margin-top:30px; margin-bottom:5px;}
.gd{width:100%; height:75px;}
.gd .p_t{ width:74px; margin-top:10px; }
.gd .p_t label{ width:64px; height:30px; line-height:30px; display:block; margin-left:10px;}
.gd .yj{ width:595px;}
.tr_title{ width:100%; height:40px; line-height:40px;text-align:center; font-size:16px; color:#169bd6; font-weight:bold; background:url(<%=basePath%>/images/title_bg2.png) no-repeat;}
.gd .pdf{width:158px; margin-top:10px;}
.gd .pdf label{ width:79px; height:30px; line-height:30px; display:block; float:left;}
.flo_bm{ width:100%; height:60px; text-align:center;}
.flo_bm a{ width:85px; height:29px; background:url(<%=basePath%>/images/sub_bt.png) no-repeat; display:block; float:left; margin-left:380px; margin-top:20px;}

table{ width:100%; background-color:#99bbe8;}
.table_1{ margin-top:10px;}
table tr td{ height:30px; line-height:30px; background-color:#fff;}
table tr td label{ float:left; margin-left:10px;}
.td_w_title{width:97px; text-align:center;}

</style>
</head>

<body>
<h1 class="head_title">学员档案</h1>
<div class="archives">
    <div class="clear"></div>
    <div class="jbxx fl">
        <div class="jbxx_l fl">
          <div class="jb_tit fl">基本信息</div>
          <div class="clear"></div>
          <h6>档案编号：${staff.staffCode }</h6>
          <div class="clear"></div>
          <table width="100%" border="0" cellspacing="1" cellpadding="1">
              <tr>
                <td class="td_w_title">学员姓名</td>
                <td>${dpp.studentname }</td>
                <td class="td_w_title">出生日期</td>
                <td>${staff.birthday }</td>
              </tr>
              <tr>
                <td class="td_w_title">家庭电话</td>
                <td>${staff.staffCode }</td>
                <td class="td_w_title">移动电话</td>
                <td>${dpp.studentphone }</td>
              </tr>
              <tr>
                <td class="td_w_title">E-mail</td>
                <td>${scon.contactWay }</td>
                <td class="td_w_title">QQ</td>
                <td>${staff.referenceCode }</td>
              </tr>
              <tr>
                <td class="td_w_title">身份证号</td>
                <td colspan="3">${dpp.studentcard }</td>
              </tr>
              <tr>
                <td class="td_w_title">家庭住址</td>
                <td colspan="3">${sadd.addressDetailed }</td>
              </tr>
            </table>
       </div>
        <div class="jbxx_r fr">
          <a href="javascript:void(0)"><img alt="" title="" src="<%=basePath%>/images/pto.png"/></a>
        </div>
    </div>
    <div class="clear"></div>
    
    <div class="bmqk fl">
       <div class="tr_title fl">报名情况</div>
       <div class="clear"></div>
       <table class="table_1" width="100%" border="0" cellspacing="1" cellpadding="1">
          <tr>
            <td style="width:105px; text-align:center">报名时间</td>
            <td style="width:105px; text-align:center">${fn:substring(dpp.registrationdate ,0,10)}</td>
            <td style="width:105px; text-align:center">报名点</td>
            <td style="width:105px; text-align:center">${dainf.organizationName }</td>
            <td style="width:110px; text-align:center">报名负责人</td>
            <td style="width:105px; text-align:center">${dainf.acceptPeople }</td>
            <td style="width:105px; text-align:center">驾照类型</td>
            <td style="text-align:center">${dpp.registrationcarname }</td>
          </tr>
        </table>
    </div>
    <div class="clear"></div>

    <div class="bmqk fl">
       <div class="tr_title fl">培训记录</div>
       <div class="clear"></div>
       <table class="table_1" width="100%" border="0" cellspacing="1" cellpadding="1">
          <tr>
            <td style="width:120px; text-align:center">教学内容</td>
            <td style="width:120px; text-align:center">起时间</td>
            <td style="width:120px; text-align:center">止时间</td>
            <td style="width:120px; text-align:center">学时</td>
            <td style="width:120px; text-align:center">教练</td>
            <td style="width:120px; text-align:center">老师意见</td>
            <td style="width:120px; text-align:center">完成/否</td>
          </tr>
           <s:iterator value="dar" status="number"  var="ent">
          <tr>
            <td style="width:120px; text-align:center">
				<span>${ent[0] }</span>
			</td>
            <td style="width:120px; text-align:center">
				<span>${fn:substring(ent[1] ,0,10) }</span>
			</td>
            <td style="width:120px; text-align:center">
				<span>${fn:substring(ent[2] ,0,10) }</span>
			</td>
            <td style="width:120px; text-align:center">
				<span>${ent[3] }</span>
			</td>
            <td style="width:120px; text-align:center">
				<span>${ent[4] }</span>
			</td>
            <td style="width:120px; text-align:center">
				<span>${ent[5] }</span>
			</td>
            <td style="width:120px; text-align:center">
				<span>${ent[6] }</span>
			</td>
           </tr>
           </s:iterator>
        </table>
    </div>
    <div class="clear"></div>
    
        <div class="bmqk fl">
       <div class="tr_title fl">考试记录</div>
       <div class="clear"></div>
       <table class="table_1" width="100%" border="0" cellspacing="1" cellpadding="1">
          <tr>
            <td style="width:105px; text-align:center">${dpt[0][0]}</td>
            <td style="width:105px; text-align:center">${dpt[0][1]}</td>
            <td style="width:105px; text-align:center">${dpt[1][0]}</td>
            <td style="width:105px; text-align:center">${dpt[1][1]}</td>
            <td style="width:105px; text-align:center">${dpt[2][0]}</td>
            <td style="width:105px; text-align:center">${dpt[2][1]}</td>
            <td style="width:105px; text-align:center">${dpt[3][0]}</td>
            <td style="text-align:center">${dpt[3][1]}</td>
            
          </tr>
        </table>
    </div>
    <div class="clear"></div>
	<form name="stuForm" id="stuForm" method="post" enctype="multipart/form-data">
	<input type="submit" name="submit" style="display: none" />
        <div class="bmqk fl">
       <div class="tr_title fl">归档资料</div>
       <div class="clear"></div>
       <table class="table_1" width="100%" border="0" cellspacing="1" cellpadding="1">
          <tr>
            <td style="width:155px;"><label>身份证复印件</label></td>
            <td style="width:55px; text-align:center"><input name="stua.cardcopy" type="checkbox" value="${stua.cardcopy }" class="check"></td>
            <td style="width:155px;"><label>照片</label></td>
            <td style="width:55px; text-align:center"><input name="stua.photo" type="checkbox" value="${stua.photo }" class="check"></td>
            <td style="width:155px;"><label>体检表</label></td>
            <td style="width:55px; text-align:center"><input name="stua.medical" type="checkbox" value="${stua.medical }" class="check"></td>
            <td style="width:155px;"><label>申请表</label></td>
            <td style="width:55px; text-align:center"><input name="stua.health" type="checkbox" value="${stua.health }" class="check"></td>
          </tr>
          <tr>
            <td style="width:155px;"><label>培训记录</label></td>
            <td style="width:55px; text-align:center"><input name="stua.records" type="checkbox" value="${stua.records }" class="check"></td>
            <td style="width:155px;"><label>学生证</label></td>
            <td style="width:55px; text-align:center"><input name="stua.stucard" type="checkbox" value="${stua.stucard }" class="check"></td>
            <td style="width:155px;"><label>暂住证（外地）</label></td>
            <td style="width:55px; text-align:center"><input name="stua.temporary" type="checkbox" value="${stua.temporary }" class="check"></td>
            <td style="width:155px;"><label></label></td>
            <td style="width:55px; text-align:center"></td>
            </tr>
          <tr>
            <td style="width:155px;"><label>查询单（增驾）</label></td>
            <td style="width:55px; text-align:center"><input name="stua.seach" type="checkbox" value="${stua.seach }" class="check"></td>
            <td style="width:155px;"><label>驾照复印件（增驾）</label></td>
            <td style="width:55px; text-align:center"><input name="stua.drivercopy" type="checkbox" value="${stua.drivercopy }" class="check"></td>
            <td style="width:155px;"><label></label></td>
            <td style="width:55px; text-align:center"></td>
            <td style="width:155px;"><label></label></td>
            <td style="width:55px; text-align:center"></td>
            </tr>
          <tr>
            <td style="width:155px;"><label>驾照复印件（毕业）</label></td>
            <td style="width:55px; text-align:center"><input name="stua.driverfin" type="checkbox" value="${stua.driverfin }" class="check"></td>
            <td style="width:155px;"><label></label></td>
            <td style="width:55px; text-align:center"></td>
            <td style="width:155px;"><label></label></td>
            <td style="width:55px; text-align:center"></td>
            <td style="width:155px;"><label></label></td>
            <td style="width:55px; text-align:center"></td>
            </tr>
        </table>
        <div class="gd fl">
         <div class="p_t fl">
         <label>其他档案</label>
         </div>
         <div class="yj fl">
           <table class="table_1" width="100%" border="0" cellspacing="1" cellpadding="1">
              <tr><td>
              	<input type="text" name="stua.other" value="${stua.other }" class="inp" style="width:600px; height:30px; line-height:30px; border:none;outline: none;padding: 0px;"/>
              </td></tr>
           </table>
         </div>
      </div>
    </div>
    <div class="clear"></div>
    
        <div class="bmqk fl">
       <div class="tr_title fl">归档意见</div>
       <div class="clear"></div>
      <div class="gd fl">
         <div class="p_t fl">
         <label>归档意见</label>
         <label>检查意见</label>
         </div>
         <div class="yj fl">
           <table class="table_1" width="100%" border="0" cellspacing="1" cellpadding="1">
              <tr><td>
              	<input type="text" name="stua.archivesviews" value="${stua.archivesviews }" id="archivesviews" class="inp" style="width:600px; height:30px; line-height:30px; border:none;outline: none;padding: 0px;"/>
              </td></tr>
              <tr><td>
              	<input type="text" name="stua.comments" value="${stua.comments }" id="comments" class="inp" style="width:600px; height:30px; line-height:30px; border:none;outline: none;padding: 0px;"/>
              </td></tr>
           </table>
         </div>
         <div class="pdf fr">
         <label>归档责任人:</label>
         <label>${stua.archivesname }
      	<input type="hidden" name="stua.archivesname" value="${stua.archivesname }"/></label>
         <label>检查责任人:</label>
         <label>${stua.checkname }
      	<input type="hidden" name="stua.checkname" value="${stua.checkname }" /></label>
         </div>
      </div>
      <div class="clear"></div>
      
      <div class="flo_bm fl">
      	<input type="hidden" name="stua.studentarchiveskey" value="${stua.studentarchiveskey }"/>
      	<input type="hidden" name="stua.studentarchivesid" value="${stua.studentarchivesid }"/>
      	<input type="hidden" name="stua.companyid" value="${stua.companyid }"/>
      	<input type="hidden" name="stua.staffid" value="${stua.staffid }"/>
      	<input type="hidden" name="stua.drivingprincipalid" value="${stua.drivingprincipalid }"/>
      	<input type="hidden" name="stua.cname" value="${stua.cname }"/>
      	<input type="hidden" name="stua.ctime" value="${stua.ctime }"/>
        <a href="javascript:save()" class="save" style="display: none;"></a>
      </div>
      
    </div>
    </form>
    <div class="clear"></div>

</div>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
<script type="text/javascript">

$(function() {

	if($("#comments").val() == ""){
		$(".save").css("display","");
	}else{
		$(".inp").attr("readonly","true");
	}
	$("input.check").each(function(){
		if($(this).attr("value") == "1"){
			$(this).attr("checked",true);
		}else{
			$(this).attr("checked",false);
		}
	});
	$("input.check").click(function(){
		if($(this).attr("checked") == true){
			$(this).attr("value","1");
		}else{
			$(this).attr("value","");
		}
	});
});
function save(){
	if (confirm("归档意见与检查意见确定保存？")) {
		if($("#archivesviews").val() == ""){
			alert("请填写归档意见！");
		}
		var url = basePath + "ea/studentarchive/ea_save.jspa?";
		$("#stuForm").attr("target", "hidden") .attr("action",url);
		document.stuForm.submit.click();
		token = 2;
	}
}
function re_load() {
	document.location.href = basePath + "ea/studentarchive/ea_toSTUarvhive.jspa?stua.studentarchivesid="+studentarchiveID;
	}
</script>
</body>
</html>
