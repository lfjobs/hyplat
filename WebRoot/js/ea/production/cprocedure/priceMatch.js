$(function(){

//高亮
var boxStandard = $(".boxStandard").eq(0).text();
var tprice = $(".tprice").eq(0).text();
	$(".light").click(function() {

		$(".boxStandard").each(function(index,obj){
			if(index!=0){
              if(boxStandard!=$(obj).text()){
				$(".boxStandard").addClass("highlight");
				$(".light").hide();
			    $(".cancelight").show();
		
              }
			
			}
		});
		
		$(".tprice").each(function(index,obj){
			if(index!=0){
              if(tprice!=$(obj).text()){
				$(".tprice").addClass("highlight");
				$(".light").hide();
			    $(".cancelight").show();
		
              }
			
			}
		});

	});	//取消高亮
		
	$(".cancelight").click(function() {

			$(".boxStandard").removeClass("highlight");
			$(".tprice").removeClass("highlight");
			$(".light").show();
			$(".cancelight").hide();
		});
	
	//确定中标
	
	$(".selectbid").click(function(){
		var bfids = "";
		$('input[name=JQuerypersonvalue]:checked').each(function(){
		 bfids+=$(this).val()+",";
		});
		if(bfids==""){
			alert("请选择");
			return;
		}
		$("#bfids").val(bfids);
		$("#mainForm").attr("target", "hidden").attr("action",
				basePath + "ea/purchasebids/ea_confirmBids.jspa");
		

		document.mainForm.submit.click();
		token = 2;
	});
});

function re_load() {
	window.opener.location.href = window.opener.location.href;
	window.close();

}

