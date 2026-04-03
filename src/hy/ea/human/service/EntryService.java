package hy.ea.human.service;

import hy.ea.bo.human.Audition;
import hy.ea.bo.human.Staff;
import hy.ea.bo.production.recruit.TalentPool;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;

public interface EntryService {

	public PageForm getlist(int pageNumber, int pageSize, String parameter, String status, String companyID);


	public Audition getViewDetail(String auditionID);

	public Audition getViewBc(String auditionID);
	public void passExam(String vm,Audition audition,String staffID);


	public void interviewReg(Audition audition);

	public void bdnotice(Audition audition);

	public TalentPool getTalentPoolDetail(String tpId);


	public Staff getStaffInfo(String sccId);

	/**
	 *
	 * 社会人力
	 * @param companyID
	 * @param parameter
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public PageForm getSocietHumanlist(String companyID,String parameter,int pageNumber,int pageSize);

	/**
	 *
	 * 从人力直接转到面试待登记，无需通知
	 * @return
	 */
	public String zmsRegister(String staffID,String companyID);

	/**
	 *
	 * 保存基本信息
	 * @param staff
	 * @param staffID
	 * @param companyID
	 */
	public void saveInfo(Staff staff,String staffID,String companyID);

	/**
	 *
	 * 验证身份证号
	 * @param card
	 * @return
	 */
	public  String checkCard(String card,String staffID);


	/**
	 *
	 * 验证账号
	 * @param acc
	 * @return
	 */
	public  String checkWfjAccount(String acc);

	/**
	 *
	 * 获取题库
	 * @param type
	 * @param companyID
	 * @return
	 */
	public List<BaseBean> getQuestionBase(String type,String companyID);

}
