package com.tiantai.wfj.service;

import com.tiantai.wfj.bo.*;
import com.wechat.bo.sft.Withdraw;
import hy.ea.bo.CAccount;
import hy.ea.bo.CDetail;
import hy.ea.bo.Company;
import hy.ea.bo.human.Staff;
import hy.plat.bo.BaseBean;

import java.util.List;
import java.util.Map;

import hy.plat.bo.PageForm;

public interface WfjJifenService {

	/**
	 * 金币兑现（对接银联代付接口）
	 *
	 * @param sccid
	 * @param user
	 * @param money
	 * @param bankId
	 * @return
	 * @throws Exception
	 */
	String gecOrderAndDeal(String sccid, String user, int money, String bankId) throws Exception;

	boolean buyComany(String goodname, TEshopCustomer cus, Company company, CAccount account, List<BaseBean> beans);

	public void registerCompanyInfo(String ccmomtype, TEshopCusCom scc, Company company, CDetail cdl, int typeNumber);

	public void registerCompanyInfo(String ccmomtype, TEshopCusCom scc, Company company, CDetail cdl);

	void registerCompanyInfoZps(String ccmomtype, TEshopCusCom scc, Company company, CDetail cdl, String ppidUser);

	String zhuCe(TEshopCusCom tuicus, String sccid, String phones, String intf, Staff staff);

	/**
	 * 记录导出信息
	 *
	 * @param excellist
	 */
	void saveExportInfo(List<BaseBean> excellist, String staffID);

	String test(String merSeqId, String sccid, String wfj_user, int money, String bankId) throws Exception;

	/**
	 * （人工打款）提交兑现
	 *
	 * @param sccid
	 * @param user
	 * @param money
	 * @param bankId
	 * @return
	 * @throws Exception
	 */
	String gecOrderAndManualDeal(String sccid, String user, int money, String bankId) throws Exception;

	PageForm getWdaList(PageForm pageForm, int pageNumber, Object[] params);

	List<BaseBean> getExcelList(String companyID, int exportNum, Object[] params);

	/**
	 * 查询需要审核的信息
	 *
	 * @param wdaID
	 * @return
	 */
	WithDrawApply getQueryAudit(String wdaID);

	/**
	 * 添加审核
	 *
	 * @param tradeCode, receiptOprName, payOperatorName
	 * @return
	 */
	boolean addAudit(String tradeCode, String receiptOprName, String payOperatorName, String wdaID, String payState, String auditOpinion, String flag, String payOperatorID, String receiptOprID);

	public String goldWithdrawalVerify(String sccid, String staffId, String wfj_user, String jNumOrder, String money, String trade_no, String methodPay) throws Exception;

	PageForm goldWithdrawalList(int pageNumber, int pageSize, String user, WithDrawReq withDraw, String staffName, String sdate, String edate, String type);

	int saveOrUpdateithDrawReq(WithDrawReq withDrawReq);

	Object getDate(String sccid);

	/**
	 * 微信商户号提现
	 *
	 * @param companyID
	 * @param amount
	 * @param sccid
	 * @param out_request_no
	 * @return
	 */
	String withdrawbywechatsh(String companyID, int amount, String sccid, String out_request_no);


	public WxMonthAccount getWxSummry(String Date, String companyID);


	public PageForm getWxDetailList(int pageNumber, int pageSize, String companyID, String year, String month);

	public Object getWxDetails(String wxdId, String wfjGuizeCalc);

	public String searchWithdraw(String journalNum);

	public void addWxAccountDetail(Withdraw withdraw, String companyID, List<BaseBean> beans);

	}
