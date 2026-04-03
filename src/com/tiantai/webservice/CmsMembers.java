package com.tiantai.webservice;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * 此WebService是用于对接汽车联盟网会员管理功能
 * @author zhb
 *
 */
@WebService(targetNamespace = "http://service.impf2010.com", name = "CmsMembers")
public interface CmsMembers {
	
	/**
	 * 将会员基本信息导入五层五清
	 * @param members 以xml格式包装会员信息
	 * @return 每个会员导入成功与否的xml格式字符串
	 */
	@WebResult(name = "return", targetNamespace = "http://service.impf2010.com")
	public String importMembers(@WebParam(name = "members", targetNamespace = "http://service.impf2010.com") String members);

}
