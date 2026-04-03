$(function(){
	if(length=="0"||length==null){
		window.location.href=basePath+"ea/design/ea_selCommissionList.jspa";
	}
	var br=true;
	var query = "佣金分配比例设计";
	var designText="";
	var designId="";
	
	$('.fexlist').flexigrid({
		height : 145,
		width : 'auto',
		title : query,
		minwidth : 30,
		checkbox : true,
		minheight : 80,
		buttons : [{
			name : '添加',
			bclass : 'add',
			onpress : action
				// 当点击调用方法
			}, {
				separator : true
			},{
			name : '保存',
			bclass : 'edit',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
			},{
			name : '修改',
			bclass : 'edit',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
			},{
			name : '删除',
			bclass : 'delete',
			onpress : action
				// 当点击调用方法
			}]
	});
});
function action(com, grid) {
	switch (com) {

		case '添加' :
//			var aut=authority();
//			if(aut=="not"){
//				alert("抱歉，您没有此权限");
//				break;
//			}
			if(select>0){
				alert("请先保存");return;
			}
			$("#thsprs").attr("style","");
			$(".tdsprs").attr("style","");
			$("#trprs").attr("style","").attr("id","addtr");
			select++;
			status="add";
			break;
		case '保存' :
			if(status=="add"){
				$(".error").each(function(){
					$(this).attr("style",$(this).attr("style")+"background-color: red;");
				});
				var b=true;
				$(".putbs").each(function(index){
					if(!$(this).val()){
						$(this).attr("style","width:100%;border:0px;background-color: red;");
						b=false;
					}
				});
				if(!b||$(".error").val())
					break;
				var number=0;
				$("#addtr").find(".putds").each(function(index){
					if($(this).val())
						number+=parseInt($(this).val().split("%")[0]);
				});
				if(number!=100){
					alert("同方案下各个机构的比例相加必须为100才可保存");
					break;
				}
				var url=basePath+"ea/design/ea_addCommissionData.jspa?data=";
				$("#addtr").find(".putds").each(function(index){
						if(index==0)
							url+=($(this).val()==""?"null":$(this).val());
						else
							url+=(","+($(this).val()==""?"null":$(this).val()));
							
				});
				url+=("&bsName="+$(".putbs").eq(0).val()+"&bsId="+$(".putbs").eq(0).attr("id"));
				if(!$(".deltes").attr("class"))
					url+="&behavior=true";
				else
					url+="&behavior=false";
				$("#form").attr("target", "hidden").attr("action",encodeURI(url));
				window.form.submit.click();			
				window.form.reset();
				token = 2;
			}else if(status=="edt"){
				$(".error").each(function(){
					$(this).attr("style",$(this).attr("style")+"background-color: red;");
				});
				if($(".error").val())
					break;
				var number=0;
				$("#endtr").find(".putds").each(function(index){
					if($(this).val())
						number+=parseInt($(this).val().split("%")[0]);
					
				});
				if(number!=100){
					alert("同方案下各个机构的比例相加必须为100才可保存");
					break;
				}
				var number="";
				var meid="";
				var url=basePath+"ea/design/ea_modifyCommissionData.jspa?data=";
				$(".modify").each(function(index){
						if(index==0){
							url+=($(this).val()==""?"null":$(this).val());
							number+=$(this).attr("id");
							meid+=$("#tthps").find("#th"+$(this).attr("id")).attr("name");
						}else{
							url+=(","+($(this).val()==""?"null":$(this).val()));
							number+=","+$(this).attr("id");
							meid+=","+$("#tthps").find("#th"+$(this).attr("id")).attr("name");
						}			
				});
				url+=("&number="+number+"&meId="+meid+"&bsId="+$("#endtr").find(".bsName").attr("id")+"&bsName="+$("#endtr").find(".bsName").val());	
				$("#form").attr("target", "hidden").attr("action",encodeURI(url));
				window.form.submit.click();			
				window.form.reset();
				token = 2;
			}else if(status=="del"){
				$(".error").each(function(){
					$(this).attr("style",$(this).attr("style")+"background-color: red;");
				});
				if($(".error").val())
					break;
				var bbrs=true;
				$(".dele").each(function(index){
					var number=0;
					$(this).find(".putds").each(function(index){
						if($(this).val())
							number+=parseInt($(this).val().split("%")[0]);
						
					});
					if(number!=100){
						$(this).find("input").eq(1).attr("style","width:100%;border:0px;background-color: red;");
						bbrs=false;
					}else{
						$(this).find("input").eq(1).attr("style","width:100%;border:0px;background-color: #E0FFFF;");
					}
				});
				if(!bbrs){
					alert("某方案中数值总和非100，请修改");break;
				}
				var obj=new Array();
				$(".dele").each(function(index){
					var int=index;
					obj[int]=new Array();
					obj[int][0]=$(this).find(".bsName").attr("id");
					$(this).find(".modify").each(function(index){
						obj[int][index+1]=$(this).val()+"-"+$("#tthps").find("#th"+$(this).attr("id")).attr("name");
					});
				});
				var js=arrayToJson(obj);
				var url=basePath+"ea/design/ea_deleteCommissionData.jspa?sta=column&json="+js+"&meid="+$(".thHide").attr("name")+"&bsid="+designId+"&bsName="+designText;
				$("#form").attr("target", "hidden").attr("action",encodeURI(url));
				window.form.submit.click();			
				window.form.reset();
				token = 2;
			}
			
			break;
		case '修改' :
//			var aut=authority();
//			if(aut=="not"){
//				alert("抱歉，您没有此权限");
//				break;
//			}
			if(selber==0){
				alert("请选择");
				break;
			}
			if(select>0){
				alert("请先保存");return;
			}
			var btrs=false;
			$(".rad").each(function(){
				if($(this).attr("checked")){
					$(this).parents("tr").attr("id","endtr");
					$(this).parents("tr").find("td").each(function(index){
						if(index!=0){
							$(this).find("span").attr("style","display: none;");
							$(this).find("input").removeClass("model1");
						}
					});
					btrs=true;
				}
			});
			if(btrs)
			select++;
			status="edt";
			break;
		case '删除' :
//			var aut=authority();
//			if(aut=="not"){
//				alert("抱歉，您没有此权限");
//				break;
//			}
			if(select>0){
				alert("请先保存");return;
			}
			if(selber==0){
				alert("请选择");
				break;
			}
			var le=$(".rad");
			if(!$(".rad").eq(le.length-1).attr("checked")){
				alert("删除时只能删除最后一条数据");
				break;
			}
			if($(".rad").eq(le.length-1).parents("tr").find("td").eq(1).find("span").text()=="代理商会员"){
				alert("默认数据只可修改，不可删除");break;
			}
			if(!confirm("是否确认删除"))
			break;
			br=confirm("是否删除各个方案中的该会员的信息");
			if(br){
				$(".rad").eq(le.length-1).parents("tr").attr("style","display: none;");
				designText=$(".rad").eq(le.length-1).parents("tr").find("td").eq(1).find("span").text();
				designId=$(".rad").eq(le.length-1).parents("tr").find("td").eq(1).find("input").attr("id");
				var leToo=0;
				$("th").each(function(index){
					if($(this).text()==designText){
						leToo=index;
						$(this).attr("style","display: none;").addClass("thHide");
					}		
				});
				$(".ttrs").each(function(index){
					if(index!=le.length){
						$(this).find("td").eq(leToo).attr("style","display: none;").find(".putds").removeClass("putds");
						var text=$(this).find("td").eq(leToo).find("span").text()
						if($(this).find("td").eq(leToo).find("span").text()!=""&&index!=0){
							$(this).addClass("dele");
							$(this).find("td").each(function(index){
								if(index!=leToo&&index!=0){
									$(this).find("span").attr("style","display: none;");
									$(this).find("input").removeClass("model1");
								}
							});
						}
					}
				});
				if($(".dele").attr("class")){
					alert("您删除的会员在其他方案中含有数据，请修改所对应方案内的数值，使其总和为100%");
				}else{
					var url=basePath+"ea/design/ea_deleteCommissionData.jspa?sta=column&meid="+$(".thHide").attr("name")+"&bsid="+designId+"&bsName="+designText;
					$("#form").attr("target", "hidden").attr("action",encodeURI(url));
					window.form.submit.click();			
					window.form.reset();
					token = 2;
				}
			}else{
				designText=$(".rad").eq(le.length-1).parents("tr").find("td").eq(1).find("span").text();
				designId=$(".rad").eq(le.length-1).parents("tr").find("td").eq(1).find("input").attr("id");
				var url=basePath+"ea/design/ea_deleteCommissionData.jspa?sta=capable&bsid="+designId+"&bsName="+designText;
				$("#form").attr("target", "hidden").attr("action",encodeURI(url));
				window.form.submit.click();			
				window.form.reset();
				token = 2;
			}
			status="del";
			break;
	}
}
$(function(){
	// 为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	
	$(".putds").live("change",function(){
		var zz=/^(100|[1-9]?\d)%$/;
		if(!zz.test($(this).val())&&$(this).val()!="")
			$(this).attr("style",$(this).attr("style")+"background-color: red;").addClass("error");
		else
			$(this).attr("style",$(this).attr("style")+"background-color: #E0FFFF;").removeClass("error").addClass("modify");
	});
	$("td").live("click",function(){
		$(this).parent().find("input").attr("checked","checked");
		selber=1;
	});
	
	$(".putbs").live("click",function(){
		GetCodeList("1","10");
	});

	$(".operation").click(function(){
		if($(this).attr("id")=="determine"){
			var brss=false;
			var brps=false;
			$(".trps").each(function(){
				if($(this).find("input").attr("checked")){
					var bbrs=false;
					var str=$(this).find(".spls").text();
					$(".datapps").each(function(){
						if($(this).text()==str)
							bbrs=true;
					});
					if(!bbrs){
						$(".levelpps").each(function(){
							if($(this).text()==str)
								$(".deltes").remove();
						});
						$(".putbs").val($(this).find(".spls").text()).attr("id",$(this).find("input").attr("id"));
						brss=true;	
					}else
						brps=true;
				}
			});
			if(brss)
				$("#divrs").jqmHide();
			else{
				if(brps)
					alert("请勿重复添加");
				else
					alert("请选择");
			}
			if(!$(".deltes").attr("class"))
				alert("会员已存在，请填写方案的信息");
		}else{
			$(".trps").remove();
			$("#divrs").jqmHide();
		}
	});
	
	$(".page").click(function(){
		if($(this).attr("id")=="home")
			GetCodeList("1","10");
		else if($(this).attr("id")=="previous")
			GetCodeList(number-1,"10");
		else if($(this).attr("id")=="next")
			GetCodeList(number+1,"10");
		else
			GetCodeList(saech/10!=0?saech%10+1:saech%10,"10");
	});
});
function arrayToJson(o) {     
    var r = [];     
    if (typeof o == "string") return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";     
    if (typeof o == "object") {      
    if (!o.sort) {     
    for (var i in o)     
    r.push(i + ":" + arrayToJson(o[i]));     
    if (!!document.all && !/^\n?function\s*toString\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) {     
    r.push("toString:" + o.toString.toString());     
    }     
    r = "{" + r.join() + "}";     
    } else {     
    for (var i = 0; i < o.length; i++) {     
    r.push(arrayToJson(o[i]));     
    }     
    r = "[" + r.join() + "]";     
    }     
    return r;     
    }     
    return o.toString();     
}  
function GetCodeList(pages,rows){
	if(pages<1){
		pages=1;number=1;
	}else if(pages>(saech/10!=0?saech%10+1:saech%10)){
		pages=(saech/10!=0?saech%10+1:saech%10);number=(saech/10!=0?saech%10+1:saech%10);
	}
	$(".trps").remove();
	$.ajax({
		url:basePath+"ea/design/sajax_ea_ajaxGetCodeList.jspa?pageForm.pageNumber="+pages+"&pageNumber="+rows,
		type:"post",
		async : true,
		success:function(data){
			var member = eval("(" + data + ")");
			var pageForm=member.pageForm;
			var codeList=pageForm.list;
			number=pageForm.pageNumber;
			saech=member.saech;
			for(var i=0;i<codeList.length;i++){
				var tr=$("#trus").clone(true).attr("style","").addClass("trps").attr("id",codeList[i].ppID);
				tr.find("td").eq(0).html("<input type='radio' name='raps' id="+codeList[i].ppID+" >");
				tr.find("td").eq(1).html("<span class='spps'>"+codeList[i].model+"</span>");
				tr.find("td").eq(2).html("<span class='spls'>"+codeList[i].goodsName+"</span>");
				$("#trus").before(tr);
			}
			$("#divrs").jqmShow();
		},
		error:function(data){
			alert("数据获取失败！");
		}
	});
}
//查询是否有添加的权限
function authority(){
	var s="";
	$.ajax({
		url:basePath+"ea/category/sajax_ea_authority.jspa",
		type:"post",
        async: false,
		success:function(data){
			var member = eval("("+data+")");
			s=member.authority;
		},
		error:function(data){
			alert(">>>>");
		}		
	});
	return s;
}
function re_load(){
	document.location.href=basePath+"/ea/design/ea_selCommissionList.jspa?";
}