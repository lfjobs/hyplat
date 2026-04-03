<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="org.apache.struts2.ServletActionContext"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加车辆安全检查</title>
    <link href="<%=basePath%>css/addCarSafety.css" rel="stylesheet" type="text/css" />
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
    <script src="<%=basePath%>js/ea/office_ea/addCarSafeinformation.js"></script>
	<script type="text/javascript">
	 	 var select = 1;
		 var  basePath='<%=basePath%>';           
         var  carID = '';
         var  safetyid = '';
         var pNumber = ${pageNumber}
		</script>  
<STYLE type="text/css">
.biankuang{
width: 40;
height:40;
cursor:pointer;
}
.operate{
cursor:pointer;
}
.leixing{
width: 40px;
border: 0;
border-color: white;
}
</STYLE>
<style type="text/css">
body,html{margin:0; padding:0; height:100%;}
#main{position: relative; min-height:100%; _height:100%; /* for ie6 因为ie6不支持min-height */ background:#eee;}
#content {padding:10px; padding-bottom:30px; /* 不至被footer盖住 */}
#footer {position:absolute; bottom:0; height:30px; width:100%; background:#ccc;}
</style>

  </head>
  
  <body>
   <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data" action=""/>  
   <input type="submit" name="submit" style="display:none"/>
    <table height="33" width="99%" cellspacing="0" cellpadding="0" border="0" align="center" id="caffes"> 
  	<tr> 
    <td align="center" style="font-size: 14px; font-weight: bold;"><br><br>公司 资阳胜威 部门 教务处车辆管理组 汽车安全卫生检查表<br><br></td></tr></table>&nbsp;&nbsp;&nbsp; 检查时间 : <input type="text" onfocus="date(this);" class="td_bg01" name="adddate" id="adddate"><br><br>
  <table width="99%" align="center" cellpadding="1" cellspacing="1" class="table" style="margin-bottom:5px" id ="contexttable">
  <tr>
    <td width="0" height="21" rowspan="3" align="center" bgcolor="#E4F1FA">序号</td>
    <td width="25" rowspan="3" align="center" bgcolor="#E4F1FA"> 选择</td>
    <td width="87" rowspan="3" align="center" bgcolor="#E4F1FA"> 职务</td>
    <td width="0" rowspan="3" align="center" bgcolor="#E4F1FA">责任人</td>
    <td width="81" rowspan="3" align="center" bgcolor="#E4F1FA">车牌号</td>
    <td width="4%" colspan="8" align="center" bgcolor="#E4F1FA">卫生/扣分数（分）</td>
    <td width="5%" colspan="6" align="center" bgcolor="#E4F1FA">安全/扣分数（分）</td>
    <td width="0" rowspan="3" align="center" bgcolor="#E4F1FA">奖励分</td>
    <td width="0" rowspan="3" align="center" bgcolor="#E4F1FA">扣分</td>
    <td width="0" rowspan="3" align="center" bgcolor="#E4F1FA">总计<br>
    得分</td>
    <td width="0" rowspan="3" align="center" bgcolor="#E4F1FA">备注</td>
    <td rowspan="3" align="center" bgcolor="#E4F1FA">操作</td>
  </tr>
  <tr>
    <td colspan="4" align="center" bgcolor="#E4F1FA">车内</td>
    <td colspan="4" align="center" bgcolor="#E4F1FA">车外</td>
    <td width="5%" rowspan="2" align="center" bgcolor="#E4F1FA">发动机</td>
    <td width="0" rowspan="2" align="center" bgcolor="#E4F1FA">刹车</td>
    <td width="0" rowspan="2" align="center" bgcolor="#E4F1FA">大灯</td>
    <td width="0" rowspan="2" align="center" bgcolor="#E4F1FA">应急灯</td>
    <td width="0" rowspan="2" align="center" bgcolor="#E4F1FA">防雾灯</td>
    <td width="0" rowspan="2" align="center" bgcolor="#E4F1FA">转弯灯</td>
  </tr>
  <tr>
    <td width="4%" align="center" bgcolor="#E4F1FA">车箱</td>
    <td width="0" align="center" bgcolor="#E4F1FA">后备箱</td>
    <td width="0" align="center" bgcolor="#E4F1FA">脚垫</td>
    <td width="0" align="center" bgcolor="#E4F1FA">坐垫</td>
    <td width="0" align="center" bgcolor="#E4F1FA">车身</td>
    <td width="0" align="center" bgcolor="#E4F1FA">玻璃</td>
    <td width="0" align="center" bgcolor="#E4F1FA">门窗</td>
    <td width="0" align="center" bgcolor="#E4F1FA">发动机</td>
  </tr>
  <%int number = 1; %>
<c:forEach var="arr" items="${carNumList}">
	<tr  id="${arr[3]}" class="x">
	<td class="td_bg01" align="center" >   <!-- 序号 -->
		<span><%=number%></span>
	</td>
    <td class="td_bg01" align="center" >   <!-- 选择 -->
		<input type="radio" name="a"  class="JQuerypersonvalue" value="${arr[3]}"/>
		<input type="hidden" name="dtcarassemblytablemap[<%=number%>].carid" value="${arr[3]}"/>
	</td>
    <td width="87" align="center"><input type="text" id="dostopname" readonly="readonly" value="${arr[0]}" class="leixing" name=""></td>  <!-- 职务 -->
    <td align="center"><input type="text" id="stffname" readonly="readonly" class="leixing" name="staffName" value="${arr[1]}"></td>   <!--责任人 -->
    <td width="81" align="center"><input type="text" id="carNum" readonly="readonly" value="${arr[2]}" class="leixing" name="">
      <input name="carInformation.carKey" id="carKey" type="hidden"/>
      <input name="carInformation.carID" id="carsafeid" type="hidden" value="${arr[3]}"/>
    </td>  <!-- 车牌号 -->
    <td align="center"><select name="dtcarassemblytablemap[<%=number%>].boxcar" id="select1" class="istrues " >
		<option selected="selected">选择</option>
       	<option value="00">优</option>
        <option value="01">良</option>
        <option value="02">差</option>
      </select>
    </td>
    <td align="center"><select name="dtcarassemblytablemap[<%=number%>].cartrunk" id="select2"  class="istrues">
        <option selected="selected">选择</option>
        <option value="00">优</option>
        <option value="01">良</option>
        <option value="02">差</option>
    </select></td>
    <td align="center"><select name="dtcarassemblytablemap[<%=number%>].floormats" id="select3"  class="istrues">
        <option selected="selected">选择</option>
        <option value="00">优</option>
        <option value="01">良</option>
        <option value="02">差</option>
    </select></td>
    <td align="center"><select name="dtcarassemblytablemap[<%=number%>].cushion" id="select4"  class="istrues">
        <option selected="selected">选择</option>
        <option value="00">优</option>
        <option value="01">良</option>
        <option value="02">差</option>
    </select></td>
    <td align="center"><select name="dtcarassemblytablemap[<%=number%>].carbody" id="select5"  class="istrues">
        <option selected="selected">选择</option>
        <option value="00">优</option>
        <option value="01">良</option>
        <option value="02">差</option>
    </select></td>
    <td align="center"><select name="dtcarassemblytablemap[<%=number%>].glass" id="select6"  class="istrues">
        <option selected="selected">选择</option>
        <option value="00">优</option>
        <option value="01">良</option>
        <option value="02">差</option>
    </select></td>
    <td align="center"><select name="dtcarassemblytablemap[<%=number%>].doorwindows" id="select7"  class="istrues">
       <option selected="selected">选择</option>
       <option value="00">优</option>
        <option value="01">良</option>
        <option value="02">差</option>
    </select></td>
    <td align="center"><select name="dtcarassemblytablemap[<%=number%>].engine" id="select8"  class="istrues">
        <option selected="selected">选择</option>
        <option value="00">优</option>
        <option value="01">良</option>
        <option value="02">差</option>
    </select></td>
    <td align="center"><select name="dtcarassemblytablemap[<%=number%>].engine" id="select9"  class="istrues">
        <option selected="selected">选择</option>
        <option value="00">优</option>
        <option value="01">良</option>
        <option value="02">差</option>
    </select></td>
    <td align="center"><select name="dtcarassemblytablemap[<%=number%>].braking" id="select10"  class="istrues">
        <option selected="selected">选择</option>
        <option value="00">优</option>
        <option value="01">良</option>
        <option value="02">差</option>
    </select></td>
    <td align="center"><select name="dtcarassemblytablemap[<%=number%>].biglight" id="select11"  class="istrues">
        <option selected="selected">选择</option>
        <option value="00">优</option>
        <option value="01">良</option>
        <option value="02">差</option>
    </select></td>
    <td align="center"><select name="dtcarassemblytablemap[<%=number%>].soslight" id="select12"  class="istrues">
        <option selected="selected">选择</option>
        <option value="00">优</option>
        <option value="01">良</option>
        <option value="02">差</option>
    </select></td>
    <td align="center"><select name="dtcarassemblytablemap[<%=number%>].fogprooflight" id="select13"  class="istrues">
        <option selected="selected">选择</option>
        <option value="00">优</option>
        <option value="01">良</option>
        <option value="02">差</option>
    </select></td>
    <td align="center"><select name="dtcarassemblytablemap[<%=number%>].turnofflight" id="select14"  class="istrues">
    	 <option selected="selected">选择</option>
    	 <option value="00">优</option>
        <option value="01">良</option>
        <option value="02">差</option>
    </select></td>
    <td align="center" width="30" style="text-align: center"><input type="text" readonly="readonly"  class="leixing jiangli" id="j"></td>   <!-- 奖励分 -->
    <td align="center" style="text-align: center"><input type="text" readonly="readonly" class="leixing koufen" id="k"></td>  <!-- 扣分 -->
    <td width="75" align="center" style="text-align: center"><input type="text" readonly="readonly" class="leixing totlecount" >
    <input type="hidden" name="countrewards"  id="countrewards"  value="">   <!--本次总计奖励分 -->
    <input type="hidden" name="countpenalty"  id="countpenalty"  value=""> <!--  本次总计扣分 -->
    <input type="hidden" name="totleScore"  id="totleScore"  value=""><!-- 本次总计得分 --></td>  
    <td align="center"><input type="text"  name="dtcarassemblytablemap[<%=number%>].remark" class="leixing" ></td>      <!-- 备注 -->
    <td align="center"> <img src="<%=basePath%>/images/gtk-del.png" width="16" height="16" title="删除" border="0" id="delete" class="operate"/></td>
  </tr>
  <!-- 主要内容  结束 -->
   <%
   number++;
   %>
</c:forEach>
<tr></tr>
</table>

<table width="99%" height="33" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="left" style="font-size:14px; color:#000000; font-weight:bold">注：扣分细则</td>
  </tr>
</table>
<table width="99%" height="33" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="left" style="font-size:12px; line-height:22px ">（一）卫生：1、车箱干净无杂乱；2、后备箱完好清洁；3、脚垫无灰尘；4、车身无泥土；5、玻璃窗门无灰；6、门窗干净无损；7、发动机无杂音。评优扣分方法（按月计算）：评优打勾 10个勾奖 0.5分 评劣打半勾 评10个半勾扣0.5分 评差打× ；每个差扣0.5分 部门按工位累加                                                                                                                     <br>
    （二）安全：1、发动机正常；2、刹车灵活；3、大灯正常；4、应急灯正常；5、防雾灯正常；6、转弯灯正常。评优扣分方法（按月计算）：评优打勾 10个勾奖 0.5分 评劣打半勾 评10个半勾扣0.5分 评差打× ；每个差扣0.5分 部门按工位累加 </td>
  </tr>
</table><br/><br/>
<div align="center"><input type="button" value="保存" class="input-button JQuerySubmit" style="cursor:pointer;width:80px;"> 
<input type="button" value="取消" class="input-button JQueryreturn" style="cursor:pointer;width:80px;"> 
</div></form>
  </body>
</html>
