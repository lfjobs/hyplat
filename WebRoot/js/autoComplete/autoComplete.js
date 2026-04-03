
//页面加载好，autoKey应该隐藏

$(document).ready(function(){

    //定义高亮变量，控制上下键的选择
    //-1代表不高亮任何行
    var highlightindex = -1;
    //提示层的子层数组
    var completesVal;
	//将要显示的提示
	var keys;
    //时间延迟对象
    var timeDelay;
    //访问路径
    var searchurl=$("#key").attr("src");
    //获得输入input的对象
    var keyInput=$("#key");
    var keyInputOffset=keyInput.offset();
    var autoDiv=$("#autoKey");
    //获得输入input的值
    var currentVal;
    //提示层的样式
    autoDiv.hide().css("border","1px solid #999999").css("position","absolute")
            .css("top",keyInputOffset.top + keyInput.height() + 8 + "px")
            .css("left",keyInputOffset.left + "px").width(keyInput.width() + 6)
            .css("color","#aaaaaa").css("z-index","10").css("background-color","#FFFFFF");


    
    //按键按下后与服务器的交互
    keyInput.keyup(function(event){
        
        //获取键盘事件对象
        var keyEvent=event || window.event;

        //判断键盘输入的值得范围
        //1.输入的是正常的字母
        //2.向上向下键
        //3。回车键
        var keyCode=keyEvent.keyCode;

        if(keyCode !=38 && keyCode !=40 && keyCode != 13){
            //获取当前文本框中的值
            if(currentVal == keyInput.val()){return;}
            currentVal=keyInput.val();
            //如果当前的文本框内容为空，就不在向服务器发送请求
            if(currentVal != ""){
                //取消上次提交
                window.clearTimeout(timeDelay);
                //延迟300ms提交,防止频繁提交
                //如果你服务端不够强悍，就调高点
                timeDelay=window.setTimeout(function(){
                  try {  
                  $.ajax({
	                        url: searchurl+"?parameter="+encodeURI(currentVal),
	                        type: "get",
	                        async: true,
	                        dataType: "json",
	                        success: function cbf(data){
	                        		 //清空autoDiv的内容并且隐藏
                    	autoDiv.html("");
                        var member = eval("(" + data + ")");
                       keys = member.Arraylist;
                       for(var i=0;i<keys.length;i++){
                         if(i==10) break;
                         $("<div id ="+keys[i][1]+">").css("font-size","13px").css("height","18px").css("padding-top","1px").html(keys[i][0]).appendTo(autoDiv);
                       }
                       
                       if(keys.length){
                        //为提示加上鼠标的选择功能
                        //鼠标over||out会触发事件，并高亮
                        //单击高亮层关闭提示层，并且把值传给文本框

                        completesVal=autoDiv.children("div");

                        //鼠标一移到autoKey上面，上下键的选择就取消
                        autoDiv.mouseover(function(){
                            //highlightindex不等于-1说明先前用上下键移动过
                            if(highlightindex != -1){
                                //定位到那个节点，取消其高亮
                                completesVal.eq(highlightindex).css("background-color","white").css("color","#aaaaaa");
                                //highlightindex = -1;
                                highlightindex = -1;
                            }
                        });

                        completesVal.each(function(){
                            var t=$(this);
                            t.css("cursor","pointer").mouseover(function(){
                                //加上mouseover事件
                                t.css("background-color","#7EC0EE").css("color","#000000");
                            }).mouseout(function(){
                                //mouseout事件
                                t.css("background-color","white").css("color","#aaaaaa");
                            }).click(function(){
                                //单击事件
                                $("#KeyID").attr("value",t.attr("id"));
                                keyInput.val(t.text());
                                autoDiv.hide();
                            });
                        });

                        highlightindex = -1;
                        autoDiv.show();
                    }else{
                        highlightindex = -1;
                        autoDiv.hide();
                    }
	                         },   error: function cbf(data){
						                           alert("数据获取失败！");
						                        }
						                    });
                 
                    }catch(e){   
				  return;
				}
                },300);
                }

            
        
        }else if(keyCode == 38 || keyCode ==40 || keyCode == 13){
            //第一步先获得提示框里所有补全信息的数组
            // var completesVal=$("#autoKey").children("div");考虑到效率，把这步放到全局
            //向下键的控制
            if(completesVal == null){return;}
            if(keyCode == 40){
                //如果现在的高亮是存在的，那么我们就要移动highlightindex
                if(highlightindex != -1){
                    //去掉当前高亮
                    completesVal.eq(highlightindex).css("background-color","white").css("color","#aaaaaa");
                }
                //移动高亮的指针,
                //注意这里的highlightindex++一定要放在if的外面，
                // 放在里面如果hightlightindex=-1，则永远也移动不了
                highlightindex++;
                //判断是否到了最后一个元素
                if(highlightindex == completesVal.length){
                    highlightindex = 0;
                }
                //高亮当前highlightindex节点
                completesVal.eq(highlightindex).css("background-color","#7EC0EE").css("color","#000000");
            }
            //向上键的控制
            if(keyCode == 38){
                if(highlightindex != -1){
                    completesVal.eq(highlightindex).css("background-color","white").css("color","#aaaaaa");
                    highlightindex--;
                }
                if(highlightindex ==-1){
                    highlightindex=completesVal.length;
                }
                 //高亮当前highlightindex节点
                completesVal.eq(highlightindex).css("background-color","#7EC0EE").css("color","#000000");
            }

            //enter键的控制
            //1.按下enter键，把提示的值传给文本框
            //2.关闭提示层
            //3.提交
            if(keyCode == 13){
                
                if(highlightindex != -1){
                    //获取当前高亮值
                    var comsVal = completesVal.eq(highlightindex).text();
                    $("#KeyID").attr("value",completesVal.eq(highlightindex).attr("id"));
                    //给文本框赋值
                    keyInput.val(comsVal);
                    //高亮索引回归到初始化
                    highlightindex = -1;
                    //隐藏提示框
                    autoDiv.hide();
                }else{
                    //文本框失去焦点，不然按回车会不停的发出请求
                    keyInput.get(0).blur();
                    autoDiv.hide();
                }
            }

        }
    });
});