<%@page import="hy.ea.bo.Company"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>  
		<title>组织机构</title> 
		<%@ page language="java" pageEncoding="UTF-8"%>   
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
			Company c = (Company)session.getAttribute("currentcompany"); 
		%>	
		<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
	</head>
	<body style="overflow:auto">
		<div class="r_t_c_left">
			<div class="rtcl_top01">
				机构菜单
				<!-- <a style="margin-right: 2px;" id="hide" href="javascript:;" onclick="hide()">隐藏头部</a> -->
			</div>
		<div id="aadTree"
			style="width:220px;border-bottom-color: white;width: 196px;">
		</div>
	</div>
	
	<script src="<%=basePath%>/js/jquery.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js" type="text/javascript"></script>
	<script type="text/javascript">
	var basePath = "<%=basePath%>";
	var treeid = "<%=c.getCompanyID()%>";
	var result='';
	 var url1 = basePath +"/ea/office/sajax_ea_findOrgByAcc.jspa?organizationID="+encodeURI(treeid)+"&datesete=new Date()";
            $.ajax({ url: url1,type: "get",dataType: "json",
                        success: function cbf(data){
		                    var member = eval("(" + data + ")");
							var nologin = member.nologin;
							if (nologin) {
								document.location.href = basePath
										+ "page/ea/not_login.jsp";
							}
							var oList = member.organizationList;
							var data = new Array();
							data[0] = {
								id : treeid,
								pid : '-1',
								durl : 0,
								text : "<%=c.getCompanyName()%>",
								str : '00'
							};
							for (var i = 0; i < oList.length; i++) {
								data[i + 1] = {
									id : oList[i].organizationID,
									pid : oList[i].organizationPID,
									durl : oList[i].organizationUrl,
									text : oList[i].organizationName,
									str : oList[i].status
									
								};
							}
							parentMenu(treeid,data);
                        },error: function cbf(data){
                           alert("数据获取失败！");
                        }
                    });
		            var url = basePath + "/ea/office/ea_toSersonnelSystemsum.jspa?organizationID="+ treeid;
					parent.document.getElementById("daohang").src= encodeURI(url);
					window.parent.document.getElementById("mainContent").rows = "50,*";
					window.parent.document.getElementById("daohang").style.display = "block";
					window.parent.document.getElementById("rightFrame").src= "";
            
					function parentMenu(compareID,vals) {//1级
					
						result+="<ul id='browser' class='filetree'><li title='"+vals[0].text+"' id='"+vals[0].id+"' class='curor'><span class='folder' onclick='javascript:changemenu(\""+vals[0].durl+"\",\""+vals[0].id+"\")'>"+vals[0].text+"</span><ul id='child'>";
						childMenu(compareID,vals);
						result+="</ul></li></ul>";
						$(result).appendTo("#aadTree");
						$("#browser").treeview({
							 persist: "location",
						      collapsed: true,
						      unique: true
						});
						result="";
					}
					function childMenu(compareID,vals){//2级
						for (var j = 0; j < vals.length; j++) {
							if( vals[j].pid == compareID && vals[j].str == "00" ){
								    result+="<li title='"+vals[j].text+"'><span id='"+vals[j].id+"' class='folder curor' onclick='javascript:changemenu(\""+vals[j].durl+"\",\""+vals[j].id+"\")'>"+vals[j].text+"</span>";
								    result+="<ul class='filetree "+vals[j].id+"'>";
								    secMenu(vals[j].id,vals);
								    result+="</ul></li>";
							}
						}
					}
					function secMenu(compareID,vals){//3级
						for (var j = 0; j < vals.length; j++) {
							if( vals[j].pid == compareID && vals[j].str == "00" ){
								    result+="<li title='"+vals[j].text+"'><span id='"+vals[j].id+"' class='folder curor' onclick='javascript:changemenu(\""+vals[j].durl+"\",\""+vals[j].id+"\")'>"+vals[j].text+"</span></li>";
							}
						}
					}
					function changemenu(op,oid){
						if(op==0)
							op="/ea/office/ea_toSersonnelSystemsum";
						var url = basePath + op + ".jspa?organizationID="+ oid;
						parent.document.getElementById("daohang").src= encodeURI(url);
           				window.parent.document.getElementById("mainContent").rows = "50,*";
           				window.parent.document.getElementById("daohang").style.display = "block";
	       				window.parent.document.getElementById("rightFrame").src= "";
					}
	$(function(){
         $(window).resize(function(){
             setTimeout(function () {
                 $("#aadTree").height($(window).height()- 30); 
             },100);
         });
         $("#aadTree").height($(window).height()- 30); 
         
     });
</script>
    </body>
</html>
