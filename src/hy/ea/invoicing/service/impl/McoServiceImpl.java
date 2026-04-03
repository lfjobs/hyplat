package hy.ea.invoicing.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import hy.ea.bo.invoicing.voucher.DtInvMco;
import hy.ea.invoicing.dao.proDao;
import hy.ea.invoicing.service.McoService;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
@Service
public class McoServiceImpl implements McoService {
	@Resource
	private proDao prodao;
	@Resource
	private BaseBeanDao baseDao;
	@Resource
	private BaseBeanService baseBeanService;
	/**
	 * 月结
	 * @param groupsh 集团标示
	 * @param ym 月结年月
	 * @param com_id 公司id
	 * @return
	 */
	@Override
	public void proDMCO(String groupsh,String ym,String com_id){
		prodao.ProDMCO(groupsh, ym, com_id);
	}
	
	/**
	 * 删除月结资料
	 * @param groupsh 集团标示
	 * @param ym 月结年月
	 * @param com_id 公司id
	 * @return
	 */
	@Override
	public void delDMCO(String ym,String com_id){
		String hql="DELETE FROM DtInvDmco WHERE dyco_year = ? AND COMPANYID = ?";
		String [] basehql={hql};
		baseDao.saveBeansListAndexecuteHqlsByParams(null, basehql, new Object[]{com_id,ym});
	}
	
	/**
	 * 年度结转
	 * @param groupsh 集团标示
	 * @param ym 年结年月
	 * @param com_id 公司id
	 * @return
	 */
	@Override
	public String proDYCO(String groupsh,String ym,String com_id){
		return prodao.ProDYCO(groupsh, ym, com_id);
	}
	
	/**
	 * 年结(删掉之后月结资料重新月结)
	 * @param groupsh 集团标示
	 * @param ym 年结年月
	 * @param com_id 公司id
	 * @return
	 */
	@Override
	public String DelDMCOProDYCO(String groupsh,String ym,String com_id){
		String b=proDYCO(groupsh, ym, com_id);
		if(b.equals("")||b==null){
			String hql="from DtInvMco where companyid=? and (yearmonth=? or yearmonth>?) order by yearmonth";
		    List<BaseBean> blist=baseDao.getListBeanByHqlAndParams(hql, new Object[]{com_id,ym,ym});
			if(blist.size()>0){
				for (int i = 0; i < blist.size(); i++) {
					DtInvMco dtInvMco=(DtInvMco)blist.get(i);
					delDMCO(dtInvMco.getYearmonth(),com_id);
					proDMCO(groupsh, dtInvMco.getYearmonth(), com_id);
				}
			}
		}
		return b;
		
	}
	
	/**
	 * 退结年度结转数据
	 * @param groupsh 集团标示
	 * @param ym 年结年月
	 * @param com_id 公司id
	 * @return
	 */
	@Override
	public void delDYCO(String ym,String com_id){
		String hql="delete from DtInvDyco where dycoYear = ? and companyid = ?";
		String hql2="delete from FiscalPeriod where startDate = ? and companyID = ?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql,hql2}, new Object[]{ym,com_id});
	}
	
	/**
	 * 试算表存储过程调用
	 * @param obj 参数集合
	 * 集合排序：
	 *  1.作业id
	 *  2.公司ID
	 *  3.起始日期
	 *  4.终止日期
	 *  5.起始科目
	 *  6.终止科目
	 *  7.部门id
	 *  8.报表类型【报表类型 A：汇总表，B：明细表】
	 * @param clazz 返回实体类型
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<BaseBean> ProBalances(Object[] obj,Class clazz){
		return prodao.ProBalances(obj,clazz);
	}
	
	/**
	 * 现金银行流水存储过程调用
	 * @param obj 参数集合
	 * 参数集合排序：
	 *  1.作业id
	 *  2.公司ID
	 *  3.起始日期
	 *  4.终止日期
	 *  5.起始科目
	 *  6.终止科目  
	 *  7.部门id
	 *  8.现金银行注记 【A.银行，B.现金】
	 * @param clazz 返回实体类型
	 * @return object[] 集合
	 * 	返回结果集合排序：
	 * 		1.总额式
	 * 		2.余额式
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object[] ProDays(Object[] obj,Class clazz){
		return new Object[]{prodao.ProZEDays(obj,clazz),prodao.ProYEDays(obj,clazz)};
	}
	
	/**
	 * 资产负债表、损益表存储过程调用
	 * @param obj 参数集合
	 * 集合排序：
	 *  1.起始日期
	 *  2.终止日期
	 *  3.报表类别（A:资产负债表，B ：损益表C:资产负债表以及损益表 ）
	 *  4.单位注记：Y 千元    N 元
	 *  5.是否列印明细：Y 列印明细N 不列印明细
	 *  6.作业id
	 *  7.公司ID
	 */
	public void probcflexp(Object[] obj) {
		prodao.probcflexp(obj);
	}
}
