package hy.ea.finance.service.impl.InvestDeviceBind;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.finance.InvestDeviceBind.DeviceBind;
import hy.ea.bo.finance.InvestDeviceBind.DeviceBindStaff;
import hy.ea.finance.service.InvestDeviceBind.DeviceBindService;
import hy.ea.production.service.WarehouseService;
import hy.ea.service.CLogBookService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

@Service
@Transactional
public class DeviceBindServiceImpl implements DeviceBindService {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private BaseBeanDao beandao;
    @Resource
    private CLogBookService logBookService;
    @Resource
    private WarehouseService warehouseService;

    /**
     * 车辆信息公司汇总列表
     */
    public PageForm CarInforList(CAccount account, String carNum, String deviceStatu, int pageNumber) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select g.twocode,g.barcode,g.goodscoding,g.goodsname,co.companyname,cor.organizationname,");
        sql.append(" c.staffname,c.carid,c.staffid,c.state1,c.carnum,g.goodsid");
        sql.append(" from dt_car_carinformation c,dtcorganization cor,dtcompany co,dtgoodsmanage g");
        sql.append(" where c.organizationid = cor.organizationid and c.companyid=co.companyid");
        sql.append(" and c.state1 is not null and c.companyid = ? and c.state3 = ?");
        sql.append("  and g.goodsid = c.goodsid");
        params.add(account.getCompanyID());
        params.add("01");
        if (carNum != null && !carNum.equals("")) {
            sql.append(" and c.carNum like ?");
            params.add("%" + carNum + "%");
        }
        if (deviceStatu != null && !deviceStatu.equals("")) {
            sql.append(" and c.state1=?");
            params.add(deviceStatu);
        }
        PageForm pageForm = baseBeanService.getPageFormBySQL(
                (pageNumber == 0 ? 1 : pageNumber), 10, sql.toString(),
                "select count(1)" + sql.toString().substring(sql.indexOf("from")), params.toArray());
        return pageForm;
    }

    /**
     * 查询投资责任人
     */
    public PageForm selInvestRespose(int pageNumber, CAccount account, String tzName, String tzAccount, String tzCustype) {
        PageForm pageForm = null;
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select e.account,p.goodsname,h.staffcode,h.staffname,h.staffid,e.sccid,e.custype");
        sql.append(" from t_Eshop_Cuscom e,dt_hr_staff h,dt_productpackaging p");
        sql.append(" where e.staffid = h.staffid and p.model = e.custype and p.type = ? and e.custype between ? and ?");
        params.add("会员类型级别");
        params.add("2");
        params.add("7");
        if (tzName != null && !"".equals(tzName)) {
            sql.append(" and h.staffname=?");
            params.add(tzName);
        }
        if (tzAccount != null && !tzAccount.equals("")) {
            sql.append(" and e.account like ?");
            params.add("%" + tzAccount + "%");
        }
        if (tzCustype != null && !tzCustype.equals("")) {
            sql.append(" and e.custype=?");
            params.add(tzCustype);
        }
        pageForm = baseBeanService.getPageFormBySQL(
                (pageNumber == 0 ? 1 : pageNumber), 10, sql.toString(),
                "select count(1)" + sql.toString().substring(sql.indexOf("from")), params.toArray());
        return pageForm;
    }

    /**
     * 查询公司投资的投资责任人
     */
    public List<BaseBean> selComInvestment(CAccount account) {
        String sql = "select e.account,e.custype,h.staffcode,h.staffname,h.staffid,e.sccid from t_eshop_cuscom e,dt_hr_staff h where e.staffid = h.staffid and e.companyid = ?";
        @SuppressWarnings("unchecked")
        List<BaseBean> investList = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{account.getCompanyID()});
        return investList;
    }

    /**
     * 给DeviceBind表添加一条记录
     */
    public void addDeviceBind(String carid, String goodsid, String investSccid,
                              String investStaffid, String carNum) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        String organizationID = (String) session.get("organizationID");
        DeviceBind db = new DeviceBind();
        db.setDbId(serverService.getServerID("DeviceBind"));
        db.setDbCarId(carid);
        db.setDbGoodsId(goodsid);
        db.setDbSccId(investSccid);
        db.setDbStaffId(investStaffid);
        db.setDbcarNumber(carNum);
        db.setDbStatus("1");
        db.setDbtzType("01"); //01教练车
        db.setDbDate(new Date());
        List<BaseBean> beans = new ArrayList<BaseBean>();
        beans.add(db);
        String parameter = "添加投资设备和投资人以及关联业务员,车牌号：" + carNum;
        CLogBook logBook = logBookService.saveCLogBook(organizationID,
                parameter, account);
        beans.add(logBook);
        baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    }


    /**
     * 查询所有设备
     */
    public PageForm selDeviceBind(CAccount account, String carNum,
                                  String deviceStatu, String tzName, String tzAccount, PageForm pageForm) {
        StringBuffer sql = new StringBuffer();
        sql.append("select g.twocode,g.barcode,b.dbtztype,g.goodscoding,g.goodsname,co.companyname,cor.organizationname,");
        sql.append(" h.staffname as tzname,c.staffname as glname,e.account,b.dbid,c.state1,c.carNum,b.dbdate");
        sql.append(" from dt_car_carinformation c,dtcorganization cor,dtcompany co,");
        sql.append(" dtgoodsmanage g,dt_devicebind b,dt_hr_staff h,t_Eshop_Cuscom e");
        sql.append(" where c.organizationid = cor.organizationid and c.companyid = co.companyid");
        sql.append(" and b.dbcarid = c.carid and b.dbstaffid = h.staffid and b.dbsccid = e.sccid");
        sql.append(" and g.goodsid = b.dbgoodsid and c.companyid = ? and b.dbStatus = ?");
        List<Object> params = new ArrayList<Object>();
        params.add(account.getCompanyID());
        params.add("1");
        if (carNum != null && !carNum.equals("")) {
            sql.append(" and c.carNum like ?");
            params.add("%" + carNum + "%");
        }
        if (deviceStatu != null && !deviceStatu.equals("")) {
            sql.append(" and c.state1=?");
            params.add(deviceStatu);
        }
        if (tzName != null && !"".equals(tzName)) {
            sql.append(" and h.staffname=?");
            params.add(tzName);
        }
        if (tzAccount != null && !tzAccount.equals("")) {
            sql.append(" and e.account like ?");
            params.add("%" + tzAccount + "%");
        }
        sql.append(" order by b.dbdate desc");
        pageForm = baseBeanService.getPageFormBySQL(
                (null != pageForm ? pageForm.getPageNumber() : 1), 10, sql.toString(),
                "select count(1)" + sql.substring(sql.toString().indexOf("from")), params.toArray());
        return pageForm;
    }

    /**
     * 添加关联业务员
     */
    public void addGlStaff(String dbId, String sccidStaffids) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        String organizationID = (String) session.get("organizationID");
        String[] sccidStaffid = sccidStaffids.substring(0, sccidStaffids.length() - 1).split("@");
        List<BaseBean> beans = new ArrayList<BaseBean>();
        for (int i = 0; i < sccidStaffid.length; i++) {
            String[] ss = sccidStaffid[i].split(",");
            DeviceBindStaff dbs = new DeviceBindStaff();
            dbs.setDbsId(serverService.getServerID("DeviceBindStaff"));
            dbs.setDbsDbId(dbId);
            dbs.setDbsDate(new Date());
            dbs.setDbsStaffId(ss[0]);
            dbs.setDbsSccId(ss[1]);
            dbs.setDbsStatus("1");
            beans.add(dbs);
            TEshopCusCom tesc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams
                    ("from TEshopCusCom where sccId = ?", new Object[]{dbs.getDbsSccId()});
            String parameter = "添加关联业务员,被添加的微分金账号：" + tesc.getAccount();
            CLogBook logBook = logBookService.saveCLogBook(organizationID,
                    parameter, account);
            beans.add(logBook);
        }
        baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    }

    /**
     * 查询所有已关联业务员
     */
    public PageForm selGlStaff(String dbId, PageForm pageForm) {
        StringBuffer sql = new StringBuffer();
        sql.append("select h.staffname,p.goodsname,e.account,e.custype,s.dbsid");
        sql.append(" from dt_devicebind_staff s,dt_hr_staff h,t_Eshop_Cuscom e,dt_devicebind d,dt_productpackaging p");
        sql.append(" where s.dbsdbid = d.dbid and s.dbsstaffid = h.staffid and s.dbssccid = e.sccid and p.model = e.custype");
        sql.append(" and p.type = ? and dbid = ? and s.dbsStatus = ? order by s.dbsdate desc");
        Object[] params = new Object[]{"会员类型级别", dbId, "1"};
        pageForm = baseBeanService.getPageFormBySQL(
                (null != pageForm ? pageForm.getPageNumber() : 1), 10, sql.toString(),
                "select count(1)" + sql.substring(sql.toString().indexOf("from")), params);
        return pageForm;
    }

    /**
     * 判断该车辆是否已经被添加到dt_deviceBind表中
     */
    public String isCarInDevice(String carId, String carnum) {
        String sql = "select count(*) from dt_devicebind d,dt_car_carinformation c where d.dbcarid = c.carid and (d.dbcarid = ? or d.dbcarNumber = ?) and d.dbstatus = ?";
        int count = baseBeanService.getConutByBySqlAndParams(sql, new Object[]{carId, carnum, "1"});
        return count + "";
    }

    /**
     * 判断选中业务员是否已被关联
     */
    public String isStaffInDbs(CAccount account, String sccidStaffids) {
        String[] sccidStaffid = sccidStaffids.substring(0, sccidStaffids.length() - 1).split("@");
        int count = 0;
        for (int i = 0; i < sccidStaffid.length; i++) {
            String[] ss = sccidStaffid[i].split(",");
            StringBuffer sql = new StringBuffer();
            sql.append("select count(*) from dt_devicebind_staff s,dt_devicebind d,dt_car_carinformation c ");
            sql.append(" where s.dbsdbid = d.dbid and d.dbcarid = c.carid and s.dbsstatus = ?");
            //sql.append(" and c.companyid = ? and s.dbssccid = ?");
            sql.append(" and s.dbssccid = ?");
            count = baseBeanService.getConutByBySqlAndParams(sql.toString(), new Object[]{"1", ss[1]});
            if (count > 0) {
                break;
            }
        }
        return count + "";
    }

    /**
     * 删除deviceBind表的某条数据(即修改该条数据的状态为0，同时修改与该条数据关联的deviceBindStaff表的数据状态为02)
     */
    public void delDeviceBind(String dbid) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        String organizationID = (String) session.get("organizationID");
        DeviceBind db = (DeviceBind) baseBeanService.getBeanByHqlAndParams
                ("from DeviceBind where dbid = ?", new Object[]{dbid});
        List<BaseBean> beans = new ArrayList<BaseBean>();
        if (db != null) {
            db.setDbStatus("0");
            db.setDbModifyDate(new Date());
            beans.add(db);
            List<BaseBean> dbsList = baseBeanService.getListBeanByHqlAndParams
                    ("from DeviceBindStaff where dbsDbId = ?",
                            new Object[]{dbid});
            for (BaseBean bb : dbsList) {
                DeviceBindStaff dbs = (DeviceBindStaff) bb;
                dbs.setDbsStatus("02");
                beans.add(dbs);
            }
        }
        if (beans != null) {
            String parameter = "删除投资设备和投资人以及关联业务员,车牌号：" + db.getDbcarNumber();
            CLogBook logBook = logBookService.saveCLogBook(organizationID,
                    parameter, account);
            beans.add(logBook);
            beandao.executeHqlsByParmsList(beans, null, null);
        }
    }

    /**
     * 删除deviceBindStaff表的某条数据(即修改该条数据的状态为01)
     */
    public void delDeviceBindStaff(String dbsid) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        String organizationID = (String) session.get("organizationID");
        DeviceBindStaff dbs = (DeviceBindStaff) baseBeanService.getBeanByHqlAndParams
                ("from DeviceBindStaff where dbsId = ?", new Object[]{dbsid});
        if (dbs != null) {
            List<BaseBean> beans = new ArrayList<BaseBean>();
            dbs.setDbsStatus("02");
            beans.add(dbs);
            TEshopCusCom tesc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams
                    ("from TEshopCusCom where sccId = ?", new Object[]{dbs.getDbsSccId()});
            String parameter = "删除关联业务员,被删除的微分金账号：" + tesc.getAccount();
            CLogBook logBook = logBookService.saveCLogBook(organizationID,
                    parameter, account);
            beans.add(logBook);
            beandao.executeHqlsByParmsList(beans, null, null);
        }
    }

    /**
     * 投资设备绑定设置Excel导出
     */
    public List<Object> exportExcelDevice(CAccount account, String carNum,
                                          String deviceStatu, String tzName, String tzAccount) {
        StringBuffer sql = new StringBuffer();
        sql.append("select t.companyname,t.organizationname,t.goodsname,t.dbcarnumber,t.dbtztype,t.state1,");
        sql.append(" t.glname,t.tzname,t.tzaccount,t.tzcustype,u.ywname,u.ywaccount,u.ywcustype");
        sql.append(" from (select co.companyname,cor.organizationname,g.goodsname,d.dbcarnumber,d.dbtztype,c.state1,");
        sql.append(" c.staffname as glname,hs.staffname as tzname,ec.account as tzaccount,ec.custype as tzcustype,d.dbid");
        sql.append(" from dt_devicebind d,dt_car_carinformation c,dtcorganization cor,dtcompany co,dtgoodsmanage g,");
        sql.append(" dt_hr_staff hs,t_Eshop_Cuscom ec ");
        sql.append(" where d.dbcarid = c.carid and d.dbgoodsid = g.goodsid and c.organizationid = cor.organizationid");
        sql.append(" and c.companyid = co.companyid and d.dbstaffid = hs.staffid and d.dbsccid = ec.sccid");
        sql.append(" and d.dbstatus = ? and co.companyid = ?");
        List<Object> params = new ArrayList<Object>();
        params.add("1");
        params.add(account.getCompanyID());
        if (carNum != null && !carNum.equals("")) {
            sql.append(" and c.carNum like ?");
            params.add("%" + carNum + "%");
        }
        if (deviceStatu != null && !deviceStatu.equals("")) {
            sql.append(" and c.state1=?");
            params.add(deviceStatu);
        }
        if (tzName != null && !"".equals(tzName)) {
            sql.append(" and hs.staffname=?");
            params.add(tzName);
        }
        if (tzAccount != null && !tzAccount.equals("")) {
            sql.append(" and ec.account like ?");
            params.add("%" + tzAccount + "%");
        }
        sql.append(" order by d.dbdate desc");
        sql.append(" ) t left join(select h.staffname as ywname,e.account as ywaccount,e.custype as ywcustype,ds.dbsdbid");
        sql.append(" from dt_devicebind_staff ds,dt_hr_staff h,t_Eshop_Cuscom e");
        sql.append(" where ds.dbssccid = e.sccid and ds.dbsstaffid = h.staffid and ds.dbsstatus = ?) u");
        sql.append(" on t.dbid = u.dbsdbid");
        params.add("1");
        @SuppressWarnings("unchecked")
        List<Object> list = baseBeanService.getListBeanBySqlAndParams(sql.toString(), params.toArray());
        for (int i = 0; i < list.size(); i++) {
            Object[] dbs = (Object[]) list.get(i);
            if ("01".equals(dbs[4])) {
                ((Object[]) list.get(i))[4] = "教练车";
            } else {
                ((Object[]) list.get(i))[4] = "单车";
            }
            if ("01".equals(dbs[5])) {
                ((Object[]) list.get(i))[5] = "公司投资设备";
            } else {
                ((Object[]) list.get(i))[5] = "挂靠设备";
            }

            if ("2".equals(dbs[9])) {
                ((Object[]) list.get(i))[9] = "公司商城业主会员";
            } else if ("3".equals(dbs[9])) {
                ((Object[]) list.get(i))[9] = "经理商城业主会员";
            } else if ("4".equals(dbs[9])) {
                ((Object[]) list.get(i))[9] = "主管商城业主会员";
            } else if ("5".equals(dbs[9])) {
                ((Object[]) list.get(i))[9] = "业务商城业主会员";
            }

            if ("0".equals(dbs[12])) {
                ((Object[]) list.get(i))[12] = "平台";
            } else if ("0.5".equals(dbs[12])) {
                ((Object[]) list.get(i))[12] = "国税";
            } else if ("1".equals(dbs[12])) {
                ((Object[]) list.get(i))[12] = "地税";
            } else if ("2".equals(dbs[12])) {
                ((Object[]) list.get(i))[12] = "公司商城业主会员";
            } else if ("3".equals(dbs[12])) {
                ((Object[]) list.get(i))[12] = "经理商城业主会员";
            } else if ("4".equals(dbs[12])) {
                ((Object[]) list.get(i))[12] = "主管商城业主会员";
            } else if ("5".equals(dbs[12])) {
                ((Object[]) list.get(i))[12] = "业务商城业主会员";
            } else if ("6".equals(dbs[12])) {
                ((Object[]) list.get(i))[12] = "VIP客户";
            } else if ("7".equals(dbs[12])) {
                ((Object[]) list.get(i))[12] = "客户";
            }
        }
        return list;

    }


}



