package hy.ea.marketing.service;

import java.util.List;
import java.util.Map;

import hy.ea.bo.finance.BenDis.ProSetupSub;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

public interface ProductAgentService 
{

/**
 * 查询导航
 */
	List<Object> getNavigation();

/**
 * 查询招商产品
 * @param pageNumber 当前页数
 * @param pageSize 显示记录数
 * @param flag 判断标识tpye_ppid
 * @param search 搜索关键词
 * @return
 */	

	PageForm getAgentProductsPageForm(Integer pageNumber, Integer pageSize,String flag,String search);

/**
 *  查询招商产品根据公司id
 *  @param pageNumber 当前页数
 *  @param pageSize 显示记录数
 *  @param companyId 公司id
 *  @param flag 00招商中，01招商结束
 *  @return
 */
	Map<String,Object> getAgentProPageFormByCom(Integer pageNumber,Integer pageSize,String companyId,String flag);

/**
 * 招商详情
 * @param ppId 产品id
 * @return
 */	
	Map<String,Object> getAgentDetail(String ppId);

	/**
	 * 查找区域是否审核通过
	 * @param ppId 产品id
     * @param areappid 区域产品id
	 * @return
	 */
	int getSnapCount(String ppId,String areappid);
/**
 * 发布招商
 * @param ppId 产品id
 * @param html 招商要求文本
 * @return
 */
	String saveProAgent(String ppId, String html);

/**
 * 招商产品详情
 * @param  ppId 产品id
 * @return
 */
	Map<String,Object> proAgentDetail(String ppId);
/**
 * 产品信息
 * @param ppId 产品id
 * @return
 *
 */
	List<BaseBean> proInfo(String ppId);
/**
 * 查看招商要求文本
 * @param suid 佣金id
 * @return
 */
	Map<String,Object> demandsDetail(String suid);
/**
 *同意申请招商
 * @param inapId 申请记录id
 * @return
 */
	void  agreeToInvest(String inapId);
/**
 * 结束招商
 * @param suid 佣金id
 * @param flag 00招商，01招商结束
 */
    void endInvest(String suid,String flag);
/**
 * 立即抢购
 * @param susid 佣金关系id
 */
	String snapUp(String susid);
/**
 * 申请详情
 * @param ppId 产品id
 * @param inapId 申请记录id
 */
	Map<String,Object> applyDetail(String ppId,String inapId);

    /**
     * 批量处理数据
     * @param comid
     * @param sccid
     * @param ppid
     */
	void zsdl(String comid,String sccid,String ppid);
}
