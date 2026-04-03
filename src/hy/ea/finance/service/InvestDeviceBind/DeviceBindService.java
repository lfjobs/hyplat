package hy.ea.finance.service.InvestDeviceBind;

import java.util.List;

import hy.ea.bo.CAccount;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

public interface DeviceBindService {
	
	/**
	 * 车辆信息公司汇总列表
	 * @param session
	 * @param account
	 * @param search
	 * @param pageNumber
	 * @return
	 */
	public PageForm CarInforList(CAccount account,String carNum,String deviceStatu,int pageNumber);
	
	/**
	 * 查询投资责任人（所有微分金账号）
	 */
	public PageForm selInvestRespose(int pageNumber,CAccount account,String tzName,String tzAccount,String tzCustype);
	
	/**
	 * 查询所有设备
	 */
	public PageForm selDeviceBind(CAccount account,String carNum,
			String deviceStatu,String tzName,String tzAccount,PageForm pageForm);
	
	/**
	 * 查询公司投资的投资责任人
	 */
	public List<BaseBean> selComInvestment(CAccount account);
	
	/**
	 * 给DeviceBind表添加一条记录
	 */
	public void addDeviceBind(String carid,String goodsid,String investSccid,
			String investStaffid,String carNum);
	
	/**
	 * 添加关联业务员
	 */
	public void addGlStaff(String dbId,String sccidStaffids);
	/**
	 * 查询所有已关联业务员
	 */
	public PageForm selGlStaff(String dbId,PageForm pageForm);
	
	/**
	 * 判断该车辆是否已经被添加到dt_deviceBind表中
	 */
	public String isCarInDevice(String carId,String carnum);
	
	/**
	 * 判断选中业务员是否已被关联
	 */
	public String isStaffInDbs(CAccount account,String sccidStaffids);
	
	/**
	 * 删除deviceBind表的某条数据(即修改该条数据的状态为0，同时修改与该条数据关联的deviceBindStaff表的数据状态为02)
	 */
	public void delDeviceBind(String dbid);
	
	/**
	 * 删除deviceBindStaff表的某条数据(即修改该条数据的状态为01)
	 */
	public void delDeviceBindStaff(String dbsid);
	
	/**
	 * 投资设备绑定设置Excel导出
	 */
	public List<Object> exportExcelDevice(CAccount account,String carNum,
			String deviceStatu,String tzName,String tzAccount);
	
	 
}
