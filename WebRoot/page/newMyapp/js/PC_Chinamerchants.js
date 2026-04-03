
$(document).ready(function() {
	//查询一级行业
	industry();
	//查询代理
	theagent();
	//热门
	hot();
	//列表
	ajax();
})

//查询一级行业
function industry(){
 	var url=basePath+"ea/industry/sajax_ea_getIndustry.jspa?";			
	$.ajax({
		url : url,
		type : "post",
		async : false,
		dataType : "json",
		data:{	
			"codePID":""
		},
		success : function(data) {
			var member = eval("(" + data + ")");
			$(".bid_con").find(".top").find(".right").empty();
			if(member!=null){
				var m = member.industryList;
				var s = [];
				s.push("<li class='active' date-name=''>全部</li>");
				for ( var i = 0; i < m.length; i++) {
					var b = m[i].codeDesc;
					s.push("<li date-name='"+b+"'>"+b+"</li>");
				}
				$(".bid_con").find(".top").find(".right").append(s.join(""));
			}
		},
		error: function(){
			alert("行业加载失败");
		}
	});		
}
//查询代理
function theagent(){
	var url=basePath+"/ea/newpcend/sajax_ea_ajaxTheagent.jspa?";			
	$.ajax({
		url : url,
		type : "post",
		async : false,
		dataType : "json",
		success : function(data) {
			var agent = eval("(" + data + ")");
			var list = agent.list;
			$(".bid_con").find(".bottom").find(".tit").empty();
			var s=[];
			if(list!=null){
				$(list).each(function(i, dom) {
					if(i==0){
						s.push("<li class='active' date-name='"+this[0]+"'>"+this[1]+"产品</li>");
					}
					s.push("<li date-name='"+this[0]+"'>"+this[1]+"产品</li>");
				})
				$(".bid_con").find(".bottom").find(".tit").append(s.join(""));
			}
		},
		error: function(){
			alert("代理加载失败");
		}
	});	
}
//热门
function hot(){
    url = basePath + "ea/newpcend/sajax_ea_pcProAgentList.jspa?";
    $.ajax({
        url : url,
        type: "post",
        async :true,
        dataType : "json",
        data:{
            "pageForm.pageNumber":pageNumber,
            "pageForm.pageSize":4,
            "hot":"pc"
        },
        success : function cbf(data){
            var member = eval("("+data+")");
            var pageForm = member.pageForm;
            var str = [];
            $(".bot_con").find(".right").find("ul").empty();
            if (pageForm != null && pageForm.list != null
					&& pageForm.list.length > 0) {
				$(pageForm.list).each(function(i, dom) {
					str.push("<li onclick='details(this)'>");
					str.push("<input type='hidden' class='ppID' value='"+this[0]+"'/>");
					str.push("<img src='"+basePath+this[6]+"' alt='' class='shop'>");
					str.push("<div class='text'>");
					str.push("<h4>"+this[7]+"</h4>");
					str.push("<p>&yen;"+this[4]+"</p>");
					str.push("</div>");
					str.push("</li>");
				});	
				$(".bot_con").find(".right").find("ul").append(str.join(""));
			}
        }
    })
}


//列表
function ajax(){
	var flag = $(".bid_con").find(".bottom").find(".tit").find(".active").attr("date-name");
	var tradeName = $(".bid_con").find(".top").find(".right").find(".active").attr("date-name");
    url = basePath + "ea/newpcend/sajax_ea_pcProAgentList.jspa?";
    $.ajax({
        url : url,
        type: "post",
        async :true,
        dataType : "json",
        data:{
            "pageForm.pageNumber":pageNumber,
            "pageForm.pageSize":7,
            "ppk.ppID":$.trim(flag),
            "ppk.tradeName":$.trim(tradeName)
        },
        success : function cbf(data){
            var member = eval("("+data+")");
            var pageForm = member.pageForm;
            var str = [];
            $(".bot_con").find(".left").find("ul").empty();
            if (pageForm != null && pageForm.list != null
					&& pageForm.list.length > 0) {
				$(pageForm.list).each(function(i, dom) {
					str.push("<li onclick='details(this)'>");
					str.push("<input type='hidden' class='ppID' value='"+this[0]+"'/>");
					str.push("<img src='"+basePath+this[6]+"' alt='' class='shop'>");
					str.push("<div class='text'>");
					str.push("<h1>"+this[7]+"</h1>");
					str.push("<h5>"+this[9]+"次投标</h5>");
					str.push("<p>");
					str.push("<span class='money'>&yen;"+this[4]+"</span>");
					str.push("<span class='bidding'>招商进行中</span>");
					str.push("</p>");
					str.push("</div>");
					str.push("</li>");
				});	
				$(".bot_con").find(".left").find("ul").append(str.join(""));
				$(".left_page").find("span").text(pageForm.recordCount);
				pageCount = pageForm.pageCount;
		        pageNumber = pageForm.pageNumber;
		        $(".page_my").find("ul").empty();
		        var paging = [];
		        for ( var i = 1; i <= pageCount; i++) {
		        	if(pageNumber==i){
		        		paging.push("<li onclick='choose(this)' class='active'>"+i+"</li>");
		        	}else{
		        		paging.push("<li onclick='choose(this)'>"+i+"</li>");
		        	}
				}
		        $(".page_my").find("ul").append(paging.join(""));
		        var page_nub = $(".page_my li").length;
		         $(" .page_my ul").css("width",page_nub*40+"px");
		         $(".page_my").css("width",80+page_nub*40+"px");
			}
        }
    })
}


//上一步
function lastStep(){
	if(pageNumber>1){
		pageNumber = parseInt(pageNumber)-1;
		ajax();
	}
}
//下一步
function nextStep(){
	if(pageNumber<pageCount){
		pageNumber = parseInt(pageNumber)+1;
		ajax();
	}
}
//选取
function choose(obj){
	pageNumber = $(obj).text();
	ajax();
}

//详情
function details(obj){
	var ppid = $(obj).find(".ppID").val();
	document.location.href = basePath + "ea/newpcend/ea_pcproAgentDetail.jspa?pss.ppid="+ppid;
}
