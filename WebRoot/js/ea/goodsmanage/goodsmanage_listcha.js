$(function()
		{
		});
//选项卡淡入隐藏效果
function zhuti(name)
{
	  $jianjie= $(".jianjie");
	  $jiegou=$(".jiegou");
	  $gongneng=$(".gongneng");
	  $yongtu=$(".yongtu");
	  $changjia=$(".changjia");
	
	  if(name=="yongtu")
		{
			$jianjie.hide(); 
			$jiegou.hide();
		    $gongneng.hide();
		    $yongtu.fadeIn();
		    $changjia.hide();
		
		}
//	构成
	if(name=="jiegou")
	{
	    $jiegou.fadeIn(); 
	    $jianjie.hide();
	    $gongneng.hide();
	    $yongtu.hide();
	    $changjia.hide();
	
	}
	//简介
		if(name=="jianjie")
		{
		    $jiegou.hide(); 
		    $jianjie.fadeIn();
		    $gongneng.hide();
		    $yongtu.hide();
		    $changjia.hide();
		
		} //厂家
			if(name=="changjia")
			{
			    $jiegou.hide(); 
			    $jianjie.hide();
			    $gongneng.hide();
			    $yongtu.hide();
			    $changjia.fadeIn();
			}
			 //功能
			if(name=="gongneng")
			{
			    $jiegou.hide(); 
			    $jianjie.hide();
			    $gongneng.fadeIn();
			    $yongtu.hide();
			    $changjia.hide();
			}
			
};