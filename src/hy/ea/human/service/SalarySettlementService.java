package hy.ea.human.service;

import hy.ea.bo.human.salary.SalaryDay;
import hy.plat.bo.PageForm;

import java.util.List;

public interface SalarySettlementService {
	String findSalarySettlementList(PageForm pageForm,String settlementTime) throws Exception;
	String findSalaryStaffSettlementList(PageForm pageForm,String settlementTime) throws Exception;
	void saveEmployeeSalary(String year,String month) throws Exception;
	String findStaffById(String staffId);
	public void saveEmployeeSalaryDay(String year,String date) throws Exception;
	void saveEmployeeSalaryAdd(String year,String month,String[] companyIds) throws Exception;
	String findSalarySettlementAddList(PageForm pageForm,String settlementTime,String staffId) throws Exception;
	String findSalaryStaffSettlementAddList(PageForm pageForm,String settlementTime) throws Exception;
	String findSalaryStaffTimeList(PageForm pageForm,String settlementTimeType) throws Exception;
	void saveCurEmployeeSalaryAdd(String year,String month) throws Exception;
	void deleteSalaryCurMonthAdd();
	List<SalaryDay> findSalaryDayListByMonth(String year, String month, String companyId, String staffId) throws Exception;
	String findSalarySettlementDayList(PageForm pageForm,String settlementTime,String settlementTimeType,String companyId,String staffId) throws Exception;
	String findSalaryStaffTime(String settlementTimeType);

	void deleteSalaryMonthAdd(String[] year,String[] month,String[] companyId);
}
