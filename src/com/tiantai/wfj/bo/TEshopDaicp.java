package com.tiantai.wfj.bo;

import java.util.Date;

import hy.plat.bo.BaseBean;

public class TEshopDaicp  implements BaseBean {
	
	    private String cpkey;
	    private String cpid;//这个代理的的ID
	    private String cpcomdid;//被代理的物品的公司ID
	    private String cpppID;// 被代理的产品id
	    private String cpuserID;//代理人的ID
	    private String cpusercomdid;//代理人的公司ID
	    private Date cpdaidate;//代理这个物品的时间
		public String getCpkey() {
			return cpkey;
		}
		public void setCpkey(String cpkey) {
			this.cpkey = cpkey;
		}
		public String getCpid() {
			return cpid;
		}
		public void setCpid(String cpid) {
			this.cpid = cpid;
		}
		public String getCpcomdid() {
			return cpcomdid;
		}
		public void setCpcomdid(String cpcomdid) {
			this.cpcomdid = cpcomdid;
		}
		public String getCpppID() {
			return cpppID;
		}
		public void setCpppID(String cpppID) {
			this.cpppID = cpppID;
		}
		public String getCpuserID() {
			return cpuserID;
		}
		public void setCpuserID(String cpuserID) {
			this.cpuserID = cpuserID;
		}
		public String getCpusercomdid() {
			return cpusercomdid;
		}
		public void setCpusercomdid(String cpusercomdid) {
			this.cpusercomdid = cpusercomdid;
		}
		public Date getCpdaidate() {
			return cpdaidate;
		}
		public void setCpdaidate(Date cpdaidate) {
			this.cpdaidate = cpdaidate;
		}
	    
	    
	    

}
