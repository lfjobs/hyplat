<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>兑换账单</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/Chart.js"></script>
	<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/slideUpDownRefresh_files/iscroll.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/slideUpDownRefresh_files/bootstrap.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/slideUpDownRefresh_files/bootstrap-theme.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/slideUpDownRefresh_files/iscroll.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath %>css/contacts/style12.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
	<script type="text/javascript">
		var khd="${khd}";
		var year="${year}";
		var month="${month}";
		var basePath="<%=basePath%>";
    	var user="${user}";
    	var sccid="${sccid}";
    	var pagenumber="0";
    	var pagecount="0";
    	var t;
    	var flag = "${flag}";
    	var mths = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"];  
		var WEEKs =["周日", "周一", "周二", "周三", "周四", "周五","周六"];  
		var WEKs = [ "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" ]; 
		 
    	function toWeek(date){
			var  str=date.toString();
       		str =  str.replace(/-/g,"/");
   			var oDate1 = new Date(str);
			var d = new Date(oDate1.getTime());
			var week = WEEKs[d.getDay()];
		    return week;
		}
		
		function toDDMMMYYYY(date) {  
		    var d = new Date(date.getTime());  
		    var dd = d.getDate() < 10 ? "0" + d.getDate() : d.getDate().toString();  
		    var mmm = mths[d.getMonth()];
		    var yyyy = d.getFullYear().toString(); //2011  
		        //var YY = YYYY.substr(2);   // 11 
		    return mmm+"-"+dd;
		}
		
		
    	//+---------------------------------------------------
		//| 求两个时间的天数差 日期格式为 YYYY-MM-dd
		//+---------------------------------------------------
		function daysBetween(DateOne,DateTwo){
			var OneMonth = DateOne.substring(5,DateOne.lastIndexOf ('-'));
			var OneDay = DateOne.substring(DateOne.length,DateOne.lastIndexOf ('-')+1);
			var OneYear = DateOne.substring(0,DateOne.indexOf ('-'));
			
			var TwoMonth = DateTwo.substring(5,DateTwo.lastIndexOf ('-'));
			var TwoDay = DateTwo.substring(DateTwo.length,DateTwo.lastIndexOf ('-')+1);
			var TwoYear = DateTwo.substring(0,DateTwo.indexOf ('-'));
			
			var cha=((Date.parse(OneMonth+'/'+OneDay+'/'+OneYear)- Date.parse(TwoMonth+'/'+TwoDay+'/'+TwoYear))/86400000);
			return Math.abs(cha);
		}
		
		function fmoney(s, n){
		   n = n > 0 && n <= 20 ? n : 2;
		   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
		   var l = s.split(".")[0].split("").reverse(),
		   r = s.split(".")[1];
		   t = "";
		   for(i = 0; i < l.length; i ++ )
		   {
		      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
		   }
		   return t.split("").reverse().join("") + "." + r;
		}
	</script>
	<style type="text/css">
		.clearfix{
    clear: both;
}

/*--7.15--*/

/*--bill-details--*/
header{
    width: 100%;
    height: 45px;
    /*position: fixed;*/
    top: 0;
    background-color: #fff;
    border-bottom: 1px solid #c6c6c6;
    z-index: 9;
}
header ul{
    padding: 0;
    height: 100%;
}
header ul li{
    float: left;
    text-align: center;
}
header ul li:nth-child(2){
    font-size: 5vw;
}
header ul li img{
    width: 35%;
    margin-top: 1rem;
}
.bill-de_head{
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 20px 0;
    border-bottom: 1px solid #ddd;
}
.bill-de_head div{
    width: 50px;
    height: 50px;
    border: 2px solid #ddd;
    border-radius: 50%;
    overflow: hidden;
    margin-right: 15px;
}
.bill-de_head div img{
    width: 100%;
    height: 100%;
}
.bill-de_head span{
    font-size: 18px;
    letter-spacing: 1px;
}
.bill-de_mil{
    text-align: center;
    border-bottom: 1px solid #ddd;
    padding: 20px 0;
}
.bill-de_mil p{
    font-size: 31px;
    word-wrap: break-word;
}
.bill-de_mil h5{
    font-size: 15px;
    padding-top: 5px;
}
.bill-de_mil2{
    border-bottom: 1px solid #ddd;
}
.bill-de_mil2 li{
    display: flex;
    margin: 20px 0;
}
.bill-de_mil2 li p:nth-child(1){
    width: 25%;
    font-size: 16px;
}
.bill-de_mil2 li p:nth-child(2){
    font-size: 16px;
    width: 75%;
    word-wrap: break-word;
}
.bill-de_mil2 li p span{
    margin-left: 10px;
}
/*--bill-details end--*/

/*--convert-bill--*/
.con-bill_mil li{
    display: box;
    display: -webkit-box;
    display: -moz-box;
    display: -ms-flexbox;
    display: -webkit-flex;
    display: flex;
    padding: 15px 0;
    border-bottom: 1px solid #ddd;
    -webkit-box-align: center;
    -webkit-align-items: center;
    -moz-align-items: center;
    -ms-align-items: center;
    -o-align-items: center;
    align-items: center;
}
.con-bill_mil li p{
    width: 100%;
    font-size: 1.4rem;
    color: #afacac;
}
.con-bill_mil li .con-bill_head{
   width: 47px;
    height: 47px;
    /* border: 1px solid #ddd; */
    border-radius: 50%;
    overflow: hidden;
    margin-right: 15px;
    margin-left: 15px;
}
.con-bill_mil li .con-bill_head img{
    width: 100%;
    height: 100%;
}
.con-bill_txt{
    width: 50%;
}
.con-bill_txt h3{
    font-size: 1.4rem;
    font-weight: 700;
    padding-bottom: 0.5rem;
}
.zj{
	font-size: 1.4rem;
    line-height: 1.5;
    color: #afacac;
}
.con-bill_txt p{
    width: 100% !important;
    font-size: 15px;
    color: #666 !important;
}

#head_img{
	position: absolute;
    top: 50%;
    transform: translateY(-50%);
    -webkit-transform: translateY(-50%);
    -moz-transform: translateY(-50%);
    -ms-transform: translateY(-50%);
    -o-transform: translateY(-50%);
}
#head_img2{
	position: absolute;
    top: 50%;
    transform: translateY(-50%);
    -webkit-transform: translateY(-50%);
    -moz-transform: translateY(-50%);
    -ms-transform: translateY(-50%);
    -o-transform: translateY(-50%);
}
/*--convert-bill end--*/
	</style>
</head>

<body>
    
	<div class="wfj01_019">
    	
    	<s:if test="khd==0">
    	<!--中联园区头部-->
    	<div class="wfj_top">
        	<ul>
            	<li><a href="<%=basePath%>ea/jinbi/ea_gethyjifen.jspa?user=${user}&sccid=${sccid}&flag=${flag}&khd=0" target="_self"><img src="<%=basePath %>images/ea/finance/BenDis/wfj_return_01.png" id="head_img"/></a></li>
            	<li>兑换账单</li>
            	<li><a href="javascript:;"><img src="<%=basePath %>images/ea/finance/BenDis/top_more.png" id="head_img2"/></a></li>
            </ul>
        </div>
    	<!--中联园区头部 end-->
    	</s:if>
        <input type="text" id = "d2"  style="display:none;"/>
        
        
        
        <div class="wfj01_019_top">
        	<div class="wfj01_019_top_date" onclick="WdatePicker({el:'d2',skin:'default',dateFmt:'yyyy-MM',onpicked:pickedFunc})">
        	   
            	<ul>
                	<li ><span id="year">${year }</span>年</li>
                	<li id="dateimg"><font><span id="month">${month }</span></font>月<img src="<%=basePath %>images/ea/finance/BenDis/wfj_more_date_01.png"/></li>
                </ul>
            </div>
            <div class="wfj01_019_top_expend">
            	<ul>
                	<li>兑换</li>
                	<li><fmt:formatNumber value="${xjtx==null?0:xjtx}" groupingUsed="true"/></li>
                </ul>
            </div>
            <div class="wfj01_019_top_income">
            	<ul>
                	<li>收入</li>
                	<li><fmt:formatNumber value="${jdetail==null?0:jdetail}" groupingUsed="true"/></li>
                </ul>
            </div>
        </div>
        
        
        
        
        <div class="wfj01_019_top_title">
            <ul class="wfj01_019_top_link left">
                <li><img src="<%=basePath %>images/ea/finance/BenDis/wfj_gold_bill_01.png" />账单明细</li>
            </ul>
            <ul class="wfj01_019_top_link right">
                <li><img src="<%=basePath %>images/ea/finance/BenDis/wfj_gold_bill_02.png" />类别报表</li>
            </ul>
        </div>
     
        <div class="wfj01_019_content">
        
        
        
        	<div class="wfj01_019_hidden">
                <div class="wfj01_019_con">
                	
                	
                	
                    <div class="wfj01_019_con_bill">                           					
                        <div class="content" id="jbtab"></div>                                         
                    </div>
                    
                    
                    
                    
                    <div class="wfj01_019_con_type">
                        <table>
                            <tr>
                                <td>库存金币</td><%-- ${jifen.wfjJifenScore } --%>
                                <td><input class="allgold" type="text" disabled="disabled"/></td>
                            </tr>
                            <tr>
                                <td>已兑换金币</td>
                                <td><input class="othergold" type="text" disabled="disabled"/></td>
                            </tr>
                        </table>
                    	<div class="wfj01_019_con_type_width">
                        	<canvas id="myChart" width="100%" height="100%"></canvas>
                        	<div class="wfj01_019_con_type_text">
								<p>库存金币数</p>
								<p><span class="span1">${jifen.wfjJifenScore }</span></p>
								<p>已兑换金币数</p>
								<p><span class="span2">${xjtx}</span></p>
							</div>
                        </div>
                    </div>
                </div>
            </div>
          
        </div>
        </div> 
        
        
              
   <div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
   <script type="text/javascript">
		
		function getHeight(){

			
			t=setTimeout("getHeight()",200);
			if($(".last").length>0){
			   	if($(".last").offset().top-$(".wfj01_019_top").height()*3.5<$(window).height()){
			   		if(pagenumber<pagecount){
			   			loaded();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
			   		}
			   	}		 
			  }
		}	
									
			function loaded () {
				clearTimeout(t);
				 pagenumber++;						
						var url=basePath+"/ea/jinbi/sajax_AjxaHyjifenBill.jspa?";
						$.ajax({
							url : url,
							type : "get",
							async : false,
							dataType : "json",
							data:{
							  "user":user,
							  "sccid":sccid,
							  "year":year,
							  "month":month,
							  "pageNumber":pagenumber
							},
							success : function (data) {
								var member = eval("(" + data + ")");
								var pageForm = member.pageForm;
								var jbtab="";	
								if(pageForm == null  ){
									
								}else{
								pagenumber = pageForm.pageNumber;
								pagecount = pageForm.pageCount;
								var jblist=pageForm.list;
								$(".last").removeClass("last");				
								for( var i = 0; i < jblist.length; i++){
									var jb=jblist[i];									
									var zj=toWeek(jb.jifenDetailDate.toString());
									var  str=jb.jifenDetailDate.toString();
       								str =  str.replace(/-/g,"/");
       								var sj=toDDMMMYYYY(new Date(str));
       								
       								
       								if(i==jblist.length-1){
    									jbtab+="<ul class='con-bill_mil last' id='"+jb.jifenDetailId +"' onclick='details(\""+jb.jifenDetailId +"\",\""+jb.wfjGuizeCalc+"\")'><li><div align=center style='width:15%;'><span class='zj'>"+zj+"</span>";
       								}else{
    									jbtab+="<ul class='con-bill_mil' id='"+jb.jifenDetailId +"' onclick='details(\""+jb.jifenDetailId +"\",\""+jb.wfjGuizeCalc+"\")'><li><div align=center style='width:15%;'><span class='zj'>"+zj+"</span>";
       								}
       								
					                jbtab+="<p>"+sj +"</p></div>";
					                jbtab+="<div class='con-bill_head'>";
					                
					                if(jb.wfjGuizeId=="WfjGuize20151120R5HZ2UPCV50000000006"){
					                	jbtab+="<img src='"+basePath+"images/ea/finance/BenDis/赠@2x.png' />";
					                }	
									if(jb.wfjGuizeId=="69CD270453D54AE4817274AC0D269B71"){
										jbtab+="<img src='"+basePath+"images/ea/finance/BenDis/取@2x.png' />";
									}
									if(jb.wfjGuizeId=="WfjGuize201605152R4ASTNAP90000000001"){
										jbtab+="<img src='"+basePath+"images/ea/finance/BenDis/本@2x.png' />";
									}
									if(jb.wfjGuizeId=="WfjGuize20160901AII9IY89ZX0000000001"){
										jbtab+="<img src='"+basePath+"images/ea/finance/BenDis/充值@2x.png' />";
									}
									if(jb.wfjGuizeId=="WfjGuize20160726AHW88UNBRN0000000001"||jb.wfjGuizeId=="WfjGuize20160726AHW88UNBRN0000000002"){
										jbtab+="<img src='"+basePath+"images/ea/finance/BenDis/包@2x.png' />";
									}
									if(jb.wfjGuizeId=='WfjGuize201701118RU8JA6IG60000000001'){
										jbtab+="<img src='"+basePath+"images/ea/finance/BenDis/惩罚icon@2x.png' />";
									}
									if(jb.wfjGuizeId=='WfjGuize2017011192CRSBGFZ70000000001'){
										jbtab+="<img src='"+basePath+"images/ea/finance/BenDis/收回@2x.png' />";
									}
									
									
									jbtab+="</div><div class='con-bill_txt'><h3>";
									if(jb.wfjGuizeCalc=="A"){
										jbtab+="+";
									}else if(jb.wfjGuizeCalc=="M"){
										jbtab+="-";
									}
									
									jbtab+=fmoney(jb.jifenDetailScore,2) +"</span></h3><p>"+jb.wfjGuizeName+"</p></div></li></ul>";
									var jfdate=jb.jifenDetailDate;
					            	jfdate=jfdate.substring(0,19);	            	
                    				var zj=toWeek(jfdate);
                    				$("#"+"jb.jifenDetailId").find(".zj").text(zj);
								
								
								}
								$("div#jbtab").append(jbtab);
								
								$(".wfj01_019_con_bill").find("td").attr("style","font-size:" + $(window).height() * 0.02+ "px; border-bottom:"+ $(window).height() * 0.003+ "px solid #F0F0F0; height:"+ $(window).height() * 0.065+ "px;");
								
								$(".wfj01_019_con_type").attr("style","margin-top:" + $(window).height() * 0.02+ "px;");
								$(".wfj01_019_con_type").find("td").attr("style","height:" + $(window).height()* 0.05 + "px;font-size:"+ $(window).height()* 0.025+ "px; padding-left:"+ $(window).height() * 0.01+ "px;");
								$(".wfj01_019_con_type").find("input").attr("style","width:" + $(window).height() * 0.1+ "px;height:" + $(window).height()* 0.03 + "px;");
								$(".wfj01_019_con_type").find("#myChart").attr("style","margin-top:" + $(window).height() * 0.03+ "px;");
								
												
								$(".wfj01_019_date").attr("style","top:" + $(window).height() * 0.2 + "px;");
								$(".wfj01_019_date").find("li").attr("style","font-size:" + $(window).height() * 0.025+ "px;");
								$(".wfj01_019_choice_date").attr("style"," height:" + $(window).height() * 0.06+ "px; line-height:"+ $(window).height() * 0.06+ "px; border-bottom:"+ $(window).height() * 0.008+ "px solid #4CACCE;font-size:"+ $(window).height() * 0.025+ "px;");
								$(".wfj01_019_choice_date").find("font").attr("style","padding-left:" + $(window).height()* 0.015 + "px;");
								$(".wfj01_019_date").find("li").find("div").attr("style","font-size:" + $(window).height() * 0.025+ "px;");
								
								var len = $(".wfj01_019_con_bill").find("tr").length;
								for ( var i = 0; i < len; i++) {
									if ($(".wfj01_019_con_bill").find("tr").eq(i).find(".wfjGuizeCalc").val() == "A") {
										$(".wfj01_019_con_bill").find("tr").eq(i).find("td").eq(2).css("color","#5584D3");
										$(".wfj01_019_con_bill").find("tr").eq(i+1).find("td").eq(0).css("color","#5584D3");
									} else {
										$(".wfj01_019_con_bill").find("tr").eq(i).find("td").eq(2).css("color","#EB4F38");
										$(".wfj01_019_con_bill").find("tr").eq(i+1).find("td").eq(0).css("color","#5584D3");
									}
								}
								getHeight();
								
							}
							},
							error:function(data){
								alert("获取单据失败");
							}
						}); 
					
				}
	</script>
	<script type="text/javascript">
		var data = [ {
			value : ${jifen.wfjJifenScore==null?0:jifen.wfjJifenScore==null },
			color : "#6387A8"
		}, {
			value : ${xjtx==null?0:xjtx},
			color : "#FA5C29"
		} ]

		$(document).ready(function(e) {	
			loaded();
		        $(".content_hidden").attr("style",";overflow: auto;position: relative;"+"top:"+$(window).height()*0.08+"px");
		        $(".content").attr("style",";overflow: auto;width:95%;margin:0 auto;");
				
				//中联园区头部
				$(".wfj_top").attr("style","height:" + $(window).height()* 0.08 + "px;line-height:"+ $(window).height() * 0.08+ "px;");
				//$(".wfj01_019 .wfj_top ul").attr("style","height:" + $(window).height()* 0.08 + "px;line-height:"+ $(window).height() * 0.08+ "px;");

				$(".wfj_top").find("li").attr("style", "width:15%;");
				$(".wfj_top").find("li").find("img").attr("style","height:"+$(window).height()*0.03+"px");
           		$(".wfj_top").find("li").find("img").each(function(){
	            	var ihei=imgPosition($(".wfj_top").height(),$(this).height());
	            	/* $(this).attr("style",$(this).attr("style")+";margin-top:"+ihei+"px"); */
	            	$(this).attr("style",$(this).attr("style")+";");

           		});
				$(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:" + $(window).height()* 0.06 + "px;");
				$(".wfj01_019_top").attr("style","height:" + $(window).height()* 0.12 + "px;line-height:"+ $(window).height() * 0.12+ "px;");
				$(".wfj01_019_top").find("li").attr("style","font-size:" + $(window).height()* 0.025 + "px;line-height:"+ $(window).height() * 0.06+ "px;");
				$(".wfj01_019_top").find("font").attr("style","font-size:" + $(window).height() * 0.04+ "px;");
				$(".wfj01_019_top").find("img").attr("style","width:" + $(window).height() * 0.025+ "px;");
				$(".wfj01_019_top_date").attr("style"," border-right:" + $(window).height()* 0.003 + "px dotted #FFF;;");
				$(".wfj01_019_top_title").attr("style","height:" + $(window).height() * 0.1+ "px;line-height:"+ $(window).height() * 0.1 + "px;");
				$(".wfj01_019_top_link").find("li").attr("style","font-size:" + $(window).height() * 0.03+ "px;");
				$(".wfj01_019_top_link").find("img").attr("style","height:" + $(window).height()* 0.06+ "px; margin-right:"+ $(window).height() * 0.01+ "px;");
				$(".wfj01_019_top_link").find("li").eq(0).attr("style","font-size:" + $(window).height() * 0.03+ "px;color:#ea8010;");
				$(".wfj01_019_con").attr("style","margin-top" + $(window).height() * 0.01+ "px;");
				$(".wfj01_019_con_bill").find("td").attr("style","font-size:" + $(window).height() * 0.02+ "px; border-bottom:"+ $(window).height() * 0.003+ "px solid #F0F0F0; height:"+ $(window).height() * 0.065+ "px;");
				$(".wfj01_019_con_type").attr("style","margin-top:" + $(window).height() * 0.02+ "px;");
				$(".wfj01_019_con_type").find("td").attr("style","height:" + $(window).height()* 0.05 + "px;font-size:"+ $(window).height()* 0.025+ "px; padding-left:"+ $(window).height() * 0.01+ "px;");
				$(".wfj01_019_con_type").find("input").attr("style","width:" + $(window).height() * 0.1+ "px;height:" + $(window).height()* 0.03 + "px;");
				$(".wfj01_019_con_type").find("#myChart").attr("style","margin-top:" + $(window).height() * 0.03+ "px;");
				$(".wfj01_019_con .wfj01_019_con_type .wfj01_019_con_type_width .wfj01_019_con_type_text").css({"position":"absolute","top":$(window).height()*0.5+"px","width":$(window).width()*0.27+"px","left":"41%"})
				$(".wfj01_019_con .wfj01_019_con_type .wfj01_019_con_type_width .wfj01_019_con_type_text").find("p").css({"font-size":$(window).height()*0.02+"px","line-height":$(window).height()*0.033+"px"})
				$(".wfj01_019_con .wfj01_019_con_type .wfj01_019_con_type_width .wfj01_019_con_type_text").find("p").find(".span1").css({"color":"#6387A8","font-size":$(window).height()*0.03+"px"})
				$(".wfj01_019_con .wfj01_019_con_type .wfj01_019_con_type_width .wfj01_019_con_type_text").find("p").find(".span2").css({"color":"#FA5C29","font-size":$(window).height()*0.03+"px"})	
				$(".wfj01_019_date").attr("style","top:" + $(window).height() * 0.2 + "px;");
				$(".wfj01_019_date").find("li").attr("style","font-size:" + $(window).height() * 0.025+ "px;");
				$(".wfj01_019_choice_date").attr("style"," height:" + $(window).height() * 0.06+ "px; line-height:"+ $(window).height() * 0.06+ "px; border-bottom:"+ $(window).height() * 0.008+ "px solid #4CACCE;font-size:"+ $(window).height() * 0.025+ "px;");
				$(".wfj01_019_choice_date").find("font").attr("style","padding-left:" + $(window).height()* 0.015 + "px;");
				$(".wfj01_019_date").find("li").find("div").attr("style","font-size:" + $(window).height() * 0.025+ "px;");
				$(".wfj01_019_date").find(".wfj01_019_date_center").attr("style"," height:" + $(window).height()* 0.18 + "px;");
				$(".wfj01_019_date_bottom").attr("style"," height:" + $(window).height()* 0.06 + "px; line-height:"+ $(window).height() * 0.06+ "px;");
				$(".allgold").css("backgroundColor", "#6387A8");
				$(".othergold").css("backgroundColor", "#FA5C29");
				
				$(".zj").css("font-size",$(window).width()*0.04+"px");
				$(".sj").css("font-size",$(window).width()*0.04+"px");
				//$(".con-bill_txt h3").css("font-size",$(window).width()*0.04+"px");
				$(".con-bill_mil li p").css("font-size",$(window).width()*0.04+"px");
				$(".con-bill_mil li .con-bill_head").attr("style","width:"+$(window).width()*0.126+"px;"+"height:"+$(window).width()*0.126+"px;");
				
				 $(".wfj01_019 .wfj_top ul").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;position: relative;");
		            $(".wfj01_019 .wfj_top ul li").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;position: relative;");
		            $(".wfj01_019 .wfj_top ul li:nth-child(1)").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;width:15%;");

		            $(".wfj01_019 .wfj_top ul li:nth-child(2)").attr("style","width:70%;font-size:" + $(window).height()* 0.025 + "px;");

		            $(".wfj01_019 .wfj_top ul li:nth-child(2)").attr("style","width:70%;font-size:"+$(window).height()*0.025+"px;");

		            $(".wfj01_019 .wfj_top ul li:nth-child(3)").attr("style","width:15%;");
				
				var len = $(".wfj01_019_con_bill").find("tr").length;
				for ( var i = 0; i < len; i++) {
					if ($(".wfj01_019_con_bill").find("tr").eq(i).find(".wfjGuizeCalc").val() == "A") {
						$(".wfj01_019_con_bill").find("tr").eq(i).find("td").eq(2).css("color","#5584D3");
						$(".wfj01_019_con_bill").find("tr").eq(i+1).find("td").eq(0).css("color","#5584D3");
					} else {
						$(".wfj01_019_con_bill").find("tr").eq(i).find("td").eq(2).css("color","#EB4F38");
						$(".wfj01_019_con_bill").find("tr").eq(i+1).find("td").eq(0).css("color","#5584D3");
					}
				}

				$(".wfj01_019_top_link").find("li").click(function() {
					if ($(this).text() == "账单明细") {
						$(".wfj01_019_top_link").find("li").css("color","#666");
						$(this).css("color","#ea8010");
						$(".wfj01_019_con_bill").css("display","block");
						$(".wfj01_019_con_type").css("display","none");
					} else {
						$(".wfj01_019_top_link").find("li").css("color","#666");
						$(this).css("color","#5D9164");
						$(".wfj01_019_con_bill").css("display","none");
						$(".wfj01_019_con_type").css("display","block");
						$("#myChart").css("width","100%");
						$("#myChart").css("height","auto");
	
						var ctx = document.getElementById("myChart").getContext('2d');
						var myChart = new Chart(ctx).Doughnut(data);
					}
				});

				if ($(window).width() > $(window).height()) {
					$(".wfj01_019").css("width", "70%");
				} else {
					$(".wfj01_019").css("width", "100%");
				}

				var h = $(".wfj01_019_con").height()+ $(window).height() * 0.01;

				$(".wfj01_019_content").attr("style","width:"+ $(".wfj01_019").width()+ "px; height:"+ parseInt($(window).height()- $(".wfj_top").height()- $(".wfj01_019_top").height()- $(".wfj01_019_top_title").height()- $(window).height()* 0.01)	+ "px;overflow:hidden;");

				if (h > $(".wfj01_019_content").height()) {
					$(".wfj01_019_hidden").attr("style","width:"+ parseInt($(".wfj01_019_content").width() + 17)+ "px; height:"+ $(".wfj01_019_content").height()+ "px;overflow:auto;");
				} else {
					$(".wfj01_019_hidden").attr("style","width:"+ parseInt($(".wfj01_019_content").width())+ "px; height:"+ $(".wfj01_019_content").height()+ "px;overflow:auto;");
					
				}
				
				if($("#jbtab").height()<$(".wfj01_019_content").height()){
					$("#scroller").attr("style","width:"+ parseInt($(".wfj01_019_content").width())+ "px; height:"+ parseInt($(window).height()-($(".wfj_top").height()+ $(".wfj01_019_top").height()+ $(".wfj01_019_top_title").height()+ $(window).height()* 0.01))+ "px");
				}
				
				/* if($("#jbtab").height()<=0){
					$("#scroller").attr("style","width:"+ parseInt($(".wfj01_019_content").width())+ "px; height:"+ parseInt($(window).height()-($(".wfj_top").height()+ $(".wfj01_019_top").height()+ $(".wfj01_019_top_title").height()+ $(window).height()* 0.01))+ "px");
				}  */
				//弹出层初始化
				$(".jqmWindow").jqm({
					modal : true,
					overlay : 20
				}).jqmAddClose('.close');

			});
			
			function details(detailid,wfjGuizeCalc){
				console.dirxml(detailid)
				open(basePath+"/ea/jinbi/ea_getdetails.jspa?detailid="+detailid+"&wfjGuizeCalc="+wfjGuizeCalc,"_self");
			}
			function imgPosition(phi,thi){
				return (phi-thi)/2;
			}
			
			function pickedFunc(){
				$("#year").text($dp.cal.getP('y'));
				$("#month").text($dp.cal.getP('M'));
				window.location.href=basePath+"/ea/jinbi/ea_gethyjifenBill.jspa?year="+$dp.cal.getP('y')+"&month="+$dp.cal.getP('M')+"&user="+user+"&khd="+khd+"&sccid="+sccid+"&flag="+flag;
			}
	</script>
</body>
</html>
