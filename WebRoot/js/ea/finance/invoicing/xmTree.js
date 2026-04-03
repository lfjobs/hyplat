$(function(){
	$("#xmul").find("li").live("click",function(){

		
			if($(this).attr("class").indexOf("collapsable")!=-1){
				
					if($(this).find("tr").length==0){
					var $newtable = $("#Layer1").clone();
					$newtable.find("table").attr("id",$(this).attr("id")+"tbl").addClass("datatbl");
					$(this).find("span").after($newtable.html());
					}else{
						$("#"+$(this).attr("id")+"tbl").show();
					}
				
			}else{
				$("#"+$(this).attr("id")+"tbl").hide();
			}
		
	});
	
	
});
function TreeSelector(item, data, rootId) {
	this._data = data;
	this._item = item;
	this._rootId = rootId;
	
}

TreeSelector.prototype.createTree = function() {
	var len = this._data.length;
	for (var i = 0; i < len; i++) {
		if (this._data[i].pid == this._rootId) {
			//父对象
			this._item.append("<li id='"+this._data[i].id+"'><span class='folder'>"+this._data[i].text+"</span><ul id='"+this._data[i].id+"ul'></ul></li>");

			for (var j = 0; j < len; j++) {
				this.createSubOption(len, this._data[i], this._data[j]);
			}
//			$("#xmul").treeview({
//	            collapsed: true,
//	            unique: true
//	        });

		}
	}
};

TreeSelector.prototype.createSubOption = function(len, current, next) {
	
	if (next.pid == current.id) {
		
        $("#"+current.id+"ul").append("<li id='"+next.id+"'><span class='folder' id='"+this._type+"'>"+next.text+"</span><ul id='"+next.id+"ul'></ul></li>");	

        
		for (var j = 0; j < len; j++) {
			this.createSubOption(len, next, this._data[j]);
		}
	}
};




