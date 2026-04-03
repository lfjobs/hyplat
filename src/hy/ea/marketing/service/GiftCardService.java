package hy.ea.marketing.service;

import com.tiantai.wfj.bo.GiftCards;
import com.tiantai.wfj.bo.TEshopCusCom;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffAddress;
import hy.plat.bo.PageForm;

import java.util.Map;


/**
 * Created by Administrator on 2018/10/13.
 */
public interface GiftCardService {
    public PageForm getSearch(int pageNumber, int pageSize, String companyID, Staff staff, GiftCards giftCards);

    public void registered(TEshopCusCom tuicus, String sccid, String phones, String intf, Staff staff, GiftCards giftCards, StaffAddress staffAddress,String sccid_app,String paymentCode);
}

