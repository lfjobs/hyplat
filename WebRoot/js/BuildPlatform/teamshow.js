$(function(){
	loaded();
	
});//加载结束

function getHeight(){
	 t=setTimeout("getHeight()",200);
	 if($(".last").length>0){
		 if($(".last").offset().top+$(".last").height()-$("header").height()*4<$(window).height()){
			 if(pagenumber<pagecount){
				 loaded();
			 }
		}		 
	 }
}

function loaded(){
	 clearTimeout(t);
	 pagenumber++;
	 var url=basePath+"people/manage/sajax_ajaxPageForm.jspa?";
	 $.ajax({
		url:url,
		type:"get",
		data:{"pageForm.pageNumber":pagenumber},
		async:false,
		success:function (data){
			var member=eval("("+data+")");
			var pageForm=member.pageForm;
			var str=new Array();
			if(pageForm!=null&&pageForm.recordCount>0){
				pagenumber=pageForm.pageNumber;
				pagecount=pageForm.pageCount;
				var list=pageForm.list;
				$(".team li").removeClass("last");
				for(var i=0;i<list.length;i++){
					var s=list[i];
					if(i==list.length-1){
						str.push('<li class="last" onclick="detail(\''+s[1]+'\')">');
					}else{
						str.push('<li onclick="detail(\''+s[1]+'\')">');
					}
					str.push('<a href="javascript:;">');
					if(s[2]==null||s[2]==''){
						 str.push('<img src="'+basePath+'images/BuildPlatform/datu.png" alt="" class="head">');
					}else{
						 str.push('<img src="'+basePath+s[2]+'" alt="" class="head">');
					}	               
	                str.push('<div class="txt">');
	                str.push('<h4><span>'+s[0]+'</span><span class="jl">'+s[3]+'</span></h4>');
	                str.push('<p>'+s[5]+'</p>');
	                str.push('<h5></h5>');
	                str.push('</div></a></li>');  
				}				
			}
			$(".team").append(str.join(""));
			getHeight();
		}
	 });
}
function detail(staffId){
	window.location.href=basePath+"/people/manage/ea_personalDetail.jspa?staff.staffID="+staffId+"&flag="+flag+"&ccompanyId="+ccompanyId;
}
