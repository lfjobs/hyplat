<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="<%=basePath%>js/jquery-1.6.1.min.js"></script>
<link href="<%=basePath%>css/contacts/Network/style13.css"
	rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript"
	src="<%=basePath%>js/slideUpDownRefresh_files/iscroll.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/slideUpDownRefresh_files/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/slideUpDownRefresh_files/bootstrap-theme.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/slideUpDownRefresh_files/iscroll.css" />
<title>关注粉丝人脉</title>
</head>
<body>

	<body onload="loaded();">
		<div class="wfj11_023">

			<div class="wfj11_023_avatar">
				<div class="wfj11_023_pos">
					<ul>
						<li>
						<center>
						<div class="heads">
						<c:if test='${tcc.staff.headimage==null||tcc.staff.headimage=="(null)"}'><img src="<%=basePath%>images/contacts/Network/defaultbig.png" style="border-radius:150px;" /></c:if>
						<c:if test='${tcc.staff.headimage!=null&&tcc.staff.headimage!="(null)"}'><img src="<%=basePath%>${tcc.staff.headimage}" style="border-radius:150px;"/></c:if>
						</div>
						</center>
						</li>
						<li>${tcc.staff.staffName}</li>
						<li>${tcc.cusTypeName}</li>
					</ul>
				</div>
			</div>
			<div class="wfj11_023_cos">
				<c:forEach items="${countlist}" var="obj">
					<span class="spandts"></span>
					<span>
						<ul class="changetitle" id="${obj[2]}">
							<li>${obj[0]}</li>
							<li>${obj[1]}</li>
						</ul> </span>
					
				</c:forEach>
			</div>

			<table width="100%" id="table1">
				<tr>
					<td width="20%" align="center" rowspan="2"><img width="80%"
						src="<%=basePath%>images/contacts/Network/defaultsmall.png" style="border-radius:150px;"/></td>
					<td width="40%"></td>
					<td width="40%"></td>
				</tr>
				<tr>
					<td></td>
					<td style="color:#F74C31;"></td>
				</tr>
			</table>
			<input type="hidden" id="pageNumber" value="${pageForm.pageNumber}" />
			<input type="hidden" id="pageCount" value="${pageForm.pageCount}" />
			<input type="hidden" id="choosecusType" value="${choosecusType}" />
			 <span style="display:none;" id="typers">${cusType}</span>
			<div class="wfj11_023_content">
				<div class="wfj11_023_hidden">
					<div id="wrapper">
						<div id="scroller">
							<div id="scroller-pullDown">
								<img id="wfj_sx"
									src="<%=basePath%>images/ea/finance/BenDis/fej_jiazai.gif"
									width="20px" height="20px"> <span id="down-icon"
									class="icon-double-angle-down pull-down-icon"></span> <span
									id="pullDown-msg" class="pull-down-msg sx">下拉刷新</span>
							</div>
							<div class="wfj11_023_change01 changes01" id="main">

								<c:forEach items="${pageForm.list}" var="objc">
									<table width="100%">
										<tr class="card" id="state">
											<td width="20%" align="center" rowspan="2">
											<c:if test='${objc[3]==null||objc[3]=="(null)"}'><img  width="80%" src="<%=basePath%>images/contacts/Network/defaultsmall.png" style="border-radius:150px;"/></c:if>
						                    <c:if test='${objc[3]!=null&&objc[3]!="(null)"}'><img width="80%" src="<%=basePath%>${objc[3]}" /></c:if>
											</td>
											<c:if test='${objc[2]!=null}'>
											<td width="40%">${objc[2]}</td>
											</c:if>
											<c:if test='${objc[2]==null}'>
											<td width="40%">${objc[0]}</td>
											</c:if>
											<td width="40%">
												<input type="hidden" id="acn" name="" value="${objc[0]}"/>
												<input type="hidden" id="acns" name="" value="${objc[5]}"/>
											</td>
											
											
										</tr>
										<tr>
											<td>${objc[4]}</td>
											<td style="color:#F74C31;">
											<c:if test='${objc[1]=="0.5"}'><span id="">国税</span></c:if>
											<c:if test='${objc[1]=="1"}'><span id="">地税</span></c:if>
											<c:if test='${objc[1]=="2"}'><span id="">公司商城业主会员</span></c:if>
											<c:if test='${objc[1]=="3"}'><span id="">合伙创业商城业主会员</span></c:if>
											<c:if test='${objc[1]=="4"}'><span id="">微分金商城业主会员</span></c:if> 
											<c:if test='${objc[1]=="5"}'><span id="">代理商商城业主会员</span></c:if> 
											<c:if test='${objc[1]=="6"}'><span id="">VIP客户</span></c:if> 
											<c:if test='${objc[1]=="7"}'><span id="wfj1">客户</span></c:if>
											</td>
										</tr>
									</table>
								</c:forEach>


							</div>

							<div id="scroller-pullUp">
								<img src="<%=basePath%>images/ea/finance/BenDis/fej_jiazai.gif"
									width="20px" height="20px"> <span id="up-icon"
									class="icon-double-angle-up pull-up-icon"></span> <span
									id="pullUp-msg" class="pull-up-msg sx">加载更多</span>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>


		<script type="text/javascript">
		var myScroll,
			upIcon = $("#up-icon"),
			downIcon = $("#down-icon");
			document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
		function loaded () {

			$("#wrapper").css("margin-top",$(".wfj11_023_avatar").height()+$(".wfj11_023_cos").height()+$(window).height()*0.02+"px");
            
			myScroll = new IScroll('#wrapper', { scrollX: false, scrollY: true, mouseWheel: true,probeType: 3,preventDefault:false});
	
			myScroll.on("scroll",function(){
				var y = this.y,	maxY = this.maxScrollY - y,downHasClass = downIcon.hasClass("reverse_icon"),
					upHasClass = upIcon.hasClass("reverse_icon");
				if(y >= 40){
					!downHasClass && downIcon.addClass("reverse_icon");
					return "";
				}else if(y < 40 && y > 0){
					
					downHasClass && downIcon.removeClass("reverse_icon");
					return "";
				}
				
				if(maxY >= 40){
					!upHasClass && upIcon.addClass("reverse_icon");
					return "";
				}else if(maxY < 40 && maxY >=0){
					upHasClass && upIcon.removeClass("reverse_icon");
					return "";
				}
			});
			
			myScroll.on("slideDown",function(){
				if(this.y > 40){
					console.log("down");
                                     findVipList($("#choosecusType").val())
					myScroll.refresh();
				}
			});
			
			myScroll.on("slideUp",function(){
				console.log("maxScrollY:"+this.maxScrollY);
				console.log("y:"+this.y);
		
				if(this.maxScrollY - this.y > 40){
					console.log("up");

					
					var pn=parseInt($("#pageNumber").val())+1;
					if(pn<=parseInt($("#pageCount").val())){
					
					
					  var chooseCusType = $("#choosecusType").val();

   		var url=basePath+"/ea/resourse/sajax_ea_findVipList.jspa";
		$.ajax({
			url : url,
			type : "get",
			async : false,
			dataType : "json",
			data:{
				"pageForm.pageNumber":pn,
				chooseCusType:chooseCusType
			},
			success : function (data) {
			var member = eval("(" + data + ")");
			var pageForm = member.pageForm;
			var list=pageForm.list;
			$("#pageNumber").val(pageForm.pageNumber);
		    $("#pageCount").val(pageForm.pageCount);
			
			  for ( var i = 0; i < list.length; i++) {
					$("#main").append($("#table1").clone(true).attr("id","table"+select));
					$("#table"+select).show();
					 $("#table"+select).find("tr").eq(0).attr("class", "card");
			        $("#table"+select).find("td").eq(0).append('<input type="hidden" id="acn" value="'+list[i][0]+'"/><input type="hidden" id="acns" value="'+list[i][5]+'"/>');
					if(list[i][3]!=null){
					  $("#table"+select).find("tr").eq(0).find("td").eq(0).find("img").attr("src",basePath+list[i][3]);
					}
					
					if(list[i][2]!=null&&list[i][2]!="(null)"){
					$("#table"+select).find("tr").eq(0).find("td").eq(1).text(list[i][2]);
					}
					if(list[i][2]==null){
					$("#table"+select).find("tr").eq(0).find("td").eq(1).text(list[i][0]);
					}
					
					
					$("#table"+select).find("tr").eq(1).find("td").eq(0).text(list[i][4]);
					$("#table"+select).find("tr").eq(1).find("td").eq(1).text(getVipLevel(list[i][1]));
					$("#table"+select).find("tr").eq(0).find("td").eq(0).find("input").val((list[i][2]));
					
					
					select++;
			        $(".wfj11_023_change01").find("td").css("fontSize",$(window).height()*0.02+"px");
			        $(".wfj11_023_change01").find("td").css("lineHeight",$(window).height()*0.03+"px");
			        $(".wfj11_023_change01").find("td").css("padding",$(window).height()*0.01+"px");

			        $(".wfj11_023_change01").find("table").css("borderBottom",$(window).height()*0.005+"px solid #F0F0F0");	
		
			   }


			$(".wfj11_023_hidden").attr("style","overflow:auto;");
			   
  	        $("#scroller").attr("style","height:"+($(".wfj11_023_change01").height())+"px");					

             myScroll.refresh();
				

			},error:function(data){
				alert("获取会员信息失败");
				}
			}); 
  
			
					}
				}
			});
		}
	</script>

		<script type="text/javascript">
  var basePath = "<%=basePath%>";
   var pagenumber = "${pageForm.pageNumber}";
  var select = 2;
  $(".changetitle").live("click",function(){
      var chooseCusType = $(this).attr("id");
      $("#choosecusType").val(chooseCusType);
      findVipList(chooseCusType);
  
  });
  //跳转到名片
    $(".card").live("click",function(){
     var account = $(this).find("#acn").val();
     var types = $(this).find("#acns").val();
      if(types=="1"){
        document.location.href = basePath+"ea/perinfor/ea_getPageHomeData.jspa?user="+account;
      }else{
        document.location.href = basePath+"ea/industry/ea_CompanyCard.jspa?user="+account;
      } 
    });
  
     function findVipList(chooseCusType){
     
     
 
   		var url=basePath+"/ea/resourse/sajax_ea_findVipList.jspa";
		$.ajax({
			url : url,
			type : "get",
			async : false,
			dataType : "json",
			data:{
			chooseCusType:chooseCusType
			},
			success : function (data) {
			var member = eval("(" + data + ")");
			var pageForm = member.pageForm;
			var list=pageForm.list;
			$("#pageNumber").val(pageForm.pageNumber);
		        $("#pageCount").val(pageForm.pageCount);
			
                        $("#main").html("");

			for ( var i = 0; i < list.length; i++) {
					$("#main").append($("#table1").clone(true).attr("id","table"+select));
					$("#table"+select).show();
					
			       $("#table"+select).find("tr").eq(0).attr("class", "card");
			        $("#table"+select).find("td").eq(0).append('<input type="hidden" id="acn" value="'+list[i][0]+'"/><input type="hidden" id="acns" value="'+list[i][5]+'"/>');
			        if(list[i][3]!=null&&list[i][3]!="(null)"){
					  $("#table"+select).find("tr").eq(0).find("td").eq(0).find("img").attr("src",basePath+list[i][3]);
					} 
					if(list[i][2]!=null&&list[i][2]!="(null)"){
					$("#table"+select).find("tr").eq(0).find("td").eq(1).text(list[i][2]);
					}
					if(list[i][2]==null){
					$("#table"+select).find("tr").eq(0).find("td").eq(1).text(list[i][0]);
					}
					$("#table"+select).find("tr").eq(1).find("td").eq(0).text(list[i][4]);
					$("#table"+select).find("tr").eq(1).find("td").eq(1).text(getVipLevel(list[i][1]));
					$("#table"+select).find("tr").eq(0).find("input").text(list[i][0]);
					select++;
			        $(".wfj11_023_change01").find("td").css("fontSize",$(window).height()*0.02+"px");
			        $(".wfj11_023_change01").find("td").css("lineHeight",$(window).height()*0.03+"px");
			        $(".wfj11_023_change01").find("td").css("padding",$(window).height()*0.01+"px");
			        $(".wfj11_023_change01").find("table").css("borderBottom",$(window).height()*0.005+"px solid #F0F0F0");	
					
			   
			   }
	

            var h = $("#main").find("table").height()*$("#main").find("table").length;
			if(h < $(".wfj11_023_content").height()){
                         $("#scroller").attr("style","height:"+($(".wfj11_023_content").height())+"px;transform: translate(0px, 0px)"); 
			

			}else{
				$(".wfj11_023_hidden").css("width",parseInt($(".wfj12_023_content").width()+17)+"px");
                $("#scroller").attr("style","height:"+($(".wfj11_023_change01").height())+"px"); 
			} 
			},error:function(data){
				alert("获取会员信息失败");
				}
			}); 

    }
  
  function getVipLevel(cusType){
    var cusTypeName = "";
    if(cusType=="0"){
     cusTypeName="中联园区平台";
    }else if(cusType=="0.5"){
     cusTypeName="国税";
    }else if(cusType=="1"){
     cusTypeName="地税";
    }else if(cusType=="2"){
     cusTypeName="公司商城业主会员";
    }else if(cusType=="3"){
     cusTypeName="合伙创业商城业主会员";
    }else if(cusType=="4"){
     cusTypeName="微分金商城业主会员";
    }else if(cusType=="5"){
     cusTypeName="代理商商城业主会员";
    }else if(cusType=="6"){
     cusTypeName="VIP客户";
    }else if(cusType=="7"){
    
     cusTypeName="普通客户";
    }
   　 return cusTypeName;
  }
  
  </script>

		<script type="text/javascript">
    	$(document).ready(function(e) {
         	$("body").css("height",$(window).height()) ;
			//修改字体大小
			$("#tops").find("li").attr("style","float:left;");
			$("#tops").find("li").eq(0).attr("style","width:15%;text-align:center;");
			$("#tops").find("li").eq(0).find("img").attr("style","height:"+$(window).height()*0.03+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");
			$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.025+"px;");
			$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;");
			$("#tops").find("li").eq(2).find("img").attr("style","height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");
				
			$(".wfj11_023_top").css("height",$(window).height()*0.08+"px");
			$(".wfj11_023_top").css("lineHeight",$(window).height()*0.08+"px");
			$(".heads").attr("style","height:"+$(window).height()*0.13+"px;width:"+$(window).height()*0.13+"px;");
			
			$(".wfj11_023_pos").attr("style"," line-height:"+$(window).height()*0.04+"px;padding:"+$(window).height()*0.03+"px 0;");
			$(".wfj11_023_pos").find("li").eq(1).attr("style","font-size:"+$(window).height()*0.03+"px;color:#000;");
			$(".wfj11_023_pos").find("li").eq(2).attr("style","font-size:"+$(window).height()*0.02+"px;");
			$(".wfj11_023_cos").attr("style"," margin-bottom:"+$(window).height()*0.01+"px;");
			$(".wfj11_023_cos").find("li").attr("style","font-size:"+$(window).height()*0.02+"px; line-height:"+$(window).height()*0.03+"px;");
			$(".wfj11_023_cos").find("ul").eq(0).find("li").css("color","#F74C31");
			
			$(".wfj11_023_change01").find("td").css("fontSize",$(window).height()*0.02+"px");
			$(".wfj11_023_change01").find("td").css("lineHeight",$(window).height()*0.03+"px");
			$(".wfj11_023_change01").find("td").css("padding",$(window).height()*0.01+"px");
			$(".wfj11_023_change01").find("table").css("borderBottom",$(window).height()*0.005+"px");
			$(".wfj11_bottom").attr("style","width:100%;height:"+$(window).height()*0.02+"px;");
			$("#table1").attr("style","display:none;");
			$(".changetitle").click(function(){
				$(".changetitle").find("li").attr("style","color:#666;font-size:"+$(window).height()*0.02+"px; line-height:"+$(window).height()*0.03+"px;")
				$(this).find("li").attr("style","color:#F74C31;font-size:"+$(window).height()*0.02+"px; line-height:"+$(window).height()*0.03+"px;");
				if($(this).find("li").eq(1).text()=="全部"){
					$(".wfj11_023_change01").css("display","none");
					$(".changes01").css("display","");
				}else if($(this).find("li").eq(1).text()=="微分金会员"){
					$(".wfj11_023_change01").css("display","none");
					$(".changes02").css("display","");
				}else if($(this).find("li").eq(1).text()=="代理商会员"){
					$(".wfj11_023_change01").css("display","none");
					$(".changes03").css("display","");
				}else if($(this).find("li").eq(1).text()=="客户"){
					$(".wfj11_023_change01").css("display","none");
					$(".changes04").css("display","");
				}else if($(this).find("li").eq(1).text()=="粉丝名片"){
					$(".wfj11_023_change01").css("display","none");
					$(".changes05").css("display","");
				}
				
			});
			
			
			
			$(".wfj11_023_content").attr("style","width:"+$(window).width()+"px;height:"+parseInt($(window).height()-$(".wfj11_023_top").height()-$(".wfj11_023_avatar").height()-$(".wfj11_023_cos").height())+"px; overflow:hidden;min-width:320px;");

			$(".wfj11_023_hidden").attr("style","width:"+parseInt($(".wfj11_023_content").width()+17)+"px;height:"+parseInt($(".wfj11_023_content").height())+"px; overflow:auto;");
			var wd=$(".changetitle").eq(0).width()+$(".changetitle").eq(1).width()+$(".changetitle").eq(2).width()+$(".changetitle").eq(3).width()+$(".changetitle").eq(4).width();
			$(".wfj11_023_cos").find(".spandts").attr("style","display:-moz-inline-box;display:inline-block;width:"+($(window).width()-wd)/10+"px;height:"+$(window).height()*0.03+"px; line-height:"+$(window).height()*0.03+"px;");

		    var h = $("#main").find("table").height()*$("#main").find("table").length;
			if(h < $(".wfj11_023_content").height()){
				$("#scroller").css("height",$(".wfj11_023_content").height()+"px");
			}else{
				$(".wfj11_023_hidden").css("width",parseInt($(".wfj12_023_content").width()+17)+"px");
                $("#scroller").attr("style","height:"+($(".wfj11_023_change01").height())+"px"); 
			} 
			

        });
    </script>
	</body>

</body>
</html>
