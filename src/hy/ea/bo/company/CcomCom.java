package hy.ea.bo.company;

import hy.plat.bo.BaseBean;

/**
 * 往来单位公司关系表
 * @author 
 */
public class CcomCom implements BaseBean{
	private String ccomRelationKey;
	private String ccomRelationId;
	private String comanyId;      //公司业务ID FK
	private String ccompanyId;	   //往来单位ID
	private String state;         //保留字段状态默认0
	
	public CcomCom() {
		super();
	}
	public CcomCom(String comanyId) {
		super();
		this.comanyId=comanyId;
	}
	public CcomCom(String ccomRelationKey, String ccomRelationId,
			String comanyId, String ccompanyId, String state) {
		super();
		this.ccomRelationKey = ccomRelationKey;
		this.ccomRelationId = ccomRelationId;
		this.comanyId = comanyId;
		this.ccompanyId = ccompanyId;
		this.state = state;
	}
	
	
	public String getCcomRelationKey() {
		return ccomRelationKey;
	}
	public void setCcomRelationKey(String ccomRelationKey) {
		this.ccomRelationKey = ccomRelationKey;
	}
	public String getCcomRelationId() {
		return ccomRelationId;
	}
	public void setCcomRelationId(String ccomRelationId) {
		this.ccomRelationId = ccomRelationId;
	}
	public String getComanyId() {
		return comanyId;
	}
	public void setComanyId(String comanyId) {
		this.comanyId = comanyId;
	}
	public String getCcompanyId() {
		return ccompanyId;
	}
	public void setCcompanyId(String ccompanyId) {
		this.ccompanyId = ccompanyId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

}
