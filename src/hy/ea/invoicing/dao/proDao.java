package hy.ea.invoicing.dao;

import hy.plat.bo.BaseBean;

import java.util.List;

public interface proDao {
	
	/**
	 * 月结存储过程调用
	 * @param groupsh 集团标示
	 * @param ym 月结年月
	 * @param com_id 公司id
	 * @return
	 */
	void ProDMCO(String groupsh,String ym,String com_id);
	
	/**
	 * 年结存储过程调用
	 * @param groupsh 集团标示
	 * @param ym 年结年月
	 * @param com_id 公司id
	 * @return
	 */
	String ProDYCO(String groupsh,String ym,String com_id);
	
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
	 *  8.报表类型【报表类型 A：总额式，B：余额式】
	 * @param clazz 返回实体类型
	 */
	@SuppressWarnings("rawtypes")
	List<BaseBean> ProBalances(Object[] obj,Class clazz);
	
	/**
	 * 总额式现金银行流水存储过程调用
	 * @param obj 参数集合
	 * 集合排序：
	 *  1.作业id
	 *  2.公司ID
	 *  3.起始日期
	 *  4.终止日期
	 *  5.起始科目
	 *  6.终止科目  
	 *  7.部门id
	 *  8.现金银行注记 【A.银行，B.现金】
	 * @param clazz 返回实体类型
	 */
	@SuppressWarnings("rawtypes")
	List<Object[]> ProZEDays(Object[] obj,Class clazz);
	
	/**
	 * 余额式现金银行流水存储过程调用
	 * @param obj 参数集合
	 * 集合排序：
	 *  1.作业id
	 *  2.公司ID
	 *  3.起始日期
	 *  4.终止日期
	 *  5.起始科目
	 *  6.终止科目  
	 *  7.部门id
	 *  8.现金银行注记 【A.银行，B.现金】
	 * @param clazz 返回实体类型
	 */
	@SuppressWarnings("rawtypes")
	List<Object[]> ProYEDays(Object[] obj,Class clazz);
	
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
	void probcflexp(Object[] obj);
}
