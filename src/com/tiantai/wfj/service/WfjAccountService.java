package com.tiantai.wfj.service;

import com.wechat.bo.WxUserInfo;

import javax.servlet.http.HttpServletRequest;

public interface WfjAccountService {

    /**
     *
	 * 微信公众号授权登陆创建账号或者查询账号
	 * @return

	**/
	public String createAccount(WxUserInfo wxUserInfo, String  tjsccid,String realPath);

	 /*
			* 判断是否是微信公众账号访问如果是引导跳转到微信授权页面
     * @param request
     * @param url
     * @return
			 */
	public String  isWxLogin(HttpServletRequest request, String url);

	/**
	 * 根据产品ID
	 * @param pid
	 * @return
	 */
	public String  getTjBycanzuo(String pid);


	public  void fileUpload (final String fileUrl,final String path);



	public String validateAccount(String openid);


	public String bindTel(String openid,String tel,String nickName);

}
