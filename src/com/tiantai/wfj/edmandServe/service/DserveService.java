package com.tiantai.wfj.edmandServe.service;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.edmandServe.bo.DemandDetail;
import com.tiantai.wfj.edmandServe.bo.TCtomerWorktype;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import net.sf.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by lf on 2017-07-12.
 * 发布需求抢单接口
 */
public interface DserveService {

    /**
     * 发布需求详情保存（发布需求）
     * @param demandDetail
     * @return
     */
    String saveDetail(DemandDetail demandDetail);

    /**
     * 根据发布帐号查询需求列表
     *
     * @param ddcid 发布关联id
    //* @param tab   0:(我的发布)发布过的需求但还未确认抢单对象  1:(发布记录)发布者已经选了抢单用户了（状态）以及 超时结束的
     * @param tab 0:已抢单 1:未抢单 2:已结算 3:全部
     * @param otype 1:个人 2:企业
     * @return
     */
    List<BaseBean> detailListBySccid(String ddcid, String tab,String otype);

    /**
     * 根据id查询需求详情
     * @param ddid
     * @return
     */
    DemandDetail detailByDdid(String ddid);

    /**
     * 根据需求id查询已抢单责任人列表
     * @param ddid
     * @return
     */
    List<Object> serveListByDdid(String ddid);

    /**
     * 根据地点查询需求列表
     * @param address 地点
     * @return
     */
    List<BaseBean> detailListByAddress(String address);

    /**
     * 查询行业
     * @return
     */
    List<BaseBean> industryList(String level,String pid);

    /**
     * 查询行业
     * @return
     */
    List<BaseBean> industryListNew(String level,String pid);

    /**
     * 查询行业
     * @return
     */
    List<BaseBean> industryListNew(String typeName);

    /**
     * 根据时间查询需求列表
     * @param pageSize 条数
     * @return
     */
    List<BaseBean> detailListByDate(String pageSize,String wts,String wtype) throws Exception;

    /**
     * 分页查询需求列表
     * @param pageNumber
     * @param wts
     * @return
     */
    PageForm detailListBywts(int pageNumber, String wts,String wtype);

    /**
     * 根据抢单人查询需求列表
     * @param cid 抢单人id
     * @return
     */
    List<Object> detailListBydssccid(String cid);

    /**
     * 根据抢单人查询需求列表
     * @param cid 抢单人id
     * @return
     */
    PageForm detailListBydssccid(String cid,int pageNumber);

    /**
     * 保存抢单数据
     *
     * @param ddid  需求id
     * @param sccid 抢单帐号id
     * @param cid 关联id (来源不同关联表不同,来源1:关联staff 2:关联company)
     * @param otype 来源 1:个人 2:企业
     * @param dsaddress 抢单人地址
     * @param coordinate 抢单人经纬度
     * @return
     */
    String saveServe(String ddid, String sccid,String cid,String otype,String dsaddress,String coordinate);

    /**
     * 确认清单人员
     * @param ddid
     * @return
     */
    String updateDetail(String ddid,String dsid);

    /**
     * 查询工种列表
     * @param cwcid 关联id
     * @param user  微分金帐号
     * @param type 类别 1:个人 2:企业
     * @return
     */
    List<BaseBean> wtypeListBySccid(String cwcid, String user,String type);

    /**
     * 判断是否有工种
     * @param sccid 微分金帐号id
     * @param user 微分金帐号
     * @return
     */
    JSONObject wtypeCountBySccid(String sccid, String user);

    /**
     * 保存工种
     * @param user 帐号
     * @param tcw 工种信息
     * @return
     */
    Map<String,Object> saveWtype(String user, TCtomerWorktype tcw);

    /**
     * 删除工种
     * @param cwtid 工种id
     * @return
     */
    String delWtype(String cwtid);

    /**
     * 查看
     * @param cwkey
     * @return
     */
    TCtomerWorktype getWtypeByKey(String cwkey);

    /**
     * 可以被抢单的列表，注册用户无推荐人
     * @return
     */
    PageForm zhuceqdList(int pageNumber);

    /**
     * 用户已抢 抢单服务（服务平台）集合
     * @param cid 用户id
     * @return
     */
    List detailhyListBydssccid(String cid);

    /**
     * 收单服务,查询用户已经抢到的单
     */
    public PageForm shouDan(String sccid,int pageNumber,String phone,String title);

    /**
     * 抢单人详细信息
     */
    public Object selQdPerDetail(String dsid);

    /**
     * 保存确认抢单人信息
     * @param dsid
     * @return
     */
    String[] isOKDemand(String dsid);

    /**
     * 新注册用户确认抢单成功人
     */
    public String[] insureQdPerson(String sccid,String account,String dsid,TEshopCusCom cc);

    /**
     * 判断登录用户的级别以及推介人是否还是默认的
     *比较抢单用户的级别和登录用户的级别
     */
    public String isPanduan(String sccid,String dlSccid);

    /**
     * 根据人员id获取点击量最多的行业
     * @param staffid 人员id
     * @return
     */
    List<BaseBean> industryListNewBybtid(String staffid);
}

