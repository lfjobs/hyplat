package mobile.tiantai.android.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tiantai.wfj.bo.WfjGuize;
import com.tiantai.wfj.bo.WfjJifen;
import com.tiantai.wfj.bo.WfjJifenDetail;
import hy.ea.bo.finance.BenDis.JoinFans;
import hy.ea.bo.finance.BenDis.RedPacket;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffContact;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import javax.annotation.Resource;

import mobile.tiantai.android.util.JushMain;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tiantai.wfj.bo.TEshopCusCom;

import mobile.tiantai.android.dao.TelDao;
import mobile.tiantai.android.service.TelService;

@Service @Transactional
public class TelServiceImpl implements TelService{

	@Resource
	private TelDao telDao;
	
	@Resource
	private ServerService serverService;
	
	@Resource
	private BaseBeanService baseBeanService;
	
	@Override
	public void updateTel(String sccId,String account, String tel) {
		TEshopCusCom tscc = telDao.getCompanyID(sccId);
		String companyID = null;
		if(tscc.getState().equals("1")){
			companyID = "company201009046vxdyzy4wg0000000025";
		}
		if(tscc.getState().equals("2")){
			companyID = tscc.getCompanyId();
		}
		JSONObject json = new JSONObject(tel);
		JSONArray jsa = json.getJSONArray("contact");
		for (int i = 0; i < jsa.length(); i++) {
			List<BaseBean> list=new ArrayList<BaseBean>();
			
			JSONObject jsonObj = (JSONObject) jsa.get(i);
			String number =  jsonObj.get("number").toString();
			String name =  jsonObj.get("name").toString();
			StaffContact sc1 = telDao.getAccount(number,tscc.getStaffid());
			if(sc1==null){
				StaffContact sc = new StaffContact();
				sc.setStaffID(tscc.getStaffid());
				sc.setContactID(serverService.getServerID("contact"));
				sc.setContactType("scode20100426c8rdqacjae0000000001");//联系类型：电话
				sc.setContactName(name);//姓名
				sc.setContactWay(number);//号码
				sc.setCompanyID(companyID);
				list.add(sc);
				
				TEshopCusCom tsc= null;
				List<BaseBean> tsclist = (List<BaseBean>)baseBeanService.getListBeanByHqlAndParams(
						"from TEshopCusCom t where t.account=? order by t.acquiesce desc,t.cusType asc", 
						new Object[]{number});
				if(tsclist!=null&&tsclist.size()>0){
					tsc=(TEshopCusCom)tsclist.get(0);
				}
				Staff staff = null;
				if(tsc==null){
					staff=new Staff();
					staff.setStaffID(serverService.getServerID("staff"));
					staff.setStaffName(name);
					staff.setReference(number);
					String phql = "select count(*) from Staff ";
					int pcount = baseBeanService.getConutByByHqlAndParams(phql, null);
					staff.setStaffCode("NO" + pcount);
					staff.setVerifyTime(new Date());
					staff.setGroupCompanySn("groupcompany20120523G3VR9PXHZD0000000021");
					staff.setStaus("01");
					staff.setStaffDesc("移动粉丝");
					list.add(staff);
				}
				JoinFans jf = new JoinFans();
				jf.setJfID(serverService.getServerID("jf"));
				jf.setState("00");
				jf.setZsccId(tscc.getSccId());
				jf.setFsccId(tsc==null?"":tsc.getSccId());
				jf.setStaffid(staff==null?"":staff.getStaffID());
				jf.setZaccount(tscc.getAccount());
				jf.setFaccount(number);
				jf.setSource("移动粉丝");
				list.add(jf);
			}else{
				sc1.setContactName(name);
				list.add(sc1);
				
				Staff staff = (Staff)baseBeanService.getBeanByHqlAndParams("from Staff where reference=? and staffDesc='移动粉丝'", new Object[]{number});
				if(staff!=null){
					staff.setStaffName(name);
					list.add(staff);
				}
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
		}
		
	}
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public PageForm getWfjList(String staffID,String wfj,String pageNumber,String parameter){
		List<Object> array = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		array.add(staffID);
		if(wfj.equals("1")){//是微分金用户
			sql.append("select t.contactWay,t.contactName,c.staffid,d.headimage,c.sccId,c.state,cc.ccompany_id");
			sql.append(" from dt_hr_staff_contact t ");
			sql.append(" left join dt_ccom_com cc on t.companyid = cc.compnay_id");
			sql.append(" left join t_eshop_cuscom c on t.contactWay = c.account ,dt_hr_Staff d");
			sql.append(" where t.staffID=? and d.staffid = c.staffID");
			sql.append(" and c.account is not null");
		}else if (wfj.equals("0")){//不是微分金用户
			sql.append("select t.contactWay,t.contactName,c.staffid");
			sql.append(" from dt_hr_staff_contact t left join t_eshop_cuscom c");
			sql.append(" on t.contactWay = c.account where t.staffID=?");
			sql.append(" and c.account is null");
		}
		if(parameter !=null && !parameter.equals("")){
			sql.append(" and (t.contactWay like ? or t.contactName like ?)");
			array.add("%"+parameter+"%");
			array.add("%"+parameter+"%");
		}
		sql.append(" order by t.contactName");
		PageForm pageForm = baseBeanService.getPageFormBySQL(Integer.parseInt(pageNumber), 25, sql.toString(),"select count(*) from ("+sql+")", array.toArray());
		return pageForm;
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<Object> getPeopleInformation(String xlist) {
		
		JSONObject json = new JSONObject(xlist);
		JSONArray li = json.getJSONArray("accountlist");
		List<Object> params = new ArrayList<Object>();
		StringBuffer str=new StringBuffer();
		str.append(" select cus.account,staff.staffname,staff.headimage from t_eshop_cuscom cus ");
		str.append(" left join dt_hr_staff staff on cus.account = staff.reference ");
		str.append(" where staff.staffkey in (select max(staff.staffkey) from dt_hr_Staff staff ");
		str.append(" where  staff.reference in ( ");
		
		for (int i = 0; i < li.length(); i++) {
			JSONObject jsonObj = (JSONObject) li.get(i);
			String user =  jsonObj.get("account").toString();
				if(i==li.length()-1){
					str.append("?");
				}else{
				str.append("?,");
				}
				params.add(user);
			}
		str.append(" ) group by staff.reference ) ");
		List<Object> lists=baseBeanService.getListBeanBySqlAndParams(str.toString(), params.toArray());
		
		return lists;
	}
         /*
         *发送红包
         * goldNum金币数量，sender发送人，recipient接收人，liuYan留言
         * return
         * 1 成功
         * 2黑名单
         * 3; //发送金币数量不够
         */
    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public synchronized int redPacket(String sender,String recipient,String goldNum,String liuYan) {

        String hql = " from TEshopCusCom where sccId=?";
        List<BaseBean> beans = new ArrayList<BaseBean>();
        // 发送人
        TEshopCusCom tccS = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[] { sender });
        // 接收人
        TEshopCusCom tccR = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[] { recipient });
        int countR = baseBeanService.getConutByByHqlAndParams(
                "select count(*) from TEshopCustomer where account=? AND logOff=0 and status is not null",
                new Object[] { tccR.getAccount() });
        if (countR > 0) {
            return  2;   //黑名单
        } else {
            if (tccS != null && tccR != null) {
                String Hql = " from WfjJifen where staffId=? and sccid=?";
                // 发送人
                WfjJifen wfjJifenS = (WfjJifen) baseBeanService.getBeanByHqlAndParams(Hql,
                        new Object[]{tccS.getStaffid(), tccS.getSccId()});
                // 接收人
                WfjJifen wfjJifenR = (WfjJifen) baseBeanService.getBeanByHqlAndParams(Hql,
                        new Object[]{tccR.getStaffid(), tccR.getSccId()});
                if (Float.parseFloat(wfjJifenS.getWfjJifenScore()) - Float.parseFloat(goldNum) >= 0){
                    if (wfjJifenR == null) {
                        wfjJifenR = new WfjJifen();
                        wfjJifenR.setWfjJifenId(serverService.getServerID("WfjJifen"));
                        wfjJifenR.setStaffId(tccR.getStaffid());
                        wfjJifenR.setWfjJifenScore("0");
                        wfjJifenR.setWfjJifenState(0);
                        wfjJifenR.setSccid(tccR.getSccId());
                    }

                if (wfjJifenS != null) {

                    // 在金币明细表中插入记录
                    WfjJifenDetail DetailS = new WfjJifenDetail();// 发送人的明细
                    WfjJifenDetail DetailR = new WfjJifenDetail();// 接收人的明细
                    // 发送人
                    Staff staffS = (Staff) baseBeanService.getBeanByHqlAndParams(" from Staff where staffId=? ",
                            new Object[]{wfjJifenS.getStaffId()});
                    // 接收人
                    Staff staffR = (Staff) baseBeanService.getBeanByHqlAndParams(" from Staff where staffId=? ",
                            new Object[]{wfjJifenR.getStaffId()});

                    // 添加红包记录表明细
                    RedPacket hb = new RedPacket();

                    hb.setRedPacketId(serverService.getServerID("RedPacket"));
                    hb.setSccIdR(recipient);// 接收人的sccId
                    hb.setSccIdS(sender);// 发送人的sccId
                    hb.setSender(staffS.getStaffName());// 发送人的名字
                    hb.setRecipient(staffR.getStaffName());// 接收人的名字
                    hb.setGoldNum(Integer.parseInt(goldNum));// 红包的金币数
                    hb.setRedDate(new Date());// 时间

                    // 订单表(金币出库单 是发送人的)
                    float hbJB = Float.parseFloat(goldNum);
                    float hbSum = (float) (hbJB * 0.01);

                    CashierBills cashierBillsCK = new CashierBills();
                    cashierBillsCK.setCashierBillsID(serverService.getServerID("cashierTally"));

                    // 获得公司id
                    String hqlCK = "select t.pseudo_company_name,t.companyId from T_ESHOP_CUSCOM t where t.state=2 connect by nocycle prior t.suppersccid = t.SCCID start with t.SCCID = ?";
                    // 接收人公司
                    List<Object> listCK = baseBeanService.getListBeanBySqlAndParams(hqlCK,
                            new Object[]{tccR.getSccId()});
                    Object ck = listCK.get(0);
                    Object[] ckd = (Object[]) ck;
                    cashierBillsCK.setCompanyID(ckd[1].toString());// 获取公司id
                    cashierBillsCK.setCompanyName(ckd[0].toString());// 获得公司名称
                    cashierBillsCK.setCashierDate(new Date());
                    cashierBillsCK.setJournalNum(serverService.getBillID(ck.toString()));
                    cashierBillsCK.setBillsType("金币出库单");
                    // 谁发的
                    cashierBillsCK.setStaffID(staffS.getStaffID());// 出库是发送人的账单，存的接收人的staffid
                    cashierBillsCK.setStaffName(staffS.getStaffName());// 出库是发送人的账单，存的接收人的staffid
                    cashierBillsCK.setStatus("16");
                    // 发给谁
                    cashierBillsCK.setContactUserID(tccR.getStaffid());
                    cashierBillsCK.setCtUserName(staffR.getStaffName());
                    cashierBillsCK.setAppstyle("13");
                    cashierBillsCK.setStatusbill("06");
                    cashierBillsCK.setProID("007");
                    cashierBillsCK.setProjectName("红包");
                    cashierBillsCK.setPriceSub(String.valueOf(hbSum));

                    // 订单表(金币入库单 接收人)
                    CashierBills cashierBillsRK = new CashierBills();
                    cashierBillsRK.setCashierBillsID(serverService.getServerID("cashierTally"));
                    // 获取发送人公司id
                    List<Object> listRK = baseBeanService.getListBeanBySqlAndParams(hqlCK,
                            new Object[]{tccS.getSccId()});
                    Object rk = listRK.get(0);
                    Object[] rkd = (Object[]) rk;
                    cashierBillsRK.setCompanyID(rkd[1].toString());// 公司id
                    cashierBillsRK.setCompanyName(rkd[0].toString());// 公司名字
                    cashierBillsRK.setCashierDate(new Date());
                    cashierBillsRK.setJournalNum(serverService.getBillID(rk.toString()));
                    cashierBillsRK.setBillsType("金币入库单");
                    // 谁收的
                    cashierBillsRK.setStaffID(staffS.getStaffID());
                    cashierBillsRK.setStaffName(staffS.getStaffName());
                    cashierBillsRK.setStatus("15");
                    // 收的谁的
                    cashierBillsRK.setContactUserID(tccS.getStaffid());
                    cashierBillsRK.setCtUserName(staffS.getStaffName());
                    cashierBillsRK.setAppstyle("13");
                    cashierBillsRK.setStatusbill("06");
                    cashierBillsRK.setProID("007");
                    cashierBillsRK.setProjectName("红包");
                    cashierBillsRK.setPriceSub(String.valueOf(hbSum));

                    // 商品表 (出库表) 从ProductPackaging表中查找，然后 添加字段
                    GoodsBills goodsBillsCK = new GoodsBills();
                    goodsBillsCK.setCompanyID("company201009046vxdyzy4wg0000000025");// 北京天太世统的公司id
                    goodsBillsCK.setGoodsName("微分金金币");
                    goodsBillsCK.setGoodsBillsID(serverService.getServerID("goodsbills"));
                    goodsBillsCK.setCashierBillsID(cashierBillsCK.getCashierBillsID());

                    String ppHQL = "from ProductPackaging where goodsName=?";
                    ProductPackaging pd = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(ppHQL,
                            new Object[]{goodsBillsCK.getGoodsName()});
                    goodsBillsCK.setGoodsID(pd.getGoodsID());
                    goodsBillsCK.setPpID(pd.getPpID());
                    goodsBillsCK.setTypeID(pd.getType());
                    goodsBillsCK.setGoodsNum(pd.getGoodsNum());
                    goodsBillsCK.setQuantity(goldNum);
                    goodsBillsCK.setPrice("0.01");
                    goodsBillsCK.setGoodsVariableID("个");
                    goodsBillsCK.setMoney(String.valueOf(hbSum));
                    goodsBillsCK.setKcStatus("16");

                    // 商品表 (入库表)
                    GoodsBills goodsBillsRK = new GoodsBills();
                    goodsBillsRK.setCompanyID("company201009046vxdyzy4wg0000000025");// 北京天太世统的公司id
                    goodsBillsRK.setGoodsName("微分金金币");
                    goodsBillsRK.setGoodsBillsID(serverService.getServerID("goodsbills"));
                    goodsBillsRK.setCashierBillsID(cashierBillsRK.getCashierBillsID());
                    goodsBillsRK.setGoodsID(pd.getGoodsID());
                    goodsBillsRK.setPpID(pd.getPpID());
                    goodsBillsRK.setTypeID(pd.getType());
                    goodsBillsRK.setGoodsNum(pd.getGoodsNum());
                    goodsBillsRK.setQuantity(goldNum);
                    goodsBillsRK.setPrice("0.01");
                    goodsBillsRK.setGoodsVariableID("个");
                    goodsBillsRK.setMoney(String.valueOf(hbSum));
                    goodsBillsRK.setKcStatus("15");

                    // 添加明细 发送人明细
                    // 查询发红包规则id
                    String de = " from WfjGuize where wfjGuizeName=?";
                    WfjGuize wfjGuizeS = (WfjGuize) baseBeanService.getBeanByHqlAndParams(de,
                            new Object[]{"发送红包"});

                    DetailS.setJifenDetailId(serverService.getServerID("WfjJifenDetail"));
                    DetailS.setRedPacketId(hb.getRedPacketId());
                    DetailS.setJifenDetailDate(new Date());
                    DetailS.setJifenDetailName("发送给" + staffR.getStaffName() + "的金币红包");// 发送给
                    // 接收人
                    DetailS.setJifenDetailScore(goldNum);
                    DetailS.setJifenDetailState(0);
                    DetailS.setWfjGuizeId(wfjGuizeS.getWfjGuizeId());
                    DetailS.setWfjCashId(cashierBillsCK.getCashierBillsID());
                    DetailS.setWfjJifenId(wfjJifenS.getWfjJifenId());

                    // 接收人明细
                    // 查找接收红包的规则id
                    WfjGuize wfjGuizeR = (WfjGuize) baseBeanService.getBeanByHqlAndParams(de,
                            new Object[]{"接收红包"});
                    DetailR.setJifenDetailId(serverService.getServerID("WfjJifenDetail"));
                    DetailR.setRedPacketId(hb.getRedPacketId());
                    DetailR.setJifenDetailDate(new Date());
                    DetailR.setJifenDetailName("收到了" + staffS.getStaffName() + "的金币红包。留言：" + liuYan);
                    DetailR.setJifenDetailScore(goldNum);
                    DetailR.setJifenDetailState(0);
                    DetailR.setWfjGuizeId(wfjGuizeR.getWfjGuizeId());
                    DetailR.setWfjCashId(cashierBillsRK.getCashierBillsID());
                    DetailR.setWfjJifenId(wfjJifenR.getWfjJifenId());

                    // 接收人发送人的总金币的变化数
                    String goldSumS = wfjJifenS.getWfjJifenScore();// 发送人的总金币
                    String goldSumR = wfjJifenR.getWfjJifenScore();// 接收人的总金币

                    float SSum = Float.parseFloat(goldSumS) - Float.parseFloat(goldNum);// 发送金币后，剩余的总金币数
                    float RSum = Float.parseFloat(goldSumR) + Float.parseFloat(goldNum);// 接收金币后，总的金币数

                    wfjJifenS.setWfjJifenScore(String.valueOf(SSum));// 更新发送人总金币数
                    wfjJifenR.setWfjJifenScore(String.valueOf(RSum));// 更新接收人的总金币数

                    // 将新得到的数据存入数据库中，并更新
                    beans.add(wfjJifenS);
                    beans.add(wfjJifenR);
                    beans.add(DetailS);
                    beans.add(DetailR);
                    beans.add(hb);
                    beans.add(cashierBillsRK);
                    beans.add(cashierBillsCK);
                    beans.add(goodsBillsCK);
                    beans.add(goodsBillsRK);

                    baseBeanService.executeHqlsByParamsList(beans, null, null);// 将更新好的数据存入集合中,刷新

                    // 留言功能(发送短信)

						/*
						 * msage.setMobiles(tccR.getAccount());//获得接收人的电话号码
						 *
						 * if(liuYan == null || "".equals(liuYan)){
						 * msage.setMessage("恭喜您，收到"+staffS.getStaffName()+
						 * "赠送的金币红包！！！");//发送人的名字 }else{
						 * msage.setMessage("恭喜您，收到"+staffS.getStaffName()+
						 * "赠送的金币红包！！！留言："+liuYan);//发送人的名字 }
						 *
						 * msage.sendMsg("【微分金平台】");
						 */

                    List<String> ac = new ArrayList<String>();
                    ac.add(tccR.getAccount());
                    JushMain.sendjiguangMessage("恭喜您，收到" + staffS.getStaffName() + "赠送的金币红包!留言：" + liuYan, "",
                            DetailR.getJifenDetailId(), "envelope", ac);

                    return 0;// 操作成功后，返回1，失败返回0
                } else {
                    return 1;
                }
            }else{
                    return 3; //发送金币数量不够
                }
            }
        }
        return 0;
    }
}
