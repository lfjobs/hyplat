package hy.ea.company.service.impf;

import com.tiantai.wfj.bo.PosDevice;
import hy.ea.bo.company.DepotManage;
import hy.ea.company.service.DepotManageService;
import hy.ea.util.Constant;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


@Service
public class DepotManageServiceImpf implements DepotManageService {
    @Resource
    private ServerService serverService;
    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private BaseBeanService bs;

    /**
     * 根据仓库编码获取仓库信息
     *
     * @param depotParam code or id
     * @param companyId  公司id
     * @param t          0:全部 1：智能货柜  2：柜门  3：秤盘
     * @return
     */
    public BaseBean getDepotToCoding(String depotParam, String companyId, int t) {

        String hql = " from DepotManage where 1=1";
        List<Object> params = new ArrayList<Object>();
        switch (t) {
            case 1:
                /*PosDevice pos=(PosDevice)getPosDeviceByPosNum(depotParam);
                if (pos != null){
                    depotParam=pos.getHgcode();
                }*/
                //库房类别 智能货柜id
                hql += " and itemID = ?";
                params.add(Constant.DEPOT_VM);
                break;
            case 2:
                //库房类别 柜门id
                hql += " and itemID = ?";
                params.add(Constant.DEPOT_DOOR);
                break;
            case 3:
                //库房类别 秤盘id
                hql += " and itemID = ?";
                params.add(Constant.DEPOT_SCALE);
                break;
        }
        hql += " and (depotCoding = ? or depotID = ?) and depotState in(?,?)";
        params.add(depotParam);
        params.add(depotParam);
        params.add("00");
        params.add("02");
        if (companyId != null && !companyId.equals("")) {
            hql += " and companyID = ?";
            params.add(companyId);
        }
        DepotManage depotManage = (DepotManage) baseBeanDao.getBeanByHqlAndParams(hql, params.toArray());
        return depotManage;
    }

    /**
     * 根据仓库父id获取仓库信息
     *
     * @param depotpid  父id
     * @param companyId 公司id
     * @param t         0:全部 1：智能货柜  2：柜门  3：秤盘
     * @return
     */
    public List<BaseBean> getListDepotToPid(String depotpid, String companyId, int t) {
        List<BaseBean> bList = new ArrayList<BaseBean>();
        String hql = " from DepotManage where depotPID = ? and depotState in(?,?)";
        List<Object> params = new ArrayList<Object>();
        params.add(depotpid);
        params.add("00");
        params.add("02");
        switch (t) {
            case 1:
                //库房类别 智能货柜id
                hql += " and itemID = ?";
                params.add(Constant.DEPOT_VM);
                break;
            case 2:
                //库房类别 柜门id
                hql += " and itemID = ?";
                params.add(Constant.DEPOT_DOOR);
                break;
            case 3:
                //库房类别 秤盘id
                hql += " and itemID = ?";
                params.add(Constant.DEPOT_SCALE);
                break;
            case 4:
                //库房类别 自助超市id
                hql += " and itemID = ?";
                params.add(Constant.DEPOT_SM);
                break;

        }
        if (companyId != null && !companyId.equals("")) {
            hql += " and companyID = ?";
            params.add(companyId);
        }
        hql += " order by depotCoding";
        bList = baseBeanDao.getListBeanByHqlAndParams(hql, params.toArray());
        return bList;
    }

    /**
     * 根据posNum获取pos设备信息
     *
     * @param posNum 设备编号
     * @return
     */
    public BaseBean getPosDeviceByPosNum(String posNum) {
        String hql = " from PosDevice where posNum = ? or hgcode=?";
        PosDevice pos = (PosDevice) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{posNum, posNum});
        return pos;
    }

    /**
     * 查询符合规格的仓库
     *
     * @param depotid    仓库上级id
     * @param companyId  公司id
     * @param selectType 0：向上查询 1：向下查询 默认向下查询
     * @return
     */
    public List<Object> ParenByDepotid(String depotid, String companyId, int selectType) {
        List<Object> bList;
        List<Object> params = new ArrayList<Object>();
        /*String sql = " SELECT t.depotID, t.companyid, t.depotName,t.depotCoding";*/
        String sql = " SELECT t.*";
        sql += " FROM dtDepotManage t  where depotState in(?,?)";
        params.add("00");
        params.add("02");
        if (companyId != null && !companyId.isEmpty()) {
            sql += " and companyID = ?";
            params.add(companyId);
        }
        sql += " START WITH t.depotID = ? CONNECT BY PRIOR ";
        switch (selectType) {
            case 0:
                sql += "t.depotPID=t.depotID";
                break;
            case 1:
                sql += "t.depotID=t.depotPID";
                break;
            default:
                sql += "t.depotID=t.depotPID";
                break;
        }
        params.add(depotid);
        bList = bs.getListBeanBySqlAndParams(sql, params.toArray());
        return bList;
    }

    /**
     * 根据公司id查询库房
     *
     * @param companyId
     * @return
     */
    public Map<String, Object> getListDepotToComid(String companyId) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<BaseBean> bList;
        String hql = " from DepotManage where depotState !=? and companyID = ? and itemID in(?,?,?)";
        List<Object> params = new ArrayList<Object>();
        params.add("01");
        params.add(companyId);
        params.add(Constant.DEPOT_VM);
        params.add(Constant.DEPOT_DOOR);
        params.add(Constant.DEPOT_SCALE);
        try {
            bList = baseBeanDao.getListBeanByHqlAndParams(hql, params.toArray());
            map.put("depotList", bList);
            map.put("flag", 0);
        } catch (Exception e) {
            map.put("code", 500);
            map.put("meg", e.getMessage());
            map.put("flag", 1);
        }
        return map;
    }


    /**
     * 查询符合规格的仓库
     *
     * @param companyId 公司id
     * @return
     */
    public Map<String, Object> getParenByDepotid(String companyId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map = getListDepotToComid(companyId);
        int flag = Integer.parseInt(map.get("flag") + "");
        if (flag == 0) {
            List<BaseBean> bList = (List<BaseBean>) map.get("depotList");
            if (bList != null && bList.size() > 0) {
                List<BaseBean> depotList = new ArrayList<>();
                List<BaseBean> doorList = new ArrayList<>();
                List<BaseBean> cabinetList = new ArrayList<>();
                for (int i = 0; i < bList.size(); i++) {
                    DepotManage d = (DepotManage) bList.get(i);
                    if (d.getItemID() != null && d.getItemID().equals(Constant.DEPOT_DOOR)) {
                        doorList.add(d);
                        continue;
                    }
                    if (d.getItemID() != null && d.getItemID().equals(Constant.DEPOT_SCALE)) {
                        depotList.add(d);
                        continue;
                    }
                    if (d.getItemID() != null && d.getItemID().equals(Constant.DEPOT_VM)) {
                        cabinetList.add(d);
                        continue;
                    }
                }
                map.put("doorList", doorList);
                map.put("depotList", depotList);
                map.put("cabinetList", cabinetList);
            }
            /*if (bList != null&&bList.size()>0) {
                List<BaseBean> depotList=new ArrayList<BaseBean>();
                for (int i = 0; i < bList.size(); i++) {
                    DepotManage d=(DepotManage)bList.get(i);
                    List<Object> o=ParenByDepotid(d.getDepotID(),companyId,0);
                    if (o != null&&o.size()>0) {
                        int n=3;
                        if (o.size()<3) {
                            n=o.size();
                        }
                        String str="";
                        for (int j = 0; j < n; j++) {
                            Object[] oo=(Object[])o.get(j);
                            String code= (String) oo[3];
                            String name= (String) oo[2];
                            //str+="-("+code+")"+name;
                            if (!str .equals("")) {
                                str=" > "+str;
                            }
                            str=name+str;
                        }
                        d.setDepotName(str);
                    }else {
                        map.put("code",2);
                        map.put("meg",d.getDepotName()+"-没有父级仓库信息");
                        map.put("flag",0);
                    }
                    depotList.add(d);
                }
                if (depotList != null&&depotList.size()>0) {
                    map.put("depotList",depotList);
                }
            }else {
                map.put("code",1);
                map.put("meg","暂无仓库信息");
                map.put("flag",1);
            }*/
        }
        return map;
    }

    /**
     * 根据名称查询数据条数（验证重名）
     *
     * @param companyId 公司id
     * @param name      仓库名称
     * @return
     */
    public Map<String, Object> getCountByName(String companyId, String name) {
        Map<String, Object> map = new HashMap<String, Object>();
        String hql = "select count(*) from DepotManage where depotState !=? and companyID = ? and depotName=?";
        int count = baseBeanDao.getConutByByHqlAndParams(hql, new Object[]{"01", companyId, name});
        map.put("count", count);
        return map;
    }

    /**
     * 查询秤盘总数量
     *
     * @return
     */
    public Integer getCountByItemId() {
        String hql = "select count(*) from DepotManage where itemID=?";
        int count = baseBeanDao.getConutByByHqlAndParams(hql, new Object[]{Constant.DEPOT_SCALE});
        return count;
    }

    /**
     * 仓库数据升级
     *
     * @param companyId 公司id
     */
    @Override
    @Transactional
    public String dataUpgrade(String companyId) {
        try {
            //初始化保存对象集
            List<BaseBean> beans = new ArrayList();
            List<BaseBean> swDepot = getListDepotToPid("001", companyId, 0);
            List<BaseBean> cwDepot = getListDepotToPid("003", companyId, 0);

            String[] stra = {"股东库", "董事会库", "监事会库", "工会库", "顾问会库", "董事长室库", "总经理室库", "人事库", "财务库", "生产库", "营销库", "创收库"};
            String[] strb = {"实物仓库", "资料仓库", "资金仓库", "金币仓库"};
            String[] strs = {"原材料库", "成品库", "销售库", "退货库", "物流库"};
            if (swDepot != null && swDepot.size() > 0) {
                Random random = new Random();
                int ni = random.nextInt(swDepot.size());
                if (swDepot != null) {
                    for (int i = 0; i < swDepot.size(); i++) {
                        DepotManage depotManage = (DepotManage) swDepot.get(i);
                        if (depotManage.getDepotName().equals(stra[ni])) {
                            return "2";
                        }
                    }
                }
            }
            for (int j = 0; j < stra.length; j++) {
                String apid = saveDepot(companyId, "001", j + 1, stra[j], beans);
                for (int i = 0; i < strb.length; i++) {
                    if (!stra[j].equals("财务库") && i >= 2) {
                        break;
                    }
                    /*if (cwDepot != null&&cwDepot.size()>0&&stra[j].equals("财务库")) {
                        break;
                    }*/
                    String bpid = saveDepot(companyId, apid, i + 1, strb[i], beans);
                    if (stra[j].equals("营销库") && strb[i].equals("实物仓库")) {
                        if (swDepot != null && swDepot.size() > 0) {
                            for (int n = 0; n < swDepot.size(); n++) {
                                DepotManage depotManage = (DepotManage) swDepot.get(n);
                                for (int k = 0; k < strs.length; k++) {
                                    if (depotManage.getDepotName().equals(strs[k])) {
                                        depotManage.setDepotState("02");
                                        break;
                                    }
                                }
                                depotManage.setDepotPID(bpid);
                                depotManage.setDepotNum(n + 1);
                                System.out.println(depotManage.getDepotID() + "--" + depotManage.getDepotPID() + ":" + depotManage.getDepotName());
                                beans.add(depotManage);
                            }
                        } else {
                            for (int n = 0; n < strs.length; n++) {
                                saveDepot(companyId, bpid, n + 1, strs[n], beans);
                            }
                        }
                    }
                }
                if (stra[j].equals("财务库") && cwDepot != null && cwDepot.size() > 0) {
                    for (int i = 0; i < cwDepot.size(); i++) {
                        DepotManage depotManage = (DepotManage) cwDepot.get(i);
                        if (depotManage.getDepotName().equals(strs[i])) {
                            depotManage.setDepotState("02");
                        }
                        depotManage.setDepotPID(apid);
                        depotManage.setDepotNum(2 + 1);
                        System.out.println(depotManage.getDepotID() + "--" + depotManage.getDepotPID() + ":" + depotManage.getDepotName());
                        beans.add(depotManage);
                    }
                }
            }
            baseBeanDao.executeHqlsByParmsList(beans, null, null);
        } catch (Exception e) {
            return "3";
        }
        return "0";
    }

    /**
     * 保存仓库信息
     *
     * @param comid
     * @param name
     * @param beans
     */
    private String saveDepot(String comid, String pid, int i, String name, List<BaseBean> beans) {
        DepotManage depot = new DepotManage();
        depot.setDepotID(serverService.getServerID("DepotManage"));
        depot.setCompanyID(comid);
        depot.setDepotPID(pid);
        depot.setDepotNum(i);
        depot.setDepotName(name);
        depot.setDepotState("02");
        depot.setDepotType("1");
        beans.add(depot);
        System.out.println(depot.getDepotID() + "--" + depot.getDepotPID() + ":" + depot.getDepotName());
        return depot.getDepotID();
    }
}
