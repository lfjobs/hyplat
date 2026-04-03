package hy.ea.human.service;

import com.tiantai.wfj.bo.TEshopCusCom;
import hy.ea.bo.CAccount;
import hy.plat.bo.BaseBean;

import java.util.List;

public interface OutworkingMobileService {
	void working(String local, String img, String type, CAccount account, TEshopCusCom shopCusCom, String id);
	List<BaseBean> workingList(String staffID, String companyID);
	void queryWorkingListDetail(String travelID);


}
