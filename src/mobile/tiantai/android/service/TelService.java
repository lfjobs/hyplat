package mobile.tiantai.android.service;

import java.util.List;

import hy.plat.bo.PageForm;

public interface TelService {
	
	/**
	 * @param @account手机号
	 * @param tel 选择的联系人
	 */
	void updateTel(String sccId,String account,String tel);

	/**	
	 * @param staffID 
	 * @param wfj 0为非微分金用户 1为微分金用户
	 * @param pageNumber 分页页数
	 * @param parameter 查询内容
	 * @return PageForm
	 */
	PageForm getWfjList(String staffID, String wfj,String pageNumber,String parameter);


	List<Object> getPeopleInformation(String slist);

	/*
	 *发送红包
	 * goldNum金币数量，sender发送人，recipient接收人，liuYan留言
	 */
	 int  redPacket (String sender,String recipient,String goldNum,String liuYan);
}
