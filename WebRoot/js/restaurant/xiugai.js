var number=2;
$(function(){
	$(".hp").css("height",$(".hp").height()-$(".huaixin_footer").height());
	$(".huaixin_footer_hide").css("height",$(".huaixin_footer").height()+"px");
    $(".Img_gou").css({"height":$(".wfj_same_bot_lis").height()+"px"});
   $(".wfj_same_bot .wfj_same_bot_lis img.gou").css({"position":"absolute","top":"50%","margin-top":-$(".wfj_same .wfj_same_bot .wfj_same_bot_lis img.gou").height()/2+"px"})
   $(".can_Nav_main div").click(function(){
        $(".can_Nav_main div").removeClass("on");
        $(this).addClass("on");
    });
var Scroll1;



setTimeout(function(){
	 Scroll1 = new IScroll('#scroll1',{
		disableMouse: true,
	    disablePointer: true,
	    mouseWheel: true,
	    probeType: 3,
	    click:true
	    });
	 

	 Scroll1.on('scroll', function(){
		 Scroll1.refresh();
						var nowPos = $('#pp')[0].offsetHeight - $('#pp').parent()[0].offsetHeight - parseInt(this.y)*(-1);
						if ( nowPos <= 0  ) {
					//服务员的
					  var waiterNumber = parseInt($("#waiterNumber").val());
					  var waiterCounts = parseInt($("#waiterCounts").val());
					  //包间的
					  var roomNumber = parseInt($("#roomNumber").val());
					  var roomCount = parseInt($("#roomCount").val());
					  var curent = $(".can_Nav_main div.current").text();
					var ajaxvalue = curent=="选择包间"?"privateRoom":"waiter";
					
					
					if((curent=="选择服务员"&&waiterNumber<waiterCounts)||(curent!="选择服务员"&&roomNumber<roomCount)){
						var url=basePath+"ea/restaurant/sajax_ea_getChoice.jspa?pageNumber="+number;	
						load(url);
					}
                       
				}
	 });
},0);
//切换的点击事件
    var tabs = function(tab, con){
        tab.click(function(){
            var indx = tab.index(this);
            tab.removeClass('current');
            $(this).addClass('current');
            con.hide();
            con.eq(indx).show();
            Scroll1.refresh();
        })
    };
    tabs($("#tabs01 div"), $('#container01 .con'));
   

});



function load(type){
		var curent = $(".can_Nav_main div.current").text();
		var ajaxvalue = curent=="选择包间"?"privateRoom":"waiter";
		
		$.ajax({
			url : encodeURI(type),
			type : "get",
			async : false,
			dataType : "json",
			data:{
				ajax:ajaxvalue,
				companyId:companyId
			},
			success : function(data) {
				var member = eval("(" + data + ")");
				var page=member.pageForm;//包间
				//var pages=member.pageFormto;//.大厅
				var com="";
				var coa="";
				var room = page.list;//包
				if(room==null){
					number++;
				    return;
				}
			    
				if($(".can_Nav_main div.current").text()=="选择服务员"){
					for(var i = 0; i < room.length; i++){
						var pp=room[i];
						com +="<div class='wfj_same_bot_lis'><div><div class='imgs2 Img'>";
						if(pp[1]!=null){
						com +="<img src='"+basePath+pp[1]+"' />";
						}
						if(pp[1]==null){
						com +="<img src='"+basePath+"/images/contacts/restaurant/defaltwaiter.png'/></div>";
						}
						com +="<div class='Text'><h3>"+pp[0]+pp[2]+"</h3>";
						com +="<div class='Img_start'>";
						com +="<span><img src='"+basePath+"/images/contacts/start_03.png'/></span>";
						com +="<span><img src='"+basePath+"/images/contacts/start_03.png'/></span>";
						com +="<span><img src='"+basePath+"/images/contacts/start_03.png'/></span>";
						com +="<span><img src='"+basePath+"/images/contacts/start_03.png'/></span>";
						com +="<span><img src='"+basePath+"/images/contacts/start_03.png'/></span>";
						com +="</div>";
						com +="</div><div class='Img_gou'>";
						com +="<img class='gou'src='"+basePath+"/images/contacts/restaurant/chan_03_03.png' ";
						com +="style='position:absolute;top:50%;margin-top:-"+$('.gou').height()/2+"px;'/>";
						com +="<img class='gou on'src='"+basePath+"/images/contacts/restaurant/chan_07.png'";
						com +="style='position:absolute;top:50%;margin-top:-"+$('.gou').height()/2+"px;'/>";
						com +="</div></div></div>";
					}
					$("#fuwuyuan").append(com);
					//$(".huaixin_footer").height())
					
					$(".Img_gou").css({"height":$(".waiter").find(".wfj_same_bot_lis").height()+"px"});
					$(".wfj_same_bot .wfj_same_bot_lis div.Img").css("height",$(".waiter").find(".Img_gou").height()+ "px")
					$("#waiterNumber").val(number);
					number++;
					
				}else{
					
					for(var i = 0; i < room.length; i++){
						var pp=room[i];
						coa +="<div class='wfj_same_bot_lis'><div class='wfj_same_bot_lis_main'><div class='imgs Img'>";
						coa +="<img src='"+basePath+pp[1]+"' /></div>";
						coa +="<div class='Text' onclick='dianji(this)'><h3>";
						coa +="	"+pp[0]+"<input type='hidden' id='baojian'value='"+pp[0]+"></h3>";
						coa +="<input type='hidden' id='goodsid' value='"+pp[3]+"'>";
						coa +="<div class='Img_start'>";
						coa +="<span><img src='"+basePath+"/images/contacts/start_03.png'/></span>";
						coa +="<span><img src='"+basePath+"/images/contacts/start_03.png'/></span>";
						coa +="<span><img src='"+basePath+"/images/contacts/start_03.png'/></span>";
						coa +="<span><img src='"+basePath+"/images/contacts/start_03.png'/></span>";
						coa +="<span><img src='"+basePath+"/images/contacts/start_03.png'/></span></div>";
						coa +="<div class='Text_main'>";
						coa +="<nobr>"+pp[2]+"</nobr>";
						coa +="</div></div>";
						coa +="<div class='Img_gou'><img class='gou' src='"+basePath+"/images/contacts/restaurant/chan_03_03.png' style='position:absolute;top:50%;margin-top:-"+$('.gou').height()/2+"px;'/>";
						coa +="<img class='gou on'src='"+basePath+"/images/contacts/restaurant/chan_07.png' style='position:absolute;top:50%;margin-top:-"+$('.gou').height()/2+"px;'/>";
						
						coa +="</div></div></div>";
						
						
					}
					$("#VIP").append(coa);
					//$(".hp").css({$(".hp").height()-$(".huaixin_footer").height()});
					
					$(".Img_gou").css({"height":$(".jiucan").find(".wfj_same_bot_lis").height()+"px"})
					$(".wfj_same_bot .wfj_same_bot_lis div.Img").css("height",$(".jiucan").find(".Img_gou").height()+ "px")
					$("#roomNumber").val(number);
					number++;
					
				}
				//$this.attr("name",parseInt(pagenumber+1));
			   
			},
			error : function(data) {
				alert("获取b");
			}
		});
		}

//确定点击
function clickJump(obj) {
	var morre = $("#morre").val();
	var privateRoom = $(obj).parents(".xuan").find(".canzhuo")
			.html();
	var waiter = $(obj).parents(".xuan").find(".waiters").html();
	if (privateRoom != "" && waiter != "") {
		var url = basePath
				+ "ea/restaurant/ea_queryLoginName.jspa?privateRoom="
				+ privateRoom + "&waiter=" + waiter + "&companyId="
				+ companyId + "&ccompanyId=" + ccompanyId
				+ "&selecton=1";
		document.location.href = url;
	}
}
//单个点击
function dianji(obj) {
	var goodsid = $(obj).parents(".wfj_same_bot_lis_main").find(
			".Text").find("#goodsid").val();
	var bao = $(obj).parents(".wfj_same_bot_lis_main")
			.find(".Text").find("#baojian").val();
	var url = basePath + "ea/restaurant/ea_getPuy.jspa?goodsid="
			+ goodsid + "&baojian=" + bao + "&companyId="
			+ companyId + "&ccompanyId=" + ccompanyId;
	document.location.href = url;
}

