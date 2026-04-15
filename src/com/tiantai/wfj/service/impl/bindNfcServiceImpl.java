package com.tiantai.wfj.service.impl;

import com.tiantai.wfj.bo.InspectAnnex;
import com.tiantai.wfj.bo.NfcChip;
import com.tiantai.wfj.service.bindNfcService;
import hy.ea.bo.office.NetWorkAntiVirus;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class bindNfcServiceImpl implements bindNfcService {
    @Resource
    private BaseBeanDao beandao;


    /**
     * 读取NFC芯片
     *
     * @param sn 序列号
     * @return
     */
    @Override
    public boolean readNfc(String sn) {
        NfcChip nfc = getNfcBySn(sn);
        boolean falg = false;
        return falg;
    }

    /**
     * 保存
     * @param nfc
     * @return
     */
    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public boolean saveNfc(NfcChip nfc) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(NfcChip.class);
            dc.add(Restrictions.eq("companyID", nfc.getCompanyID()));
            dc.addOrder(Order.desc("bindDate"));
            List list = beandao.getListByDC(dc);
            List jb=new ArrayList();
            List bd=new ArrayList();
            if (list.size() > 0) {
                for (int i=0;list.size()>i;i++){
                    NfcChip nfcc= (NfcChip) list.get(i);
                    if(nfcc.getBindState().equals("00")){
                        bd.add(nfcc);
                    }else {
                        jb.add(nfcc);
                    }
                }
                if(bd.size()>0&&jb.size()>0){
                    boolean b=true;
                    for (int i=0;jb.size()>i;i++){
                        b=true;
                        NfcChip jbnfc= (NfcChip) list.get(i);
                        for (int j=0;bd.size()>j;j++){
                            NfcChip bdnfc= (NfcChip) list.get(i);
                            if(jbnfc.getLn()==bdnfc.getLn()){
                                b=false;
                                break;
                            }
                        }
                        if (b){
                            nfc.setLn(jbnfc.getLn());
                            break;
                        }
                    }
                    if (!b){
                        nfc.setLn(list.size()+1);
                    }
                }else {
                    if(bd.size()<=0){
                        nfc.setLn(1);
                    }
                    if(jb.size()<=0){
                        nfc.setLn(bd.size()+1);
                    }
                }
            }else {
                nfc.setLn(1);
            }
            nfc.setBindState("00");
            nfc.setBindDate(new Date());
            beandao.save(nfc);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 解绑
     * @param id
     * @return
     */
    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public boolean updateNfc(String id) {
        try {
            String hql="from NfcChip where nfcID=?";
            NfcChip nfc = (NfcChip) beandao.getBeanByHqlAndParams(hql,new Object[]{id});
            nfc.setBindState("01");
            nfc.setOnBindDate(new Date());
            beandao.update(nfc);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据序列号查询nfc记录
     *
     * @param sn 序列号
     * @return
     */
    public NfcChip getNfcBySn(String sn) {
        String sql = "from NfcChip where sn=? and bindState=?";
        return (NfcChip) beandao.getBeanByHqlAndParams(sql, new Object[]{sn, "00"});
    }

    /**
     * 根据id查询nfc记录
     *
     * @param id
     * @return
     */
    public NfcChip getNfcById(String id) {
        return (NfcChip) beandao.getBeanByHqlAndParams("from NfcChip where nfcID=?",new Object[]{id});
    }
    /**

     * nfc列表
     * @param comid 公司id
     * @param bindState 绑定状态
     * @param staffname 责任人
     * @param ln 绑定状态
     * @param oaskName 安全类别
     * @return
     */
    public DetachedCriteria getNfcList(String comid, String bindState, String staffname, String ln, String oaskName) {
        DetachedCriteria dc = DetachedCriteria.forClass(NfcChip.class);
        dc.add(Restrictions.eq("companyID", comid));
        if (bindState != null) {
            dc.add(Restrictions.eq("bindState", bindState));
        }
        if(staffname!=null&&!staffname.equals("")){
            dc.add(Restrictions.like("staffName", staffname, MatchMode.ANYWHERE));
        }
        if(ln!=null&&!ln.equals("")){
            int num=Integer.parseInt(ln);
            dc.add(Restrictions.eq("ln", num));
        }
        if(oaskName!=null&&!oaskName.equals("")){
            dc.add(Restrictions.like("oaskName", oaskName, MatchMode.ANYWHERE));
        }
        dc.addOrder(Order.desc("bindDate"));
        return dc;
    }
}
