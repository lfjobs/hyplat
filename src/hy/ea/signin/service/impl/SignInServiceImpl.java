package hy.ea.signin.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.tiantai.wfj.bo.TEshopCusFace;
import hy.ea.bo.finance.BenDis.*;
import hy.ea.interceptor.JsonHandlerException;
import hy.ea.signin.service.SignInService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.bo.WfjGuize;
import com.tiantai.wfj.service.GoldOrderService;

import hy.ea.bo.Company;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.company.DepotManage;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.bo.lottery.PrizeActivityBean;
import hy.ea.bo.lottery.SignScoreBean;
import hy.ea.finance.dao.BonusPointsDao;
import hy.ea.finance.service.BonusPointsService;
import hy.ea.util.DateUtil;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.util.JushMain;

@Service

public class SignInServiceImpl implements SignInService {

    @Resource
    private BaseBeanDao beandao;
    @Resource
    private BonusPointsDao bonusPointsDao;
    @Resource
    private ServerService serverService;
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private GoldOrderService gOrderService;


    private final static Logger logger = LoggerFactory.getLogger(SignInServiceImpl.class);

    /**
     * 跳转签到页面
     *
     * @param companyId 发起活动的公司id
     * @return 字符串 toSign  yes 说明签到过了  no  说明还没有签到
     */
    @Override
    @Transactional
    public String getSign(String companyId, String account) throws JsonHandlerException {
        String toSign = "";
        //根据时间查询用户是否签到（一个帐号只能在一个公司签到）
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String time = df.format(new Date());
        StringBuffer sql = new StringBuffer();
        List<Object> obj = new ArrayList<Object>();
        sql.append("select * from DT_WFJ_SIGN sign ");
        sql.append("where sign.companyId=? ");
        sql.append("and sign.account=? ");
        sql.append("and sign.signdate between ? and ?");
        obj.add(companyId);
        obj.add(account);

        obj.add(Utilities.getDateFromString(time + " 00:00:00", "yyyy-MM-dd HH:ss:mm"));
        obj.add(Utilities.getDateFromString(time + " 23:59:59", "yyyy-MM-dd HH:ss:mm"));
        @SuppressWarnings("unchecked") List<Object> list = baseBeanService.getListBeanBySqlAndParams(sql.toString(), obj.toArray());
        if (list != null && list.size() > 0) {//说明该用户签到过了
            toSign = "yes";
        } else {
            toSign = "no";
        }
        return toSign;
    }

    /**
     * 分享之前保存手机签到信息
     *
     * @param sccid         签到人sccid
     * @param account       签到人account
     * @param companyId     被签到公司ID
     * @param nums          积分数
     * @param signSite      签到地址
     * @param signInfo      签到信息
     * @param signImagePath 签到发表图片路径
     * @return
     */
    @Transactional
    public Map<String, Object> saveSign(String sccid, String account, String companyId, int nums, String signSite, String signInfo, String signImagePath) throws JsonHandlerException {
        Map<String, Object> map = new HashMap();
        String flag = "";
        try {
            List<BaseBean> beans = new ArrayList();
//			this.beandao.saveBeansListAndexecuteHqlsByParams(beans, null, null);
            Sign sign = new Sign();
            sign.setSignId(this.serverService.getServerID("Sign"));
            sign.setSccid(sccid);
            sign.setAccount(account);
            sign.setCompanyId(companyId);
            sign.setSignScore(nums + "");
            sign.setSignDate(new Date());
            sign.setSignSite(signSite);
            if ("pos".equals(signImagePath)) {
                sign.setSignType("01");
            } else {
                sign.setSignType("00");
            }
            if ((signInfo != null) && (!"".equals(signInfo))) {
                sign.setSignInformation(signInfo);
            }
            if ((signImagePath != null) && (!"".equals(signImagePath))) {
                sign.setSignImagePath(signImagePath);
            }
            beans.add(sign);
            this.beandao.saveBeansListAndexecuteHqlsByParams(beans, null, null);
            flag = "签到成功";
            String signId = sign.getSignId();
            map.put("signId", signId);
            map.put("signDate", Utilities.getDateString(sign.getSignDate(), "yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new JsonHandlerException("", "签到失败，请稍后重试！");
        }
        return map;
    }

    /**
     * 根据编号获取当前公司信息
     *
     * @param posNum
     * @return
     */
    public Company getCompanyByPosNum(String posNum) throws JsonHandlerException {
        String hql = "from Company c where c.companyID = (select cc.comanyId from CcomCom cc where cc.ccompanyId = (select p.accessCcomID from  PosDevice p where p.posNum = ?))";
        return (Company) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{posNum});
    }

    /**
     * @param faceEntityId
     * @param companyId
     * @return
     */
    @Transactional
    public Map<String, Object> signIn(String faceEntityId, String companyId) throws JsonHandlerException {
        Map<String, Object> map = new HashMap<String, Object>();

        TEshopCusFace cusFace = (TEshopCusFace) baseBeanService.getBeanByHqlAndParams("from TEshopCusFace where faceEntityId = ?", new Object[]{faceEntityId});
        TEshopCusCom tc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0", new Object[]{cusFace.getAccount()});

        Map<String, Object> mp = saveSign(cusFace.getSccId(), cusFace.getAccount(), companyId, 0, null, null, "pos");
        map.put("signDate", (String) mp.get("signDate"));

        Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{tc.getStaffid()});
        map.put("staffName", staff.getStaffName());

        //查询今日签到次数
        String sql = "from Sign where companyId = ? and sccid = ? and signDate  between ? and ?";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String time = df.format(new Date());
        Date start = Utilities.getDateFromString(time + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
        Date end = Utilities.getDateFromString(time + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
        List<BaseBean> signlist = baseBeanService.getListBeanByHqlAndParams(sql, new Object[]{companyId, cusFace.getSccId(), start, end});
        map.put("signCount", signlist.size());

        return map;
    }

    /**
     * 微分金账号绑定faceEntityId
     *
     * @param faceEntityId
     * @param account
     * @return
     */
    @Transactional
    public TEshopCusFace bindFaceEntityId(String faceEntityId, String account) throws JsonHandlerException {
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0 and cusType!='0' order by cusType", new Object[]{account});
        if (list.isEmpty()) {
            throw new JsonHandlerException("", "该手机号没有注册微分金，请注册后使用签到功能！");
        }

        TEshopCusFace face = (TEshopCusFace) baseBeanService.getBeanByHqlAndParams("from TEshopCusFace where account = ? and faceEntityId = ?", new Object[]{account, faceEntityId});
        if (face == null) {
            face = new TEshopCusFace();
        }

        TEshopCusCom cusCom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0 and acquiesce = '01'", new Object[]{account});

        face.setCusfId(serverService.getServerID("cusf"));
        face.setSccId(cusCom.getSccId());
        face.setAccount(account);
        face.setFaceEntityId(faceEntityId);

        baseBeanService.saveOrUpdate(face);
        return face;
    }

}
