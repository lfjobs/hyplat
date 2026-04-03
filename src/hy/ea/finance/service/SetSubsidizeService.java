package hy.ea.finance.service;

import hy.ea.bo.finance.BenDis.SetSubsidize;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by Administrator on 2018-01-06.
 */
public interface SetSubsidizeService {
    /**
     * 保存
     * @param subsidize 消费补助数据
     * @return
     */
    JSONObject saveSetSubsidize(SetSubsidize subsidize);

    /**
     * 列表查询
     * @param pageForm
     * @param pageNumber
     * @return
     */
    PageForm ListSetSubsidize(PageForm pageForm,int pageNumber);

    /**
     * 查询消费补助类别
     * @return
     */
    JSONObject ListTypeSubsidize();

    /**
     * 查询行业类别
     * @param codePid 行业类别父id
     * @return
     */
    JSONObject getCCodeListByPID(String codePid);

    /**
     * 删除
     * @param ssid 消费补助id
     * @return
     */
    JSONObject delSetSubsidize(String ssid);

    /**
     * 根据往来单位id查询该公司的消费补助分配数据
     * @param ccompanyid 往来单位id
     * @return 消费补助数据
     */
    BaseBean setSubsidizeByType(String ccompanyid);

    /**
     * 根据公司id查询该公司的消费补助分配数据
     * @param companyid 公司id
     * @return 消费补助数据
     */
    BaseBean setSubsidizeByTypeGetcid(String companyid);

    /**
     * 查询消费补助行业是否添加过
     * @param gtid 行业类别
     * @return 条数
     */
    JSONObject getsstype(String gtid);

    /**
     * 查询数据
     * @param sskey key
     * @return 实体
     */
    SetSubsidize getSubsidize(String sskey);

    /**
     * 查询总条数
     * @return
     */
    int subsidizeByCount();

    /**
     * 处理历史数据
     * @return
     */
    void handleHistory(SetSubsidize subsidize);

    /**
     * 发红包
     * @param goldNum 金币数
     * @param sender 发送人
     * @param ssid
     * @param gtid
     * @return
     */
    void slkdfj(String goldNum,String sender,String ssid,String gtid,String strnum,String endnum);

    /**
     * 根据帐号验证交易密码
     * @param zh
     * @param mm
     * @return
     */
    JSONObject getmm(String zh,String mm);
}
