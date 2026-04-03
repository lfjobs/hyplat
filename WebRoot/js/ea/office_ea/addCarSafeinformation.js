$(document).ready(function(){
	//var count = 0;  //统计评价的分数
	//动态显示
	$("select.istrues").change(function(){
		var i = 0;
		var j=  0;
		$(this).parent().parent().find("select.istrues").each(function(){
				if($(this).val()=="00"){
					$("tr#"+carID).find(".jiangli").attr("value",i+=0.5);
					$("#countrewards").attr("value","10");
				}
				if($(this).val()=="01"){
					$("tr#"+carID).find(".koufen").attr("value",j+=0.25);
				}
				if($(this).val()=="02"){
					$("tr#"+carID).find(".totlecount").attr("value",j+=0.5);
				}
			});
			$("tr#"+carID).find(".koufen").val(j);
			$("tr#"+carID).find(".jiangli").val(i);
			$("tr#"+carID).find(".totlecount").val(i-j);
	});

	$(".operate").click(function(){
			$("#"+carID).hide();
	});
	//获取选中行
	$(".table tr[id]").click(function() {
		carID = this.id;
	$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
	});
	
	
	
	//计算本次奖励总分和总计扣分 和总得分
$("select.istrues").change(function(){
		var h = 0;
		var k = 0;
		$(this).parent().parent().find("select.istrues").each(function(){
				if($(this).val()=="00"){
					$("tr#"+carID).find(".jiangli").attr("value",h+=0.5);
					$("#countrewards").attr("value","10");
				}
				if($(this).val()=="01"){
					$("tr#"+carID).find(".koufen").attr("value",k+=0.25);
				}
				if($(this).val()=="02"){
					$("tr#"+carID).find(".totlecount").attr("value",k+=0.5);
				}
			});
			$("tr#"+carID).find(".koufen").val(k);
			$("tr#"+carID).find(".jiangli").val(h);
			$("tr#"+carID).find(".totlecount").val(h-k);
	});

	
	
	
	
	
	
	
	//返回
	$(".JQueryreturn").click(function() {
		if (confirm("您确定要离开本页吗？尚未保存")){
			window.opener=null;
			window.open('','_self');
			window.close();
			}
			else{}
	});
	//提交信息
	$(".JQuerySubmit").click(function() {
		if($("#adddate").val()== ''){
			alert("请输入检查时间！");
			return;
		}
		$("#cstaffForm").attr("action", basePath + "ea/carassectinformation/ea_SaveSafetyInformation.jspa");
		document.cstaffForm.submit.click();
	});

});

function re_load() {
		document.location.href = basePath
				+ "ea/carassectinformation/ea_getSafetyHealthList.jspa";
	
}