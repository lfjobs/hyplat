package hy.ea.human.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import hy.ea.bo.human.salaryNew.SalaryItem;

import hy.ea.bo.human.salaryNew.SalaryUnits;

import hy.ea.human.service.SalaryLevelService;

import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.*;

@Service
@Transactional
public class SalaryLevelServiceImpl implements SalaryLevelService {

    @Resource
    private BaseBeanDao beandao;
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;

    public PageForm getSalaryLevelList(int pageNumber, int pageSize, String companyID){




        return null;
    }
    /**
     *
     *
     * 获取所有薪资项目点
     * @param suID
     * @return
     */
    public List<Object> getSalaryItemsList(String suID){
        String sql = "select stID,itemName from dtSalaryItem where suID = ? and status = '00' order by to_number(seq) asc";
        List<Object> list = beandao.getListObjectBySqlAndParams(sql,new Object[]{suID});

        return list;
    }

    /**
     *
     * 获取薪资组成单元
     * @param parentslID
     * @return
     */
    public List<Object> getSalaryUnitsList(String parentslID,String companyID){
        List<Object> list = new ArrayList<Object>();
        if(parentslID==null||parentslID.equals("")){
            String sql = "select suID,unitName from dtSalaryUnits where parentslID is null and companyID = ? and status = '00' order by to_number(seq) asc";
             list = beandao.getListObjectBySqlAndParams(sql, new Object[]{companyID});

        }else {
            String sql = "select suID,unitName from dtSalaryUnits where parentslID = ? and companyID = ? and status = '00' order by to_number(seq) asc";
             list = beandao.getListObjectBySqlAndParams(sql, new Object[]{parentslID,companyID});
        }
        return list;
    }


    /**
     *
     * 复制预设数据到公司
     * @param companyID
     * @param staffID
     * @return
     */
    public  String copySalaryStruct(String companyID,String staffID){
        if(companyID==null||companyID.equals("")){
            return "fail";
        }

        List<BaseBean> listy = beandao.getListBeanByHqlAndParams("from SalaryUnits where parentslID is null and status = '00' and companyID = ? and isPreset = '00'",new Object[]{companyID});

        if(listy.size()>0){

            return "repeat";
        }

        List<BaseBean> beans = new ArrayList<BaseBean>();
        String hql1 = "from SalaryUnits where parentslID is null and status = '00' and companyID is null and isPreset = '00'";
        String hql2 = "from SalaryUnits where parentslID = ? and status = '00' and companyID is null and isPreset = '00'";
        String hql3 = "from SalaryItem where suID = ? and status = '00' and companyID is null and isPreset = '00'";

        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql1,null);
        for (int i = 0;i<list.size();i++){

            SalaryUnits salary = (SalaryUnits)list.get(i);

            SalaryUnits units1 = new SalaryUnits();
            units1.setSuID(serverService.getServerID("suid"));
            units1.setStaffID(staffID);
            units1.setCompanyID(companyID);
            units1.setIsPreset(salary.getIsPreset());
            units1.setUnitName(salary.getUnitName());
            units1.setStatus(salary.getStatus());
            units1.setCreateDate(new Date());
            units1.setSeq(salary.getSeq());
            beans.add(units1);

            List<BaseBean> list3 = baseBeanService.getListBeanByHqlAndParams(hql3,new Object[]{salary.getSuID()});
            for (int k = 0;k<list3.size();k++){

                SalaryItem salaryItem1 = (SalaryItem)list3.get(k);
                SalaryItem item1 = new SalaryItem();
                item1.setSuID(units1.getSuID());
                item1.setStID(serverService.getServerID("stid"));

                item1.setStaffID(staffID);
                item1.setCompanyID(companyID);
                item1.setIsPreset(salaryItem1.getIsPreset());
                item1.setItemName(salaryItem1.getItemName());
                item1.setStatus(salaryItem1.getStatus());
                item1.setCreateDate(new Date());
                item1.setSeq(salaryItem1.getSeq());
                item1.setStatisRules(salaryItem1.getStatisRules());
                beans.add(item1);
            }


            List<BaseBean> list2 = baseBeanService.getListBeanByHqlAndParams(hql2,new Object[]{salary.getSuID()});
            for (int j = 0;j<list2.size();j++){

                SalaryUnits salary2 = (SalaryUnits)list2.get(j);
                SalaryUnits units2 = new SalaryUnits();
                units2.setParentslID(units1.getSuID());
                units2.setSuID(serverService.getServerID("suid"));
                units2.setStaffID(staffID);
                units2.setCompanyID(companyID);
                units2.setIsPreset(salary2.getIsPreset());
                units2.setUnitName(salary2.getUnitName());
                units2.setStatus(salary2.getStatus());
                units2.setCreateDate(new Date());
                units2.setSeq(salary2.getSeq());
                beans.add(units2);

                List<BaseBean> list4 = baseBeanService.getListBeanByHqlAndParams(hql3,new Object[]{salary2.getSuID()});
                for (int l = 0;l<list4.size();l++){

                    SalaryItem salaryItem2 = (SalaryItem)list4.get(l);
                    SalaryItem item2 = new SalaryItem();
                    item2.setSuID(units2.getSuID());
                    item2.setStID(serverService.getServerID("stid"));

                    item2.setStaffID(staffID);
                    item2.setCompanyID(companyID);
                    item2.setIsPreset(salaryItem2.getIsPreset());
                    item2.setItemName(salaryItem2.getItemName());
                    item2.setStatus(salaryItem2.getStatus());
                    item2.setCreateDate(new Date());
                    item2.setSeq(salaryItem2.getSeq());
                    item2.setStatisRules(salaryItem2.getStatisRules());
                    beans.add(item2);
                }

            }

        }

         beandao.saveBeansListAndexecuteHqlsByParams(beans,null,null);
        return "suc";
    }


    /**
     *
     * 改变排序
     * @param id1
     * @param id2
     * @return
     */
    public String changeSort(String id1,String id2,String staffID,String companyID){
        String result = "suc";
        try {
            List<BaseBean> beans = new ArrayList<BaseBean>();
            if (id1.indexOf("suid") != -1) {

                String hql = "from SalaryUnits where suID = ? and companyID = ?";
                SalaryUnits s1 = (SalaryUnits) beandao.getBeanByHqlAndParams(hql, new Object[]{id1, companyID});
                SalaryUnits s2 = (SalaryUnits) beandao.getBeanByHqlAndParams(hql, new Object[]{id2, companyID});

                String seq1 = s1.getSeq();
                String seq2 = s2.getSeq();
                s1.setSeq(seq2);
                s2.setSeq(seq1);
                s1.setSortDate(new Date());
                s1.setSorterID(staffID);
                s2.setSortDate(new Date());
                s2.setSorterID(staffID);
                beans.add(s1);
                beans.add(s2);


            } else {

                String hql = "from SalaryItem where stID = ? and companyID = ?";
                SalaryItem s1 = (SalaryItem) beandao.getBeanByHqlAndParams(hql, new Object[]{id1, companyID});
                SalaryItem s2 = (SalaryItem) beandao.getBeanByHqlAndParams(hql, new Object[]{id2, companyID});

                String seq1 = s1.getSeq();
                String seq2 = s2.getSeq();
                s1.setSeq(seq2);
                s2.setSeq(seq1);
                s1.setSortDate(new Date());
                s1.setSorterID(staffID);
                s2.setSortDate(new Date());
                s2.setSorterID(staffID);
                beans.add(s1);
                beans.add(s2);
            }
            beandao.saveBeansListAndexecuteHqlsByParams(beans, null, null);
        }catch (Exception e){
            logger.error("操作异常", e);
            result = "fail";
        }
        return result;
    }

    /**
     *
     * 添加薪资单元
     * @param salaryUnits
     * @return
     */
    public String addSalaryUnits(SalaryUnits salaryUnits,String staffID,String companyID){
        salaryUnits.setSuID(serverService.getServerID("suid"));
        salaryUnits.setStaffID(staffID);
        salaryUnits.setCreateDate(new Date());
        salaryUnits.setStatus("00");
        salaryUnits.setIsPreset("01");
        salaryUnits.setCompanyID(companyID);
        int  a = 0;
        try {
            if (salaryUnits.getParentslID() != null && !salaryUnits.getParentslID().equals("")) {
                String sql = "select max(to_number(seq)) from dtSalaryUnits where parentslID = ? and status='00' and companyID = ?";
                a = beandao.getConutByBySqlAndParams(sql, new Object[]{salaryUnits.getParentslID(), companyID});
            } else {

                String sql = "select max(to_number(seq)) from dtSalaryUnits where parentslID is null and status='00' and companyID = ?";
                a = beandao.getConutByBySqlAndParams(sql, new Object[]{companyID});

            }
        }catch (Exception e){
//           logger.error("操作异常", e);
        }
        salaryUnits.setSeq((a+1)+"");

        beandao.save(salaryUnits);
        return null;
    }

    /**
     *
     * 添加薪资项目节点

     * @param
     * @return
     */
    public String addSalaryItems(SalaryItem salaryItem,String staffID,String companyID){

        salaryItem.setStID(serverService.getServerID("stid"));
        salaryItem.setStaffID(staffID);
        salaryItem.setCreateDate(new Date());
        salaryItem.setStatus("00");
        salaryItem.setIsPreset("01");
        salaryItem.setCompanyID(companyID);
        int  a = 0;
        try {
            String sql = "select max(to_number(seq)) from dtSalaryUnits where suID = ? and status='00' and companyID = ?";
            a = beandao.getConutByBySqlAndParams(sql, new Object[]{salaryItem.getSuID(), companyID});
        }catch (Exception e){
            logger.error("操作异常", e);
        }
        salaryItem.setSeq((a+1)+"");

        beandao.save(salaryItem);
        return null;

    }

    /**
     *
     * 修改薪资结构名称
     * @param id
     * @param name
     * @param staffID
     * @param companyID
     * @return
     */
    public String updateSalaryStrutsName(String id,String name,String staffID,String companyID){
        if(id.indexOf("suid")!=-1) {
            String hql = "from SalaryUnits where suID = ? and companyID = ?";
            SalaryUnits salaryUnits = (SalaryUnits)beandao.getBeanByHqlAndParams(hql,new Object[]{id,companyID});
            salaryUnits.setUnitName(name);
            salaryUnits.setUpdateDate(new Date());
            salaryUnits.setUpdateID(staffID);
            beandao.update(salaryUnits);
        }else{
            String hql = "from SalaryItem where stID = ? and companyID = ?";
            SalaryItem salaryItem = (SalaryItem)beandao.getBeanByHqlAndParams(hql,new Object[]{id,companyID});
            salaryItem.setItemName(name);
            salaryItem.setUpdateDate(new Date());
            salaryItem.setUpdateID(staffID);
            beandao.update(salaryItem);

        }
             return null;
    }

    /**
     *
     * 删除
     * @param id
     * @param staffID
     * @param companyID
     * @return
     */
    public String deleteSalaryStruts(String id,String staffID,String companyID){
        String result = "suc";
        try {
            List<BaseBean> beans = new ArrayList<BaseBean>();
            if (id.indexOf("suid") != -1) {
                String hql = "from SalaryUnits where suID = ? and companyID = ?";
                SalaryUnits salaryUnits = (SalaryUnits) beandao.getBeanByHqlAndParams(hql, new Object[]{id, companyID});
                salaryUnits.setStatus("99");//本身
                salaryUnits.setDelerID(staffID);
                salaryUnits.setDelDate(new Date());
                beans.add(salaryUnits);
                //下级单元或者直接下级item或者下级单元的下级item
                String hql2 = "from SalaryUnits where parentslID = ? and companyID = ?";
                List<BaseBean> unitslist = beandao.getListBeanByHqlAndParams(hql2, new Object[]{id, companyID});

                String hql3 = "from SalaryItem where suID = ? and companyID = ?";
                if (unitslist.size() > 0) {
                    //有下级单元
                    for (int i = 0; i < unitslist.size(); i++) {
                        SalaryUnits s = (SalaryUnits) unitslist.get(i);
                        s.setStatus("99");
                        s.setDelerID(staffID);
                        s.setDelDate(new Date());
                        beans.add(s);
                        List<BaseBean> itemlist = beandao.getListBeanByHqlAndParams(hql3, new Object[]{s.getSuID(), companyID});
                        if (itemlist.size() > 0) {
                            for (int k = 0; k < itemlist.size(); k++) {
                                SalaryItem si = (SalaryItem) itemlist.get(i);
                                si.setStatus("99");
                                si.setDelerID(staffID);
                                si.setDelDate(new Date());
                                beans.add(si);
                            }
                        }
                    }

                } else {
                    //无二级单元查询直接Item

                    List<BaseBean> itemlist = beandao.getListBeanByHqlAndParams(hql3, new Object[]{id, companyID});
                    if (itemlist.size() > 0) {
                        for (int i = 0; i < itemlist.size(); i++) {
                            SalaryItem s = (SalaryItem) itemlist.get(i);
                            s.setStatus("99");
                            s.setDelerID(staffID);
                            s.setDelDate(new Date());
                            beans.add(s);
                        }
                    }
                }

            } else {
                String hql = "from SalaryItem where stID = ? and companyID = ?";
                SalaryItem salaryItem = (SalaryItem) beandao.getBeanByHqlAndParams(hql, new Object[]{id, companyID});
                salaryItem.setStatus("99");
                beans.add(salaryItem);
            }
            beandao.saveBeansListAndexecuteHqlsByParams(beans, null, null);
        }catch (Exception e){
            logger.error("操作异常", e);
            result = "fail";
        }
        return result;
    }

}