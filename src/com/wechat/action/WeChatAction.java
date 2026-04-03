package com.wechat.action;





import hy.base.action.BaseAction;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.wechat.bo.AccessToken;
import com.wechat.bo.ArticleMain;
import com.wechat.bo.Articles;
import com.wechat.bo.MediaID;
import com.wechat.bo.MpnewsMessage;
import com.tiantai.wfj.bo.WeChatToken;
import com.wechat.utils.ConstantURL;
import com.wechat.utils.WeixinUtil;



@Controller
@Scope("prototype")
public class WeChatAction extends BaseAction<Object>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private BaseBeanService baseBeanService;
	

	public void doAccessToken(){
		
    String hql = "from WeChatToken";
     List<BaseBean>  list =  baseBeanService.getListBeanByHqlAndParams(hql, null);
     List<BaseBean> beanlist = new ArrayList<BaseBean>();
     for (int i = 0; i < list.size(); i++) {
    	 WeChatToken wct = (WeChatToken)list.get(0);
    	 wct.setAccessTime(new Date());
    	 AccessToken at = WeixinUtil.getAccessToken(wct.getAppID(), wct.getAppSecret());
    	 wct.setAccessToken(at.getToken());
		 String jsapi_ticket = WeixinUtil.getJSticket(wct.getAccessToken());
		 wct.setJsapi_ticket(jsapi_ticket);

		 beanlist.add(wct);
	 }
     baseBeanService.saveBeansListAndexecuteHqlsByParams(beanlist, null, null);
		
  }
	
	
	
	
	
	/**
	 * 
	 * 创建图文消息
	 * @return
	 */
	public String createMaterialMessage(){
		
		ArticleMain articleMain = new ArticleMain();
		Articles articles = new Articles();
		
        articles.setThumb_media_id("CIfkProlY5c_51atUtEnBjfLeBBXLEdHfMI4bu6pGIMZ0a_kJfc1nXs3YaXYiFjZ");
		articles.setAuthor("mz");
		articles.setTitle("Happy Day title");
		articles.setContent_source_url("www.qq.com");
		articles.setContent("Happy Day content");
		articles.setDigest("描述");
		articles.setShow_cover_pic("1");
		articleMain.setArticles(new Articles[]{articles});

		
	    // 将对象转换成json字符串  
	    String jsonmater = JSONObject.fromObject(articleMain).toString();  
		JSONObject jsonObject = WeixinUtil.httpRequest(ConstantURL.material_upload_url, "POST",jsonmater);
		
		
		
		return "";
		
	}
	
	/**
	 * 
	 * 发送到手机端预览
	 * @return
	 */
	public String MessagePreview(){
		MpnewsMessage mpMessage = new MpnewsMessage();
		mpMessage.setTowxname("mzdudumz");
		mpMessage.setMsgtype("mpnews");
		MediaID mediaID = new MediaID();
		mediaID.setMedia_id("");
		mpMessage.setMpnews(mediaID);
		
	    // 将对象转换成json字符串  
	    String jsonmater = JSONObject.fromObject(mpMessage).toString();  
		JSONObject jsonObject = WeixinUtil.httpRequest(ConstantURL.material_preview_url, "POST",jsonmater);
		
		return  "";
		
		
	}
	
	
	
	
}
