$(function() {

	//菜单起效
     //关闭
	 $(".closewindow").click(function(){
		 if(confirm("确定要关闭添加窗口？")){
		 window.close();
		 }
		 
	 });
	


	
	//记录产品配件选中的id
	$("#goodtable").find("tr").click(function(){
		$("#line").text($(this).attr("id"));
	});
	//记录产品选中的id
	$("input.productSelect").click(function(){
		$("#line").text("");
		placeholder="";
		$("#body_02").html("");
		$("#selectType").val("goods");
		$(".jqmWindow", $("#goodsForm")).jqmShow();
	});

	
	// 物品表格单击样式问题
	$("#goodtable :input").click(function() {
		$("#goodtable td").find("div").each(function() {
			$(this).css("border", "none");
			$(this).find(".caz").hide();

		});
		$(this).parent("div").css("border", "1px solid #000000");
		$(this).parent("div").find(".fou").focus();
		$(this).parent("div").find(".caz").show();

	});
	$("#goodtable td").click(function() {
		$("#goodtable td").each(function() {
			$(this).find("div").css("border", "none");
			$(this).find(".caz").hide();

		});

		$(this).find("div").css("border", "1px solid #000000");
		$(this).find(".fou").focus();
		$(this).find(".caz").show();

	});
	






/*******************************************物品以及子物品列表*****************************************************/	
	
$("tr.checkgoods",$("table#goodtable")).each(function(){
	var $trThis=$(this);
	var quantity=parseInt($.trim($trThis.find("input#quantity").val())==""?"0":$.trim($trThis.find("input#quantity").val()));
	var time=parseFloat($.trim($trThis.find("input#time").val())==""?"0":$.trim($trThis.find("input#time").val()));
	var baifenbi=(time/quantity)*100;
	if(!baifenbi){
		$trThis.find("input#time").val(0);
		$trThis.find("input#remaining").val(quantity);
	}
	$(this).find("#p").progressbar({value: baifenbi.toFixed(0)});
});

//切换当前物品下的子物品的可见状态
$("img.showOrHideChildren").click(function(){
	   	
	   var clicktr=$(this).parent().parent().attr("id");
	   if ($("tr#"+ clicktr).find("#goodsNum").val() == "") {
			alert("父级物品为空!");
       } else {
    	   if($(this).hasClass("show")){
    		   //隐藏
    		   $(this).removeClass("show");
    		   showOrHideChildrenHide($(this).parent().parent().find("input#ppID").attr("value"));
    	   }else{
    		   //显示
    		   $(this).addClass("show");
    		   getProductPackagingListByPID($(this).parent().parent().find("input#ppID").attr("value"));
        	   showOrHideChildrenShow($(this).parent().parent().find("input#ppID").attr("value"));
    	   }
       }
});
});
//隐藏物品以及所有子物品
function showOrHideChildrenHide(id){
	var $child=$("input#parentId[value="+id+"]").parent().parent();
	for ( var int = 0; int < $child.size(); int++) {
		$child.eq(int).hide();
		showOrHideChildrenHide($child.eq(int).find("input#ppID").attr("value"));
	}
}
//显示物品以及所有子物品
function showOrHideChildrenShow(id){
	var $child=$("input#parentId[value="+id+"]").parent().parent();
	for ( var int = 0; int < $child.size(); int++) {
		$child.eq(int).show();
	}
}
//ajax查询子物品并展示
function getProductPackagingListByPID(id){
	var $Select=$("input#ppID[value="+id+"]").parent().parent();
	var placeholder="&nbsp;&nbsp;&nbsp;&nbsp;";
	var url = basePath + "/ea/trainingSchedule/sajax_ea_getProductPackagingListByPID.jspa?";
	$.ajax({
		url:url,
		type:"get",
		async : false,
		data : {
			"productDesign.ppID" : id,
			"cstaff.staffID" : studentid,
			"date":new Date().toLocaleString()
		},
		dataType : "json",
		success : function(data) {
			var member = eval("(" + data + ")");
			var nologin = member.nologin;
			var productPackagingList = member.productPackagingList;
			if (nologin) {
				document.location.href = basePath + "page/ea/not_login.jsp";
			}
			if(productPackagingList!=null||productPackagingList.length!=0){
				for ( var int = 0; int < productPackagingList.length; int++) {
					if($("input#ppID[value="+productPackagingList[int][6] +"]").size()==0){
						select++;
						$Select.after($("tr#kelong").clone(true).attr("id","kelong"+ select).addClass("checkgoods"));		
						//添加占位符placeholder  达到层级效果 
			        	$("tr#kelong"+ select).find("span.hierarchy").html($Select.find("span.hierarchy").html()+placeholder);

			        	$("input#goodsID",$("tr#kelong"+ select)).attr("value",productPackagingList[int][0]);
				        $("input#goodsNum",$("tr#kelong"+ select)).attr("value",productPackagingList[int][1]);
			        	$("input#goodsName",$("tr#kelong"+ select)).attr("value",productPackagingList[int][2]);
						
						
						
						
						$("input#quantity",$("tr#kelong"+ select)).attr("value",productPackagingList[int][3]);
						$("input#time",$("tr#kelong"+ select)).attr("value",productPackagingList[int][4]==null?"0":productPackagingList[int][4]);
						$("input#remaining",$("tr#kelong"+ select)).attr("value",productPackagingList[int][5]==null?productPackagingList[int][3]:productPackagingList[int][5]);
						
						
						$("input#ppID",$("tr#kelong"+ select)).attr("value",productPackagingList[int][6]);
						$("input#parentId",$("tr#kelong"+ select)).attr("value",productPackagingList[int][7]);
						
						var quantity=parseInt(productPackagingList[int][3]==null?"0":productPackagingList[int][3]);
						var time=parseFloat(productPackagingList[int][4]==null?"0":productPackagingList[int][4]);
						var baifenbi=(time/quantity)*100;
						$("tr#kelong"+ select).find("#p").progressbar({value: baifenbi.toFixed(0)});
						
			        	$("tr#kelong"+ select).show();
					}
				}
			}
		},
		error : function(data) {
			alert("获取数据失败");
		}
		
	});
}
