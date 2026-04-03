
$(document).ready(function () {
	var contactcID = "";// 已经添加到往来单位的ID
	var ccID = "";// ccompanyID
	var  contactName  = "" ;//单位名称
	// 为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');
	$(".JQueryreturns").click(function() {
		notoken = 0;
		$(".jqmWindow").jqmHide();
	});
	
 	var m = new Map();//房间号map数组类型k,y
 	var dm = new Map();//删除房间号map数组类型k,y
	var rid = ''; // 选中id
	
	//创建房间号
	
	$("#addNum").click(function() {
		var cnValue = $("#createNum").attr("value");
		if(cnValue == ""){
			alert("请输入房间号!");
			$("#createNum").focus();
			return
		}
		
		if(m.get(cnValue)!= null){
			alert("输入房间号以存在!");
			$("#createNum").attr("value","").focus();
			return
		}
		 
		$("#d000").after($("#d000").clone(true).attr("id","d"+ cnValue).addClass("check"));
		$("#d" + cnValue).find('input').each(function() {
			$(this).attr("id",cnValue).attr("value",cnValue);
		});
		m.put(cnValue,cnValue);	
		$("#createNum").attr("value","").focus();
		$("#d" + cnValue).show();
	});
	 
	// 删除房间号
	
	$("#delNum").click(function() {
		$("#alldiv").find("div#d"+rid).remove();
		dm.put(rid,"");
		m.remove(rid);	
	});
	
	
	//往来单位事件
	$("table#gostable tr[id]").live("click", function(event) {
		contactcID = this.id;
		ccID = this.title;
		contactName = $("tr#"+ this.id).find("#ccompanyname").text();
		$("input.ra", $(this)).attr("checked", "checked");
	});
	
	// 选择往来单位
	$("#xzwlaw").click(function() {
		$("input#ccompanyID", $("table#searchcompany")).attr("value","");
		$(".jqmWindow", $("#selectcompanyForm")).jqmShow();
		cxwldw("contactCompany.companyName=&cconnection.contactConnections=");
	});
	
	//新增往来单位
	$(".xzdw").click(function() {
		window.open(basePath
				+ "ea/contactcompany/ea_getListContactCompany.jspa?");
	});		
	// 添加所选中的往来单位
	$("#qdcompany").click(function() {
		if (contactcID != "") {
			$("table#cataffSearchTable").find("input#contactid").attr("value",ccID);	
			$("table#cataffSearchTable").find("input#hotelName").attr("value",contactName);
			$(".jqmWindow", $("#selectcompanyForm")).jqmHide();
			valHotelStsByName(ccID);
		} else {
			alert("请选择往来单位！");
		}
	});
	
	// 根据输入的往来单位名称查询
	$("input#searchcc").click(function() {
		contactcID = "";
		var typeName = $("input#ccompanyID", $("table#searchcompany")).val();
		var contactConnections = $("select#contactConnections",
				$("table#searchcompany")).val();
		quzhi=contactConnections;
		cxwldw("contactCompany.companyName=" + encodeURI(typeName)
				+ "&cconnection.contactConnections=" + contactConnections);
	});
			
	//radio选择
	$(".radio").click(function() {
		$("#alldiv").find(".radio").each(function(i, tmp){
				if(this.checked==true){
					this.checked=false;
				}
		});
		this.checked=true;
		rid = this.id;
		
	});
	//返回
	$("#reth").click(function(){
		document.location.href = basePath
				+ "ea/accommod/ea_getAllList.jspa?";
	});
	
	//保存
	$("#addh").click(function(){
		
		$(".put3").trigger("blur");
		if ($("form .error").length) {
			return;
		}
		var url =  basePath + "ea/accommod/ea_addHotel.jspa?"; 
		
		 mapList(m,dm);
		
			
		$('#accommodForm').attr("target", "hidden").attr("action",url);
		document.accommodForm.submit.click();	
		token =	2;
	});
	
	if(edit !="" && edit != "add"){
		editHotel(m);
	}
	
});

function mapList(m,dm){
	var select  = 1;
	for(var k = 0 ;k < m.elements.length;k++){
			var opt = document.createElement('input');
			opt.setAttribute( "name" , "m[" + select + "].roomNum" );
			opt.setAttribute( "value" , m.element(k).value );
			opt.setAttribute("type","hidden");
			$("#tdu").append(opt);
			var opt = document.createElement('input');
			opt.setAttribute( "name" , "m[" + select + "].roomNumID" );
			opt.setAttribute( "value" , m.element(k).key );
			opt.setAttribute("type","hidden");
			$("#tdu").append(opt);
			
			select++;
		}
	var select1 = 1;
		for(var k = 0 ;k < dm.elements.length;k++){
		//	alert(dm.element(k).key +"--"+ dm.element(k).value)
			if(dm.element(k).key.length > 20){
				var opt = document.createElement('input');
				opt.setAttribute( "name" , "dm[" + select1 + "].roomNumID" );
				opt.setAttribute( "value" , dm.element(k).key );
				opt.setAttribute("type","hidden");
				$("#tdu").append(opt);
			}
			select1++;
		}
}

function re_load() {
if (token)
		document.location.href = basePath
				+ "ea/accommod/ea_getAllList.jspa?pageNumber=" + pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");

}   
//修改加载
function editHotel(m){
	var searchurl = basePath
				+ "ea/accommod/sajax_ea_editHotel.jspa?accommod.accommodID="+edit;
		$.ajax({
			url : encodeURI(searchurl+"&date="
					+ new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				if(member.accommod != ""){
					$("#floor").attr("value",member.accommod.floor);
					$("#addstart").find("option[value='"+member.accommod.stars+"']")
					.attr("selected","selected");
					$("#addstart").find("option[value!='"+member.accommod.stars+"']")
					.attr("disabled","true");
					$("#addRoomtype").find("option[value='"+member.accommod.roomType+"']")
					.attr("selected","selected");
					$("#roomPrice").attr("value",member.accommod.roomPrice);
					$("#roomDisPrice").attr("value",member.accommod.roomDisPrice);
					$("#roomAgrPrice").attr("value",member.accommod.roomAgrPrice);
					$("#bedNum").attr("value",member.accommod.bedNum);
					$("#bedOccNum").attr("value",member.accommod.bedOccNum);
					$("#remarks").val(member.accommod.remarks);
					
					$("#accommodKey").attr("value",member.accommod.accommodKey);
					$("#accommodID").attr("value",member.accommod.accommodID);
					$("#companyID").attr("value",member.accommod.companyID);
					$("#organizationID").attr("value",member.accommod.organizationID);
					$("#createName").attr("value",member.accommod.createName);
					$("#createDate").attr("value",member.accommod.createDate);
				}
				$("#hotelName").attr("value",member.hoteln.companyName);
				$("#contactid").attr("value",member.hoteln.ccompanyID);
				$("#xzwlaw").hide();
				var rnb = member.roomNumberList;
				if(rnb !=""){
					for(var i = 0 ; i<rnb.length;i++){
						$("#d000").after($("#d000").clone(true).attr("id", "d"+rnb[i].roomNumID)
							.addClass("check"));
						$("#" + "d"+rnb[i].roomNumID).find('input').each(function() {
							$(this).attr("id",rnb[i].roomNumID).attr("value",rnb[i].roomNum);
						});
						m.put(rnb[i].roomNumID,rnb[i].roomNum);
						$("#" + "d"+rnb[i].roomNumID).show();
					}
				}
			}
		});
}

//动态验证星级
function valHotelStsByName(id){
	var searchurl = basePath
				+ "ea/accommod/sajax_ea_valHotelStsByName.jspa?accommod.hotelName="+id;
		$.ajax({
			url : encodeURI(searchurl+"&date="
					+ new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				if(member.accommod != ""){
					$("#addstart").find("option[value='"+member.accommod.stars+"']")
					.attr("selected","selected");
					$("#addstart").find("option[value!='"+member.accommod.stars+"']")
					.attr("disabled","true");
				}
				
			}
		});
}
// ajax查询往来单位列表
	function cxwldw(typeCN) {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$("#dwsy").attr("title", 0);
		$("#dwxy").attr("title", 0);
		$("#dwzy").attr("title", 0);
		var searchurl = basePath
				+ "ea/contactcompany/sajax_ea_getListContactCompanyByCompanyName.jspa?";
		$.ajax({
			url : encodeURI(searchurl + typeCN + "&date="
					+ new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var pageForm = member.pageForm;
				var connectionlist = member.connectionlist;
				if (pageForm == null) {
					alert("没有数据");
					notoken = 0;
					return;
				}
				var $se = $("select#contactConnections",
						$("table#searchcompany"));
				$se.empty();
				$select = "<option selected='selected' value = ''>--全部--</option>";
				$se.append($select);
				for (var i = 0; i < connectionlist.length; i++) {
					$op = $("<option />");
					$op.attr("value", connectionlist[i].codeValue)
							.text(connectionlist[i].codeValue);
					$se.append($op);
				}
				var dqy = pageForm.pageNumber;// 当前页
				var zys = pageForm.pageCount;// 总页数
				if (dqy > 1) {
					$("#dwsy").attr("title", dqy - 1);
				}
				if (dqy < zys) {
					$("#dwxy").attr("title", dqy + 1);
				}
				$("span#zycount").text(zys);
				var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来单位</td></tr></table>";
				tabletr += "<table width='99%' align='center' id='gostable' cellpadding='0'  cellspacing='0' class='table'>";
				tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>往来单位名称</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>往来关系</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>行业类别</th><th align='center' bgcolor='#E4F1FA'>单位电话</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>单位负责人</th><th align='center' bgcolor='#E4F1FA'>负责人电话</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>公司地址</th></tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' title="
							+ pageForm.list[i].ccompanyID + " id = "
							+ pageForm.list[i].contactConnectionID + ">";
					tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='ra' value="
							+ pageForm.list[i].contactConnectionID
							+ " name='checkradio'/></td>";
					tabletr += "<td id='ccompanyname' align='center' >"
							+ pageForm.list[i].companyName + "</td>";
					tabletr += "<td id='contactConnections' align='center'>"
							+ pageForm.list[i].contactConnections + "</td>";
					tabletr += "<td id='industryType' align='center'>"
							+ pageForm.list[i].industryType + "</td>";
					tabletr += "<td id='companyTel'  align='center'>"
							+ pageForm.list[i].companyTel + "</td>";
					tabletr += "<td id='cresponsible' align='center'>"
							+ pageForm.list[i].cresponsible + "</td>";
					tabletr += "<td id='responsibleTel' align='center'>"
							+ pageForm.list[i].responsibleTel + "</td>";
					tabletr += "<td id='companyAddr'  align='center'>"
							+ pageForm.list[i].companyAddr + "</td>";
					tabletr += "<td id='ccompanyID' style='display:none' align='center'>"
							+ pageForm.list[i].ccompanyID + "</td>";
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_02cc").html(tabletr);
				$("#body_02cc").show();
				//alert("数据加载成功");
				notoken = 0;
				window.status = "数据加载成功";
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	}

function Map()   
{   
this.elements = new Array();   
   
//获取MAP元素个数   
this.size = function(){   
 return this.elements.length;   
};
   
//判断MAP是否为空   
this.isEmpty = function() {   
 return (this.elements.length < 1);   
};
   
//删除MAP所有元素   
this.clear = function() {   
 this.elements = new Array();   
};   
   
//向MAP中增加元素（key, value)    
this.put = function(_key, _value) {   
 this.elements.push({key:_key, value:_value});   
};   
   
//删除指定KEY的元素，成功返回True，失败返回False   
this.remove = function(_key) {   
 var bln = false;   
 try  {      
  for (var i = 0; i < this.elements.length; i++) {     
   if (this.elements[i].key == _key){   
    this.elements.splice(i, 1);   
    return true;   
   }   
  }   
 }catch(e){   
  bln = false;       
 }   
 return bln;   
};   
   
//获取指定KEY的元素值VALUE，失败返回NULL   
this.get = function(_key) {   
 try{      
  for (var i = 0; i < this.elements.length; i++) {   
   if (this.elements[i].key == _key) {   
    return this.elements[i].value;   
   }   
  }   
 }catch(e) {   
  return null;      
 }   
};   
   
//获取指定索引的元素（使用element.key，element.value获取KEY和VALUE），失败返回NULL   
this.element = function(_index) {   
 if (_index < 0 || _index >= this.elements.length)   
 {   
  return null;   
 }   
 return this.elements[_index];   
};   
   
//判断MAP中是否含有指定KEY的元素   
this.containsKey = function(_key) {   
 var bln = false;   
 try {   
  for (var i = 0; i < this.elements.length; i++) {     
   if (this.elements[i].key == _key){   
    bln = true;   
   }   
  }   
 }catch(e) {   
   bln = false;       
  }   
  return bln;   
 };   
       
 //判断MAP中是否含有指定VALUE的元素   
 this.containsValue = function(_value) {   
  var bln = false;   
  try {   
   for (var i = 0; i < this.elements.length; i++) {     
    if (this.elements[i].value == _value){   
     bln = true;   
    }   
   }   
  }catch(e) {   
   bln = false;       
  }   
  return bln;   
 };   
    
 //获取MAP中所有VALUE的数组（ARRAY）   
 this.values = function() {   
  var arr = new Array();   
  for (var i = 0; i < this.elements.length; i++) {     
   arr.push(this.elements[i].value);   
  }   
  return arr;   
 };   
    
 //获取MAP中所有KEY的数组（ARRAY）   
 this.keys = function() {   
  var arr = new Array();   
  for (var i = 0; i < this.elements.length; i++) {     
   arr.push(this.elements[i].key);   
  }   
  return arr;   
 };   
}   