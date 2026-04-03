package hy.ea.finance.service.turnovermanage.impl;

import hy.ea.bo.finance.BenDis.DtOrderBillAdd;
import hy.ea.bo.finance.BenDis.PhoneBill;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.finance.action.response.OrderDetailResponse;
import hy.ea.finance.service.turnovermanage.MakeOrderService;
import hy.ea.util.StringUtil;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class MakeOrderServiceImpl implements MakeOrderService {

    @Resource
    private BaseBeanService baseBeanService;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public PageForm getList(String pageNum, String pageSize, String fkStatus, String companyid) {
        if (StringUtil.isNotEmpty(companyid)) {
            if (StringUtil.isNotEmpty(fkStatus)) {
                String hql = "from PhoneBill WHERE companyid  = ? and fkStatus = ?";
                Object[] obj = {companyid, fkStatus};
                PageForm pageForm = baseBeanService.getPageForm(Integer.parseInt(pageNum),
                        Integer.parseInt(pageSize), hql, obj);
                return pageForm;
            }
            String hql = "from PhoneBill WHERE companyid  = ?";
            Object[] obj = {companyid};
            PageForm pageForm = baseBeanService.getPageForm(Integer.parseInt(pageNum),
                    Integer.parseInt(pageSize), hql, obj);
            return pageForm;
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OrderDetailResponse getOrderDetail(String cashierBillsID) {
        if (StringUtil.isNotEmpty(cashierBillsID)) {
            String hql1 = "from PhoneBill WHERE cashierBillsID = ?";
            Object[] obj1 = {cashierBillsID};
            PhoneBill phoneBill = (PhoneBill) baseBeanService.getBeanByHqlAndParams(hql1, obj1);
            String hql2 = "from GoodsBills WHERE cashierBillsID = ?";
            Object[] obj2 = {cashierBillsID};
            GoodsBills goodsBills = (GoodsBills) baseBeanService.getBeanByHqlAndParams(hql2, obj2);
            String hql3 = "from DtOrderBillAdd WHERE oaBillId = ?";
            Object[] obj3 = {cashierBillsID};
            DtOrderBillAdd dtOrderBillAdd = (DtOrderBillAdd) baseBeanService.getBeanByHqlAndParams(hql3, obj3);
            OrderDetailResponse response = new OrderDetailResponse();
            response.setGoodsName(goodsBills.getGoodsName());
            response.setJournalNum(phoneBill.getJournalNum());
            response.setStandard(goodsBills.getStandard());
            response.setGoodsVariableID(goodsBills.getGoodsVariableID());
            response.setPrice(goodsBills.getPrice());
            response.setPriceSub(phoneBill.getPriceSub());
            response.setCashierDate(phoneBill.getCashierdate());
            response.setFkDate(dtOrderBillAdd.getFkDate());
            response.setShDate(dtOrderBillAdd.getShDate());
            response.setPayType(goodsBills.getPayType());
            response.setAttachment(goodsBills.getAttachmentPath());
            response.setReceivename(phoneBill.getReceivename());
            response.setReceivetel(phoneBill.getReceivetel());
            response.setReceiveaddress(phoneBill.getReceiveaddress());
            if (phoneBill.getFkStatus() == "00" || phoneBill.getFkStatus() == "02" ||
                    phoneBill.getFkStatus() == "03" || phoneBill.getFkStatus() == "04") {
                response.setSkStatus("已收款");
            } else {
                response.setSkStatus("未收款");
            }
            return response;
        }
        return null;
    }
}
