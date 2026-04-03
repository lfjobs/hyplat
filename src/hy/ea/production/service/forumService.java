package hy.ea.production.service;

import java.util.Map;

import com.tiantai.wfj.bo.TEshopCusCom;

import hy.ea.bo.finance.ProductComment;
import hy.plat.bo.PageForm;


public interface forumService {

	Object[] forumMessage(String ccompanyID);

	Object invitation(Object companyID);

	Object attention(Object companyID);

	Map<String, Object> invitationList(int pageNumber, int pageSize,String companyid, String commonEssence);

	Map<String, Object> estimate(Object companyID);
	
	void storageUrl();

	void follow(String companyid);

	PageForm reply(String string,int pageNumber,int pageSize);

	TEshopCusCom queryUser();

	Map<String, Object> myMessage(String sccid, String wsccid);

	PageForm myiNvitation(String sccid, int pageNumber,int pageSize);

	PageForm myAttention(String sccid, int pageNumber, int pageSize, String community);

	PageForm myFans(String sccid, int pageNumber, int pageSize, String community);

	Map<String, Object> shift(String sccId);

	Object replyParticulars(String pcID);

	PageForm invitationReply(String pcID, int i, int j);

	boolean delReply(String pcID);

	boolean delMyReply(String pcID);

	Map<String, Object> userReply(TEshopCusCom cuscom, ProductComment pct);

}
